package com.sammy.malum.core.recipes;

import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;

import static com.sammy.malum.core.recipes.TaintTransfusion.transfusions;

public class SpiritKilnFuelData
{
    public static final ArrayList<SpiritKilnFuelData> data = new ArrayList<>();
    public final Item inputItem;
    public final int fuelDuration;
    public final float fuelSpeed;
    
    public SpiritKilnFuelData(RegistryObject<Item> inputItem, int fuelDuration, float fuelSpeed) {
        this.inputItem = inputItem.get();
        this.fuelDuration = fuelDuration;
        this.fuelSpeed = fuelSpeed;
        data.add(this);
    }
    
    public static void init()
    {
        new SpiritKilnFuelData(MalumItems.ARCANE_CHARCOAL, 32, 1);
        new SpiritKilnFuelData(MalumItems.ARCANE_DISTILLATE, 64, 1);
    }
    public static SpiritKilnFuelData getData(ItemStack stack)
    {
        for (SpiritKilnFuelData data : data)
        {
            if (data.inputItem.equals(stack.getItem()))
            {
                return data;
            }
        }
        return null;
    }
}
