package com.sammy.malum.core.init;

import com.sammy.malum.common.blocks.arcanecraftingtable.ArcaneCraftingTableTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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
    
    public static final RegistryObject<TileEntityType<?>> ARCANE_CRAFTING_TABLE_TILE_ENTITY = TILE_ENTITIES.register("arcane_crafting_table", ()-> TileEntityType.Builder.create((Supplier<TileEntity>) ArcaneCraftingTableTileEntity::new, MalumBlocks.SUN_KISSED_ARCANE_CRAFTING_TABLE.get(), MalumBlocks.TAINTED_ARCANE_CRAFTING_TABLE.get()).build(null));
    
}
