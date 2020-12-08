package com.sammy.malum.core.init.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AddFeaturesToBiomes
{
    @SubscribeEvent
    public static void PreventPlacingBlockOnBottomSlab(PlayerInteractEvent event)
    {
        if (event.getFace() != Direction.UP)
        {
            return;
        }
        BlockState blockState = event.getWorld().getBlockState(event.getPos());
        Block block = blockState.getBlock();
        if (block instanceof SlabBlock)
        {
            if (blockState.hasProperty(SlabBlock.TYPE))
            {
                if (blockState.get(SlabBlock.TYPE).equals(SlabType.BOTTOM))
                {
                    event.setCanceled(true);
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event)
    {
        event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> MalumFeatures.SUN_SPOT);
    }
}
