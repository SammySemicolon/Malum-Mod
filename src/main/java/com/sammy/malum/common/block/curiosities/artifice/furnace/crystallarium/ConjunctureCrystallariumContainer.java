package com.sammy.malum.common.block.curiosities.artifice.furnace.crystallarium;

import com.sammy.malum.common.block.curiosities.artifice.furnace.MalumFurnaceBlockItemStackHandler;
import com.sammy.malum.registry.common.MalumContainers;
import com.sammy.malum.registry.common.MalumDataMaps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneBlockEntityContainer;

//TODO make sure shift clicking fuel moves to fuel slot
public class ConjunctureCrystallariumContainer extends LodestoneBlockEntityContainer<ConjunctureCrystallariumBlockEntity> {

    public static final Component CONJUNCTURE_CRYSTALLARIUM = Component.translatable("container.malum.conjuncture_crystallarium");
    private final ContainerData data;

    public ConjunctureCrystallariumContainer(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf data) {
        this(containerId, playerInventory, ContainerLevelAccess.create(playerInventory.player.level(), data.readBlockPos()), new SimpleContainerData(4));
    }

    public ConjunctureCrystallariumContainer(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL, new SimpleContainerData(4));
    }

    public ConjunctureCrystallariumContainer(int containerId, Inventory playerInventory, final ContainerLevelAccess access, ContainerData dataAccess) {
        super(MalumContainers.CONJUNCTURE_CRYSTALLARIUM.get(), containerId, playerInventory, access);
        this.data = dataAccess;
        if (blockEntity != null) {
            var itemHandler = getItemStackHandler();
            addSlot(new SlotItemHandler(itemHandler, 1, 80, 73));
            addSlot(new SlotItemHandler(itemHandler, itemHandler.getFuelSlot(), 80, 109) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return isFuel(stack);
                }
            });

            for (int i = 0; i < 3; i++) {
                addSlot(new SlotItemHandler(itemHandler, 2 + i, 135, 39 + 17 * i) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                });

            }

            this.addDataSlots(dataAccess);
        }
    }

    //TODO handle next 4 methods in MalumAbstractFurnaceContainer later
    private boolean isFuel(ItemStack stack) {
        return stack.getItemHolder().getData(MalumDataMaps.CONJUNCTURE_CRYSTALLARIUM_FUEL) != null;
    }

    public float getBurnProgress() {
        int cookingProgress = data.get(2);
        int cookingTimeTotal = data.get(3);
        return cookingTimeTotal != 0 && cookingProgress != 0 ? Mth.clamp((float) cookingProgress / (float) cookingTimeTotal, 0.0F, 1.0F) : 0.0F;
    }

    public float getLitProgress() {
        int litDuration = data.get(1);
        if (litDuration == 0) {
            litDuration = 200;
        }

        return Mth.clamp((float) data.get(0) / (float) litDuration, 0.0F, 1.0F);
    }

    public boolean isLit() {
        return data.get(0) > 0;
    }

    @Override
    public int[] getPlayerInventoryTopLeft() {
        return new int[]{8, 144};
    }

    @Override
    public Class<ConjunctureCrystallariumBlockEntity> getBlockEntityClass() {
        return ConjunctureCrystallariumBlockEntity.class;
    }

    @Override
    public MalumFurnaceBlockItemStackHandler getItemStackHandler() {
        return blockEntity.inventory();
    }

    @Override
    public boolean stillValid(Player player) {
        return true; //TODO: The usual stuff
    }
}