package com.sammy.malum.common.blocks.spiritjar;

import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.core.systems.spirits.block.ISpiritHolderTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class SpiritJarBlock extends AbstractSpiritJarBlock
{
    public SpiritJarBlock(Properties properties)
    {
        super(properties);
    }
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (worldIn.getTileEntity(pos) instanceof ISpiritHolderTileEntity)
        {
            ISpiritHolderTileEntity tileEntity = (ISpiritHolderTileEntity) worldIn.getTileEntity(pos);
            if (tileEntity.getSpiritType() != null)
            {
                player.sendStatusMessage(ClientHelper.combinedComponent(ClientHelper.importantComponent(tileEntity.currentSpirits() + "/" + tileEntity.maxSpirits() + " ", true), ClientHelper.importantComponent(tileEntity.getSpiritType(), true)), true);
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}