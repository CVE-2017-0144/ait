package dev.amble.ait.core.tardis.control;

import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.impl.SecurityControl;
import dev.amble.ait.core.tardis.control.sound.ControlSoundRegistry;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.data.schema.console.ConsoleTypeSchema;
import dev.amble.lib.api.Identifiable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;


public class Control implements Identifiable {

    private final ResourceLocation id;

    public Control(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation id() {
        return id;
    }

    protected Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console,
                             boolean leftClick) throws ControlSequencedException {
        if (this.shouldBeAddedToSequence(tardis)) {
            this.addToControlSequence(tardis, player, console);
            throw ControlSequencedException.INSTANCE;
        }

        TardisEvents.USE_CONTROL.invoker().onUse(this, tardis, player, world, console, leftClick);

        return Result.FAILURE;
    }

    public Result handleRun(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console,
                             boolean leftClick) {
        try {
            return this.runServer(tardis, player, world, console, leftClick);
        } catch (Control.ControlSequencedException e) {
            return Result.SEQUENCE;
        }
    }

    /**
     * The label shown for this control (e.g. when scanning it with the sonic). May vary with live
     * TARDIS state; the default implementation returns the static, translatable control name.
     */
    public Component getName(Tardis tardis) {
        return Component.translatable(id.toLanguageKey("control"));
    }

    protected boolean shouldBeAddedToSequence(Tardis tardis) {
        return tardis.sequence().hasActiveSequence() && tardis.sequence().controlPartOfSequence(this);
    }

    public void addToControlSequence(Tardis tardis, ServerPlayer player, BlockPos pos) {
        tardis.sequence().add(this, player, pos);

        if (AITMod.RANDOM.nextInt(0, 20) == 4) {
            tardis.loyalty().addLevel(player, 1);

            player.serverLevel().sendParticles(ParticleTypes.HEART, pos.getCenter().x(),
                    pos.getCenter().y() + 1, pos.getCenter().z(), 1, 0f, 1F, 0f, 5.0F);
        }
    }


    public SoundEvent getFallbackSound() {
        return null;
    }

    /**
     * Get the sound to play when this control is used
     * @param console The console variant this control is being used on
     * @param result Result of the control
     * @return The sound to play
     */
    public SoundEvent getSound(ConsoleTypeSchema console, Result result) {
        SoundEvent sound = ControlSoundRegistry.getInstance().get(console, this).sound(result);

        if (this.getFallbackSound() != null && (sound == null || sound == AITSounds.ERROR)) {
            return this.getFallbackSound();
        }

        return sound;
    }

    public boolean requiresPower() {
        return true;
    }

    protected SubSystem.IdLike requiredSubSystem() {
        return SubSystem.Id.ENGINE;
    }

    public void runAnimation(Tardis tardis, ServerPlayer player, ServerLevel world) {
        // no animation
    }

    @Override
    public String toString() {
        return "Control{" + "id='" + id + '\'' + '}';
    }

    public long getDelayLength(Tardis tardis) {
        return 5;
    }

    public boolean shouldHaveDelay() {
        return true;
    }

    public boolean shouldHaveDelay(Tardis tardis) {
        return !this.shouldBeAddedToSequence(tardis) && this.shouldHaveDelay();
    }

    // Bypass security controls when using the disc :al_clueless: - Loqorb
    public boolean ignoresSecurity(ServerPlayer user) {
        return user.getMainHandItem().getItem() == AITItems.CONTROL_DISC;
    }

    public boolean canRun(Tardis tardis, ServerPlayer user) {
        if (tardis.isGrowth())
            return false;

        if (this.requiresPower() && !tardis.fuel().hasPower())
            return false;

        boolean security = tardis.stats().security().get();

        if (!this.ignoresSecurity(user) && security)
            return SecurityControl.hasMatchingKey(user, tardis);

        SubSystem.IdLike dependent = this.requiredSubSystem();

        if (dependent != null && !this.shouldBeAddedToSequence(tardis)) {
            boolean enabled = tardis.subsystems().get(dependent).isEnabled();

            if (!enabled)
                user.displayClientMessage(Component.translatable("warning.ait.needs_subsystem", Component.literal(WorldUtil.fakeTranslate(dependent.toString())).withStyle(ChatFormatting.RED)).withStyle(ChatFormatting.WHITE), true);

            return enabled;
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || this.getClass() != o.getClass())
            return false;

        Control control = (Control) o;
        return this.id.equals(control.id());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    public enum Result {
        SUCCESS, FAILURE, SEQUENCE, SUCCESS_ALT;

        public boolean isSuccess() {
            return this == SUCCESS || this == SUCCESS_ALT;
        }

        public boolean isAltSound() {
            return this == SUCCESS_ALT || this == FAILURE;
        }
    }

    public static class ControlSequencedException extends RuntimeException {
        /**
         * The singleton instance, to reduce object allocations.
         */
        public static final ControlSequencedException INSTANCE = new ControlSequencedException();

        private ControlSequencedException() {
            this.setStackTrace(new StackTraceElement[0]);
        }

        public synchronized Throwable fillInStackTrace() {
            this.setStackTrace(new StackTraceElement[0]);
            return this;
        }
    }

    public static class ControlState {
        private float damage = 1.0f;
        private boolean sticky = false;

        public float damage() {
            return damage;
        }

        public boolean sticky() {
            return sticky;
        }

        public ControlState setDamage(float damage) {
            this.damage = damage;
            return this;
        }

        public ControlState setSticky(boolean sticky) {
            this.sticky = sticky;
            return this;
        }

        public CompoundTag writeNbt() {
            CompoundTag stateCompound = new CompoundTag();
            stateCompound.putFloat("Durability", this.damage);
            stateCompound.putBoolean("Sticky", this.sticky);
            return stateCompound;
        }

        public ControlState readNbt(CompoundTag nbt) {
            float durability = nbt.getFloat("Durability");
            boolean sticky = nbt.getBoolean("Sticky");
            this.setDamage(durability);
            this.setSticky(sticky);
            return this;
        }
    }
}
