package dev.amble.ait.client.renderers;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.function.Function;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class AITRenderLayers extends RenderType {

    /**
     * The emissive layer, always with the quad sorter.
     *
     * <p>The sorter is not an optimisation to be skipped when the alpha is opaque. This layer writes
     * no depth ({@code COLOR_WRITE}) and disables back face culling, so nothing in the batch occludes
     * anything else in it and both faces of every part are submitted. Whichever quad is drawn last
     * wins, which leaves submission order as the only thing deciding what ends up on top: a part's
     * own back face, or a light sitting behind a panel, will paint over the front of it.
     *
     * <p>That is independent of the texture's alpha. Binary alpha makes the <em>blend</em> order
     * independent, not the occlusion, and dropping the sorter on that reasoning put lights through
     * panels and left the animated monitor glow on Renaissance and Toyota not reading as animated.
     *
     * <p>It does cost. {@code BufferBuilder.setSorter} allocates a primitive centre per quad and
     * writes an explicit index buffer instead of reusing the shared sequential one, which on Copper's
     * roughly 6800 quad emission pass measured about 0.6 ms a frame. That is the price of drawing it
     * in the right order.
     */
    private static RenderType emissive(ResourceLocation texture) {
        RenderStateShard.TextureStateShard texture2 = new RenderStateShard.TextureStateShard(texture, false, false);
        CompositeState multiPhaseParameters = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_EYES_SHADER)
                .setTextureState(texture2)
                .setCullState(NO_CULL)
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                .setLightmapState(LIGHTMAP)
                .setWriteMaskState(COLOR_WRITE)
                .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                .createCompositeState(false);

        return RenderType.create("emissive_cull_z_offset",
                DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256,
                false, true, multiPhaseParameters);
    }

    private static final Function<ResourceLocation, RenderType> EMISSIVE = Util.memoize(AITRenderLayers::emissive);

    /** One layer per emission texture. See {@link #emissive} for why the sort is not optional. */
    public static RenderType tardisEmissiveCullZOffset(ResourceLocation texture) {
        return EMISSIVE.apply(texture);
    }

    private AITRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.Mode drawMode,
                            int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction,
                            Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static RenderType getBoti() {
        CompositeState parameters = CompositeState.builder()
                .setTextureState(RenderStateShard.BLOCK_SHEET_MIPPED)
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setLayeringState(RenderStateShard.NO_LAYERING)
                .createCompositeState(false);
        return RenderType.create("boti", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
                VertexFormat.Mode.QUADS, 256, false, true, parameters);
    }

    public static RenderType getBotiInteriorEmission(ResourceLocation texture) {
        CompositeState parameters = CompositeState.builder()
                .setTextureState(new TextureStateShard(texture, false, false))
                .setShaderState(RENDERTYPE_ENTITY_CUTOUT_NO_CULL_Z_OFFSET_SHADER)
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                .createCompositeState(false);
        return RenderType.create("boti_interior_emission", DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS, 256, false, true, parameters);
    }

    public static RenderType getBotiInterior(ResourceLocation texture) {
        // Not memoized, unlike EMISSIVE_CULL_Z_OFFSET above. Counted so the per-frame allocation rate is
        // visible rather than inferred.
        Minecraft.getInstance().getProfiler().incrementCounter("ait_renderlayer_alloc");

        CompositeState parameters = CompositeState.builder()
                .setTextureState(new TextureStateShard(texture, false, false))
                .setShaderState(RENDERTYPE_ENTITY_CUTOUT_NO_CULL_SHADER)
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setCullState(CULL)
                .setLayeringState(RenderStateShard.NO_LAYERING)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                .createCompositeState(false);
        return RenderType.create("boti_interior", DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS, 256, false, true, parameters);
    }
}
