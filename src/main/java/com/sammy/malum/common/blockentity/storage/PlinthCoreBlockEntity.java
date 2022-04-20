package com.sammy.malum.common.blockentity.storage;

import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
import com.sammy.malum.core.systems.item.ISoulContainerItem;
import com.sammy.malum.core.systems.multiblock.MultiBlockCoreEntity;
import com.sammy.malum.core.systems.multiblock.MultiBlockStructure;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class PlinthCoreBlockEntity extends MultiBlockCoreEntity {
    public static final Supplier<MultiBlockStructure> STRUCTURE = ()->(MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.SOULWOOD_PLINTH_COMPONENT.get().defaultBlockState())));

    public SimpleBlockEntityInventory inventory;
    public MalumEntitySpiritData data;

    public PlinthCoreBlockEntity(BlockEntityType<? extends PlinthCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
    }

    public PlinthCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.PLINTH.get(), STRUCTURE.get(), pos, state);
        inventory = new SimpleBlockEntityInventory(1, 64, (s)->data == null) {
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
        if (data != null)
        {
            data.saveTo(compound);
        }
    }

    @Override
    public void load(CompoundTag compound) {
        inventory.load(compound);
        if (compound.contains(MalumEntitySpiritData.SOUL_DATA)) {
            data = MalumEntitySpiritData.load(compound);
        }
        else
        {
            data = null;
        }
        super.load(compound);
    }

    @Override
    public MultiBlockStructure getStructure() {
        return STRUCTURE.get();
    }
    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof ISoulContainerItem) {
            if (player.level.isClientSide)
            {
                return InteractionResult.SUCCESS;
            }
            if (data == null) {
                if (stack.hasTag() && stack.getTag().contains(MalumEntitySpiritData.SOUL_DATA)) {
                    data = MalumEntitySpiritData.load(stack.getTag());
                    if (stack.getCount() > 1) {
                        ItemStack split = stack.split(1);
                        split.getOrCreateTag().remove(MalumEntitySpiritData.SOUL_DATA);
                        ItemHelper.giveItemToEntity(split, player);
                    }
                    else
                    {
                        stack.getOrCreateTag().remove(MalumEntitySpiritData.SOUL_DATA);
                    }
                }
            }
            else
            {
                if (!stack.getOrCreateTag().contains(MalumEntitySpiritData.SOUL_DATA)) {
                    if (stack.getCount() > 1) {
                        ItemStack split = stack.split(1);
                        data.saveTo(split.getOrCreateTag());
                        data = null;
                        ItemHelper.giveItemToEntity(split, player);
                    }
                    else
                    {
                        data.saveTo(stack.getOrCreateTag());
                        data = null;
                    }
                }
            }
            player.swing(hand, true);
            BlockHelper.updateAndNotifyState(level, worldPosition);
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
                Vec3 pos = getItemPos();
                double x = pos.x;
                double y = pos.y + Math.sin((level.getGameTime() ) / 20f) * 0.05f;
                double z = pos.z;
                SpiritHelper.spawnSpiritParticles(level, x, y, z, item.type.color, item.type.endColor);
            }
            if (data != null) {
                Vec3 pos = getItemPos();
                double x = pos.x;
                double y = pos.y + Math.sin((level.getGameTime() ) / 20f) * 0.08f;
                double z = pos.z;
                SpiritHelper.spawnSoulParticles(level, x, y, z, 1, 1, Vec3.ZERO, data.primaryType.color, data.primaryType.endColor);
            }
        }
    }
    public Vec3 getItemPos() {
        return DataHelper.fromBlockPos(getBlockPos()).add(itemOffset());
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
