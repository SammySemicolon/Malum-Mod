package com.sammy.malum.common.block.storage.coffer;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.container.CofferContainer;
import com.sammy.malum.registry.common.block.MalumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntityType;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackBlockHandler;

public class CofferBlockEntity extends LodestoneBlockEntity implements MenuProvider, WorldlyContainer {
    private final LodestoneItemStackBlockHandler handler = new CofferItemStackHandler(this);

    public CofferBlockEntity(LodestoneBlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CofferBlockEntity(BlockPos pos, BlockState state) {
        super(MalumBlockEntities.COFFER.get(), pos, state);
    }

    public LodestoneItemStackBlockHandler getItemHandler() {
        return handler;
    }


    @Override
    public Component getDisplayName() {
        //This is temp, we can replace it later
        return Component.translatable(MalumMod.malumPath("coffer").toLanguageKey());
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return direction != Direction.DOWN;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack itemStack, Direction direction) {
        return direction == Direction.DOWN;
    }

    @Override
    public int getContainerSize() {
        return 54;
    }

    @Override
    public boolean isEmpty() {
        return handler.isEmpty();
    }

    @Override
    public ItemStack getItem(int index) {
        return handler.getStackInSlot(index);
    }

    @Override
    public ItemStack removeItem(int index, int amt) {
        return handler.extractItem(index, amt, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return handler.extractItem(index, handler.getStackInSlot(index).getCount(), false);
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        handler.setStackInSlot(index, itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        handler.clear();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new CofferContainer(id, inventory, ContainerLevelAccess.create(player.level(), getBlockPos()));
    }

    @Override
    public ItemInteractionResult onUse(Player player, InteractionHand pHand) {
        if (player instanceof ServerPlayer serverPlayer)
            serverPlayer.openMenu(this, worldPosition);
        return ItemInteractionResult.SUCCESS;
    }
}
