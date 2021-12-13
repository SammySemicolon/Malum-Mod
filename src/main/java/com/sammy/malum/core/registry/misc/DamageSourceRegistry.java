package com.sammy.malum.core.registry.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class DamageSourceRegistry {

    public static final DamageSource VOODOO = new DamageSource("voodoo").setMagicDamage();
    public static final DamageSource FORCED_SHATTER = new DamageSource("forced_shatter").setMagicDamage().setDamageBypassesArmor().setDamageIsAbsolute();

    public static DamageSource causeVoodooDamage(Entity attacker) {
        return new EntityDamageSource("voodoo", attacker).setMagicDamage();
    }
}
