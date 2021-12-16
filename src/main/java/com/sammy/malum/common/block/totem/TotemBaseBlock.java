package com.sammy.malum.common.block.totem;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.tile.TotemBaseTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.Level.IBlockReader;
import net.minecraft.Level.Level;

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
    public TileEntity createTileEntity(BlockState state, IBlockReader Level)
    {
        return new TotemBaseTileEntity(((TotemBaseBlock)state.getBlock()).corrupted);
    }

    @Override
    public ActionResultType use(BlockState state, Level LevelIn, BlockPos pos, Player player, Hand handIn, BlockRayTraceResult hit)
    {
        if (LevelIn.getBlockEntity(pos) instanceof TotemBaseTileEntity)
        {
            TotemBaseTileEntity totemBaseTileEntity = (TotemBaseTileEntity) LevelIn.getBlockEntity(pos);

            if (MalumHelper.areWeOnClient(LevelIn) || handIn == Hand.OFF_HAND)
            {
                return ActionResultType.SUCCESS;
            }
            if (totemBaseTileEntity.active && totemBaseTileEntity.rite != null)
            {
                totemBaseTileEntity.endRite();
            }
            else
            {
                totemBaseTileEntity.startRite();
            }
            player.swing(Hand.MAIN_HAND, true);
            return ActionResultType.SUCCESS;
        }
        return super.use(state, LevelIn, pos, player, handIn, hit);
    }

}
