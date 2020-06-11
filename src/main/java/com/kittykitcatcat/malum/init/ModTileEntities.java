package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.blocks.machines.spiritbellows.SpiritBellowsTileEntity;
import com.kittykitcatcat.malum.blocks.machines.spiritfurnace.SpiritFurnaceBottomTileEntity;
import com.kittykitcatcat.malum.blocks.machines.spiritfurnace.SpiritFurnaceTopTileEntity;
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
    @ObjectHolder("malum:spirit_furnace_bottom_tile_entity")
    public static TileEntityType<SpiritFurnaceBottomTileEntity> spirit_furnace_bottom_tile_entity;

    @ObjectHolder("malum:spirit_furnace_top_tile_entity")
    public static TileEntityType<SpiritFurnaceTopTileEntity> spirit_furnace_top_tile_entity;

    @ObjectHolder("malum:spirit_bellows_tile_entity")
    public static TileEntityType<SpiritBellowsTileEntity> spirit_bellows_tile_entity;

    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> e)
    {
        e.getRegistry().registerAll(
            TileEntityType.Builder.create((Supplier<TileEntity>) SpiritFurnaceBottomTileEntity::new, ModBlocks.spirit_furnace).build(null).setRegistryName("spirit_furnace_bottom_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) SpiritFurnaceTopTileEntity::new, ModBlocks.spirit_furnace_top).build(null).setRegistryName("spirit_furnace_top_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) SpiritBellowsTileEntity::new, ModBlocks.spirit_bellows).build(null).setRegistryName("spirit_bellows_tile_entity")
        );
    }
}
