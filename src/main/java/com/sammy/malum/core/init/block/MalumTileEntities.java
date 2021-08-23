package com.sammy.malum.core.init.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.generic.sign.MalumStandingSignBlock;
import com.sammy.malum.common.block.generic.sign.MalumWallSignBlock;
import com.sammy.malum.common.block.item_storage.ItemPedestalBlock;
import com.sammy.malum.common.block.item_storage.ItemStandBlock;
import com.sammy.malum.common.tile.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.sammy.malum.MalumMod.MODID;

public class MalumTileEntities
{
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);

    public static final RegistryObject<TileEntityType<MalumSignTileEntity>> SIGN_TILE_ENTITY = TILE_ENTITIES.register("sign_tile_entity", () -> TileEntityType.Builder.create(MalumSignTileEntity::new, MalumHelper.getModBlocks(MalumWallSignBlock.class, MalumStandingSignBlock.class)).build(null));

    public static final RegistryObject<TileEntityType<SpiritAltarTileEntity>> SPIRIT_ALTAR_TILE_ENTITY = TILE_ENTITIES.register("spirit_altar_tile_entity", () -> TileEntityType.Builder.create(SpiritAltarTileEntity::new, MalumBlocks.SPIRIT_ALTAR.get()).build(null));

    public static final RegistryObject<TileEntityType<SpiritJarTileEntity>> SPIRIT_JAR_TILE_ENTITY = TILE_ENTITIES.register("spirit_jar_tile_entity", () -> TileEntityType.Builder.create(SpiritJarTileEntity::new, MalumBlocks.SPIRIT_JAR.get()).build(null));

    public static final RegistryObject<TileEntityType<?>> ETHER_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("ether_tile_entity", () -> TileEntityType.Builder.create((Supplier<TileEntity>) EtherTileEntity::new, MalumHelper.getModBlocks(EtherBlock.class)).build(null));

    public static final RegistryObject<TileEntityType<ItemStandTileEntity>> ITEM_STAND_TILE_ENTITY = TILE_ENTITIES.register("item_stand_tile_entity", () -> TileEntityType.Builder.create(ItemStandTileEntity::new, MalumHelper.getModBlocks(ItemStandBlock.class)).build(null));
    public static final RegistryObject<TileEntityType<ItemPedestalTileEntity>> ITEM_PEDESTAL_TILE_ENTITY = TILE_ENTITIES.register("item_pedestal_tile_entity", () -> TileEntityType.Builder.create(ItemPedestalTileEntity::new, MalumHelper.getModBlocks(ItemPedestalBlock.class)).build(null));

    public static final RegistryObject<TileEntityType<TotemBaseTileEntity>> TOTEM_BASE_TILE_ENTITY = TILE_ENTITIES.register("totem_base_tile_entity", () -> TileEntityType.Builder.create(TotemBaseTileEntity::new, MalumBlocks.TOTEM_BASE.get()).build(null));
    public static final RegistryObject<TileEntityType<TotemPoleTileEntity>> TOTEM_POLE_TILE_ENTITY = TILE_ENTITIES.register("totem_pole_tile_entity", () -> TileEntityType.Builder.create(TotemPoleTileEntity::new, MalumBlocks.TOTEM_POLE.get()).build(null));


}
