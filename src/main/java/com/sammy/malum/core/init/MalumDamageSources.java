package com.sammy.malum.core.init;

import net.minecraft.util.DamageSource;

public class MalumDamageSources
{
    public static final DamageSource BLEEDING = (new DamageSource("bleeding"));
    public static final DamageSource VOODOO = (new DamageSource("voodoo")).setMagicDamage();
    
    public static void init()
    {
    
    }
}
