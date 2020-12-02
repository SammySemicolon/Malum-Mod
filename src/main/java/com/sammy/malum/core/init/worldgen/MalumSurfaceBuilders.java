package com.sammy.malum.core.init.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class MalumSurfaceBuilders
{
    public static final DeferredRegister<SurfaceBuilder<?>> BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, MalumMod.MODID);
    
    static
    {
        BUILDERS.register("sun_kissed", () -> new DefaultSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
    }
}
