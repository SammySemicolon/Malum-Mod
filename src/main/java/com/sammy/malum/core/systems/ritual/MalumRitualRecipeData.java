package com.sammy.malum.core.systems.ritual;

import team.lodestar.lodestone.systems.recipe.*;

import java.util.*;

public class MalumRitualRecipeData {

    public final MalumRitualType ritualType;
    public final IngredientWithCount input;
    public final List<IngredientWithCount> extraItems;

    public MalumRitualRecipeData(MalumRitualType ritualType, IngredientWithCount input, List<IngredientWithCount> extraItems) {
        this.ritualType = ritualType;
        this.input = input;
        this.extraItems = extraItems;
    }
}