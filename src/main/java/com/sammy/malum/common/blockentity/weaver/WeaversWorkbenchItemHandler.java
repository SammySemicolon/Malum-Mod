package com.sammy.malum.common.blockentity.weaver;

import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class WeaversWorkbenchItemHandler extends ItemStackHandler {

    private final WeaversWorkbenchBlockEntity entity;
    private final int outputs;

    public WeaversWorkbenchItemHandler(BlockEntity blockEntity, int outputs) {
        this.entity = (WeaversWorkbenchBlockEntity) blockEntity;
        this.outputs = outputs;
    }

    public WeaversWorkbenchItemHandler(int size, int outputs, BlockEntity blockEntity) {
        super(size + outputs);
        this.entity = (WeaversWorkbenchBlockEntity) blockEntity;
        this.outputs = outputs;
    }

    public WeaversWorkbenchItemHandler(NonNullList<ItemStack> stacks, int outputs, BlockEntity blockEntity) {
        super(stacks);
        this.entity = (WeaversWorkbenchBlockEntity) blockEntity;
        this.outputs = outputs;
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.entity.setChanged();
        if(slot < 2){
            this.entity.tryCraft();
        }
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true;
    }
    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if(slot >= this.getSlots() - this.outputs){
            return stack;
        }
        //todo: check if item allowed
        return super.insertItem(slot, stack, simulate);
    }
}