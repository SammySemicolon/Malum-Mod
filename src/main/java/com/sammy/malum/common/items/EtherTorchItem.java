package com.sammy.malum.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.nbt.CompoundNBT;

public class EtherTorchItem extends WallOrFloorItem implements IDyeableArmorItem
{
    public EtherTorchItem(Block floorBlock, Block wallBlockIn, Properties propertiesIn)
    {
        super(floorBlock, wallBlockIn, propertiesIn);
    }
    @Override
    public int getColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 15712278;
    }
}
