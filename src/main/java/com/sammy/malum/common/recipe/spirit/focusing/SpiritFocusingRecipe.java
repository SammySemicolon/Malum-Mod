package com.sammy.malum.common.recipe.spirit.focusing;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

import java.util.*;

public class SpiritFocusingRecipe extends LodestoneInWorldRecipe<SpiritFocusingRecipeInput> {

    public static final String NAME = "spirit_focusing";

    public final int time;
    public final int durabilityCost;

    public final Ingredient ingredient;
    public final ItemStack output;
    public final List<SpiritIngredient> spirits;

    public SpiritFocusingRecipe(int time, int durabilityCost, Ingredient ingredient, ItemStack output, List<SpiritIngredient> spirits) {
        super(RecipeSerializerRegistry.FOCUSING_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.SPIRIT_FOCUSING.get());
        this.time = time;
        this.durabilityCost = durabilityCost;
        this.ingredient = ingredient;
        this.output = output;
        this.spirits = spirits;
    }

    @Override
    public boolean matches(SpiritFocusingRecipeInput input, Level level) {
        return input.test(ingredient, spirits);
    }

    public static class Serializer implements RecipeSerializer<SpiritFocusingRecipe> {

        public static final MapCodec<SpiritFocusingRecipe> CODEC = RecordCodecBuilder.mapCodec((obj) -> obj.group(
                Codec.INT.fieldOf("time").forGetter((recipe) -> recipe.time),
                Codec.INT.fieldOf("durabilityCost").forGetter((recipe) -> recipe.durabilityCost),
                Ingredient.CODEC.fieldOf("ingredient").forGetter((recipe) -> recipe.ingredient),
                ItemStack.CODEC.fieldOf("output").forGetter((recipe) -> recipe.output),
                SpiritIngredient.CODEC.listOf().fieldOf("spirits").forGetter((recipe) -> recipe.spirits)
        ).apply(obj, SpiritFocusingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SpiritFocusingRecipe> STREAM_CODEC =
                StreamCodec.of(SpiritFocusingRecipe.Serializer::toNetwork, SpiritFocusingRecipe.Serializer::fromNetwork);

        @Override
        public MapCodec<SpiritFocusingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SpiritFocusingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static SpiritFocusingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int time = buffer.readInt();
            int durabilityCost = buffer.readInt();
            var ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            var output = ItemStack.STREAM_CODEC.decode(buffer);
            int spiritCount = buffer.readInt();
            ArrayList<SpiritIngredient> spirits = new ArrayList<>();
            for (int i = 0; i < spiritCount; i++) {
                var spirit = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
                if (spirit.getCustomIngredient() instanceof SpiritIngredient spiritIngredient) {
                    spirits.add(spiritIngredient);
                }
            }
            return new SpiritFocusingRecipe(time, durabilityCost, ingredient, output, spirits);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, SpiritFocusingRecipe recipe) {
            buffer.writeInt(recipe.time);
            buffer.writeInt(recipe.durabilityCost);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            buffer.writeInt(recipe.spirits.size());
            for (SpiritIngredient spirit : recipe.spirits) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, spirit.toVanilla());
            }
        }
    }
}
