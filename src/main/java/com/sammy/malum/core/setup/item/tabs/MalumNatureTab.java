package com.sammy.malum.core.setup.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.item.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumNatureTab extends CreativeModeTab
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
