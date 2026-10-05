package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.*;
import net.minecraft.world.damagesource.DamageType;

public class MalumDamageTypes {

    public static final ResourceKey<DamageType> CULTIST_MAGIC = createDamageType("cultist_magic");

    public static final ResourceKey<DamageType> VOODOO = createDamageType("voodoo");
    public static final ResourceKey<DamageType> VOODOO_PLAYERLESS = createDamageType("voodoo_playerless");

    public static final ResourceKey<DamageType> NITRATE = createDamageType("nitrate");
    public static final ResourceKey<DamageType> NITRATE_PLAYERLESS = createDamageType("nitrate_playerless");

    public static final ResourceKey<DamageType> VOID = createDamageType("void");
    public static final ResourceKey<DamageType> KARMIC = createDamageType("karmic");
    public static final ResourceKey<DamageType> ROT = createDamageType("rot");

    public static final ResourceKey<DamageType> SCYTHE_MELEE = createDamageType("scythe_melee");
    public static final ResourceKey<DamageType> SCYTHE_SWEEP = createDamageType("scythe_sweep");
    public static final ResourceKey<DamageType> SCYTHE_REBOUND = createDamageType("scythe_rebound");
    public static final ResourceKey<DamageType> SCYTHE_ASCENSION = createDamageType("scythe_ascension");
    public static final ResourceKey<DamageType> SCYTHE_COMBO = createDamageType("scythe_combo");
    public static final ResourceKey<DamageType> SCYTHE_MAELSTROM = createDamageType("scythe_maelstrom");

    public static final ResourceKey<DamageType> HIDDEN_BLADE_PHYSICAL_COUNTER = createDamageType("hidden_blade_physical_counter");
    public static final ResourceKey<DamageType> HIDDEN_BLADE_MAGIC_COUNTER = createDamageType("hidden_blade_magic_counter");

    public static final ResourceKey<DamageType> TYRVING = createDamageType("tyrving");

    public static final ResourceKey<DamageType> SUNDERING_ANCHOR_PHYSICAL_COMBO = createDamageType("sundering_anchor_physical_combo");
    public static final ResourceKey<DamageType> SUNDERING_ANCHOR_MAGIC_COMBO = createDamageType("sundering_anchor_magic_combo");

    public static final ResourceKey<DamageType> VINDICTIVE_BRAND_MELEE = createDamageType("vindictive_brand_melee");
    public static final ResourceKey<DamageType> VINDICTIVE_BRAND_SWEEP = createDamageType("vindictive_brand_sweep");
    public static final ResourceKey<DamageType> VINDICTIVE_BRAND_COMBO = createDamageType("vindictive_brand_combo");

    //Geas
    public static final ResourceKey<DamageType> WARLOCK_SPIRIT_IMPACT = createDamageType("warlock_spirit_impact");
    public static final ResourceKey<DamageType> BERSERKER_SPIRIT_IMPACT = createDamageType("berserker_spirit_impact");

    public static final ResourceKey<DamageType> UNMAKERS_DISDAIN_COMBO = createDamageType("unmakers_disdain_combo");

    public static final ResourceKey<DamageType> INVERTED_HEART_RETALIATION = createDamageType("inverted_heart_retaliation");
    public static final ResourceKey<DamageType> INVERTED_HEART_PROPAGATION = createDamageType("inverted_heart_propagation");

    public static ResourceKey<DamageType> createDamageType(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath(name));
    }
}