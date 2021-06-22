package com.sammy.malum.core.init.events;

import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.blocks.MalumLeavesBlock;
import com.sammy.malum.common.blocks.itempedestal.ItemPedestalBlock;
import com.sammy.malum.common.blocks.itempedestal.ItemPedestalRenderer;
import com.sammy.malum.common.blocks.itemstand.ItemStandBlock;
import com.sammy.malum.common.blocks.itemstand.ItemStandRenderer;
import com.sammy.malum.common.blocks.lighting.EtherBrazierBlock;
import com.sammy.malum.common.blocks.runetable.RuneTableBlock;
import com.sammy.malum.common.blocks.spiritaltar.SpiritAltarRenderer;
import com.sammy.malum.common.blocks.spiritjar.SpiritJarRenderer;
import com.sammy.malum.common.blocks.totem.TotemBaseBlock;
import com.sammy.malum.common.blocks.totem.pole.TotemPoleBlock;
import com.sammy.malum.common.blocks.totem.pole.TotemPoleRenderer;
import com.sammy.malum.common.entities.boomerang.ScytheBoomerangEntityRenderer;
import com.sammy.malum.common.entities.spirit.SpiritItemRenderer;
import com.sammy.malum.core.init.MalumEntities;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.multiblock.BoundingBlock;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.MalumColors.*;
import static com.sammy.malum.MalumHelper.brighter;
import static com.sammy.malum.MalumHelper.darker;
import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;
import static com.sammy.malum.core.init.items.MalumItems.ITEMS;
import static com.sammy.malum.core.modcontent.MalumSpiritTypes.*;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ClientStartupEvents
{
    @SubscribeEvent
    public static void bindTERs(FMLClientSetupEvent event)
    {
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_ALTAR_TILE_ENTITY.get(), SpiritAltarRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.TOTEM_POLE_TILE_ENTITY.get(), TotemPoleRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ITEM_STAND_TILE_ENTITY.get(), ItemStandRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ITEM_PEDESTAL_TILE_ENTITY.get(), ItemPedestalRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_JAR_TILE_ENTITY.get(), SpiritJarRenderer::new);
    }
    
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(MalumEntities.SPIRIT_ESSENCE.get(), ClientStartupEvents::essenceRenderer);
        RenderingRegistry.registerEntityRenderingHandler(MalumEntities.SCYTHE_BOOMERANG.get(), ClientStartupEvents::scytheRenderer);
    }
    
    public static SpiritItemRenderer essenceRenderer(EntityRendererManager manager)
    {
        return new SpiritItemRenderer(manager, Minecraft.getInstance().getItemRenderer());
    }
    
    public static ScytheBoomerangEntityRenderer scytheRenderer(EntityRendererManager manager)
    {
        return new ScytheBoomerangEntityRenderer(manager, Minecraft.getInstance().getItemRenderer());
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

        ClientHelper.registerItemColor(itemColors, MalumItems.ORANGE_ETHER, brighter(ORANGE, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.MAGENTA_ETHER, brighter(MAGENTA, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.LIGHT_BLUE_ETHER, brighter(LIGHT_BLUE, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.YELLOW_ETHER, brighter(YELLOW, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.LIME_ETHER, brighter(LIME, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.CYAN_ETHER, brighter(CYAN, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.PURPLE_ETHER, brighter(PURPLE, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.BLUE_ETHER, brighter(BLUE, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.BROWN_ETHER, brighter(BROWN, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.GREEN_ETHER, brighter(GREEN, 1));
        ClientHelper.registerItemColor(itemColors, MalumItems.RED_ETHER, brighter(RED, 3));
        ClientHelper.registerItemColor(itemColors, MalumItems.PINK_ETHER, brighter(PINK, 3));
    
        ClientHelper.registerItemColorTint(itemColors, MalumItems.ORANGE_ETHER_TORCH, brighter(ORANGE, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.MAGENTA_ETHER_TORCH, brighter(MAGENTA, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.LIGHT_BLUE_ETHER_TORCH, brighter(LIGHT_BLUE, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.YELLOW_ETHER_TORCH, brighter(YELLOW, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.LIME_ETHER_TORCH, brighter(LIME, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.CYAN_ETHER_TORCH, brighter(CYAN, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.PURPLE_ETHER_TORCH, brighter(PURPLE, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.BLUE_ETHER_TORCH, brighter(BLUE, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.BROWN_ETHER_TORCH, brighter(BROWN, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.GREEN_ETHER_TORCH, brighter(GREEN, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.RED_ETHER_TORCH, brighter(RED, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.PINK_ETHER_TORCH, brighter(PINK, 3));
    
        ClientHelper.registerItemColorTint(itemColors, MalumItems.ORANGE_ETHER_BRAZIER, brighter(ORANGE, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.MAGENTA_ETHER_BRAZIER, brighter(MAGENTA, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.LIGHT_BLUE_ETHER_BRAZIER, brighter(LIGHT_BLUE, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.YELLOW_ETHER_BRAZIER, brighter(YELLOW, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.LIME_ETHER_BRAZIER, brighter(LIME, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.CYAN_ETHER_BRAZIER, brighter(CYAN, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.PURPLE_ETHER_BRAZIER, brighter(PURPLE, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.BLUE_ETHER_BRAZIER, brighter(BLUE, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.BROWN_ETHER_BRAZIER, brighter(BROWN, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.GREEN_ETHER_BRAZIER, brighter(GREEN, 1));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.RED_ETHER_BRAZIER, brighter(RED, 3));
        ClientHelper.registerItemColorTint(itemColors, MalumItems.PINK_ETHER_BRAZIER, brighter(PINK, 3));

        ClientHelper.registerItemColor(itemColors, MalumItems.SACRED_SPIRIT, brighter(SACRED_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.WICKED_SPIRIT, WICKED_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.ARCANE_SPIRIT, brighter(ARCANE_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.ELDRITCH_SPIRIT, darker(ELDRITCH_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.AERIAL_SPIRIT, brighter(AERIAL_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.AQUATIC_SPIRIT, brighter(AQUATIC_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.INFERNAL_SPIRIT, brighter(INFERNAL_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.EARTHEN_SPIRIT, brighter(EARTHEN_SPIRIT_COLOR,1));
        ClientHelper.registerItemColor(itemColors, MalumItems.BRILLIANCE_SPIRIT, brighter(BRILLIANCE_SPIRIT_COLOR,1));
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void stitchTextures(TextureStitchEvent.Pre event)
    {
        if (!event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE))
        {
            return;
        }
        SPIRITS.forEach(s ->
        {
            event.addSprite(MalumHelper.prefix("spirit/" + "overlay_" + s.identifier));
            event.addSprite(MalumHelper.prefix("spirit/" + "cutout_" + s.identifier));
        });
    }
    
    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event)
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TorchBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BoundingBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LeavesBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof EtherBrazierBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof ItemStandBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof ItemPedestalBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TotemBaseBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TotemPoleBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof RuneTableBlock).forEach(ClientStartupEvents::setCutout);
        setCutout(MalumBlocks.SPIRIT_JAR);
        setCutout(MalumBlocks.SPIRIT_ALTAR);
        setCutout(MalumBlocks.BLAZING_QUARTZ_ORE);
    }
    
    public static void setCutout(RegistryObject<Block> b)
    {
        RenderTypeLookup.setRenderLayer(b.get(), RenderType.getCutout());
    }
}