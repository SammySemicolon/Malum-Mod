package com.sammy.malum.common.block.fusion_plate;

import com.sammy.malum.core.systems.multiblock.ComponentBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.HitResult;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class FusionPlateComponentBlock extends ComponentBlock {
    public static BooleanProperty CORNER = BooleanProperty.create("corner");
    private final Supplier<Item> cloneStack;
    public FusionPlateComponentBlock(Properties properties, Supplier<Item> cloneStack) {
        super(properties);
        this.cloneStack = cloneStack;
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(CORNER, false));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return cloneStack.get().getDefaultInstance();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, CORNER);
    }
}