package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.blocks.spiritfurnace.SpiritFurnaceTileEntity;
import io.netty.util.internal.SuppressJava6Requirement;
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
    @ObjectHolder("malum:spirit_furnace_tile_entity")
    public static TileEntityType<SpiritFurnaceTileEntity> spirit_furnace_tile_entity;


    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> e)
    {
        e.getRegistry().registerAll(
            TileEntityType.Builder.create((Supplier<TileEntity>) SpiritFurnaceTileEntity::new, ModBlocks.spirit_furnace_bottom).build(null).setRegistryName("spirit_furnace_tile_entity")
        );
    }
}
