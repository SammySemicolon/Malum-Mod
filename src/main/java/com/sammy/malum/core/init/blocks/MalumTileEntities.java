package com.sammy.malum.core.init.blocks;

import com.sammy.malum.common.blocks.abstruceblock.AbstruseBlockTileEntity;
import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;

import com.sammy.malum.common.blocks.lighting.BasicLightingTileEntity;
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
    
    public static final RegistryObject<TileEntityType<?>> BOUNDING_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("bounding_block_tile_entity", () -> TileEntityType.Builder.create((Supplier<TileEntity>) BoundingBlockTileEntity::new, MalumBlocks.SPIRIT_KILN_TOP.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> SPIRIT_KILN_TILE_ENTITY = TILE_ENTITIES.register("spirit_kiln_tile_entity", () -> TileEntityType.Builder.create((Supplier<TileEntity>) SpiritKilnCoreTileEntity::new, MalumBlocks.SPIRIT_KILN.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> ABSTRUSE_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("abstruse_block_tile_entity", () -> TileEntityType.Builder.create((Supplier<TileEntity>) AbstruseBlockTileEntity::new, MalumBlocks.ABSTRUSE_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> ETHER_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("ether_tile_entity", () -> TileEntityType.Builder.create((Supplier<TileEntity>) BasicLightingTileEntity::new, MalumBlocks.ETHER_TORCH.get(), MalumBlocks.WALL_ETHER_TORCH.get(), MalumBlocks.ETHER.get(), MalumBlocks.ETHER_BRAZIER.get(), MalumBlocks.ORANGE_ETHER_BRAZIER.get(), MalumBlocks.MAGENTA_ETHER_BRAZIER.get(), MalumBlocks.LIGHT_BLUE_ETHER_BRAZIER.get(), MalumBlocks.YELLOW_ETHER_BRAZIER.get(), MalumBlocks.LIME_ETHER_BRAZIER.get(), MalumBlocks.CYAN_ETHER_BRAZIER.get(), MalumBlocks.PURPLE_ETHER_BRAZIER.get(), MalumBlocks.BLUE_ETHER_BRAZIER.get(), MalumBlocks.BROWN_ETHER_BRAZIER.get(), MalumBlocks.GREEN_ETHER_BRAZIER.get(), MalumBlocks.RED_ETHER_BRAZIER.get(),MalumBlocks.ORANGE_ETHER_TORCH.get(), MalumBlocks.MAGENTA_ETHER_TORCH.get(), MalumBlocks.LIGHT_BLUE_ETHER_TORCH.get(), MalumBlocks.YELLOW_ETHER_TORCH.get(), MalumBlocks.LIME_ETHER_TORCH.get(), MalumBlocks.CYAN_ETHER_TORCH.get(), MalumBlocks.PURPLE_ETHER_TORCH.get(), MalumBlocks.BLUE_ETHER_TORCH.get(), MalumBlocks.BROWN_ETHER_TORCH.get(), MalumBlocks.GREEN_ETHER_TORCH.get(), MalumBlocks.RED_ETHER_TORCH.get(), MalumBlocks.ORANGE_WALL_ETHER_TORCH.get(), MalumBlocks.MAGENTA_WALL_ETHER_TORCH.get(), MalumBlocks.LIGHT_BLUE_WALL_ETHER_TORCH.get(), MalumBlocks.YELLOW_WALL_ETHER_TORCH.get(), MalumBlocks.LIME_WALL_ETHER_TORCH.get(), MalumBlocks.CYAN_WALL_ETHER_TORCH.get(), MalumBlocks.PURPLE_WALL_ETHER_TORCH.get(), MalumBlocks.BLUE_WALL_ETHER_TORCH.get(), MalumBlocks.BROWN_WALL_ETHER_TORCH.get(), MalumBlocks.GREEN_WALL_ETHER_TORCH.get(), MalumBlocks.RED_WALL_ETHER_TORCH.get(), MalumBlocks.ORANGE_ETHER.get(), MalumBlocks.MAGENTA_ETHER.get(), MalumBlocks.LIGHT_BLUE_ETHER.get(), MalumBlocks.YELLOW_ETHER.get(), MalumBlocks.LIME_ETHER.get(), MalumBlocks.CYAN_ETHER.get(), MalumBlocks.PURPLE_ETHER.get(), MalumBlocks.BLUE_ETHER.get(), MalumBlocks.BROWN_ETHER.get(), MalumBlocks.GREEN_ETHER.get(), MalumBlocks.RED_ETHER.get()).build(null));
    public static final RegistryObject<TileEntityType<?>> ITEM_STAND_TILE_ENTITY = TILE_ENTITIES.register("item_stand_tile_entity", () -> TileEntityType.Builder.create((Supplier<TileEntity>) ItemStandTileEntity::new, MalumBlocks.ITEM_STAND.get()).build(null));
}
