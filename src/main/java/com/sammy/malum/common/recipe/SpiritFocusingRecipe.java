package com.sammy.malum.common.recipe;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.crafting.*;
import team.lodestar.lodestone.modules.toolkit.recipe.LodestoneInWorldRecipe;

import java.util.*;

public class SpiritFocusingRecipe extends LodestoneInWorldRecipe<SpiritBasedRecipeInput> {

    public static final MapCodec<SpiritFocusingRecipe> CODEC = RecordCodecBuilder.mapCodec((obj) -> obj.group(
            Ingredient.CODEC.fieldOf("input").forGetter((recipe) -> recipe.input),
            ItemStack.CODEC.fieldOf("result").forGetter((recipe) -> recipe.output),
            SpiritIngredient.CODEC.codec().listOf().fieldOf("spirits").forGetter((recipe) -> recipe.spirits),
            Codec.INT.fieldOf("time").forGetter((recipe) -> recipe.time),
            Codec.INT.fieldOf("durabilityCost").forGetter((recipe) -> recipe.durabilityCost)
    ).apply(obj, SpiritFocusingRecipe::new));

    public static final String NAME = "spirit_focusing";

    protected final Ingredient input;
    protected final ItemStack output;
    protected final List<SpiritIngredient> spirits;

    protected final int time;
    protected final int durabilityCost;

    public SpiritFocusingRecipe(Ingredient input, ItemStack output, List<SpiritIngredient> spirits, int time, int durabilityCost) {
        super(MalumRecipeSerializers.FOCUSING_RECIPE_SERIALIZER.get(), MalumRecipeTypes.SPIRIT_FOCUSING.get(), output);
        this.input = input;
        this.output = output;
        this.spirits = spirits;
        this.time = time;
        this.durabilityCost = durabilityCost;
    }

    @Override
    public boolean matches(SpiritBasedRecipeInput input, Level level) {
        return input.test(new SizedIngredient(this.input, 1), spirits);
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutputRaw() {
        return output;
    }

    public ItemStack createOutput() {
        return output.copy();
    }

    public List<SpiritIngredient> getSpirits() {
        return spirits;
    }

    public int getTime() {
        return time;
    }

    public int getDurabilityCost() {
        return durabilityCost;
    }
}
