package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.block.MalumBlockEntityInventory;
import com.sammy.malum.common.item.augment.AbstractAugmentItem;
import com.sammy.malum.common.item.augment.core.AbstractCoreAugmentItem;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class AugmentBlockEntityInventory extends MalumBlockEntityInventory {
    public AugmentBlockEntityInventory(int slotCount, int allowedItemSize) {
        this(slotCount, allowedItemSize, t -> t.getItem() instanceof AbstractAugmentItem augmentItem && !(augmentItem instanceof AbstractCoreAugmentItem));
    }

    public AugmentBlockEntityInventory(int slotCount, int allowedItemSize, Predicate<ItemStack> inputPredicate) {
        super(slotCount, allowedItemSize, inputPredicate);
    }

    @Override
    public SoundEvent getInsertSound(ItemStack stack) {
        return SoundRegistry.APPLY_AUGMENT.get();
    }

    @Override
    public SoundEvent getExtractSound(ItemStack stack) {
        return SoundRegistry.REMOVE_AUGMENT.get();
    }
}
