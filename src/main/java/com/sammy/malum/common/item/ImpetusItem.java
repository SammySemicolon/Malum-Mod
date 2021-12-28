package com.sammy.malum.common.item;

import net.minecraft.tags.StaticTagHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

public class ImpetusItem extends Item {
    private final Tags.IOptionalNamedTag<Item> tag;
    public Supplier<Item> cracked;
    public ImpetusItem(Properties properties) {
        super(properties);
        this.tag = null;
    }

    public ImpetusItem(Properties properties, Tags.IOptionalNamedTag<Item> tag) {
        super(properties);
        this.tag = tag;
    }
    public ImpetusItem setCrackedVariant(Supplier<Item> cracked)
    {
        this.cracked = cracked;
        return this;
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return false;
    }

    @Override
    protected boolean allowdedIn(CreativeModeTab tab) {
        if (tag instanceof StaticTagHelper.Wrapper wrapper) {
            if (wrapper.tag != null && wrapper.tag.getValues().isEmpty()) {
                return false;
            }
        }
        return super.allowdedIn(tab);
    }
}