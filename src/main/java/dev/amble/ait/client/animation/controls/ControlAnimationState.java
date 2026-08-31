package dev.amble.ait.client.animation.controls;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.world.entity.AnimationState;

public class ControlAnimationState {
    private final AnimationDefinition animation;
    private final AnimationState state;

    public ControlAnimationState(AnimationDefinition animation) {
        this.animation = animation;
        this.state = new AnimationState();
    }

    public AnimationDefinition getAnimation() {
        return animation;
    }

    public AnimationState getAnimationState() {
        return state;
    }

    public boolean isRunning() {
        return state.isStarted();
    }

    public void start(int age) {
        state.start(age);
    }

    public void startIfNotRunning(int age) {
        state.startIfStopped(age);
    }

    public void stop() {
        state.stop();
    }

    // Add any other methods you need
}
