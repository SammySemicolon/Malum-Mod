package com.sammy.malum.common.blockentity.storage;

import com.sammy.malum.client.CommonParticleEffects;
import com.sammy.malum.common.blockentity.spirit_altar.IAltarProvider;
import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.common.recipe.AugmentingRecipe;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.ItemHolderBlockEntity;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;

public class ItemPedestalBlockEntity extends ItemHolderBlockEntity implements IAltarProvider {

    public ItemPedestalBlockEntity(BlockEntityType<? extends ItemPedestalBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public ItemPedestalBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.ITEM_PEDESTAL.get(), pos, state);
        inventory = new LodestoneBlockEntityInventory(1, 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    @Override
    public LodestoneBlockEntityInventory getInventoryForAltar() {
        return inventory;
    }

    @Override
    public Vec3 getItemPosForAltar() {
        return getItemPos();
    }

    @Override
    public BlockPos getBlockPosForAltar() {
        return worldPosition;
    }

    public Vec3 getItemPos() {
        return BlockHelper.fromBlockPos(getBlockPos()).add(itemOffset());
    }

    public Vec3 itemOffset() {
        return new Vec3(0.5f, 1.1f, 0.5f);
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (inventory.getStackInSlot(0).getItem() instanceof MalumSpiritItem item) {
                Vec3 pos = getItemPos();
                double x = pos.x;
                double y = pos.y + Math.sin((level.getGameTime() ) / 20f) * 0.1f;
                double z = pos.z;
                CommonParticleEffects.spawnSpiritGlimmerParticles(level, x, y, z, item.type.getColor(), item.type.getEndColor());
            }
        }
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        InteractionResult result = AugmentingRecipe.performAugmentation(this, player, hand);
        if (!result.equals(InteractionResult.PASS)) {
            return result;
        }
        return super.onUse(player, hand);
    }
}
