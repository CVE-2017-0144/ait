package dev.amble.lib.datagen.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ShovelMineable {
    Tool tool() default Tool.NONE;

    enum Tool {
        NONE(null),
        STONE(BlockTags.NEEDS_STONE_TOOL),
        IRON(BlockTags.NEEDS_IRON_TOOL),
        DIAMOND(BlockTags.NEEDS_DIAMOND_TOOL),;

        public final TagKey<Block> tag;

        Tool(TagKey<Block> tag) {
            this.tag = tag;
        }
    }
}
