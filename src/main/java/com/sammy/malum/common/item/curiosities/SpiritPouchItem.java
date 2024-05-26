package com.sammy.malum.common.item.curiosities;

import com.sammy.malum.common.container.SpiritPouchContainer;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.registry.common.ContainerRegistry;
import io.github.fabricators_of_create.porting_lib.util.NetworkHooks;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.mixin.screenhandler.NamedScreenHandlerFactoryMixin;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.container.ItemInventory;

import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import java.util.stream.Stream;

public class SpiritPouchItem extends Item {

    public SpiritPouchItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onDestroyed(ItemEntity pItemEntity) {
        Iterator<ItemStack> iter = new Iterator<>() {
            private int i = 0;
            private final ItemInventory inventory = getInventory(pItemEntity.getItem());

            @Override
            public boolean hasNext() {
                return i < inventory.getContainerSize();
            }

            @Override
            public ItemStack next() {
                return inventory.getItem(i++);
            }
        };

        ItemUtils.onContainerDestroyed(pItemEntity, Stream.iterate(iter.next(), t -> iter.hasNext(), t -> iter.next()));
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        ItemStack stack = pSlot.getItem();

        if (pAction != ClickAction.SECONDARY || !(stack.getItem() instanceof SpiritShardItem)) {
            return false;
        } else {
            ItemInventory inventory = getInventory(pStack);

            if (!stack.isEmpty() && stack.getItem().canFitInsideContainerItems()) {
                ItemStack toInsert = pSlot.safeTake(stack.getCount(), Integer.MAX_VALUE, pPlayer);
                ItemStack remainder = inventory.addItem(toInsert);
                pSlot.set(remainder);
                if (remainder.getCount() != toInsert.getCount())
                    pPlayer.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pPlayer.level().getRandom().nextFloat() * 0.4F);
            }

            return true;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if (pAction != ClickAction.SECONDARY || !pSlot.allowModification(pPlayer) || !(pOther.getItem() instanceof SpiritShardItem)) {
            return false;
        } else {
            ItemInventory inventory = getInventory(pStack);

            if (!pOther.isEmpty() && pOther.getItem().canFitInsideContainerItems()) {
                ItemStack remainder = inventory.addItem(pOther.copy());
                if (pOther.getCount() != remainder.getCount())
                    pPlayer.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pPlayer.level().getRandom().nextFloat() * 0.4F);

                pOther.shrink(pOther.getCount() - remainder.getCount());
            }

            return true;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
        if (!level.isClientSide) {
            ItemStack stack = playerIn.getItemInHand(handIn);
            //MenuProvider container = new SimpleMenuProvider((w, p, pl) -> new SpiritPouchContainer(w, p, stack), stack.getHoverName());
            //NetworkHooks.openScreen((ServerPlayer) playerIn, container, b -> b.writeItem(stack));
            NonNullList<ItemStack> stacks = NonNullList.withSize(27, ItemStack.EMPTY);
            CompoundTag tag = stack.getTag();
            if (tag != null) {
                ContainerHelper.loadAllItems(tag, stacks);
            }
            playerIn.openMenu(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                    buf.writeItem(stack);
                }

                @Override
                public Component getDisplayName() {
                    return Component.empty();
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                    return new SpiritPouchContainer(ContainerRegistry.SPIRIT_POUCH.get(), i, inventory, stack);
                }
            });
            playerIn.level().playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1, 1);
        }
        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    }

    public static ItemInventory getInventory(ItemStack stack) {

        var inv = new ItemInventory(stack, 27) {
            @Override
            public boolean canPlaceItem(int pIndex, ItemStack pStack) {
                return pStack.getItem() instanceof SpiritShardItem;
            }
        };

        return inv;
    }
}
