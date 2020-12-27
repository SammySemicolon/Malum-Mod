package com.sammy.malum.core.systems;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumSpiritSplinters extends ItemGroup
{
    public static final MalumSpiritSplinters INSTANCE = new MalumSpiritSplinters();
    
    public MalumSpiritSplinters() {
        super(MalumMod.MODID);
    }
    
    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(MalumItems.SUN_KISSED_PANEL.get());
    }
}
