package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.modcontent.MalumRunes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AffectEntitiesRite extends MalumRite
{
    public AffectEntitiesRite(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    public void effect(LivingEntity entity)
    {
    
    }
    public long interval()
    {
        return 20L;
    }
    public int range()
    {
        return 10;
    }
    @Override
    public void effect(BlockPos pos, World world)
    {
        if (world.getGameTime() % interval() == 0 || isInstant)
        {
            world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos).grow(range())).forEach(this::effect);
        }
        super.effect(pos, world);
    }
}
