package com.sammy.malum.common.recipe.vanilla;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class NodeCookingSerializer<T extends AbstractCookingRecipe & INodeSmeltingRecipe> implements RecipeSerializer<T> {
    public final int defaultCookingTime;
    public final NodeBaker<T> factory;
    public final MapCodec<T> codec;
    public final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public NodeCookingSerializer(NodeBaker<T> pFactory, int defaultCookingTime) {
        this.defaultCookingTime = defaultCookingTime;
        this.factory = pFactory;
        this.codec = RecordCodecBuilder.mapCodec(obj -> obj.group(
                Codec.STRING.fieldOf("group").forGetter(AbstractCookingRecipe::getGroup),
                Ingredient.CODEC.fieldOf("ingredient").forGetter(INodeSmeltingRecipe::getIngredient),
                ItemStack.CODEC.fieldOf("output").forGetter(INodeSmeltingRecipe::getOutput),
                Codec.FLOAT.fieldOf("experience").forGetter(AbstractCookingRecipe::getExperience),
                Codec.INT.fieldOf("cookingTime").forGetter(AbstractCookingRecipe::getCookingTime)
        ).apply(obj, factory::create));
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    public T fromNetwork(RegistryFriendlyByteBuf buffer) {
        String group = buffer.readUtf();
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
        float xp = buffer.readFloat();
        int ctime = buffer.readInt();
        return this.factory.create(group, ingredient, result, xp, ctime);
    }


    public void toNetwork(RegistryFriendlyByteBuf buffer, T pRecipe) {
        buffer.writeUtf(pRecipe.getGroup());
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, pRecipe.getIngredient());
        ItemStack.STREAM_CODEC.encode(buffer, pRecipe.getOutput());
        buffer.writeFloat(pRecipe.getExperience());
        buffer.writeInt(pRecipe.getCookingTime());
    }

    @Override
    public MapCodec<T> codec() {
        return null;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return null;
    }

    public interface NodeBaker<T extends AbstractCookingRecipe> {
        T create(String pGroup, Ingredient pIngredient, ItemStack pResult, float pExperience, int pCookingTime);
    }
}