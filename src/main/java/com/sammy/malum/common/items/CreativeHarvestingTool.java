package com.sammy.malum.common.items;

import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.core.systems.essences.EssenceHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class CreativeHarvestingTool extends Item
{
    public CreativeHarvestingTool(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        EssenceHelper.giveItemEssences(target, stack);
        return super.hitEntity(stack, target, attacker);
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        ClientHelper.makeTooltip(stack, worldIn, tooltip, flagIn,ClientHelper.itemEssences(stack));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
