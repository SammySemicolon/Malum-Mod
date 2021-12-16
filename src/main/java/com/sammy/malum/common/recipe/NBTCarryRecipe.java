package com.sammy.malum.common.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.registry.content.RecipeSerializerRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.Map;
import java.util.Set;

public class NBTCarryRecipe extends CustomRecipe implements IShapedRecipe<CraftingContainer>
{
    public static final String NAME = "nbt_carry";
    public static class Type implements RecipeType<NBTCarryRecipe> {
        @Override
        public String toString () {
            return MalumMod.MODID + ":" + NAME;
        }

        public static final NBTCarryRecipe.Type INSTANCE = new NBTCarryRecipe.Type();
    }
    static int MAX_WIDTH = 3;
    static int MAX_HEIGHT = 3;

    public final int recipeWidth;
    public final int recipeHeight;
    public final Ingredient nbtCarry;
    public final NonNullList<Ingredient> recipeItems;
    public final ItemStack recipeOutput;
    public final String group;

    public NBTCarryRecipe(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, Ingredient nbtCarry, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn)
    {
        super(idIn);
        this.group = groupIn;
        this.recipeWidth = recipeWidthIn;
        this.recipeHeight = recipeHeightIn;
        this.nbtCarry = nbtCarry;
        this.recipeItems = recipeItemsIn;
        this.recipeOutput = recipeOutputIn;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return RecipeSerializerRegistry.NBT_CARRY_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public boolean matches(CraftingContainer inv, Level level)
    {
        for (int i = 0; i <= inv.getWidth() - this.recipeWidth; ++i)
        {
            for (int j = 0; j <= inv.getHeight() - this.recipeHeight; ++j)
            {
                if (this.checkMatch(inv, i, j, true))
                {
                    return true;
                }

                if (this.checkMatch(inv, i, j, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkMatch(CraftingContainer craftingInventory, int width, int height, boolean p_77573_4_)
    {
        for (int i = 0; i < craftingInventory.getWidth(); ++i)
        {
            for (int j = 0; j < craftingInventory.getHeight(); ++j)
            {
                int k = i - width;
                int l = j - height;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight)
                {
                    if (p_77573_4_)
                    {
                        ingredient = this.recipeItems.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
                    }
                    else
                    {
                        ingredient = this.recipeItems.get(k + l * this.recipeWidth);
                    }
                }

                if (!ingredient.test(craftingInventory.getItem(i + j * craftingInventory.getWidth())))
                {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv)
    {
        ItemStack stack = recipeOutput.copy();
        for (int i = 0; i < inv.getContainerSize(); i++)
        {
            ItemStack oldStack = inv.getItem(i);
            if (nbtCarry.test(oldStack))
            {
                if (oldStack.hasTag())
                {
                    stack.setTag(oldStack.getTag());
                }
            }
        }
        return stack;
    }
    @Override
    public boolean isSpecial()
    {
        return false;
    }
    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getResultItem() {
        return this.recipeOutput;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.recipeWidth && height >= this.recipeHeight;
    }

    @Override
    public int getRecipeWidth() {
        return this.recipeWidth;
    }

    @Override
    public int getRecipeHeight() {
        return this.recipeHeight;
    }

    private static NonNullList<Ingredient> deserializeIngredients(String[] pattern, Map<String, Ingredient> keys, int patternWidth, int patternHeight)
    {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(patternWidth * patternHeight, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(keys.keySet());
        set.remove(" ");

        for (int i = 0; i < pattern.length; ++i)
        {
            for (int j = 0; j < pattern[i].length(); ++j)
            {
                String s = pattern[i].substring(j, j + 1);
                Ingredient ingredient = keys.get(s);
                if (ingredient == null)
                {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                nonnulllist.set(j + patternWidth * i, ingredient);
            }
        }

        if (!set.isEmpty())
        {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        }
        else
        {
            return nonnulllist;
        }
    }

    @VisibleForTesting
    static String[] shrink(String... toShrink)
    {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for (int i1 = 0; i1 < toShrink.length; ++i1)
        {
            String s = toShrink[i1];
            i = Math.min(i, firstNonSpace(s));
            int j1 = lastNonSpace(s);
            j = Math.max(j, j1);
            if (j1 < 0)
            {
                if (k == i1)
                {
                    ++k;
                }

                ++l;
            }
            else
            {
                l = 0;
            }
        }

        if (toShrink.length == l)
        {
            return new String[0];
        }
        else
        {
            String[] astring = new String[toShrink.length - l - k];

            for (int k1 = 0; k1 < astring.length; ++k1)
            {
                astring[k1] = toShrink[k1 + k].substring(i, j + 1);
            }

            return astring;
        }
    }

    private static int firstNonSpace(String str)
    {
        int i;
        for (i = 0; i < str.length() && str.charAt(i) == ' '; ++i)
        {
        }

        return i;
    }

    private static int lastNonSpace(String str)
    {
        int i;
        for (i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i)
        {
        }

        return i;
    }

    private static String[] patternFromJson(JsonArray jsonArr)
    {
        String[] astring = new String[jsonArr.size()];
        if (astring.length > MAX_HEIGHT)
        {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
        }
        else if (astring.length == 0)
        {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        }
        else
        {
            for (int i = 0; i < astring.length; ++i)
            {
                String s = GsonHelper.convertToString(jsonArr.get(i), "pattern[" + i + "]");
                if (s.length() > MAX_WIDTH)
                {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
                }

                if (i > 0 && astring[0].length() != s.length())
                {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                astring[i] = s;
            }

            return astring;
        }
    }

    /**
     * Returns a key json object as a Java HashMap.
     */
    private static Map<String, Ingredient> deserializeKey(JsonObject json)
    {
        Map<String, Ingredient> map = Maps.newHashMap();

        for (Map.Entry<String, JsonElement> entry : json.entrySet())
        {
            if (entry.getKey().length() != 1)
            {
                throw new JsonSyntaxException("Invalid key entry: '" + (String) entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey()))
            {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static ItemStack deserializeItem(JsonObject object)
    {
        String s = GsonHelper.getAsString(object, "item");
        Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + s + "'"));
        if (object.has("data"))
        {
            throw new JsonParseException("Disallowed data tag found");
        }
        else
        {
            int i = GsonHelper.getAsInt(object, "count", 1);
            return net.minecraftforge.common.crafting.CraftingHelper.getItemStack(object, true);
        }
    }


    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<NBTCarryRecipe>
    {
        public NBTCarryRecipe fromJson(ResourceLocation recipeId, JsonObject json)
        {
            String s = GsonHelper.getAsString(json, "group", "");
            Map<String, Ingredient> map = deserializeKey(GsonHelper.getAsJsonObject(json, "key"));
            String[] astring = shrink(patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
            int i = astring[0].length();
            int j = astring.length;
            NonNullList<Ingredient> nonnulllist = deserializeIngredients(astring, map, i, j);
            Ingredient nbtCarry = Ingredient.fromJson(json.get("nbtCarry"));
            ItemStack itemstack = deserializeItem(GsonHelper.getAsJsonObject(json, "result"));

            return new NBTCarryRecipe(recipeId, s, i, j, nbtCarry, nonnulllist, itemstack);
        }

        public NBTCarryRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
        {
            int i = buffer.readVarInt();
            int j = buffer.readVarInt();
            String s = buffer.readUtf(32767);
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for (int k = 0; k < nonnulllist.size(); ++k)
            {
                nonnulllist.set(k, Ingredient.fromNetwork(buffer));
            }

            Ingredient nbtCarry = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            return new NBTCarryRecipe(recipeId, s, i, j, nbtCarry, nonnulllist, itemstack);
        }

        public void toNetwork(FriendlyByteBuf buffer, NBTCarryRecipe recipe)
        {
            buffer.writeVarInt(recipe.recipeWidth);
            buffer.writeVarInt(recipe.recipeHeight);
            buffer.writeUtf(recipe.group);

            for (Ingredient ingredient : recipe.recipeItems)
            {
                ingredient.toNetwork(buffer);
            }
            recipe.nbtCarry.toNetwork(buffer);
            buffer.writeItem(recipe.recipeOutput);
        }
    }
}