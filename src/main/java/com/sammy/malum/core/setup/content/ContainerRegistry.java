package com.sammy.malum.core.setup.content;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.container.SpiritPouchContainerScreen;
import com.sammy.malum.common.container.SpiritPouchContainer;
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

    public static final RegistryObject<MenuType<SpiritPouchContainer>> SPIRIT_POUCH = CONTAINERS.register("spirit_pouch", () -> IForgeMenuType.create((int id, Inventory inv, FriendlyByteBuf extraData) -> {return new SpiritPouchContainer(id, inv, extraData.readItem());}));


    @SubscribeEvent
    public static void bindContainerRenderers(FMLClientSetupEvent event) {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            MenuScreens.register(ContainerRegistry.SPIRIT_POUCH.get(), SpiritPouchContainerScreen::new);
        });
    }
}