package dev.amble.ait.core.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.ApiStatus;
import dev.amble.ait.AITMod;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class SimpleCriterion extends SimpleCriterionTrigger<SimpleCriterion.Conditions> {

    private static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::player))
            .apply(instance, Conditions::new));

    protected final ResourceLocation id;

    protected SimpleCriterion(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public Codec<Conditions> codec() {
        return CODEC;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, Conditions::requirementsMet);
    }

    /**
     * @return a newly created conditions object
     */
    public Conditions conditions() {
        return new Conditions(Optional.empty());
    }

    public SimpleCriterion register() {
        AITMod.LOGGER.info("Registering criterion: {}", this.id);

        Registry.register(BuiltInRegistries.TRIGGER_TYPES, this.id, this);
        return this;
    }

    public static SimpleCriterion create(ResourceLocation id) {
        return new SimpleCriterion(id);
    }

    @ApiStatus.Internal
    public static SimpleCriterion create(String name) {
        return new SimpleCriterion(AITMod.id(name));
    }

    public record Conditions(Optional<ContextAwarePredicate> player)
            implements SimpleCriterionTrigger.SimpleInstance {

        boolean requirementsMet() {
            return true;
        }
    }
}
