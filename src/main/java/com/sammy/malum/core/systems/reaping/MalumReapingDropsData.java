package com.sammy.malum.core.systems.reaping;

import net.minecraft.world.item.crafting.Ingredient;

public class MalumReapingDropsData {

    public final Ingredient drop;
    public final float chance;
    public final int min;
    public final int max;

    public MalumReapingDropsData(Ingredient drop, float chance, int min, int max) {
        this.drop = drop;
        this.chance = chance;
        this.min = min;
        this.max = max;
    }
}