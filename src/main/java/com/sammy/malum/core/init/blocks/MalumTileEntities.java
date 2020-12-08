package com.sammy.malum.core.init.blocks;

import com.sammy.malum.common.blocks.abstruceblock.AbstruseBlockTileEntity;
import com.sammy.malum.common.blocks.arcanecraftingtable.ArcaneCraftingTableTileEntity;
import com.sammy.malum.common.blocks.blightingfurnace.BlightingFurnaceTileEntity;
import com.sammy.malum.common.blocks.essencejar.EssenceJarTileEntity;
import com.sammy.malum.common.blocks.essencepipe.EssencePipeTileEntity;
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
    
    public static final RegistryObject<TileEntityType<ArcaneCraftingTableTileEntity>> ARCANE_CRAFTING_TABLE_TILE_ENTITY = TILE_ENTITIES.register("arcane_crafting_table_tile_entity", ()-> TileEntityType.Builder.create(ArcaneCraftingTableTileEntity::new, MalumBlocks.ARCANE_CRAFTING_TABLE.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> BOUNDING_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("bounding_block_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) BoundingBlockTileEntity::new, MalumBlocks.BLIGHTING_FURNACE_TOP.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> BLIGHTING_FURNACE_TILE_ENTITY = TILE_ENTITIES.register("blighting_furnace_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) BlightingFurnaceTileEntity::new, MalumBlocks.BLIGHTING_FURNACE.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> ABSTRUSE_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("abstruse_block_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) AbstruseBlockTileEntity::new, MalumBlocks.ABSTRUSE_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> ESSENCE_JAR_TILE_ENTITY = TILE_ENTITIES.register("essence_jar_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) EssenceJarTileEntity::new, MalumBlocks.ESSENCE_JAR.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> ESSENCE_PIPE_TILE_ENTITY = TILE_ENTITIES.register("essence_pipe_tile_entity", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) EssencePipeTileEntity::new, MalumBlocks.ESSENCE_PIPE.get()).build(null));

}
