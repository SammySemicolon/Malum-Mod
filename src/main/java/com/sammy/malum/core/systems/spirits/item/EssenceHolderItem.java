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

public class EssenceHolderItem extends Item implements IEssenceHolder
{
    public int essenceSlots;
    public int maxEssence;
    public EssenceHolderItem(Properties properties, int essenceSlots, int maxEssence)
    {
        super(properties);
        this.essenceSlots = essenceSlots;
        this.maxEssence = maxEssence;
    }
    
    @Override
    public int getEssenceSlots()
    {
        return essenceSlots;
    }
    
    @Override
    public int getMaxEssence()
    {
        return maxEssence;
    }
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        ArrayList<Pair<String, Integer>> itemSpirits = SpiritHelper.itemSpirits(stack, true);
        ClientHelper.makeTooltip(stack, worldIn, tooltip, flagIn,ClientHelper.stackSpiritsTooltip(stack, itemSpirits));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
    
}
