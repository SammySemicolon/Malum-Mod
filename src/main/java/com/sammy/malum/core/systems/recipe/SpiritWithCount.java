package com.sammy.malum.core.systems.recipe;

import com.google.gson.JsonObject;
import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.ortus.systems.recipe.IRecipeComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class SpiritWithCount implements IRecipeComponent {
    public final MalumSpiritType type;
    public final int count;

    public SpiritWithCount(ItemStack stack) {
        this.type = ((MalumSpiritItem)stack.getItem()).type;
        this.count = stack.getCount();
    }

    public SpiritWithCount(MalumSpiritType type, int count) {
        this.type = type;
        this.count = count;
    }

    public static SpiritWithCount deserialize(JsonObject inputObject) {
        MalumSpiritType type = SpiritHelper.getSpiritType(inputObject.get("type").getAsString());
        int count = 1;
        if (inputObject.has("count")) {
            count = inputObject.get("count").getAsInt();
        }
        return new SpiritWithCount(type, count);
    }

    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        object.addProperty("type", type.identifier);
        if (getCount() > 1) {
            object.addProperty("count", getCount());
        }
        return object;
    }
    public Component getComponent() {
        return type.getComponent(count);
    }

    public CompoundTag save(CompoundTag tag) {
        tag.putString("type", type.identifier);
        tag.putInt("count", count);
        return tag;
    }

    public static SpiritWithCount load(CompoundTag tag) {
        MalumSpiritType type = SpiritHelper.getSpiritType(tag.getString("type"));
        int count = tag.getInt("count");
        return new SpiritWithCount(type, count);
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(getItem(), getCount());
    }

    @Override
    public ArrayList<ItemStack> getStacks() {
        return new ArrayList<>(List.of(getStack()));
    }

    @Override
    public Item getItem() {
        return type.getSplinterItem();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return stack.getItem().equals(getItem()) && stack.getCount() >= getCount();
    }
}