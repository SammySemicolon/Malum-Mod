package com.sammy.malum.core.init;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.MalumLeavesBlock;
import com.sammy.malum.common.blocks.abstruceblock.AbstruseBlock;
import com.sammy.malum.common.blocks.arcanecraftingtable.ArcaneCraftingTableRenderer;
import com.sammy.malum.common.blocks.zoomrock.ZoomRockBlock;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.worldgen.MalumFeatures;
import com.sammy.malum.core.systems.multiblock.BoundingBlock;
import com.sammy.malum.core.systems.multiblock.IMultiblock;
import com.sammy.malum.core.systems.recipes.ArcaneCraftingRecipe;
import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;
import static com.sammy.malum.core.init.MalumItems.ITEMS;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StartupEvents
{
    @SubscribeEvent
    public static void setBlockColors(ColorHandlerEvent.Block event)
    {
        BlockColors blockColors = event.getBlockColors();
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        MalumHelper.takeAll(blocks, block -> block.get() instanceof MalumLeavesBlock).forEach(block -> blockColors.register((state, reader, pos, color) -> {
            float i = state.get(MalumLeavesBlock.COLOR);
            MalumLeavesBlock malumLeavesBlock = (MalumLeavesBlock) block.get();
            int r = (int) MathHelper.lerp(i / 9f, malumLeavesBlock.minColor.getRed(), malumLeavesBlock.maxColor.getRed());
            int g = (int) MathHelper.lerp(i / 9f, malumLeavesBlock.minColor.getGreen(), malumLeavesBlock.maxColor.getGreen());
            int b = (int) MathHelper.lerp(i / 9f, malumLeavesBlock.minColor.getBlue(), malumLeavesBlock.maxColor.getBlue());
            return r << 16 | g << 8 | b;
        }, block.get()));
    
    }
    @SubscribeEvent
    public static void setItemColors(ColorHandlerEvent.Item event)
    {
        ItemColors blockColors = event.getItemColors();
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        MalumHelper.takeAll(items, item -> item.get() instanceof BlockItem && ((BlockItem) item.get()).getBlock() instanceof MalumLeavesBlock).forEach(item -> {
            MalumLeavesBlock malumLeavesBlock = (MalumLeavesBlock) ((BlockItem) item.get()).getBlock();
            int r = malumLeavesBlock.minColor.getRed();
            int g = malumLeavesBlock.minColor.getGreen();
            int b = malumLeavesBlock.minColor.getBlue();
            blockColors.register((stack, i) -> r << 16 | g << 8 | b, item.get());
        });
    }
    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event)
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BoundingBlock).forEach(StartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof IMultiblock).forEach(StartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(StartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(StartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(StartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LeavesBlock).forEach(StartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(StartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(StartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof ZoomRockBlock).forEach(StartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof AbstruseBlock).forEach(StartupEvents::setCutout);
    }
    public static void setCutout(RegistryObject<Block> b)
    {
        RenderTypeLookup.setRenderLayer(b.get(), RenderType.getCutout());
    }
    @SubscribeEvent
    public static void registerRecipes(FMLCommonSetupEvent event)
    {
        ArcaneCraftingRecipe.initRecipes();
    }
    @SubscribeEvent
    public static void registerFeatures(FMLCommonSetupEvent event)
    {
        event.enqueueWork(MalumFeatures::new);
    }
    @SubscribeEvent
    public static void bindTERs(FMLClientSetupEvent event)
    {
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ARCANE_CRAFTING_TABLE_TILE_ENTITY.get(), ArcaneCraftingTableRenderer::new);
    }
}
