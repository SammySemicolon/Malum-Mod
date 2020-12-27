package com.sammy.malum.core.systems.spirits.item;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class SpiritHolderBlockItem extends BlockItem implements ISpiritHolderBlockItem
{
    private final int maxSpirits;
    public SpiritHolderBlockItem(Block blockIn, Properties builder, int maxSpirits)
    {
        super(blockIn, builder);
        this.maxSpirits = maxSpirits;
    }
    @Override
    public int getSpiritSlots()
    {
        return 1;
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
