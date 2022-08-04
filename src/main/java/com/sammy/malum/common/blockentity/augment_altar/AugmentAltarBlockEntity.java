package com.sammy.malum.common.blockentity.augment_altar;

import com.sammy.malum.common.blockentity.storage.ItemPedestalBlockEntity;
import com.sammy.malum.common.recipe.AugmentingRecipe;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntityInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class AugmentAltarBlockEntity extends ItemPedestalBlockEntity {
    public AugmentAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.AUGMENT_ALTAR.get(), pos, state);
        inventory = new OrtusBlockEntityInventory(1, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        ItemStack target = inventory.getStackInSlot(0);
        if (!target.isEmpty()) {
            ItemStack applied = player.getItemInHand(hand);
            AugmentingRecipe recipe = AugmentingRecipe.getRecipe(level, target, applied);
            if (recipe != null) {
                if (!level.isClientSide) {
                    target.getOrCreateTag().merge(recipe.tagAugment);
                    applied.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.onUse(player, hand);
    }
}