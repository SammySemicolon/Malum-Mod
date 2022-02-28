package com.sammy.malum.core.setup.content.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumVanityTab extends CreativeModeTab
{
    public static final MalumVanityTab INSTANCE = new MalumVanityTab();

    public MalumVanityTab() {
        super(MalumMod.MODID + "_vanity");
    }
    
    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.FANCY_TOPHAT.get());
    }
}
