package com.sammy.malum.registry.common;

import com.sammy.malum.visual_effects.networked.altar.*;
import com.sammy.malum.visual_effects.networked.arcana_pylon.*;
import com.sammy.malum.visual_effects.networked.attack.*;
import com.sammy.malum.visual_effects.networked.attack.scythe.*;
import com.sammy.malum.visual_effects.networked.attack.sundering_anchor.*;
import com.sammy.malum.visual_effects.networked.attack.vindictive_brand.*;
import com.sammy.malum.visual_effects.networked.attack.vindictive_brand.unleashed.*;
import com.sammy.malum.visual_effects.networked.avarice.AvariceFortuneParticleEffect;
import com.sammy.malum.visual_effects.networked.banner.*;
import com.sammy.malum.visual_effects.networked.blight.*;
import com.sammy.malum.visual_effects.networked.brazier.*;
import com.sammy.malum.visual_effects.networked.crucible.*;
import com.sammy.malum.visual_effects.networked.cultist.*;
import com.sammy.malum.visual_effects.networked.geas.*;
import com.sammy.malum.visual_effects.networked.gluttony.*;
import com.sammy.malum.visual_effects.networked.nitrate.*;
import com.sammy.malum.visual_effects.networked.repair_pylon.*;
import com.sammy.malum.visual_effects.networked.runic_workbench.*;
import com.sammy.malum.visual_effects.networked.sap.SapCollectionParticleEffect;
import com.sammy.malum.visual_effects.networked.spirit_diode.SpiritDiodeCloseParticleEffect;
import com.sammy.malum.visual_effects.networked.spirit_diode.SpiritDiodeOpenParticleEffect;
import com.sammy.malum.visual_effects.networked.spirit_mote.*;
import com.sammy.malum.visual_effects.networked.staff.*;
import com.sammy.malum.visual_effects.networked.totem.*;
import com.sammy.malum.visual_effects.networked.weeping_well.*;
import com.sammy.malum.visual_effects.networked.wind_gust.*;

public class MalumParticleEffectTypes {

    //Blight vfx
    public static final BlightPropagationParticleEffect BLIGHT_PROPAGATION = new BlightPropagationParticleEffect("blight_propagation");
    public static final BlightPlantGrowthParticleEffect BLIGHT_PLANT_GROWTH = new BlightPlantGrowthParticleEffect("blight_plant_growth");
    public static final ScarstoneParticleEffect SCARSTONE_FORMS = new ScarstoneParticleEffect("scarstone_monument_forms");
    public static final StrangeCrystalParticleEffect STRANGE_CRYSTAL_FORMS = new StrangeCrystalParticleEffect("strange_crystal_forms");

    //Cultist vfx
    public static final AltarBlessTargetParticleEffect ALTAR_BESTOWS_BLESSING = new AltarBlessTargetParticleEffect("altar_bestows_blessing");
    public static final AltarWeaveProjectileParticleEffect ALTAR_WEAVES_PROJECTILE = new AltarWeaveProjectileParticleEffect("altar_weaves_projectile");

    public static final EntropyChargeDetonateParticleEffect ENTROPY_CHARGE_DETONATES = new EntropyChargeDetonateParticleEffect("entropy_charge_detonates");
    public static final CardinalDetonationBlastParticleEffect CARDINAL_DETONATION_BLAST = new CardinalDetonationBlastParticleEffect("cardinal_fire_detonation");
    public static final CardinalImmolationBlastParticleEffect CARDINAL_IMMOLATION_BLAST = new CardinalImmolationBlastParticleEffect("cardinal_immolation");
    public static final CardinalRetaliationBlastParticleEffect CARDINAL_RETALIATION_BLAST = new CardinalRetaliationBlastParticleEffect("cardinal_retaliation");


    //Spirit Altar
    public static final SpiritAltarCraftParticleEffect SPIRIT_ALTAR_CRAFTS = new SpiritAltarCraftParticleEffect("spirit_altar_crafts");
    public static final SpiritAltarEatItemParticleEffect SPIRIT_ALTAR_EATS_ITEM = new SpiritAltarEatItemParticleEffect("spirit_altar_eats_item");

    //Runic Workbench
    public static final RunicWorkbenchCraftRuneParticleEffect RUNIC_WORKBENCH_CRAFTS_RUNE = new RunicWorkbenchCraftRuneParticleEffect("runic_workbench_crafts_rune");
    public static final RunicWorkbenchCraftSpiritlessItemParticleEffect RUNIC_WORKBENCH_CRAFTS_SPIRITLESS_ITEM = new RunicWorkbenchCraftSpiritlessItemParticleEffect("runic_workbench_crafts_spiritless_item");

    //Arcana Pylon
    public static final ArcanaPylonEatSpiritParticleEffect ARCANA_PYLON_EATS_SPIRIT = new ArcanaPylonEatSpiritParticleEffect("arcana_pylon_eats_spirit");

    //Crucible
    public static final SpiritCrucibleCraftParticleEffect SPIRIT_CRUCIBLE_CRAFTS = new SpiritCrucibleCraftParticleEffect("spirit_crucible_crafts");
    public static final SuspiciousDevicePrimerParticleEffect SUSPICIOUS_DEVICE_PRIMER = new SuspiciousDevicePrimerParticleEffect("suspicious_device_primer");

    //Repair Pylon
    public static final PylonPrepareRepairParticleEffect REPAIR_PYLON_PREPARES = new PylonPrepareRepairParticleEffect("repair_pylon_prepare");
    public static final PylonRepairParticleEffect REPAIR_PYLON_REPAIRS = new PylonRepairParticleEffect("repair_pylon_repairs");

    //Wind Effects
    public static final WindTrailParticleEffect WIND_TRAIL = new WindTrailParticleEffect("wind_trail");

    //Totem Effects
    public static final TotemPoleActivatedParticleEffect TOTEM_POLE_ACTIVATED = new TotemPoleActivatedParticleEffect("totem_pole_activated");
    public static final EntityTotemParticleEffect ENTITY_RITE_EFFECT = new EntityTotemParticleEffect("entity_rite_effect");
    public static final BlockTotemParticleEffect BLOCK_RITE_EFFECT = new BlockTotemParticleEffect("block_rite_effect");
    public static final BlockFallTotemParticleEffect BLOCK_FALL_RITE_EFFECT = new BlockFallTotemParticleEffect("block_fall_rite_effect");
    public static final BlockInfusionTotemParticleEffect BLOCK_INFUSION_RITE_EFFECT = new BlockInfusionTotemParticleEffect("block_infusion_rite_effect");
    public static final BlockGrowTotemParticleEffect BLOCK_GROW_RITE_EFFECT = new BlockGrowTotemParticleEffect("block_grow_rite_effect");

    public static final RiteAnchorTriggerParticleEffect RITE_ANCHOR_EFFECT = new RiteAnchorTriggerParticleEffect("rite_anchor_effect");
    public static final RiteAnchorFailParticleEffect RITE_ANCHOR_FAILURE = new RiteAnchorFailParticleEffect("rite_anchor_failure");
    public static final RiteUnweaverParticleEffect RITE_UNWEAVER_EFFECT = new RiteUnweaverParticleEffect("rite_unweaver_effect");

    //Soulbinding Brazier
    public static final SoulBrazierStartParticleEffect SOULBINDING_BRAZIER_BEGINS = new SoulBrazierStartParticleEffect("brazier_start");
    public static final SoulBrazierAcceptSacrificeParticleEffect SOULBINDING_BRAZIER_ACCEPTS_SACRIFICE = new SoulBrazierAcceptSacrificeParticleEffect("brazier_sacrifice");
    public static final SoulBrazierEndParticleEffect SOULBINDING_BRAZIER_ENDS = new SoulBrazierEndParticleEffect("brazier_end");

    //Ritual Plinth
//    public static final NetworkedParticleEffectType RITUAL_PLINTH_EATS_ITEM = new RitualPlinthAbsorbItemParticleEffect("ritual_plinth_eats_item");
//    public static final NetworkedParticleEffectType RITUAL_PLINTH_EATS_SPIRIT = new RitualPlinthAbsorbSpiritParticleEffect("ritual_plinth_eats_spirit");
//    public static final NetworkedParticleEffectType RITUAL_PLINTH_BEGINS_CHARGING = new RitualPlinthBeginChargingParticleEffect("ritual_plinth_begins_charging");
//    public static final NetworkedParticleEffectType RITUAL_PLINTH_CHANGES_TIER = new RitualPlinthChangeTierParticleEffect("ritual_plinth_changes_tier");
//    public static final NetworkedParticleEffectType RITUAL_PLINTH_FAILURE = new RitualPlinthFailureParticleEffect("ritual_plinth_failure");

    //Spirit Diode
    public static final SpiritDiodeOpenParticleEffect SPIRIT_DIODE_OPEN = new SpiritDiodeOpenParticleEffect("spirit_diode_open");
    public static final SpiritDiodeCloseParticleEffect SPIRIT_DIODE_CLOSE = new SpiritDiodeCloseParticleEffect("spirit_diode_close");

    //Slash Effects
    public static final ScytheSlashParticleEffect SCYTHE_SLASH = new ScytheSlashParticleEffect("scythe_slash");
    public static final AscensionRadialSlashParticleEffect SCYTHE_ASCENSION_SPIN = new AscensionRadialSlashParticleEffect("scythe_ascension_spin");
    public static final AscensionUppercutParticleEffect SCYTHE_ASCENSION_UPPERCUT = new AscensionUppercutParticleEffect("scythe_ascension_uppercut");

    public static final TyrvingSlashParticleEffect TYRVING_SLASH = new TyrvingSlashParticleEffect("tyrving_slash");

    public static final WeightOfWorldsCritParticleEffect WEIGHT_OF_WORLDS_CRIT = new WeightOfWorldsCritParticleEffect("weight_of_worlds_crit");
    public static final EdgeOfDeliveranceCritParticleEffect EDGE_OF_DELIVERANCE_CRIT = new EdgeOfDeliveranceCritParticleEffect("weight_of_worlds_crit");

    public static final SunderingAnchorSlashParticleEffect SUNDERING_ANCHOR_SLASH = new SunderingAnchorSlashParticleEffect("sundering_anchor_slash");
    public static final SunderingAnchorSweepParticleEffect SUNDERING_ANCHOR_SWEEP = new SunderingAnchorSweepParticleEffect("sundering_anchor_sweep");

    public static final VindictiveBrandSlashParticleEffect VINDICTIVE_BRAND_SLASH = new VindictiveBrandSlashParticleEffect("vindictive_brand_slash");
    public static final VindictiveBrandUnleashedSlashParticleEffect VINDICTIVE_BRAND_UNLEASHED_SLASH = new VindictiveBrandUnleashedSlashParticleEffect("vindictive_brand_unleashed_slash");

    public static final VindictiveBrandDashCleaveParticleEffect VINDICTIVE_BRAND_DASH_CLEAVE = new VindictiveBrandDashCleaveParticleEffect("vindictive_brand_dash_cleave");
    public static final VindictiveBrandUnleashedDashCleaveParticleEffect VINDICTIVE_BRAND_UNLEASHED_DASH_CLEAVE = new VindictiveBrandUnleashedDashCleaveParticleEffect("vindictive_brand_unleashed_dash_cleave");


    public static final VindictiveBrandExtraSlashParticleEffect VINDICTIVE_BRAND_EXTRA_SLASH = new VindictiveBrandExtraSlashParticleEffect("vindictive_brand_extra_slash");


    public static final HiddenBladeCounterParticleEffect HIDDEN_BLADE_COUNTER_FLURRY = new HiddenBladeCounterParticleEffect("hidden_blade_counter_flurry");

    //Slam Effects
    public static final StaffSlamAttackParticleEffect STAFF_SLAM = new StaffSlamAttackParticleEffect("staff_slam");
    public static final BludgeonAttackParticleEffect BLUDGEON_SLAM = new BludgeonAttackParticleEffect("bludgeon_slam");

    //Wand
    public static final BoltImpactParticleEffect HEX_BOLT_IMPACT = new HexBoltImpactParticleEffect("hex_bolt_impact");
    public static final BoltImpactParticleEffect DRAINING_BOLT_IMPACT = new DrainingBoltImpactParticleEffect("draining_bolt_impact");
    public static final BoltImpactParticleEffect ENTROPIC_BOLT_IMPACT = new EntropicBoltImpactParticleEffect("entropic_bolt_impact");
    public static final UnwindingChaosChargeParticleEffect UNWINDING_CHAOS_CHARGE = new UnwindingChaosChargeParticleEffect("unwinding_chaos_charge");

    //Geas
    public static final LifeweaverHealingBeamParticleEffect HEALING_BEAM = new LifeweaverHealingBeamParticleEffect("healing_beam");
    public static final WarlockSpiritImpactParticleEffect WARLOCK_IMPACT = new WarlockSpiritImpactParticleEffect("spirit_impact");
    public static final BerserkerSpiritImpactParticleEffect BERSERKER_IMPACT = new BerserkerSpiritImpactParticleEffect("berserker_impact");
    public static final HighPriestPenanceParticleEffect HIGH_PRIEST_PENANCE = new HighPriestPenanceParticleEffect("high_priest_penance");
    public static final PatienceRepaidParticleEffect PATIENCE_REPAID = new PatienceRepaidParticleEffect("patience_repaid");
    public static final ProspectorsGreedBurnParticleEffect PROSPECTORS_STREAK_BURN = new ProspectorsGreedBurnParticleEffect("prospectors_streak_burn");
    public static final CombustionBurnParticleEffect COMBUSTION_BURN = new CombustionBurnParticleEffect("combustion_burn");
    public static final WyrdReconstructionReviveParticleEffect WYRD_RECONSTRUCTION_REVIVE = new WyrdReconstructionReviveParticleEffect("wyrd_reconstruction_revive");
    public static final InvertedHeartGeasImpactParticleEffect INVERTED_HEART_IMPACT = new InvertedHeartGeasImpactParticleEffect("inverted_heart_impact");

    //Gluttony
    public static final ThrownGluttonyParticleEffect THROWN_GLUTTONY_IMPACT = new ThrownGluttonyParticleEffect("splash_of_gluttony");
    public static final AbsorbGluttonyParticleEffect GLUTTONY_ABSORB = new AbsorbGluttonyParticleEffect("gluttony_absorbed");

    //Avarice
    public static final AvariceFortuneParticleEffect AVARICE_FORTUNE_EFFECT = new AvariceFortuneParticleEffect("avarice_fortune");

    //Nitrate
    public static final EthericNitrateImpactParticleEffect ETHERIC_NITRATE_IMPACT = new EthericNitrateImpactParticleEffect("etheric_nitrate_impact");

    //Void vfx
    public static final WeepingWellReactionParticleEffect WEEPING_WELL_REACTS = new WeepingWellReactionParticleEffect("weeping_well_reacts");

    //Misc vfx

    //Banner vfx
    public static final SoulwovenBannerApplyParticleEffect APPLY_SOULWOVEN_BANNER_GLOW = new SoulwovenBannerApplyParticleEffect("apply_soulwoven_banner_glow");
    public static final SoulwovenBannerRemoveParticleEffect REMOVE_SOULWOVEN_BANNER_GLOW = new SoulwovenBannerRemoveParticleEffect("remove_soulwoven_banner_glow");

    public static final SpiritMotePlaceParticleEffect SPIRIT_MOTE_SPARKLES = new SpiritMotePlaceParticleEffect("spirit_mote_sparkles");
    public static final SapCollectionParticleEffect SAP_COLLECTED = new SapCollectionParticleEffect("sap_collected");

    public static void init() {

    }
}
