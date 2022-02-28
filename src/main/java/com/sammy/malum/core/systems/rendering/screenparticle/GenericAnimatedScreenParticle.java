package com.sammy.malum.core.systems.rendering.screenparticle;

import com.sammy.malum.core.systems.rendering.particle.options.ScreenParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SpriteSet;

import java.util.ArrayList;

public abstract class GenericAnimatedScreenParticle extends GenericScreenParticle {
    public final SpriteSet spriteSet;
    public ArrayList<Integer> ageToFrame = new ArrayList<>();

    public GenericAnimatedScreenParticle(ClientLevel world, ScreenParticleOptions data, double x, double y, double vx, double vy, SpriteSet spriteSet) {
        super(world, data, x, y, vx, vy);
        this.spriteSet = spriteSet;
        this.setSprite(0);
    }

    public void setSprite(int spriteIndex) {
        if (spriteSet instanceof ParticleEngine.MutableSpriteSet animatedSprite) {
            if (spriteIndex < animatedSprite.sprites.size() && spriteIndex >= 0) {
                setSprite(animatedSprite.sprites.get(spriteIndex));
            }
        }
    }

    @Override
    public void tick() {
        setSprite(ageToFrame.get(age));
        super.tick();
    }

    protected void addLoop(int min, int max, int times) {
        for (int i = 0; i < times; i++) {
            addFrames(min, max);
        }
    }

    protected void addFrames(int min, int max) {
        for (int i = min; i <= max; i++) {
            ageToFrame.add(i);
        }
    }

    protected void insertFrames(int insertIndex, int min, int max) {
        for (int i = min; i <= max; i++) {
            ageToFrame.add(insertIndex, i);
        }
    }
}

