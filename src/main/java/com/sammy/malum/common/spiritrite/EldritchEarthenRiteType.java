package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class EldritchEarthenRiteType extends MalumRiteType
{
    public EldritchEarthenRiteType()
    {
        super("eldritch_earthen_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public void riteEffect(World world, BlockPos pos) {
        getNearbyBlocksUnderBase(Block.class, world, pos, false).forEach(p -> {
            BlockState state = world.getBlockState(p);
        });
    }

    @Override
    public void corruptedRiteEffect(World world, BlockPos pos) {
        super.corruptedRiteEffect(world, pos);
    }
}
