package com.sammy.malum.visual_effects.networked.data;

import net.minecraft.nbt.*;
import net.minecraft.world.item.*;

public class NBTEffectData {

    public static final String ITEM = "stack";

    public final CompoundTag compoundTag;

    public NBTEffectData(CompoundTag compoundTag) {
        this.compoundTag = compoundTag;
    }

    public NBTEffectData(ItemStack stack) {
        this.compoundTag = new CompoundTag();
        CompoundTag stackTag = new CompoundTag();
        stack.save(stackTag);
        compoundTag.put(ITEM, stackTag);
    }

    public ItemStack getStack() {
        return compoundTag.contains(ITEM) ? ItemStack.of(compoundTag.getCompound(ITEM)) : ItemStack.EMPTY;
    }
}
