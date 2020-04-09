package com.kittykitcatcat.malum.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static com.kittykitcatcat.malum.SpiritData.makeDefaultTooltip;

public class JeiSpiritItem extends Item
{
    public JeiSpiritItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        makeDefaultTooltip(stack,worldIn,tooltip,flagIn);
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}

