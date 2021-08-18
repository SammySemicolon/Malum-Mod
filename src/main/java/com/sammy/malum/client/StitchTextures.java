package com.sammy.malum.client;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.sammy.malum.core.init.MalumSpiritTypes.SPIRITS;

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