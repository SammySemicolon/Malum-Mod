package com.sammy.malum.common.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.misc.MalumLogBlock;
import com.sammy.malum.common.tile.TotemPoleTileEntity;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.Level.Level;

import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock.Properties;

public class RunewoodLogBlock extends MalumLogBlock
{
    public final Supplier<Block> totemPole;
    public RunewoodLogBlock(Properties properties, Supplier<Block> stripped, Supplier<Block> totemPole)
    {
        super(properties, stripped);
        this.totemPole = totemPole;
    }

    @Override
    public ActionResultType use(BlockState state, Level LevelIn, BlockPos pos, Player player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack stack = player.getItemInHand(handIn);
        if (MalumHelper.areWeOnClient(LevelIn))
        {
            if (stack.getItem() instanceof MalumSpiritItem)
            {
                return ActionResultType.SUCCESS;
            }
        }
        if (stack.getItem() instanceof MalumSpiritItem)
        {
            MalumSpiritItem item = (MalumSpiritItem) stack.getItem();
            LevelIn.setBlockAndUpdate(pos, totemPole.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, hit.getDirection()));
            if (LevelIn.getBlockEntity(pos) instanceof TotemPoleTileEntity)
            {
                TotemPoleTileEntity tileEntity = (TotemPoleTileEntity) LevelIn.getBlockEntity(pos);
                tileEntity.create(item.type);
            }
            if (!player.isCreative())
            {
                stack.shrink(1);
            }
            player.swing(handIn, true);
        }
        return super.use(state, LevelIn, pos, player, handIn, hit);
    }
}
