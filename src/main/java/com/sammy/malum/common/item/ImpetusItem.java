package com.sammy.malum.common.item;

import net.minecraft.tags.StaticTagHelper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

public class ImpetusItem extends Item {
    private final Tags.IOptionalNamedTag<Item> tag;

    public ImpetusItem(Properties properties) {
        super(properties);
        this.tag = null;
    }

    public ImpetusItem(Properties properties, Tags.IOptionalNamedTag<Item> tag) {
        super(properties);
        this.tag = tag;
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