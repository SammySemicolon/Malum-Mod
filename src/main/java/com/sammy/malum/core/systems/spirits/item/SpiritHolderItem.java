package com.sammy.malum.core.systems.spirits.item;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class SpiritHolderItem extends Item implements ISpiritHolderBlockItem
{
    private final int spiritSlots;
    private final int maxSpirits;
    public SpiritHolderItem(Properties properties, int spiritSlots, int maxSpirits)
    {
        super(properties);
        this.spiritSlots = spiritSlots;
        this.maxSpirits = maxSpirits;
    }
    
    @Override
    public int getSpiritSlots()
    {
        return spiritSlots;
    }
    
    @Override
    public int getMaxSpirits()
    {
        return maxSpirits;
    }
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        ArrayList<Pair<String, Integer>> itemSpirits = SpiritHelper.itemSpirits(stack, true);
        ClientHelper.makeTooltip(stack, worldIn, tooltip, flagIn,ClientHelper.stackSpiritsTooltip(stack, itemSpirits));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
    
}
