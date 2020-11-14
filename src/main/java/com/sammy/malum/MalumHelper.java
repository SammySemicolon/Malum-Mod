package com.sammy.malum;

import net.minecraft.util.ResourceLocation;

import static com.sammy.malum.MalumMod.MODID;

public class MalumHelper
{
    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MODID, path);
    }
}
