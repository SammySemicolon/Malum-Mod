package com.sammy.malum.common.item.curiosities.runes;

import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.handlers.screenparticle.*;
import team.lodestar.lodestone.systems.particle.screen.*;

import java.util.*;

public class MalumRuneCurioItem extends MalumCurioItem implements ParticleEmitterHandler.ItemParticleSupplier {

    public final MalumSpiritType spiritType;
    public MalumRuneCurioItem(Properties builder, MalumSpiritType spiritType) {
        super(builder, MalumTrinketType.RUNE);
        this.spiritType = spiritType;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        final List<Component> attributesTooltip = super.getAttributesTooltip(tooltips, stack);

        final Optional<String> effectDescriptor = getEffectDescriptor();
        effectDescriptor.ifPresent(s -> {
            if (attributesTooltip.isEmpty()) {
                attributesTooltip.add(Component.empty());
                attributesTooltip.add(Component.translatable("curios.modifiers.rune")
                        .withStyle(ChatFormatting.GOLD));
            }
            attributesTooltip.add(Component.translatable("malum.gui.rune.plus")
                    .append(Component.translatable("malum.gui.rune.effect." + s)).withStyle(ChatFormatting.BLUE));
        });

        return attributesTooltip;
    }

    public Optional<String> getEffectDescriptor() {
        return Optional.empty();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        ScreenParticleEffects.spawnRuneParticles(target, spiritType);
    }
}