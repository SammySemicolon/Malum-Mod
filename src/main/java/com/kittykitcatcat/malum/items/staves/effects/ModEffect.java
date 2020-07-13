package com.kittykitcatcat.malum.items.staves.effects;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

@Nullable
public abstract class ModEffect
{
    public void effect(PlayerEntity playerEntity, ItemStack stack)
    {

    }
    public boolean requirement(PlayerEntity playerEntity, ItemStack stack)
    {
        return false;
    }
    public int cooldown()
    {
        return 10;
    }
}