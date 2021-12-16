package com.sammy.malum.core.registry.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;

import static net.minecraft.client.renderer.Sheets.SIGN_SHEET;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class WoodTypeRegistry {
    public static ArrayList<WoodType> WOOD_TYPES = new ArrayList<>();
    public static final WoodType RUNEWOOD = new MalumWoodType("runewood");
    public static final WoodType SOULWOOD = new MalumWoodType("soulwood");

    @SubscribeEvent
    public static void addWoodTypes(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            for (WoodType type : WOOD_TYPES) {
                addWoodType(type);
            }
        });
    }

    public static void addWoodType(WoodType woodType) {
        Sheets.SIGN_MATERIALS.put(woodType, new Material(SIGN_SHEET, MalumHelper.prefix("entity/signs/" + woodType.name())));
    }

    static class MalumWoodType extends WoodType {
        public MalumWoodType(String nameIn) {
            super(nameIn);
            WOOD_TYPES.add(this);
        }
    }
}