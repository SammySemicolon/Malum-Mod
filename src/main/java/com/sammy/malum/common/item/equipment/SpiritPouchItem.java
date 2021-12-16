package com.sammy.malum.common.item.equipment;

import com.sammy.malum.common.container.SpiritPouchContainer;
import com.sammy.malum.core.systems.container.ItemInventory;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SpiritPouchItem extends Item {

    public SpiritPouchItem(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new InventoryCapability(stack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
        if (!level.isClientSide)
        {
            ItemStack stack = playerIn.getItemInHand(handIn);
            MenuProvider container =
                    new SimpleMenuProvider((w, p, pl) -> new SpiritPouchContainer(w, p, stack), stack.getHoverName());
            NetworkHooks.openGui((ServerPlayer) playerIn, container, b -> b.writeItem(stack));
            playerIn.level.playSound(null, playerIn.blockPosition(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1, 1);
        }
        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
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
}