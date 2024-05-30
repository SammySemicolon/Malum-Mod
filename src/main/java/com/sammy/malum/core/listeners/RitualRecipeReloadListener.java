package com.sammy.malum.core.listeners;

import com.google.gson.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.ritual.MalumRitualRecipeData;
import com.sammy.malum.core.systems.ritual.MalumRitualType;
import com.sammy.malum.registry.common.RitualRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RitualRecipeReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = (new GsonBuilder()).create();

    public RitualRecipeReloadListener() {
        super(GSON, "ritual_recipes");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        for (int i = 0; i < objectIn.size(); i++) {
            ResourceLocation location = (ResourceLocation) objectIn.keySet().toArray()[i];
            JsonObject object = objectIn.get(location).getAsJsonObject();
            String name = object.getAsJsonPrimitive("ritual_identifier").getAsString();
            ResourceLocation resourceLocation = new ResourceLocation(name);
            MalumRitualType ritualType = RitualRegistry.get(resourceLocation);
            if (ritualType == null) {
                MalumMod.LOGGER.info("Ritual with registry name: " + name + " does not exist");
                continue;
            }
            if (ritualType.getRecipeData() != null) {
                MalumMod.LOGGER.info("Ritual with registry name: " + name + " already has a recipe. Overwriting");
            }
            IngredientWithCount input = IngredientWithCount.deserialize(object.getAsJsonObject("input"));
            JsonArray extraItemsArray = object.getAsJsonArray("extra_items");
            List<IngredientWithCount> extraItems = new ArrayList<>();
            for (int j = 0; j < extraItemsArray.size(); j++) {
                JsonObject extraItemObject = extraItemsArray.get(j).getAsJsonObject();
                extraItems.add(IngredientWithCount.deserialize(extraItemObject));
            }
            ritualType.setRecipeData(new MalumRitualRecipeData(ritualType, input, extraItems));
        }
    }
}
