package com.sammy.malum.registry.common;

import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.SapCollectionParticleEffect;
import com.sammy.malum.visual_effects.networked.SpiritMoteCreationParticleEffect;
import com.sammy.malum.visual_effects.networked.altar.SpiritAltarCraftParticleEffect;
import com.sammy.malum.visual_effects.networked.altar.SpiritAltarEatItemParticleEffect;
import com.sammy.malum.visual_effects.networked.blight.BlightingMistParticleEffect;
import com.sammy.malum.visual_effects.networked.crucible.SpiritCrucibleCraftParticleEffect;
import com.sammy.malum.visual_effects.networked.generic.DrippingSmokeParticleEffect;
import com.sammy.malum.visual_effects.networked.generic.HexingSmokeParticleEffect;
import com.sammy.malum.visual_effects.networked.generic.RisingSparklesParticleEffect;
import com.sammy.malum.visual_effects.networked.gluttony.ThrownGluttonyParticleEffect;
import com.sammy.malum.visual_effects.networked.nitrate.EthericNitrateImpactParticleEffect;
import com.sammy.malum.visual_effects.networked.pylon.PylonPrepareRepairParticleEffect;
import com.sammy.malum.visual_effects.networked.pylon.PylonRepairParticleEffect;
import com.sammy.malum.visual_effects.networked.ritual.*;
import com.sammy.malum.visual_effects.networked.staff.AuricBoltImpactParticleEffect;
import com.sammy.malum.visual_effects.networked.staff.DrainingBoltImpactParticleEffect;
import com.sammy.malum.visual_effects.networked.staff.HexBoltImpactParticleEffect;
import com.sammy.malum.visual_effects.networked.totem.TotemPoleActivatedParticleEffect;
import com.sammy.malum.visual_effects.networked.weeping_well.WeepingWellRadianceParticleEffect;
import com.sammy.malum.visual_effects.networked.weeping_well.WeepingWellReactionParticleEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

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

    public static final ParticleEffectType SPIRIT_CRUCIBLE_CRAFTS = new SpiritCrucibleCraftParticleEffect("spirit_crucible_crafts");

    public static final ParticleEffectType REPAIR_PYLON_PREPARES = new PylonPrepareRepairParticleEffect("repair_pylon_prepare");
    public static final ParticleEffectType REPAIR_PYLON_REPAIRS = new PylonRepairParticleEffect("repair_pylon_repairs");

    public static final ParticleEffectType TOTEM_POLE_ACTIVATED = new TotemPoleActivatedParticleEffect("totem_pole_activated");

    public static final ParticleEffectType RITUAL_PLINTH_EATS_ITEM = new RitualPlinthAbsorbItemParticleEffect("ritual_plinth_eats_item");
    public static final ParticleEffectType RITUAL_PLINTH_EATS_SPIRIT = new RitualPlinthAbsorbSpiritParticleEffect("ritual_plinth_eats_spirit");
    public static final ParticleEffectType RITUAL_PLINTH_BEGINS_CHARGING = new RitualPlinthBeginChargingParticleEffect("ritual_plinth_begins_charging");
    public static final ParticleEffectType RITUAL_PLINTH_CHANGES_TIER = new RitualPlinthChangeTierParticleEffect("ritual_plinth_changes_tier");
    public static final ParticleEffectType RITUAL_PLINTH_FAILURE = new RitualPlinthFailureParticleEffect("ritual_plinth_failure");

    //Wand
    public static final ParticleEffectType HEX_BOLT_IMPACT = new HexBoltImpactParticleEffect("hex_bolt_impact");
    public static final ParticleEffectType DRAINING_BOLT_IMPACT = new DrainingBoltImpactParticleEffect("draining_bolt_impact");
    public static final ParticleEffectType AURIC_BOLT_IMPACT = new AuricBoltImpactParticleEffect("auric_bolt_impact");

    //Gluttony
    public static final ParticleEffectType THROWN_GLUTTONY_IMPACT = new ThrownGluttonyParticleEffect("thrown_gluttony_potion_lands");

    //Nitrate
    public static final ParticleEffectType ETHERIC_NITRATE_IMPACT = new EthericNitrateImpactParticleEffect("etheric_nitrate_impact");

    //Void vfx
    public static final ParticleEffectType WEEPING_WELL_REACTS = new WeepingWellReactionParticleEffect("weeping_well_reacts");
    public static final ParticleEffectType WEEPING_WELL_EMITS_RADIANCE = new WeepingWellRadianceParticleEffect("weeping_well_emits_radiance");

    //Misc vfx
    public static final ParticleEffectType SPIRIT_MOTE_SPARKLES = new SpiritMoteCreationParticleEffect("spirit_mote_sparkles");
    public static final ParticleEffectType SAP_COLLECTED = new SapCollectionParticleEffect("sap_collected");

}
