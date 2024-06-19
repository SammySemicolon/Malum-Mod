package com.sammy.malum;

import com.sammy.malum.client.MalumModelLoaderPlugin;
import com.sammy.malum.client.renderer.armor.MalignantStrongholdArmorRenderer;
import com.sammy.malum.client.renderer.armor.SoulHunterArmorRenderer;
import com.sammy.malum.client.renderer.armor.SoulStainedSteelArmorRenderer;
import com.sammy.malum.client.renderer.block.SpiritCrucibleRenderer;
import com.sammy.malum.client.renderer.block.TotemBaseRenderer;
import com.sammy.malum.client.renderer.curio.TokenOfGratitudeRenderer;
import com.sammy.malum.client.renderer.curio.TopHatCurioRenderer;
import com.sammy.malum.client.renderer.item.SpiritJarItemRenderer;
import com.sammy.malum.common.block.nature.MalumHangingLeavesBlock;
import com.sammy.malum.common.block.nature.MalumLeavesBlock;
import com.sammy.malum.common.block.nature.soulwood.SoulwoodGrowthBlock;
import com.sammy.malum.common.worldgen.tree.grower.SoulwoodTreeGrower;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.registry.client.HiddenTagRegistry;
import com.sammy.malum.registry.client.ModelRegistry;
import com.sammy.malum.registry.client.ParticleEmitterRegistry;
import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.registry.common.ContainerRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.block.MalumBlockProperties;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigType;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.awt.*;

import static com.sammy.malum.registry.common.block.BlockRegistry.*;
import static com.sammy.malum.registry.common.item.ItemRegistry.SPIRIT_JAR;

public class MalumModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ConfigRegistry.registerConfig(MalumMod.MALUM, ConfigType.CLIENT, ClientConfig.SPEC);

        ClientTickEvents.START_CLIENT_TICK.register(this::startTick);

        //SpiritTypeRegistry.init();
        ParticleRegistry.registerParticleFactory();
        BlockEntityRegistry.ClientOnly.registerRenderer();
        BlockRegistry.ClientOnly.setBlockColors();
        EntityRegistry.ClientOnly.bindEntityRenderers();
        ItemRegistry.ClientOnly.addItemProperties();
        ItemRegistry.ClientOnly.setItemColors();
        ContainerRegistry.bindContainerRenderers();

        EntityEvents.ON_JOIN_WORLD.register(ParticleEmitterRegistry::addParticleEmitters);

        TrinketRendererRegistry.registerRenderer(ItemRegistry.TOPHAT.get(), new TopHatCurioRenderer());
        TrinketRendererRegistry.registerRenderer(ItemRegistry.TOKEN_OF_GRATITUDE.get(), new TokenOfGratitudeRenderer());

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            ModelRegistry.registerLayerDefinitions();
            ModelRegistry.registerLayers();
        });

        BuiltinItemRendererRegistry.INSTANCE.register(SPIRIT_JAR.get(), new SpiritJarItemRenderer());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(),
                BlockRegistry.SPIRIT_JAR.get(),
                BlockRegistry.ETHER_TORCH.get(),
                BlockRegistry.IRIDESCENT_ETHER_TORCH.get(),
                BlockRegistry.IRIDESCENT_WALL_ETHER_TORCH.get(),
                BlockRegistry.WALL_ETHER_TORCH.get(),
                BlockRegistry.TAINTED_ETHER_BRAZIER.get(),
                BlockRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER.get(),
                BlockRegistry.TWISTED_ETHER_BRAZIER.get(),
                BlockRegistry.TWISTED_IRIDESCENT_ETHER_BRAZIER.get()
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutoutMipped(),
                BlockRegistry.HANGING_SOULWOOD_LEAVES.get(),
                BlockRegistry.HANGING_RUNEWOOD_LEAVES.get(),
                BlockRegistry.HANGING_AZURE_RUNEWOOD_LEAVES.get()
                );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                BlockRegistry.CTHONIC_GOLD_CLUSTER.get(),
                BlockRegistry.BLAZING_QUARTZ_CLUSTER.get(),
                BlockRegistry.NATURAL_QUARTZ_CLUSTER.get(),
                BlockRegistry.BLAZING_QUARTZ_ORE.get(),
                BlockRegistry.BRILLIANT_STONE.get(),
                BlockRegistry.BRILLIANT_DEEPSLATE.get(),
                BlockRegistry.RUNEWOOD_SAPLING.get(),
                BlockRegistry.SOULWOOD_GROWTH.get(),
                BlockRegistry.AZURE_RUNEWOOD_SAPLING.get(),

                BLIGHTED_GROWTH.get(),
                CLINGING_BLIGHT.get(),
                CALCIFIED_BLIGHT.get(),
                TALL_CALCIFIED_BLIGHT.get(),
                SOULWOOD_DOOR.get(),
                SOULWOOD_TRAPDOOR.get(),
                RUNEWOOD_DOOR.get(),
                RUNEWOOD_TRAPDOOR.get(),
                SOULWOOD_LEAVES.get(),
                BUDDING_SOULWOOD_LEAVES.get()
        );

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

        ModelLoadingPlugin.register(new MalumModelLoaderPlugin());


        FabricLoader.getInstance().getModContainer(MalumMod.MALUM).ifPresent(container ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                        MalumMod.malumPath("chibi_sprites"),
                        container,
                        Component.translatable("chibi_sprites"),
                        ResourcePackActivationType.NORMAL
                )
        );

        HiddenTagRegistry.registerHiddenTags();
    }

    private void startTick(Minecraft minecraft) {
        SpiritCrucibleRenderer.checkForTuningFork(minecraft);
        TotemBaseRenderer.checkForTotemicStaff(minecraft);
    }
}
