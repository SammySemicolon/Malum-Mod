package com.sammy.malum;

import com.sammy.malum.client.renderer.armor.MalignantStrongholdArmorRenderer;
import com.sammy.malum.client.renderer.armor.SoulHunterArmorRenderer;
import com.sammy.malum.client.renderer.armor.SoulStainedSteelArmorRenderer;
import com.sammy.malum.client.renderer.block.SpiritCrucibleRenderer;
import com.sammy.malum.client.renderer.block.TotemBaseRenderer;
import com.sammy.malum.client.renderer.item.SpiritJarItemRenderer;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.registry.client.ModelRegistry;
import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.registry.client.ScreenParticleRegistry;
import com.sammy.malum.registry.common.ContainerRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

import static com.sammy.malum.registry.common.item.ItemRegistry.SPIRIT_JAR;

public class MalumModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigRegistry.registerConfig(MalumMod.MALUM, ConfigType.CLIENT, ClientConfig.SPEC);

        ClientTickEvents.START_CLIENT_TICK.register(this::startTick);

        SpiritTypeRegistry.init();
        ParticleRegistry.registerParticleFactory();
        ScreenParticleRegistry.registerParticleFactory();
        BlockEntityRegistry.ClientOnly.registerRenderer();
        BlockRegistry.ClientOnly.setBlockColors();
        EntityRegistry.ClientOnly.bindEntityRenderers();
        ItemRegistry.ClientOnly.addItemProperties();
        ItemRegistry.ClientOnly.setItemColors();
        ContainerRegistry.bindContainerRenderers();
        ModelRegistry.registerLayerDefinitions();
        ModelRegistry.registerLayers();

        BuiltinItemRendererRegistry.INSTANCE.register(SPIRIT_JAR.get(), new SpiritJarItemRenderer());

        ArmorRenderer.register(new SoulHunterArmorRenderer(),
                ItemRegistry.SOUL_HUNTER_CLOAK.get(),
                ItemRegistry.SOUL_HUNTER_ROBE.get(),
                ItemRegistry.SOUL_HUNTER_LEGGINGS.get(),
                ItemRegistry.SOUL_HUNTER_BOOTS.get()
        );

        ArmorRenderer.register(new SoulStainedSteelArmorRenderer(),
                ItemRegistry.SOUL_STAINED_STEEL_HELMET.get(),
                ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get(),
                ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get(),
                ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get()
        );

        ArmorRenderer.register(new MalignantStrongholdArmorRenderer(),
                ItemRegistry.MALIGNANT_STRONGHOLD_HELMET.get(),
                ItemRegistry.MALIGNANT_STRONGHOLD_CHESTPLATE.get(),
                ItemRegistry.MALIGNANT_STRONGHOLD_LEGGINGS.get(),
                ItemRegistry.MALIGNANT_STRONGHOLD_BOOTS.get()
        );

    }

    private void startTick(Minecraft minecraft) {
        SpiritCrucibleRenderer.checkForTuningFork(minecraft);
        TotemBaseRenderer.checkForTotemicStaff(minecraft);
    }
}
