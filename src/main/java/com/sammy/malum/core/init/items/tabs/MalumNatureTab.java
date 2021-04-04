package com.sammy.malum.core.init.items.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumNatureTab extends ItemGroup
{
    public static final MalumNatureTab INSTANCE = new MalumNatureTab();

    public MalumNatureTab() {
        super(MalumMod.MODID + "_natural_wonders");
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(MalumItems.RUNEWOOD_SAPLING.get());
    }
}
