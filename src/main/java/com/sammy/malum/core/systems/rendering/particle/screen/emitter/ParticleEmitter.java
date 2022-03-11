package com.sammy.malum.core.systems.rendering.particle.screen.emitter;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.core.systems.rendering.particle.screen.GenericScreenParticle;
import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.sammy.malum.core.handlers.ScreenParticleHandler.PARTICLES;

public class ParticleEmitter {
    public final EmitterSupplier supplier;

    public ParticleEmitter(EmitterSupplier supplier) {
        this.supplier = supplier;
    }

    public void render(ItemStack stack, float x, float y) {
        PARTICLES.forEach((type, particles) -> {
            for (ScreenParticle particle : particles) {
                if (particle instanceof GenericScreenParticle screenParticle) {
                    if (stack.equals(screenParticle.data.stack)) {
                        screenParticle.x = x+screenParticle.data.xOffset+screenParticle.xMoved;
                        screenParticle.y = y+screenParticle.data.yOffset+screenParticle.yMoved;
                    }
                }
            }
        });
    }

    public void tick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        supplier.tick(stack, x, y, renderOrder, this);
    }

    public interface EmitterSupplier {
        void tick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder, ParticleEmitter emitter);
    }
}