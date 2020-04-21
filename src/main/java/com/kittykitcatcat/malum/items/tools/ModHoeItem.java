package com.kittykitcatcat.malum.items.tools;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ModHoeItem extends HoeItem
{
    public ModHoeItem(IItemTier material, float speed, Properties properties)
    {
        super(material, speed - 3, properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        ModifierEventHandler.makeDefaultTooltip(stack,worldIn,tooltip,flagIn);
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}

