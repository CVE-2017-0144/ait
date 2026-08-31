package dev.amble.ait.client.animation.console.hartnell;

import static dev.amble.ait.client.animation.AnimationConstants.STEP;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class HartnellAnimations {

    public static final AnimationDefinition ROTOR = AnimationDefinition.Builder.withLength(3.4f).looping()
            .addAnimation("rotor",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.68f, KeyframeAnimations.posVec(0f, -4f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.36f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("compass",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.68f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.36f, KeyframeAnimations.degreeVec(0f, 360f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_INFLIGHT_ANIMATION = AnimationDefinition.Builder.withLength(8f).looping()
            .addAnimation("rotor",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2f, KeyframeAnimations.posVec(0f, -4f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6f, KeyframeAnimations.posVec(0f, -4f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("compass",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(8f, KeyframeAnimations.degreeVec(0f, -360f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone166",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(1.3433333f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(5.343333f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP)))
            .addAnimation("bone169",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(1.3433333f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(5.343333f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP)))
            .addAnimation("bone167",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(1.3433333f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(2.6766665f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(5.343333f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(6.676667f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone170",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(1.3433333f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(2.6766665f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(5.343333f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(6.676667f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone168",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(2.6766665f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(6.676667f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone171",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(2.6766665f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(6.676667f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone91",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.2083435f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(2.7916765f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(4f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(5.041677f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(6.208343f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(6.834333f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(7.416767f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone93",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.75f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(3.9167665f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(5.25f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(6.416767f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(7.343333f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(7.676667f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone92",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1.2916767f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.6766665f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(3.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(4.208343f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(4.75f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(5.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(6.167667f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(7.083433f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(7.375f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(7.416767f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(7.958343f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone94",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1.2916767f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(2.1676665f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.0416765f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(4.083433f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(4.958343f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(5.541677f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(6.041677f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(6.791677f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(7.25f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(7.791677f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone95",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.7916766f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1.0834333f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1.9167667f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(2.2083435f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.0834335f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(3.375f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(4.208343f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(4.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(5.416767f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(5.708343f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(5.958343f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(6.25f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(6.958343f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(7.25f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(7.375f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(7.676667f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone109",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4583433f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.9583434f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.4167667f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.6766667f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.0834335f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.3433335f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.7916765f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.2916765f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.75f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.416767f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.834333f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5.375f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5.791677f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6.041677f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6.5f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6.916767f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(7.167667f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(7.625f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(7.958343f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("bone116",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.4583433f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1.375f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(3.5834335f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(4.791677f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(5.834333f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(6.676667f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(6.958343f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(7.291677f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(7.583433f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(7.834333f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone126",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.125f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.625f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.9167667f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.4167665f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.6766665f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.2083435f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.7916765f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.291677f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.583433f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5.083433f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5.541677f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6.167667f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6.676667f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6.958343f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(7.458343f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(7.875f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("bone127",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(0f, 20f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 40f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.625f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.0834335f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.9583435f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.25f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.7083435f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.083433f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.416767f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.541677f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.916767f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5.625f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6.125f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6.708343f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(7.167667f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(7.458343f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(7.916767f, KeyframeAnimations.degreeVec(0f, 17.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("bone131",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -0.7f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 0f, 0.1f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, -0.3f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0.7f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.125f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.6766665f, KeyframeAnimations.posVec(0f, 0f, 0.6f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(3.2916765f, KeyframeAnimations.posVec(0f, 0f, -0.6f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.041677f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.5f, KeyframeAnimations.posVec(0f, 0f, -0.6f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(4.958343f, KeyframeAnimations.posVec(0f, 0f, 0.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(5.416767f, KeyframeAnimations.posVec(0f, 0f, -0.7f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, -0.3f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(6.583433f, KeyframeAnimations.posVec(0f, 0f, -0.6f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(7.167667f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(7.5f, KeyframeAnimations.posVec(0f, 0f, -0.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(7.676667f, KeyframeAnimations.posVec(0f, 0f, -0.2f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(8f, KeyframeAnimations.posVec(0f, 0f, -0.7f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();

    public static final AnimationDefinition HARTNELL_IDLE_ANIMATION = AnimationDefinition.Builder.withLength(8f).looping()
            .addAnimation("bone33",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.8343333f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone91",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.8343334f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(2.1676665f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.5416765f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(5.167667f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone93",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.0834335f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.0834335f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(4.083433f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(6.291677f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(7.958343f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone92",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1.4167667f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.3433335f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(2.625f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(4.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(5.875f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(7.083433f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(7.676667f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone94",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(6.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(7f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone95",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(2.2083435f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.75f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(4.083433f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(5.167667f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(6.583433f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(7.416767f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("m_sensor_1",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone109",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7083434f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.375f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5834333f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.25f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.4583435f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.6766665f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.2083435f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.4167665f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5834335f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.625f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.7916765f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.676667f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.875f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.083433f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.75f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.958343f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.167667f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.375f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.583433f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.708343f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.791677f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.916767f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(7.125f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(7.375f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(7.583433f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(7.791677f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone116",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(2.2083435f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(4.625f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(6.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone126",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4167667f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.625f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.208343f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.416767f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.583433f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.791677f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(7f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone127",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(0f, -8.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(0f, -27.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(0f, -7.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.2916765f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5834335f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5834335f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.8343335f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.125f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.834333f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.125f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.375f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.916767f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.167667f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.416767f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.5f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.75f, KeyframeAnimations.degreeVec(0f, -17.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(7f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone131",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4583433f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.0416765f, KeyframeAnimations.posVec(0f, 0f, 0.2f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.4583435f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.375f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.9583435f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.375f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4.708343f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.291677f, KeyframeAnimations.posVec(0f, 0f, -0.4f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(5.708343f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.041677f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.583433f, KeyframeAnimations.posVec(0f, 0f, 0.6f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.834333f, KeyframeAnimations.posVec(0f, 0f, 0.4f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(7.125f, KeyframeAnimations.posVec(0f, 0f, 0.6f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(7.5f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("compass",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(8f, KeyframeAnimations.degreeVec(0f, -180f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone166",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(1.3433333f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(5.343333f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone169",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(1.3433333f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(5.343333f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone167",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(1.3433333f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(2.6766665f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(5.343333f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(6.676667f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone170",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(1.3433333f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(2.6766665f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(5.343333f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(6.676667f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone168",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(2.6766665f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(6.676667f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone171",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(2.6766665f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP),
                            new Keyframe(6.676667f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP),
                            new Keyframe(8f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .build();

    public static final AnimationDefinition HARTNELL_CONTROL_HAILMARY_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone61",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("bone97",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_HAILMARY_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone61",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("bone97",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_DIMENSION_FIRST_ANIMATION = AnimationDefinition.Builder.withLength(1.75f)
            .addAnimation("bone86",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone87",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone88",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone89",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone90",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone62",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.375f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.6766667f, KeyframeAnimations.degreeVec(0f, 312.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.75f, KeyframeAnimations.degreeVec(0f, 360f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("bone63",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone65",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone64",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone80",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone66",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone81",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_DIMENSION_SECOND_ANIMATION = AnimationDefinition.Builder.withLength(1.75f)
            .addAnimation("bone86",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5416766f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone87",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone88",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone89",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone90",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone62",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(0f, 15f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.625f, KeyframeAnimations.degreeVec(0f, 312.5f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.75f, KeyframeAnimations.degreeVec(0f, 360f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("bone63",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone65",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone64",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone80",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone66",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5416766f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone81",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 70f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_RANDOMISER_ANIMATION = AnimationDefinition.Builder.withLength(1.75f)
            .addAnimation("bone85",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.8343334f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.9167666f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1.0834333f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1.1676667f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1.25f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1.3433333f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1.4167667f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1.5834333f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1.6766667f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1.75f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone79",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_DOORCONTROL_OPEN_ANIMATION = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation("bone117",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -2f, 0f), STEP)))
            .addAnimation("bone123",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_DOORCONTROL_CLOSE_ANIMATION = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation("bone117",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone123",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_DOORLOCK_UNLOCKED_ANIMATION = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation("bone118",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone125",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_DOORLOCK_LOCKED_ANIMATION = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation("bone118",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone125",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_LANDTYPE_ANIMATION = AnimationDefinition.Builder.withLength(1.25f)
            .addAnimation("bone129",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08343333f, KeyframeAnimations.posVec(0f, 0f, -0.15f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.20834334f, KeyframeAnimations.posVec(0f, 0f, -1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, KeyframeAnimations.posVec(-0.15f, 0f, -1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4167667f, KeyframeAnimations.posVec(-1f, 0f, -1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(-0.85f, 0f, -1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7083434f, KeyframeAnimations.posVec(0f, 0f, -1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.7916766f, KeyframeAnimations.posVec(0f, 0f, -1.25f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, -2f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.0834333f, KeyframeAnimations.posVec(0f, 0f, -1.58f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_FASTRETURN_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone25",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -0.25f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_XINC_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone70",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone82",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_YINC_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone76",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone83",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_ZINC_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone77",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone84",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_XYZINC_1_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone74",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 120f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_XYZINC_10_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone74",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 25f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_XYZINC_100_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone74",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 25f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 75f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_XYZINC_1000_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone74",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 75f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 120f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_THROTTLE_1_FIRST_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone45",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 52.5f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_THROTTLE_1_SECOND_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone45",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 52.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_AUTOPILOT_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone26",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(0f, 5.42f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 62.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone145",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_AUTOPILOT_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone26",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 62.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(0f, 57.08f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone145",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_HANDBRAKE_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone46",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 52.5f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_HANDBRAKE_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone46",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 52.5f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_ANTIGRAV_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.125f)
            .addAnimation("bone33",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_ANTIGRAV_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.125f)
            .addAnimation("bone33",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_REFUELER_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation("bone106",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_REFUELER_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation("bone106",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_DIRECTION_NORTH_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone59",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 27.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 117.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_DIRECTION_EAST_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone59",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 117.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 207.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_DIRECTION_SOUTH_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone59",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 207.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 297.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_DIRECTION_WEST_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone59",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 297.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 387.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH1_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone34",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH1_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone34",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH2_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone35",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH2_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone35",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH3_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone47",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH3_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone47",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_LEVER1_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone138",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone136",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone143",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_LEVER1_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone138",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone136",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone143",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 85f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_LEVER2_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone135",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone140",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone144",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_LEVER2_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone135",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone140",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone144",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 85f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_LEVER3_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone139",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone137",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone146",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_LEVER3_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone139",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone137",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone146",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 85f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_LEVER4_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone142",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 52.5f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_LEVER4_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation("bone142",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 52.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_CRANK1_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone147",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 55f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_CRANK1_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone147",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 55f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_CRANK2_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone148",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, -55f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_CRANK2_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone148",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -55f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH4_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.16766666f)
            .addAnimation("bone124",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH4_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.16766666f)
            .addAnimation("bone124",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH5_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.16766666f)
            .addAnimation("bone128",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH5_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.16766666f)
            .addAnimation("bone128",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_TOGGLESWITCH1_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.75f)
            .addAnimation("bone120",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone121",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone119",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_TOGGLESWITCH1_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.75f)
            .addAnimation("bone120",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone121",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone119",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_TOGGLESWITCH2_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.75f)
            .addAnimation("bone111",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone102",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone115",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone104",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone105",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_TOGGLESWITCH2_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.75f)
            .addAnimation("bone115",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone104",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone105",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone111",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone102",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_TURNSWITCH1_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation("bone101",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone108",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_TURNSWITCH1_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation("bone101",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone108",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_TURNSWITCH2_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation("bone103",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone107",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_TURNSWITCH2_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation("bone103",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone107",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 180f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_CRANK3_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone75",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 77.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_CRANK3_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone75",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 77.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_CRANK4_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone78",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 67.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_CRANK4_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone78",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 67.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_LEVER5_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation("bone71",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone96",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_LEVER5_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation("bone71",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone96",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH6_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone72",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH6_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone72",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH7_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone73",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH7_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone73",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_CRANK5_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone60",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 135f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_CRANK5_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone60",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 135f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH8_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone56",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH8_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone56",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH9_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone57",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH9_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone57",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH10_ON_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone58",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_CONTROL_SWITCH10_OFF_ANIMATION = AnimationDefinition.Builder.withLength(0.25f)
            .addAnimation("bone58",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static final AnimationDefinition HARTNELL_POWER_ON_ANIMATION = AnimationDefinition.Builder.withLength(9.291676f)
            .addAnimation("bone33",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone91",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(4.75f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone93",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(3.9583435f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone92",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(3.2083435f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone94",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(3.9583435f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone95",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(4.75f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("m_sensor_1",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone109",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone116",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(5.916767f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone126",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone127",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone131",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone166",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP)))
            .addAnimation("bone169",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f), STEP)))
            .addAnimation("bone167",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone170",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone168",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, -0.7f, 1f), STEP)))
            .addAnimation("bone171",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone86",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(9.083434f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone87",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(9.083434f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone88",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(9.083434f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone89",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(9.083434f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone90",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(9.083434f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone82",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(4.343333f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone83",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(5.125f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone84",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(5.916767f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone85",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(7.541677f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone111",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(6.291677f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone101",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(5.916767f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone102",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(6.676667f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone103",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(7.167667f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone117",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(5.5f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone118",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(6.958343f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone135",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.20834334f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone138",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.20834334f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone136",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.7916766f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone139",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(0.7916766f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone137",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1.5416767f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("bone140",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP),
                            new Keyframe(1.5416767f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP)))
            .addAnimation("rotor",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.6766665f, KeyframeAnimations.posVec(0f, -5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(6.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("light_3",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(9.291676f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    public static final AnimationDefinition HARTNELL_POWER_OFF_ANIMATION = AnimationDefinition.Builder.withLength(4f)
            .addAnimation("bone33",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone91",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.0416765f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone93",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1.7083433f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone92",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1.375f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone94",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1.7083433f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone95",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.0416765f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("m_sensor_1",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone109",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone116",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.5416765f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone126",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone127",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone131",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -0.75f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bone166",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone169",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, -1.3f, 1f), STEP)))
            .addAnimation("bone167",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone170",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone168",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, -0.7f, 1f), STEP)))
            .addAnimation("bone171",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0f, 1f), STEP)))
            .addAnimation("bone86",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.9167665f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone87",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.9167665f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone88",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.9167665f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone89",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.9167665f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone90",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.9167665f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone82",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(1.875f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone83",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.2083435f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone84",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.5416765f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone85",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.25f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone111",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.7083435f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone101",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.5416765f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone102",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.875f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone103",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3.0834335f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone117",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(2.375f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone118",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(3f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone135",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.08343333f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone138",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.08343333f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone136",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone139",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone137",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("bone140",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), STEP),
                            new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, -1f, 0f), STEP)))
            .addAnimation("rotor",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.375f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4f, KeyframeAnimations.posVec(0f, -5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("light_3",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();

    public static List<AnimationDefinition> listOfControlAnimations() {
        List<AnimationDefinition> animationList = new ArrayList<>();
        animationList.add(HARTNELL_CONTROL_ANTIGRAV_OFF_ANIMATION); // 0
        animationList.add(HARTNELL_CONTROL_ANTIGRAV_ON_ANIMATION); // 1
        animationList.add(HARTNELL_CONTROL_AUTOPILOT_OFF_ANIMATION); // 2
        animationList.add(HARTNELL_CONTROL_AUTOPILOT_ON_ANIMATION); // 3
        animationList.add(HARTNELL_CONTROL_CRANK1_OFF_ANIMATION); // 4
        animationList.add(HARTNELL_CONTROL_CRANK1_ON_ANIMATION); // 5
        animationList.add(HARTNELL_CONTROL_CRANK2_OFF_ANIMATION); // 6
        animationList.add(HARTNELL_CONTROL_CRANK2_ON_ANIMATION); // 7
        animationList.add(HARTNELL_CONTROL_CRANK3_OFF_ANIMATION); // 8
        animationList.add(HARTNELL_CONTROL_CRANK3_ON_ANIMATION); // 9
        animationList.add(HARTNELL_CONTROL_CRANK4_OFF_ANIMATION); // 10
        animationList.add(HARTNELL_CONTROL_CRANK4_ON_ANIMATION); // 11
        animationList.add(HARTNELL_CONTROL_CRANK5_OFF_ANIMATION); // 12
        animationList.add(HARTNELL_CONTROL_CRANK5_ON_ANIMATION); // 13
        animationList.add(HARTNELL_CONTROL_DIMENSION_FIRST_ANIMATION); // 14
        animationList.add(HARTNELL_CONTROL_DIMENSION_SECOND_ANIMATION); // 15
        animationList.add(HARTNELL_CONTROL_DIRECTION_NORTH_ANIMATION); // 16
        animationList.add(HARTNELL_CONTROL_DIRECTION_EAST_ANIMATION); // 17
        animationList.add(HARTNELL_CONTROL_DIRECTION_SOUTH_ANIMATION); // 18
        animationList.add(HARTNELL_CONTROL_DIRECTION_WEST_ANIMATION); // 19
        animationList.add(HARTNELL_CONTROL_DOORCONTROL_CLOSE_ANIMATION); // 20
        animationList.add(HARTNELL_CONTROL_DOORCONTROL_OPEN_ANIMATION); // 21
        animationList.add(HARTNELL_CONTROL_DOORLOCK_LOCKED_ANIMATION); // 22
        animationList.add(HARTNELL_CONTROL_DOORLOCK_UNLOCKED_ANIMATION); // 23
        animationList.add(HARTNELL_CONTROL_FASTRETURN_ANIMATION); // 24
        animationList.add(HARTNELL_CONTROL_HAILMARY_OFF_ANIMATION); // 25
        animationList.add(HARTNELL_CONTROL_HAILMARY_ON_ANIMATION); // 26
        animationList.add(HARTNELL_CONTROL_HANDBRAKE_OFF_ANIMATION); // 27
        animationList.add(HARTNELL_CONTROL_HANDBRAKE_ON_ANIMATION); // 28
        animationList.add(HARTNELL_CONTROL_LANDTYPE_ANIMATION); // 29
        animationList.add(HARTNELL_CONTROL_LEVER1_OFF_ANIMATION); // 30
        animationList.add(HARTNELL_CONTROL_LEVER1_ON_ANIMATION); // 31
        animationList.add(HARTNELL_CONTROL_LEVER2_OFF_ANIMATION); // 32
        animationList.add(HARTNELL_CONTROL_LEVER1_ON_ANIMATION); // 33
        animationList.add(HARTNELL_CONTROL_LEVER2_OFF_ANIMATION); // 34
        animationList.add(HARTNELL_CONTROL_LEVER2_ON_ANIMATION); // 35
        animationList.add(HARTNELL_CONTROL_LEVER3_OFF_ANIMATION); // 36
        animationList.add(HARTNELL_CONTROL_LEVER3_ON_ANIMATION); // 37
        animationList.add(HARTNELL_CONTROL_LEVER4_OFF_ANIMATION); // 38
        animationList.add(HARTNELL_CONTROL_LEVER4_ON_ANIMATION); // 39
        animationList.add(HARTNELL_CONTROL_RANDOMISER_ANIMATION); // 40
        animationList.add(HARTNELL_CONTROL_REFUELER_OFF_ANIMATION); // 41
        animationList.add(HARTNELL_CONTROL_REFUELER_ON_ANIMATION); // 42
        animationList.add(HARTNELL_CONTROL_SWITCH1_OFF_ANIMATION); // 43
        animationList.add(HARTNELL_CONTROL_SWITCH1_ON_ANIMATION); // 44
        animationList.add(HARTNELL_CONTROL_SWITCH2_OFF_ANIMATION); // 45
        animationList.add(HARTNELL_CONTROL_SWITCH3_OFF_ANIMATION); // 46
        animationList.add(HARTNELL_CONTROL_SWITCH3_ON_ANIMATION); // 47
        animationList.add(HARTNELL_CONTROL_SWITCH4_OFF_ANIMATION); // 48
        animationList.add(HARTNELL_CONTROL_SWITCH4_ON_ANIMATION); // 49
        animationList.add(HARTNELL_CONTROL_SWITCH5_OFF_ANIMATION); // 50
        animationList.add(HARTNELL_CONTROL_SWITCH5_ON_ANIMATION); // 51
        animationList.add(HARTNELL_CONTROL_SWITCH6_OFF_ANIMATION); // 52
        animationList.add(HARTNELL_CONTROL_SWITCH6_ON_ANIMATION); // 53
        animationList.add(HARTNELL_CONTROL_SWITCH7_OFF_ANIMATION); // 54
        animationList.add(HARTNELL_CONTROL_SWITCH7_ON_ANIMATION); // 55
        animationList.add(HARTNELL_CONTROL_SWITCH8_OFF_ANIMATION); // 56
        animationList.add(HARTNELL_CONTROL_SWITCH8_ON_ANIMATION); // 57
        animationList.add(HARTNELL_CONTROL_SWITCH9_OFF_ANIMATION); // 58
        animationList.add(HARTNELL_CONTROL_SWITCH9_ON_ANIMATION); // 59
        animationList.add(HARTNELL_CONTROL_SWITCH10_OFF_ANIMATION); // 60
        animationList.add(HARTNELL_CONTROL_SWITCH10_ON_ANIMATION); // 61
        animationList.add(HARTNELL_CONTROL_THROTTLE_1_FIRST_ANIMATION); // 62
        animationList.add(HARTNELL_CONTROL_THROTTLE_1_SECOND_ANIMATION); // 63
        animationList.add(HARTNELL_CONTROL_TOGGLESWITCH1_OFF_ANIMATION); // 64
        animationList.add(HARTNELL_CONTROL_TOGGLESWITCH1_ON_ANIMATION); // 65
        animationList.add(HARTNELL_CONTROL_TOGGLESWITCH2_OFF_ANIMATION); // 66
        animationList.add(HARTNELL_CONTROL_TOGGLESWITCH2_ON_ANIMATION); // 67
        animationList.add(HARTNELL_CONTROL_TURNSWITCH1_OFF_ANIMATION); // 68
        animationList.add(HARTNELL_CONTROL_TURNSWITCH1_ON_ANIMATION); // 69
        animationList.add(HARTNELL_CONTROL_TURNSWITCH2_OFF_ANIMATION); // 70
        animationList.add(HARTNELL_CONTROL_TURNSWITCH2_ON_ANIMATION); // 71
        animationList.add(HARTNELL_CONTROL_XYZINC_1_ANIMATION); // 72
        animationList.add(HARTNELL_CONTROL_XYZINC_10_ANIMATION); // 73
        animationList.add(HARTNELL_CONTROL_XYZINC_100_ANIMATION); // 74
        animationList.add(HARTNELL_CONTROL_XYZINC_1000_ANIMATION); // 75
        animationList.add(HARTNELL_CONTROL_XINC_ANIMATION); // 76
        animationList.add(HARTNELL_CONTROL_YINC_ANIMATION); // 77
        animationList.add(HARTNELL_CONTROL_ZINC_ANIMATION); // 78
        return animationList;
    }

    /*
     * public static HashMap<Integer, ControlAnimationState>
     * animationStatePerControl(List<Animation> animList) { HashMap<Integer,
     * ControlAnimationState> map = new HashMap<>(); for(int i = 0; i <
     * animList.size(); i++) { Animation animation = animList.get(i);
     * ControlAnimationState animationState = new ControlAnimationState(animation);
     * map.put(i, animationState); } return map; }
     *
     * public static int animationOnIdFromControlName(String id) { return switch(id)
     * { default -> 62; case "randomiser" -> 40;
     */
    /* case "refueler" -> 41; */
    /*
     */
    /* case "handbrake" -> 28; */
    /*
     */
    /* case "increment" -> 75; */
    /*
     * case "x" -> 76; case "y" -> 77; case "z" -> 78; }; }
     *
     * public static int animationFromDirection(TardisTravel travel) { return
     * switch(travel.getDestination().getDirection()) { default -> 16; case EAST ->
     * 17; case SOUTH -> 18; case WEST -> 19; }; }
     */
}
