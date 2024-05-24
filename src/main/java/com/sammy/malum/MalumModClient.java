package com.sammy.malum;

import com.sammy.malum.client.renderer.block.SpiritCrucibleRenderer;
import com.sammy.malum.client.renderer.block.TotemBaseRenderer;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.registry.client.ScreenParticleRegistry;
import com.sammy.malum.registry.common.ContainerRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class MalumModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigRegistry.registerConfig(MalumMod.MALUM, ConfigType.CLIENT, ClientConfig.SPEC);

        ClientTickEvents.START_CLIENT_TICK.register(this::startTick);

        ParticleRegistry.registerParticleFactory();
        ScreenParticleRegistry.registerParticleFactory();
        BlockEntityRegistry.ClientOnly.registerRenderer();
        BlockRegistry.ClientOnly.setBlockColors();
        EntityRegistry.ClientOnly.bindEntityRenderers();
        ItemRegistry.ClientOnly.addItemProperties();
        ItemRegistry.ClientOnly.setItemColors();
        ContainerRegistry.bindContainerRenderers();
    }

    private void startTick(Minecraft minecraft) {
        SpiritCrucibleRenderer.checkForTuningFork(minecraft);
        TotemBaseRenderer.checkForTotemicStaff(minecraft);
    }
}
