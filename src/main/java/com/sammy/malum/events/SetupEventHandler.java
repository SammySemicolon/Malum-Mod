package com.sammy.malum.events;

import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlockItem;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.MalumDataMaps;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.entity.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.neoforge.capabilities.*;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import top.theillusivec4.curios.api.extensions.*;

@EventBusSubscriber()
public class SetupEventHandler {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event){
        MalumContent.addPottedBlocks(event);
    }

    @SubscribeEvent
    public static void registerCurioExtensions(RegisterCuriosExtensionsEvent event) {
        GeasEffectHandler.registerSlotExtensions(event);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        MalumBlockEntities.registerCapabilities(event);
    }

    @SubscribeEvent
    public static void bindBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        MalumBlockEntities.bindBlockEntities(event);
    }

    @SubscribeEvent
    public static void buildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        SoulwovenBannerBlockItem.addBannerVariantsToCreativeTab(event);
    }

    @SubscribeEvent
    public static void registerDataMaps(RegisterDataMapTypesEvent event) {
        MalumDataMaps.registerDataMapTypes(event);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        MalumCultistEntityTypes.registerEntityAttributes(event);
    }
}