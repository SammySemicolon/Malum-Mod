package com.sammy.malum.datagen.sound;

import static com.sammy.malum.registry.common.sound.MalumGearSoundEvents.*;
import static team.lodestar.lodestone.modules.datagen.providers.sound.LodestoneSoundEventSystem.sound;

public class MalumGearSoundDatagen extends MalumSoundDatagenWrapper {

    public MalumGearSoundDatagen(MalumSoundDatagen soundDatagen) {
        super(soundDatagen);
    }

    @Override
    public void registerSounds() {
        //Scythe
        add(SCYTHE_SWEEP, s -> s.with(allSounds("curiosities/gear/weapons/scythe/sweep")));
        add(SCYTHE_CUT, s -> s.with(allSounds("curiosities/gear/weapons/scythe/sweep")));

        add(SCYTHE_ASCENSION, s -> s.with(allSounds("curiosities/gear/weapons/scythe/throw")));
        add(SCYTHE_ASCENSION_LAUNCH, s -> s.with(allSounds("minecraft:entity/wind_charge/wind_burst")));
        add(SCYTHE_THROW, s -> s.with(allSounds("curiosities/gear/weapons/scythe/throw")));
        add(SCYTHE_SPINS, s -> s.with(sound("curiosities/gear/weapons/scythe/spin")));
        add(SCYTHE_CATCH, s -> s.with(allSounds("curiosities/gear/weapons/scythe/catch")));

        //Staff
        add(STAFF_FIRES, s -> s.with(allSounds("curiosities/gear/weapons/staff/fire")));
        add(STAFF_POWERS_UP, s -> s.with(allSounds("curiosities/gear/weapons/staff/power_up")));
        add(STAFF_SIZZLES_OUT, s -> s.with(allSounds("curiosities/gear/weapons/staff/sizzle")));
        add(STAFF_CHARGED, s -> s.with(allSounds("curiosities/gear/weapons/staff/charge")));
        add(STAFF_STRIKES, s -> s.with(allSounds("curiosities/gear/weapons/staff/hit")));

        //Spellweaving Tools
        add(SPELLWEAVING_TOOL_PRIME, s -> s.with(allSounds("curiosities/spellweaving/prime_tool")));
        add(SPELLWEAVING_TOOL_DAMPEN, s -> s.with(allSounds("curiosities/spellweaving/dampen_tool")));
        add(SPELLWOVEN_SPRITE_SPAWN, s -> s.with(allSounds("curiosities/spellweaving/spell_spawn")));
        add(SPELLWOVEN_SPRITE_HARVESTS, s -> s.with(allSounds("curiosities/spellweaving/spell_mine")));
        add(SPELLWOVEN_SPRITE_RETURNS, s -> s.with(allSounds("curiosities/spellweaving/spell_vanish")));

        //Trinkets
        add(HUNGRY_BELT_FEEDS, s -> s.with(allSounds("curiosities/gear/trinkets/starved/nom")));
        add(VORACIOUS_RING_FEEDS, s -> s.with(allSounds("curiosities/gear/trinkets/starved/nom")));
        add(GRUESOME_RING_FEEDS, s -> s.with(allSounds("curiosities/gear/trinkets/starved/nom")));
        add(FLESH_RING_ABSORBS, s -> s.with(allSounds("curiosities/gear/trinkets/cancer_ring/grow")));
        add(ECHOING_RING_ABSORBS, s -> s.with(allSounds("curiosities/gear/trinkets/cancer_ring/grow"))); // TODO: unique sound

        //Hidden Blade
        add(HIDDEN_BLADE_CHARGED, s -> s.with(sound("curiosities/gear/trinkets/hidden_blade/charge")));
        add(HIDDEN_BLADE_PRIMED, s -> s.with(sound("curiosities/gear/trinkets/hidden_blade/charge")));
        add(HIDDEN_BLADE_DISRUPTED, s -> s.with(sound("curiosities/gear/trinkets/hidden_blade/charge")));
        add(HIDDEN_BLADE_UNLEASHED, s -> s.with(allSounds("curiosities/gear/trinkets/hidden_blade/flurry")));

        //Gluttony
        add(CONCENTRATED_GLUTTONY_DRINK, s -> s.with(allSounds("curiosities/gear/concentrated_gluttony/drink")));
        add(RAVENOUS_SCYTHE_EATS, s -> s.with(allSounds("curiosities/gear/weapons/ravenous/scythe_hit")));
        add(GLUTTONOUS_BLUDGEON_SPROUTS, s -> s.with(allSounds("curiosities/gear/weapons/ravenous/bludgeon_hit")));

        //Misc
        add(TYRVING_SLASH, s -> s.with(allSounds("curiosities/gear/weapons/tyrving/hit")));

        //Malignant Gear
        add(WEIGHT_OF_WORLDS_CUT, s -> s.with(allSounds("curiosities/gear/weapons/scythe/sweep")));

        add(EDGE_OF_DELIVERANCE_SWEEP, s -> s.with(allSounds("curiosities/gear/weapons/scythe/sweep")));
        add(EDGE_OF_DELIVERANCE_CUT, s -> s.with(allSounds("curiosities/gear/weapons/scythe/sweep")));

        add(MALIGNANT_METAL_CRIT, s -> s.with(allSounds("curiosities/motifs/malignant_crit")));
        add(MALIGNANT_METAL_COMBO, s -> s.with(allSounds("curiosities/motifs/malignant_crit")));

        //Sundering Anchor
        add(SUNDERING_ANCHOR_SWING, s -> s.with(allSounds("curiosities/gear/weapons/sundering_anchor/swing")));
        add(SUNDERING_ANCHOR_EXTRA_SWING, s -> s.with(allSounds("curiosities/gear/weapons/sundering_anchor/extra_swing")));
        add(SUNDERING_ANCHOR_PROJECTILE_SWING, s -> s.with(allSounds("curiosities/gear/weapons/sundering_anchor/extra_swing")));
        add(SUNDERING_ANCHOR_THROW, s -> s.with(allSounds("curiosities/gear/weapons/scythe/throw")));
        add(SUNDERING_ANCHOR_CATCH, s -> s.with(allSounds("curiosities/gear/weapons/scythe/catch")));

        //Vindictive Brand
        add(VINDICTIVE_BRAND_SWING, s -> s.with(allSounds("curiosities/gear/weapons/vindictive_brand/swing")));
        add(VINDICTIVE_BRAND_UNLEASHED_SWING, s -> s.with(allSounds("curiosities/gear/weapons/vindictive_brand/unleashed_swing")));
        add(VINDICTIVE_BRAND_EXTRA_SWING, s -> s.with(allSounds("curiosities/gear/weapons/vindictive_brand/extra_swing")));
        add(VINDICTIVE_BRAND_SPROUT_RESENTMENT, s -> s.with(allSounds("curiosities/gear/weapons/vindictive_brand/sprout")));
        add(VINDICTIVE_BRAND_PROGRESS_RITUAL, s -> s.with(allSounds("curiosities/gear/weapons/vindictive_brand/ritual")));
        add(VINDICTIVE_BRAND_DASH, s -> s.with(allSounds("curiosities/gear/weapons/vindictive_brand/dash")));
        add(VINDICTIVE_BRAND_DASH_CLEAVE, s -> s.with(allSounds("curiosities/gear/weapons/vindictive_brand/dash_cleave")));
        add(VINDICTIVE_BRAND_UNLEASHED_DASH_CLEAVE, s -> s.with(allSounds("curiosities/gear/weapons/vindictive_brand/unleashed_dash_cleave")));



        //Catalyst Lobber
        add(CATALYST_LOBBER_UNLOCKED, s -> s.with(allSounds("curiosities/gear/catalyst_lobber/open")));
        add(CATALYST_LOBBER_LOCKED, s -> s.with(allSounds("curiosities/gear/catalyst_lobber/open")));
        add(CATALYST_LOBBER_PRIMED, s -> s.with(allSounds("curiosities/gear/catalyst_lobber/load")));
        add(CATALYST_LOBBER_FIRED, s -> s.with(allSounds("curiosities/gear/catalyst_lobber/fire")));


        //Misc Motifs
        add(DRAINING_MOTIF, s -> s.with(allSounds("curiosities/motifs/draining_bubbling")));
        add(WORLDSOUL_MOTIF_LIGHT_IMPACT, s -> s.with(allSounds("curiosities/motifs/worldsoul_minor_impact")));
        add(WORLDSOUL_MOTIF_HEAVY_IMPACT, s -> s.with(allSounds("curiosities/motifs/worldsoul_impact")));
        add(WORLDSOUL_MOTIF_REVERB, s -> s.with(allSounds("curiosities/motifs/worldsoul_reverb")));
    }
}
