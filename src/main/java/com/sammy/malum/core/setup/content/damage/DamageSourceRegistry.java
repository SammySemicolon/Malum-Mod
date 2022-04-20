package com.sammy.malum.core.setup.content.damage;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class DamageSourceRegistry {

    public static final String VOODOO_DAMAGE = "voodoo";
    public static final String FORCED_SHATTER_DAMAGE = "voodoo_no_shatter";
    public static final String MAGEBANE_DAMAGE = "magebane";
    public static final String SCYTHE_SWEEP_DAMAGE = "scythe_sweep";
    public static final DamageSource VOODOO = new DamageSource(VOODOO_DAMAGE).setMagic();
    public static final DamageSource FORCED_SHATTER = new DamageSource(FORCED_SHATTER_DAMAGE).setMagic().bypassArmor().bypassMagic();

    public static DamageSource causeVoodooDamage(Entity attacker) {
        return new EntityDamageSource(VOODOO_DAMAGE, attacker).setMagic();
    }
    public static DamageSource causeMagebaneDamage(Entity attacker) {
        return new EntityDamageSource(MAGEBANE_DAMAGE, attacker).setThorns().setMagic();
    }
    public static DamageSource scytheSweepDamage(Entity attacker) {
        return new EntityDamageSource(SCYTHE_SWEEP_DAMAGE, attacker);
    }
}
