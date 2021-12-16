package com.sammy.malum.core.registry.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.registry.item.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumBuildingTab extends CreativeModeTab
{
    public static final MalumBuildingTab INSTANCE = new MalumBuildingTab();
    
    public MalumBuildingTab() {
        super(MalumMod.MODID + "_shaped_stones");
    }
    
    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.TAINTED_ROCK.get());
    }
}
