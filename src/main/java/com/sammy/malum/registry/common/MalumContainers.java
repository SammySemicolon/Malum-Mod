package com.sammy.malum.registry.common;

import com.sammy.malum.client.screen.container.*;
import com.sammy.malum.client.screen.container.furnace.ConjunctureCrystallariumScreen;
import com.sammy.malum.client.screen.container.tinkerer.WandTinkererScreen;
import com.sammy.malum.common.block.curiosities.artifice.furnace.crystallarium.*;
import com.sammy.malum.common.block.curiosities.sorcery.wand_tinkerer.WandTinkererContainer;
import com.sammy.malum.common.container.WeaversWorkbenchContainer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.extensions.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.sammy.malum.MalumMod.MALUM;

public class MalumContainers {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, MALUM);

    public static final DeferredHolder<MenuType<?>, MenuType<WandTinkererContainer>> WAND_TINKERER = CONTAINERS.register("wand_tinkerer", () -> IMenuTypeExtension.create(WandTinkererContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<ConjunctureCrystallariumContainer>> CONJUNCTURE_CRYSTALLARIUM = CONTAINERS.register("conjuncture_crystallarium", () -> IMenuTypeExtension.create(ConjunctureCrystallariumContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<WeaversWorkbenchContainer>> WEAVERS_WORKBENCH = CONTAINERS.register("weavers_workbench", () -> IMenuTypeExtension.create(WeaversWorkbenchContainer::new));


    public static class ClientOnly {
        public static void bindContainerRenderers(RegisterMenuScreensEvent event) {
            event.register(MalumContainers.WAND_TINKERER.get(), WandTinkererScreen::new);
            event.register(MalumContainers.CONJUNCTURE_CRYSTALLARIUM.get(), ConjunctureCrystallariumScreen::new);
            event.register(MalumContainers.WEAVERS_WORKBENCH.get(), WeaversWorkbenchContainerScreen::new);
        }
    }
}