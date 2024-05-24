package com.sammy.malum;

import com.sammy.malum.client.renderer.block.SpiritCrucibleRenderer;
import com.sammy.malum.client.renderer.block.TotemBaseRenderer;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.registry.client.ScreenParticleRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

public class MalumModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigRegistry.registerConfig(MalumMod.MALUM, ConfigType.CLIENT, ClientConfig.SPEC);

        ClientTickEvents.START_CLIENT_TICK.register(this::startTick);

        ParticleRegistry.registerParticleFactory();
        ScreenParticleRegistry.registerParticleFactory();
    }

    private void startTick(Minecraft minecraft) {
        SpiritCrucibleRenderer.checkForTuningFork(minecraft);
        TotemBaseRenderer.checkForTotemicStaff(minecraft);
    }
}
