package com.sammy.malum.init;

import com.sammy.malum.blocks.machines.funkengine.FunkEngineTileEntity;
import com.sammy.malum.blocks.machines.spiritfurnace.SpiritFurnaceTileEntity;
import com.sammy.malum.blocks.machines.mirror.HolderMirrorTileEntity;
import com.sammy.malum.blocks.machines.mirror.InputMirrorTileEntity;
import com.sammy.malum.blocks.machines.mirror.OutputMirrorTileEntity;
import com.sammy.malum.blocks.machines.redstoneclock.RedstoneClockTileEntity;
import com.sammy.malum.blocks.machines.spiritjar.SpiritJarTileEntity;
import com.sammy.malum.blocks.machines.spiritsmeltery.SpiritSmelteryTileEntity;
import com.sammy.malum.blocks.utility.BasicTileEntity;
import com.sammy.malum.blocks.utility.multiblock.BoundingBlockTileEntity;
import com.sammy.malum.blocks.utility.spiritstorage.SpiritStoringTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTileEntities
{
    @ObjectHolder("malum:basic_tile_entity")
    public static TileEntityType<BasicTileEntity> basic_tile_entity;

    @ObjectHolder("malum:soul_storing_tile_entity")
    public static TileEntityType<SpiritStoringTileEntity> soul_storing_tile_entity;

    @ObjectHolder("malum:spirit_jar_tile_entity")
    public static TileEntityType<SpiritJarTileEntity> spirit_jar_tile_entity;

    @ObjectHolder("malum:basic_mirror_tile_entity")
    public static TileEntityType<HolderMirrorTileEntity> basic_mirror_tile_entity;

    @ObjectHolder("malum:input_mirror_tile_entity")
    public static TileEntityType<InputMirrorTileEntity> input_mirror_tile_entity;

    @ObjectHolder("malum:output_mirror_tile_entity")
    public static TileEntityType<OutputMirrorTileEntity> output_mirror_tile_entity;

    @ObjectHolder("malum:funk_engine_tile_entity")
    public static TileEntityType<FunkEngineTileEntity> funk_engine_tile_entity;
    
    @ObjectHolder("malum:redstone_clock_tile_entity")
    public static TileEntityType<RedstoneClockTileEntity> redstone_clock_tile_entity;
    
    @ObjectHolder("malum:spirit_furnace_tile_entity")
    public static TileEntityType<SpiritFurnaceTileEntity> spirit_furnace_tile_entity;
    
    @ObjectHolder("malum:spirit_smeltery_tile_entity")
    public static TileEntityType<SpiritSmelteryTileEntity> spirit_smeltery_tile_entity;
    
    @ObjectHolder("malum:bounding_block_tile_entity")
    public static TileEntityType<BoundingBlockTileEntity> bounding_block_tile_entity;

    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> e)
    {
        e.getRegistry().registerAll(
                TileEntityType.Builder.create((Supplier<TileEntity>) SpiritJarTileEntity::new, ModBlocks.spirit_jar).build(null).setRegistryName("spirit_jar_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) HolderMirrorTileEntity::new, ModBlocks.basic_mirror).build(null).setRegistryName("basic_mirror_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) InputMirrorTileEntity::new, ModBlocks.input_mirror).build(null).setRegistryName("input_mirror_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) OutputMirrorTileEntity::new, ModBlocks.output_mirror).build(null).setRegistryName("output_mirror_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) FunkEngineTileEntity::new, ModBlocks.funk_engine).build(null).setRegistryName("funk_engine_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) RedstoneClockTileEntity::new, ModBlocks.redstone_clock).build(null).setRegistryName("redstone_clock_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) SpiritFurnaceTileEntity::new, ModBlocks.spirit_furnace).build(null).setRegistryName("spirit_furnace_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) SpiritSmelteryTileEntity::new, ModBlocks.spirit_smeltery).build(null).setRegistryName("spirit_smeltery_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) BoundingBlockTileEntity::new, ModBlocks.spirit_furnace_bounding_block, ModBlocks.spirit_smeltery_bounding_block).build(null).setRegistryName("bounding_block_tile_entity")
        );
    }
}
