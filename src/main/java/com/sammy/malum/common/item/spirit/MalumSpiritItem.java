package com.sammy.malum.common.item.spirit;

import com.sammy.malum.core.systems.item.IFloatingGlowItem;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler.ItemParticleSupplier;
import team.lodestar.lodestone.systems.particle.screen.LodestoneScreenParticleRenderType;
import team.lodestar.lodestone.systems.particle.screen.base.ScreenParticle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sammy.malum.client.CommonParticleEffects.spawnSpiritScreenParticles;

public class MalumSpiritItem extends Item implements IFloatingGlowItem, ItemParticleSupplier {
    public MalumSpiritType type;

    public MalumSpiritItem(Properties properties, MalumSpiritType type) {
        super(properties.rarity(type.rarity));
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(type.getFlavourComponent(pStack));
    }

    @Override
    public Color getColor() {
        return type.getColor();
    }

    @Override
    public Color getEndColor() {
        return type.getEndColor();
    }

    @Override
    public void spawnParticles(HashMap<LodestoneScreenParticleRenderType, ArrayList<ScreenParticle>> target, Level level, float partialTick, ItemStack stack, float x, float y) {
        spawnSpiritScreenParticles(target, type.getColor(), type.getEndColor(), stack, x, y);
    }
}
