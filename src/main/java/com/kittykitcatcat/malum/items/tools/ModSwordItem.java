package com.kittykitcatcat.malum.items.tools;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;


public class ModSwordItem extends SwordItem
{
    public ModSwordItem(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage + 3, attackSpeed - 2.4f, properties);

    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        ModifierEventHandler.makeDefaultTooltip(stack,worldIn,tooltip,flagIn);
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}

