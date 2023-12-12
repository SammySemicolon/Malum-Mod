package com.sammy.malum.common.item;

import com.sammy.malum.core.systems.item.*;
import net.minecraft.world.item.*;

import static net.minecraft.world.item.Rarity.UNCOMMON;

public class ComplexVoidIngredientItem extends SimpleFoiledItem implements IVoidItem {
    public ComplexVoidIngredientItem(Properties builder) {
        super(builder.rarity(UNCOMMON));
    }

}