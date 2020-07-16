package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.blocks.machines.mirror.BasicMirrorTileEntity;
import com.kittykitcatcat.malum.blocks.machines.mirror.InputMirrorTileEntity;
import com.kittykitcatcat.malum.blocks.machines.mirror.OutputMirrorTileEntity;
import com.kittykitcatcat.malum.blocks.machines.spiritfurnace.SpiritFurnaceBottomTileEntity;
import com.kittykitcatcat.malum.blocks.machines.spiritfurnace.SpiritFurnaceTopTileEntity;
import com.kittykitcatcat.malum.blocks.machines.spiritjar.SpiritJarTileEntity;
import com.kittykitcatcat.malum.blocks.utility.soulstorage.SpiritStoringTileEntity;
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
    @ObjectHolder("malum:soul_storing_tile_entity")
    public static TileEntityType<SpiritStoringTileEntity> soul_storing_tile_entity;

    @ObjectHolder("malum:spirit_jar_tile_entity")
    public static TileEntityType<SpiritJarTileEntity> spirit_jar_tile_entity;

    @ObjectHolder("malum:spirit_furnace_bottom_tile_entity")
    public static TileEntityType<SpiritFurnaceBottomTileEntity> spirit_furnace_bottom_tile_entity;

    @ObjectHolder("malum:spirit_furnace_top_tile_entity")
    public static TileEntityType<SpiritFurnaceTopTileEntity> spirit_furnace_top_tile_entity;

    @ObjectHolder("malum:basic_mirror_tile_entity")
    public static TileEntityType<BasicMirrorTileEntity> basic_mirror_tile_entity;

    @ObjectHolder("malum:input_mirror_tile_entity")
    public static TileEntityType<InputMirrorTileEntity> input_mirror_tile_entity;

    @ObjectHolder("malum:output_mirror_tile_entity")
    public static TileEntityType<OutputMirrorTileEntity> output_mirror_tile_entity;
    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> e)
    {
        e.getRegistry().registerAll(
            TileEntityType.Builder.create((Supplier<TileEntity>) SpiritFurnaceBottomTileEntity::new, ModBlocks.spirit_furnace).build(null).setRegistryName("spirit_furnace_bottom_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) SpiritFurnaceTopTileEntity::new, ModBlocks.spirit_furnace_top).build(null).setRegistryName("spirit_furnace_top_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) SpiritJarTileEntity::new, ModBlocks.spirit_jar).build(null).setRegistryName("spirit_jar_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) BasicMirrorTileEntity::new, ModBlocks.basic_mirror).build(null).setRegistryName("basic_mirror_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) InputMirrorTileEntity::new, ModBlocks.input_mirror).build(null).setRegistryName("input_mirror_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) OutputMirrorTileEntity::new, ModBlocks.output_mirror).build(null).setRegistryName("output_mirror_tile_entity")
        );
    }
}
