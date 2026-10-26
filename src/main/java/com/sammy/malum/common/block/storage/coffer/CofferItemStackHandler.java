package com.sammy.malum.common.block.storage.coffer;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackBlockHandler;

import java.util.stream.Stream;

public class CofferItemStackHandler extends LodestoneItemStackBlockHandler {
    public CofferItemStackHandler(CofferBlockEntity parent) {
        super(parent, 54, 64, (h, s) -> true, null);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return pins[i] ? 1 : 0;
            }

            @Override
            public void set(int i, int value) {
                pins[i] = value == 1;
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }

    protected final ContainerData data;

    private boolean[] pins = new boolean[5];

    public Stream<Item> getValidItemsForRow(int row) {
        Stream.Builder<Item> stream = Stream.builder();
        //If we are not on the first row and there is no pin before this row, we should start at the first row of the section
        while (row > 0 && data.get(row - 1) != 1)
            --row;
        //Go through each row until we go through every row or hit a pin, adding every new Item into the stream
        for (int i = row; i < 6 && (i == row || data.get(i - 1) != 1); i++) {
            //Add each item in that row to the stream
            for (int j = 0; j < 9; j++) {
                if (!getStackInSlot(i * 9 + j).isEmpty())
                    stream.add(getStackInSlot(i * 9 + j).getItem());
            }
        }
        return stream.build().distinct();
    }


    public boolean checkPin(int i) {
        return data.get(i) == 1;
    }

    public void togglePin(int i) {
        data.set(i, data.get(i) == 1 ? 0 : 1);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return super.isItemValid(slot, stack) && getValidItemsForRow(slot/9).anyMatch((i) -> stack.getItem().equals(i));
    }

    @Override
    public void save(HolderLookup.Provider provider, CompoundTag compound, String name) {
        super.save(provider, compound, name);
        for (int i = 0; i < 5; i++)
            compound.putBoolean("row" + i, pins[i]);
    }

    @Override
    public void load(HolderLookup.Provider provider, CompoundTag compound, String name) {
        super.load(provider, compound, name);
        for (int i = 0; i < 5; i++)
            if (compound.contains("row" + i))
                pins[i] = compound.getBoolean("row" + i);
    }

    @Override
    public void clear() {
        pins = new boolean[5];
        super.clear();
    }
}
