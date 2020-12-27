package com.sammy.malum.core.init.blocks;

import com.sammy.malum.common.blocks.abstruceblock.AbstruseBlockTileEntity;
import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;
import com.sammy.malum.common.blocks.spiritjar.SpiritJarTileEntity;
import com.sammy.malum.common.blocks.spiritpipe.SpiritPipeTileEntity;
import com.sammy.malum.common.blocks.taintedfurnace.TaintedFurnaceCoreTileEntity;
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
    
//    public static final RegistryObject<TileEntityType<ArcaneCraftingTableTileEntity>> ARCANE_CRAFTING_TABLE_TILE_ENTITY = TILE_ENTITIES.register("arcane_crafting_table_tile_entity", ()-> TileEntityType.Builder.create(ArcaneCraftingTableTileEntity::new, MalumBlocks.ARCANE_CRAFTING_TABLE.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> BOUNDING_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("bounding_block_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) BoundingBlockTileEntity::new, MalumBlocks.TAINTED_FURNACE_TOP.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> TAINTED_FURNACE_TILE_ENTITY = TILE_ENTITIES.register("tainted_furnace_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) TaintedFurnaceCoreTileEntity::new, MalumBlocks.TAINTED_FURNACE.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> ABSTRUSE_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("abstruse_block_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) AbstruseBlockTileEntity::new, MalumBlocks.ABSTRUSE_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> ITEM_STAND_TILE_ENTITY = TILE_ENTITIES.register("item_stand_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) ItemStandTileEntity::new, MalumBlocks.ITEM_STAND.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> SPIRIT_JAR_TILE_ENTITY = TILE_ENTITIES.register("spirit_jar_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) SpiritJarTileEntity::new, MalumBlocks.SPIRIT_JAR.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> SPIRIT_PIPE_TILE_ENTITY = TILE_ENTITIES.register("spirit_pipe_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) SpiritPipeTileEntity::new, MalumBlocks.SPIRIT_PIPE.get(), MalumBlocks.OPEN_TRANSMISSIVE_METAL_BLOCK.get()).build(null));

}
