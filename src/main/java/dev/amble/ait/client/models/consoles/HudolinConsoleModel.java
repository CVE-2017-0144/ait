package dev.amble.ait.client.models.consoles;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.animation.console.hudolin.HudolinAnimations;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.tardis.control.impl.pos.IncrementManager;
import dev.amble.ait.core.tardis.handler.CloakHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;

public class HudolinConsoleModel extends SimpleConsoleModel {
    public final ModelPart console;
    public final ModelPart pannel4;
    public final ModelPart fix;
    public final ModelPart control;
    public final ModelPart fix2;
    public final ModelPart control2;
    public final ModelPart fix3;
    public final ModelPart control3;
    public final ModelPart fix4;
    public final ModelPart control4;
    public final ModelPart fix5;
    public final ModelPart bone4;
    public final ModelPart fix12;
    public final ModelPart bone10;
    public final ModelPart fix7;
    public final ModelPart bone5;
    public final ModelPart fix8;
    public final ModelPart bone6;
    public final ModelPart fix9;
    public final ModelPart bone7;
    public final ModelPart fix10;
    public final ModelPart bone8;
    public final ModelPart fix11;
    public final ModelPart bone9;
    public final ModelPart spin2;
    public final ModelPart bone2;
    public final ModelPart lighton13;
    public final ModelPart off18;
    public final ModelPart lighton15;
    public final ModelPart off20;
    public final ModelPart lighton16;
    public final ModelPart off21;
    public final ModelPart lighton17;
    public final ModelPart off22;
    public final ModelPart lighton14;
    public final ModelPart off19;
    public final ModelPart lighton9;
    public final ModelPart off14;
    public final ModelPart lighton10;
    public final ModelPart off15;
    public final ModelPart lightson;
    public final ModelPart off13;
    public final ModelPart lighton11;
    public final ModelPart off16;
    public final ModelPart lighton12;
    public final ModelPart off17;
    public final ModelPart pannel3;
    public final ModelPart dematlever;
    public final ModelPart dematleveryay;
    public final ModelPart dial;
    public final ModelPart dialfix;
    public final ModelPart spin;
    public final ModelPart lighton4;
    public final ModelPart off5;
    public final ModelPart lighton5;
    public final ModelPart off6;
    public final ModelPart lighton6;
    public final ModelPart off7;
    public final ModelPart brightlighton3;
    public final ModelPart off10;
    public final ModelPart brightlighton2;
    public final ModelPart off9;
    public final ModelPart brightlighton;
    public final ModelPart off8;
    public final ModelPart pannel5;
    public final ModelPart fix13;
    public final ModelPart spin3;
    public final ModelPart fix14;
    public final ModelPart spin4;
    public final ModelPart fix16;
    public final ModelPart spin6;
    public final ModelPart fix17;
    public final ModelPart spin7;
    public final ModelPart fix15;
    public final ModelPart spin5;
    public final ModelPart blinkingon;
    public final ModelPart blinkingoff;
    public final ModelPart lighton7;
    public final ModelPart off11;
    public final ModelPart lighton8;
    public final ModelPart off12;
    public final ModelPart lighton18;
    public final ModelPart off23;
    public final ModelPart thing;
    public final ModelPart thing2;
    public final ModelPart bone11;
    public final ModelPart thing3;
    public final ModelPart fix18;
    public final ModelPart dial2;
    public final ModelPart fix19;
    public final ModelPart dial3;
    public final ModelPart fix20;
    public final ModelPart dial4;
    public final ModelPart fix22;
    public final ModelPart dial6;
    public final ModelPart fix21;
    public final ModelPart dial5;
    public final ModelPart dividers;
    public final ModelPart slide2;
    public final ModelPart slide;
    public final ModelPart fix6;
    public final ModelPart handbreak;
    public final ModelPart flightlighton;
    public final ModelPart off;
    public final ModelPart fix30;
    public final ModelPart thing10;
    public final ModelPart thing9;
    public final ModelPart thing8;
    public final ModelPart thing7;
    public final ModelPart thing6;
    public final ModelPart fix29;
    public final ModelPart thing5;
    public final ModelPart fix28;
    public final ModelPart thing4;
    public final ModelPart fix24;
    public final ModelPart dial7;
    public final ModelPart fix25;
    public final ModelPart bone3;
    public final ModelPart fix26;
    public final ModelPart bone12;
    public final ModelPart fix27;
    public final ModelPart bone13;
    public final ModelPart fix23;
    public final ModelPart spin8;
    public final ModelPart fix31;
    public final ModelPart dial8;
    public final ModelPart fix32;
    public final ModelPart dial9;
    public final ModelPart clock;
    public final ModelPart minute;
    public final ModelPart hour;
    public final ModelPart rotor;
    public final ModelPart bottom;
    public final ModelPart top2;
    public final ModelPart nature;
    public final ModelPart inside;
    public final ModelPart underdividers;
    public final ModelPart top;
    public final ModelPart bone;
    public final ModelPart flick4;
    public final ModelPart flick2;
    public final ModelPart flick3;
    public final ModelPart flick5;
    public final ModelPart lighton3;
    public final ModelPart off4;
    public final ModelPart lighton2;
    public final ModelPart off3;
    public final ModelPart lighton;
    public final ModelPart off2;
    public final ModelPart bone14;
    public final ModelPart bone15;
    public final ModelPart toolbox;
    public final ModelPart lid;
    public final ModelPart lid2;
    public final ModelPart hammer;
    public final ModelPart bone17;
    public final ModelPart bone16;
    public HudolinConsoleModel(ModelPart root) {
        this.console = root.getChild("console");
        this.pannel4 = this.console.getChild("pannel4");
        this.fix = this.pannel4.getChild("fix");
        this.control = this.fix.getChild("control");
        this.fix2 = this.pannel4.getChild("fix2");
        this.control2 = this.fix2.getChild("control2");
        this.fix3 = this.pannel4.getChild("fix3");
        this.control3 = this.fix3.getChild("control3");
        this.fix4 = this.pannel4.getChild("fix4");
        this.control4 = this.fix4.getChild("control4");
        this.fix5 = this.pannel4.getChild("fix5");
        this.bone4 = this.fix5.getChild("bone4");
        this.fix12 = this.pannel4.getChild("fix12");
        this.bone10 = this.fix12.getChild("bone10");
        this.fix7 = this.pannel4.getChild("fix7");
        this.bone5 = this.fix7.getChild("bone5");
        this.fix8 = this.pannel4.getChild("fix8");
        this.bone6 = this.fix8.getChild("bone6");
        this.fix9 = this.pannel4.getChild("fix9");
        this.bone7 = this.fix9.getChild("bone7");
        this.fix10 = this.pannel4.getChild("fix10");
        this.bone8 = this.fix10.getChild("bone8");
        this.fix11 = this.pannel4.getChild("fix11");
        this.bone9 = this.fix11.getChild("bone9");
        this.spin2 = this.pannel4.getChild("spin2");
        this.bone2 = this.spin2.getChild("bone2");
        this.lighton13 = this.console.getChild("lighton13");
        this.off18 = this.lighton13.getChild("off18");
        this.lighton15 = this.console.getChild("lighton15");
        this.off20 = this.lighton15.getChild("off20");
        this.lighton16 = this.console.getChild("lighton16");
        this.off21 = this.lighton16.getChild("off21");
        this.lighton17 = this.console.getChild("lighton17");
        this.off22 = this.lighton17.getChild("off22");
        this.lighton14 = this.console.getChild("lighton14");
        this.off19 = this.lighton14.getChild("off19");
        this.lighton9 = this.console.getChild("lighton9");
        this.off14 = this.lighton9.getChild("off14");
        this.lighton10 = this.console.getChild("lighton10");
        this.off15 = this.lighton10.getChild("off15");
        this.lightson = this.console.getChild("lightson");
        this.off13 = this.lightson.getChild("off13");
        this.lighton11 = this.console.getChild("lighton11");
        this.off16 = this.lighton11.getChild("off16");
        this.lighton12 = this.console.getChild("lighton12");
        this.off17 = this.lighton12.getChild("off17");
        this.pannel3 = this.console.getChild("pannel3");
        this.dematlever = this.pannel3.getChild("dematlever");
        this.dematleveryay = this.dematlever.getChild("dematleveryay");
        this.dial = this.pannel3.getChild("dial");
        this.dialfix = this.dial.getChild("dialfix");
        this.spin = this.pannel3.getChild("spin");
        this.lighton4 = this.console.getChild("lighton4");
        this.off5 = this.lighton4.getChild("off5");
        this.lighton5 = this.console.getChild("lighton5");
        this.off6 = this.lighton5.getChild("off6");
        this.lighton6 = this.console.getChild("lighton6");
        this.off7 = this.lighton6.getChild("off7");
        this.brightlighton3 = this.console.getChild("brightlighton3");
        this.off10 = this.brightlighton3.getChild("off10");
        this.brightlighton2 = this.console.getChild("brightlighton2");
        this.off9 = this.brightlighton2.getChild("off9");
        this.brightlighton = this.console.getChild("brightlighton");
        this.off8 = this.brightlighton.getChild("off8");
        this.pannel5 = this.console.getChild("pannel5");
        this.fix13 = this.pannel5.getChild("fix13");
        this.spin3 = this.fix13.getChild("spin3");
        this.fix14 = this.pannel5.getChild("fix14");
        this.spin4 = this.fix14.getChild("spin4");
        this.fix16 = this.pannel5.getChild("fix16");
        this.spin6 = this.fix16.getChild("spin6");
        this.fix17 = this.pannel5.getChild("fix17");
        this.spin7 = this.fix17.getChild("spin7");
        this.fix15 = this.pannel5.getChild("fix15");
        this.spin5 = this.fix15.getChild("spin5");
        this.blinkingon = this.console.getChild("blinkingon");
        this.blinkingoff = this.blinkingon.getChild("blinkingoff");
        this.lighton7 = this.console.getChild("lighton7");
        this.off11 = this.lighton7.getChild("off11");
        this.lighton8 = this.console.getChild("lighton8");
        this.off12 = this.lighton8.getChild("off12");
        this.lighton18 = this.console.getChild("lighton18");
        this.off23 = this.lighton18.getChild("off23");
        this.thing = this.console.getChild("thing");
        this.thing2 = this.console.getChild("thing2");
        this.bone11 = this.console.getChild("bone11");
        this.thing3 = this.console.getChild("thing3");
        this.fix18 = this.console.getChild("fix18");
        this.dial2 = this.fix18.getChild("dial2");
        this.fix19 = this.console.getChild("fix19");
        this.dial3 = this.fix19.getChild("dial3");
        this.fix20 = this.console.getChild("fix20");
        this.dial4 = this.fix20.getChild("dial4");
        this.fix22 = this.console.getChild("fix22");
        this.dial6 = this.fix22.getChild("dial6");
        this.fix21 = this.console.getChild("fix21");
        this.dial5 = this.fix21.getChild("dial5");
        this.dividers = this.console.getChild("dividers");
        this.slide2 = this.console.getChild("slide2");
        this.slide = this.console.getChild("slide");
        this.fix6 = this.console.getChild("fix6");
        this.handbreak = this.fix6.getChild("handbreak");
        this.flightlighton = this.console.getChild("flightlighton");
        this.off = this.flightlighton.getChild("off");
        this.fix30 = this.console.getChild("fix30");
        this.thing10 = this.fix30.getChild("thing10");
        this.thing9 = this.fix30.getChild("thing9");
        this.thing8 = this.fix30.getChild("thing8");
        this.thing7 = this.fix30.getChild("thing7");
        this.thing6 = this.fix30.getChild("thing6");
        this.fix29 = this.console.getChild("fix29");
        this.thing5 = this.fix29.getChild("thing5");
        this.fix28 = this.console.getChild("fix28");
        this.thing4 = this.fix28.getChild("thing4");
        this.fix24 = this.console.getChild("fix24");
        this.dial7 = this.fix24.getChild("dial7");
        this.fix25 = this.console.getChild("fix25");
        this.bone3 = this.fix25.getChild("bone3");
        this.fix26 = this.console.getChild("fix26");
        this.bone12 = this.fix26.getChild("bone12");
        this.fix27 = this.console.getChild("fix27");
        this.bone13 = this.fix27.getChild("bone13");
        this.fix23 = this.console.getChild("fix23");
        this.spin8 = this.fix23.getChild("spin8");
        this.fix31 = this.console.getChild("fix31");
        this.dial8 = this.fix31.getChild("dial8");
        this.fix32 = this.console.getChild("fix32");
        this.dial9 = this.fix32.getChild("dial9");
        this.clock = this.console.getChild("clock");
        this.minute = this.clock.getChild("minute");
        this.hour = this.clock.getChild("hour");
        this.rotor = this.console.getChild("rotor");
        this.bottom = this.rotor.getChild("bottom");
        this.top2 = this.rotor.getChild("top2");
        this.nature = this.console.getChild("nature");
        this.inside = this.nature.getChild("inside");
        this.underdividers = this.console.getChild("underdividers");
        this.top = this.console.getChild("top");
        this.bone = this.console.getChild("bone");
        this.flick4 = this.bone.getChild("flick4");
        this.flick2 = this.bone.getChild("flick2");
        this.flick3 = this.bone.getChild("flick3");
        this.flick5 = this.bone.getChild("flick5");
        this.lighton3 = this.bone.getChild("lighton3");
        this.off4 = this.lighton3.getChild("off4");
        this.lighton2 = this.bone.getChild("lighton2");
        this.off3 = this.lighton2.getChild("off3");
        this.lighton = this.bone.getChild("lighton");
        this.off2 = this.lighton.getChild("off2");
        this.bone14 = this.console.getChild("bone14");
        this.bone15 = this.bone14.getChild("bone15");
        this.toolbox = root.getChild("toolbox");
        this.lid = this.toolbox.getChild("lid");
        this.lid2 = this.toolbox.getChild("lid2");
        this.hammer = this.toolbox.getChild("hammer");
        this.bone17 = this.hammer.getChild("bone17");
        this.bone16 = this.bone17.getChild("bone16");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition console = modelPartData.addOrReplaceChild("console", CubeListBuilder.create().texOffs(21, 29).addBox(-11.0F, -13.0F, -19.0526F, 22.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(36, 44).addBox(1.0F, -13.5F, -18.0526F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(26, 26).addBox(1.0F, -15.0F, -16.0526F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(26, 26).addBox(1.0F, -15.0F, -17.0526F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(26, 26).addBox(1.0F, -15.0F, -18.0526F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(26, 26).addBox(5.0F, -15.0F, -18.0526F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(26, 26).addBox(5.0F, -15.0F, -17.0526F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(26, 26).addBox(5.0F, -15.0F, -16.0526F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(61, 49).addBox(-10.5F, -12.1F, -18.1865F, 21.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -19.6311F, -5.1962F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -19.6311F, 4.1962F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-3.0F, -1.6311F, -5.1962F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(4, 30).addBox(-3.5F, -0.6311F, -6.0622F, 7.0F, 1.0F, 5.0F, new CubeDeformation(0.001F))
                .texOffs(4, 30).addBox(-3.5F, -0.6311F, 1.0622F, 7.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(50, 0).addBox(-3.0F, -13.1311F, -5.1962F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-2.5F, -21.2311F, -4.5278F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.27F))
                .texOffs(0, 3).addBox(-2.5F, -3.2311F, -4.5278F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.27F))
                .texOffs(0, 40).addBox(-2.5F, -46.5311F, -4.3301F, 5.0F, 25.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).mirror().addBox(-2.5F, -9.3311F, -4.3301F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(69, 57).mirror().addBox(-2.0F, -9.3311F, -3.4641F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(69, 57).mirror().addBox(-2.0F, -9.3311F, 2.4641F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 23.6F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r1 = console.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(45, 2).addBox(-6.0F, -3.7926F, -12.188F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(25, 40).addBox(2.0F, -3.7926F, -12.188F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(29, 68).addBox(-2.5F, -3.4926F, -8.988F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(29, 68).addBox(-3.0F, -3.4926F, -8.988F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(29, 68).addBox(0.5F, -3.4926F, -8.988F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 72).addBox(-0.5F, -3.4926F, -9.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 72).addBox(0.0F, -3.4926F, -8.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 72).addBox(-3.5F, -3.4926F, -8.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 72).addBox(-3.0F, -3.4926F, -9.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 72).addBox(-0.5F, -3.4926F, -6.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 72).addBox(-3.0F, -3.4926F, -6.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(-1.5F, -3.4926F, -3.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(-0.5F, -3.4926F, -3.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(0.0F, -3.4926F, -4.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(-2.0F, -3.4926F, -4.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(-2.5F, -3.4926F, -9.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(0.5F, -3.4926F, -9.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(0.0F, -3.4926F, -9.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 71).addBox(-2.0F, -3.4926F, -6.488F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(34, 71).addBox(-2.0F, -3.4926F, -9.988F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(34, 73).addBox(-1.5F, -3.4926F, -3.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(34, 73).addBox(-2.0F, -3.4926F, -4.488F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(33, 70).addBox(-2.0F, -3.4926F, -5.988F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(33, 70).addBox(-2.0F, -3.4926F, -9.488F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(-2.0F, -3.4926F, -9.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(-2.0F, -3.4926F, -9.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 70).addBox(0.0F, -3.4926F, -9.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(-2.0F, -3.4926F, -6.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(0.0F, -3.4926F, -6.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(0.5F, -3.4926F, -6.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 70).addBox(-2.5F, -3.4926F, -6.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 70).addBox(0.0F, -3.4926F, -6.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 70).addBox(-2.0F, -3.4926F, -6.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 70).addBox(-2.5F, -3.4926F, -6.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 71).addBox(-3.5F, -3.4926F, -6.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 71).addBox(-2.5F, -3.4926F, -3.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 71).addBox(-1.0F, -3.4926F, -3.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 71).addBox(0.0F, -3.4926F, -6.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 71).addBox(-0.5F, -3.4926F, -6.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 71).addBox(-3.0F, -3.4926F, -6.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 71).addBox(-3.0F, -3.4926F, -8.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 71).addBox(-0.5F, -3.4926F, -8.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(29, 68).addBox(1.0F, -3.4926F, -8.988F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(9, 17).addBox(-3.0F, -3.1926F, -4.488F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 48).addBox(-3.0F, -3.1926F, -10.488F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(49, 48).addBox(-3.0F, -3.7926F, -12.988F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 59).addBox(-9.0F, -3.4926F, -13.488F, 16.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1962F, -17.8746F, 0.4641F, 0.6109F, -1.0472F, 0.0F));

        PartDefinition cube_r2 = console.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(35, 50).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5169F, -17.6737F, -6.5921F, 0.7117F, -1.1162F, -0.1141F));

        PartDefinition cube_r3 = console.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(43, 48).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.934F, -15.8082F, -4.4725F, 2.3855F, -0.422F, -2.4617F));

        PartDefinition cube_r4 = console.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(37, 48).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.841F, -15.8077F, -4.6352F, -0.2534F, -1.1339F, 0.9583F));

        PartDefinition cube_r5 = console.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(31, 48).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.9538F, -15.7539F, -4.5933F, 2.9671F, 1.0472F, 3.1416F));

        PartDefinition cube_r6 = console.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(31, 48).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.8209F, -15.8614F, -4.5165F, 1.0472F, -1.0472F, 0.0F));

        PartDefinition cube_r7 = console.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(22, 38).addBox(2.5F, -0.6F, -0.8F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.6201F, -13.9415F, -6.9744F, 0.6109F, -1.0472F, 0.0F));

        PartDefinition cube_r8 = console.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(22, 38).addBox(1.5F, -0.6F, -0.8F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 38).addBox(0.5F, -0.6F, -0.8F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.6101F, -13.9415F, -6.9917F, 0.6109F, -1.0472F, 0.0F));

        PartDefinition cube_r9 = console.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(22, 38).addBox(-0.5F, -0.6F, -0.8F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.6106F, -13.9415F, -6.9909F, 0.6109F, -1.0472F, 0.0F));

        PartDefinition cube_r10 = console.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-2.5F, -4.5F, -4.3301F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 40).addBox(-2.5F, -41.5F, -4.3301F, 5.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0311F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r11 = console.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-2.5F, -4.5F, -4.3301F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 40).addBox(-2.5F, -41.5F, -4.3301F, 5.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0311F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r12 = console.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 7).addBox(-2.5F, -4.5F, -3.6699F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 40).mirror().addBox(-2.5F, -41.5F, -3.6699F, 5.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -5.0311F, 0.6603F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r13 = console.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-2.5F, -4.5F, -4.3301F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 40).addBox(-2.5F, -41.5F, -4.3301F, 5.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0311F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r14 = console.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-2.5F, -4.5F, -4.3301F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 40).addBox(-2.5F, -41.5F, -4.3301F, 5.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0311F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r15 = console.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(69, 57).addBox(-2.0F, -3.0F, -0.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.567F, -6.3311F, 1.4821F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r16 = console.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(69, 57).addBox(-2.0F, -3.0F, -0.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.567F, -6.3311F, 1.4821F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r17 = console.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(69, 57).addBox(-2.0F, -3.0F, -0.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.567F, -6.3311F, -1.4821F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r18 = console.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(69, 57).mirror().addBox(-2.0F, -3.0F, -0.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.567F, -6.3311F, -1.4821F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r19 = console.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(17, 0).addBox(0.6314F, -1.6314F, -5.0301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, -5.0301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 0).mirror().addBox(-1.6314F, 0.6314F, -5.0301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.5311F, 0.0F, 2.2555F, 0.6591F, 2.0344F));

        PartDefinition cube_r20 = console.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(17, 0).mirror().addBox(-1.6314F, -1.6314F, 4.0301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 0).addBox(-0.5F, -0.5F, 4.0301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 0).addBox(0.6314F, 0.6314F, 4.0301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5311F, 0.0F, -2.2555F, 0.6591F, -2.0344F));

        PartDefinition cube_r21 = console.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(17, 0).addBox(0.6314F, -1.6314F, -5.0301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, -5.0301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 0).mirror().addBox(-1.6314F, 0.6314F, -5.0301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.5311F, 0.0F, -2.2555F, -0.6591F, 2.0344F));

        PartDefinition cube_r22 = console.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(17, 0).mirror().addBox(-1.6314F, -1.6314F, 4.0301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 0).addBox(-0.5F, -0.5F, 4.0301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 0).addBox(0.6314F, 0.6314F, 4.0301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5311F, 0.0F, 2.2555F, -0.6591F, -2.0344F));

        PartDefinition cube_r23 = console.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.6F, -2.5311F, 4.5301F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r24 = console.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(17, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5311F, 4.5301F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r25 = console.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(17, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6F, -2.5311F, 4.5301F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r26 = console.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(17, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6F, -2.5311F, -4.5301F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r27 = console.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.5311F, -4.5301F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r28 = console.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.6F, -2.5311F, -4.5301F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r29 = console.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-2.5F, -1.5F, -4.3301F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.27F)).mirror(false), PartPose.offsetAndRotation(-0.1712F, -1.7311F, -0.0988F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r30 = console.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(0, 3).addBox(-2.5F, -1.5F, -4.3301F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.27F))
                .texOffs(0, 3).addBox(-2.5F, -19.5F, -4.3301F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.27F)), PartPose.offsetAndRotation(0.1712F, -1.7311F, 0.0988F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r31 = console.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-2.5F, -1.5F, -4.3301F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.27F)).mirror(false)
                .texOffs(0, 3).mirror().addBox(-2.5F, -19.5F, -4.3301F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.27F)).mirror(false), PartPose.offsetAndRotation(-0.1712F, -1.7311F, 0.0988F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r32 = console.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(0, 3).addBox(-2.5F, -1.5F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.27F)), PartPose.offsetAndRotation(0.0F, -1.7311F, 4.0278F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r33 = console.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(0, 3).addBox(-2.5F, -1.5F, -4.3301F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.27F)), PartPose.offsetAndRotation(0.1712F, -1.7311F, -0.0988F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r34 = console.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-1.628F, -1.5F, -5.0313F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.271F)).mirror(false), PartPose.offsetAndRotation(0.0F, -19.7311F, 1.007F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r35 = console.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(0, 3).addBox(-2.5F, -1.5F, -3.5208F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.271F)), PartPose.offsetAndRotation(0.0F, -19.7311F, 1.007F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r36 = console.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(76, 5).addBox(-5.372F, -1.7F, -5.0313F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8901F, -19.7311F, 1.5208F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r37 = console.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(0, 3).addBox(-3.372F, -1.5F, -5.0313F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.271F)), PartPose.offsetAndRotation(0.0F, -19.7311F, 1.007F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r38 = console.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(17, 0).addBox(0.7728F, -1.7728F, -5.1301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 0).addBox(0.7728F, -1.7728F, 4.1301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.5311F, 0.0F, -2.2555F, -0.6591F, 2.0344F));

        PartDefinition cube_r39 = console.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, -5.1301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 0).mirror().addBox(-1.7728F, -1.7728F, -5.1301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 0).mirror().addBox(-1.7728F, -1.7728F, 4.1301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, 4.1301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -20.5311F, 0.0F, 2.2555F, -0.6591F, -2.0344F));

        PartDefinition cube_r40 = console.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(17, 0).addBox(0.7728F, -1.7728F, -5.1301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 0).addBox(0.7728F, -1.7728F, 4.1301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.5311F, 0.0F, -0.8861F, -0.6591F, 1.1071F));

        PartDefinition cube_r41 = console.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, -5.1301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 0).mirror().addBox(-1.7728F, -1.7728F, -5.1301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 0).mirror().addBox(-1.7728F, -1.7728F, 4.1301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, 4.1301F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -20.5311F, 0.0F, 0.8861F, -0.6591F, -1.1071F));

        PartDefinition cube_r42 = console.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, -9.7603F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.8F, -20.5311F, 4.6301F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r43 = console.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 0).mirror().addBox(-0.5F, -0.5F, -9.7603F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -20.5311F, 4.6301F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r44 = console.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(17, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 0).addBox(-0.5F, -0.5F, -9.7603F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8F, -20.5311F, 4.6301F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r45 = console.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(50, 0).mirror().addBox(-3.0F, -2.0F, -5.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 23).mirror().addBox(-3.0F, 9.5F, -5.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(-3.0F, -8.5F, -5.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.2631F, -11.1311F, 0.1519F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r46 = console.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(50, 0).addBox(-3.0F, -2.0F, -5.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-3.0F, 9.5F, -5.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -8.5F, -5.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2631F, -11.1311F, 0.1519F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r47 = console.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(50, 0).addBox(-3.0F, -2.0F, -5.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-3.0F, 9.5F, -5.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -8.5F, -5.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2631F, -11.1311F, -0.1519F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r48 = console.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(50, 0).mirror().addBox(-3.0F, -2.0F, -5.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 23).mirror().addBox(-3.0F, 9.5F, -5.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(-3.0F, -8.5F, -5.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.2631F, -11.1311F, -0.1519F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r49 = console.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(50, 0).addBox(-3.0F, -2.0F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-3.0F, 9.5F, -2.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.1311F, 2.6961F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r50 = console.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(4, 30).addBox(-4.2712F, -0.5F, -6.5075F, 7.0F, 1.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -0.1311F, -0.8905F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r51 = console.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(4, 30).addBox(-4.0F, 1.0F, -5.5F, 7.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7369F, -1.6311F, 0.1519F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r52 = console.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(4, 30).addBox(-2.7288F, -0.5F, -6.5075F, 7.0F, 1.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -0.1311F, -0.8905F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r53 = console.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(4, 30).mirror().addBox(-4.0F, 1.0F, -5.5F, 7.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.2369F, -1.6311F, -0.7141F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r54 = console.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(79, 29).addBox(-2.0F, -2.9926F, -5.488F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(79, 33).addBox(-2.0F, -3.4926F, -5.488F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(79, 31).addBox(-2.0F, -3.4926F, -3.488F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(79, 30).addBox(-2.0F, -3.4926F, -5.488F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(79, 30).addBox(0.0F, -3.4926F, -5.488F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(79, 28).addBox(-4.0F, -2.9926F, -5.488F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(79, 30).addBox(-4.0F, -3.4926F, -5.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(79, 29).addBox(-4.0F, -3.4926F, -4.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(78, 27).addBox(-4.0F, -3.4926F, -5.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(78, 27).addBox(-3.0F, -3.4926F, -5.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(79, 28).addBox(1.0F, -2.9926F, -5.488F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(79, 30).addBox(1.0F, -3.4926F, -5.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(79, 29).addBox(1.0F, -3.4926F, -4.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(78, 27).addBox(1.0F, -3.4926F, -5.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(78, 27).addBox(2.0F, -3.4926F, -5.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(78, 31).addBox(1.0F, -3.1926F, -10.488F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(29, 35).addBox(-4.5F, -3.6926F, -7.488F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 39).addBox(-4.5F, -3.6926F, -7.988F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(23, 29).addBox(-3.0F, -3.6926F, -7.488F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 36).addBox(-3.0F, -3.6926F, -7.988F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(23, 29).addBox(-3.0F, -3.6926F, -9.988F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(23, 32).addBox(-3.0F, -3.6926F, -10.488F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(50, 13).addBox(-7.0F, -3.6926F, -12.988F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(50, 10).addBox(-7.0F, -3.6926F, -13.488F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(57, 17).addBox(-9.0F, -3.4926F, -13.488F, 16.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -17.8746F, -0.8038F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r55 = console.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(49, 83).addBox(2.6F, -0.6F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(49, 83).addBox(1.2F, -0.6F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(49, 83).addBox(1.2F, -0.6F, 0.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(49, 83).addBox(-1.0F, -0.6F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(49, 83).addBox(-1.0F, -0.6F, 0.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-10.8429F, -14.4987F, -3.9507F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition cube_r56 = console.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(43, 70).addBox(-4.0F, -3.6926F, -12.588F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(57, 25).addBox(0.0F, -3.9926F, -6.088F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(57, 25).addBox(-4.0F, -3.9926F, -6.088F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(53, 61).addBox(3.0F, -3.6926F, -13.488F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(53, 61).addBox(-7.0F, -3.6926F, -13.488F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(74, 47).addBox(-1.8F, -3.2926F, -7.788F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 6).addBox(-9.0F, -3.4926F, -13.488F, 16.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1961F, -17.8746F, -1.2679F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition cube_r57 = console.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(54, 21).mirror().addBox(-4.0F, -3.7926F, -7.488F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(54, 21).addBox(0.0F, -3.7926F, -7.488F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(55, 18).addBox(-3.7F, -4.1926F, -9.588F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(55, 18).addBox(-2.0F, -4.1926F, -9.588F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(55, 18).addBox(-2.0F, -4.1926F, -10.988F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(55, 18).addBox(-3.7F, -4.1926F, -10.988F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(67, 0).addBox(-4.0F, -3.8926F, -12.488F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(23, 85).addBox(1.0F, -3.4926F, -3.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 85).addBox(0.0F, -3.4926F, -5.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 85).addBox(-2.0F, -3.4926F, -5.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 85).addBox(-3.0F, -3.4926F, -5.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 85).addBox(1.0F, -3.4926F, -5.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 85).addBox(0.0F, -3.4926F, -3.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 85).addBox(-2.0F, -3.4926F, -3.488F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 85).addBox(-3.0F, -3.4926F, -3.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 86).addBox(-4.0F, -3.4926F, -3.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(23, 86).addBox(-4.0F, -3.4926F, -4.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(23, 86).addBox(0.5F, -3.4926F, -4.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(23, 86).addBox(0.5F, -3.4926F, -3.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(23, 84).addBox(1.5F, -3.4926F, -4.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 84).addBox(-3.5F, -3.4926F, -4.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 85).addBox(-3.0F, -3.4926F, -5.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 85).addBox(-3.0F, -3.4926F, -3.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 85).addBox(0.0F, -3.4926F, -3.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 85).addBox(0.0F, -3.4926F, -5.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(23, 85).addBox(-2.0F, -3.4926F, -5.988F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(23, 85).addBox(-2.0F, -3.4926F, -2.988F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(68, 44).addBox(-3.5F, -3.2926F, -5.988F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-6.5F, -3.4926F, -12.488F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 34).addBox(-6.5F, -3.4926F, -10.488F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-5.0F, -3.4926F, -12.488F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 58).addBox(-6.0F, -3.4926F, -10.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 57).addBox(-5.5F, -3.4926F, -11.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 58).addBox(-6.0F, -3.4926F, -11.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 57).addBox(-6.0F, -3.4926F, -11.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 39).addBox(-6.5F, -3.4926F, -11.988F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 58).addBox(3.5F, -3.4926F, -11.488F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 58).addBox(3.5F, -3.4926F, -10.988F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 57).addBox(4.0F, -3.4926F, -11.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 57).addBox(3.5F, -3.4926F, -11.988F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(3.0F, -3.4926F, -12.488F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(4.5F, -3.4926F, -12.488F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 39).addBox(3.0F, -3.4926F, -11.988F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(4, 34).addBox(3.0F, -3.4926F, -10.488F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(57, 23).addBox(2.5F, -3.1926F, -12.488F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(57, 23).addBox(-7.0F, -3.1926F, -12.488F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 74).addBox(-9.0F, -3.4926F, -13.488F, 16.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1962F, -17.8746F, -0.4641F, -2.5307F, 1.0472F, 3.1416F));

        PartDefinition cube_r58 = console.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(20, 22).addBox(0.4F, -0.1F, -1.4F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 22).addBox(-1.6F, -0.1F, -1.4F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 27).addBox(-1.6F, -0.1F, -1.4F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(17, 27).addBox(-1.6F, -0.1F, -0.4F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 56).addBox(-1.6F, 0.3F, -1.4F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.173F, -15.1704F, 7.0281F, -2.5307F, 1.0472F, 3.1416F));

        PartDefinition cube_r59 = console.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(18, 53).addBox(-0.44F, 0.1F, -1.9F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.2421F, -16.6864F, 5.6823F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition cube_r60 = console.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(18, 53).addBox(-0.35F, 0.1F, -1.9F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.8921F, -16.6864F, 2.8244F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition cube_r61 = console.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(18, 53).addBox(-0.575F, 0.1F, -1.9F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.9796F, -16.6864F, 4.4049F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition cube_r62 = console.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(20, 4).mirror().addBox(-0.5F, 0.0F, 1.2F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 4).mirror().addBox(0.7F, 0.0F, 1.2F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 4).mirror().addBox(0.7F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 4).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(11.6923F, -13.7319F, 6.3464F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition cube_r63 = console.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(21, 17).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(11.3416F, -14.0761F, 5.9707F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition cube_r64 = console.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(21, 17).addBox(0.5F, -1.0F, 0.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(21, 17).mirror().addBox(-0.75F, -1.0F, 1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.5222F, -13.1135F, 6.6524F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition cube_r65 = console.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(21, 17).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.8534F, -13.3591F, 7.1322F, -0.6109F, 1.0472F, 0.0F));

        PartDefinition cube_r66 = console.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(57, 52).addBox(-1.5F, -1.0F, -5.8F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(35, 52).addBox(-3.5F, -1.0F, -2.0F, 7.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(7.2747F, -15.8427F, 4.2001F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition cube_r67 = console.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(12, 14).addBox(-4.0F, 0.5F, -1.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 14).addBox(-5.5F, 0.5F, -1.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(13, 14).addBox(-5.5F, 0.5F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 14).addBox(-4.0F, 0.5F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 14).addBox(-5.5F, 0.5F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 14).addBox(-4.0F, 0.5F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 15).addBox(-4.5F, 0.5F, -1.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(13, 15).addBox(-6.5F, 0.5F, -1.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(13, 15).addBox(-6.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(13, 15).addBox(-4.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(12, 14).addBox(-3.5F, 0.5F, -1.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 14).addBox(-6.0F, 0.5F, -1.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 14).addBox(-6.0F, 0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(12, 14).addBox(-6.0F, 0.5F, -2.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(83, 63).addBox(-6.0F, 0.5F, -1.5F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(83, 63).addBox(-6.0F, 0.5F, 0.5F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 53).addBox(-6.0F, 0.8F, -2.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(69, 52).addBox(-9.0F, 0.5F, -3.0F, 16.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.6197F, -15.1295F, 6.7086F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition cube_r68 = console.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(15, 39).addBox(-2.5F, -0.5F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(9, 14).addBox(-2.5F, -0.5F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(51, 0).addBox(-2.5F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(15, 39).addBox(-2.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(9, 14).addBox(-2.5F, -0.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(15, 39).addBox(-2.5F, -0.5F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(9, 14).addBox(-2.5F, -0.5F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(9, 14).addBox(-0.5F, -0.5F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(9, 14).addBox(-0.5F, -0.5F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(51, 0).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(51, 0).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(9, 14).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(15, 39).addBox(-0.5F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(51, 0).addBox(-0.5F, -0.5F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.0F, -15.7934F, 10.3422F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition cube_r69 = console.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(22, 36).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 38).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.6204F, 8.3021F, -1.5708F, -0.9599F, 1.5708F));

        PartDefinition cube_r70 = console.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(22, 36).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 38).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.0469F, 9.1212F, -1.5708F, -0.9599F, 1.5708F));

        PartDefinition cube_r71 = console.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(22, 36).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 38).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.4733F, 9.9404F, -1.5708F, -0.9599F, 1.5708F));

        PartDefinition cube_r72 = console.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(22, 36).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 38).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.8997F, 10.7595F, -1.5708F, -0.9599F, 1.5708F));

        PartDefinition cube_r73 = console.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(22, 36).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 38).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.3261F, 11.5787F, -1.5708F, -0.9599F, 1.5708F));

        PartDefinition cube_r74 = console.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(22, 38).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 36).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.179F, 13.217F, -1.5708F, -0.9599F, 1.5708F));

        PartDefinition cube_r75 = console.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(22, 38).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 36).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.7525F, 12.3978F, -1.5708F, -0.9599F, 1.5708F));

        PartDefinition cube_r76 = console.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(62, 45).addBox(3.5F, -0.2F, -1.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -15.1295F, 11.6852F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition cube_r77 = console.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(57, 57).addBox(-6.0F, 0.2F, -2.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 65).addBox(-2.5F, 0.2F, -2.0F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(88, 75).addBox(-3.5F, 0.5F, 7.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 17).addBox(0.5F, 0.8F, 6.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 17).addBox(-1.5F, 0.8F, 6.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 17).addBox(0.75F, 0.6F, 5.3F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 17).addBox(-1.25F, 0.6F, 5.3F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 17).addBox(-3.25F, 0.6F, 5.3F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 17).addBox(-3.5F, 0.8F, 6.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 12).addBox(-3.5F, 0.5F, 6.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(15, 0).addBox(-2.5F, 0.5F, 6.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 0).addBox(-3.5F, 0.5F, 6.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(88, 75).addBox(0.5F, 0.5F, 7.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(13, 12).addBox(0.5F, 0.5F, 6.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(15, 0).addBox(1.5F, 0.5F, 6.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 0).addBox(0.5F, 0.5F, 6.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 0).addBox(-1.5F, 0.5F, 6.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 0).addBox(-0.5F, 0.5F, 6.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 12).addBox(-1.5F, 0.5F, 6.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(88, 75).addBox(-1.5F, 0.5F, 7.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(69, 64).addBox(-9.0F, 0.5F, -3.0F, 16.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -15.1295F, 11.6852F, -2.5307F, 0.0F, 3.1416F));

        PartDefinition cube_r78 = console.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(61, 49).addBox(-10.0F, -1.0F, -19.0526F, 21.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -11.1F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r79 = console.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(61, 49).addBox(-10.0F, -1.0F, -19.0526F, 21.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -11.1F, -0.866F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r80 = console.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(61, 49).addBox(-10.0F, -1.0F, -19.0526F, 21.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -11.1F, 0.0F, -3.1416F, 1.0472F, -3.1416F));

        PartDefinition cube_r81 = console.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(61, 49).addBox(-10.0F, -1.0F, -19.0526F, 21.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -11.1F, -0.866F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r82 = console.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(61, 49).addBox(-10.0F, -1.0F, -19.0526F, 21.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -11.1F, 0.866F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r83 = console.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(21, 29).addBox(-11.0F, -0.5F, -16.0394F, 22.0F, 1.0F, 14.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -12.5F, 3.0131F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r84 = console.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(21, 29).addBox(-18.817F, -1.0F, -5.5131F, 22.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.817F, -12.0F, 13.5394F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r85 = console.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(21, 29).addBox(-13.6095F, -0.5F, -20.5591F, 22.0F, 1.0F, 14.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -12.5F, 3.0131F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r86 = console.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(21, 29).addBox(-3.183F, -1.0F, -5.5131F, 22.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.817F, -12.0F, 13.5394F, -3.1416F, 1.0472F, -3.1416F));

        PartDefinition cube_r87 = console.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(21, 29).addBox(-8.3905F, -0.5F, -20.5591F, 22.0F, 1.0F, 14.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -12.5F, 3.0131F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r88 = console.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(27, 44).addBox(-1.5F, -0.7F, -1.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 40).addBox(-1.5F, -0.6F, -1.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(18, 44).addBox(-1.75F, -0.5F, -1.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.65F, -13.0F, -16.1526F, 0.1484F, 0.0F, 0.0F));

        PartDefinition cube_r89 = console.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0489F, -0.1101F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3865F, -15.1844F, -17.5526F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r90 = console.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(10, 7).addBox(-1.2654F, 0.6152F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3865F, -15.1844F, -17.5526F, 0.0F, 0.0F, 1.0908F));

        PartDefinition cube_r91 = console.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(10, 8).addBox(-0.6837F, 0.2297F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3865F, -15.1844F, -17.5526F, 0.0F, 0.0F, 1.7017F));

        PartDefinition cube_r92 = console.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(11, 2).addBox(-0.7049F, -0.7436F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3865F, -15.1844F, -17.5526F, 0.0F, 0.0F, 1.6144F));

        PartDefinition cube_r93 = console.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(10, 11).addBox(-1.689F, -1.5863F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3865F, -15.1844F, -17.5526F, 0.0F, 0.0F, 2.5744F));

        PartDefinition cube_r94 = console.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(13, 0).addBox(-0.8491F, -1.5734F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3865F, -15.1844F, -17.5526F, 0.0F, 0.0F, 1.8326F));

        PartDefinition cube_r95 = console.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0489F, -0.1101F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3865F, -15.1844F, -16.5526F, -3.1416F, 0.0F, 3.0107F));

        PartDefinition cube_r96 = console.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(10, 7).addBox(-1.2654F, 0.6152F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3865F, -15.1844F, -16.5526F, -3.1416F, 0.0F, 2.0508F));

        PartDefinition cube_r97 = console.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(10, 8).addBox(-0.6837F, 0.2297F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3865F, -15.1844F, -16.5526F, -3.1416F, 0.0F, 1.4399F));

        PartDefinition cube_r98 = console.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(11, 2).addBox(-0.7049F, -0.7436F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3865F, -15.1844F, -16.5526F, 3.1416F, 0.0F, 1.5272F));

        PartDefinition cube_r99 = console.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(10, 11).addBox(-1.689F, -1.5863F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3865F, -15.1844F, -16.5526F, -3.1416F, 0.0F, 0.5672F));

        PartDefinition cube_r100 = console.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(13, 0).addBox(-0.8491F, -1.5734F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3865F, -15.1844F, -16.5526F, -3.1416F, 0.0F, 1.309F));

        PartDefinition cube_r101 = console.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(10, 11).addBox(-0.5F, -2.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8984F, -16.5938F, -15.5526F, 0.0F, 0.0F, 2.5744F));

        PartDefinition cube_r102 = console.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, -2.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5818F, -15.7615F, -15.5526F, 0.0F, 0.0F, 1.8326F));

        PartDefinition cube_r103 = console.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(11, 2).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.6398F, -15.4221F, -15.5526F, 0.0F, 0.0F, 1.6144F));

        PartDefinition cube_r104 = console.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(10, 8).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.687F, -15.4618F, -15.5526F, 0.0F, 0.0F, 1.7017F));

        PartDefinition cube_r105 = console.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(10, 7).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0439F, -15.3483F, -15.5526F, 0.0F, 0.0F, 1.0908F));

        PartDefinition cube_r106 = console.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8F, -15.0F, -15.5526F, 0.0F, 0.0F, 0.1309F));

        PartDefinition pannel4 = console.addOrReplaceChild("pannel4", CubeListBuilder.create(), PartPose.offset(-0.6F, -15.8997F, 10.7595F));

        PartDefinition fix = pannel4.addOrReplaceChild("fix", CubeListBuilder.create(), PartPose.offsetAndRotation(4.8F, 1.778F, 2.0315F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition control = fix.addOrReplaceChild("control", CubeListBuilder.create().texOffs(53, 54).addBox(-0.5F, -0.6667F, -1.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(41, 50).addBox(-0.5F, -0.1667F, -0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(53, 51).addBox(-0.5F, -0.6667F, -1.45F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -0.0246F, 0.3339F));

        PartDefinition fix2 = pannel4.addOrReplaceChild("fix2", CubeListBuilder.create(), PartPose.offsetAndRotation(3.3F, 1.778F, 2.0315F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition control2 = fix2.addOrReplaceChild("control2", CubeListBuilder.create().texOffs(53, 54).addBox(-0.5F, -0.6667F, -1.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(41, 50).addBox(-0.5F, -0.1667F, -0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(53, 51).addBox(-0.5F, -0.6667F, -1.45F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -0.0246F, 0.3339F));

        PartDefinition fix3 = pannel4.addOrReplaceChild("fix3", CubeListBuilder.create(), PartPose.offsetAndRotation(3.3F, 0.9176F, 0.8028F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition control3 = fix3.addOrReplaceChild("control3", CubeListBuilder.create().texOffs(53, 54).addBox(-0.5F, -0.6667F, -1.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(41, 50).addBox(-0.5F, -0.1667F, -0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(53, 51).addBox(-0.5F, -0.6667F, -1.45F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -0.0246F, 0.3339F));

        PartDefinition fix4 = pannel4.addOrReplaceChild("fix4", CubeListBuilder.create(), PartPose.offsetAndRotation(4.8F, 0.9176F, 0.8028F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition control4 = fix4.addOrReplaceChild("control4", CubeListBuilder.create().texOffs(53, 54).addBox(-0.5F, -0.6667F, -1.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(41, 50).addBox(-0.5F, -0.1667F, -0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(53, 51).addBox(-0.5F, -0.6667F, -1.45F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -0.0246F, 0.3339F));

        PartDefinition fix5 = pannel4.addOrReplaceChild("fix5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6F, 1.7207F, 2.4575F, -0.3927F, -0.9599F, 1.5708F));

        PartDefinition bone4 = fix5.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(7, 13).addBox(-0.5F, -1.1667F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.1667F, 0.0F));

        PartDefinition fix12 = pannel4.addOrReplaceChild("fix12", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6F, 1.1471F, 1.6384F, -0.3927F, -0.9599F, 1.5708F));

        PartDefinition bone10 = fix12.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(7, 13).addBox(-0.5F, -1.1667F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.1667F, 0.0F));

        PartDefinition fix7 = pannel4.addOrReplaceChild("fix7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6F, 0.5736F, 0.8192F, -0.3927F, -0.9599F, 1.5708F));

        PartDefinition bone5 = fix7.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(7, 13).addBox(-0.5F, -1.1667F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.1667F, 0.0F));

        PartDefinition fix8 = pannel4.addOrReplaceChild("fix8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6F, 0.0F, 0.0F, -0.3927F, -0.9599F, 1.5708F));

        PartDefinition bone6 = fix8.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(7, 13).addBox(-0.5F, -1.1667F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.1667F, 0.0F));

        PartDefinition fix9 = pannel4.addOrReplaceChild("fix9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6F, -0.5736F, -0.8191F, -0.3927F, -0.9599F, 1.5708F));

        PartDefinition bone7 = fix9.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(7, 13).addBox(-0.5F, -1.1667F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.1667F, 0.0F));

        PartDefinition fix10 = pannel4.addOrReplaceChild("fix10", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6F, -1.1472F, -1.6383F, -0.3927F, -0.9599F, 1.5708F));

        PartDefinition bone8 = fix10.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(7, 13).addBox(-0.5F, -1.1667F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.1667F, 0.0F));

        PartDefinition fix11 = pannel4.addOrReplaceChild("fix11", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6F, -1.7208F, -2.4574F, -0.3927F, -0.9599F, 1.5708F));

        PartDefinition bone9 = fix11.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(7, 13).addBox(-0.5F, -1.1667F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.1667F, 0.0F));

        PartDefinition spin2 = pannel4.addOrReplaceChild("spin2", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.875F, 0.0945F, 2.0092F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition bone2 = spin2.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(17, 23).addBox(-0.475F, -0.325F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(29, 23).addBox(-0.575F, 0.175F, -0.5F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 23).addBox(1.425F, -1.425F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(18, 51).addBox(1.425F, -1.425F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lighton13 = console.addOrReplaceChild("lighton13", CubeListBuilder.create(), PartPose.offset(-3.0F, -15.4164F, 11.0549F));

        PartDefinition cube_r107 = lighton13.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(9, 14).addBox(-1.5F, -0.4F, 2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition off18 = lighton13.addOrReplaceChild("off18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r108 = off18.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(22, 71).addBox(-1.5F, -0.4F, 2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition lighton15 = console.addOrReplaceChild("lighton15", CubeListBuilder.create(), PartPose.offset(-3.0F, -15.4164F, 11.0549F));

        PartDefinition cube_r109 = lighton15.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(15, 39).addBox(-1.5F, -0.4F, 0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition off20 = lighton15.addOrReplaceChild("off20", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r110 = off20.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(38, 71).addBox(-1.5F, -0.4F, 0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition lighton16 = console.addOrReplaceChild("lighton16", CubeListBuilder.create(), PartPose.offset(-3.0F, -15.4164F, 11.0549F));

        PartDefinition cube_r111 = lighton16.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(9, 14).addBox(-0.5F, -0.4F, 0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition off21 = lighton16.addOrReplaceChild("off21", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r112 = off21.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(22, 71).addBox(-0.5F, -0.4F, 0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition lighton17 = console.addOrReplaceChild("lighton17", CubeListBuilder.create(), PartPose.offset(-3.0F, -15.4164F, 11.0549F));

        PartDefinition cube_r113 = lighton17.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(15, 39).addBox(0.5F, -0.4F, 0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition off22 = lighton17.addOrReplaceChild("off22", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r114 = off22.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(38, 71).addBox(0.5F, -0.4F, 0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition lighton14 = console.addOrReplaceChild("lighton14", CubeListBuilder.create(), PartPose.offset(-3.0F, -15.4164F, 11.0549F));

        PartDefinition cube_r115 = lighton14.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(51, 0).addBox(-0.1F, -0.4F, 2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition off19 = lighton14.addOrReplaceChild("off19", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r116 = off19.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(26, 71).addBox(-0.1F, -0.4F, 2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition lighton9 = console.addOrReplaceChild("lighton9", CubeListBuilder.create(), PartPose.offset(3.0F, -15.4164F, 11.0549F));

        PartDefinition cube_r117 = lighton9.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(51, 0).addBox(0.5F, -0.4F, 0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, 3.1416F));

        PartDefinition off14 = lighton9.addOrReplaceChild("off14", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r118 = off14.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(26, 71).addBox(0.5F, -0.4F, 0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, 3.1416F));

        PartDefinition lighton10 = console.addOrReplaceChild("lighton10", CubeListBuilder.create(), PartPose.offset(3.0F, -15.4164F, 11.0549F));

        PartDefinition cube_r119 = lighton10.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(9, 14).addBox(0.5F, -0.4F, 2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, 3.1416F));

        PartDefinition off15 = lighton10.addOrReplaceChild("off15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r120 = off15.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(22, 71).addBox(0.5F, -0.4F, 2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, 3.1416F));

        PartDefinition lightson = console.addOrReplaceChild("lightson", CubeListBuilder.create(), PartPose.offset(3.0F, -15.4164F, 11.0549F));

        PartDefinition cube_r121 = lightson.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(51, 0).addBox(-0.9F, -0.4F, 2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, 3.1416F));

        PartDefinition off13 = lightson.addOrReplaceChild("off13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r122 = off13.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(26, 71).addBox(-0.9F, -0.4F, 2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, 3.1416F));

        PartDefinition lighton11 = console.addOrReplaceChild("lighton11", CubeListBuilder.create(), PartPose.offset(3.0F, -15.4164F, 11.0549F));

        PartDefinition cube_r123 = lighton11.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(15, 39).addBox(-0.5F, -0.4F, 0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, 3.1416F));

        PartDefinition off16 = lighton11.addOrReplaceChild("off16", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r124 = off16.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(38, 71).addBox(-0.5F, -0.4F, 0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, 3.1416F));

        PartDefinition lighton12 = console.addOrReplaceChild("lighton12", CubeListBuilder.create(), PartPose.offset(3.0F, -15.4164F, 11.0549F));

        PartDefinition cube_r125 = lighton12.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(9, 14).addBox(-1.5F, -0.4F, 0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, 3.1416F));

        PartDefinition off17 = lighton12.addOrReplaceChild("off17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r126 = off17.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(22, 71).addBox(-1.5F, -0.4F, 0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, 3.1416F));

        PartDefinition pannel3 = console.addOrReplaceChild("pannel3", CubeListBuilder.create(), PartPose.offsetAndRotation(10.0636F, -15.92F, 7.3113F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r127 = pannel3.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(45, 50).addBox(2.4F, 0.7409F, -2.5917F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(45, 50).addBox(2.4F, 0.7409F, -1.5917F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(45, 50).addBox(2.4F, 0.7409F, -0.5917F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(45, 50).addBox(-3.0F, 0.7409F, -0.5917F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(45, 50).addBox(-3.0F, 0.7409F, -1.5917F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(45, 50).addBox(-3.0F, 0.7409F, -2.5917F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition dematlever = pannel3.addOrReplaceChild("dematlever", CubeListBuilder.create(), PartPose.offsetAndRotation(1.425F, 2.5757F, -5.7202F, -0.5042F, 0.272F, 0.4531F));

        PartDefinition dematleveryay = dematlever.addOrReplaceChild("dematleveryay", CubeListBuilder.create().texOffs(10, 59).addBox(-0.625F, -6.15F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(12, 59).addBox(-0.375F, -6.35F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition dial = pannel3.addOrReplaceChild("dial", CubeListBuilder.create(), PartPose.offsetAndRotation(0.7292F, 1.4842F, 0.1516F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition dialfix = dial.addOrReplaceChild("dialfix", CubeListBuilder.create().texOffs(51, 2).addBox(-0.5F, -0.675F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(18, 17).addBox(-0.5F, -0.175F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(33, 53).addBox(-0.2401F, 0.025F, -0.7F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(41, 50).addBox(-0.5F, -0.675F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.065F, 0.0F, -0.075F));

        PartDefinition spin = pannel3.addOrReplaceChild("spin", CubeListBuilder.create().texOffs(41, 50).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.7F, 1.8065F, 0.0678F, -2.5307F, 0.0F, -3.1416F));

        PartDefinition lighton4 = console.addOrReplaceChild("lighton4", CubeListBuilder.create(), PartPose.offset(7.2142F, -14.3594F, 10.285F));

        PartDefinition cube_r128 = lighton4.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(9, 14).addBox(-0.5F, -1.1F, -0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition off5 = lighton4.addOrReplaceChild("off5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r129 = off5.addOrReplaceChild("cube_r129", CubeListBuilder.create().texOffs(22, 71).addBox(-0.5F, -1.1F, -0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition lighton5 = console.addOrReplaceChild("lighton5", CubeListBuilder.create(), PartPose.offset(7.2142F, -14.3594F, 10.285F));

        PartDefinition cube_r130 = lighton5.addOrReplaceChild("cube_r130", CubeListBuilder.create().texOffs(15, 39).addBox(-0.5F, -1.1F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition off6 = lighton5.addOrReplaceChild("off6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r131 = off6.addOrReplaceChild("cube_r131", CubeListBuilder.create().texOffs(38, 71).addBox(-0.5F, -1.1F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition lighton6 = console.addOrReplaceChild("lighton6", CubeListBuilder.create(), PartPose.offset(7.2142F, -14.3594F, 10.285F));

        PartDefinition cube_r132 = lighton6.addOrReplaceChild("cube_r132", CubeListBuilder.create().texOffs(51, 0).addBox(-0.5F, -1.1F, -2.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition off7 = lighton6.addOrReplaceChild("off7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r133 = off7.addOrReplaceChild("cube_r133", CubeListBuilder.create().texOffs(26, 71).addBox(-0.5F, -1.1F, -2.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition brightlighton3 = console.addOrReplaceChild("brightlighton3", CubeListBuilder.create(), PartPose.offset(5.6504F, -18.743F, 3.2622F));

        PartDefinition cube_r134 = brightlighton3.addOrReplaceChild("cube_r134", CubeListBuilder.create().texOffs(14, 36).addBox(1.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition off10 = brightlighton3.addOrReplaceChild("off10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r135 = off10.addOrReplaceChild("cube_r135", CubeListBuilder.create().texOffs(10, 36).addBox(1.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition brightlighton2 = console.addOrReplaceChild("brightlighton2", CubeListBuilder.create(), PartPose.offset(5.6504F, -18.743F, 3.2622F));

        PartDefinition cube_r136 = brightlighton2.addOrReplaceChild("cube_r136", CubeListBuilder.create().texOffs(14, 36).addBox(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition off9 = brightlighton2.addOrReplaceChild("off9", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r137 = off9.addOrReplaceChild("cube_r137", CubeListBuilder.create().texOffs(10, 36).addBox(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition brightlighton = console.addOrReplaceChild("brightlighton", CubeListBuilder.create(), PartPose.offset(5.6504F, -18.743F, 3.2622F));

        PartDefinition cube_r138 = brightlighton.addOrReplaceChild("cube_r138", CubeListBuilder.create().texOffs(14, 36).addBox(-2.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition off8 = brightlighton.addOrReplaceChild("off8", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r139 = off8.addOrReplaceChild("cube_r139", CubeListBuilder.create().texOffs(10, 36).addBox(-2.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, -1.0472F, -3.1416F));

        PartDefinition pannel5 = console.addOrReplaceChild("pannel5", CubeListBuilder.create(), PartPose.offset(-9.2958F, -16.1044F, 6.9258F));

        PartDefinition fix13 = pannel5.addOrReplaceChild("fix13", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.7725F, 0.6473F, -2.2675F, -2.5307F, 1.0472F, -3.1416F));

        PartDefinition spin3 = fix13.addOrReplaceChild("spin3", CubeListBuilder.create().texOffs(55, 17).addBox(-0.4752F, -0.9091F, -0.5143F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0249F, -0.091F, 0.0143F));

        PartDefinition cube_r140 = spin3.addOrReplaceChild("cube_r140", CubeListBuilder.create().texOffs(18, 57).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0248F, -0.091F, 0.0143F, 0.0F, -0.7418F, 0.0F));

        PartDefinition fix14 = pannel5.addOrReplaceChild("fix14", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0219F, 0.0F, 0.0F, -2.5307F, 1.0472F, -3.1416F));

        PartDefinition spin4 = fix14.addOrReplaceChild("spin4", CubeListBuilder.create().texOffs(55, 18).addBox(-0.5F, -0.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
                .texOffs(58, 19).addBox(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.8281F, 0.1F, -0.7F));

        PartDefinition fix16 = pannel5.addOrReplaceChild("fix16", CubeListBuilder.create(), PartPose.offsetAndRotation(0.9713F, -0.803F, -0.5734F, -2.5307F, 1.0472F, -3.1416F));

        PartDefinition spin6 = fix16.addOrReplaceChild("spin6", CubeListBuilder.create().texOffs(55, 18).addBox(-0.5F, -0.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
                .texOffs(58, 19).addBox(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.8281F, 0.1F, -0.7F));

        PartDefinition fix17 = pannel5.addOrReplaceChild("fix17", CubeListBuilder.create(), PartPose.offsetAndRotation(0.1212F, -0.803F, -2.0457F, -2.5307F, 1.0472F, -3.1416F));

        PartDefinition spin7 = fix17.addOrReplaceChild("spin7", CubeListBuilder.create().texOffs(55, 18).addBox(-0.5F, -0.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
                .texOffs(58, 19).addBox(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.8281F, 0.1F, -0.7F));

        PartDefinition fix15 = pannel5.addOrReplaceChild("fix15", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.8719F, 0.0F, -1.4722F, -2.5307F, 1.0472F, -3.1416F));

        PartDefinition spin5 = fix15.addOrReplaceChild("spin5", CubeListBuilder.create().texOffs(55, 18).addBox(-0.5F, -0.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
                .texOffs(58, 19).addBox(-0.5F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.8281F, 0.1F, -0.7F));

        PartDefinition blinkingon = console.addOrReplaceChild("blinkingon", CubeListBuilder.create(), PartPose.offset(-1.1962F, -17.8746F, -0.4641F));

        PartDefinition cube_r141 = blinkingon.addOrReplaceChild("cube_r141", CubeListBuilder.create().texOffs(18, 53).addBox(-1.5F, -4.3926F, -7.488F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 1.0472F, 3.1416F));

        PartDefinition blinkingoff = blinkingon.addOrReplaceChild("blinkingoff", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r142 = blinkingoff.addOrReplaceChild("cube_r142", CubeListBuilder.create().texOffs(39, 15).addBox(-1.5F, -4.3926F, -7.488F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.5307F, 1.0472F, 3.1416F));

        PartDefinition lighton7 = console.addOrReplaceChild("lighton7", CubeListBuilder.create(), PartPose.offset(-10.8219F, -14.1954F, -6.248F));

        PartDefinition cube_r143 = lighton7.addOrReplaceChild("cube_r143", CubeListBuilder.create().texOffs(9, 14).addBox(-5.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition off11 = lighton7.addOrReplaceChild("off11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r144 = off11.addOrReplaceChild("cube_r144", CubeListBuilder.create().texOffs(22, 71).addBox(-5.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition lighton8 = console.addOrReplaceChild("lighton8", CubeListBuilder.create(), PartPose.offset(-10.8219F, -14.1954F, -6.248F));

        PartDefinition cube_r145 = lighton8.addOrReplaceChild("cube_r145", CubeListBuilder.create().texOffs(9, 14).addBox(4.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition off12 = lighton8.addOrReplaceChild("off12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r146 = off12.addOrReplaceChild("cube_r146", CubeListBuilder.create().texOffs(22, 71).addBox(4.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition lighton18 = console.addOrReplaceChild("lighton18", CubeListBuilder.create(), PartPose.offset(-5.7142F, -18.3252F, -3.2991F));

        PartDefinition cube_r147 = lighton18.addOrReplaceChild("cube_r147", CubeListBuilder.create().texOffs(51, 0).addBox(-0.5F, -0.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition off23 = lighton18.addOrReplaceChild("off23", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r148 = off23.addOrReplaceChild("cube_r148", CubeListBuilder.create().texOffs(26, 71).addBox(-0.5F, -0.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.14F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition thing = console.addOrReplaceChild("thing", CubeListBuilder.create().texOffs(18, 55).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-7.7771F, -15.7687F, -7.3769F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition thing2 = console.addOrReplaceChild("thing2", CubeListBuilder.create().texOffs(18, 55).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-8.2771F, -15.7687F, -6.5109F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition bone11 = console.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(18, 55).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-10.4054F, -14.0479F, -7.7396F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition thing3 = console.addOrReplaceChild("thing3", CubeListBuilder.create().texOffs(18, 55).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-9.9054F, -14.0479F, -8.6056F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition fix18 = console.addOrReplaceChild("fix18", CubeListBuilder.create(), PartPose.offsetAndRotation(-10.3068F, -15.6453F, -3.7123F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition dial2 = fix18.addOrReplaceChild("dial2", CubeListBuilder.create().texOffs(0, 14).addBox(-0.4116F, -0.7455F, -0.3857F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-0.4116F, -0.2455F, -0.3857F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 36).addBox(-1.0116F, 0.0555F, -0.8857F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 18).addBox(-0.3116F, -0.2455F, -0.3857F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 18).addBox(0.5884F, -0.2455F, -0.3857F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-0.4116F, -0.7455F, 0.1143F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fix19 = console.addOrReplaceChild("fix19", CubeListBuilder.create(), PartPose.offsetAndRotation(-9.2068F, -15.6453F, -5.6176F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition dial3 = fix19.addOrReplaceChild("dial3", CubeListBuilder.create().texOffs(0, 14).addBox(-0.4116F, -0.7455F, -0.3856F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-0.4116F, -0.2455F, -0.3856F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 36).addBox(-1.0116F, 0.0555F, -0.8856F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 18).addBox(-0.3116F, -0.2455F, -0.3856F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 18).addBox(0.5884F, -0.2455F, -0.3856F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-0.4116F, -0.7455F, 0.1144F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fix20 = console.addOrReplaceChild("fix20", CubeListBuilder.create(), PartPose.offsetAndRotation(-10.6256F, -14.4981F, -6.4367F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition dial4 = fix20.addOrReplaceChild("dial4", CubeListBuilder.create().texOffs(0, 14).addBox(-0.4116F, -0.7455F, -0.3857F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-0.4116F, -0.2455F, -0.3857F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 36).addBox(-1.0116F, 0.0555F, -0.8857F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 18).addBox(-0.3116F, -0.2455F, -0.3857F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 18).addBox(0.5884F, -0.2455F, -0.3857F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-0.4116F, -0.7455F, 0.1143F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fix22 = console.addOrReplaceChild("fix22", CubeListBuilder.create(), PartPose.offsetAndRotation(-9.1662F, -15.0717F, -7.3262F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition dial6 = fix22.addOrReplaceChild("dial6", CubeListBuilder.create().texOffs(0, 14).addBox(-0.4116F, -0.7455F, -0.3856F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-0.4116F, -0.2455F, -0.3856F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 36).addBox(-1.0116F, 0.0555F, -0.8856F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 18).addBox(-0.3116F, -0.2455F, -0.3856F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 18).addBox(0.5884F, -0.2455F, -0.3856F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-0.4116F, -0.7455F, 0.1144F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fix21 = console.addOrReplaceChild("fix21", CubeListBuilder.create(), PartPose.offsetAndRotation(-11.7256F, -14.4981F, -4.5315F, 0.6109F, 1.0472F, 0.0F));

        PartDefinition dial5 = fix21.addOrReplaceChild("dial5", CubeListBuilder.create().texOffs(0, 14).addBox(-0.4116F, -0.7455F, -0.3856F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-0.4116F, -0.2455F, -0.3856F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 36).addBox(-1.0116F, 0.0555F, -0.8856F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 18).addBox(-0.3116F, -0.2455F, -0.3856F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 18).addBox(0.5884F, -0.2455F, -0.3856F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-0.4116F, -0.7455F, 0.1144F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition dividers = console.addOrReplaceChild("dividers", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.8671F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r149 = dividers.addOrReplaceChild("cube_r149", CubeListBuilder.create().texOffs(54, 62).mirror().addBox(-0.5F, -6.3198F, 2.6353F, 1.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.2351F, 0.0F, 2.5744F, 0.0F, -3.1416F));

        PartDefinition cube_r150 = dividers.addOrReplaceChild("cube_r150", CubeListBuilder.create().texOffs(54, 62).addBox(-0.5F, -1.0F, -6.5F, 1.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.6567F, 10.563F, -0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r151 = dividers.addOrReplaceChild("cube_r151", CubeListBuilder.create().texOffs(54, 62).mirror().addBox(-0.5F, -6.3198F, 2.6353F, 1.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.2351F, 0.0F, 2.5744F, -1.0472F, -3.1416F));

        PartDefinition cube_r152 = dividers.addOrReplaceChild("cube_r152", CubeListBuilder.create().texOffs(54, 62).addBox(-0.5F, -1.0F, -6.5F, 1.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.1478F, 0.6567F, 5.2815F, -0.5672F, 1.0472F, 0.0F));

        PartDefinition cube_r153 = dividers.addOrReplaceChild("cube_r153", CubeListBuilder.create().texOffs(54, 62).addBox(-0.5F, -1.0F, -6.5F, 1.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.1478F, 0.6567F, -5.2815F, 2.5744F, 1.0472F, -3.1416F));

        PartDefinition cube_r154 = dividers.addOrReplaceChild("cube_r154", CubeListBuilder.create().texOffs(54, 62).mirror().addBox(-0.5F, -0.2F, -6.0F, 1.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.1549F, -0.2866F, 5.2856F, -0.5672F, -1.0472F, 0.0F));

        PartDefinition slide2 = console.addOrReplaceChild("slide2", CubeListBuilder.create(), PartPose.offset(-2.8F, -15.7237F, -9.9648F));

        PartDefinition cube_r155 = slide2.addOrReplaceChild("cube_r155", CubeListBuilder.create().texOffs(16, 3).addBox(0.2F, -0.2F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.7F, -0.1023F, -0.3769F, 0.6109F, 0.0F, 0.0F));

        PartDefinition slide = console.addOrReplaceChild("slide", CubeListBuilder.create(), PartPose.offset(-4.5F, -15.1501F, -10.784F));

        PartDefinition cube_r156 = slide.addOrReplaceChild("cube_r156", CubeListBuilder.create().texOffs(0, 26).addBox(-1.5F, -0.2F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.0F, -0.6759F, 0.4423F, 0.6109F, 0.0F, 0.0F));

        PartDefinition fix6 = console.addOrReplaceChild("fix6", CubeListBuilder.create(), PartPose.offsetAndRotation(3.25F, -14.6157F, -8.6533F, -0.5672F, 0.0F, 0.0F));

        PartDefinition handbreak = fix6.addOrReplaceChild("handbreak", CubeListBuilder.create(), PartPose.offset(0.25F, -2.6098F, -0.527F));

        PartDefinition cube_r157 = handbreak.addOrReplaceChild("cube_r157", CubeListBuilder.create().texOffs(30, 17).addBox(-0.25F, 0.3681F, -0.3228F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 17).addBox(-0.25F, -0.6319F, -0.5228F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.3598F, -0.923F, 1.6581F, 0.0F, 0.0F));

        PartDefinition cube_r158 = handbreak.addOrReplaceChild("cube_r158", CubeListBuilder.create().texOffs(30, 17).addBox(0.25F, -0.6319F, -0.5228F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.3598F, -0.923F, 1.4399F, 0.0F, 0.0F));

        PartDefinition cube_r159 = handbreak.addOrReplaceChild("cube_r159", CubeListBuilder.create().texOffs(26, 23).addBox(-0.45F, -1.6653F, -0.5876F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.15F))
                .texOffs(17, 23).addBox(-0.5F, -0.7653F, -0.5376F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-0.25F, 0.3598F, -0.923F, 0.6109F, 0.0F, 0.0F));

        PartDefinition flightlighton = console.addOrReplaceChild("flightlighton", CubeListBuilder.create(), PartPose.offset(4.9F, -14.7117F, -11.7588F));

        PartDefinition cube_r160 = flightlighton.addOrReplaceChild("cube_r160", CubeListBuilder.create().texOffs(9, 14).addBox(-0.5F, -0.5F, -0.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition off = flightlighton.addOrReplaceChild("off", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r161 = off.addOrReplaceChild("cube_r161", CubeListBuilder.create().texOffs(22, 71).addBox(-0.5F, -0.5F, -0.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition fix30 = console.addOrReplaceChild("fix30", CubeListBuilder.create(), PartPose.offset(-4.0F, -13.2492F, -12.6271F));

        PartDefinition thing10 = fix30.addOrReplaceChild("thing10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r162 = thing10.addOrReplaceChild("cube_r162", CubeListBuilder.create().texOffs(36, 3).addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition thing9 = fix30.addOrReplaceChild("thing9", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition cube_r163 = thing9.addOrReplaceChild("cube_r163", CubeListBuilder.create().texOffs(41, 1).addBox(2.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition thing8 = fix30.addOrReplaceChild("thing8", CubeListBuilder.create(), PartPose.offset(4.5F, 0.0F, 0.0F));

        PartDefinition cube_r164 = thing8.addOrReplaceChild("cube_r164", CubeListBuilder.create().texOffs(41, 1).addBox(4.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offsetAndRotation(-4.5F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition thing7 = fix30.addOrReplaceChild("thing7", CubeListBuilder.create(), PartPose.offset(6.0F, 0.0F, 0.0F));

        PartDefinition cube_r165 = thing7.addOrReplaceChild("cube_r165", CubeListBuilder.create().texOffs(41, 1).addBox(5.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition thing6 = fix30.addOrReplaceChild("thing6", CubeListBuilder.create(), PartPose.offset(8.5F, 0.0F, 0.0F));

        PartDefinition cube_r166 = thing6.addOrReplaceChild("cube_r166", CubeListBuilder.create().texOffs(44, 0).addBox(7.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offsetAndRotation(-8.5F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition fix29 = console.addOrReplaceChild("fix29", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -16.7889F, -8.2692F, 2.1817F, 0.0F, 0.0F));

        PartDefinition thing5 = fix29.addOrReplaceChild("thing5", CubeListBuilder.create().texOffs(25, 38).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fix28 = console.addOrReplaceChild("fix28", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -15.355F, -10.3171F, 2.1817F, 0.0F, 0.0F));

        PartDefinition thing4 = fix28.addOrReplaceChild("thing4", CubeListBuilder.create().texOffs(25, 3).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fix24 = console.addOrReplaceChild("fix24", CubeListBuilder.create(), PartPose.offset(1.0F, -15.4369F, -10.3744F));

        PartDefinition dial7 = fix24.addOrReplaceChild("dial7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r167 = dial7.addOrReplaceChild("cube_r167", CubeListBuilder.create().texOffs(33, 4).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 3).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1817F, 0.0F, 0.0F));

        PartDefinition fix25 = console.addOrReplaceChild("fix25", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.3F, -13.7162F, -12.8319F, 2.1817F, 0.0F, 0.0F));

        PartDefinition bone3 = fix25.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(33, 4).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 3).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fix26 = console.addOrReplaceChild("fix26", CubeListBuilder.create(), PartPose.offsetAndRotation(2.7F, -13.7162F, -12.8319F, 2.1817F, 0.0F, 0.0F));

        PartDefinition bone12 = fix26.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(33, 4).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 3).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fix27 = console.addOrReplaceChild("fix27", CubeListBuilder.create(), PartPose.offsetAndRotation(5.2F, -13.7162F, -12.8319F, 2.1817F, 0.0F, 0.0F));

        PartDefinition bone13 = fix27.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(33, 4).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 3).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fix23 = console.addOrReplaceChild("fix23", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.25F, -16.8708F, -8.3265F, 2.1817F, 0.0F, 0.0F));

        PartDefinition spin8 = fix23.addOrReplaceChild("spin8", CubeListBuilder.create().texOffs(32, 3).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(33, 4).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fix31 = console.addOrReplaceChild("fix31", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.3F, -17.3012F, -6.4915F, 0.6109F, 0.0F, 0.0F));

        PartDefinition dial8 = fix31.addOrReplaceChild("dial8", CubeListBuilder.create().texOffs(10, 12).addBox(-0.3F, -0.5F, -0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(-0.2F, 0.0F, -0.2F));

        PartDefinition fix32 = console.addOrReplaceChild("fix32", CubeListBuilder.create(), PartPose.offsetAndRotation(2.35F, -17.3012F, -6.4915F, 0.6109F, 0.0F, 0.0F));

        PartDefinition dial9 = fix32.addOrReplaceChild("dial9", CubeListBuilder.create().texOffs(10, 12).mirror().addBox(-0.7F, -0.1F, -0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(0.2F, -0.4F, -0.2F));

        PartDefinition clock = console.addOrReplaceChild("clock", CubeListBuilder.create(), PartPose.offsetAndRotation(0.25F, -18.1287F, -6.0943F, 0.6109F, 0.0F, 0.0F));

        PartDefinition minute = clock.addOrReplaceChild("minute", CubeListBuilder.create().texOffs(11, 11).addBox(-0.25F, 0.0F, -0.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.25F, -0.05F, -0.25F));

        PartDefinition hour = clock.addOrReplaceChild("hour", CubeListBuilder.create().texOffs(11, 11).addBox(-0.25F, 0.0F, -0.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.25F, 0.05F, -0.25F));

        PartDefinition rotor = console.addOrReplaceChild("rotor", CubeListBuilder.create(), PartPose.offset(-0.8079F, -25.4311F, 3.015F));

        PartDefinition bottom = rotor.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 73).addBox(-0.5F, -5.817F, 2.6102F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 63).addBox(-2.0F, 0.6829F, 2.5262F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(79, 36).addBox(-3.0F, 0.584F, -3.0484F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(79, 36).addBox(-3.0F, 1.774F, -3.0484F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.8079F, -1.1829F, -3.0038F));

        PartDefinition cube_r168 = bottom.addOrReplaceChild("cube_r168", CubeListBuilder.create().texOffs(43, 77).addBox(-2.5F, -8.0F, 0.5F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.083F, 3.819F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r169 = bottom.addOrReplaceChild("cube_r169", CubeListBuilder.create().texOffs(10, 63).mirror().addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.1829F, -3.0484F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r170 = bottom.addOrReplaceChild("cube_r170", CubeListBuilder.create().texOffs(10, 63).mirror().addBox(-2.0F, -0.5F, 2.4641F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0634F, 1.1829F, 0.0255F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r171 = bottom.addOrReplaceChild("cube_r171", CubeListBuilder.create().texOffs(10, 63).mirror().addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(2.6304F, 1.1829F, -1.5298F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r172 = bottom.addOrReplaceChild("cube_r172", CubeListBuilder.create().texOffs(10, 63).addBox(-2.0F, -0.5F, 2.4641F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.0634F, 1.1829F, 0.0255F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r173 = bottom.addOrReplaceChild("cube_r173", CubeListBuilder.create().texOffs(10, 63).addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-2.6304F, 1.1829F, -1.5298F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r174 = bottom.addOrReplaceChild("cube_r174", CubeListBuilder.create().texOffs(0, 73).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r175 = bottom.addOrReplaceChild("cube_r175", CubeListBuilder.create().texOffs(0, 73).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r176 = bottom.addOrReplaceChild("cube_r176", CubeListBuilder.create().texOffs(0, 73).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r177 = bottom.addOrReplaceChild("cube_r177", CubeListBuilder.create().texOffs(0, 73).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r178 = bottom.addOrReplaceChild("cube_r178", CubeListBuilder.create().texOffs(0, 73).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, -3.1416F, 0.7854F, 3.1416F));

        PartDefinition cube_r179 = bottom.addOrReplaceChild("cube_r179", CubeListBuilder.create().texOffs(0, 73).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r180 = bottom.addOrReplaceChild("cube_r180", CubeListBuilder.create().texOffs(0, 73).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, -3.1416F, -0.7854F, 3.1416F));

        PartDefinition top2 = rotor.addOrReplaceChild("top2", CubeListBuilder.create().texOffs(44, 86).addBox(-3.0143F, 1.0911F, -3.0345F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(44, 86).addBox(-3.0143F, -0.0989F, -3.0345F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(53, 67).addBox(-2.0F, 0.0F, 2.5373F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.8079F, -17.5F, -3.015F, 0.0F, -0.3927F, 0.0F));

        PartDefinition cube_r181 = top2.addOrReplaceChild("cube_r181", CubeListBuilder.create().texOffs(53, 67).mirror().addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.5F, -3.0373F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r182 = top2.addOrReplaceChild("cube_r182", CubeListBuilder.create().texOffs(53, 67).mirror().addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(2.6304F, 0.5F, -1.5187F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r183 = top2.addOrReplaceChild("cube_r183", CubeListBuilder.create().texOffs(53, 67).mirror().addBox(-2.0F, -0.4961F, 2.5373F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.4961F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r184 = top2.addOrReplaceChild("cube_r184", CubeListBuilder.create().texOffs(53, 67).addBox(-2.0F, -0.4961F, 2.5373F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.4961F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r185 = top2.addOrReplaceChild("cube_r185", CubeListBuilder.create().texOffs(53, 67).addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-2.6304F, 0.5F, -1.5187F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r186 = top2.addOrReplaceChild("cube_r186", CubeListBuilder.create().texOffs(4, 73).addBox(-0.5F, -8.0F, 2.6213F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -1.5708F, 3.1416F));

        PartDefinition cube_r187 = top2.addOrReplaceChild("cube_r187", CubeListBuilder.create().texOffs(4, 73).addBox(-0.5F, -8.0F, 2.6213F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -3.1416F, -0.7854F, 0.0F));

        PartDefinition cube_r188 = top2.addOrReplaceChild("cube_r188", CubeListBuilder.create().texOffs(4, 73).addBox(-0.5F, -8.0F, 2.6213F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r189 = top2.addOrReplaceChild("cube_r189", CubeListBuilder.create().texOffs(4, 73).addBox(-0.5F, -8.0F, 2.6213F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r190 = top2.addOrReplaceChild("cube_r190", CubeListBuilder.create().texOffs(4, 73).addBox(-0.5F, -8.0F, 2.6213F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 1.5708F, -3.1416F));

        PartDefinition cube_r191 = top2.addOrReplaceChild("cube_r191", CubeListBuilder.create().texOffs(4, 73).addBox(-0.5F, -8.0F, 2.6213F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 0.7854F, 3.1416F));

        PartDefinition cube_r192 = top2.addOrReplaceChild("cube_r192", CubeListBuilder.create().texOffs(4, 73).addBox(-0.5F, -8.0F, 2.6213F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 3.1416F));

        PartDefinition cube_r193 = top2.addOrReplaceChild("cube_r193", CubeListBuilder.create().texOffs(4, 73).addBox(-0.5F, -8.0F, 2.6213F, 1.0F, 17.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -0.7854F, 3.1416F));

        PartDefinition nature = console.addOrReplaceChild("nature", CubeListBuilder.create().texOffs(1, 101).addBox(-0.5F, -5.817F, 2.6102F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 121).addBox(-2.0F, 0.6829F, 2.5262F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(14, 117).addBox(-3.0F, 0.584F, -3.0484F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(14, 117).addBox(-3.0F, 1.774F, -3.0484F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(8, 101).addBox(-2.5F, -6.917F, -4.3412F, 5.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -26.614F, 0.0111F));

        PartDefinition cube_r194 = nature.addOrReplaceChild("cube_r194", CubeListBuilder.create().texOffs(8, 101).addBox(-2.5F, -10.5F, -4.3301F, 5.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.583F, -0.0111F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r195 = nature.addOrReplaceChild("cube_r195", CubeListBuilder.create().texOffs(8, 101).addBox(-2.5F, -10.5F, -4.3301F, 5.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.583F, -0.0111F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r196 = nature.addOrReplaceChild("cube_r196", CubeListBuilder.create().texOffs(43, 93).addBox(-2.5F, -8.0F, 0.5F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(8, 101).addBox(-2.5F, -8.0F, -0.5F, 5.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.083F, 3.819F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r197 = nature.addOrReplaceChild("cube_r197", CubeListBuilder.create().texOffs(8, 101).addBox(-2.5F, -10.5F, -4.3301F, 5.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.583F, -0.0111F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r198 = nature.addOrReplaceChild("cube_r198", CubeListBuilder.create().texOffs(8, 101).addBox(-2.5F, -10.5F, -4.3301F, 5.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.583F, -0.0111F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r199 = nature.addOrReplaceChild("cube_r199", CubeListBuilder.create().texOffs(7, 121).mirror().addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.1829F, -3.0484F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r200 = nature.addOrReplaceChild("cube_r200", CubeListBuilder.create().texOffs(7, 121).mirror().addBox(-2.0F, -0.5F, 2.4641F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0634F, 1.1829F, 0.0255F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r201 = nature.addOrReplaceChild("cube_r201", CubeListBuilder.create().texOffs(7, 121).mirror().addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(2.6304F, 1.1829F, -1.5298F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r202 = nature.addOrReplaceChild("cube_r202", CubeListBuilder.create().texOffs(7, 121).addBox(-2.0F, -0.5F, 2.4641F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.0634F, 1.1829F, 0.0255F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r203 = nature.addOrReplaceChild("cube_r203", CubeListBuilder.create().texOffs(7, 121).addBox(-2.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-2.6304F, 1.1829F, -1.5298F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r204 = nature.addOrReplaceChild("cube_r204", CubeListBuilder.create().texOffs(1, 101).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r205 = nature.addOrReplaceChild("cube_r205", CubeListBuilder.create().texOffs(1, 101).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r206 = nature.addOrReplaceChild("cube_r206", CubeListBuilder.create().texOffs(1, 101).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r207 = nature.addOrReplaceChild("cube_r207", CubeListBuilder.create().texOffs(1, 101).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r208 = nature.addOrReplaceChild("cube_r208", CubeListBuilder.create().texOffs(1, 101).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, -3.1416F, 0.7854F, 3.1416F));

        PartDefinition cube_r209 = nature.addOrReplaceChild("cube_r209", CubeListBuilder.create().texOffs(1, 101).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r210 = nature.addOrReplaceChild("cube_r210", CubeListBuilder.create().texOffs(1, 101).addBox(-0.5F, -7.5F, 2.6213F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6829F, -0.0111F, -3.1416F, -0.7854F, 3.1416F));

        PartDefinition inside = nature.addOrReplaceChild("inside", CubeListBuilder.create().texOffs(8, 85).addBox(-2.5F, 8.5F, -4.3301F, 5.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.417F, -0.0111F));

        PartDefinition cube_r211 = inside.addOrReplaceChild("cube_r211", CubeListBuilder.create().texOffs(8, 85).addBox(-2.5F, -10.5F, -4.3301F, 5.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r212 = inside.addOrReplaceChild("cube_r212", CubeListBuilder.create().texOffs(8, 85).addBox(-2.5F, -10.5F, -4.3301F, 5.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r213 = inside.addOrReplaceChild("cube_r213", CubeListBuilder.create().texOffs(8, 85).addBox(-2.5F, -8.0F, -0.5F, 5.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.5F, 3.8301F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r214 = inside.addOrReplaceChild("cube_r214", CubeListBuilder.create().texOffs(8, 85).addBox(-2.5F, -10.5F, -4.3301F, 5.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r215 = inside.addOrReplaceChild("cube_r215", CubeListBuilder.create().texOffs(8, 85).addBox(-2.5F, -10.5F, -4.3301F, 5.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition underdividers = console.addOrReplaceChild("underdividers", CubeListBuilder.create().texOffs(0, 30).addBox(-0.5F, -7.0F, 4.6278F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 8).mirror().addBox(-0.5F, -1.0F, -5.6278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(22, 23).addBox(-0.5F, -56.45F, 5.6278F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 23).addBox(-0.5F, -60.45F, 5.6278F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 23).addBox(-0.5F, -61.45F, 5.6278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -2.6311F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r216 = underdividers.addOrReplaceChild("cube_r216", CubeListBuilder.create().texOffs(0, 23).mirror().addBox(-0.5F, 1.0569F, 0.175F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(8.0849F, 1.2922F, 4.6678F, 1.2654F, -1.0472F, 3.1416F));

        PartDefinition cube_r217 = underdividers.addOrReplaceChild("cube_r217", CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-1.0F, 1.2562F, -3.0287F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.602F, 0.5234F, 4.9664F, 1.8762F, 1.0472F, 0.0F));

        PartDefinition cube_r218 = underdividers.addOrReplaceChild("cube_r218", CubeListBuilder.create().texOffs(21, 0).mirror().addBox(-0.5F, -1.0522F, -0.8966F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.4618F, 1.8688F, 4.8854F, 1.7017F, -1.0472F, -3.1416F));

        PartDefinition cube_r219 = underdividers.addOrReplaceChild("cube_r219", CubeListBuilder.create().texOffs(12, 2).mirror().addBox(-0.5F, -4.3012F, -0.2325F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(8.0849F, 1.2922F, 4.6678F, 2.0508F, -1.0472F, 3.1416F));

        PartDefinition cube_r220 = underdividers.addOrReplaceChild("cube_r220", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, 1.4066F, 0.5519F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0234F, -9.9328F, -1.8762F, 0.0F, 0.0F));

        PartDefinition cube_r221 = underdividers.addOrReplaceChild("cube_r221", CubeListBuilder.create().texOffs(0, 23).addBox(-0.5F, 1.0569F, 0.175F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, 1.2922F, -9.3356F, -1.8762F, 0.0F, 0.0F));

        PartDefinition cube_r222 = underdividers.addOrReplaceChild("cube_r222", CubeListBuilder.create().texOffs(21, 0).addBox(-0.5F, -1.1025F, -0.0372F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0234F, -9.9328F, -1.4399F, 0.0F, 0.0F));

        PartDefinition cube_r223 = underdividers.addOrReplaceChild("cube_r223", CubeListBuilder.create().texOffs(12, 2).addBox(-0.5F, -4.3012F, -0.2325F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, 1.2922F, -9.3356F, -1.0908F, 0.0F, 0.0F));

        PartDefinition cube_r224 = underdividers.addOrReplaceChild("cube_r224", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -0.0301F, -2.3954F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.8294F, 0.7406F, 5.675F, 1.8762F, -1.0472F, 0.0F));

        PartDefinition cube_r225 = underdividers.addOrReplaceChild("cube_r225", CubeListBuilder.create().texOffs(0, 23).addBox(-0.5F, 1.0569F, 0.175F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-8.0849F, 1.2922F, 4.6678F, 1.2654F, 1.0472F, -3.1416F));

        PartDefinition cube_r226 = underdividers.addOrReplaceChild("cube_r226", CubeListBuilder.create().texOffs(21, 0).addBox(-0.5F, -1.0522F, -0.8966F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4618F, 1.8688F, 4.8854F, 1.7017F, 1.0472F, -3.1416F));

        PartDefinition cube_r227 = underdividers.addOrReplaceChild("cube_r227", CubeListBuilder.create().texOffs(12, 2).mirror().addBox(-0.5F, -4.3012F, -0.2325F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(-8.0849F, 1.2922F, 4.6678F, 2.0508F, 1.0472F, -3.1416F));

        PartDefinition cube_r228 = underdividers.addOrReplaceChild("cube_r228", CubeListBuilder.create().texOffs(22, 23).addBox(-0.5F, -52.1358F, -0.3535F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-5.18F, -9.3142F, -2.9907F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r229 = underdividers.addOrReplaceChild("cube_r229", CubeListBuilder.create().texOffs(22, 23).addBox(-0.5F, -56.55F, 5.6278F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(22, 23).addBox(-0.5F, -55.55F, 5.6278F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 23).addBox(-0.5F, -51.55F, 5.6278F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.9F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r230 = underdividers.addOrReplaceChild("cube_r230", CubeListBuilder.create().texOffs(22, 23).mirror().addBox(-0.5F, -2.05F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(22, 23).mirror().addBox(-0.5F, -1.05F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(22, 23).mirror().addBox(-0.5F, 2.95F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -59.4F, -6.1278F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r231 = underdividers.addOrReplaceChild("cube_r231", CubeListBuilder.create().texOffs(22, 23).addBox(-0.5F, -52.0892F, -1.949F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(6.5616F, -9.3608F, -3.7884F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r232 = underdividers.addOrReplaceChild("cube_r232", CubeListBuilder.create().texOffs(22, 23).addBox(-0.5F, -57.45F, 5.4301F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(22, 23).addBox(-0.5F, -56.45F, 5.4301F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 23).addBox(-0.5F, -52.45F, 5.4301F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1712F, -4.0F, 0.0988F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r233 = underdividers.addOrReplaceChild("cube_r233", CubeListBuilder.create().texOffs(22, 23).addBox(-0.5F, -52.1358F, -0.3535F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.18F, -8.3142F, -2.9907F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r234 = underdividers.addOrReplaceChild("cube_r234", CubeListBuilder.create().texOffs(22, 23).addBox(-0.5F, -52.0892F, -1.949F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5616F, -8.3608F, -3.7884F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r235 = underdividers.addOrReplaceChild("cube_r235", CubeListBuilder.create().texOffs(22, 23).addBox(-0.5F, -53.0892F, -1.949F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5616F, -3.3608F, -3.7884F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r236 = underdividers.addOrReplaceChild("cube_r236", CubeListBuilder.create().texOffs(22, 23).addBox(-0.5F, -53.1358F, -0.3535F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.18F, -3.3142F, -2.9907F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r237 = underdividers.addOrReplaceChild("cube_r237", CubeListBuilder.create().texOffs(11, 8).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.4408F, 0.0F, 2.5639F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r238 = underdividers.addOrReplaceChild("cube_r238", CubeListBuilder.create().texOffs(10, 40).mirror().addBox(2.0128F, -19.0122F, 5.5155F, 1.0F, 16.0F, 3.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(-2.5128F, -5.9648F, -1.4508F, -1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r239 = underdividers.addOrReplaceChild("cube_r239", CubeListBuilder.create().texOffs(10, 40).mirror().addBox(-3.0128F, -19.0122F, 5.5155F, 1.0F, 16.0F, 3.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(-2.5128F, -5.9648F, -1.4508F, 2.138F, 1.0472F, 3.1416F));

        PartDefinition cube_r240 = underdividers.addOrReplaceChild("cube_r240", CubeListBuilder.create().texOffs(0, 30).addBox(-0.5F, -2.5392F, -2.949F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5616F, -4.4608F, -3.7884F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r241 = underdividers.addOrReplaceChild("cube_r241", CubeListBuilder.create().texOffs(10, 40).mirror().addBox(-0.5F, -15.3414F, 3.177F, 1.0F, 16.0F, 3.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(-2.5128F, -5.9648F, -1.4508F, 2.138F, -1.0472F, 3.1416F));

        PartDefinition cube_r242 = underdividers.addOrReplaceChild("cube_r242", CubeListBuilder.create().texOffs(0, 30).addBox(-0.5F, -2.5858F, -1.3535F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.18F, -4.4142F, -2.9907F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r243 = underdividers.addOrReplaceChild("cube_r243", CubeListBuilder.create().texOffs(11, 8).mirror().addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.4408F, 0.0F, 2.5639F, 0.0F, -2.0944F, 0.0F));

        PartDefinition top = console.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 18).addBox(-3.384F, 7.7F, -5.9952F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 19).addBox(-3.884F, 3.2F, -6.9344F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(25, 0).addBox(-3.884F, 6.0F, -6.788F, 7.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(34, 21).addBox(-3.384F, -0.75F, -5.9952F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(34, 20).addBox(-3.384F, -5.75F, -5.9952F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.384F, -58.2311F, 0.799F));

        PartDefinition cube_r244 = top.addOrReplaceChild("cube_r244", CubeListBuilder.create().texOffs(57, 77).addBox(-6.372F, -66.7F, -6.0313F, 11.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.274F, 77.9621F, 0.7218F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r245 = top.addOrReplaceChild("cube_r245", CubeListBuilder.create().texOffs(15, 18).addBox(-3.5F, -0.75F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.5F, 3.95F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.5F, 6.95F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.5F, 5.45F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.5F, 14.1F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.5F, 14.7F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.384F, -5.0F, 4.7631F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r246 = top.addOrReplaceChild("cube_r246", CubeListBuilder.create().texOffs(15, 18).addBox(-3.0F, -1.25F, -5.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.0F, 3.45F, -5.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.0F, 6.45F, -5.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.0F, 4.95F, -5.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.0F, 13.6F, -5.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.0F, 14.2F, -5.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7679F, -4.5F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r247 = top.addOrReplaceChild("cube_r247", CubeListBuilder.create().texOffs(15, 18).addBox(-3.0F, -1.25F, 4.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.0F, 3.45F, 4.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.0F, 6.45F, 4.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.0F, 4.95F, 4.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.0F, 13.6F, 4.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.0F, 14.2F, 4.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7679F, -4.5F, -1.5981F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r248 = top.addOrReplaceChild("cube_r248", CubeListBuilder.create().texOffs(15, 18).addBox(-3.5F, -0.75F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.5F, 3.95F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.5F, 6.95F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.5F, 5.45F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.5F, 14.1F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 18).addBox(-3.5F, 14.7F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.384F, -5.0F, -6.3612F, 0.0F, -3.1416F, 0.0F));

        PartDefinition cube_r249 = top.addOrReplaceChild("cube_r249", CubeListBuilder.create().texOffs(15, 18).mirror().addBox(-4.0F, -1.25F, -5.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(15, 18).mirror().addBox(-4.0F, 3.45F, -5.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(15, 18).mirror().addBox(-4.0F, 6.45F, -5.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(15, 18).mirror().addBox(-4.0F, 4.95F, -5.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(15, 18).mirror().addBox(-4.0F, 13.6F, -5.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(15, 18).mirror().addBox(-4.0F, 14.2F, -5.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.5F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r250 = top.addOrReplaceChild("cube_r250", CubeListBuilder.create().texOffs(15, 18).mirror().addBox(-4.0F, -1.25F, 4.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(15, 18).mirror().addBox(-4.0F, 3.45F, 4.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(15, 18).mirror().addBox(-4.0F, 6.45F, 4.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(15, 18).mirror().addBox(-4.0F, 4.95F, 4.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(15, 18).mirror().addBox(-4.0F, 13.6F, 4.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(15, 18).mirror().addBox(-4.0F, 14.2F, 4.3301F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.5F, -1.5981F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r251 = top.addOrReplaceChild("cube_r251", CubeListBuilder.create().texOffs(34, 20).addBox(-2.5F, -2.25F, -4.3301F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(34, 21).addBox(-2.5F, 2.75F, -4.3301F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-2.5F, 11.2F, -4.3301F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.616F, -3.5F, -0.799F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r252 = top.addOrReplaceChild("cube_r252", CubeListBuilder.create().texOffs(31, 17).addBox(-2.5F, -2.25F, -4.3301F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(31, 18).addBox(-2.5F, 2.75F, -4.3301F, 6.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).mirror().addBox(-2.5F, 11.2F, -4.3301F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.884F, -3.5F, 0.067F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r253 = top.addOrReplaceChild("cube_r253", CubeListBuilder.create().texOffs(34, 20).mirror().addBox(-2.5F, -2.25F, -4.3301F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(34, 21).mirror().addBox(-2.5F, 2.75F, -4.3301F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 18).mirror().addBox(-2.5F, 11.2F, -4.3301F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.384F, -3.5F, -0.799F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r254 = top.addOrReplaceChild("cube_r254", CubeListBuilder.create().texOffs(34, 20).addBox(-3.0F, -2.25F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(34, 21).addBox(-3.0F, 2.75F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.384F, -3.5F, 2.3971F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r255 = top.addOrReplaceChild("cube_r255", CubeListBuilder.create().texOffs(34, 20).addBox(-2.5F, -2.25F, -4.3301F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(34, 21).addBox(-2.5F, 2.75F, -4.3301F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-2.5F, 11.2F, -4.3301F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.116F, -3.5F, -1.6651F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r256 = top.addOrReplaceChild("cube_r256", CubeListBuilder.create().texOffs(29, 56).mirror().addBox(-0.5F, -0.5F, 5.289F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(29, 56).mirror().addBox(0.9849F, 0.9849F, 5.289F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(29, 56).mirror().addBox(0.9849F, 0.9849F, -6.289F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(29, 56).mirror().addBox(-0.5F, -0.5F, -6.289F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.384F, 7.0F, -0.799F, 2.2555F, -0.6591F, -2.0344F));

        PartDefinition cube_r257 = top.addOrReplaceChild("cube_r257", CubeListBuilder.create().texOffs(29, 56).addBox(-1.9849F, 0.9849F, 5.289F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-1.9849F, 0.9849F, -6.289F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.384F, 7.0F, -0.799F, -2.2555F, -0.6591F, 2.0344F));

        PartDefinition cube_r258 = top.addOrReplaceChild("cube_r258", CubeListBuilder.create().texOffs(29, 56).mirror().addBox(-0.5F, -0.5F, 5.289F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(29, 56).mirror().addBox(0.9849F, 0.9849F, 5.289F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(29, 56).mirror().addBox(0.9849F, 0.9849F, -6.289F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(29, 56).mirror().addBox(-0.5F, -0.5F, -6.289F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.384F, 7.0F, -0.799F, 0.8861F, -0.6591F, -1.1071F));

        PartDefinition cube_r259 = top.addOrReplaceChild("cube_r259", CubeListBuilder.create().texOffs(29, 56).addBox(-1.9849F, 0.9849F, 5.289F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-1.9849F, 0.9849F, -6.289F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.384F, 7.0F, -0.799F, -0.8861F, -0.6591F, 1.1071F));

        PartDefinition cube_r260 = top.addOrReplaceChild("cube_r260", CubeListBuilder.create().texOffs(29, 56).mirror().addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(29, 56).mirror().addBox(-0.5F, -0.5F, -12.0779F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.384F, 7.0F, 4.9899F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r261 = top.addOrReplaceChild("cube_r261", CubeListBuilder.create().texOffs(29, 56).mirror().addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(29, 56).mirror().addBox(-0.5F, -0.5F, -12.0779F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.716F, 7.0F, 4.9899F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r262 = top.addOrReplaceChild("cube_r262", CubeListBuilder.create().texOffs(29, 56).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-0.5F, -0.5F, -12.0779F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.484F, 7.0F, 4.9899F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r263 = top.addOrReplaceChild("cube_r263", CubeListBuilder.create().texOffs(25, 0).addBox(-3.5F, -1.0F, -6.0622F, 7.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.4474F, 7.0F, -0.7624F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r264 = top.addOrReplaceChild("cube_r264", CubeListBuilder.create().texOffs(25, 0).addBox(-3.5F, -1.0F, -6.0622F, 7.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.4474F, 7.0F, -0.8356F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r265 = top.addOrReplaceChild("cube_r265", CubeListBuilder.create().texOffs(25, 0).addBox(-3.5F, -1.0F, -0.5F, 7.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.384F, 7.0F, 4.6899F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r266 = top.addOrReplaceChild("cube_r266", CubeListBuilder.create().texOffs(25, 0).mirror().addBox(-3.5F, -1.0F, -6.0622F, 7.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.3206F, 7.0F, -0.8356F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r267 = top.addOrReplaceChild("cube_r267", CubeListBuilder.create().texOffs(25, 0).mirror().addBox(-3.5F, -1.0F, -6.0622F, 7.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.3206F, 7.0F, -0.7624F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r268 = top.addOrReplaceChild("cube_r268", CubeListBuilder.create().texOffs(15, 19).mirror().addBox(-3.5F, -1.5F, -6.0622F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.4474F, 4.7F, -0.8356F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r269 = top.addOrReplaceChild("cube_r269", CubeListBuilder.create().texOffs(15, 19).mirror().addBox(-3.5F, -1.5F, -6.0622F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.4474F, 4.7F, -0.7624F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r270 = top.addOrReplaceChild("cube_r270", CubeListBuilder.create().texOffs(15, 19).addBox(-3.5F, -1.5F, -6.0622F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.384F, 4.7F, -0.7258F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r271 = top.addOrReplaceChild("cube_r271", CubeListBuilder.create().texOffs(15, 19).addBox(-3.5F, -1.5F, -6.0622F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.3206F, 4.7F, -0.7624F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r272 = top.addOrReplaceChild("cube_r272", CubeListBuilder.create().texOffs(15, 19).addBox(-3.5F, -1.5F, -6.0622F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.3206F, 4.7F, -0.8356F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r273 = top.addOrReplaceChild("cube_r273", CubeListBuilder.create().texOffs(29, 56).addBox(-1.9142F, 0.9142F, 4.5961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-0.5F, -0.5F, 4.5961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(0.9142F, -1.9142F, 4.5961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(0.9142F, -1.9142F, -5.5961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-0.5F, -0.5F, -5.5961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-1.9142F, 0.9142F, -5.5961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.384F, 10.8F, -0.799F, -2.2555F, -0.6591F, 2.0344F));

        PartDefinition cube_r274 = top.addOrReplaceChild("cube_r274", CubeListBuilder.create().texOffs(29, 56).addBox(-1.9142F, 0.9142F, 4.5961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-0.5F, -0.5F, 4.5961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(0.9142F, -1.9142F, 4.5961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(0.9142F, -1.9142F, -5.5961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-0.5F, -0.5F, -5.5961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-1.9142F, 0.9142F, -5.5961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.384F, 10.8F, -0.799F, -0.8861F, -0.6591F, 1.1071F));

        PartDefinition cube_r275 = top.addOrReplaceChild("cube_r275", CubeListBuilder.create().texOffs(29, 56).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-0.5F, -0.5F, -10.6923F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.384F, 10.8F, 4.2971F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r276 = top.addOrReplaceChild("cube_r276", CubeListBuilder.create().texOffs(29, 56).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-0.5F, -0.5F, -10.6923F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.384F, 10.8F, 4.2971F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r277 = top.addOrReplaceChild("cube_r277", CubeListBuilder.create().texOffs(29, 56).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 56).addBox(-0.5F, -0.5F, -10.6923F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.616F, 10.8F, 4.2971F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r278 = top.addOrReplaceChild("cube_r278", CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -2.0F, -0.5F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.384F, 9.7F, 3.8971F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone = console.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(10.2685F, -14.6995F, -5.9718F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r279 = bone.addOrReplaceChild("cube_r279", CubeListBuilder.create().texOffs(4, 30).mirror().addBox(4.7375F, -0.0412F, -0.7948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 23).addBox(3.5375F, -0.4412F, -0.7948F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(4, 30).addBox(2.2375F, -0.0412F, -0.7948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 36).addBox(3.0375F, -0.1412F, -1.2948F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(31, 20).addBox(3.5375F, -0.0412F, -2.4948F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 32).addBox(-0.4625F, -0.0412F, 2.4052F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(49, 44).addBox(-1.9625F, -0.0412F, -2.5948F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 36).addBox(1.3075F, -0.4412F, -1.8948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 36).addBox(0.2875F, -0.4412F, -1.8948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 36).addBox(-0.7125F, -0.4412F, -1.8948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 36).addBox(-1.7125F, -0.4412F, -1.8948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 30).addBox(-5.7625F, -0.0412F, -0.7948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 30).mirror().addBox(-3.2625F, -0.0412F, -0.7948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 23).addBox(-4.4625F, -0.4412F, -0.7948F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(16, 36).addBox(-4.9625F, -0.1412F, -1.2948F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(31, 20).addBox(-4.4625F, -0.0412F, -2.4948F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r280 = bone.addOrReplaceChild("cube_r280", CubeListBuilder.create().texOffs(31, 20).addBox(-4.5375F, -0.0412F, -1.9052F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(31, 20).addBox(3.4625F, -0.0412F, -1.9052F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.5307F, 0.0F, 3.1416F));

        PartDefinition flick4 = bone.addOrReplaceChild("flick4", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, 0.5F, -1.25F, 0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r281 = flick4.addOrReplaceChild("cube_r281", CubeListBuilder.create().texOffs(24, 38).addBox(-1.7125F, -1.2412F, -1.3948F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.5F, 1.25F, 0.6109F, 0.0F, 0.0F));

        PartDefinition flick2 = bone.addOrReplaceChild("flick2", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 0.5F, -1.25F, 0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r282 = flick2.addOrReplaceChild("cube_r282", CubeListBuilder.create().texOffs(24, 38).addBox(-1.7125F, -1.2412F, -1.3948F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.5F, 1.25F, 0.6109F, 0.0F, 0.0F));

        PartDefinition flick3 = bone.addOrReplaceChild("flick3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 0.5F, -1.25F, 0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r283 = flick3.addOrReplaceChild("cube_r283", CubeListBuilder.create().texOffs(24, 38).addBox(-1.7125F, -1.2412F, -1.3948F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.5F, 1.25F, 0.6109F, 0.0F, 0.0F));

        PartDefinition flick5 = bone.addOrReplaceChild("flick5", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, 0.5F, -1.25F, 0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r284 = flick5.addOrReplaceChild("cube_r284", CubeListBuilder.create().texOffs(24, 38).addBox(-1.7125F, -1.2412F, -1.3948F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.5F, 1.25F, 0.6109F, 0.0F, 0.0F));

        PartDefinition lighton3 = bone.addOrReplaceChild("lighton3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r285 = lighton3.addOrReplaceChild("cube_r285", CubeListBuilder.create().texOffs(15, 39).addBox(0.9375F, -0.4412F, -0.5948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition off4 = lighton3.addOrReplaceChild("off4", CubeListBuilder.create(), PartPose.offset(2.8F, 1.0F, 0.0F));

        PartDefinition cube_r286 = off4.addOrReplaceChild("cube_r286", CubeListBuilder.create().texOffs(38, 71).addBox(-1.8625F, -0.4412F, -0.5948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition lighton2 = bone.addOrReplaceChild("lighton2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r287 = lighton2.addOrReplaceChild("cube_r287", CubeListBuilder.create().texOffs(15, 39).addBox(-0.4625F, -0.4412F, -0.5948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition off3 = lighton2.addOrReplaceChild("off3", CubeListBuilder.create(), PartPose.offset(1.4F, 1.0F, 0.0F));

        PartDefinition cube_r288 = off3.addOrReplaceChild("cube_r288", CubeListBuilder.create().texOffs(38, 71).addBox(-1.8625F, -0.4412F, -0.5948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition lighton = bone.addOrReplaceChild("lighton", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r289 = lighton.addOrReplaceChild("cube_r289", CubeListBuilder.create().texOffs(9, 14).addBox(-1.8625F, -0.4412F, -0.5948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition off2 = lighton.addOrReplaceChild("off2", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition cube_r290 = off2.addOrReplaceChild("cube_r290", CubeListBuilder.create().texOffs(22, 71).addBox(-1.8625F, -0.4412F, -0.5948F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.03F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition bone14 = console.addOrReplaceChild("bone14", CubeListBuilder.create(), PartPose.offset(5.1369F, -17.8042F, -6.5191F));

        PartDefinition bone15 = bone14.addOrReplaceChild("bone15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r291 = bone15.addOrReplaceChild("cube_r291", CubeListBuilder.create().texOffs(50, 21).addBox(0.4939F, -2.0403F, -0.3528F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.251F))
                .texOffs(50, 17).addBox(0.4939F, -2.5403F, -1.3528F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, -2.0F, 1.2198F, 0.0547F, -0.9943F));

        PartDefinition cube_r292 = bone15.addOrReplaceChild("cube_r292", CubeListBuilder.create().texOffs(18, 48).addBox(0.3491F, -0.901F, -0.3528F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -1.25F, -2.0F, 1.2103F, -0.2319F, -0.8878F));

        PartDefinition cube_r293 = bone15.addOrReplaceChild("cube_r293", CubeListBuilder.create().texOffs(34, 17).addBox(0.6383F, -0.1859F, -0.3538F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.251F)), PartPose.offsetAndRotation(0.0F, -1.25F, -2.0F, 1.061F, -0.7904F, -0.5961F));

        PartDefinition cube_r294 = bone15.addOrReplaceChild("cube_r294", CubeListBuilder.create().texOffs(31, 50).addBox(1.1679F, 0.5804F, -0.3528F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -1.25F, -2.0F, 0.6413F, -1.128F, -0.094F));

        PartDefinition toolbox = modelPartData.addOrReplaceChild("toolbox", CubeListBuilder.create().texOffs(62, 86).addBox(-4.1786F, 2.8588F, -2.0509F, 9.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(68, 161).addBox(-4.1786F, -0.1412F, 1.9491F, 9.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(68, 161).addBox(-4.1786F, -0.1412F, -2.0509F, 9.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(13, 65).addBox(4.8214F, -0.1412F, -2.0509F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(78, 160).addBox(-4.1786F, -1.9412F, -0.5509F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(68, 159).addBox(4.8214F, -1.9412F, -0.5509F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.003F))
                .texOffs(31, 40).addBox(4.8214F, -1.1412F, -1.0509F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(13, 65).addBox(-4.1786F, -0.1412F, -2.0509F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(31, 40).addBox(-4.1786F, -1.1412F, -1.0509F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(68, 159).addBox(-4.1786F, -1.9412F, -0.5509F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.003F)), PartPose.offsetAndRotation(-12.0854F, 21.1412F, -7.4027F, 0.0F, 1.309F, 0.0F));

        PartDefinition cube_r295 = toolbox.addOrReplaceChild("cube_r295", CubeListBuilder.create().texOffs(18, 43).addBox(0.0F, -1.087F, -1.1637F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.002F))
                .texOffs(18, 43).addBox(9.0F, -1.087F, -1.1637F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-4.1786F, -0.672F, 0.1271F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r296 = toolbox.addOrReplaceChild("cube_r296", CubeListBuilder.create().texOffs(15, 7).addBox(0.0F, -2.1637F, -0.913F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.002F))
                .texOffs(15, 7).addBox(9.0F, -2.1637F, -0.913F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-4.1786F, -0.672F, 0.1271F, -2.3562F, 0.0F, 0.0F));

        PartDefinition lid = toolbox.addOrReplaceChild("lid", CubeListBuilder.create(), PartPose.offset(-1.4085F, -0.1422F, -2.0496F));

        PartDefinition cube_r297 = lid.addOrReplaceChild("cube_r297", CubeListBuilder.create().texOffs(15, 11).addBox(-3.0F, -1.5892F, -2.0142F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 3).addBox(3.0F, -2.0892F, -2.0142F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 11).addBox(0.0F, -1.5892F, -2.0142F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(78, 12).addBox(-4.5F, -2.1892F, -2.0142F, 9.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.73F, 0.851F, 1.9988F, -0.7854F, 0.0F, 0.0F));

        PartDefinition lid2 = toolbox.addOrReplaceChild("lid2", CubeListBuilder.create().texOffs(77, 47).addBox(-4.5F, -1.8197F, -2.4697F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(80, 46).addBox(-4.5F, -1.3197F, -2.4697F, 9.0F, 0.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(58, 16).addBox(-1.5F, -3.8197F, -1.9697F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(0.3214F, -0.1215F, 1.9188F));

        PartDefinition cube_r298 = lid2.addOrReplaceChild("cube_r298", CubeListBuilder.create().texOffs(15, 11).addBox(-2.0F, -1.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2318F, -0.8889F, -2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r299 = lid2.addOrReplaceChild("cube_r299", CubeListBuilder.create().texOffs(23, 2).addBox(1.0F, -0.5868F, 1.0153F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 2).addBox(3.0F, -0.5868F, 1.0153F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(83, 0).addBox(-4.5F, -2.1868F, 2.0153F, 9.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.8303F, -1.9697F, 0.7854F, 0.0F, 0.0F));

        PartDefinition hammer = toolbox.addOrReplaceChild("hammer", CubeListBuilder.create(), PartPose.offsetAndRotation(5.0656F, 1.3092F, -0.7743F, -1.5708F, -1.0472F, -3.1416F));

        PartDefinition bone17 = hammer.addOrReplaceChild("bone17", CubeListBuilder.create(), PartPose.offsetAndRotation(0.1628F, -0.8422F, -0.2597F, 0.0392F, 0.0192F, 1.0428F));

        PartDefinition cube_r300 = bone17.addOrReplaceChild("cube_r300", CubeListBuilder.create().texOffs(112, 74).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.3F, -0.2508F, -0.0114F, 3.1378F, -0.1139F, 3.1333F));

        PartDefinition bone16 = bone17.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(115, 63).addBox(-0.5F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(112, 56).addBox(5.25F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -0.0008F, -0.0114F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
                       float green, float blue, float alpha) {
        matrices.pushPose();

        matrices.mulPose(Axis.YN.rotationDegrees(180f));

        console.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        toolbox.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        matrices.popPose();
    }

    @Override
    public void renderWithAnimations(ConsoleBlockEntity console, ClientTardis tardis, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        float delta = !AITModClient.CONFIG.animateControls ? 1.0f : 0.1f * client.getFrameTime();
        matrices.pushPose();
        matrices.translate(0.5f, -1.5f, -0.5f);
        matrices.mulPose(Axis.YN.rotationDegrees(180f));

        // Throttle Control
        ModelPart throttle = this.dematleveryay;
        float throttleTarget = tardis.travel().maxSpeed().get() > 0 ? (float) tardis.travel().speed() / (float) tardis.travel().maxSpeed().get() : 0f;
        throttle.zRot = -getAngle(console, "throttle", throttleTarget, delta);

        // Handbrake Control
        ModelPart handbrake = this.console.getChild("fix6");
        float handbrakeTarget = !tardis.travel().handbrake() ? 1.2f : 0f;
        handbrake.xRot = getAngle(console, "handbrake", handbrakeTarget, delta) - 0.5f;

        // Power Switch
        ModelPart power = this.console.getChild("pannel4").getChild("spin2").getChild("bone2");
        float powerTarget = tardis.fuel().hasPower() ? 0 : -1.55f;
        power.yRot = getAngle(console, "power", powerTarget, delta);

        // Door Locking Mechanism Control
        ModelPart doorlock = this.fix9;
        float doorLockTarget = tardis.door().locked() ? -1.55f : 0;
        doorlock.xRot = getAngle(console, "door_lock", doorLockTarget, delta);

        // Door Control
        ModelPart doorControl = this.fix11;
        float doorControlTarget = tardis.door().isRightOpen() ? -1.55f : tardis.door().isLeftOpen() ? -1f : 0;
        doorControl.xRot = getAngle(console, "door_control", doorControlTarget, delta);

        // Alarm Control
        ModelPart alarms = this.dial2;
        float alarmTarget = tardis.alarm().isEnabled() ? 1f : -0.9f;
        alarms.yRot = getAngle(console, "alarm", alarmTarget, delta);

        // Hail Mary
        ModelPart hailmary = this.dial3;
        float hailMaryTarget = tardis.stats().hailMary().get() ? 1f : -0.9f;
        hailmary.yRot = getAngle(console, "hail_mary", hailMaryTarget, delta);

        // Cloak
        ModelPart cloak = this.dial5;
        float cloakTarget = tardis.<CloakHandler>handler(TardisComponent.Id.CLOAK).cloaked().get() ? 1f : -0.9f;
        cloak.yRot = getAngle(console, "cloak", cloakTarget, delta);

        // Siege Mode
        ModelPart siege = this.dial6;
        float siegeTarget = tardis.siege().isActive() ? 1f : -0.9f;
        siege.yRot = getAngle(console, "siege", siegeTarget, delta);

        // Shields
        ModelPart shields = this.dial4;
        float shieldTarget = tardis.areVisualShieldsActive() ? 1f : tardis.areShieldsActive() ? 0 : -0.9f;
        shields.yRot = getAngle(console, "shields", shieldTarget, delta);

        // Security
        ModelPart security = this.flick5;
        float securityTarget = tardis.stats().security().get() ? -1f : 0.85f;
        security.xRot = getAngle(console, "security", securityTarget, delta);

        // Direction Control
        ModelPart direction = this.dialfix;
        float directionTargetDegrees = (0.3927f * tardis.travel().destination().getRotation()) * (180f / (float) Math.PI);
        direction.yRot = getLerpedDegrees(console, "direction", directionTargetDegrees, delta);

        // Increments
        ModelPart increment = this.slide2;
        ModelPart increment2 = this.slide;

        int incrementVal = IncrementManager.increment(tardis);
        float targetOne;
        float targetTwo;

        if (incrementVal < 10) {
            targetOne = -1.0f;
            targetTwo = 0f;
        } else if (incrementVal < 100) {
            targetOne = -0.5f;
            targetTwo = 0.2f;
        } else if (incrementVal < 1000) {
            targetOne = -1.0f;
            targetTwo = 1.0f;
        } else if (incrementVal < 10000) {
            targetOne = -1.25f;
            targetTwo = 1.15f;
        } else {
            targetOne = -1.5f;
            targetTwo = 1.3f;
        }

        increment.x = getAngle(console, "increment_1", targetOne, delta) - 2.75f;
        increment2.x = getAngle(console, "increment_2", targetTwo, delta) - 4.75f;

        // Hammer
        ModelPart hammer = this.toolbox.getChild("hammer");
        hammer.skipDraw = tardis.extra().getConsoleHammer() == null || tardis.extra().getConsoleHammer().isEmpty();

        super.renderWithAnimations(console, tardis, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        toolbox.render(matrices, vertices, light, overlay, red, green, blue, pAlpha);

        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return console;
    }

    @Override
    public AnimationDefinition getAnimationForState(TravelHandlerBase.State state) {
        return switch (state) {
            case MAT, DEMAT, FLIGHT -> HudolinAnimations.FLIGHT;
            case LANDED -> HudolinAnimations.IDLE;
        };
    }
}