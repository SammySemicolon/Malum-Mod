package com.kittykitcatcat.malum.recipes;

import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class SpiritFurnaceFuelData
{

    private final Item fuelItem;
    private final int fuelTime;

    public SpiritFurnaceFuelData(Item fuelItem, int fuelTime)
    {
        this.fuelItem = fuelItem;
        this.fuelTime = fuelTime;
    }
    
    public Item getFuelItem()
    {
        return fuelItem;
    }
    
    public int getFuelTime()
    {
        return fuelTime;
    }
    public static void initData()
    {
        ModRecipes.addSpiritFurnaceFuelData(new SpiritFurnaceFuelData(ModItems.spirit_charcoal, 1600));
        ModRecipes.addSpiritFurnaceFuelData(new SpiritFurnaceFuelData(ModItems.necrotic_catalyst, 400));
        ModRecipes.addSpiritFurnaceFuelData(new SpiritFurnaceFuelData(ModItems.umbral_steel_helm, 4000));
        ModRecipes.addSpiritFurnaceFuelData(new SpiritFurnaceFuelData(ModItems.vampire_necklace, 4000));
        ModRecipes.addSpiritFurnaceFuelData(new SpiritFurnaceFuelData(ModItems.funk_engine, 40000));
    }
}