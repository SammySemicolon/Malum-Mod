package com.sammy.malum.core.setup.content.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumSplinterTab extends CreativeModeTab
{
    public static final MalumSplinterTab INSTANCE = new MalumSplinterTab();
    
    public MalumSplinterTab() {
        super(MalumMod.MALUM + "_spirits");
    }
    
    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.ARCANE_SPIRIT.get());
    }
}