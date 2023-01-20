package com.sammy.malum.registry.common;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class DamageSourceRegistry {

    public static final String VOODOO_IDENTIFIER = "voodoo";
    public static final String GUARANTEED_SOUL_SHATTER = "soul_strike";
    public static final String MAGEBANE_IDENTIFIER = "magebane";
    public static final String SCYTHE_SWEEP_IDENTIFIER = "scythe_sweep";

    public static final DamageSource VOODOO = new DamageSource(VOODOO_IDENTIFIER).setMagic();
    public static final DamageSource SOUL_STRIKE = new DamageSource(GUARANTEED_SOUL_SHATTER).setMagic();

    public static DamageSource causeVoodooDamage(Entity attacker) {
        return new EntityDamageSource(VOODOO_IDENTIFIER, attacker).setMagic();
    }

    public static DamageSource causeSoulStrikeDamage(Entity attacker) {
        return new EntityDamageSource(GUARANTEED_SOUL_SHATTER, attacker).setMagic();
    }

    public static DamageSource causeMagebaneDamage(Entity attacker) {
        return new EntityDamageSource(MAGEBANE_IDENTIFIER, attacker).setThorns().setMagic();
    }

    public static DamageSource scytheSweepDamage(Entity attacker) {
        return new EntityDamageSource(SCYTHE_SWEEP_IDENTIFIER, attacker);
    }
}