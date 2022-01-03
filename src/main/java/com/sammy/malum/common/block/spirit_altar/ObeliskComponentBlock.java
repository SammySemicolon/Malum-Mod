package com.sammy.malum.common.block.spirit_altar;

import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.core.systems.multiblock.ComponentBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class ObeliskComponentBlock extends ComponentBlock implements IAltarAccelerator {
    public static final AcceleratorType OBELISK = new AcceleratorType(4, "obelisk");
    public static final VoxelShape SHAPE = makeShape();
    private final Supplier<Item> cloneStack;
    public ObeliskComponentBlock(Properties properties, Supplier<Item> cloneStack) {
        super(properties);
        this.cloneStack = cloneStack;
    }

    @Override
    public AcceleratorType getType() {
        return OBELISK;
    }

    @Override
    public float getAcceleration(Level level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.above()).getBlock() instanceof EtherBlock ? 1f : 0.75f;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return cloneStack.get().getDefaultInstance();
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.1875, 0.8125, 0.5625, 0.8125), BooleanOp.OR);

        return shape;
    }
}