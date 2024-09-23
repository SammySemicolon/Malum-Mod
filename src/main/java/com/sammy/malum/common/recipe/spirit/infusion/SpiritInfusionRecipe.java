package com.sammy.malum.common.recipe.spirit.infusion;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.*;

public class SpiritInfusionRecipe extends LodestoneInWorldRecipe<SpiritBasedRecipeInput>  {
    public static final ResourceLocation NAME = MalumMod.malumPath("spirit_infusion");

    public final SizedIngredient ingredient;
    public final ItemStack output;

    public final List<SizedIngredient> extraIngredients;
    public final List<SpiritIngredient> spirits;
    public final boolean carryOverData;

    public SpiritInfusionRecipe(SizedIngredient ingredient, ItemStack output, List<SizedIngredient> extraIngredients, List<SpiritIngredient> spirits, boolean carryOverData) {
        super(RecipeSerializerRegistry.INFUSION_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.SPIRIT_INFUSION.get());
        this.ingredient = ingredient;
        this.output = output;
        this.extraIngredients = extraIngredients;
        this.spirits = spirits;
        this.carryOverData = carryOverData;
    }

    @Override
    public boolean matches(SpiritBasedRecipeInput input, Level level) {
        return input.test(ingredient, extraIngredients, spirits);
    }

    public static class Serializer implements RecipeSerializer<SpiritInfusionRecipe> {
        public static final MapCodec<SpiritInfusionRecipe> CODEC = RecordCodecBuilder.mapCodec((obj) -> obj.group(
                SizedIngredient.FLAT_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
                ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
                SizedIngredient.FLAT_CODEC.listOf().fieldOf("extraIngredients").forGetter(recipe -> recipe.extraIngredients),
                SpiritIngredient.CODEC.listOf().fieldOf("spirits").forGetter(recipe -> recipe.spirits),
                Codec.BOOL.fieldOf("carryOverComponentData").forGetter(recipe -> recipe.carryOverData)
        ).apply(obj, SpiritInfusionRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SpiritInfusionRecipe> STREAM_CODEC =
                StreamCodec.of(SpiritInfusionRecipe.Serializer::toNetwork, SpiritInfusionRecipe.Serializer::fromNetwork);

        @Override
        public MapCodec<SpiritInfusionRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SpiritInfusionRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static SpiritInfusionRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            var ingredient = SizedIngredient.STREAM_CODEC.decode(buffer);
            var output = ItemStack.STREAM_CODEC.decode(buffer);

            int extraIngredientCount = buffer.readInt();
            ArrayList<SizedIngredient> extraIngredients = new ArrayList<>();
            for (int i = 0; i < extraIngredientCount; i++) {
                extraIngredients.add(SizedIngredient.STREAM_CODEC.decode(buffer));
            }

            int spiritCount = buffer.readInt();
            ArrayList<SpiritIngredient> spirits = new ArrayList<>();
            for (int i = 0; i < spiritCount; i++) {
                var spirit = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
                if (spirit.getCustomIngredient() instanceof SpiritIngredient spiritIngredient) {
                    spirits.add(spiritIngredient);
                }
            }
            boolean carryOverComponentData = buffer.readBoolean();
            return new SpiritInfusionRecipe(ingredient, output, extraIngredients, spirits, carryOverComponentData);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, SpiritInfusionRecipe recipe) {
            SizedIngredient.STREAM_CODEC.encode(buffer, recipe.ingredient);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            buffer.writeInt(recipe.extraIngredients.size());
            for (SizedIngredient extraIngredient : recipe.extraIngredients) {
                SizedIngredient.STREAM_CODEC.encode(buffer, extraIngredient);
            }
            buffer.writeInt(recipe.spirits.size());
            for (SpiritIngredient spirit : recipe.spirits) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, spirit.toVanilla());
            }
        }
    }
}
