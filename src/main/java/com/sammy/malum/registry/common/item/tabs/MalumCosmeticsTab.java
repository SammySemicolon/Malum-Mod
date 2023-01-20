package com.sammy.malum.registry.common.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumCosmeticsTab extends CreativeModeTab
{
    public static final MalumCosmeticsTab INSTANCE = new MalumCosmeticsTab();

    public MalumCosmeticsTab() {
        super(MalumMod.MALUM + "_cosmetics");
    }
    
    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.GAY_PRIDEWEAVE.get());
    }
}
