package com.sammy.malum.core.init;

import com.sammy.malum.core.MalumCreativeTab;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;

@SuppressWarnings("unused")
public class MalumItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    public static final RegistryObject<Item> SPIRIT_STONE = ITEMS.register("spirit_stone",
            () -> new BlockItem(MalumBlocks.SPIRIT_STONE.get(), new Item.Properties().group(MalumCreativeTab.INSTANCE))
    );
    public static final RegistryObject<Item> SPIRIT_STONE_SLAB = ITEMS.register("spirit_stone_slab",
            () -> new BlockItem(MalumBlocks.SPIRIT_STONE_SLAB.get(), new Item.Properties().group(MalumCreativeTab.INSTANCE))
    );
    public static final RegistryObject<Item> SPIRIT_STONE_STAIRS = ITEMS.register("spirit_stone_stairs",
            () -> new BlockItem(MalumBlocks.SPIRIT_STONE_STAIRS.get(), new Item.Properties().group(MalumCreativeTab.INSTANCE))
    );
}
