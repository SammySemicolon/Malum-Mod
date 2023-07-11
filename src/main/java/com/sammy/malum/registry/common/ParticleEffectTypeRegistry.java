package com.sammy.malum.registry.common;

import com.sammy.malum.client.vfx.*;
import com.sammy.malum.client.vfx.types.*;

import java.util.*;

public class ParticleEffectTypeRegistry {

    public static final Map<String, ParticleEffectType> EFFECT_TYPES = new LinkedHashMap<>();

    //Generic, all-purpose effect application vfx
    public static final ParticleEffectType HEXING_SMOKE = new HexingSmokeParticleEffectType("minor_hexing_smoke", 1);
    public static final ParticleEffectType MAJOR_HEXING_SMOKE = new HexingSmokeParticleEffectType("major_hexing_smoke", 1.75f);

    //Dripstone ritual vfx
    public static final ParticleEffectType DRIPPING_SMOKE = new DrippingSmokeParticleEffectType("dripping_smoke", 1.25f);

    //Blight vfx
    public static final ParticleEffectType BLIGHTING_MIST = new BlightingMistParticleEffectType("blighting_mist");

    //Void vfx
    public static final ParticleEffectType WEEPING_WELL_REACTS = new WeepingWellReactionParticleEffectType("weeping_well_reacts");

    public static final ParticleEffectType SPIRIT_MOTE_SPARKLES = new SpiritMoteCreationParticleEffectType("spirit_mote_sparkles");

}
