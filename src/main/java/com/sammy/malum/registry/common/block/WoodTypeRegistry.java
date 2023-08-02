package com.sammy.malum.registry.common.block;

import com.sammy.malum.MalumMod;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

public class WoodTypeRegistry {
    public static final WoodType RUNEWOOD = WoodType.register(new WoodType("runewood", MalumBlockSetTypes.RUNEWOOD));
    public static final WoodType SOULWOOD = WoodType.register(new WoodType("soulwood", MalumBlockSetTypes.SOULWOOD));
}
