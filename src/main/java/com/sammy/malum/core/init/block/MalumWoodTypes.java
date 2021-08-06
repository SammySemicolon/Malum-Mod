package com.sammy.malum.core.init.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraft.client.renderer.Atlases.SIGN_ATLAS;
import static net.minecraft.client.renderer.Atlases.SIGN_MATERIALS;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class MalumWoodTypes
{
    public static final WoodType RUNEWOOD = new MalumWoodType("runewood");

    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> addWoodType(RUNEWOOD));
    }

    public static void addWoodType(WoodType woodType) {
        SIGN_MATERIALS.put(woodType, new RenderMaterial(SIGN_ATLAS, MalumHelper.prefix("entity/signs/" + woodType.getName())));
    }
    static class MalumWoodType extends WoodType
    {
        public MalumWoodType(String nameIn)
        {
            super(nameIn);
        }
    }
}
