package com.sammy.malum.data;


import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import io.github.fabricators_of_create.porting_lib.util.TrueCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Predicate;

public class NotCondition implements ConditionJsonProvider {
    public static final ResourceLocation ID = MalumMod.malumPath("not");
    public static final NotCondition INSTANCE = new NotCondition();

    public static void init() {
        ResourceConditions.register(ID, jsonObject -> false);

    }

    @Override
    public ResourceLocation getConditionId() {
        return ID;
    }

    @Override
    public void writeParameters(JsonObject object) {
    }
}