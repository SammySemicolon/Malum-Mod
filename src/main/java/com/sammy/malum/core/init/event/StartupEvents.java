package com.sammy.malum.core.init.event;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.spirit_altar.SpiritAltarBlock;
import com.sammy.malum.core.init.block.MalumBlocks;
import com.sammy.malum.core.init.worldgen.MalumFeatures;
import com.sammy.malum.core.mod_content.MalumArcaneAssemblyRecipes;
import com.sammy.malum.core.mod_content.MalumRites;
import com.sammy.malum.core.mod_content.MalumSpiritAltarRecipes;
import com.sammy.malum.core.mod_systems.multiblock.BoundingBlock;
import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;

import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.core.init.block.MalumBlocks.BLOCKS;
import static com.sammy.malum.core.mod_content.MalumSpiritTypes.SPIRITS;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class StartupEvents
{
    @SubscribeEvent
    public static void registerModContents(FMLCommonSetupEvent event)
    {
        MalumSpiritAltarRecipes.init();
        MalumArcaneAssemblyRecipes.init();
        MalumRites.init();
    }
    @SubscribeEvent
    public static void registerFeatures(FMLCommonSetupEvent event)
    {
        event.enqueueWork(MalumFeatures::new);
    }

    @SubscribeEvent
    public static void registerCurios(InterModEnqueueEvent event)
    {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("necklace").size(1).cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("ring").size(2).cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("belt").size(1).cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("charm").size(1).cosmetic().build());
    }
}