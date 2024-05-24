package com.sammy.malum.data;

import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TagEmptyCondition implements ConditionJsonProvider {

    public static final ResourceLocation NAME = MalumMod.malumPath("tagEmpty");
    private final TagKey<Item> base;

    public TagEmptyCondition(TagKey<Item> base) {
        this.base = base;
    }

    @Override
    public ResourceLocation getConditionId() {
        return NAME;
    }

    public boolean test() {
        // get the base tag
        return false;
    }

    @Override
    public void writeParameters(JsonObject object) {
        object.addProperty("registry", BuiltInRegistries.ITEM.getDefaultKey().toString());
        object.addProperty("base", this.base.location().toString());
    }
}
