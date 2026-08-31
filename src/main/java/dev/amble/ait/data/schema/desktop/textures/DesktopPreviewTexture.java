package dev.amble.ait.data.schema.desktop.textures;

import dev.amble.ait.data.schema.desktop.TardisDesktopSchema;
import net.minecraft.resources.ResourceLocation;

public class DesktopPreviewTexture {
    // public static final Codec<DesktopPreviewTexture> CODEC =
    // RecordCodecBuilder.create(
    // instance -> instance.group(
    // Identifier.CODEC.fieldOf("path").forGetter(texture -> texture.path),
    // Codec.INT.fieldOf("width").forGetter(texture -> texture.width),
    // Codec.INT.fieldOf("height").forGetter(texture -> texture.height)
    // )
    // )

    private final ResourceLocation path;
    public final int width;
    public final int height;

    public DesktopPreviewTexture(ResourceLocation path, int width, int height) {
        this.path = path;
        this.width = width;
        this.height = height;
    }

    public DesktopPreviewTexture(TardisDesktopSchema schema, int width, int height) {
        this(pathFromDesktopId(schema.id()), width, height);
    }

    public DesktopPreviewTexture(ResourceLocation path) {
        this(path, 128, 128);
    }

    public DesktopPreviewTexture(TardisDesktopSchema schema) {
        this(schema.id());
    }

    public ResourceLocation texture() {
        return this.path;
    }

    public static ResourceLocation pathFromDesktopId(ResourceLocation desktopId) {
        return ResourceLocation.fromNamespaceAndPath(desktopId.getNamespace(), "textures/desktop/" + desktopId.getPath() + ".png");
    }
}
