package com.sammy.malum.common.item.impetus;

import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CrackedImpetusItem extends Item implements SpiritRepairRecipe.IRepairOutputOverride {
    public ImpetusItem impetus;
    public CrackedImpetusItem(Properties pProperties) {
        super(pProperties);
    }

    public CrackedImpetusItem setRepairedVariant(ImpetusItem cracked) {
        this.impetus = cracked;
        return this;
    }

    @Override
    public Item overrideRepairResult() {
        return impetus;
    }
}
