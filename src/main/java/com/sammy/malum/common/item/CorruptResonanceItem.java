package com.sammy.malum.common.item;

import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.lodestar.lodestone.setup.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;
import team.lodestar.lodestone.systems.rendering.particle.screen.emitter.ItemParticleEmitter;

import java.awt.*;

public class CorruptResonanceItem extends Item {
    public CorruptResonanceItem(Properties builder) {
        super(builder);
    }
}