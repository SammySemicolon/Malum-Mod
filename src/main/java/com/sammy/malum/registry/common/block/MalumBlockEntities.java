package com.sammy.malum.registry.common.block;

import com.sammy.malum.*;
import com.sammy.malum.client.renderer.block.*;
import com.sammy.malum.client.renderer.block.artifice.SpiritCatalyzerRenderer;
import com.sammy.malum.client.renderer.block.artifice.SpiritCrucibleRenderer;
import com.sammy.malum.client.renderer.block.banner.FancyBannerRenderer;
import com.sammy.malum.client.renderer.block.banner.SoulwovenBannerRenderer;
import com.sammy.malum.client.renderer.block.enscription.MyriadGatewayRenderer;
import com.sammy.malum.client.renderer.block.redstone.WaveMakerRenderer;
import com.sammy.malum.client.renderer.block.redstone.WaveChargerRenderer;
import com.sammy.malum.client.renderer.block.redstone.WavebankerRenderer;
import com.sammy.malum.client.renderer.block.redstone.WaveBreakerRenderer;
import com.sammy.malum.client.renderer.block.totemancy.*;
import com.sammy.malum.common.block.building.banner.fancy.FancyBannerBlock;
import com.sammy.malum.common.block.building.banner.fancy.FancyBannerBlockEntity;
import com.sammy.malum.common.block.curiosities.artifice.crystallarium.ConjunctureCrystallariumBlockEntity;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.aerial.GustIgniterBlockEntity;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.aerial.WindTunnelBlockEntity;
import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlock;
import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlockEntity;
import com.sammy.malum.common.block.curiosities.escription.MyriadGatewayBlockEntity;
import com.sammy.malum.common.block.curiosities.obelisk.brilliant.*;
import com.sammy.malum.common.block.curiosities.obelisk.rite_pylon.*;
import com.sammy.malum.common.block.curiosities.obelisk.runewood.*;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavemaker.WaveMakerBlock;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavemaker.WaveMakerBlockEntity;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavebanker.WaveBankerBlock;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavebanker.WaveBankerBlockEntity;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavecharger.WaveChargerBlock;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavecharger.WaveChargerBlockEntity;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavebreaker.WaveBreakerBlock;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavebreaker.WaveBreakerBlockEntity;
import com.sammy.malum.common.block.curiosities.artifice.repair_pylon.*;
import com.sammy.malum.common.block.curiosities.poppetry.PoppetPillowBlock;
import com.sammy.malum.common.block.curiosities.poppetry.PoppetPillowBlockEntity;
import com.sammy.malum.common.block.curiosities.sorcery.runic_workbench.*;
import com.sammy.malum.common.block.curiosities.sorcery.soul_brazier.*;
import com.sammy.malum.common.block.curiosities.sorcery.spirit_altar.*;
import com.sammy.malum.common.block.curiosities.artifice.spirit_crucible.*;
import com.sammy.malum.common.block.curiosities.artifice.spirit_catalyzer.*;
import com.sammy.malum.common.block.curiosities.sorcery.wand_tinkerer.WandTinkererBlockEntity;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.block.curiosities.totem.anchor.*;
import com.sammy.malum.common.block.curiosities.totem.channel.*;
import com.sammy.malum.common.block.curiosities.totem.spreader.*;
import com.sammy.malum.common.block.curiosities.totem.unweaver.*;
import com.sammy.malum.common.block.curiosities.totem.waveform.*;
import com.sammy.malum.common.block.curiosities.weavers_workbench.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.common.block.ether.*;
import com.sammy.malum.common.block.soulstone.*;
import com.sammy.malum.common.block.storage.jar.*;
import com.sammy.malum.common.block.storage.pedestal.*;
import com.sammy.malum.common.block.storage.stand.*;
import com.sammy.malum.registry.common.MalumContent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.*;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.modules.toolkit.blockentity.*;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntityTicker.Type;

import java.util.*;
import java.util.function.Supplier;

import static com.sammy.malum.MalumMod.*;
import static team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntityTypeBuilder.create;

public class MalumBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MALUM);

    public static final Supplier<LodestoneBlockEntityType<SoulstoneBudBlockEntity>> SOULSTONE_BUD = BLOCK_ENTITY_TYPES.register("soulstone_bud", () -> create(SoulstoneBudBlockEntity::new, MalumContent.Materials.SOULSTONE_BUD).build());

    public static final Supplier<LodestoneBlockEntityType<ItemStandBlockEntity>> ITEM_STAND = BLOCK_ENTITY_TYPES.register("item_stand", () -> create(ItemStandBlockEntity::new, getBlocks(ItemStandBlock.class)).setTickerType(Type.CLIENT).build());
    public static final Supplier<LodestoneBlockEntityType<ItemPedestalBlockEntity>> ITEM_PEDESTAL = BLOCK_ENTITY_TYPES.register("item_pedestal", () -> create(ItemPedestalBlockEntity::new, getBlocks(ItemPedestalBlock.class)).setTickerType(Type.CLIENT).build());

    public static final Supplier<LodestoneBlockEntityType<EtherBlockEntity>> ETHER = BLOCK_ENTITY_TYPES.register("ether", () -> create(EtherBlockEntity::new, MalumContent.BuildingBlocks.ETHER, MalumContent.BuildingBlocks.IRIDESCENT_ETHER).setTickerType(Type.CLIENT).build());
    public static final Supplier<LodestoneBlockEntityType<EtherCandleBlockEntity>> ETHER_CANDLE = BLOCK_ENTITY_TYPES.register("ether_candle", () -> create(EtherCandleBlockEntity::new, MalumContent.BuildingBlocks.ETHER_CANDLE, MalumContent.BuildingBlocks.IRIDESCENT_ETHER_CANDLE).setTickerType(Type.CLIENT).build());
    public static final Supplier<LodestoneBlockEntityType<EtherTorchBlockEntity>> ETHER_TORCH = BLOCK_ENTITY_TYPES.register("ether_torch", () -> create(EtherTorchBlockEntity::new, getBlocks(EtherTorchBlock.class, EtherWallTorchBlock.class)).setTickerType(Type.CLIENT).build());
    public static final Supplier<LodestoneBlockEntityType<EtherBrazierBlockEntity>> ETHER_BRAZIER = BLOCK_ENTITY_TYPES.register("ether_brazier", () -> create(EtherBrazierBlockEntity::new, getBlocks(EtherBrazierBlock.class)).setTickerType(Type.CLIENT).build());
    public static final Supplier<LodestoneBlockEntityType<EtherCressetBlockEntity>> ETHER_CRESSET = BLOCK_ENTITY_TYPES.register("ether_cresset", () -> create(EtherCressetBlockEntity::new, getBlocks(EtherCressetBlock.class)).setTickerType(Type.CLIENT).build());

    public static final Supplier<LodestoneBlockEntityType<FancyBannerBlockEntity>> FANCY_BANNER = BLOCK_ENTITY_TYPES.register("fancy_banner", () -> create(FancyBannerBlockEntity::new, getBlocks(FancyBannerBlock.class)).build());
    public static final Supplier<LodestoneBlockEntityType<SoulwovenBannerBlockEntity>> SOULWOVEN_BANNER = BLOCK_ENTITY_TYPES.register("soulwoven_banner", () -> create(SoulwovenBannerBlockEntity::new, getBlocks(SoulwovenBannerBlock.class)).build());

    public static final Supplier<LodestoneBlockEntityType<SpiritAltarBlockEntity>> SPIRIT_ALTAR = BLOCK_ENTITY_TYPES.register("spirit_altar", () -> create(SpiritAltarBlockEntity::new, MalumContent.Sorcery.SPIRIT_ALTAR).setTickerType(Type.BOTH).build());
    public static final Supplier<LodestoneBlockEntityType<SpiritJarBlockEntity>> SPIRIT_JAR = BLOCK_ENTITY_TYPES.register("spirit_jar", () -> create(SpiritJarBlockEntity::new, MalumContent.Sorcery.SPIRIT_JAR).setTickerType(Type.CLIENT).build());

    public static final Supplier<LodestoneBlockEntityType<RunewoodObeliskBlockEntity>> RUNEWOOD_OBELISK = BLOCK_ENTITY_TYPES.register("runewood_obelisk", () -> create(RunewoodObeliskBlockEntity::new, MalumContent.Sorcery.RUNEWOOD_OBELISK).build());
    public static final Supplier<LodestoneBlockEntityType<BrilliantObeliskBlockEntity>> BRILLIANT_OBELISK = BLOCK_ENTITY_TYPES.register("brilliant_obelisk", () -> create(BrilliantObeliskBlockEntity::new, MalumContent.Sorcery.BRILLIANT_OBELISK).build());
    public static final Supplier<LodestoneBlockEntityType<ArcanaPylonBlockEntity>> ARCANA_PYLON = BLOCK_ENTITY_TYPES.register("arcana_pylon", () -> create(ArcanaPylonBlockEntity::new, MalumContent.Sorcery.ARCANA_PYLON).setTickerType(Type.BOTH).build());

    public static final Supplier<LodestoneBlockEntityType<WandTinkererBlockEntity>> WAND_TINKERER = BLOCK_ENTITY_TYPES.register("wand_tinkerer", () -> create(WandTinkererBlockEntity::new, MalumContent.Sorcery.WAND_TINKERER).setTickerType(Type.BOTH).build());
    public static final Supplier<LodestoneBlockEntityType<RunicWorkbenchBlockEntity>> RUNIC_WORKBENCH = BLOCK_ENTITY_TYPES.register("runic_workbench", () -> create(RunicWorkbenchBlockEntity::new, MalumContent.Sorcery.RUNIC_WORKBENCH).setTickerType(Type.SERVER).build());
    public static final Supplier<LodestoneBlockEntityType<ConjunctureCrystallariumBlockEntity>> CONJUNCTURE_CRYSTALLARIUM = BLOCK_ENTITY_TYPES.register("conjuncture_crystallarium", () -> create(ConjunctureCrystallariumBlockEntity::new, MalumContent.Artifice.CONJUNCTURE_CRYSTALLARIUM).setTickerType(Type.BOTH).build());

    public static final Supplier<LodestoneBlockEntityType<WeaversWorkbenchBlockEntity>> WEAVERS_WORKBENCH = BLOCK_ENTITY_TYPES.register("weavers_workbench", () -> create(WeaversWorkbenchBlockEntity::new, MalumContent.Sorcery.WEAVERS_WORKBENCH).build());
    public static final Supplier<LodestoneBlockEntityType<SoulBrazierBlockEntity>> SOUL_BRAZIER = BLOCK_ENTITY_TYPES.register("soulbinding_brazier", () -> create(SoulBrazierBlockEntity::new, MalumContent.Sorcery.SOUL_BRAZIER).setTickerType(Type.BOTH).build());

    public static final Supplier<LodestoneBlockEntityType<SpiritCrucibleCoreBlockEntity>> SPIRIT_CRUCIBLE = BLOCK_ENTITY_TYPES.register("spirit_crucible", () -> create(SpiritCrucibleCoreBlockEntity::new, MalumContent.Focusing.SPIRIT_CRUCIBLE).setTickerType(Type.BOTH).build());
    public static final Supplier<LodestoneBlockEntityType<SpiritCatalyzerCoreBlockEntity>> SPIRIT_CATALYZER = BLOCK_ENTITY_TYPES.register("spirit_catalyzer", () -> create(SpiritCatalyzerCoreBlockEntity::new, MalumContent.Focusing.SPIRIT_CATALYZER).setTickerType(Type.BOTH).build());
    public static final Supplier<LodestoneBlockEntityType<RepairPylonCoreBlockEntity>> REPAIR_PYLON = BLOCK_ENTITY_TYPES.register("repair_pylon", () -> create(RepairPylonCoreBlockEntity::new, MalumContent.Focusing.REPAIR_PYLON).setTickerType(Type.BOTH).build());

    public static final Supplier<LodestoneBlockEntityType<PoppetPillowBlockEntity>> POPPET_PILLOW = BLOCK_ENTITY_TYPES.register("poppet_pillow", () -> create(PoppetPillowBlockEntity::new, getBlocks(PoppetPillowBlock.class)).setTickerType(Type.SERVER).build());

    public static final Supplier<LodestoneBlockEntityType<TotemPoleBlockEntity>> TOTEM_POLE = BLOCK_ENTITY_TYPES.register("totem_pole", () -> create(TotemPoleBlockEntity::new, MalumContent.Totemancy.RUNEWOOD_TOTEM_POLE, MalumContent.Totemancy.SOULWOOD_TOTEM_POLE).setTickerType(Type.BOTH).build());
    public static final Supplier<LodestoneBlockEntityType<TotemBaseBlockEntity>> TOTEM_BASE = BLOCK_ENTITY_TYPES.register("totem_base", () -> create(TotemBaseBlockEntity::new, getBlocks(TotemBaseBlock.class)).setTickerType(Type.SERVER).build());
    public static final Supplier<LodestoneBlockEntityType<WaveformTotemBaseBlockEntity>> WAVEFORM_TOTEM_BASE = BLOCK_ENTITY_TYPES.register("waveform_totem_base", () -> create(WaveformTotemBaseBlockEntity::new, getBlocks(WaveformTotemBaseBlock.class)).setTickerType(Type.SERVER).build());

    public static final Supplier<LodestoneBlockEntityType<RiteAnchorBlockEntity>> RITE_ANCHOR = BLOCK_ENTITY_TYPES.register("rite_anchor", () -> create(RiteAnchorBlockEntity::new, MalumContent.Totemancy.RITE_ANCHOR).setTickerType(Type.BOTH).build());
    public static final Supplier<LodestoneBlockEntityType<RiteUnweaverBlockEntity>> RITE_UNWEAVER = BLOCK_ENTITY_TYPES.register("rite_unweaver", () -> create(RiteUnweaverBlockEntity::new, MalumContent.Totemancy.RITE_UNWEAVER).build());
    public static final Supplier<LodestoneBlockEntityType<RiteSpreaderBlockEntity>> RITE_SPREADER = BLOCK_ENTITY_TYPES.register("rite_spreader", () -> create(RiteSpreaderBlockEntity::new, MalumContent.Totemancy.RITE_SPREADER).build());
    public static final Supplier<LodestoneBlockEntityType<RiteChannelBlockEntity>> RITE_CHANNEL = BLOCK_ENTITY_TYPES.register("rite_channel", () -> create(RiteChannelBlockEntity::new, MalumContent.Totemancy.RITE_CHANNEL).build());

    public static final Supplier<LodestoneBlockEntityType<WaveChargerBlockEntity>> WAVECHARGER = BLOCK_ENTITY_TYPES.register("wavecharger", () -> create(WaveChargerBlockEntity::new, getBlocks(WaveChargerBlock.class)).build());
    public static final Supplier<LodestoneBlockEntityType<WaveBankerBlockEntity>> WAVEBANKER = BLOCK_ENTITY_TYPES.register("wavebanker", () -> create(WaveBankerBlockEntity::new, getBlocks(WaveBankerBlock.class)).build());
    public static final Supplier<LodestoneBlockEntityType<WaveMakerBlockEntity>> WAVEMAKER = BLOCK_ENTITY_TYPES.register("wavemaker", () -> create(WaveMakerBlockEntity::new, getBlocks(WaveMakerBlock.class)).build());
    public static final Supplier<LodestoneBlockEntityType<WaveBreakerBlockEntity>> WAVEBREAKER = BLOCK_ENTITY_TYPES.register("wavebreaker", () -> create(WaveBreakerBlockEntity::new, getBlocks(WaveBreakerBlock.class)).build());

    public static final Supplier<LodestoneBlockEntityType<GustIgniterBlockEntity>> GUST_IGNITER = BLOCK_ENTITY_TYPES.register("gust_igniter", () -> create(GustIgniterBlockEntity::new, MalumContent.Artifice.GUST_IGNITER).setTickerType(Type.BOTH).build());
    public static final Supplier<LodestoneBlockEntityType<WindTunnelBlockEntity>> WIND_TUNNEL = BLOCK_ENTITY_TYPES.register("wind_tunnel", () -> create(WindTunnelBlockEntity::new, MalumContent.Artifice.WIND_TUNNEL).build());


    public static final Supplier<LodestoneBlockEntityType<MyriadGatewayBlockEntity>> MYRIAD_GATEWAY = BLOCK_ENTITY_TYPES.register("myriad_gateway", () -> create(MyriadGatewayBlockEntity::new, MalumContent.Enscription.MYRIAD_GATEWAY).setTickerType(Type.BOTH).build());

    public static final Supplier<LodestoneBlockEntityType<VoidConduitBlockEntity>> VOID_CONDUIT = BLOCK_ENTITY_TYPES.register("void_conduit", () -> create(VoidConduitBlockEntity::new, MalumContent.Enscription.WEEPING_WELL_CENTERPIECE).setTickerType(Type.BOTH).build());


    public static void bindBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        MalumContent.BuildingBlocks.RUNEWOOD_SET.bindSigns(event);
        MalumContent.BuildingBlocks.SOULWOOD_SET.bindSigns(event);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SPIRIT_ALTAR.get(), IInventoryCapabilityProvider::getInventory);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SPIRIT_JAR.get(), IInventoryCapabilityProvider::getInventory);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ARCANA_PYLON.get(), IInventoryCapabilityProvider::getInventory);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SPIRIT_CRUCIBLE.get(), IInventoryCapabilityProvider::getInventory);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SPIRIT_CATALYZER.get(), IInventoryCapabilityProvider::getInventory);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, REPAIR_PYLON.get(), IInventoryCapabilityProvider::getInventory);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ITEM_STAND.get(), IInventoryCapabilityProvider::getInventory);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ITEM_PEDESTAL.get(), IInventoryCapabilityProvider::getInventory);
    }

    public static Block[] getBlocks(Class<?>... blockClasses) {
        Collection<DeferredHolder<Block, ? extends Block>> blocks = MalumContent.BLOCKS.getEntries();
        List<Block> matchingBlocks = new ArrayList<>();
        for (DeferredHolder<Block, ? extends Block> registryObject : blocks) {
            if (registryObject.isBound() && Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(registryObject.get()))) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    public static Block[] getBlocksExact(Class<?> clazz) {
        Collection<DeferredHolder<Block, ? extends Block>> blocks = MalumContent.BLOCKS.getEntries();
        List<Block> matchingBlocks = new ArrayList<>();
        for (DeferredHolder<Block, ? extends Block> registryObject : blocks) {
            if (clazz.equals(registryObject.get().getClass())) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    @EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT)
    public static class ClientOnly {
        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(SPIRIT_ALTAR.get(), SpiritAltarRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_JAR.get(), SpiritJarRenderer::new);
            event.registerBlockEntityRenderer(RUNIC_WORKBENCH.get(), MalumItemHolderRenderer::new);
            event.registerBlockEntityRenderer(SOUL_BRAZIER.get(), SoulBrazierRenderer::new);
            event.registerBlockEntityRenderer(ARCANA_PYLON.get(), ArcanaPylonRenderer::new);

            event.registerBlockEntityRenderer(SPIRIT_CRUCIBLE.get(), SpiritCrucibleRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_CATALYZER.get(), SpiritCatalyzerRenderer::new);
            event.registerBlockEntityRenderer(REPAIR_PYLON.get(), RepairPylonRenderer::new);

            event.registerBlockEntityRenderer(ITEM_STAND.get(), MalumItemHolderRenderer::new);
            event.registerBlockEntityRenderer(ITEM_PEDESTAL.get(), MalumItemHolderRenderer::new);

            event.registerBlockEntityRenderer(TOTEM_BASE.get(), TotemBaseRenderer::new);
            event.registerBlockEntityRenderer(TOTEM_POLE.get(), TotemPoleRenderer::new);

            event.registerBlockEntityRenderer(RITE_ANCHOR.get(), RiteAnchorRenderer::new);

            event.registerBlockEntityRenderer(WAVECHARGER.get(), WaveChargerRenderer::new);
            event.registerBlockEntityRenderer(WAVEBANKER.get(), WavebankerRenderer::new);
            event.registerBlockEntityRenderer(WAVEMAKER.get(), WaveMakerRenderer::new);
            event.registerBlockEntityRenderer(WAVEBREAKER.get(), WaveBreakerRenderer::new);

            event.registerBlockEntityRenderer(WIND_TUNNEL.get(), WindTunnelRenderer::new);

            event.registerBlockEntityRenderer(FANCY_BANNER.get(), FancyBannerRenderer::new);
            event.registerBlockEntityRenderer(SOULWOVEN_BANNER.get(), SoulwovenBannerRenderer::new);

            event.registerBlockEntityRenderer(MYRIAD_GATEWAY.get(), MyriadGatewayRenderer::new);

        }
    }
}
