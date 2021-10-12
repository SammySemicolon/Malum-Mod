package com.sammy.malum.core.init.event;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.client.entity_renderer.FloatingItemEntityRenderer;
import com.sammy.malum.client.entity_renderer.MalumBoatRenderer;
import com.sammy.malum.client.entity_renderer.ScytheBoomerangEntityRenderer;
import com.sammy.malum.client.screen.container.SpiritPouchContainerScreen;
import com.sammy.malum.client.tile_renderer.*;
import com.sammy.malum.common.block.totem.TotemBaseBlock;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.ether.EtherTorchBlock;
import com.sammy.malum.common.block.item_storage.ItemPedestalBlock;
import com.sammy.malum.common.block.item_storage.ItemStandBlock;
import com.sammy.malum.common.block.item_storage.SpiritJarBlock;
import com.sammy.malum.common.block.spirit_altar.SpiritAltarBlock;
import com.sammy.malum.core.init.MalumContainers;
import com.sammy.malum.core.init.MalumEntities;
import com.sammy.malum.core.init.block.MalumBlocks;
import com.sammy.malum.core.init.block.MalumTileEntities;
import com.sammy.malum.core.mod_systems.multiblock.BoundingBlock;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.core.init.MalumSpiritTypes.SPIRITS;
import static com.sammy.malum.core.init.block.MalumBlocks.BLOCKS;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ClientStartupEvents {
    @SubscribeEvent
    public static void bindTileEntityRenderers(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_ALTAR_TILE_ENTITY.get(), SpiritAltarRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.TOTEM_POLE_TILE_ENTITY.get(), TotemPoleRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ITEM_STAND_TILE_ENTITY.get(), ItemStandRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ITEM_PEDESTAL_TILE_ENTITY.get(), ItemPedestalRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_JAR_TILE_ENTITY.get(), SpiritJarRenderer::new);

        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SIGN_TILE_ENTITY.get(), SignTileEntityRenderer::new);
    }

    @SubscribeEvent
    public static void bindEntityRenderers(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(MalumEntities.PLAYER_HOMING_ITEM.get(), (manager) -> new FloatingItemEntityRenderer(manager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(MalumEntities.SCYTHE_BOOMERANG.get(), (manager) -> new ScytheBoomerangEntityRenderer(manager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(MalumEntities.RUNEWOOD_BOAT.get(), (manager) -> new MalumBoatRenderer(manager, "runewood"));
        RenderingRegistry.registerEntityRenderingHandler(MalumEntities.SOULWOOD_BOAT.get(), (manager) -> new MalumBoatRenderer(manager, "soulwood"));
    }

    @SubscribeEvent
    public static void bindContainerRenderers(FMLClientSetupEvent event) {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            ScreenManager.registerFactory(MalumContainers.SPIRIT_POUCH.get(), SpiritPouchContainerScreen::new);
        });
    }

    @SubscribeEvent
    public static void stitchTextures(TextureStitchEvent.Pre event) {
        if (!event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            return;
        }
        SPIRITS.forEach(s ->
        {
            event.addSprite(MalumHelper.prefix("spirit/" + "overlay_" + s.identifier));
            event.addSprite(MalumHelper.prefix("spirit/" + "cutout_" + s.identifier));
            event.addSprite(MalumHelper.prefix("spirit/" + "corrupted_cutout_" + s.identifier));
        });
    }
    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event)
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        MalumHelper.takeAll(blocks, b -> b.get() instanceof EtherTorchBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof EtherBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BoundingBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LeavesBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof ItemStandBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof ItemPedestalBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TotemBaseBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TotemPoleBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SpiritJarBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SpiritAltarBlock).forEach(ClientHelper::setCutout);
        ClientHelper.setCutout(MalumBlocks.BLAZING_QUARTZ_ORE);
    }

}