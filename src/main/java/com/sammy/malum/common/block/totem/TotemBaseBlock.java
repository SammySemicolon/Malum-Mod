package com.sammy.malum.common.block.totem;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.tile.TotemBaseTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockHitResult;
import net.minecraft.Level.IBlockReader;
import net.minecraft.world.level.Level;

import net.minecraft.block.AbstractBlock.Properties;

public class TotemBaseBlock extends Block
{
    public final boolean corrupted;
    public TotemBaseBlock(Properties properties, boolean corrupted)
    {
        super(properties);
        this.corrupted = corrupted;
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    @Override
    public BlockEntity createTileEntity(BlockState state, IBlockReader Level)
    {
        return new TotemBaseTileEntity(((TotemBaseBlock)state.getBlock()).corrupted);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if (level.getBlockEntity(pos) instanceof TotemBaseTileEntity)
        {
            TotemBaseTileEntity totemBaseTileEntity = (TotemBaseTileEntity) level.getBlockEntity(pos);

            if (level.isClientSide || handIn == InteractionHand.OFF_HAND)
            {
                return InteractionResult.SUCCESS;
            }
            if (totemBaseTileEntity.active && totemBaseTileEntity.rite != null)
            {
                totemBaseTileEntity.endRite();
            }
            else
            {
                totemBaseTileEntity.startRite();
            }
            player.swing(InteractionHand.MAIN_HAND, true);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, handIn, hit);
    }

}
