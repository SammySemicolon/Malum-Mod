package com.sammy.malum.core.setup.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.item.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumSplinterTab extends CreativeModeTab
{
    public static final MalumSplinterTab INSTANCE = new MalumSplinterTab();
    
    public MalumSplinterTab() {
        super(MalumMod.MODID + "_spirits");
    }
    
    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.ARCANE_SPIRIT.get());
    }
}