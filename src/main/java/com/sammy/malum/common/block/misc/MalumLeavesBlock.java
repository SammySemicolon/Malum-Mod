package com.sammy.malum.common.block.misc;

import com.sammy.malum.core.registry.item.ItemRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeBlock;

import java.awt.*;

import net.minecraft.block.AbstractBlock.Properties;

public class MalumLeavesBlock extends LeavesBlock implements IForgeBlock
{
    public static final IntegerProperty COLOR = IntegerProperty.create("color",0,4);
    public final Color maxColor;
    public final Color minColor;
    
    public MalumLeavesBlock(Properties properties,Color maxColor,Color minColor)
    {
        super(properties);
        this.maxColor = maxColor;
        this.minColor = minColor;
        registerDefaultState(defaultBlockState().setValue(COLOR, 0));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT,COLOR);
    }
    
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).setValue(COLOR, 0);
    }
    
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if (player.getItemInHand(handIn).getItem().equals(ItemRegistry.INFERNAL_SPIRIT.get()))
        {
            level.setBlockAndUpdate(pos,state.setValue(COLOR, (state.getValue(COLOR) + 1) % 5));
            player.swing(handIn);
            player.playSound(SoundEvents.BLAZE_SHOOT, 1F, 1.5f + RANDOM.nextFloat() * 0.5f);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, handIn, hit);
    }
}
