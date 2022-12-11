package com.sammy.malum.compability.supplementaries;

import net.mehvahdjukaar.supplementaries.common.block.blocks.SconceBlock;
import net.mehvahdjukaar.supplementaries.common.block.blocks.SconceWallBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.fml.ModList;

import static com.sammy.malum.registry.common.block.BlockRegistry.RUNEWOOD_PROPERTIES;

public class SupplementariesCompat {

    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("supplementaries");
    }
    public static class LoadedOnly {

        public static Block makeBlazingSconce() {
            return new SconceBlock(RUNEWOOD_PROPERTIES().sound(SoundType.LANTERN).isCutoutLayer().noCollission().instabreak().lightLevel((b) -> 14), ()->ParticleTypes.FLAME);
        }

        public static Block makeBlazingWallSconce() {
            return new SconceWallBlock(RUNEWOOD_PROPERTIES().sound(SoundType.LANTERN).isCutoutLayer().noCollission().instabreak().lightLevel((b) -> 14), ()->ParticleTypes.FLAME);
        }
    }
}
