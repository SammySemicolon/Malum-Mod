package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class AffectBlocksRite extends MalumRite
{
    public AffectBlocksRite(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    public boolean effect(BlockPos pos, BlockState state, World world)
    {
        return false;
    }
    
    @Override
    public int cooldown()
    {
        return 20;
    }
    
    public boolean vertical()
    {
        return false;
    }
    
    public int yOffset()
    {
        return 0;
    }
    
    @Override
    public void effect(BlockPos pos, World world)
    {
        BlockPos startPos = pos.north(range()).west(range()).up(yOffset());
        BlockPos endPos = pos.south(range()).east(range()).up(yOffset());
        if (vertical())
        {
            startPos.down(range());
            endPos.up(range());
        }
        Stream<BlockPos> stream = BlockPos.Mutable.getAllInBox(startPos, endPos);
        stream.forEach(p -> effect(p, world.getBlockState(p), world));
    }
}
