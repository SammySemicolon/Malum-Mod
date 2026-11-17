package com.sammy.malum.common.block;

import com.sammy.malum.common.item.spirit.*;

import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.inventory.InventoryInteractionResult;
import team.lodestar.lodestone.modules.toolkit.inventory.InventoryInteractionResultBuilder;
import team.lodestar.lodestone.modules.toolkit.inventory.InventoryItemStackTransaction;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackBlockHandler;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackHandler;

import java.util.Collection;
import java.util.function.*;

public class MalumBlockItemStackHandler extends LodestoneItemStackBlockHandler {

    public static MalumBlockItemStackHandlerBuilder create(LodestoneBlockEntity parent, int slotCount) {
        return new MalumBlockItemStackHandlerBuilder(parent, slotCount);
    }

    public MalumBlockItemStackHandler(LodestoneBlockEntity parent, int slotCount, int allowedItemSize, BiPredicate<LodestoneItemStackHandler, ItemStack> inputPredicate, Runnable contentsChangeBehavior) {
        super(parent, slotCount, allowedItemSize, inputPredicate, contentsChangeBehavior);
    }

    @Override
    public void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        parent.setDirty();
    }

    @Override
    public InventoryInteractionResult insertItem(ServerLevel level, ItemStack stack) {
        ItemStack remaining = stack.copy();
        for (int i = 0; i < getSlots() && !remaining.isEmpty(); i++) {
            remaining = insertItem(i, remaining, true);
        }
        int count = Math.min(stack.getCount() - remaining.getCount(), getAllowedItemSize());
        if (count <= 0) {
            return noChange(InventoryInteractionResult.insert(), stack);
        }
        return insertItem(level, stack.split(count), false);
    }

    @Override
    public InventoryInteractionResult extractItem(ServerLevel level, Function<ItemStack, Integer> amount) {
        if (isEmpty()) {
            return noChange(InventoryInteractionResult.extract(), ItemStack.EMPTY);
        }
        var result = super.extractItem(level, amount);
        return result == InventoryInteractionResult.EMPTY ? noChange(InventoryInteractionResult.extract(), ItemStack.EMPTY) : result;
    }

    private static InventoryInteractionResult noChange(InventoryInteractionResultBuilder builder, ItemStack external) {
        return builder
                .internalChange(InventoryItemStackTransaction.updated(ItemStack.EMPTY, ItemStack.EMPTY, -1))
                .externalChange(InventoryItemStackTransaction.updated(external, external, -1))
                .build();
    }

    @Override
    protected void processResult(ServerLevel level, InventoryInteractionResult result) {
        if (result.wasSuccessful()) {
            var stack = result.getTransferredItem();
            var soundEvent = getExtractSound(stack);
            playSound(level, soundEvent, stack);
        }
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (stack.is(MalumTags.Items.SPIRITS)) {
            for (int i = 0; i < getSlots(); i++) {
                if (i != slot) {
                    ItemStack stackInSlot = getStackInSlot(i);
                    if (!stackInSlot.isEmpty() && stackInSlot.is(stack.getItem()))
                        return false;
                }
            }
        }
        return super.isItemValid(slot, stack);
    }

    public void playSound(ServerLevel level, SoundEvent soundEvent, ItemStack stack) {
        var blockPos = parent.getBlockPos();
        float pitch = Easing.SINE_IN_OUT.asWeighedRandom(level.getRandom(), 1f, 1.2f);
        level.playSound(null, blockPos, soundEvent, SoundSource.BLOCKS, 0.7f, pitch);
        if (stack.getItem() instanceof BlockItem blockItem) {
            var block = blockItem.getBlock();
            var blockSoundType = block.getSoundType(block.defaultBlockState(), level, blockPos, null);
            float blockSoundVolume = blockSoundType.getVolume() * 0.4f;
            float blockSoundPitch = blockSoundType.getPitch() * 1.2f;
            level.playSound(null, blockPos, blockSoundType.getHitSound(), SoundSource.BLOCKS, blockSoundVolume, blockSoundPitch);
        }
    }

    public void spendItemsOnRecipe(Collection<SizedIngredient> items) {
        spendThingsOnRecipeSpiritsOnRecipe(items.stream().map(IngredientRecipeCostData::new).toList());
    }

    public void spendSpiritsOnRecipe(Collection<SpiritIngredient> spirits) {
        spendThingsOnRecipeSpiritsOnRecipe(spirits.stream().map(IngredientRecipeCostData::new).toList());
    }

    protected void spendThingsOnRecipeSpiritsOnRecipe(Collection<IngredientRecipeCostData> ingredients) {
        for (IngredientRecipeCostData data : ingredients) {
            for (ItemStack stack : getNonEmptyStacks()) {
                if (data.ingredient().test(stack)) {
                    stack.shrink(data.count());
                    break;
                }
            }
        }
    }

    public SoundEvent getExtractSound(ItemStack stack) {
        return stack.getItem() instanceof SpiritShardItem ? MalumSoundEvents.PEDESTAL_SPIRIT_PICKUP.get() : MalumSoundEvents.PEDESTAL_ITEM_PICKUP.get();
    }

    public SoundEvent getInsertSound(ItemStack stack) {
        return stack.getItem() instanceof SpiritShardItem ? MalumSoundEvents.PEDESTAL_SPIRIT_INSERT.get() : MalumSoundEvents.PEDESTAL_ITEM_INSERT.get();
    }

    public record IngredientRecipeCostData(Ingredient ingredient, int count) {

        public IngredientRecipeCostData(SpiritIngredient ingredient) {
            this(ingredient.toVanilla(), ingredient.count());
        }

        public IngredientRecipeCostData(SizedIngredient ingredient) {
            this(ingredient.ingredient(), ingredient.count());
        }
    }
}