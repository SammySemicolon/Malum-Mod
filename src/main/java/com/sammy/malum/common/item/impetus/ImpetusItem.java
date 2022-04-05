package com.sammy.malum.common.item.impetus;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ImpetusItem extends Item {
    public Supplier<CrackedImpetusItem> cracked;

    public ImpetusItem(Properties properties) {
        super(properties);
    }

    public ImpetusItem setCrackedVariant(Supplier<CrackedImpetusItem> cracked) {
        this.cracked = cracked;
        cracked.get().setRepairedVariant(this);
        return this;
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return false;
    }
}