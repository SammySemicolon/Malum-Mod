package com.sammy.malum.common.blockentity.mirror;

import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


public abstract class MirrorBlockEntity extends SimpleBlockEntity {

    public Direction direction;
    public BlockEntity attachedBlockEntity;
    public LazyOptional<IItemHandler> attachedInventory;
    public boolean updateAttached = false;
    public int cooldown;
    public MirrorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.direction = state.getValue(BlockStateProperties.FACING);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        if (direction != null) {
            compound.putString("direction", direction.toString());
        }
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        direction = Direction.byName(compound.getString("direction"));
        updateAttached = true;
        super.load(compound);
    }

    @Override
    public void onPlace(LivingEntity placer, ItemStack stack) {
        updateAttached = true;
    }

    @Override
    public void onNeighborUpdate(BlockState state, BlockPos pos, BlockPos neighbor) {
        updateAttached = true;
    }

    @Override
    public void tick() {
        if (updateAttached) {
            updateAttached();
            updateAttached = false;
        }
        if (cooldown > 0) {
            cooldown--;
        } else if (attachedBlockEntity != null) {
            attachedInventory.ifPresent(this::attachedTick);
        }
    }
    public void attachedTick(IItemHandler handler)
    {

    }
    public void updateAttached() {
        attachedBlockEntity = level.getBlockEntity(getBlockPos().relative(direction.getOpposite()));
        if (attachedBlockEntity != null) {
            attachedInventory = attachedBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction.getOpposite());
        }
    }
}