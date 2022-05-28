package com.sammy.malum.core.setup.content.block;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.renderer.block.*;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.fusion_plate.FusionPlateCoreBlock;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.block.mirror.EmitterMirrorBlock;
import com.sammy.malum.common.block.obelisk.BrillianceObeliskCoreBlock;
import com.sammy.malum.common.block.obelisk.RunewoodObeliskCoreBlock;
import com.sammy.malum.common.block.spirit_altar.SpiritAltarBlock;
import com.sammy.malum.common.block.spirit_crucible.SpiritCatalyzerCoreBlock;
import com.sammy.malum.common.block.spirit_crucible.SpiritCrucibleCoreBlock;
import com.sammy.malum.common.block.totem.TotemBaseBlock;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.common.block.tablet.TwistedTabletBlock;
import com.sammy.malum.common.blockentity.EtherBlockEntity;
import com.sammy.malum.common.blockentity.FusionPlateBlockEntity;
import com.sammy.malum.common.blockentity.altar.SpiritAltarTileEntity;
import com.sammy.malum.common.blockentity.storage.*;
import com.sammy.malum.common.blockentity.mirror.EmitterMirrorBlockEntity;
import com.sammy.malum.common.blockentity.obelisk.BrilliantObeliskBlockEntity;
import com.sammy.malum.common.blockentity.obelisk.RunewoodObeliskBlockEntity;
import com.sammy.malum.common.blockentity.crucible.SpiritCatalyzerCoreBlockEntity;
import com.sammy.malum.common.blockentity.crucible.SpiritCrucibleCoreBlockEntity;
import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.blockentity.totem.TotemPoleBlockEntity;
import com.sammy.malum.common.blockentity.tablet.TwistedTabletBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.sammy.malum.MalumMod.MALUM;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MALUM);

    public static final RegistryObject<BlockEntityType<SpiritAltarTileEntity>> SPIRIT_ALTAR = BLOCK_ENTITY_TYPES.register("spirit_altar", () -> BlockEntityType.Builder.of(SpiritAltarTileEntity::new, getBlocks(SpiritAltarBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<SpiritJarBlockEntity>> SPIRIT_JAR = BLOCK_ENTITY_TYPES.register("spirit_jar", () -> BlockEntityType.Builder.of(SpiritJarBlockEntity::new, getBlocks(SpiritJarBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<SoulVialBlockEntity>> SOUL_VIAL = BLOCK_ENTITY_TYPES.register("soul_vial", () -> BlockEntityType.Builder.of(SoulVialBlockEntity::new, getBlocks(SoulVialBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<SpiritCrucibleCoreBlockEntity>> SPIRIT_CRUCIBLE = BLOCK_ENTITY_TYPES.register("spirit_crucible", () -> BlockEntityType.Builder.of(SpiritCrucibleCoreBlockEntity::new, getBlocks(SpiritCrucibleCoreBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<SpiritCatalyzerCoreBlockEntity>> SPIRIT_CATALYZER = BLOCK_ENTITY_TYPES.register("spirit_catalyzer", () -> BlockEntityType.Builder.of(SpiritCatalyzerCoreBlockEntity::new, getBlocks(SpiritCatalyzerCoreBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<TwistedTabletBlockEntity>> TWISTED_TABLET = BLOCK_ENTITY_TYPES.register("twisted_tablet", () -> BlockEntityType.Builder.of(TwistedTabletBlockEntity::new, getBlocks(TwistedTabletBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<RunewoodObeliskBlockEntity>> RUNEWOOD_OBELISK = BLOCK_ENTITY_TYPES.register("runewood_obelisk", () -> BlockEntityType.Builder.of(RunewoodObeliskBlockEntity::new, getBlocks(RunewoodObeliskCoreBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<BrilliantObeliskBlockEntity>> BRILLIANT_OBELISK = BLOCK_ENTITY_TYPES.register("brilliant_obelisk", () -> BlockEntityType.Builder.of(BrilliantObeliskBlockEntity::new, getBlocks(BrillianceObeliskCoreBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<PlinthCoreBlockEntity>> PLINTH = BLOCK_ENTITY_TYPES.register("plinth", () -> BlockEntityType.Builder.of(PlinthCoreBlockEntity::new, getBlocks(PlinthCoreBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<FusionPlateBlockEntity>> FUSION_PLATE = BLOCK_ENTITY_TYPES.register("fusion_plate", () -> BlockEntityType.Builder.of(FusionPlateBlockEntity::new, getBlocks(FusionPlateCoreBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<EtherBlockEntity>> ETHER = BLOCK_ENTITY_TYPES.register("ether", () -> BlockEntityType.Builder.of(EtherBlockEntity::new, getBlocks(EtherBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<ItemStandBlockEntity>> ITEM_STAND = BLOCK_ENTITY_TYPES.register("item_stand", () -> BlockEntityType.Builder.of(ItemStandBlockEntity::new, getBlocks(ItemStandBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<ItemPedestalBlockEntity>> ITEM_PEDESTAL = BLOCK_ENTITY_TYPES.register("item_pedestal", () -> BlockEntityType.Builder.of(ItemPedestalBlockEntity::new, getBlocks(ItemPedestalBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<EmitterMirrorBlockEntity>> EMITTER_MIRROR = BLOCK_ENTITY_TYPES.register("emitter_mirror", () -> BlockEntityType.Builder.of(EmitterMirrorBlockEntity::new, getBlocks(EmitterMirrorBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<TotemBaseBlockEntity>> TOTEM_BASE = BLOCK_ENTITY_TYPES.register("totem_base", () -> BlockEntityType.Builder.of(TotemBaseBlockEntity::new, getBlocks(TotemBaseBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<TotemPoleBlockEntity>> TOTEM_POLE = BLOCK_ENTITY_TYPES.register("totem_pole", () -> BlockEntityType.Builder.of(TotemPoleBlockEntity::new, getBlocks(TotemPoleBlock.class)).build(null));


    public static Block[] getBlocks(Class<?>... blockClasses) {
        Collection<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        ArrayList<Block> matchingBlocks = new ArrayList<>();
        for (RegistryObject<Block> registryObject : blocks) {
            if (Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(registryObject.get()))) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    public static Block[] getBlocksExact(Class<?> clazz) {
        Collection<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        ArrayList<Block> matchingBlocks = new ArrayList<>();
        for (RegistryObject<Block> registryObject : blocks) {
            if (clazz.equals(registryObject.get().getClass())) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    @Mod.EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(SPIRIT_ALTAR.get(), SpiritAltarRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_CRUCIBLE.get(), SpiritCrucibleRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_CATALYZER.get(), SpiritCatalyzerRenderer::new);
            event.registerBlockEntityRenderer(TWISTED_TABLET.get(), ItemStandRenderer::new);
            event.registerBlockEntityRenderer(PLINTH.get(), PlinthRenderer::new);
            event.registerBlockEntityRenderer(TOTEM_POLE.get(), TotemPoleRenderer::new);
            event.registerBlockEntityRenderer(ITEM_STAND.get(), ItemStandRenderer::new);
            event.registerBlockEntityRenderer(ITEM_PEDESTAL.get(), ItemPedestalRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_JAR.get(), SpiritJarRenderer::new);
            event.registerBlockEntityRenderer(SOUL_VIAL.get(), SoulVialRenderer::new);
        }
    }
}