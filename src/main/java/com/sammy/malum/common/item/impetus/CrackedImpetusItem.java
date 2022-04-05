package com.sammy.malum.common.item.impetus;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CrackedImpetusItem extends Item {
    public ImpetusItem impetus;
    public CrackedImpetusItem(Properties pProperties) {
        super(pProperties);
    }

    public CrackedImpetusItem setRepairedVariant(ImpetusItem cracked) {
        this.impetus = cracked;
        return this;
    }
}
