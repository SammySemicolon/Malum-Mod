package com.sammy.malum.common.item.spirit;

import com.sammy.malum.core.systems.item.IFloatingGlowItem;
import com.sammy.ortus.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.ortus.systems.rendering.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

import static com.sammy.malum.core.helper.SpiritHelper.spawnSpiritScreenParticles;

public class MalumSpiritItem extends Item implements IFloatingGlowItem, ItemParticleEmitter {
    public MalumSpiritType type;

    public MalumSpiritItem(Properties properties, MalumSpiritType type) {
        super(properties);
        this.type = type;
    }

    @Override
    public Color getColor() {
        return type.getColor();
    }

    @Override
    public Color getEndColor() {
        return type.getEndColor();
    }

    @OnlyIn(value = Dist.CLIENT)
    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        spawnSpiritScreenParticles(type.getColor(), type.getEndColor(), stack, x, y, renderOrder);
    }
}