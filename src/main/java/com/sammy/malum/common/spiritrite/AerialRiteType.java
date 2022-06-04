package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.setup.content.potion.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.rites.PotionRiteEffect;
import net.minecraft.world.entity.player.Player;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.AERIAL_SPIRIT;
import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.ARCANE_SPIRIT;

public class AerialRiteType extends MalumRiteType {
    public AerialRiteType() {
        super("aerial_rite", ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(Player.class, EffectRegistry.AERIAL_AURA, AERIAL_SPIRIT);
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(Player.class, EffectRegistry.CORRUPTED_AERIAL_AURA, AERIAL_SPIRIT);
    }
}