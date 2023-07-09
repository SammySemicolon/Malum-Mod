package com.sammy.malum.common.block.storage;

import com.sammy.malum.client.ParticleEffects;
import com.sammy.malum.core.systems.item.ISoulContainerItem;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.ItemHelper;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class SoulVialBlockEntity extends LodestoneBlockEntity {

    public MalumEntitySpiritData data;

    public SoulVialBlockEntity(BlockEntityType<? extends SoulVialBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SoulVialBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.SOUL_VIAL.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        if (data != null) {
            data.saveTo(pTag);
        }
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        if (pTag.contains(MalumEntitySpiritData.SOUL_DATA)) {
            data = MalumEntitySpiritData.load(pTag);
        } else {
            data = null;
        }
        super.load(pTag);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof ISoulContainerItem) {
            if (data == null) {
                if (stack.hasTag() && stack.getTag().contains(MalumEntitySpiritData.SOUL_DATA)) {
                    data = MalumEntitySpiritData.load(stack.getTag());
                    if (stack.getCount() > 1) {
                        ItemStack split = stack.split(1);
                        split.getOrCreateTag().remove(MalumEntitySpiritData.SOUL_DATA);
                        ItemHelper.giveItemToEntity(split, player);
                    } else {
                        stack.getOrCreateTag().remove(MalumEntitySpiritData.SOUL_DATA);
                    }
                }
            } else {
                if (!stack.getOrCreateTag().contains(MalumEntitySpiritData.SOUL_DATA)) {
                    if (stack.getCount() > 1) {
                        ItemStack split = stack.split(1);
                        data.saveTo(split.getOrCreateTag());
                        data = null;
                        ItemHelper.giveItemToEntity(split, player);
                    } else {
                        data.saveTo(stack.getOrCreateTag());
                        data = null;
                    }
                }
            }
            player.swing(hand, true);
            BlockStateHelper.updateAndNotifyState(level, worldPosition);
            return InteractionResult.SUCCESS;
        }
        return super.onUse(player, hand);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onPlace(LivingEntity placer, ItemStack stack) {
        if (stack.hasTag()) {
            load(stack.getTag());
        }
        setChanged();
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (data != null) {
                double y = 0.5f + Math.sin(level.getGameTime() / 20f) * 0.08f;
                ParticleEffects.spawnSoulParticles(level, worldPosition.getX() + 0.5f, worldPosition.getY() + y, worldPosition.getZ() + 0.5f, 1, 0.75f, Vec3.ZERO, data.primaryType.getPrimaryColor(), data.primaryType.getSecondaryColor());
            }
        }
    }
}