package com.sammy.malum.core.init.blocks;

import com.sammy.malum.common.blocks.itempedestal.ItemPedestalTileEntity;
import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;
import com.sammy.malum.common.blocks.lighting.BasicLightingTileEntity;
import com.sammy.malum.common.blocks.spiritaltar.SpiritAltarTileEntity;
import com.sammy.malum.common.blocks.spiritjar.SpiritJarTileEntity;
import com.sammy.malum.common.blocks.spiritpipe.SpiritPipeTileEntity;
import com.sammy.malum.common.blocks.totem.TotemBaseTileEntity;
import com.sammy.malum.common.blocks.totem.pole.TotemPoleTileEntity;
import com.sammy.malum.common.blocks.wellofsuffering.WellOfSufferingTileEntity;
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

    public static final RegistryObject<TileEntityType<SpiritAltarTileEntity>> SPIRIT_ALTAR_TILE_ENTITY = TILE_ENTITIES.register("spirit_altar_tile_entity", () -> TileEntityType.Builder.create(SpiritAltarTileEntity::new, MalumBlocks.SPIRIT_ALTAR.get()).build(null));
    public static final RegistryObject<TileEntityType<WellOfSufferingTileEntity>> WELL_OF_SUFFERING_TILE_ENTITY = TILE_ENTITIES.register("well_of_suffering_tile_entity", () -> TileEntityType.Builder.create(WellOfSufferingTileEntity::new, MalumBlocks.WELL_OF_SUFFERING.get()).build(null));

    public static final RegistryObject<TileEntityType<SpiritJarTileEntity>> SPIRIT_JAR_TILE_ENTITY = TILE_ENTITIES.register("spirit_jar_tile_entity", () -> TileEntityType.Builder.create(SpiritJarTileEntity::new, MalumBlocks.SPIRIT_JAR.get()).build(null));
    public static final RegistryObject<TileEntityType<SpiritPipeTileEntity>> SPIRIT_PIPE_TILE_ENTITY = TILE_ENTITIES.register("spirit_pipe_tile_entity", () -> TileEntityType.Builder.create(SpiritPipeTileEntity::new, MalumBlocks.SPIRIT_PIPE.get()).build(null));

    public static final RegistryObject<TileEntityType<?>> ETHER_BLOCK_TILE_ENTITY = TILE_ENTITIES.register("ether_tile_entity", () -> TileEntityType.Builder.create((Supplier<TileEntity>) BasicLightingTileEntity::new, MalumBlocks.PINK_ETHER_TORCH.get(), MalumBlocks.PINK_WALL_ETHER_TORCH.get(), MalumBlocks.PINK_ETHER.get(), MalumBlocks.PINK_ETHER_BRAZIER.get(), MalumBlocks.ORANGE_ETHER_BRAZIER.get(), MalumBlocks.MAGENTA_ETHER_BRAZIER.get(), MalumBlocks.LIGHT_BLUE_ETHER_BRAZIER.get(), MalumBlocks.YELLOW_ETHER_BRAZIER.get(), MalumBlocks.LIME_ETHER_BRAZIER.get(), MalumBlocks.CYAN_ETHER_BRAZIER.get(), MalumBlocks.PURPLE_ETHER_BRAZIER.get(), MalumBlocks.BLUE_ETHER_BRAZIER.get(), MalumBlocks.BROWN_ETHER_BRAZIER.get(), MalumBlocks.GREEN_ETHER_BRAZIER.get(), MalumBlocks.RED_ETHER_BRAZIER.get(),MalumBlocks.ORANGE_ETHER_TORCH.get(), MalumBlocks.MAGENTA_ETHER_TORCH.get(), MalumBlocks.LIGHT_BLUE_ETHER_TORCH.get(), MalumBlocks.YELLOW_ETHER_TORCH.get(), MalumBlocks.LIME_ETHER_TORCH.get(), MalumBlocks.CYAN_ETHER_TORCH.get(), MalumBlocks.PURPLE_ETHER_TORCH.get(), MalumBlocks.BLUE_ETHER_TORCH.get(), MalumBlocks.BROWN_ETHER_TORCH.get(), MalumBlocks.GREEN_ETHER_TORCH.get(), MalumBlocks.RED_ETHER_TORCH.get(), MalumBlocks.ORANGE_WALL_ETHER_TORCH.get(), MalumBlocks.MAGENTA_WALL_ETHER_TORCH.get(), MalumBlocks.LIGHT_BLUE_WALL_ETHER_TORCH.get(), MalumBlocks.YELLOW_WALL_ETHER_TORCH.get(), MalumBlocks.LIME_WALL_ETHER_TORCH.get(), MalumBlocks.CYAN_WALL_ETHER_TORCH.get(), MalumBlocks.PURPLE_WALL_ETHER_TORCH.get(), MalumBlocks.BLUE_WALL_ETHER_TORCH.get(), MalumBlocks.BROWN_WALL_ETHER_TORCH.get(), MalumBlocks.GREEN_WALL_ETHER_TORCH.get(), MalumBlocks.RED_WALL_ETHER_TORCH.get(), MalumBlocks.ORANGE_ETHER.get(), MalumBlocks.MAGENTA_ETHER.get(), MalumBlocks.LIGHT_BLUE_ETHER.get(), MalumBlocks.YELLOW_ETHER.get(), MalumBlocks.LIME_ETHER.get(), MalumBlocks.CYAN_ETHER.get(), MalumBlocks.PURPLE_ETHER.get(), MalumBlocks.BLUE_ETHER.get(), MalumBlocks.BROWN_ETHER.get(), MalumBlocks.GREEN_ETHER.get(), MalumBlocks.RED_ETHER.get()).build(null));

    public static final RegistryObject<TileEntityType<ItemStandTileEntity>> ITEM_STAND_TILE_ENTITY = TILE_ENTITIES.register("item_stand_tile_entity", () -> TileEntityType.Builder.create(ItemStandTileEntity::new, MalumBlocks.RUNEWOOD_ITEM_STAND.get(),  MalumBlocks.TAINTED_ROCK_ITEM_STAND.get(), MalumBlocks.TWISTED_ROCK_ITEM_STAND.get(), MalumBlocks.PURIFIED_ROCK_ITEM_STAND.get(), MalumBlocks.CLEANSED_ROCK_ITEM_STAND.get(), MalumBlocks.ERODED_ROCK_ITEM_STAND.get()).build(null));
    public static final RegistryObject<TileEntityType<ItemPedestalTileEntity>> ITEM_PEDESTAL_TILE_ENTITY = TILE_ENTITIES.register("item_pedestal_tile_entity", () -> TileEntityType.Builder.create(ItemPedestalTileEntity::new, MalumBlocks.RUNEWOOD_ITEM_PEDESTAL.get(), MalumBlocks.TAINTED_ROCK_ITEM_PEDESTAL.get(), MalumBlocks.TWISTED_ROCK_ITEM_PEDESTAL.get(), MalumBlocks.PURIFIED_ROCK_ITEM_PEDESTAL.get(), MalumBlocks.CLEANSED_ROCK_ITEM_PEDESTAL.get(), MalumBlocks.ERODED_ROCK_ITEM_PEDESTAL.get()).build(null));

    public static final RegistryObject<TileEntityType<TotemBaseTileEntity>> TOTEM_BASE_TILE_ENTITY = TILE_ENTITIES.register("totem_base_tile_entity", () -> TileEntityType.Builder.create(TotemBaseTileEntity::new, MalumBlocks.TOTEM_BASE.get()).build(null));
    public static final RegistryObject<TileEntityType<TotemPoleTileEntity>> TOTEM_POLE_TILE_ENTITY = TILE_ENTITIES.register("totem_pole_tile_entity", () -> TileEntityType.Builder.create(TotemPoleTileEntity::new, MalumBlocks.TOTEM_POLE.get()).build(null));
}
