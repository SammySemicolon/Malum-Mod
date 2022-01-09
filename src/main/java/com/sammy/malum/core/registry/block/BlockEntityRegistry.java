package com.sammy.malum.core.registry.block;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.renderer.block.*;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.fusion_plate.FusionPlateCoreBlock;
import com.sammy.malum.common.block.item_storage.ItemPedestalBlock;
import com.sammy.malum.common.block.item_storage.ItemStandBlock;
import com.sammy.malum.common.block.item_storage.PlinthCoreBlock;
import com.sammy.malum.common.block.item_storage.SpiritJarBlock;
import com.sammy.malum.common.block.spirit_altar.ObeliskCoreBlock;
import com.sammy.malum.common.block.spirit_altar.SpiritAltarBlock;
import com.sammy.malum.common.block.spirit_crucible.SpiritCrucibleCoreBlock;
import com.sammy.malum.common.block.totem.TotemBaseBlock;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.common.blockentity.*;
import com.sammy.malum.common.blockentity.item_storage.ItemPedestalTileEntity;
import com.sammy.malum.common.blockentity.item_storage.ItemStandTileEntity;
import com.sammy.malum.common.blockentity.item_storage.PlinthCoreBlockEntity;
import com.sammy.malum.common.blockentity.item_storage.SpiritJarTileEntity;
import com.sammy.malum.common.blockentity.totem.TotemBaseTileEntity;
import com.sammy.malum.common.blockentity.totem.TotemPoleTileEntity;
import com.sammy.malum.core.systems.multiblock.ComponentBlock;
import com.sammy.malum.core.systems.multiblock.MultiBlockComponentEntity;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
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

import static com.sammy.malum.MalumMod.MODID;

public class BlockEntityRegistry
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);

    public static final RegistryObject<BlockEntityType<MalumSignTileEntity>> SIGN_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("sign", () -> BlockEntityType.Builder.of(MalumSignTileEntity::new, getBlocks(SignBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<SpiritAltarTileEntity>> SPIRIT_ALTAR = BLOCK_ENTITY_TYPES.register("spirit_altar", () -> BlockEntityType.Builder.of(SpiritAltarTileEntity::new, getBlocks(SpiritAltarBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<SpiritJarTileEntity>> SPIRIT_JAR = BLOCK_ENTITY_TYPES.register("spirit_jar_tile_entity", () -> BlockEntityType.Builder.of(SpiritJarTileEntity::new, getBlocks(SpiritJarBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<SpiritCrucibleCoreBlockEntity>> SPIRIT_CRUCIBLE = BLOCK_ENTITY_TYPES.register("spirit_crucible", () -> BlockEntityType.Builder.of(SpiritCrucibleCoreBlockEntity::new, getBlocks(SpiritCrucibleCoreBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<ObeliskCoreBlockEntity>> OBELISK = BLOCK_ENTITY_TYPES.register("obelisk", () -> BlockEntityType.Builder.of(ObeliskCoreBlockEntity::new, getBlocks(ObeliskCoreBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<PlinthCoreBlockEntity>> PLINTH = BLOCK_ENTITY_TYPES.register("plinth", () -> BlockEntityType.Builder.of(PlinthCoreBlockEntity::new, getBlocks(PlinthCoreBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<FusionPlateBlockEntity>> FUSION_PLATE = BLOCK_ENTITY_TYPES.register("fusion_plate", () -> BlockEntityType.Builder.of(FusionPlateBlockEntity::new, getBlocks(FusionPlateCoreBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<MultiBlockComponentEntity>> MULTIBLOCK_COMPONENT = BLOCK_ENTITY_TYPES.register("multiblock_component", () -> BlockEntityType.Builder.of(MultiBlockComponentEntity::new, getBlocks(ComponentBlock.class)).build(null));


    public static final RegistryObject<BlockEntityType<EtherTileEntity>> ETHER = BLOCK_ENTITY_TYPES.register("ether", () -> BlockEntityType.Builder.of(EtherTileEntity::new, getBlocks(EtherBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<ItemStandTileEntity>> ITEM_STAND = BLOCK_ENTITY_TYPES.register("item_stand", () -> BlockEntityType.Builder.of(ItemStandTileEntity::new, getBlocks(ItemStandBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<ItemPedestalTileEntity>> ITEM_PEDESTAL = BLOCK_ENTITY_TYPES.register("item_pedestal", () -> BlockEntityType.Builder.of(ItemPedestalTileEntity::new, getBlocks(ItemPedestalBlock.class)).build(null));

    public static final RegistryObject<BlockEntityType<TotemBaseTileEntity>> TOTEM_BASE = BLOCK_ENTITY_TYPES.register("totem_base", () -> BlockEntityType.Builder.of(TotemBaseTileEntity::new, getBlocks(TotemBaseBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<TotemPoleTileEntity>> TOTEM_POLE = BLOCK_ENTITY_TYPES.register("totem_pole", () -> BlockEntityType.Builder.of(TotemPoleTileEntity::new, getBlocks(TotemPoleBlock.class)).build(null));


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
    @Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(SPIRIT_ALTAR.get(), SpiritAltarRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_CRUCIBLE.get(), SpiritCrucibleRenderer::new);
            event.registerBlockEntityRenderer(PLINTH.get(), PlinthRenderer::new);
            event.registerBlockEntityRenderer(TOTEM_POLE.get(), TotemPoleRenderer::new);
            event.registerBlockEntityRenderer(ITEM_STAND.get(), ItemStandRenderer::new);
            event.registerBlockEntityRenderer(ITEM_PEDESTAL.get(), ItemPedestalRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_JAR.get(), SpiritJarRenderer::new);
            event.registerBlockEntityRenderer(SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        }
    }
}
