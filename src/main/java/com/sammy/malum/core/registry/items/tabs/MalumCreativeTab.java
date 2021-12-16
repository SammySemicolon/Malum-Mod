package com.sammy.malum.core.registry.items.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.registry.items.ItemRegistry;
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
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.SPIRIT_ALTAR.get());
    }
}
