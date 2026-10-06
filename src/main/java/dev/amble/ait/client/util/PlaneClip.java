package dev.amble.ait.client.util;

import java.util.IdentityHashMap;
import java.util.Map;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import dev.amble.ait.compat.portal.PortalsAPI;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.Level;

public final class PlaneClip implements MultiBufferSource {

    // keeps what lies on the plane
    private static final float EPS = 1e-4f;

    private final Map<RenderType, Clip> clips = new IdentityHashMap<>();
    private final double[] plane = new double[6];
    private MultiBufferSource src;
    private float nx, ny, nz, d;

    public MultiBufferSource wrap(MultiBufferSource src, Level level) {
        double[] plane = this.plane;

        if (!PortalsAPI.CLIP_PLANE.test(level, plane))
            return src;

        this.src = src;
        this.nx = (float) plane[3];
        this.ny = (float) plane[4];
        this.nz = (float) plane[5];
        this.d = (float) -(plane[3] * plane[0] + plane[4] * plane[1] + plane[5] * plane[2]) + EPS;
        return this;
    }

    @Override
    public VertexConsumer getBuffer(RenderType type) {
        VertexConsumer dst = this.src.getBuffer(type);

        if (type.mode() != VertexFormat.Mode.QUADS)
            return dst;

        Clip clip = this.clips.get(type);

        if (clip == null) {
            clip = new Clip(this, type.format());
            this.clips.put(type, clip);
        }

        clip.dst = dst;
        return clip;
    }

    private static final class Clip implements VertexConsumer {
        private static final int COLOR = 1, UV0 = 2, UV1 = 4, UV2 = 8, NORMAL = 16;

        private final PlaneClip plane;
        private final int need;
        private VertexConsumer dst;

        private final float[][] in = new float[4][8];
        private final int[][] inInt = new int[4][3];
        private final float[][] out = new float[5][8];
        private final int[][] outInt = new int[5][3];
        private int n, have;

        Clip(PlaneClip plane, VertexFormat format) {
            this.plane = plane;
            this.need = (format.contains(VertexFormatElement.COLOR) ? COLOR : 0)
                    | (format.contains(VertexFormatElement.UV0) ? UV0 : 0)
                    | (format.contains(VertexFormatElement.UV1) ? UV1 : 0)
                    | (format.contains(VertexFormatElement.UV2) ? UV2 : 0)
                    | (format.contains(VertexFormatElement.NORMAL) ? NORMAL : 0);
        }

        @Override
        public void addVertex(float x, float y, float z, int color, float u, float v, int overlay, int light, float nx, float ny, float nz) {
            float[] p = this.in[this.n];
            p[0] = x;
            p[1] = y;
            p[2] = z;
            p[3] = u;
            p[4] = v;
            p[5] = nx;
            p[6] = ny;
            p[7] = nz;

            int[] q = this.inInt[this.n];
            q[0] = color;
            q[1] = overlay;
            q[2] = light;
            this.next();
        }

        @Override
        public VertexConsumer addVertex(float x, float y, float z) {
            float[] p = this.in[this.n];
            p[0] = x;
            p[1] = y;
            p[2] = z;

            int[] q = this.inInt[this.n];
            q[0] = -1;
            q[1] = 0;
            q[2] = 0;
            this.have = 0;
            return this.fill(0);
        }

        @Override
        public VertexConsumer setColor(int r, int g, int b, int a) {
            this.inInt[this.n][0] = FastColor.ARGB32.color(a, r, g, b);
            return this.fill(COLOR);
        }

        @Override
        public VertexConsumer setUv(float u, float v) {
            float[] p = this.in[this.n];
            p[3] = u;
            p[4] = v;
            return this.fill(UV0);
        }

        @Override
        public VertexConsumer setUv1(int u, int v) {
            this.inInt[this.n][1] = u & 0xFFFF | v << 16;
            return this.fill(UV1);
        }

        @Override
        public VertexConsumer setUv2(int u, int v) {
            this.inInt[this.n][2] = u & 0xFFFF | v << 16;
            return this.fill(UV2);
        }

        @Override
        public VertexConsumer setNormal(float x, float y, float z) {
            float[] p = this.in[this.n];
            p[5] = x;
            p[6] = y;
            p[7] = z;
            return this.fill(NORMAL);
        }

        private VertexConsumer fill(int bit) {
            this.have |= bit;

            if ((this.have & this.need) == this.need) {
                this.have = 0;
                this.next();
            }

            return this;
        }

        private void next() {
            if (++this.n < 4)
                return;

            this.n = 0;
            this.flush();
        }

        private float side(float[] p) {
            return this.plane.nx * p[0] + this.plane.ny * p[1] + this.plane.nz * p[2] + this.plane.d;
        }

        private void flush() {
            int m = 0;

            for (int i = 0; i < 4; i++) {
                int j = (i + 1) & 3;
                float[] a = this.in[i];
                float[] b = this.in[j];
                float sa = this.side(a);
                float sb = this.side(b);

                if (sa >= 0) {
                    System.arraycopy(a, 0, this.out[m], 0, 8);
                    System.arraycopy(this.inInt[i], 0, this.outInt[m], 0, 3);
                    m++;
                }

                if ((sa >= 0) == (sb >= 0))
                    continue;

                float t = sa / (sa - sb);
                float[] o = this.out[m];

                for (int k = 0; k < 8; k++)
                    o[k] = a[k] + (b[k] - a[k]) * t;

                int[] qa = this.inInt[i];
                int[] qb = this.inInt[j];
                int[] qi = sa >= 0 ? qa : qb;
                int[] q = this.outInt[m];
                q[0] = qa[0] == qb[0] ? qa[0] : FastColor.ARGB32.lerp(t, qa[0], qb[0]);
                q[1] = qi[1];
                q[2] = qi[2];
                m++;
            }

            if (m < 3)
                return;

            // repeated vertex goes last, iris builds tangents from the first three
            this.emit(0, 1, 2, m > 3 ? 3 : 2);

            if (m == 5)
                this.emit(0, 3, 4, 4);
        }

        private void emit(int a, int b, int c, int e) {
            this.put(a);
            this.put(b);
            this.put(c);
            this.put(e);
        }

        private void put(int i) {
            float[] p = this.out[i];
            int[] q = this.outInt[i];
            this.dst.addVertex(p[0], p[1], p[2], q[0], p[3], p[4], q[1], q[2], p[5], p[6], p[7]);
        }
    }
}
