package com.sammy.malum.registry.common.block;

import com.sammy.malum.client.renderer.block.*;
import com.sammy.malum.common.block.curiosities.obelisk.brilliant.BrilliantObeliskBlockEntity;
import com.sammy.malum.common.block.curiosities.obelisk.runewood.RunewoodObeliskBlockEntity;
import com.sammy.malum.common.block.curiosities.repair_pylon.RepairPylonCoreBlockEntity;
import com.sammy.malum.common.block.curiosities.ritual_plinth.RitualPlinthBlockEntity;
import com.sammy.malum.common.block.curiosities.runic_workbench.RunicWorkbenchBlockEntity;
import com.sammy.malum.common.block.curiosities.spirit_altar.SpiritAltarBlockEntity;
import com.sammy.malum.common.block.curiosities.spirit_crucible.SpiritCrucibleCoreBlockEntity;
import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.SpiritCatalyzerCoreBlockEntity;
import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlock;
import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlock;
import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlockEntity;
import com.sammy.malum.common.block.curiosities.void_depot.VoidDepotBlockEntity;
import com.sammy.malum.common.block.curiosities.weavers_workbench.WeaversWorkbenchBlockEntity;
import com.sammy.malum.common.block.curiosities.weeping_well.VoidConduitBlockEntity;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.ether.EtherBlockEntity;
import com.sammy.malum.common.block.mana_mote.MoteOfManaBlockEntity;
import com.sammy.malum.common.block.mana_mote.SpiritMoteBlock;
import com.sammy.malum.common.block.storage.jar.SpiritJarBlockEntity;
import com.sammy.malum.common.block.storage.pedestal.ItemPedestalBlock;
import com.sammy.malum.common.block.storage.pedestal.ItemPedestalBlockEntity;
import com.sammy.malum.common.block.storage.stand.ItemStandBlock;
import com.sammy.malum.common.block.storage.stand.ItemStandBlockEntity;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.sammy.malum.MalumMod.MALUM;

public class BlockEntityRegistry {
    public static final LazyRegistrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = LazyRegistrar.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MALUM);

    public static final RegistryObject<BlockEntityType<VoidConduitBlockEntity>> VOID_CONDUIT = BLOCK_ENTITY_TYPES.register("void_conduit", () -> BlockEntityType.Builder.of(VoidConduitBlockEntity::new, BlockRegistry.VOID_CONDUIT.get()).build(null));
    public static final RegistryObject<BlockEntityType<VoidDepotBlockEntity>> VOID_DEPOT = BLOCK_ENTITY_TYPES.register("void_depot", () -> BlockEntityType.Builder.of(VoidDepotBlockEntity::new, BlockRegistry.VOID_DEPOT.get()).build(null));

    public static final RegistryObject<BlockEntityType<SpiritAltarBlockEntity>> SPIRIT_ALTAR = BLOCK_ENTITY_TYPES.register("spirit_altar", () -> BlockEntityType.Builder.of(SpiritAltarBlockEntity::new, BlockRegistry.SPIRIT_ALTAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<SpiritJarBlockEntity>> SPIRIT_JAR = BLOCK_ENTITY_TYPES.register("spirit_jar", () -> BlockEntityType.Builder.of(SpiritJarBlockEntity::new, BlockRegistry.SPIRIT_JAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<RitualPlinthBlockEntity>> RITUAL_PLINTH = BLOCK_ENTITY_TYPES.register("ritual_plinth", () -> BlockEntityType.Builder.of(RitualPlinthBlockEntity::new, BlockRegistry.RITUAL_PLINTH.get()).build(null));

    public static final RegistryObject<BlockEntityType<WeaversWorkbenchBlockEntity>> WEAVERS_WORKBENCH = BLOCK_ENTITY_TYPES.register("weavers_workbench", () -> BlockEntityType.Builder.of(WeaversWorkbenchBlockEntity::new, BlockRegistry.WEAVERS_WORKBENCH.get()).build(null));
    public static final RegistryObject<BlockEntityType<RunicWorkbenchBlockEntity>> RUNIC_WORKBENCH = BLOCK_ENTITY_TYPES.register("runic_workbench", () -> BlockEntityType.Builder.of(RunicWorkbenchBlockEntity::new, BlockRegistry.RUNIC_WORKBENCH.get()).build(null));

    public static final RegistryObject<BlockEntityType<SpiritCrucibleCoreBlockEntity>> SPIRIT_CRUCIBLE = BLOCK_ENTITY_TYPES.register("spirit_crucible", () -> BlockEntityType.Builder.of(SpiritCrucibleCoreBlockEntity::new, BlockRegistry.SPIRIT_CRUCIBLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<SpiritCatalyzerCoreBlockEntity>> SPIRIT_CATALYZER = BLOCK_ENTITY_TYPES.register("spirit_catalyzer", () -> BlockEntityType.Builder.of(SpiritCatalyzerCoreBlockEntity::new, BlockRegistry.SPIRIT_CATALYZER.get()).build(null));
    public static final RegistryObject<BlockEntityType<RepairPylonCoreBlockEntity>> REPAIR_PYLON = BLOCK_ENTITY_TYPES.register("repair_pylon", () -> BlockEntityType.Builder.of(RepairPylonCoreBlockEntity::new, BlockRegistry.REPAIR_PYLON.get()).build(null));
    public static final RegistryObject<BlockEntityType<RunewoodObeliskBlockEntity>> RUNEWOOD_OBELISK = BLOCK_ENTITY_TYPES.register("runewood_obelisk", () -> BlockEntityType.Builder.of(RunewoodObeliskBlockEntity::new, BlockRegistry.RUNEWOOD_OBELISK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BrilliantObeliskBlockEntity>> BRILLIANT_OBELISK = BLOCK_ENTITY_TYPES.register("brilliant_obelisk", () -> BlockEntityType.Builder.of(BrilliantObeliskBlockEntity::new, BlockRegistry.BRILLIANT_OBELISK.get()).build(null));

    public static final RegistryObject<BlockEntityType<EtherBlockEntity>> ETHER = BLOCK_ENTITY_TYPES.register("ether", () -> BlockEntityType.Builder.of(EtherBlockEntity::new, getBlocks(EtherBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<ItemStandBlockEntity>> ITEM_STAND = BLOCK_ENTITY_TYPES.register("item_stand", () -> BlockEntityType.Builder.of(ItemStandBlockEntity::new, getBlocks(ItemStandBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<ItemPedestalBlockEntity>> ITEM_PEDESTAL = BLOCK_ENTITY_TYPES.register("item_pedestal", () -> BlockEntityType.Builder.of(ItemPedestalBlockEntity::new, getBlocks(ItemPedestalBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<TotemBaseBlockEntity>> TOTEM_BASE = BLOCK_ENTITY_TYPES.register("totem_base", () -> BlockEntityType.Builder.of(TotemBaseBlockEntity::new, getBlocks(TotemBaseBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<TotemPoleBlockEntity>> TOTEM_POLE = BLOCK_ENTITY_TYPES.register("totem_pole", () -> BlockEntityType.Builder.of(TotemPoleBlockEntity::new, getBlocks(TotemPoleBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<MoteOfManaBlockEntity>> MOTE_OF_MANA = BLOCK_ENTITY_TYPES.register("mote_of_mana", () -> BlockEntityType.Builder.of(MoteOfManaBlockEntity::new, getBlocks(SpiritMoteBlock.class)).build(null));

    public static Block[] getBlocks(Class<?>... blockClasses) {
        Collection<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        List<Block> matchingBlocks = new ArrayList<>();
        for (RegistryObject<Block> registryObject : blocks) {
            if (registryObject.isPresent() && Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(registryObject.get()))) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    public static Block[] getBlocksExact(Class<?> clazz) {
        Collection<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        List<Block> matchingBlocks = new ArrayList<>();
        for (RegistryObject<Block> registryObject : blocks) {
            if (clazz.equals(registryObject.get().getClass())) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    public static class ClientOnly {

        public static void registerRenderer() {
            BlockEntityRenderers.register(VOID_CONDUIT.get(), VoidConduitRenderer::new);
            BlockEntityRenderers.register(VOID_DEPOT.get(), VoidDepotRenderer::new);
            BlockEntityRenderers.register(SPIRIT_ALTAR.get(), SpiritAltarRenderer::new);
            BlockEntityRenderers.register(RUNIC_WORKBENCH.get(), MalumItemHolderRenderer::new);
            BlockEntityRenderers.register(SPIRIT_CRUCIBLE.get(), SpiritCrucibleRenderer::new);
            BlockEntityRenderers.register(SPIRIT_CATALYZER.get(), SpiritCatalyzerRenderer::new);
            BlockEntityRenderers.register(REPAIR_PYLON.get(), RepairPylonRenderer::new);
            BlockEntityRenderers.register(TOTEM_BASE.get(), TotemBaseRenderer::new);
            BlockEntityRenderers.register(TOTEM_POLE.get(), TotemPoleRenderer::new);
            BlockEntityRenderers.register(RITUAL_PLINTH.get(), RitualPlinthRenderer::new);
            BlockEntityRenderers.register(ITEM_STAND.get(), MalumItemHolderRenderer::new);
            BlockEntityRenderers.register(ITEM_PEDESTAL.get(), MalumItemHolderRenderer::new);
            BlockEntityRenderers.register(SPIRIT_JAR.get(), SpiritJarRenderer::new);
            BlockEntityRenderers.register(MOTE_OF_MANA.get(), MoteOfManaRenderer::new);
        }
    }
}
