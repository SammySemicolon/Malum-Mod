package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.container.SpiritPouchContainerScreen;
import com.sammy.malum.client.screen.container.WeaversWorkbenchContainerScreen;
import com.sammy.malum.common.container.SpiritPouchContainer;
import com.sammy.malum.common.container.WeaversWorkbenchContainer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.sammy.malum.MalumMod.MALUM;

@Mod.EventBusSubscriber(modid= MalumMod.MALUM, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ContainerRegistry {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MALUM);

    public static final RegistryObject<MenuType<SpiritPouchContainer>> SPIRIT_POUCH = CONTAINERS.register("spirit_pouch", () -> IForgeMenuType.create((int id, Inventory inv, FriendlyByteBuf extraData) -> new SpiritPouchContainer(id, inv, extraData.readItem())));
    public static final RegistryObject<MenuType<WeaversWorkbenchContainer>> WEAVERS_WORKBENCH = CONTAINERS.register("weavers_workbench", () -> IForgeMenuType.create(WeaversWorkbenchContainer::new));


    @SubscribeEvent
    public static void bindContainerRenderers(FMLClientSetupEvent event) {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            MenuScreens.register(ContainerRegistry.SPIRIT_POUCH.get(), SpiritPouchContainerScreen::new);
            MenuScreens.register(ContainerRegistry.WEAVERS_WORKBENCH.get(), WeaversWorkbenchContainerScreen::new);
        });
    }
}