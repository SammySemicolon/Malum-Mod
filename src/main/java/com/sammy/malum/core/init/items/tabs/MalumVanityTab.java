package com.sammy.malum.core.init.items.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.items.MalumItems;
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
    public ItemStack createIcon() {
        return new ItemStack(MalumItems.SPIRIT_ALTAR.get());
    }
}
