package com.sammy.malum.core.init.worldgen;

import com.sammy.malum.MalumMod;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MalumSurfaceBuilders
{
    public static final DeferredRegister<SurfaceBuilder<?>> BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, MalumMod.MODID);
    
    static
    {
        BUILDERS.register("sun_kissed", () -> new DefaultSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
    }
}
