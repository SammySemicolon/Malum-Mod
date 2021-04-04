package com.sammy.malum.core.init;

import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.blocks.MalumLeavesBlock;
import com.sammy.malum.common.blocks.abstruceblock.AbstruseBlock;
import com.sammy.malum.common.blocks.itempedestal.ItemPedestalBlock;
import com.sammy.malum.common.blocks.itempedestal.ItemPedestalRenderer;
import com.sammy.malum.common.blocks.itemstand.ItemStandBlock;
import com.sammy.malum.common.blocks.itemstand.ItemStandRenderer;
import com.sammy.malum.common.blocks.lighting.EtherBrazierBlock;
import com.sammy.malum.common.blocks.spiritaltar.SpiritAltarRenderer;
import com.sammy.malum.common.blocks.spiritstorage.jar.SpiritJarRenderer;
import com.sammy.malum.common.blocks.totems.TotemPoleRendererModule;
import com.sammy.malum.common.entities.PlayerSoulRenderer;
import com.sammy.malum.common.entities.ScytheBoomerangEntityRenderer;
import com.sammy.malum.common.entities.SpiritSplinterItemRenderer;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
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
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.MalumHelper.brighter;
import static com.sammy.malum.core.init.items.MalumItems.ITEMS;
import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ClientStartupEvents
{
    
    @SubscribeEvent
    public static void bindTERs(FMLClientSetupEvent event)
    {
        //        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ARCANE_CRAFTING_TABLE_TILE_ENTITY.get(), t -> new AdjustableTileEntityRenderer(t,MalumHelper.toArrayList(new ItemModule())));
        //        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_JAR_TILE_ENTITY.get(), t -> new AdjustableTileEntityRenderer(t,MalumHelper.toArrayList(new SpiritHolderRendererModule())));
        //ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_KILN_TILE_ENTITY.get(), t -> new AdjustableTileEntityRenderer(t, new SpiritKilnItemRendererModule(), new SpiritKilnOutputItemRendererModule()));
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.TOTEM_POLE_TILE_ENTITY.get(), t -> new AdjustableTileEntityRenderer(t, new TotemPoleRendererModule()));
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_ALTAR_TILE_ENTITY.get(), SpiritAltarRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ITEM_STAND_TILE_ENTITY.get(), ItemStandRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ITEM_PEDESTAL_TILE_ENTITY.get(), ItemPedestalRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_JAR_TILE_ENTITY.get(), SpiritJarRenderer::new);
    }
    
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(MalumEntities.SPIRIT_ESSENCE.get(), ClientStartupEvents::essenceRenderer);
        RenderingRegistry.registerEntityRenderingHandler(MalumEntities.SCYTHE_BOOMERANG.get(), ClientStartupEvents::scytheRenderer);
        RenderingRegistry.registerEntityRenderingHandler(MalumEntities.PLAYER_SOUL.get(), ClientStartupEvents::playerSoulRenderer);
    }
    
    public static SpiritSplinterItemRenderer essenceRenderer(EntityRendererManager manager)
    {
        return new SpiritSplinterItemRenderer(manager, Minecraft.getInstance().getItemRenderer());
    }
    
    public static ScytheBoomerangEntityRenderer scytheRenderer(EntityRendererManager manager)
    {
        return new ScytheBoomerangEntityRenderer(manager, Minecraft.getInstance().getItemRenderer());
    }
    
    public static PlayerSoulRenderer playerSoulRenderer(EntityRendererManager manager)
    {
        return new PlayerSoulRenderer(manager, Minecraft.getInstance().getItemRenderer());
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
        blockColors.register((state, reader, pos, color) -> {
            Color waterColor = brighter(MalumSpiritTypes.WATER_SPIRIT_COLOR, 2);
            return waterColor.getRed() << 16 | waterColor.getGreen() << 8 | waterColor.getBlue();
        }, MalumBlocks.ABSTRUSE_BLOCK.get());
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
    
        ClientHelper.registerItemColorTint(itemColors, MalumItems.ABSTRUSE_BLOCK, brighter(MalumSpiritTypes.WATER_SPIRIT_COLOR, 2));
    
        ClientHelper.registerItemColor(itemColors, MalumItems.ORANGE_ETHER, brighter(MalumConstants.ORANGE, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.MAGENTA_ETHER, brighter(MalumConstants.MAGENTA, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.LIGHT_BLUE_ETHER, brighter(MalumConstants.LIGHT_BLUE, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.YELLOW_ETHER, brighter(MalumConstants.YELLOW, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.LIME_ETHER, brighter(MalumConstants.LIME, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.CYAN_ETHER, brighter(MalumConstants.CYAN, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.PURPLE_ETHER, brighter(MalumConstants.PURPLE, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.BLUE_ETHER, brighter(MalumConstants.BLUE, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.BROWN_ETHER, brighter(MalumConstants.BROWN, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.GREEN_ETHER, brighter(MalumConstants.GREEN, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.RED_ETHER, brighter(MalumConstants.RED, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.PINK_ETHER, brighter(MalumConstants.PINK, 3));
    
        ClientHelper.registerItemColorTint(itemColors, MalumItems.ORANGE_ETHER_TORCH, brighter(MalumConstants.ORANGE, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.MAGENTA_ETHER_TORCH, brighter(MalumConstants.MAGENTA, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.LIGHT_BLUE_ETHER_TORCH, brighter(MalumConstants.LIGHT_BLUE, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.YELLOW_ETHER_TORCH, brighter(MalumConstants.YELLOW, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.LIME_ETHER_TORCH, brighter(MalumConstants.LIME, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.CYAN_ETHER_TORCH, brighter(MalumConstants.CYAN, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.PURPLE_ETHER_TORCH, brighter(MalumConstants.PURPLE, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.BLUE_ETHER_TORCH, brighter(MalumConstants.BLUE, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.BROWN_ETHER_TORCH, brighter(MalumConstants.BROWN, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.GREEN_ETHER_TORCH, brighter(MalumConstants.GREEN, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.RED_ETHER_TORCH, brighter(MalumConstants.RED, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.PINK_ETHER_TORCH, brighter(MalumConstants.PINK, 3));
    
        ClientHelper.registerItemColorTint(itemColors, MalumItems.ORANGE_ETHER_BRAZIER, brighter(MalumConstants.ORANGE, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.MAGENTA_ETHER_BRAZIER, brighter(MalumConstants.MAGENTA, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.LIGHT_BLUE_ETHER_BRAZIER, brighter(MalumConstants.LIGHT_BLUE, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.YELLOW_ETHER_BRAZIER, brighter(MalumConstants.YELLOW, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.LIME_ETHER_BRAZIER, brighter(MalumConstants.LIME, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.CYAN_ETHER_BRAZIER, brighter(MalumConstants.CYAN, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.PURPLE_ETHER_BRAZIER, brighter(MalumConstants.PURPLE, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.BLUE_ETHER_BRAZIER, brighter(MalumConstants.BLUE, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.BROWN_ETHER_BRAZIER, brighter(MalumConstants.BROWN, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.GREEN_ETHER_BRAZIER, brighter(MalumConstants.GREEN, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.RED_ETHER_BRAZIER, brighter(MalumConstants.RED, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.PINK_ETHER_BRAZIER, brighter(MalumConstants.PINK, 3));
        
        ClientHelper.registerItemColor(itemColors, MalumItems.LIFE_SPIRIT_SPLINTER, brighter(MalumSpiritTypes.LIFE_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.DEATH_SPIRIT_SPLINTER, brighter(MalumSpiritTypes.DEATH_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.AIR_SPIRIT_SPLINTER, brighter(MalumSpiritTypes.AIR_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.WATER_SPIRIT_SPLINTER, brighter(MalumSpiritTypes.WATER_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.MAGIC_SPIRIT_SPLINTER, brighter(MalumSpiritTypes.MAGIC_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.FIRE_SPIRIT_SPLINTER, brighter(MalumSpiritTypes.FIRE_SPIRIT_COLOR,2));
        ClientHelper.registerItemColor(itemColors, MalumItems.EARTH_SPIRIT_SPLINTER, brighter(MalumSpiritTypes.EARTH_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.ELDRITCH_SPIRIT_SPLINTER, MalumSpiritTypes.ELDRITCH_SPIRIT_COLOR);
    }
    
    @SubscribeEvent
    public static void stitchTextures(TextureStitchEvent.Pre event)
    {
        MalumSpiritTypes.SPIRITS.forEach(s -> event.addSprite(MalumHelper.prefix("spirit/" + s.identifier + "_overlay")));
    }
    
    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event)
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TorchBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BoundingBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof IMultiblock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LeavesBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof EtherBrazierBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof AbstruseBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof ItemStandBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof ItemPedestalBlock).forEach(ClientStartupEvents::setCutout);
        setCutout(MalumBlocks.SPIRIT_JAR);
        setCutout(MalumBlocks.TOTEM_CORE);
        setCutout(MalumBlocks.TOTEM_POLE);
        setCutout(MalumBlocks.SPIRIT_ALTAR);
        //        setCutout(MalumBlocks.SPIRIT_JAR);
        //        setCutout(MalumBlocks.SPIRIT_PIPE);
        //        setCutout(MalumBlocks.BLAZE_QUARTZ_ORE);
    }
    
    public static void setCutout(RegistryObject<Block> b)
    {
        RenderTypeLookup.setRenderLayer(b.get(), RenderType.getCutout());
    }
}