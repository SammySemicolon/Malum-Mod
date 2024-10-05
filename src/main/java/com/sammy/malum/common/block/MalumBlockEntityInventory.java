package com.sammy.malum.common.block;

import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;

import java.util.function.Predicate;

public class MalumBlockEntityInventory extends LodestoneBlockEntityInventory {

    public MalumBlockEntityInventory(int slotCount, int allowedItemSize, Predicate<ItemStack> inputPredicate, Predicate<ItemStack> outputPredicate) {
        super(slotCount, allowedItemSize, inputPredicate, outputPredicate);
    }

    public MalumBlockEntityInventory(int slotCount, int allowedItemSize, Predicate<ItemStack> inputPredicate) {
        super(slotCount, allowedItemSize, inputPredicate);
    }

    public MalumBlockEntityInventory(int slotCount, int allowedItemSize) {
        super(slotCount, allowedItemSize);
    }

    @Override
    public void extractItem(Player playerEntity, ItemStack stack, int slot) {
        super.extractItem(playerEntity, stack, slot);
        SoundEvent soundEvent = getExtractSound(stack);

        SoundHelper.playSound(playerEntity, soundEvent, SoundSource.BLOCKS, 0.7f, RandomHelper.randomBetween(playerEntity.getRandom(), 0.8f, 1.2f));
    }

    @Override
    public ItemStack insertItem(Player playerEntity, ItemStack stack) {
        final ItemStack result = super.insertItem(playerEntity, stack);
        if (!result.isEmpty()) {
            SoundEvent soundEvent = getInsertSound(result);
            SoundHelper.playSound(playerEntity, soundEvent, SoundSource.BLOCKS, 0.7f, RandomHelper.randomBetween(playerEntity.getRandom(), 0.8f, 1.2f));
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
