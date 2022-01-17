package com.sammy.malum.common.block.ether;

import com.sammy.malum.common.blockentity.EtherBlockEntity;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.WaterLoggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class EtherBlock extends WaterLoggedBlock<EtherBlockEntity> {
    public static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);

    public EtherBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.ETHER);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (level.getBlockEntity(pos) instanceof EtherBlockEntity blockEntity) {
            AbstractEtherItem item = (AbstractEtherItem) stack.getItem();
            blockEntity.firstColor = ColorHelper.getColor(item.getFirstColor(stack));
            blockEntity.secondColor = ColorHelper.getColor(item.getSecondColor(stack));
            blockEntity.setChanged();
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = asItem().getDefaultInstance();
        if (level.getBlockEntity(pos) instanceof EtherBlockEntity blockEntity) {
            AbstractEtherItem etherItem = (AbstractEtherItem) stack.getItem();
            if (blockEntity.firstColor != null) {
                etherItem.setFirstColor(stack, blockEntity.firstColor.getRGB());
            }
            if (blockEntity.secondColor != null) {
                etherItem.setSecondColor(stack, blockEntity.secondColor.getRGB());
            }
            blockEntity.setChanged();
        }
        return stack;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}