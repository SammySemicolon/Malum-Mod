package com.sammy.malum.core.registry.items.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.registry.items.ItemRegistry;
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
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.RUNEWOOD_SAPLING.get());
    }
}
