package com.sammy.malum.core.init.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumCreativeTab extends ItemGroup
{
    public static final MalumCreativeTab INSTANCE = new MalumCreativeTab();
    
    public MalumCreativeTab() {
        super(MalumMod.MODID);
    }
    
    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(MalumItems.SOUL_GEM.get());
    }
}
