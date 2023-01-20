package com.sammy.malum.registry.common.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumNatureTab extends CreativeModeTab
{
    public static final MalumNatureTab INSTANCE = new MalumNatureTab();

    public MalumNatureTab() {
        super(MalumMod.MALUM + "_natural_wonders");
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.RUNEWOOD_SAPLING.get());
    }
}
