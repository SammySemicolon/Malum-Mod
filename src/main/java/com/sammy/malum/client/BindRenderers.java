package com.sammy.malum.client;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.entity_renderer.FloatingItemEntityRenderer;
import com.sammy.malum.client.entity_renderer.ScytheBoomerangEntityRenderer;
import com.sammy.malum.client.tile_renderer.*;
import com.sammy.malum.core.init.MalumEntities;
import com.sammy.malum.core.init.block.MalumTileEntities;
import com.sammy.malum.core.init.event.ClientStartupEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class BindRenderers
{
    @SubscribeEvent
    public static void bindTileEntityRenderers(FMLClientSetupEvent event)
    {
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_ALTAR_TILE_ENTITY.get(), SpiritAltarRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ARCANE_ASSEMBLER_TILE_ENTITY.get(), ArcaneAssemblerRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.TOTEM_POLE_TILE_ENTITY.get(), TotemPoleRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ITEM_STAND_TILE_ENTITY.get(), ItemStandRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.ITEM_PEDESTAL_TILE_ENTITY.get(), ItemPedestalRenderer::new);
        ClientRegistry.bindTileEntityRenderer(MalumTileEntities.SPIRIT_JAR_TILE_ENTITY.get(), SpiritJarRenderer::new);
    }
    @SubscribeEvent
    public static void bindEntityRenderers(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(MalumEntities.PLAYER_HOMING_ITEM.get(), (manager)-> new FloatingItemEntityRenderer(manager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(MalumEntities.SCYTHE_BOOMERANG.get(), (manager)-> new ScytheBoomerangEntityRenderer(manager, Minecraft.getInstance().getItemRenderer()));
    }
}