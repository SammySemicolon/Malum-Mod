package com.sammy.malum.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

public class RunicWorkbenchRecipe extends LodestoneInWorldRecipe<RunicWorkbenchRecipe.Input> {
    public static final String NAME = "runeworking";

    public final ItemStack primaryInput;
    public final ItemStack secondaryInput;
    public final ItemStack output;

    public RunicWorkbenchRecipe(ItemStack primaryInput, ItemStack secondaryInput, ItemStack output) {
        super(RecipeSerializerRegistry.RUNEWORKING_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.RUNEWORKING.get());
        this.primaryInput = primaryInput;
        this.secondaryInput = secondaryInput;
        this.output = output;
    }

    @Override
    public boolean matches(Input input, Level level) {
        return input.primaryInput.is(primaryInput.getItem())
                && input.primaryInput.getCount() >= primaryInput.getCount()
                && input.secondaryInput.is(secondaryInput.getItem())
                && input.secondaryInput.getCount() >= secondaryInput.getCount();
    }

    @Override
    public ItemStack assemble(Input input, HolderLookup.Provider registries) {
        return this.output.copy();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.output;
    }

    public record Input(ItemStack primaryInput, ItemStack secondaryInput) implements RecipeInput {

        @Override
        public ItemStack getItem(int index) {
            return switch (index) {
                case 0 -> this.primaryInput;
                case 1 -> this.secondaryInput;
                default -> throw new IllegalArgumentException("Recipe does not contain slot " + index);
            };
        }

        @Override
        public int size() { return 2; }
    }

    public static class Serializer implements RecipeSerializer<RunicWorkbenchRecipe> {

        public static final MapCodec<RunicWorkbenchRecipe> CODEC = RecordCodecBuilder.mapCodec(obj -> obj.group(
                ItemStack.CODEC.fieldOf("primaryInput").forGetter(recipe -> recipe.primaryInput),
                ItemStack.CODEC.fieldOf("secondaryInput").forGetter(recipe -> recipe.secondaryInput),
                ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
        ).apply(obj, RunicWorkbenchRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, RunicWorkbenchRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork, Serializer::fromNetwork
        );

        public static RunicWorkbenchRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            ItemStack primaryInput = ItemStack.STREAM_CODEC.decode(buffer);
            ItemStack secondaryInput = ItemStack.STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
            return new RunicWorkbenchRecipe(primaryInput, secondaryInput, output);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, RunicWorkbenchRecipe recipe) {
            ItemStack.STREAM_CODEC.encode(buffer, recipe.primaryInput);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.secondaryInput);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
        }

        @Override
        public MapCodec<RunicWorkbenchRecipe> codec() { return CODEC; }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RunicWorkbenchRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
