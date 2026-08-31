package dev.amble.ait.core.item;

import dev.amble.ait.api.tardis.link.LinkableItem;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.data.Loyalty;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class InteriorTeleporterItem extends LinkableItem { // todo - new model + texture?
    private static final ParticleOptions PARTICLE_SUCCESS = ParticleTypes.GLOW;
    private static final ParticleOptions PARTICLE_FAIL = ParticleTypes.ELECTRIC_SPARK;

    public InteriorTeleporterItem(Properties settings) {
        super(settings.stacksTo(1).defaultDurability(16), true);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        Tardis tardis = getTardis(world, stack);

        if (world.isClientSide()) {
            boolean success = (tardis != null);

            if (success) {
                Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
            }

            return success ? InteractionResultHolder.sidedSuccess(stack, true) : InteractionResultHolder.fail(stack);
        }
        // server-side

        if (tardis == null)
            return InteractionResultHolder.fail(stack);

        Loyalty loyalty = tardis.loyalty().get(user);
        Loyalty.Type type = loyalty.type();

        boolean success = switch (type) {
            case REJECT, NEUTRAL -> false;
            case PILOT, OWNER -> true;

            case COMPANION -> tardis.travel().isLanded();
        };

        if (!success) {
            createTeleportEffect((ServerPlayer) user, PARTICLE_FAIL);
            world.playSound(null, user.blockPosition(), AITSounds.UNSTABLE_FLIGHT_LOOP, SoundSource.PLAYERS, 1f, 1f);
            user.getCooldowns().addCooldown(this, 4 * 20);

            return InteractionResultHolder.fail(stack);
        }

        createTeleportEffect((ServerPlayer) user, PARTICLE_SUCCESS);
        world.playSound(null, user.blockPosition(), AITSounds.BWEEP, SoundSource.PLAYERS, 1f, 1f);

        TardisUtil.teleportInside(tardis.asServer(), user);

        stack.setCount(stack.getCount() - 1);
        user.getCooldowns().addCooldown(this, 16 * 20);

        BlockPos door = tardis.getDesktop().getDoorPos().getPos();
        createTeleportEffect(tardis.asServer().world(), door.getCenter().subtract(0, 0.5, 0), PARTICLE_SUCCESS);
        world.playSound(null, door, AITSounds.DING, SoundSource.PLAYERS, 1f, 1f);
        world.playSound(null, door, AITSounds.LAND_THUD, SoundSource.PLAYERS, 1f, 1f);

        return InteractionResultHolder.sidedSuccess(stack, true);
    }

    /**
     * Creates a spiral of particles around the player
     * <br>
     * from <a href="https://github.com/Duzos/vortex-manipulator/blob/trunk/src/main/java/mc/duzo/vortex/util/VortexUtil.java">this mod</a>
     */
    private static void createTeleportEffect(ServerLevel world, Vec3 source, ParticleOptions particle) {
        double b = Math.PI / 8;

        Vec3 pos;
        double x;
        double y;
        double z;

        for(double t = 0.0D; t <= Math.PI * 2; t += Math.PI / 16) {
            for (int i = 0; i <= 1; i++) {
                x = 0.4D * (Math.PI * 2 - t) * 0.5D * Math.cos(t + b + i * Math.PI);
                y = 0.5D * t;
                z = 0.4D * (Math.PI * 2 - t) * 0.5D * Math.sin(t + b + i * Math.PI);
                pos = source.add(x, y, z);

                world.sendParticles(particle, pos.x(), pos.y(), pos.z(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }
    private static void createTeleportEffect(ServerPlayer player, ParticleOptions particle) {
        Vec3 dest = player.blockPosition().getCenter().subtract(0, 0.5, 0);
        createTeleportEffect(player.serverLevel(), dest, particle);
    }
}
