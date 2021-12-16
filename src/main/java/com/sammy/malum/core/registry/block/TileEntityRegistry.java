package com.sammy.malum.core.registry.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.tile_renderer.*;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.item_storage.ItemPedestalBlock;
import com.sammy.malum.common.block.item_storage.ItemStandBlock;
import com.sammy.malum.common.block.item_storage.SpiritJarBlock;
import com.sammy.malum.common.block.misc.sign.MalumStandingSignBlock;
import com.sammy.malum.common.block.misc.sign.MalumWallSignBlock;
import com.sammy.malum.common.block.spirit_altar.SpiritAltarBlock;
import com.sammy.malum.common.block.totem.TotemBaseBlock;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.common.tile.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.sammy.malum.MalumMod.MODID;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class TileEntityRegistry
{
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);

    public static final RegistryObject<BlockEntityType<MalumSignTileEntity>> SIGN_TILE_ENTITY = TILE_ENTITIES.register("sign_tile_entity", () -> BlockEntityType.Builder.of(MalumSignTileEntity::new, MalumHelper.getModBlocks(MalumWallSignBlock.class, MalumStandingSignBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<SpiritAltarTileEntity>> SPIRIT_ALTAR_TILE_ENTITY = TILE_ENTITIES.register("spirit_altar_tile_entity", () -> BlockEntityType.Builder.of(SpiritAltarTileEntity::new, MalumHelper.getModBlocks(SpiritAltarBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<SpiritJarTileEntity>> SPIRIT_JAR_TILE_ENTITY = TILE_ENTITIES.register("spirit_jar_tile_entity", () -> BlockEntityType.Builder.of(SpiritJarTileEntity::new, MalumHelper.getModBlocks(SpiritJarBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<?>> ETHER_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("ether_tile_entity", () -> BlockEntityType.Builder.of((Supplier<BlockEntity>) EtherTileEntity::new, MalumHelper.getModBlocks(EtherBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<ItemStandTileEntity>> ITEM_STAND_TILE_ENTITY = TILE_ENTITIES.register("item_stand_tile_entity", () -> BlockEntityType.Builder.of(ItemStandTileEntity::new, MalumHelper.getModBlocks(ItemStandBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<ItemPedestalTileEntity>> ITEM_PEDESTAL_TILE_ENTITY = TILE_ENTITIES.register("item_pedestal_tile_entity", () -> BlockEntityType.Builder.of(ItemPedestalTileEntity::new, MalumHelper.getModBlocks(ItemPedestalBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<TotemBaseTileEntity>> TOTEM_BASE_TILE_ENTITY = TILE_ENTITIES.register("totem_base_tile_entity", () -> BlockEntityType.Builder.of(TotemBaseTileEntity::new, MalumHelper.getModBlocks(TotemBaseBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<TotemPoleTileEntity>> TOTEM_POLE_TILE_ENTITY = TILE_ENTITIES.register("totem_pole_tile_entity", () -> BlockEntityType.Builder.of(TotemPoleTileEntity::new, MalumHelper.getModBlocks(TotemPoleBlock.class)).build(null));

    @SubscribeEvent
    public static void bindTileEntityRenderers(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(SPIRIT_ALTAR_TILE_ENTITY.get(), SpiritAltarRenderer::new);
        BlockEntityRenderers.register(TOTEM_POLE_TILE_ENTITY.get(), TotemPoleRenderer::new);
        BlockEntityRenderers.register(ITEM_STAND_TILE_ENTITY.get(), ItemStandRenderer::new);
        BlockEntityRenderers.register(ITEM_PEDESTAL_TILE_ENTITY.get(), ItemPedestalRenderer::new);
        BlockEntityRenderers.register(SPIRIT_JAR_TILE_ENTITY.get(), SpiritJarRenderer::new);
        BlockEntityRenderers.register(SIGN_TILE_ENTITY.get(), SignRenderer::new);
    }
}
