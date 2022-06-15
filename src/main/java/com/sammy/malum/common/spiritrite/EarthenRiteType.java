package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.setup.content.potion.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteEffect;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.rites.PotionRiteEffect;
import net.minecraft.world.entity.player.Player;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;

public class EarthenRiteType extends MalumRiteType {
    public EarthenRiteType() {
        super("earthen_rite", ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(Player.class, EffectRegistry.GAIAN_BULWARK, EARTHEN_SPIRIT);
    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(Player.class, EffectRegistry.EARTHEN_MIGHT, EARTHEN_SPIRIT);
    }
}
