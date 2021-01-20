package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AffectEntitiesRite extends MalumRite
{
    public AffectEntitiesRite(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    public void effect(LivingEntity entity)
    {
    
    }
    
    @Override
    public int cooldown()
    {
        return 20;
    }
    
    @Override
    public int range()
    {
        return 5;
    }
    
    @Override
    public void effect(BlockPos pos, World world)
    {
        world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos).grow(range())).forEach(e -> { if (!world.getBlockState(e.getPosition().down()).getBlock().equals(MalumBlocks.IMPERVIOUS_ROCK.get())) effect(e); }
        );
        super.effect(pos, world);
    }
}
