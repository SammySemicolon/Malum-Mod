package com.sammy.malum.common.recipe;

import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.registry.content.RecipeSerializerRegistry;
import com.sammy.malum.core.systems.recipe.IMalumRecipe;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;

public class BlockTransmutationRecipe extends IMalumRecipe
{
    public static final String NAME = "block_transmutation";
    public static class Type implements RecipeType<BlockTransmutationRecipe> {
        @Override
        public String toString () {
            return MalumMod.MODID + ":" + NAME;
        }

        public static final BlockTransmutationRecipe.Type INSTANCE = new BlockTransmutationRecipe.Type();
    }

    private final ResourceLocation id;

    public final Block input;
    public final Block output;

    public BlockTransmutationRecipe(ResourceLocation id, Block input, Block output)
    {
        this.id = id;
        this.input = input;
        this.output = output;
    }
    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return RecipeSerializerRegistry.BLOCK_TRANSMUTATION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return Type.INSTANCE;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    public boolean doesInputMatch(Block input)
    {
        return input.equals(this.input);
    }
    public boolean doesOutputMatch(Block output)
    {
        return output.equals(this.output);
    }

    public static List<BlockTransmutationRecipe> getRecipes(Level level)
    {
        return level.getRecipeManager().getAllRecipesFor(Type.INSTANCE);
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<BlockTransmutationRecipe> {

        @Override
        public BlockTransmutationRecipe fromJson(ResourceLocation recipeId, JsonObject json)
        {
            String input = json.getAsJsonPrimitive("input").getAsString();
            String output = json.getAsJsonPrimitive("output").getAsString();

            return new BlockTransmutationRecipe(recipeId, Registry.BLOCK.get(new ResourceLocation(input)), Registry.BLOCK.get(new ResourceLocation(output)));
        }

        @Nullable
        @Override
        public BlockTransmutationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
        {
            BlockState input = Block.stateById(buffer.readVarInt());
            BlockState output = Block.stateById(buffer.readVarInt());
            return new BlockTransmutationRecipe(recipeId, input.getBlock(), output.getBlock());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BlockTransmutationRecipe recipe)
        {
            buffer.writeVarInt(Block.getId(recipe.input.defaultBlockState()));
            buffer.writeVarInt(Block.getId(recipe.output.defaultBlockState()));
        }
    }
}
