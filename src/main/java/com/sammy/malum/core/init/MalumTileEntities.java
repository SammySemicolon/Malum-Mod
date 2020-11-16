package com.sammy.malum.core.init;

import com.google.common.collect.ImmutableSet;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.arcanecraftingtable.ArcaneCraftingTableTileEntity;
import com.sammy.malum.common.blocks.blightingfurnace.BlightingFurnaceTileEntity;
import com.sammy.malum.core.systems.multiblock.BoundingBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.sammy.malum.MalumMod.MODID;
import static com.sammy.malum.core.init.MalumBlocks.BLOCKS;

public class MalumTileEntities
{
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    
    public static final RegistryObject<TileEntityType<ArcaneCraftingTableTileEntity>> ARCANE_CRAFTING_TABLE_TILE_ENTITY = TILE_ENTITIES.register("arcane_crafting_table", ()-> TileEntityType.Builder.create(ArcaneCraftingTableTileEntity::new, MalumBlocks.SUN_KISSED_ARCANE_CRAFTING_TABLE.get(), MalumBlocks.TAINTED_ARCANE_CRAFTING_TABLE.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> BOUNDING_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("bounding_block", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) BoundingBlockTileEntity::new, MalumBlocks.BLIGHTING_FURNACE_TOP.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> BLIGHTING_FURNACE_TILE_ENTITY = TILE_ENTITIES.register("blighting_furnace", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) BlightingFurnaceTileEntity::new, MalumBlocks.BLIGHTING_FURNACE.get()).build(null));

}
