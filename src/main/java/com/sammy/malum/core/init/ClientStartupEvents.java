package com.sammy.malum.core.init;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.common.blocks.MalumLeavesBlock;
import com.sammy.malum.common.blocks.abstruceblock.AbstruseBlock;
import com.sammy.malum.common.blocks.itemstand.ItemStandItemRendererModule;
import com.sammy.malum.common.blocks.spiritkiln.SpiritKilnItemRendererModule;
import com.sammy.malum.common.blocks.spiritkiln.SpiritKilnDebugTextRendererModule;
import com.sammy.malum.common.entities.SpiritSplinterItemRenderer;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.spirits.MalumSpiritTypes;
import com.sammy.malum.core.systems.multiblock.BoundingBlock;
import com.sammy.malum.core.systems.multiblock.IMultiblock;
import com.sammy.malum.core.systems.tileentityrendering.AdjustableTileEntityRenderer;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.core.init.MalumItems.ITEMS;
import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ClientStartupEvents
{
    
    @SubscribeEvent
    public static void bindTERs(FMLClientSetupEvent event)
    {
        //        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ARCANE_CRAFTING_TABLE_TILE_ENTITY.get(), t -> new AdjustableTileEntityRenderer(t,MalumHelper.toArrayList(new ItemModule())));
        //        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_JAR_TILE_ENTITY.get(), t -> new AdjustableTileEntityRenderer(t,MalumHelper.toArrayList(new SpiritHolderRendererModule())));
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_KILN_TILE_ENTITY.get(), t -> new AdjustableTileEntityRenderer(t, MalumHelper.toArrayList(new SpiritKilnItemRendererModule(), new SpiritKilnDebugTextRendererModule())));
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ITEM_STAND_TILE_ENTITY.get(), t -> new AdjustableTileEntityRenderer(t, MalumHelper.toArrayList(new ItemStandItemRendererModule())));
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(MalumEntities.SPIRIT_ESSENCE.get(), ClientStartupEvents::essenceRenderer);
    }
    
    public static SpiritSplinterItemRenderer essenceRenderer(EntityRendererManager manager)
    {
        return new SpiritSplinterItemRenderer(manager, Minecraft.getInstance().getItemRenderer());
    }
    
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
        ItemColors itemColors = event.getItemColors();
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        MalumHelper.takeAll(items, item -> item.get() instanceof BlockItem && ((BlockItem) item.get()).getBlock() instanceof MalumLeavesBlock).forEach(item -> {
            MalumLeavesBlock malumLeavesBlock = (MalumLeavesBlock) ((BlockItem) item.get()).getBlock();
            ClientHelper.registerItemColor(itemColors, item, malumLeavesBlock.minColor);
        });
        ClientHelper.registerItemColor(itemColors, MalumItems.WILD_SPIRIT_SPLINTER, MalumSpiritTypes.WILD_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.UNDEAD_SPIRIT_SPLINTER, MalumSpiritTypes.UNDEAD_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.NIMBLE_SPIRIT_SPLINTER, MalumSpiritTypes.NIMBLE_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.AQUATIC_SPIRIT_SPLINTER, MalumSpiritTypes.AQUATIC_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.SINISTER_SPIRIT_SPLINTER, MalumSpiritTypes.SINISTER_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.ARCANE_SPIRIT_SPLINTER, MalumSpiritTypes.ARCANE_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.SULPHURIC_SPIRIT_SPLINTER, MalumSpiritTypes.SULPHURIC_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.NETHERBORNE_SPIRIT_SPLINTER, MalumSpiritTypes.NETHERBORNE_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.REMEDIAL_SPIRIT_SPLINTER, MalumSpiritTypes.AVARICIOUS_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.FUSIBLE_SPIRIT_SPLINTER, MalumSpiritTypes.FUSIBLE_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.TERMINUS_SPIRIT_SPLINTER, MalumSpiritTypes.TERMINUS_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.ELDRITCH_SPIRIT_SPLINTER, MalumSpiritTypes.ELDRITCH_SPIRIT_COLOR);
    }
    
    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event)
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BoundingBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof IMultiblock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LeavesBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof AbstruseBlock).forEach(ClientStartupEvents::setCutout);
        setCutout(MalumBlocks.ITEM_STAND);
//        setCutout(MalumBlocks.SPIRIT_JAR);
//        setCutout(MalumBlocks.SPIRIT_PIPE);
//        setCutout(MalumBlocks.BLAZE_QUARTZ_ORE);
    }
    public static void setCutout(RegistryObject<Block> b)
    {
        RenderTypeLookup.setRenderLayer(b.get(), RenderType.getCutout());
    }
}