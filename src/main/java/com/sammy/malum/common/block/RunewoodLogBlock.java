package com.sammy.malum.common.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.misc.MalumLogBlock;
import com.sammy.malum.common.tile.TotemPoleTileEntity;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockHitResult;
import net.minecraft.world.level.Level;

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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        ItemStack stack = player.getItemInHand(handIn);
        if (level.isClientSide)
        {
            if (stack.getItem() instanceof MalumSpiritItem)
            {
                return InteractionResult.SUCCESS;
            }
        }
        if (stack.getItem() instanceof MalumSpiritItem)
        {
            MalumSpiritItem item = (MalumSpiritItem) stack.getItem();
            level.setBlockAndUpdate(pos, totemPole.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, hit.getDirection()));
            if (level.getBlockEntity(pos) instanceof TotemPoleTileEntity)
            {
                TotemPoleTileEntity tileEntity = (TotemPoleTileEntity) level.getBlockEntity(pos);
                tileEntity.create(item.type);
            }
            if (!player.isCreative())
            {
                stack.shrink(1);
            }
            player.swing(handIn, true);
        }
        return super.use(state, level, pos, player, handIn, hit);
    }
}
