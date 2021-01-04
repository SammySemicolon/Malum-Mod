package com.sammy.malum.core.init.blocks;

import com.sammy.malum.common.blocks.abstruceblock.AbstruseBlockTileEntity;
import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;

import com.sammy.malum.common.blocks.spiritkiln.SpiritKilnCoreTileEntity;

import com.sammy.malum.core.systems.multiblock.BoundingBlockTileEntity;
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
    
    public static final RegistryObject<TileEntityType<?>> BOUNDING_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("bounding_block_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) BoundingBlockTileEntity::new, MalumBlocks.SPIRIT_KILN_TOP.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> SPIRIT_KILN_TILE_ENTITY = TILE_ENTITIES.register("spirit_kiln_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) SpiritKilnCoreTileEntity::new, MalumBlocks.SPIRIT_KILN.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> ABSTRUSE_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("abstruse_block_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) AbstruseBlockTileEntity::new, MalumBlocks.ABSTRUSE_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> ITEM_STAND_TILE_ENTITY = TILE_ENTITIES.register("item_stand_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) ItemStandTileEntity::new, MalumBlocks.ITEM_STAND.get()).build(null));

}
