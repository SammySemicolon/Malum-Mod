package com.sammy.malum.common.block;

import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.blockentity.*;

import java.util.function.*;

public class MalumBlockEntityInventory extends LodestoneBlockEntityInventory {

    public MalumBlockEntityInventory(int slotCount, int allowedItemSize, Predicate<ItemStack> inputPredicate, Predicate<ItemStack> outputPredicate) {
        super(null, slotCount, allowedItemSize, inputPredicate, outputPredicate);
    }

    public MalumBlockEntityInventory(int slotCount, int allowedItemSize, Predicate<ItemStack> inputPredicate) {
        super(null,slotCount, allowedItemSize, inputPredicate);
    }

    public MalumBlockEntityInventory(int slotCount, int allowedItemSize) {
        super(null,slotCount, allowedItemSize);
    }

    @Override
    public ItemStack interactExtractInv(LodestoneBlockEntity be, Player player) {
        ItemStack stack = super.interactExtractInv(be, player);
        SoundEvent soundEvent = getExtractSound(stack);
        player.level().playSound(null, player.blockPosition(), soundEvent, SoundSource.BLOCKS, 0.7f, RandomHelper.randomBetween(player.level().random, 0.8f, 1.2f));
        return stack;
    }

    @Override
    public boolean interactInsertInv(LodestoneBlockEntity be, ItemStack stack) {
        final boolean result = super.interactInsertInv(be, stack);
        if (result) {
            SoundEvent soundEvent = getInsertSound(stack);
            be.getLevel().playSound(null, be.getBlockPos(), soundEvent, SoundSource.BLOCKS, 0.7f, RandomHelper.randomBetween(be.getLevel().random, 0.8f, 1.2f));
        }
        return result;
    }

    public SoundEvent getExtractSound(ItemStack stack) {
        return stack.getItem() instanceof SpiritShardItem ? SoundRegistry.PEDESTAL_SPIRIT_PICKUP.get() : SoundRegistry.PEDESTAL_ITEM_PICKUP.get();
    }
    public SoundEvent getInsertSound(ItemStack stack) {
        return stack.getItem() instanceof SpiritShardItem ? SoundRegistry.PEDESTAL_SPIRIT_INSERT.get() : SoundRegistry.PEDESTAL_ITEM_INSERT.get();
    }
}
