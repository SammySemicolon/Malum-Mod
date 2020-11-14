package com.sammy.malum.init;

import com.sammy.malum.events.customevents.SpiritHarvestEvent;
import com.sammy.malum.events.customevents.SpiritIntegrityUpdateEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;

public class ModEventFactory extends ForgeEventFactory
{
    public static SpiritHarvestEvent.Pre preSpiritHarvest(LivingEntity target, PlayerEntity playerEntity)
    {
        SpiritHarvestEvent.Pre event = new SpiritHarvestEvent.Pre(target, playerEntity);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
    
    public static SpiritHarvestEvent.Post postSpiritHarvest(LivingEntity target, PlayerEntity playerEntity, int spiritCount)
    {
        SpiritHarvestEvent.Post event = new SpiritHarvestEvent.Post(target, playerEntity, spiritCount);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
    
    public static SpiritIntegrityUpdateEvent.Decrease decreaseSpiritIntegrity(ItemStack stack, PlayerEntity playerEntity, int integrityChange)
    {
        SpiritIntegrityUpdateEvent.Decrease event = new SpiritIntegrityUpdateEvent.Decrease(stack, playerEntity, integrityChange);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
    
    public static SpiritIntegrityUpdateEvent.Fill fillSpiritIntegrity(ItemStack stack, PlayerEntity playerEntity, int integrityChange)
    {
        SpiritIntegrityUpdateEvent.Fill event = new SpiritIntegrityUpdateEvent.Fill(stack, playerEntity, integrityChange);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
}