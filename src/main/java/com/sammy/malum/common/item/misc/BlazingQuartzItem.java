package com.sammy.malum.common.item.misc;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.item.LodestoneFuelBlockItem;


public class BlazingQuartzItem extends LodestoneFuelBlockItem {
    public final int fuel;

    public BlazingQuartzItem(Block pBlock, int fuel, Properties pProperties) {
        super(pBlock, pProperties, fuel);
        this.fuel = fuel;
    }
}
