package com.sammy.malum.core.systems.spirits.item;

import com.sammy.malum.core.systems.spirits.types.MalumSpiritType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;

public interface ISpiritUsingItem
{
    public MalumSpiritType type();
    public int integrity();
    public IFormattableTextComponent textComponent(ItemStack stack, PlayerEntity playerEntity);
}
