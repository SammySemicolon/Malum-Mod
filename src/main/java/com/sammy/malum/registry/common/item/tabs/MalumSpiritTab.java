package com.sammy.malum.registry.common.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumSpiritTab extends CreativeModeTab
{
    public static final MalumSpiritTab INSTANCE = new MalumSpiritTab();
    
    public MalumSpiritTab() {
        super(MalumMod.MALUM + "_spirits");
    }
    
    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.ARCANE_SPIRIT.get());
    }
}