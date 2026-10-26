package com.sammy.malum.common.block.storage.coffer;

import net.minecraft.world.item.DyeColor;

public class DyedCofferBlock extends CofferBlock{

    public final DyeColor dyeColor;

    public DyedCofferBlock(Properties properties, DyeColor dyeColor) {
        super(properties);
        this.dyeColor = dyeColor;
    }
}
