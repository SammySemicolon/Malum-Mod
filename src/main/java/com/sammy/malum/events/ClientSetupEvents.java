package com.sammy.malum.events;

import com.sammy.malum.blocks.machines.funkengine.FunkEngineRenderer;
import com.sammy.malum.blocks.machines.mirror.BasicMirrorRenderer;
import com.sammy.malum.blocks.machines.redstoneclock.RedstoneClockRenderer;
import com.sammy.malum.blocks.machines.spiritfurnace.SpiritFurnaceBottomRenderer;
import com.sammy.malum.blocks.machines.spiritfurnace.SpiritFurnaceTopRenderer;
import com.sammy.malum.blocks.machines.spiritjar.SpiritJarRenderer;
import com.sammy.malum.init.ModBlocks;
import com.sammy.malum.init.ModItems;
import com.sammy.malum.init.ModTileEntities;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.sammy.malum.MalumMod.MODID;
import static net.minecraft.item.ItemModelsProperties.registerProperty;

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
        }
    }
    @SubscribeEvent
    public static void bindTERs(FMLClientSetupEvent event)
    {
        
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.spirit_furnace_bottom_tile_entity, SpiritFurnaceBottomRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.spirit_furnace_top_tile_entity, SpiritFurnaceTopRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.basic_mirror_tile_entity, BasicMirrorRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.input_mirror_tile_entity, BasicMirrorRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.output_mirror_tile_entity, BasicMirrorRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.spirit_jar_tile_entity, SpiritJarRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.funk_engine_tile_entity, FunkEngineRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.redstone_clock_tile_entity, RedstoneClockRenderer::new);
    }
    
    @SubscribeEvent
    public static void setModelProperties(FMLClientSetupEvent event)
    {
    
        registerProperty(ModItems.shulker_storage, new ResourceLocation("storing"), (itemStack, world, livingEntity) -> {
            if (livingEntity == null)
            {
                return 0.0F;
            }
            else
            {
               if (itemStack.getTag() != null && itemStack.getTag().contains("storing"))
               {
                   return 1.0F;
               }
            }
            return 0.0F;
        });
        registerProperty(ModItems.bow_of_lost_souls, new ResourceLocation("pull"), (itemStack, world, livingEntity) -> {
            if (livingEntity == null)
            {
                return 0.0F;
            }
            else
            {
                return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
            }
        });
        registerProperty(ModItems.bow_of_lost_souls, new ResourceLocation("pulling"), (itemStack, world, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F);
    }
    @SubscribeEvent
    public static void setRenderLayer(FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModBlocks.spirit_furnace, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.spirit_jar, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.funk_engine, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.redstone_clock, RenderType.getCutout());
    }
}