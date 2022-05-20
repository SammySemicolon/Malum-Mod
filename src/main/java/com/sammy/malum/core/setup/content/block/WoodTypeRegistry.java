package com.sammy.malum.core.setup.content.block;

import com.sammy.malum.MalumMod;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid= MalumMod.MALUM, bus= Mod.EventBusSubscriber.Bus.MOD)
public class WoodTypeRegistry {
    public static ArrayList<WoodType> WOOD_TYPES = new ArrayList<>();
    public static final WoodType RUNEWOOD = WoodType.register(new MalumWoodType("runewood"));
    public static final WoodType SOULWOOD = WoodType.register(new MalumWoodType("soulwood"));
    
    static class MalumWoodType extends WoodType {
        public MalumWoodType(String nameIn) {
            super("malum:" + nameIn);
            WOOD_TYPES.add(this);
        }
    }
}