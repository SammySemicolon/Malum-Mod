package com.sammy.malum.core.init;

import com.sammy.malum.common.container.SpiritPouchContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;

public class MalumContainers {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public static final RegistryObject<ContainerType<SpiritPouchContainer>> SPIRIT_POUCH =
            CONTAINERS.register(
                    "spirit_pouch", () -> IForgeContainerType.create((int id, PlayerInventory inv, PacketBuffer extraData)->new SpiritPouchContainer(id, inv, extraData.readItemStack())));

}
