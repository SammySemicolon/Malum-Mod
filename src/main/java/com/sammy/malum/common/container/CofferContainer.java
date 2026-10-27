package com.sammy.malum.common.container;

import com.sammy.malum.common.block.storage.coffer.CofferBlockEntity;
import com.sammy.malum.common.block.storage.coffer.CofferItemStackHandler;
import com.sammy.malum.registry.common.MalumContainers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.neoforged.neoforge.items.SlotItemHandler;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneBlockEntityContainer;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackBlockHandler;

public class CofferContainer extends LodestoneBlockEntityContainer<CofferBlockEntity> {
    public CofferContainer(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf data) {
        this(containerId, playerInventory, ContainerLevelAccess.create(playerInventory.player.level(), data.readBlockPos()));
    }

    public CofferContainer(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public CofferContainer(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(MalumContainers.COFFER.get(), containerId, playerInventory, access);
        var handler = getItemStackHandler();
        for (int y = 0; y < 6; ++y)
            for (int x = 0; x < 9; ++x)
                this.addSlot(new SlotItemHandler(handler, x + y * 9, 8 + x * 18, 18 + y * 18));
    }

    @Override
    public int[] getPlayerInventoryTopLeft() {
        return new int[]{8, 157};
    }

    @Override
    public Class<CofferBlockEntity> getBlockEntityClass() {
        return CofferBlockEntity.class;
    }

    @Override
    public CofferItemStackHandler getItemStackHandler() {
        return blockEntity.getItemHandler();
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity.stillValid(player);
    }

    public void togglePin(int index) {
        getItemStackHandler().togglePin(index);
    }

    public boolean checkPin(int index) {
        return getItemStackHandler().checkPin(index);
    }
}