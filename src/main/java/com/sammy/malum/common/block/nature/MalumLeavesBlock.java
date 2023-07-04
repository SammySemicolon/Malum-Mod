package com.sammy.malum.common.block.nature;

import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.extensions.IForgeBlock;

import java.awt.*;

public class MalumLeavesBlock extends LeavesBlock implements IForgeBlock {
    public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 4);
    public final Color maxColor;
    public final Color minColor;

    public MalumLeavesBlock(Properties properties, Color maxColor, Color minColor) {
        super(properties);
        this.maxColor = maxColor;
        this.minColor = minColor;
        registerDefaultState(defaultBlockState().setValue(COLOR, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT, COLOR);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(COLOR, 0);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).getItem().equals(ItemRegistry.INFERNAL_SPIRIT.get())) {
            level.setBlockAndUpdate(pos, state.setValue(COLOR, (state.getValue(COLOR) + 1) % 5));
            player.swing(handIn);
            player.playSound(SoundEvents.BLAZE_SHOOT, 1F, 1.5f + RANDOM.nextFloat() * 0.5f);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, handIn, hit);
    }
}