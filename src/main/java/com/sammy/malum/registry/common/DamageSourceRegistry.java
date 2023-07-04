package com.sammy.malum.registry.common;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class DamageSourceRegistry {

    public static final String VOODOO_IDENTIFIER = "voodoo";
    public static final String SCYTHE_SWEEP_IDENTIFIER = "scythe_sweep";

    public static final DamageSource VOODOO = new DamageSource(VOODOO_IDENTIFIER).setMagic();

    public static DamageSource causeVoodooDamage(Entity attacker) {
        return new EntityDamageSource(VOODOO_IDENTIFIER, attacker).setMagic();
    }

    public static DamageSource causeScytheSweepDamage(Entity attacker) {
        return new EntityDamageSource(SCYTHE_SWEEP_IDENTIFIER, attacker);
    }
}