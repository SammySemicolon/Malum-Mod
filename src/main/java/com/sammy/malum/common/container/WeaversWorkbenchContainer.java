package com.sammy.malum.common.container;

import com.sammy.malum.common.blockentity.weaver.*;
import com.sammy.malum.common.item.cosmetic.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraftforge.items.*;
import team.lodestar.lodestone.systems.item.*;

import javax.annotation.*;
import java.util.*;

public class WeaversWorkbenchContainer extends AbstractContainerMenu {

    public static final Component component = new TextComponent("Weaver's Workbench");
    public final WeaversWorkbenchItemHandler itemHandler;
    public final WeaversWorkbenchBlockEntity blockEntity;

    public WeaversWorkbenchContainer(int windowId, Inventory playerInv, FriendlyByteBuf data) {
        this(windowId, playerInv, getTileEntity(playerInv, data));
    }
    public WeaversWorkbenchContainer(int windowId, Inventory playerInv, WeaversWorkbenchBlockEntity blockEntity) {
        super(ContainerRegistry.WEAVERS_WORKBENCH.get(), windowId);
        this.itemHandler = blockEntity.itemHandler;
        this.blockEntity = blockEntity;
        addSlot(new SlotItemHandler(itemHandler, 0, 18, 52) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.getItem() instanceof LodestoneArmorItem;
            }
        });
        addSlot(new SlotItemHandler(itemHandler, 1, 54, 52) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.getItem() instanceof AbstractWeaveItem;
            }
        });
        addSlot(new SlotItemHandler(itemHandler, 2, 90, 52) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            @Override
            public void onTake(Player pPlayer, ItemStack pStack) {
                super.onTake(pPlayer, pStack);
                blockEntity.onCraft();
            }
        });

        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInv, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, 142));
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < itemHandler.getSlots()) {
                if (!this.moveItemStackTo(itemstack1, itemHandler.getSlots(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, itemHandler.getSlots(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
                slot.onTake(playerIn, itemstack1);
            } else {
                slot.setChanged();
            }
        }
        itemHandler.onContentsChanged(index);
        return itemstack;
    }


    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    private static WeaversWorkbenchBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof WeaversWorkbenchBlockEntity weaversWorkbenchBlockEntity) {
            return weaversWorkbenchBlockEntity;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }
}
