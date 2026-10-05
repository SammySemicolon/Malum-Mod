package com.sammy.malum.datagen;

import com.sammy.malum.registry.common.*;
import net.minecraft.data.worldgen.*;
import net.minecraft.resources.*;
import net.minecraft.world.damagesource.*;

public class MalumDamageTypeDatagen {

    public static void bootstrap(BootstrapContext<DamageType> context) {
        register(context, MalumDamageTypes.CULTIST_MAGIC);

        register(context, MalumDamageTypes.VOODOO);
        register(context, MalumDamageTypes.VOODOO_PLAYERLESS);

        register(context, MalumDamageTypes.NITRATE);
        register(context, MalumDamageTypes.NITRATE_PLAYERLESS);

        register(context, MalumDamageTypes.VOID);
        register(context, MalumDamageTypes.KARMIC);
        register(context, MalumDamageTypes.ROT);


        register(context, MalumDamageTypes.SCYTHE_MELEE);
        register(context, MalumDamageTypes.SCYTHE_SWEEP);
        register(context, MalumDamageTypes.SCYTHE_REBOUND);
        register(context, MalumDamageTypes.SCYTHE_ASCENSION);
        register(context, MalumDamageTypes.SCYTHE_COMBO);
        register(context, MalumDamageTypes.SCYTHE_MAELSTROM);

        register(context, MalumDamageTypes.HIDDEN_BLADE_PHYSICAL_COUNTER);
        register(context, MalumDamageTypes.HIDDEN_BLADE_MAGIC_COUNTER);

        register(context, MalumDamageTypes.TYRVING);

        register(context, MalumDamageTypes.SUNDERING_ANCHOR_PHYSICAL_COMBO);
        register(context, MalumDamageTypes.SUNDERING_ANCHOR_MAGIC_COMBO);

        register(context, MalumDamageTypes.VINDICTIVE_BRAND_MELEE);
        register(context, MalumDamageTypes.VINDICTIVE_BRAND_SWEEP);
        register(context, MalumDamageTypes.VINDICTIVE_BRAND_COMBO);

        register(context, MalumDamageTypes.WARLOCK_SPIRIT_IMPACT);
        register(context, MalumDamageTypes.BERSERKER_SPIRIT_IMPACT);


        register(context, MalumDamageTypes.UNMAKERS_DISDAIN_COMBO);

        register(context, MalumDamageTypes.INVERTED_HEART_RETALIATION);
        register(context, MalumDamageTypes.INVERTED_HEART_PROPAGATION);
    }

    private static void register(BootstrapContext<DamageType> context, ResourceKey<DamageType> key) {
        context.register(key, new DamageType(key.location().getPath(), 0.1f, DamageEffects.HURT));
    }
}