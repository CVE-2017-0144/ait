package dev.amble.lib.client.bedrock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public record BedrockPerFaceQuad(
        Vector3f p0, Vector3f p1, Vector3f p2, Vector3f p3,
        float u0, float v0, float u1, float v1,
        Vector3f normal
) {
    public void render(PoseStack.Pose entry, VertexConsumer vertices, int light, int overlay,
                       float red, float green, float blue, float alpha) {
        Matrix4f position = entry.pose();
        Matrix3f normalMat = entry.normal();

        Vector3f n = new Vector3f(normal);
        n.mul(normalMat);

        vertex(vertices, position, p0, u0, v0, n, light, overlay, red, green, blue, alpha);
        vertex(vertices, position, p1, u1, v0, n, light, overlay, red, green, blue, alpha);
        vertex(vertices, position, p2, u1, v1, n, light, overlay, red, green, blue, alpha);
        vertex(vertices, position, p3, u0, v1, n, light, overlay, red, green, blue, alpha);
    }

    private static void vertex(VertexConsumer vc, Matrix4f pos, Vector3f p,
                               float u, float v, Vector3f n,
                               int light, int overlay,
                               float red, float green, float blue, float alpha) {
        vc.vertex(pos, p.x(), p.y(), p.z())
                .color(red, green, blue, alpha)
                .uv(u, v)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(n.x(), n.y(), n.z())
                .endVertex();
    }
}