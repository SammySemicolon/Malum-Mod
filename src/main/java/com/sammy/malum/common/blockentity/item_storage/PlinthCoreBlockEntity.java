package com.sammy.malum.common.blockentity.item_storage;

import com.sammy.malum.common.item.misc.MalumSpiritItem;
import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.registry.block.BlockRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
import com.sammy.malum.core.systems.multiblock.MultiBlockCoreEntity;
import com.sammy.malum.core.systems.multiblock.MultiBlockStructure;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.function.Supplier;

public class PlinthCoreBlockEntity extends MultiBlockCoreEntity {
    public static final Supplier<MultiBlockStructure> STRUCTURE = ()->(MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.SOULWOOD_PLINTH_COMPONENT.get().defaultBlockState())));

    public SimpleBlockEntityInventory inventory;

    public PlinthCoreBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.PLINTH.get(), pos, state);
        inventory = new SimpleBlockEntityInventory(1, 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        inventory.save(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        inventory.load(compound);
        super.load(compound);
    }

    @Override
    public MultiBlockStructure getStructure() {
        return STRUCTURE.get();
    }
    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            inventory.interact(level, player, hand);
            return InteractionResult.SUCCESS;
        }
        return super.onUse(player, hand);
    }
    @Override
    public void onBreak() {
        inventory.dumpItems(level, worldPosition);
        super.onBreak();
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (inventory.getStackInSlot(0).getItem() instanceof MalumSpiritItem item) {
                Color color = item.type.color;
                Vec3 pos = itemPos(this);
                double x = pos.x;
                double y = pos.y + Math.sin((level.getGameTime() % 360) / 20f) * 0.05f;
                double z = pos.z;
                SpiritHelper.spawnSpiritParticles(level, x, y, z, color);
            }
        }
    }
    public static Vec3 itemPos(PlinthCoreBlockEntity blockEntity) {
        return DataHelper.fromBlockPos(blockEntity.getBlockPos()).add(blockEntity.itemOffset());
    }

    public Vec3 itemOffset() {
        return new Vec3(0.5f, 2f, 0.5f);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}
