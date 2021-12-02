package com.sammy.malum.common.container;

import com.sammy.malum.common.item.misc.MalumSpiritItem;
import com.sammy.malum.common.item.equipment.SpiritPouchItem;
import com.sammy.malum.core.registry.misc.ContainerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;

public class SpiritPouchContainer extends Container {
    private final IInventory inventory;

    public SpiritPouchContainer(int windowId, PlayerInventory playerInv, ItemStack backpack) {
        this(ContainerRegistry.SPIRIT_POUCH.get(), windowId, playerInv, SpiritPouchItem.getInventory(backpack));
    }

    public SpiritPouchContainer(ContainerType<? extends SpiritPouchContainer> containerType, int windowId, PlayerInventory playerInv, IInventory inventory) {
        super(containerType, windowId);
        this.inventory = inventory;
        inventory.openInventory(playerInv.player);
        for (int i = 0; i < inventory.getSizeInventory() / 9f; ++i) {
            for (int j = 0; j < 9; ++j) {
                int index = i * 9 + j;
                addSlot(new Slot(inventory, index, 8 + j * 18, 18 + i * 18) {
                    @Override
                    public boolean isItemValid(ItemStack stack) {
                        return stack.getItem() instanceof MalumSpiritItem;
                    }
                });
            }
        }
        int offset = offset();
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInv, j1 + (l + 1) * 9, 8 + j1 * 18, offset + 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, offset + 142));
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        playerIn.world.playSound(null, playerIn.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.PLAYERS, 1, 1);
        super.onContainerClosed(playerIn);
        this.inventory.closeInventory(playerIn);
    }

    public int offset() {
        return 0;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.inventory.isUsableByPlayer(playerIn);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.inventory.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.inventory.getSizeInventory(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}