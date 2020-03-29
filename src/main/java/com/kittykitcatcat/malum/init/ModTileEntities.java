package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.blocks.ritualanchor.RitualAnchorTileEntity;
import com.kittykitcatcat.malum.blocks.souljar.SoulJarTileEntity;
import com.kittykitcatcat.malum.blocks.spiritbellows.SpiritBellowsTileEntity;
import com.kittykitcatcat.malum.blocks.spiritfurnace.SpiritFurnaceBottomTileEntity;
import com.kittykitcatcat.malum.blocks.spiritfurnace.SpiritFurnaceTopTileEntity;
import com.kittykitcatcat.malum.blocks.soulbinder.SoulBinderTileEntity;
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

    @ObjectHolder("malum:soul_binder_tile_entity")
    public static TileEntityType<SoulBinderTileEntity> soul_binder_tile_entity;

    @ObjectHolder("malum:ritual_anchor_tile_entity")
    public static TileEntityType<RitualAnchorTileEntity> ritual_anchor_tile_entity;

    @ObjectHolder("malum:soul_jar_tile_entity")
    public static TileEntityType<SoulJarTileEntity> soul_jar_tile_entity;
    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> e)
    {
        e.getRegistry().registerAll(
            TileEntityType.Builder.create((Supplier<TileEntity>) SpiritFurnaceBottomTileEntity::new, ModBlocks.spirit_furnace).build(null).setRegistryName("spirit_furnace_bottom_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) SpiritFurnaceTopTileEntity::new, ModBlocks.spirit_furnace_top).build(null).setRegistryName("spirit_furnace_top_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) SpiritBellowsTileEntity::new, ModBlocks.spirit_bellows).build(null).setRegistryName("spirit_bellows_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) SoulBinderTileEntity::new, ModBlocks.soul_binder).build(null).setRegistryName("soul_binder_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) RitualAnchorTileEntity::new, ModBlocks.ritual_anchor).build(null).setRegistryName("ritual_anchor_tile_entity"),
                TileEntityType.Builder.create((Supplier<TileEntity>) SoulJarTileEntity::new, ModBlocks.soul_jar).build(null).setRegistryName("soul_jar_tile_entity")
        );
    }
}
