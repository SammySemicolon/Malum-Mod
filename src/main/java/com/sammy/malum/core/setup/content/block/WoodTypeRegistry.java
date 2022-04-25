package com.sammy.malum.core.setup.content.block;

import com.sammy.malum.MalumMod;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

import static net.minecraft.client.renderer.Sheets.SIGN_SHEET;

@Mod.EventBusSubscriber(modid= MalumMod.MALUM, bus= Mod.EventBusSubscriber.Bus.MOD)
public class WoodTypeRegistry {
    public static ArrayList<WoodType> WOOD_TYPES = new ArrayList<>();
    public static final WoodType RUNEWOOD = WoodType.register(new MalumWoodType("runewood"));
    public static final WoodType SOULWOOD = WoodType.register(new MalumWoodType("soulwood"));

    @SubscribeEvent
    public static void addWoodTypes(EntityRenderersEvent.RegisterRenderers event) {
        for (WoodType type : WOOD_TYPES) {
            addWoodType(type);
        }
    }

    public static void addWoodType(WoodType woodType) {
        Sheets.SIGN_MATERIALS.put(woodType, new Material(SIGN_SHEET, MalumMod.prefix("entity/signs/" + woodType.name())));
    }

    static class MalumWoodType extends WoodType {
        public MalumWoodType(String nameIn) {
            super(nameIn);
            WOOD_TYPES.add(this);
        }
    }
}