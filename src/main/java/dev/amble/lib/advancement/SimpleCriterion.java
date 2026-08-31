package dev.amble.lib.advancement;

import com.google.gson.JsonObject;
import dev.amble.lib.AmbleKit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.ApiStatus;

public class SimpleCriterion extends SimpleCriterionTrigger<SimpleCriterion.Conditions> {
    protected final ResourceLocation id;

    protected SimpleCriterion(ResourceLocation id) {
        this.id = id;
    }

    @Override
    protected SimpleCriterion.Conditions createInstance(JsonObject obj,
                                                            ContextAwarePredicate playerPredicate, DeserializationContext predicateDeserializer) {
        return this.conditions();
    }


    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, SimpleCriterion.Conditions::requirementsMet);
    }

    /**
     * @return a newly created conditions object
     */
    public Conditions conditions() {
        return new Conditions(this.id);
    }

    public SimpleCriterion register() {
        AmbleKit.LOGGER.info("Registering criterion: {}", this.id);

        CriteriaTriggers.register(this);
        return this;
    }

    public static SimpleCriterion create(ResourceLocation id) {
        return new SimpleCriterion(id);
    }

    @ApiStatus.Internal
    public static SimpleCriterion create(String name) {
        return new SimpleCriterion(AmbleKit.id(name));
    }

    public static class Conditions extends AbstractCriterionTriggerInstance {
        public Conditions(ResourceLocation id) {
            super(id, ContextAwarePredicate.ANY);
        }

        boolean requirementsMet() {
            return true;
        }
    }
}
