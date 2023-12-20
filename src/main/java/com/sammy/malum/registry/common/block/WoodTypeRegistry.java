package com.sammy.malum.registry.common.block;

import com.sammy.malum.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WoodTypeRegistry {

    public static final WoodType RUNEWOOD = WoodType.register(new WoodType("malum:runewood", MalumBlockSetTypes.RUNEWOOD));
    public static final WoodType SOULWOOD = WoodType.register(new WoodType("malum:soulwood", MalumBlockSetTypes.SOULWOOD));

    @Mod.EventBusSubscriber(modid= MalumMod.MALUM, bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void addWoodTypes(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                Sheets.addWoodType(RUNEWOOD);
                Sheets.addWoodType(SOULWOOD);
            });
        }
    }
}