package com.sammy.malum.registry.common;

import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.SpiritMoteCreationParticleEffect;
import com.sammy.malum.visual_effects.networked.altar.SpiritAltarCraftParticleEffect;
import com.sammy.malum.visual_effects.networked.altar.SpiritAltarEatItemParticleEffect;
import com.sammy.malum.visual_effects.networked.blight.BlightingMistParticleEffect;
import com.sammy.malum.visual_effects.networked.generic.DrippingSmokeParticleEffect;
import com.sammy.malum.visual_effects.networked.generic.HexingSmokeParticleEffect;
import com.sammy.malum.visual_effects.networked.generic.RisingSparklesParticleEffect;
import com.sammy.malum.visual_effects.networked.weeping_well.WeepingWellReactionParticleEffect;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParticleEffectTypeRegistry {

    public static final Map<String, ParticleEffectType> EFFECT_TYPES = new LinkedHashMap<>();

    //Generic, all-purpose effect application vfx
    public static final ParticleEffectType HEXING_SMOKE = new HexingSmokeParticleEffect("minor_hexing_smoke", 1);
    public static final ParticleEffectType MAJOR_HEXING_SMOKE = new HexingSmokeParticleEffect("major_hexing_smoke", 1.75f);
    public static final ParticleEffectType DRIPPING_SMOKE = new DrippingSmokeParticleEffect("dripping_smoke", 1.25f);

    public static final ParticleEffectType CONCENTRATED_RISING_SPARKLES = new RisingSparklesParticleEffect("concentrated_rising_sparkles");

    //Blight vfx
    public static final ParticleEffectType BLIGHTING_MIST = new BlightingMistParticleEffect("blighting_mist");

    //Functional vfx
    public static final ParticleEffectType SPIRIT_ALTAR_CRAFTS = new SpiritAltarCraftParticleEffect("spirit_altar_crafts");
    public static final ParticleEffectType SPIRIT_ALTAR_EATS_ITEM = new SpiritAltarEatItemParticleEffect("spirit_altar_eats_item");

    //Void vfx
    public static final ParticleEffectType WEEPING_WELL_REACTS = new WeepingWellReactionParticleEffect("weeping_well_reacts");
    public static final ParticleEffectType WEEPING_WELL_EMITS_RADIANCE = new WeepingWellRadianceParticleEffect("weeping_well_emits_radiance");

    public static final ParticleEffectType SPIRIT_MOTE_SPARKLES = new SpiritMoteCreationParticleEffect("spirit_mote_sparkles");

}
