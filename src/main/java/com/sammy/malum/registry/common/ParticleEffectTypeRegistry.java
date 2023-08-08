package com.sammy.malum.registry.common;

import com.sammy.malum.client.effects.*;
import com.sammy.malum.client.effects.basic.*;
import com.sammy.malum.core.systems.particle_effects.*;

import java.util.*;

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

    //Void vfx
    public static final ParticleEffectType WEEPING_WELL_REACTS = new WeepingWellReactionParticleEffect("weeping_well_reacts");

    public static final ParticleEffectType SPIRIT_MOTE_SPARKLES = new SpiritMoteCreationParticleEffect("spirit_mote_sparkles");

}
