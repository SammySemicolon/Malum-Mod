package com.sammy.malum.core.systems.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class SimpleBlockEntity extends BlockEntity {
    public SimpleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void onBreak() {
        invalidateCaps();
    }
    public void onPlace(LivingEntity placer, ItemStack stack) {
    }

    public ItemStack onClone(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return ItemStack.EMPTY;
    }
    public InteractionResult onUse(Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            super.handleUpdateTag(tag);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this); // (this.worldPosition, 3, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(getUpdatePacket().getTag());
    }

    public void tick()
    {

    }
}