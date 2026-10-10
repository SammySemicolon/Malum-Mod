package com.sammy.malum.common.block.curiosities.artifice.furnace;

import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackBlockHandlerBuilder;

public class MalumFurnaceBlockItemStackHandlerBuilder extends LodestoneItemStackBlockHandlerBuilder {
    private int fuelSlot = 0;
    private int[] inputSlots = new int[]{1};
    private int[] outputSlots = new int[]{2};

    protected MalumFurnaceBlockItemStackHandlerBuilder(LodestoneBlockEntity parent, int slotCount) {
        super(parent, slotCount);
    }

    public MalumFurnaceBlockItemStackHandlerBuilder setInputSlots(int... slots) {
        this.inputSlots = slots;
        return this;
    }

    public MalumFurnaceBlockItemStackHandlerBuilder setOutputSlots(int... slots) {
        this.outputSlots = slots;
        return this;
    }

    public MalumFurnaceBlockItemStackHandlerBuilder setFuelSlot(int slot) {
        this.fuelSlot = slot;
        return this;
    }

    @Override
    public MalumFurnaceBlockItemStackHandler build() {
        return new MalumFurnaceBlockItemStackHandler(parent, slotCount, allowedItemSize, inputPredicate, onContentsChanged, this.inputSlots, this.outputSlots, this.fuelSlot);
    }
}
