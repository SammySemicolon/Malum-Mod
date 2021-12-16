package com.sammy.malum.core.registry.items.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.registry.items.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumSplinterTab extends ItemGroup
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