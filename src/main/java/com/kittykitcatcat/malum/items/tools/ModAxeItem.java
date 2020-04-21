package com.kittykitcatcat.malum.items.tools;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;

public class ModAxeItem extends AxeItem
{
    public ModAxeItem(IItemTier material, int damage, float speed, Properties properties)
    {
        super(material, damage + 6, speed - 3.2f, properties.addToolType(ToolType.AXE, material.getHarvestLevel()));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        ModifierEventHandler.makeDefaultTooltip(stack,worldIn,tooltip,flagIn);
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}


