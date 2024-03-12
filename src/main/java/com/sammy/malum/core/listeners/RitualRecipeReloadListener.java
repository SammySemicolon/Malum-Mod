package com.sammy.malum.core.listeners;

import com.google.gson.*;
import com.sammy.malum.*;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.resources.*;
import net.minecraft.server.packs.resources.*;
import net.minecraft.util.profiling.*;
import net.minecraftforge.event.*;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.*;

public class RitualRecipeReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = (new GsonBuilder()).create();

    public RitualRecipeReloadListener() {
        super(GSON, "ritual_recipes");
    }

    public static void register(AddReloadListenerEvent event) {
        event.addListener(new RitualRecipeReloadListener());
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
