package com.sammy.malum.registry.common.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumImpetusTab extends CreativeModeTab
{
    public static final MalumImpetusTab INSTANCE = new MalumImpetusTab();

    public MalumImpetusTab() {
        super(MalumMod.MALUM + "_impetus");
    }
    
    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.ALCHEMICAL_IMPETUS.get());
    }
}
