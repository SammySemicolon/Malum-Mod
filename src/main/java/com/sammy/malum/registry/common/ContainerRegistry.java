package com.sammy.malum.registry.common;

import com.sammy.malum.client.screen.container.SpiritPouchContainerScreen;
import com.sammy.malum.client.screen.container.WeaversWorkbenchContainerScreen;
import com.sammy.malum.common.container.SpiritPouchContainer;
import com.sammy.malum.common.container.WeaversWorkbenchContainer;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

import static com.sammy.malum.MalumMod.MALUM;

public class ContainerRegistry {

    public static final LazyRegistrar<MenuType<?>> CONTAINERS = LazyRegistrar.create(BuiltInRegistries.MENU, MALUM);

    public static final RegistryObject<MenuType<SpiritPouchContainer>> SPIRIT_POUCH =
            CONTAINERS.register("spirit_pouch", () -> new ExtendedScreenHandlerType<>(SpiritPouchContainer::new));

    public static final RegistryObject<MenuType<WeaversWorkbenchContainer>> WEAVERS_WORKBENCH =
            CONTAINERS.register("weavers_workbench", () -> new ExtendedScreenHandlerType<>(WeaversWorkbenchContainer::new));


    public static void bindContainerRenderers() {
        MenuScreens.register(ContainerRegistry.SPIRIT_POUCH.get(), SpiritPouchContainerScreen::new);
        MenuScreens.register(ContainerRegistry.WEAVERS_WORKBENCH.get(), WeaversWorkbenchContainerScreen::new);
    }
}