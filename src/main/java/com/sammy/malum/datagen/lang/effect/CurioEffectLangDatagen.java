package com.sammy.malum.datagen.lang.effect;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.datagen.lang.*;

public class CurioEffectLangDatagen {

    public static void addTranslations() {

        addCurioEffect("scythe_execution", "Scythes Exploit Wounds");
        addCurioEffect("crits", "Critical Strikes");
        addCurioEffect("low_health_speed", "Speed at Low Health");
        addCurioEffect("shorten_negative_effect", "Shortens Negative Effects");
        addCurioEffect("burning_damage", "Burning Damage");

        addCurioEffect("scythe_chain", "Deadlier Scythe Sweeping");
        addCurioEffect("silence", "Silences Attackers");
        addCurioEffect("always_sprint", "Sprinting Always Available");
        addCurioEffect("extend_positive_effect", "Extends Positive Effects");
        addCurioEffect("burning_resistance", "Damage Resistance When Ablaze");

        addCurioEffect("totem_effect", "Grants %s");

        addCurioEffect("friendly_enemies", "Reduces Enemy Aggression");

        addCurioEffect("spirits_heal", "Spirit Collection Replenishes Lost Health");
        addCurioEffect("spirits_extend_effect", "Spirit Collection Aids Potion Durations");
        addCurioEffect("spirits_weave_mana", "Spirit Collection Recovers Lost Soul Ward");
        addCurioEffect("spirits_xp", "Spirit Collection Brings Experience Points");

        addCurioEffect("hunger_drain", "Actively Drains Hunger");
        addCurioEffect("spirits_gluttony", "Spirit Collection Generates Gluttony");
        addCurioEffect("eat_rotten", "Rotten Foods are Tastier");
        addCurioEffect("rotten_gluttony", "Eating Rotten Food Generates Gluttony");
        addCurioEffect("rot_multiplicity", "Sprout More Gluttony Locusts");
        addCurioEffect("rot_aggression", "Gluttony Locusts Seek Out Wicked Targets");

        addCurioEffect("ore_prospecting", "Collecting Precious Minerals Bestows Avarice");
        addCurioEffect("enchanted_explosions", "Explosions Are Enchanted With %s");
        addCurioEffect("avarice_healing", "Avarice Recovers Health And Hunger");
        addCurioEffect("bigger_explosions", "Enhances Explosion Blast Radius");

        addCurioEffect("windweaver_ascension", "Wind Gusts Bestow Ascension");
        addCurioEffect("windweaver_gliding", "Ascension Enables Air-Strafe Movement");
        addCurioEffect("tidebound_resilience", "Reduces Damage Taken While Soaked");
        addCurioEffect("tidebound_agility", "Passively Recovers Health While Submerged");
        addCurioEffect("tidebound_conduit", "Listed Effects Become Empowered Through Conduit Influence");
        addCurioEffect("inoculation_effect_duration", "Improves Potion Effect Durations");
        addCurioEffect("inoculation_effect_buff", "Potion Effects Provide Additional Benefits");

        addCurioEffect("no_sweep", "Disables Scythe Sweeping");
        addCurioEffect("enhanced_maneuvers", "Augments Rebound and Ascension");
        addCurioEffect("ascension_launch", "Ascension Launches Targets Upwards");
        addCurioEffect("lower_ascension_damage", "Ascension Suffers a Damage Penalty");
        addCurioEffect("rebound_maelstrom", "Rebound Creates A Windborne Maelstrom");
        addCurioEffect("longer_rebound_cooldown", "Rebound Suffers a Longer Cooldown");
        addCurioEffect("scythe_counterattack", "Powerful Scythe Counterattack When Struck");
        addCurioEffect("pacifist_recharge", "Cooldown Extends if the Scythe is Used");

        addCurioEffect("soul_ward_magic_resilience", "Soul Ward Reroutes Magic");
        addCurioEffect("soul_ward_long_shatter_cooldown", "Lengthy Soul Ward Recharge upon Disintegration");
        addCurioEffect("soul_ward_complete_absorption", "Soul Ward Absorbs All Damage");
        addCurioEffect("soul_ward_escalating_integrity", "Soul Ward Gains Integrity When Nearing Disintegration");

        addCurioEffect("full_health_fake_collection", "Striking Full Health Targets Triggers Spirit Collection Effects");
        addCurioEffect("spirits_add_health", "Spirit Collection Grants Extra Hearts");
        addCurioEffect("spirits_weave_resonance", "Spirit Collection Generates Arcane Resonance");
    }

    public static void addCurioEffect(String identifier, String name) {
        add(TooltipComponentHelper.CURIO + identifier, name);
    }


    protected static void add(String key, String value) {
        MalumLangDatagen.lang.add(key, value);
    }
}