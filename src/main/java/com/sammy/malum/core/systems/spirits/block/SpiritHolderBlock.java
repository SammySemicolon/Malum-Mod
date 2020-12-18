package com.sammy.malum.core.systems.spirits.block;

import com.sammy.malum.core.systems.otherutilities.IAlwaysActivatedBlock;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SpiritHolderBlock extends Block implements IAlwaysActivatedBlock
{
    public SpiritHolderBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (worldIn instanceof ServerWorld)
        {
            if (handIn.equals(Hand.MAIN_HAND))
            {
                if (worldIn.getTileEntity(pos) instanceof ISpiritHolderTileEntity)
                {
                    ISpiritHolderTileEntity holderTileEntity = (ISpiritHolderTileEntity) worldIn.getTileEntity(pos);
                    SpiritHelper.transferSpirit(player, player.getHeldItemMainhand(),holderTileEntity);
                    worldIn.getTileEntity(pos).markDirty();
                    worldIn.notifyBlockUpdate(pos,state,state,3);
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
