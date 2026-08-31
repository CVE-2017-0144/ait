package dev.amble.lib.datagen.advancement;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AmbleAdvancementProvider extends FabricAdvancementProvider {

    private final List<Builder> builders = new ArrayList<>();

    public AmbleAdvancementProvider(FabricDataOutput output) {
        super(output);
    }

    public Builder create(Advancement parent, String name) {
        Builder result = new Builder(parent, name);
        builders.add(result);
        return result;
    }

    public Builder create(String name) {
        return create(null, name);
    }

    public Builder task(Advancement parent, String name) {
        return create(parent, name);
    }

    public Builder task(String name) {
        return create(name);
    }

    public Builder challenge(Advancement parent, String name) {
        return create(parent, name).frame(AdvancementType.CHALLENGE);
    }

    public Builder goal(Advancement parent, String name) {
        return create(parent, name).frame(AdvancementType.GOAL);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        for (Builder builder : builders) {
            consumer.accept(builder.build());
        }
    }

    public class Builder {

        private final Advancement.Builder builder;

        private ItemLike item = Items.BARRIER;
        private boolean hidden = false;
        private AdvancementType frame = AdvancementType.TASK;
        private ResourceLocation background;
        private boolean announce = true;
        private boolean showToast = true;

        private final String name;

        public Builder(Advancement parent, String name) {
            this.builder = Advancement.Builder.advancement().parent(parent);
            this.name = name;
        }

        public Builder condition(String name, CriterionTriggerInstance conditions) {
            this.builder.addCriterion(name, conditions);
            return this;
        }

        public Builder icon(ItemLike item) {
            this.item = item;
            return this;
        }

        public Builder hidden() {
            this.hidden = true;
            return this;
        }

        public Builder frame(AdvancementType frame) {
            this.frame = frame;
            return this;
        }

        public Builder background(ResourceLocation background) {
            this.background = background;
            return this;
        }

        public Builder background(String background) {
            return background(ResourceLocation.fromNamespaceAndPath(AmbleAdvancementProvider.this.output.getModId(), background));
        }

        public Builder silent() {
            this.announce = false;
            return this;
        }

        public Builder noToast() {
            this.showToast = false;
            return this;
        }

        public Advancement build() {
            String modId = AmbleAdvancementProvider.this.output.getModId();

            return builder
                    .display(item,
                            Component.translatable("achievement." + modId + ".title." + name),
                            Component.translatable("achievement." + modId + ".description." + name),
                            background, frame, showToast, announce, hidden)
                    .save(advancement -> {}, modId + ":" + name);
        }
    }
}
