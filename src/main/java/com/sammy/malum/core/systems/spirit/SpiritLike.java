package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.color.ColorParticleDataBuilder;

import javax.annotation.*;
import java.awt.*;

public interface SpiritLike {

    @Nonnull
    SpiritArcanaType getSpirit();

    default boolean matches(SpiritLike other) {
        return getSpirit().equals(other.getSpirit());
    }

    default Holder<SpiritArcanaType> getHolder() {
        return MalumSpiritTypes.SPIRIT_TYPES_REGISTRY.getHolder(getRegistryName()).orElseThrow();
    }

    default ResourceLocation getRegistryName() {
        return MalumSpiritTypes.SPIRIT_TYPES_REGISTRY.getKey(getSpirit());
    }

    default SpiritShardItem getSpiritShard() {
        return getSpirit().getSpiritShard();
    }

    default ItemStack getSpiritStack() {
        return getSpiritShard().getDefaultInstance();
    }

    default ItemStack getSpiritStack(int count) {
        return new ItemStack(getSpiritShard(), count);
    }

    default float getAlphaMultiplier() {
        return getSpirit().getColorProperties().alphaMultiplier();
    }

    default Color getPrimaryColor() {
        return getSpirit().getColorProperties().primaryColor();
    }

    default Color getSecondaryColor() {
        return getSpirit().getColorProperties().secondaryColor();
    }

    default Color getItemColor() {
        return getSpirit().getColorProperties().itemColor();
    }

    default ColorParticleDataBuilder createColorData() {
        return createColorData(1f);
    }

    default ColorParticleDataBuilder createColorData(float coefficientMultiplier) {
        return getSpirit().getColorProperties().createColorData(coefficientMultiplier);
    }
}