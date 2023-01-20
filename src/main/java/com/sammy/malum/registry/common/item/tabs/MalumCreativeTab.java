package com.sammy.malum.registry.common.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumCreativeTab extends CreativeModeTab
{
    public static final MalumCreativeTab INSTANCE = new MalumCreativeTab();
    
    public MalumCreativeTab() {
        super(MalumMod.MALUM);
    }
    
    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.SPIRIT_ALTAR.get());
    }


}
