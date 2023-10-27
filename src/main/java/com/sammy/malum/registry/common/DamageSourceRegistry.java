package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class DamageSourceRegistry {

    public static final String VOODOO_IDENTIFIER = "voodoo";
    public static final String SCYTHE_SWEEP_IDENTIFIER = "scythe_sweep";

    public static final ResourceKey<DamageType> VOODOO = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("voodoo"));
    public static final ResourceKey<DamageType> SCYTHE_SWEEP = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("scythe_sweep"));

    public static DamageSource create(Level world, ResourceKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), source, attacker);
    }

    public static DamageSource create(Level world, ResourceKey<DamageType> key, @Nullable Entity source) {
        return create(world, key, source, null);
    }

    public static DamageSource create(Level world, ResourceKey<DamageType> key) {
        return create(world, key, null, null);
    }
}