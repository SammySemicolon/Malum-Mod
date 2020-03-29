package com.kittykitcatcat.malum.recipes;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.item.Item;

import java.util.List;

public class RitualAnchorInput
{
    Item item1;
    Item item2;
    Item item3;
    Item item4;
    public RitualAnchorInput(Item item1, Item item2, Item item3, Item item4)
    {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
    }
    public static boolean isEqual(RitualAnchorInput inputA, RitualAnchorInput inputB)
    {
        return inputA.item1.equals(inputB.item1) && inputA.item2.equals(inputB.item2) && inputA.item3.equals(inputB.item3) && inputA.item4.equals(inputB.item4);
    }
    public static boolean isEqualList(List<RitualAnchorInput> listA, List<RitualAnchorInput> listB)
    {
        if (listA.size() != listB.size() && listA.size() != 4)
        {
            return false;
        }
        for (int i = 0; i < listB.size(); i++)
        {
            if (!isEqual(listA.get(i), listB.get(i)))
            {
                return false;
            }
        }
        return true;
    }
}