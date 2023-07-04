package com.sammy.malum.registry.common.item.tabs;

import com.sammy.malum.*;
import net.minecraft.world.item.*;

import javax.annotation.*;
import java.util.function.*;

public class MalumTab extends CreativeModeTab
{

    private final Supplier<? extends Item> icon;

    public MalumTab(String name, Supplier<? extends Item> icon) {
        super(MalumMod.MALUM + "_" + name);
        this.icon = icon;
    }
    
    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(icon.get());
    }
}
