package com.sammy.malum.common.container;

import com.sammy.malum.common.blockentity.weaver.WeaversWorkbenchBlockEntity;
import com.sammy.malum.common.item.cosmetic.AbstractWeaveItem;
import com.sammy.malum.common.item.equipment.armor.SoulHunterArmorItem;
import com.sammy.malum.common.item.equipment.armor.SoulStainedSteelArmorItem;
import com.sammy.malum.common.item.spirit.SpiritPouchItem;
import com.sammy.malum.registry.common.ContainerRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class WeaversWorkbenchContainer extends AbstractContainerMenu {

    //private final Container inventory;
    public static net.minecraft.network.chat.Component component = new TextComponent("Weaver's Workbench");

    public WeaversWorkbenchContainer(int windowId, Inventory playerInv, BlockEntity entity) {
        super(ContainerRegistry.WEAVERS_WORKBENCH.get(), windowId);

        if(entity instanceof WeaversWorkbenchBlockEntity){
            entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(c ->{
                addSlot(new SlotItemHandler(c, 0, 18, 52){
                    @Override
                    public boolean mayPickup(Player pPlayer) {
                        return true;
                    }

                    @Override
                    public boolean mayPlace(ItemStack pStack) {
                        return pStack.getItem() instanceof SoulHunterArmorItem || pStack.getItem() instanceof SoulStainedSteelArmorItem;
                    }
                });
                addSlot(new SlotItemHandler(c, 1, 54, 52){
                    @Override
                    public boolean mayPickup(Player pPlayer) {
                        return true;
                    }

                    @Override
                    public boolean mayPlace(ItemStack pStack) {
                        return pStack.getItem() instanceof AbstractWeaveItem;
                    }
                });
                addSlot(new SlotItemHandler(c, 2, 90, 52){
                    @Override
                    public boolean mayPickup(Player pPlayer) {
                        return true;
                    }

                    @Override
                    public boolean mayPlace(ItemStack pStack) {
                        return false;
                    }
                });
            });
        }

//        this.inventory = inventory;
//        inventory.startOpen(playerInv.player);

        int offset = offset();
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInv, j1 + (l + 1) * 9, 8 + j1 * 18, offset + 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, offset + 142));
        }
    }

    public int offset() {
        return 0;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    public static MenuConstructor getServerContainer(WeaversWorkbenchBlockEntity entity, BlockPos pos){
        return (id, playerInv, player) -> new WeaversWorkbenchContainer(id, playerInv, entity);

    }
}
