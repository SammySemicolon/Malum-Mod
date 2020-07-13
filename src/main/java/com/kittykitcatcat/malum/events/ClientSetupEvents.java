package com.kittykitcatcat.malum.events;

import com.kittykitcatcat.malum.blocks.machines.spiritfurnace.SpiritFurnaceBottomRenderer;
import com.kittykitcatcat.malum.blocks.utility.soulstorage.SoulStoringBlockRenderer;
import com.kittykitcatcat.malum.init.ModBlocks;
import com.kittykitcatcat.malum.init.ModTileEntities;
import com.kittykitcatcat.malum.items.shield.ItemTileEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.kittykitcatcat.malum.MalumMod.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents
{
    @SubscribeEvent
    public static void stitchTextures(TextureStitchEvent.Pre evt)
    {
        if (evt.getMap().getTextureLocation() == PlayerContainer.LOCATION_BLOCKS_TEXTURE)
        {
            evt.addSprite(new ResourceLocation(MODID, "gui/empty_trinket_slot"));
            evt.addSprite(ItemTileEntityRenderer.skull_shield_texture);
        }
    }
    @SubscribeEvent
    public static void bindTERs(FMLClientSetupEvent event)
    {
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.spirit_furnace_bottom_tile_entity, SpiritFurnaceBottomRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.spirit_jar_tile_entity, SoulStoringBlockRenderer::new);
    }

    @SubscribeEvent
    public static void setRenderLayer(FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.spirit_furnace, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.spirit_jar, RenderType.getTranslucent());
    }
}