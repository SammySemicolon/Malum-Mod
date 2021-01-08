package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;

public class MalumSpiritKilnFuels
{
    public static final ArrayList<MalumSpiritKilnFuel> data = new ArrayList<>();
    
    public static void init()
    {
        new MalumSpiritKilnFuel(MalumItems.ARCANE_CHARCOAL, 16, 1);
        new MalumSpiritKilnFuel(MalumItems.ETHER, 32, 1);
    }
    public static MalumSpiritKilnFuel getData(ItemStack stack)
    {
        for (MalumSpiritKilnFuel data : data)
        {
            if (data.inputItem.equals(stack.getItem()))
            {
                return data;
            }
        }
        return null;
    }
    public static class MalumSpiritKilnFuel
    {
    
        public final Item inputItem;
        public final int fuelDuration;
        public final float fuelSpeed;
    
        public MalumSpiritKilnFuel(RegistryObject<Item> inputItem, int fuelDuration, float fuelSpeed) {
            this.inputItem = inputItem.get();
            this.fuelDuration = fuelDuration;
            this.fuelSpeed = fuelSpeed;
            data.add(this);
        }
    }
}
