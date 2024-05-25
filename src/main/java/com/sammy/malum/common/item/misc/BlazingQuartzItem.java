package com.sammy.malum.common.item.misc;

import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.item.LodestoneFuelBlockItem;


public class BlazingQuartzItem extends LodestoneFuelBlockItem {
    public final int fuel;
    public BlazingQuartzItem(Block pBlock, int fuel, Properties pProperties) {
        super(pBlock, pProperties, fuel);
        this.fuel = fuel;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return fuel;
    }
}
