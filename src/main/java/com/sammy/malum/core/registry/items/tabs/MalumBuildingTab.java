package com.sammy.malum.core.registry.items.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.registry.items.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class MalumBuildingTab extends ItemGroup
{
    public static final MalumBuildingTab INSTANCE = new MalumBuildingTab();
    
    public MalumBuildingTab() {
        super(MalumMod.MODID + "_shaped_stones");
    }
    
    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemRegistry.TAINTED_ROCK.get());
    }
}
