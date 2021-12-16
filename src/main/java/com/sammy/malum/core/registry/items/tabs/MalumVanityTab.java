package com.sammy.malum.core.registry.items.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.registry.items.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumVanityTab extends ItemGroup
{
    public static final MalumVanityTab INSTANCE = new MalumVanityTab();

    public MalumVanityTab() {
        super(MalumMod.MODID);
    }
    
    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.FANCY_TOPHAT.get());
    }
}
