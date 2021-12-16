package com.sammy.malum.common.item.equipment;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.container.SpiritPouchContainer;
import com.sammy.malum.core.systems.container.ItemInventory;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.Level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.Item.Properties;

public class SpiritPouchItem extends Item implements IDyeableArmorItem {

    public SpiritPouchItem(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new InventoryCapability(stack);
    }

    @Override
    public ActionResult<ItemStack> use(Level LevelIn, Player playerIn, Hand handIn) {
        if (MalumHelper.areWeOnServer(LevelIn))
        {
            ItemStack stack = playerIn.getItemInHand(handIn);
            INamedContainerProvider container =
                    new SimpleNamedContainerProvider((w, p, pl) -> new SpiritPouchContainer(w, p, stack), stack.getHoverName());
            NetworkHooks.openGui((ServerPlayer) playerIn, container, b -> b.writeItem(stack));
            playerIn.level.playSound(null, playerIn.blockPosition(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundCategory.PLAYERS, 1, 1);
        }
        return ActionResult.success(playerIn.getItemInHand(handIn));
    }

    private static class InventoryCapability implements ICapabilityProvider {
        private final LazyOptional<IItemHandler> opt;

        public InventoryCapability(ItemStack stack) {
            opt = LazyOptional.of(() -> new InvWrapper(getInventory(stack)));
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, opt);
        }
    }

    public static ItemInventory getInventory(ItemStack stack) {
        return new ItemInventory(stack, 27);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTagElement("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 11943351;
    }
}