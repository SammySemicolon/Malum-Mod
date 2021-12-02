package com.sammy.malum.core.registry.misc;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.container.SpiritPouchContainerScreen;
import com.sammy.malum.common.container.SpiritPouchContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ContainerRegistry {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public static final RegistryObject<ContainerType<SpiritPouchContainer>> SPIRIT_POUCH = CONTAINERS.register("spirit_pouch", () -> IForgeContainerType.create((int id, PlayerInventory inv, PacketBuffer extraData) -> new SpiritPouchContainer(id, inv, extraData.readItemStack())));


    @SubscribeEvent
    public static void bindContainerRenderers(FMLClientSetupEvent event) {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            ScreenManager.registerFactory(ContainerRegistry.SPIRIT_POUCH.get(), SpiritPouchContainerScreen::new);
        });
    }
}