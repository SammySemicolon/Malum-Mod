package com.sammy.malum.core.setup.content.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumImpetusTab extends CreativeModeTab
{
    public static final MalumImpetusTab INSTANCE = new MalumImpetusTab();

    public MalumImpetusTab() {
        super(MalumMod.MODID + "_impetus");
    }
    
    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.ALCHEMICAL_IMPETUS.get());
    }
}
