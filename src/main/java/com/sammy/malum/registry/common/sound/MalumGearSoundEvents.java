package com.sammy.malum.registry.common.sound;

import net.minecraft.sounds.*;
import net.neoforged.neoforge.registries.*;

public class MalumGearSoundEvents {

    public static void init() {

    }

    //Scythe
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_SWEEP = MalumSoundEvents.registerVariable("scythe_sweeps");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_CUT = MalumSoundEvents.registerVariable("scythe_cuts");

    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_ASCENSION = MalumSoundEvents.registerVariable("scythe_user_ascends");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_ASCENSION_LAUNCH = MalumSoundEvents.registerVariable("scythe_target_is_launched");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_THROW = MalumSoundEvents.registerVariable("scythe_thrown");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_SPINS = MalumSoundEvents.registerFixed("scythe_spins_happily", 32f);
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_CATCH = MalumSoundEvents.registerVariable("scythe_caught");

    //Staff
    public static final DeferredHolder<SoundEvent, SoundEvent> STAFF_FIRES = MalumSoundEvents.registerVariable("staff_fires");
    public static final DeferredHolder<SoundEvent, SoundEvent> STAFF_POWERS_UP = MalumSoundEvents.registerVariable("staff_powers_up");
    public static final DeferredHolder<SoundEvent, SoundEvent> STAFF_SIZZLES_OUT = MalumSoundEvents.registerVariable("staff_sizzles_out");
    public static final DeferredHolder<SoundEvent, SoundEvent> STAFF_CHARGED = MalumSoundEvents.registerVariable("staff_charged");
    public static final DeferredHolder<SoundEvent, SoundEvent> STAFF_STRIKES = MalumSoundEvents.registerVariable("staff_strikes");

    //Spellweaving Tools
    public static final DeferredHolder<SoundEvent, SoundEvent> SPELLWEAVING_TOOL_PRIME = MalumSoundEvents.registerVariable("spellweaving_tool_primed");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPELLWEAVING_TOOL_DAMPEN = MalumSoundEvents.registerVariable("spellweaving_tool_dampened");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPELLWOVEN_SPRITE_SPAWN = MalumSoundEvents.registerVariable("spellwoven_sprite_spawns");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPELLWOVEN_SPRITE_HARVESTS = MalumSoundEvents.registerVariable("spellwoven_sprite_harvests");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPELLWOVEN_SPRITE_RETURNS = MalumSoundEvents.registerVariable("spellwoven_sprite_returns");

    //Trinkets
    public static final DeferredHolder<SoundEvent, SoundEvent> HUNGRY_BELT_FEEDS = MalumSoundEvents.registerVariable("hungry_belt_feeds");
    public static final DeferredHolder<SoundEvent, SoundEvent> VORACIOUS_RING_FEEDS = MalumSoundEvents.registerVariable("voracious_ring_feeds");
    public static final DeferredHolder<SoundEvent, SoundEvent> GRUESOME_RING_FEEDS = MalumSoundEvents.registerVariable("gruesome_ring_feeds");
    public static final DeferredHolder<SoundEvent, SoundEvent> FLESH_RING_ABSORBS = MalumSoundEvents.registerVariable("flesh_ring_absorbs");
    public static final DeferredHolder<SoundEvent, SoundEvent> ECHOING_RING_ABSORBS = MalumSoundEvents.registerVariable("echoing_ring_absorbs");

    //Hidden Blade
    public static final DeferredHolder<SoundEvent, SoundEvent> HIDDEN_BLADE_CHARGED = MalumSoundEvents.registerVariable("hidden_blade_charged");
    public static final DeferredHolder<SoundEvent, SoundEvent> HIDDEN_BLADE_PRIMED = MalumSoundEvents.registerVariable("hidden_blade_primed");
    public static final DeferredHolder<SoundEvent, SoundEvent> HIDDEN_BLADE_DISRUPTED = MalumSoundEvents.registerVariable("hidden_blade_disrupted");
    public static final DeferredHolder<SoundEvent, SoundEvent> HIDDEN_BLADE_UNLEASHED = MalumSoundEvents.registerVariable("hidden_blade_unleashed");

    //Gluttony
    public static final DeferredHolder<SoundEvent, SoundEvent> CONCENTRATED_GLUTTONY_DRINK = MalumSoundEvents.registerVariable("concentrated_gluttony_drink");
    public static final DeferredHolder<SoundEvent, SoundEvent> RAVENOUS_SCYTHE_EATS = MalumSoundEvents.registerVariable("ravenous_scythe_eats");
    public static final DeferredHolder<SoundEvent, SoundEvent> GLUTTONOUS_BLUDGEON_SPROUTS = MalumSoundEvents.registerVariable("gluttonous_bludgeon_sprouts");

    //Misc
    public static final DeferredHolder<SoundEvent, SoundEvent> TYRVING_SLASH = MalumSoundEvents.registerVariable("tyrving_slashes_twice");

    //Malignant Gear
    public static final DeferredHolder<SoundEvent, SoundEvent> WEIGHT_OF_WORLDS_CUT = MalumSoundEvents.registerVariable("weight_of_worlds_cuts");

    public static final DeferredHolder<SoundEvent, SoundEvent> EDGE_OF_DELIVERANCE_SWEEP = MalumSoundEvents.registerVariable("edge_of_deliverance_sweeps");
    public static final DeferredHolder<SoundEvent, SoundEvent> EDGE_OF_DELIVERANCE_CUT = MalumSoundEvents.registerVariable("edge_of_deliverance_cuts");

    public static final DeferredHolder<SoundEvent, SoundEvent> MALIGNANT_METAL_CRIT = MalumSoundEvents.registerVariable("malignant_deliverance_triggers");
    public static final DeferredHolder<SoundEvent, SoundEvent> MALIGNANT_METAL_COMBO = MalumSoundEvents.registerVariable("malignant_deliverance_strikes_again");

    //Sundering Anchor
    public static final DeferredHolder<SoundEvent, SoundEvent> SUNDERING_ANCHOR_SWING = MalumSoundEvents.registerVariable("sundering_anchor_cuts");
    public static final DeferredHolder<SoundEvent, SoundEvent> SUNDERING_ANCHOR_EXTRA_SWING = MalumSoundEvents.registerVariable("sundering_anchor_twists_gracefully");
    public static final DeferredHolder<SoundEvent, SoundEvent> SUNDERING_ANCHOR_PROJECTILE_SWING = MalumSoundEvents.registerVariable("sundering_anchor_lashes_out_ecstatically");
    public static final DeferredHolder<SoundEvent, SoundEvent> SUNDERING_ANCHOR_THROW = MalumSoundEvents.registerVariable("sundering_anchor_leaps_out_eagerly");
    public static final DeferredHolder<SoundEvent, SoundEvent> SUNDERING_ANCHOR_CATCH = MalumSoundEvents.registerVariable("sundering_anchor_returns_with_euphoria");


    //Vindictive Brand
    public static final DeferredHolder<SoundEvent, SoundEvent> VINDICTIVE_BRAND_SWING = MalumSoundEvents.registerVariable("vindictive_brand_swings");
    public static final DeferredHolder<SoundEvent, SoundEvent> VINDICTIVE_BRAND_UNLEASHED_SWING = MalumSoundEvents.registerVariable("vindictive_brand_outright_devastates");
    public static final DeferredHolder<SoundEvent, SoundEvent> VINDICTIVE_BRAND_EXTRA_SWING = MalumSoundEvents.registerVariable("vindictive_brand_reverberates");
    public static final DeferredHolder<SoundEvent, SoundEvent> VINDICTIVE_BRAND_SPROUT_RESENTMENT = MalumSoundEvents.registerVariable("vindictive_brand_sprouts_resentment");
    public static final DeferredHolder<SoundEvent, SoundEvent> VINDICTIVE_BRAND_PROGRESS_RITUAL = MalumSoundEvents.registerVariable("vindictive_brand_progresses_the_ritual");

    public static final DeferredHolder<SoundEvent, SoundEvent> VINDICTIVE_BRAND_DASH = MalumSoundEvents.registerVariable("vindictive_brand_user_lunges_forward");
    public static final DeferredHolder<SoundEvent, SoundEvent> VINDICTIVE_BRAND_DASH_CLEAVE = MalumSoundEvents.registerVariable("vindictive_brand_cleaves_the_world");
    public static final DeferredHolder<SoundEvent, SoundEvent> VINDICTIVE_BRAND_UNLEASHED_DASH_CLEAVE = MalumSoundEvents.registerVariable("vindictive_brand_erases_the_world");


    //Catalyst Lobber
    public static final DeferredHolder<SoundEvent, SoundEvent> CATALYST_LOBBER_UNLOCKED = MalumSoundEvents.registerVariable("catalyst_lobber_unlocked");
    public static final DeferredHolder<SoundEvent, SoundEvent> CATALYST_LOBBER_LOCKED = MalumSoundEvents.registerVariable("catalyst_lobber_locked");
    public static final DeferredHolder<SoundEvent, SoundEvent> CATALYST_LOBBER_PRIMED = MalumSoundEvents.registerVariable("catalyst_lobber_primed");
    public static final DeferredHolder<SoundEvent, SoundEvent> CATALYST_LOBBER_FIRED = MalumSoundEvents.registerVariable("catalyst_lobber_fired");

    //Misc Motifs
    public static final DeferredHolder<SoundEvent, SoundEvent> DRAINING_MOTIF = MalumSoundEvents.registerVariable("the_sound_of_silence");
    public static final DeferredHolder<SoundEvent, SoundEvent> WORLDSOUL_MOTIF_LIGHT_IMPACT = MalumSoundEvents.registerVariable("the_worldsoul_shifts_slightly");
    public static final DeferredHolder<SoundEvent, SoundEvent> WORLDSOUL_MOTIF_HEAVY_IMPACT = MalumSoundEvents.registerVariable("the_worldsoul_shifts_erratically");
    public static final DeferredHolder<SoundEvent, SoundEvent> WORLDSOUL_MOTIF_REVERB = MalumSoundEvents.registerVariable("the_worldsoul_reverberates");
}
