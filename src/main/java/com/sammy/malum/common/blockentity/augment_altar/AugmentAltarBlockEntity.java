package com.sammy.malum.common.blockentity.augment_altar;

import com.sammy.malum.common.blockentity.storage.ItemPedestalBlockEntity;
import com.sammy.malum.common.blockentity.storage.ItemStandBlockEntity;
import com.sammy.malum.common.recipe.AugmentingRecipe;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntityInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

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
            AugmentingRecipe recipe = AugmentingRecipe.getRecipe(level, target);
            if (recipe != null) {
                ItemStack applied = player.getItemInHand(hand);
                if (recipe.input.test(applied)) {
                    if (!level.isClientSide) {
                        target.getOrCreateTag().merge(recipe.tagAugment);
                        applied.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.onUse(player, hand);
    }
}