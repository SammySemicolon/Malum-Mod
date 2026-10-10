package com.sammy.malum.common.block.curiosities.artifice.furnace;

import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackBlockHandler;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackHandler;

import java.util.function.BiPredicate;

public class MalumFurnaceBlockItemStackHandler extends LodestoneItemStackBlockHandler {
    private final int fuelSlot;
    private final int[] inputSlots;
    private final int[] outputSlots;

    public static MalumFurnaceBlockItemStackHandlerBuilder create(LodestoneBlockEntity parent, int slotCount) {
        return new MalumFurnaceBlockItemStackHandlerBuilder(parent, slotCount);
    }

    public MalumFurnaceBlockItemStackHandler(LodestoneBlockEntity parent, int slotCount, int allowedItemSize, BiPredicate<LodestoneItemStackHandler, ItemStack> inputPredicate, Runnable contentsChangeBehavior, int[] inputSlots, int[] outputSlots, int fuelSlot) {
        super(parent, slotCount, allowedItemSize, inputPredicate, contentsChangeBehavior);
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
        this.fuelSlot = fuelSlot;
    }

    @Override
    public void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        parent.setDirty();
    }

    public int[] getInputSlots() {
        return this.inputSlots;
    }

    public int[] getOutputSlots() {
        return this.outputSlots;
    }

    public int getFuelSlot() {
        return this.fuelSlot;
    }

    //TODO probably use the lodestone variants later
    //Copied & modified from ItemHandlerHelper
    public ItemStack fillOutputSlots(ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return stack;
        }
        int[] outputSlots = this.getOutputSlots();
        for (int i : outputSlots) {
            stack = this.insertItem(i, stack, simulate);
            if (stack.isEmpty()) {
                return ItemStack.EMPTY;
            }
        }

        return stack;
    }

    /** Copied & modified from ItemHandlerHelper. Original javadoc: <br>
     * Inserts the ItemStack into the inventory, filling up already present stacks first.
     * This is equivalent to the behaviour of a player picking up an item. <br>
     * Note: This function stacks items without subtypes with different metadata together.
     */
    public ItemStack fillOutputSlotsStacked(ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return stack;
        }

        // not stackable -> just insert into a new slot
        if (!stack.isStackable()) {
            return this.fillOutputSlots(stack, simulate);
        }

        int[] outputSlots = this.getOutputSlots();

        // go through the inventory and try to fill up already existing items
        for (int i : outputSlots) {
            ItemStack slot = this.getStackInSlot(i);
            if (ItemStack.isSameItemSameComponents(slot, stack)) {
                stack = this.insertItem(i, stack, simulate);

                if (stack.isEmpty()) {
                    break;
                }
            }
        }


        // insert remainder into empty slots
        if (!stack.isEmpty()) {
            // find empty slot
            for (int i : outputSlots) {
                if (this.getStackInSlot(i).isEmpty()) {
                    stack = this.insertItem(i, stack, simulate);
                    if (stack.isEmpty()) {
                        break;
                    }
                }
            }
        }

        return stack;
    }

    public void shrinkAllInputs() {
        int[] inputSlots = this.getInputSlots();
        for (int i : inputSlots) {
            ItemStack stack = this.getStackInSlot(i);
            if (stack.hasCraftingRemainingItem()) {
                ItemStack remainder = stack.getCraftingRemainingItem();
                this.setStackInSlot(i, remainder);
            } else {
                stack.shrink(1);
            }
        }
    }
}
