package com.sammy.malum.core.init;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.common.blocks.MalumLeavesBlock;
import com.sammy.malum.common.blocks.abstruceblock.AbstruseBlock;
import com.sammy.malum.common.blocks.itemstand.ItemStandItemRendererModule;
import com.sammy.malum.common.blocks.spiritkiln.SpiritKilnItemRendererModule;
import com.sammy.malum.common.blocks.spiritkiln.SpiritKilnSideItemRendererModule;
import com.sammy.malum.common.blocks.totems.TotemCoreBlock;
import com.sammy.malum.common.blocks.totems.TotemPoleBlock;
import com.sammy.malum.common.blocks.totems.TotemPoleRendererModule;
import com.sammy.malum.common.entities.PlayerSoulRenderer;
import com.sammy.malum.common.entities.ScytheBoomerangEntityRenderer;
import com.sammy.malum.common.entities.SpiritSplinterItemRenderer;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import com.sammy.malum.core.systems.multiblock.BoundingBlock;
import com.sammy.malum.core.systems.multiblock.IMultiblock;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
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
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_KILN_TILE_ENTITY.get(), t -> new AdjustableTileEntityRenderer(t, new SpiritKilnItemRendererModule(),new SpiritKilnSideItemRendererModule()));
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.TOTEM_POLE_TILE_ENTITY.get(), t -> new AdjustableTileEntityRenderer(t, new TotemPoleRendererModule()));
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ITEM_STAND_TILE_ENTITY.get(), t -> new AdjustableTileEntityRenderer(t, new ItemStandItemRendererModule()));
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
    
        ClientHelper.registerItemColorTint(itemColors, MalumItems.SWORD_OF_MOVING_CLOUDS, MalumSpiritTypes.AIR_SPIRIT_COLOR);
        ClientHelper.registerItemColorTint(itemColors, MalumItems.PICKAXE_OF_THE_CORE, MalumSpiritTypes.FIRE_SPIRIT_COLOR);
    
        ClientHelper.registerItemColor(itemColors, MalumItems.ORANGE_ETHER, MalumConstants.ORANGE.brighter().brighter());
        ClientHelper.registerItemColor(itemColors, MalumItems.MAGENTA_ETHER, MalumConstants.MAGENTA.brighter().brighter());
        ClientHelper.registerItemColor(itemColors, MalumItems.LIGHT_BLUE_ETHER, MalumConstants.LIGHT_BLUE.brighter().brighter());
        ClientHelper.registerItemColor(itemColors, MalumItems.YELLOW_ETHER, MalumConstants.YELLOW.brighter().brighter());
        ClientHelper.registerItemColor(itemColors, MalumItems.LIME_ETHER, MalumConstants.LIME.brighter().brighter());
        ClientHelper.registerItemColor(itemColors, MalumItems.CYAN_ETHER, MalumConstants.CYAN.brighter().brighter());
        ClientHelper.registerItemColor(itemColors, MalumItems.PURPLE_ETHER, MalumConstants.PURPLE.brighter().brighter());
        ClientHelper.registerItemColor(itemColors, MalumItems.BLUE_ETHER, MalumConstants.BLUE.brighter().brighter());
        ClientHelper.registerItemColor(itemColors, MalumItems.BROWN_ETHER, MalumConstants.BROWN.brighter().brighter());
        ClientHelper.registerItemColor(itemColors, MalumItems.GREEN_ETHER, MalumConstants.GREEN.brighter().brighter());
        ClientHelper.registerItemColor(itemColors, MalumItems.RED_ETHER, MalumConstants.RED.brighter().brighter());
        
        ClientHelper.registerItemColorTint(itemColors, MalumItems.ORANGE_ETHER_TORCH, MalumConstants.ORANGE.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.MAGENTA_ETHER_TORCH, MalumConstants.MAGENTA.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.LIGHT_BLUE_ETHER_TORCH, MalumConstants.LIGHT_BLUE.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.YELLOW_ETHER_TORCH, MalumConstants.YELLOW.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.LIME_ETHER_TORCH, MalumConstants.LIME.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.CYAN_ETHER_TORCH, MalumConstants.CYAN.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.PURPLE_ETHER_TORCH, MalumConstants.PURPLE.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.BLUE_ETHER_TORCH, MalumConstants.BLUE.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.BROWN_ETHER_TORCH, MalumConstants.BROWN.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.GREEN_ETHER_TORCH, MalumConstants.GREEN.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.RED_ETHER_TORCH, MalumConstants.RED.brighter().brighter());
    
        ClientHelper.registerItemColorTint(itemColors, MalumItems.ORANGE_ETHER_BRAZIER, MalumConstants.ORANGE.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.MAGENTA_ETHER_BRAZIER, MalumConstants.MAGENTA.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.LIGHT_BLUE_ETHER_BRAZIER, MalumConstants.LIGHT_BLUE.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.YELLOW_ETHER_BRAZIER, MalumConstants.YELLOW.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.LIME_ETHER_BRAZIER, MalumConstants.LIME.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.CYAN_ETHER_BRAZIER, MalumConstants.CYAN.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.PURPLE_ETHER_BRAZIER, MalumConstants.PURPLE.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.BLUE_ETHER_BRAZIER, MalumConstants.BLUE.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.BROWN_ETHER_BRAZIER, MalumConstants.BROWN.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.GREEN_ETHER_BRAZIER, MalumConstants.GREEN.brighter().brighter());
        ClientHelper.registerItemColorTint(itemColors, MalumItems.RED_ETHER_BRAZIER, MalumConstants.RED.brighter().brighter());
        
        ClientHelper.registerItemColor(itemColors, MalumItems.LIFE_SPIRIT_SPLINTER, MalumSpiritTypes.LIFE_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.DEATH_SPIRIT_SPLINTER, MalumSpiritTypes.DEATH_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.AIR_SPIRIT_SPLINTER, MalumSpiritTypes.AIR_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.WATER_SPIRIT_SPLINTER, MalumSpiritTypes.WATER_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.MAGIC_SPIRIT_SPLINTER, MalumSpiritTypes.MAGIC_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.FIRE_SPIRIT_SPLINTER, MalumSpiritTypes.FIRE_SPIRIT_COLOR);
        ClientHelper.registerItemColor(itemColors, MalumItems.EARTH_SPIRIT_SPLINTER, MalumSpiritTypes.EARTH_SPIRIT_COLOR);
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
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TotemCoreBlock).forEach(ClientStartupEvents::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TotemPoleBlock).forEach(ClientStartupEvents::setCutout);
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