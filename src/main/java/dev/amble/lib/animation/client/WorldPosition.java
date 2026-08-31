package dev.amble.lib.animation.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.lib.animation.EffectProvider;
import dev.amble.lib.client.bedrock.BedrockAnimation;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.concurrent.atomic.AtomicReference;

public class WorldPosition {
	private static final WorldPosition INSTANCE = new WorldPosition();

	private BlockGetter area;
	@Getter
	private Vec3 pos = Vec3.ZERO;
	private final Vector3f horizontalPlane = new Vector3f(0.0F, 0.0F, 1.0F);
	private final Vector3f verticalPlane = new Vector3f(0.0F, 1.0F, 0.0F);
	private final Vector3f diagonalPlane = new Vector3f(1.0F, 0.0F, 0.0F);
	@Getter
	private float pitch;
	@Getter
	private float yaw;
	private final Quaternionf rotation = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);

	protected void moveBy(double x, double y, double z) {
		double d = (double) this.horizontalPlane.x() * x + (double) this.verticalPlane.x() * y + (double) this.diagonalPlane.x() * z;
		double e = (double) this.horizontalPlane.y() * x + (double) this.verticalPlane.y() * y + (double) this.diagonalPlane.y() * z;
		double f = (double) this.horizontalPlane.z() * x + (double) this.verticalPlane.z() * y + (double) this.diagonalPlane.z() * z;
		this.setPos(new Vec3(this.pos.x + d, this.pos.y + e, this.pos.z + f));
	}

	protected void setRotation(float yaw, float pitch) {
		this.pitch = pitch;
		this.yaw = yaw;
		this.rotation.rotationYXZ(-yaw * (float) (Math.PI / 180.0), pitch * (float) (Math.PI / 180.0), 0.0F);
		this.horizontalPlane.set(0.0F, 0.0F, 1.0F).rotate(this.rotation);
		this.verticalPlane.set(0.0F, 1.0F, 0.0F).rotate(this.rotation);
		this.diagonalPlane.set(1.0F, 0.0F, 0.0F).rotate(this.rotation);
	}

	protected void setRotation(Vec3 rotation) {
		Tuple<Float, Float> rots = BedrockAnimation.eulerToPitchYaw(rotation);

		this.pitch = rots.getA();
		this.yaw = rots.getB();

		this.rotation.rotationXYZ((float) rotation.x(), (float) rotation.y(), (float) rotation.z());
		this.horizontalPlane.set(0.0F, 0.0F, 1.0F).rotate(this.rotation);
		this.verticalPlane.set(0.0F, 1.0F, 0.0F).rotate(this.rotation);
		this.diagonalPlane.set(1.0F, 0.0F, 0.0F).rotate(this.rotation);
	}

	protected void setPos(double x, double y, double z) {
		this.setPos(new Vec3(x, y, z));
	}

	protected void setPos(Vec3 pos) {
		this.pos = pos;
	}

	public void spawnParticle(ParticleOptions particle, Vec3 velocity, int count) {
		if (!(this.area instanceof ClientLevel world)) return;

		for (int i = 0; i < count; i++) {
			world.addParticle(particle, this.pos.x, this.pos.y, this.pos.z, velocity.x, velocity.y, velocity.z);
		}
	}

	public WorldPosition update(BedrockAnimation anim, String boneName, float progress, EffectProvider target, ModelPart root) {
		float tickDelta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);

		this.area = target.getWorld();

		this.setPos(
			target.getEffectPosition(tickDelta)
		);

		Vec3 position = anim.boneTimelines.containsKey(boneName) ? anim.boneTimelines.get(boneName).position().resolve(progress) : Vec3.ZERO;
		Vec3 animRotation = anim.boneTimelines.containsKey(boneName) ? anim.boneTimelines.get(boneName).rotation().resolve(progress) : Vec3.ZERO;

		AtomicReference<Float> height = new AtomicReference<>((float) 0);
		AtomicReference<Float> lowest = new AtomicReference<>((float) 0);

		ModelPart bone = root.getAllParts().filter(part -> part.hasChild(boneName)).findFirst().map(part -> part.getChild(boneName)).orElse(null);

		if (bone != null) {
			position = position.add(bone.getInitialPose().x, bone.getInitialPose().y, bone.getInitialPose().z);

			bone.visit(new PoseStack(), (matrix, path, index, cuboid) -> {
				height.updateAndGet(v -> cuboid.minY + bone.getInitialPose().y + -1.68F*16F);
			});

			root.visit(new PoseStack(), (matrix, path, index, cuboid) -> {
				lowest.updateAndGet(v -> Math.max(v, cuboid.maxY + bone.getInitialPose().y));
			});
		}

		Tuple<Float, Float> rots = anim.getRotations(boneName, progress);
		float animYaw = rots.getB();
		float animPitch = rots.getA();

		float entityYaw;

		if (anim.metadata.fpsCameraCopiesHead()) {
			entityYaw = (target instanceof LocalPlayer clientPlayer) ? (Mth.rotLerp(tickDelta, clientPlayer.yHeadRotO, clientPlayer.yHeadRot)) : target.getHeadYaw();
		} else {
			entityYaw = (target instanceof LocalPlayer clientPlayer) ? (Mth.rotLerp(tickDelta, clientPlayer.yBodyRotO, clientPlayer.yBodyRot)) : target.getBodyYaw();
		}

		float entityPitch = (target instanceof LocalPlayer clientPlayer) ? (Mth.lerp(tickDelta, clientPlayer.xRotO, clientPlayer.getViewXRot(tickDelta))) : target.getPitch();

		Vec3 relativePos = position.yRot((float) Math.toRadians(90)).scale(-1 / 16F);
		this.setRotation(entityYaw, 0);
		// todo \/ the clipping causes the camera to break when on ground
		this.moveBy(relativePos.x, relativePos.y + 1.68, relativePos.z);
		//this.setRotation(animRotation);
		this.setRotation(animYaw + entityYaw, animPitch);

		this.moveBy(0, height.get() / 32F, 0);


		return this;
	}

	public static WorldPosition create(BedrockAnimation anim, String part, float progress, EffectProvider target, ModelPart root) {
		return new WorldPosition().update(anim, part, progress, target, root);
	}

	public static WorldPosition get(BedrockAnimation anim, String part, float progress, EffectProvider target, ModelPart root) {
		return INSTANCE.update(anim, part, progress, target, root);
	}
}
