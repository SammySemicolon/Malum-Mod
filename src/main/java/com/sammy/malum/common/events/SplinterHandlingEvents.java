package com.sammy.malum.common.events;

import com.sammy.malum.common.items.tools.scythes.ScytheItem;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.spirits.item.ISpiritHolderItem;
import com.sammy.malum.core.systems.spirits.item.SpiritSplinterItem;
import com.sammy.malum.server.network.packets.SplinterHandlePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.sammy.malum.server.network.NetworkManager.INSTANCE;

@SuppressWarnings("all")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SplinterHandlingEvents
{
    
    @SubscribeEvent
    public static void onRightClick(GuiScreenEvent.MouseClickedEvent.Pre event)
    {
        Minecraft mc = Minecraft.getInstance();
        Screen gui = event.getGui();
        
        if (gui instanceof ContainerScreen)
        {
            ContainerScreen container = (ContainerScreen) gui;
            Slot under = container.getSlotUnderMouse();
            ItemStack heldStack = mc.player.inventory.getItemStack();
            if (container.hasShiftDown())
            {
                if (under != null && !heldStack.isEmpty() && under.inventory == mc.player.inventory)
                {
                    ItemStack underStack = under.getStack();
                    if (heldStack.getItem() instanceof SpiritSplinterItem && underStack.getItem() instanceof ISpiritHolderItem)
                    {
                        SpiritSplinterItem item = (SpiritSplinterItem) heldStack.getItem();
                        int remaining = SpiritHelper.giveStackSpirit(underStack, item.type.identifier, heldStack.getCount());
                        if (remaining == heldStack.getCount())
                        {
                        
                        }
                        heldStack.setCount(remaining);
                        INSTANCE.send(PacketDistributor.SERVER.noArg(), new SplinterHandlePacket(heldStack,underStack, under.getSlotIndex()));
                        mc.player.inventory.setItemStack(ItemStack.EMPTY);
                        container.getContainer().detectAndSendChanges();
                        
                        event.setCanceled(true);
    
                    }
                    /*
                    if (heldStack.getItem() instanceof ISpiritHolderItem && underStack.getItem() instanceof SpiritSplinterItem)
                    {
                        INSTANCE.send(PacketDistributor.SERVER.noArg(), new SplinterHandlePacket(VAULT_TO_SPLINTER, under.getSlotIndex()));
    
                        event.setCanceled(true);
                    }
                    if (heldStack.getItem() instanceof ISpiritHolderItem && underStack.getItem() instanceof ISpiritHolderItem)
                    {
                        INSTANCE.send(PacketDistributor.SERVER.noArg(), new SplinterHandlePacket(VAULT_TO_VAULT, under.getSlotIndex()));
    
                        event.setCanceled(true);
                    }
                    if (heldStack.getItem() instanceof ISpiritHolderItem && underStack.isEmpty())
                    {
                        INSTANCE.send(PacketDistributor.SERVER.noArg(), new SplinterHandlePacket(VAULT_TO_EMPTY, under.getSlotIndex()));
                        event.setCanceled(true);
                    }*/
                }
            }
        }
    }
    @SubscribeEvent
    public static void onEntityKill(LivingDeathEvent event)
    {
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity attacker = (PlayerEntity) event.getSource().getTrueSource();
            ItemStack stack = ItemStack.EMPTY;
            if (attacker.swingingHand != null)
            {
                stack = attacker.getHeldItem(attacker.swingingHand);
            }
            if (attacker.isHandActive() && stack.isEmpty())
            {
                stack = attacker.getActiveItemStack();
            }
            Item item = stack.getItem();
            if (item instanceof ScytheItem)
            {
                SpiritHelper.summonSpirits(event.getEntityLiving(), attacker);
            }
        }
    }
}
