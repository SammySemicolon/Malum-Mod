package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.modcontent.MalumRunes;
import com.sammy.malum.core.modcontent.MalumRunes.MalumRune;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RiteOfDeath extends AffectEntitiesRite
{
    public RiteOfDeath(String identifier, boolean isInstant, MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    
    @Override
    public int cooldown()
    {
        return 10;
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        entity.attackEntityFrom(DamageSource.MAGIC, 1);
    }
}
