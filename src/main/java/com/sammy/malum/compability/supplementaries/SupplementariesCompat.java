package com.sammy.malum.compability.supplementaries;

import team.lodestar.lodestone.systems.item.tools.magic.MagicKnifeItem;
import net.mehvahdjukaar.supplementaries.common.block.blocks.SconceBlock;
import net.mehvahdjukaar.supplementaries.common.block.blocks.SconceWallBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraftforge.fml.ModList;

import static com.sammy.malum.core.setup.content.block.BlockRegistry.RUNEWOOD_PROPERTIES;
import static com.sammy.malum.core.setup.content.item.ItemRegistry.GEAR_PROPERTIES;
import static com.sammy.malum.core.setup.content.item.ItemTiers.ItemTierEnum.SOUL_STAINED_STEEL;

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
