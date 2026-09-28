package dev.amble.ait.client.boti;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;

public class PortalParticleManager extends ParticleEngine {

    public PortalParticleManager(ClientLevel world, Minecraft client) {
        super(world, client.getTextureManager());

        ParticleEngine main = client.particleEngine;
        this.providers = main.providers;
        this.spriteSets = main.spriteSets;
        this.textureAtlas = main.textureAtlas;
    }
}
