package dev.amble.lib.datagen.tag;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import dev.amble.lib.datagen.util.AxeMineable;
import dev.amble.lib.datagen.util.HoeMineable;
import dev.amble.lib.datagen.util.ShovelMineable;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import dev.amble.lib.container.impl.BlockContainer;
import dev.amble.lib.datagen.util.PickaxeMineable;
import dev.amble.lib.util.ReflectionUtil;

public class AmbleBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    protected Queue<Class<? extends BlockContainer>> blockClass;

    public AmbleBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);

        this.blockClass = new LinkedList<>();
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        this.blockClass.forEach(clazz -> {
            FabricTagBuilder pickaxeBuilder = getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE);
            HashMap<Block, Optional<PickaxeMineable>> pickaxeBlocks = ReflectionUtil.getAnnotatedValues(clazz, Block.class, PickaxeMineable.class, false);

            for (Block block : pickaxeBlocks.keySet()) {
                pickaxeBuilder.add(block);
                PickaxeMineable annotation = pickaxeBlocks.get(block).orElseThrow();

                if (annotation.tool() != PickaxeMineable.Tool.NONE) {
                    getOrCreateTagBuilder(annotation.tool().tag).add(block);
                }
            }
        });
        this.blockClass.forEach(clazz -> {
            FabricTagBuilder pickaxeBuilder = getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE);
            HashMap<Block, Optional<AxeMineable>> axeBlocks = ReflectionUtil.getAnnotatedValues(clazz, Block.class, AxeMineable.class, false);

            for (Block block : axeBlocks.keySet()) {
                pickaxeBuilder.add(block);
                AxeMineable annotation = axeBlocks.get(block).orElseThrow();

                if (annotation.tool() != AxeMineable.Tool.NONE) {
                    getOrCreateTagBuilder(annotation.tool().tag).add(block);
                }
            }
        });
        this.blockClass.forEach(clazz -> {
            FabricTagBuilder pickaxeBuilder = getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_HOE);
            HashMap<Block, Optional<HoeMineable>> axeBlocks = ReflectionUtil.getAnnotatedValues(clazz, Block.class, HoeMineable.class, false);

            for (Block block : axeBlocks.keySet()) {
                pickaxeBuilder.add(block);
                HoeMineable annotation = axeBlocks.get(block).orElseThrow();

                if (annotation.tool() != HoeMineable.Tool.NONE) {
                    getOrCreateTagBuilder(annotation.tool().tag).add(block);
                }
            }
        });
        this.blockClass.forEach(clazz -> {
            FabricTagBuilder pickaxeBuilder = getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL);
            HashMap<Block, Optional<ShovelMineable>> axeBlocks = ReflectionUtil.getAnnotatedValues(clazz, Block.class, ShovelMineable.class, false);

            for (Block block : axeBlocks.keySet()) {
                pickaxeBuilder.add(block);
                ShovelMineable annotation = axeBlocks.get(block).orElseThrow();

                if (annotation.tool() != ShovelMineable.Tool.NONE) {
                    getOrCreateTagBuilder(annotation.tool().tag).add(block);
                }
            }
        });
    }

    @SafeVarargs
    public final AmbleBlockTagProvider withBlocks(Class<? extends BlockContainer>... blockClass) {
        // add all to queue
        this.blockClass.addAll(Arrays.asList(blockClass));

        return this;
    }
}
