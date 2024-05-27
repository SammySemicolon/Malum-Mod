package com.sammy.malum.core;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FabricUtil {
    public static final ItemApiLookup<Storage<ItemVariant>, ContainerItemContext> CONTEXT_ITEM_API_LOOKUP =
            ItemApiLookup.get(new ResourceLocation("fabric:item_storage"), Storage.asClass(), ContainerItemContext.class);

    public static long insertSlotSimulated(SlotExposedStorage storage, int slot, ItemStack stack) {
        try (Transaction tx = TransferUtil.getTransaction()) {
            return stack.getCount() - storage.insertSlot(slot, ItemVariant.of(stack), stack.getCount(), tx);
        }
    }

    public static ItemStack insertSlot(SlotExposedStorage storage, int slot, ItemStack stack) {
        try (Transaction tx = TransferUtil.getTransaction()) {
            ItemStack newStack = stack.copy();
            long inserted = storage.insertSlot(slot, ItemVariant.of(stack), stack.getCount(), tx);
            newStack.setCount(stack.getCount() - (int) inserted);
            tx.commit();
            return newStack;
        }
    }

    public static long extractSlot(LodestoneBlockEntityInventory storage, int slot, long amount) {
        try (Transaction tx = TransferUtil.getTransaction()) {
            TransferUtil
            long extracted = storage.extractSlot(slot, ItemVariant.of(storage.getStackInSlot(slot)), amount, tx);
            tx.commit();
            return extracted;
        }
    }

    public static long simulateExtractSlot(SlotExposedStorage storage, int slot, long amount) {
        try (Transaction tx = TransferUtil.getTransaction()) {
            return storage.extractSlot(slot, ItemVariant.of(storage.getStackInSlot(slot)), amount, tx);
        }
    }

    public static <T> long simulateExtractView(StorageView<T> view, T resource, long amount) {
        try (Transaction tx = TransferUtil.getTransaction()) {
            return view.extract(resource, amount, tx);
        }
    }

    public static <T> Optional<Storage<T>> getStorage(BlockApiLookup<Storage<T>, Direction> lookup, Level level, BlockPos pos, @Nullable Direction side) {
        List<Storage<T>> storages = new ArrayList<>();
        Optional<BlockEntity> blockEntity = TileUtil.getTileEntity(level, pos);
        if (side != null) {
            return Optional.ofNullable(lookup.find(level, pos, null, blockEntity.orElse(null), side));
        }
        for (Direction direction : Direction.values()) {
            Storage<T> tStorage = lookup.find(level, pos, null, blockEntity.orElse(null), direction);

            if (tStorage != null) {
                if (storages.size() == 0) {
                    storages.add(tStorage);
                    continue;
                }

                for (Storage<T> storage : storages) {
                    if (!Objects.equals(tStorage, storage)) {
                        storages.add(tStorage);
                        break;
                    }
                }
            }
        }

        if (storages.isEmpty()) return Optional.empty();
        if (storages.size() == 1) return Optional.of(storages.get(0));
        return Optional.of(new CombinedStorage<>(storages));
    }
}
