package com.sammy.malum.core.init;

import com.sammy.malum.core.MalumCreativeTab;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import static com.sammy.malum.MalumMod.ITEMS;
import static com.sammy.malum.core.init.MalumBlocks.SEWING_STATION_BLOCK;

public class MalumItems
{
    public static final RegistryObject<Item> SEWING_STATION_ITEM = ITEMS.register("sewing_station",
            () -> new BlockItem(SEWING_STATION_BLOCK.get(), new Item.Properties().group(MalumCreativeTab.INSTANCE))
    );
}
