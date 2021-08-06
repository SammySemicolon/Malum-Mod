package com.sammy.malum.client;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.spirit_altar.SpiritAltarBlock;
import com.sammy.malum.core.init.block.MalumBlocks;
import com.sammy.malum.core.mod_systems.multiblock.BoundingBlock;
import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.core.init.block.MalumBlocks.BLOCKS;
import static com.sammy.malum.core.mod_content.MalumSpiritTypes.SPIRITS;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class StitchTextures
{
    @SubscribeEvent
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
}