package com.sammy.malum.core.listeners;

import com.google.gson.*;
import com.mojang.datafixers.util.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.*;
import net.minecraft.server.packs.resources.*;
import net.minecraft.util.profiling.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.*;

public class MalignantConversionReloadListener extends SimpleJsonResourceReloadListener {
    public static Map<ResourceLocation, MalignantConversionData> CONVERSION_DATA = new HashMap<>();
    private static final Gson GSON = (new GsonBuilder()).create();

    public MalignantConversionReloadListener() {
        super(GSON, "malignant_conversion_data");
    }

    public static void register(AddReloadListenerEvent event) {
        event.addListener(new MalignantConversionReloadListener());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        CONVERSION_DATA.clear();
        for (int i = 0; i < objectIn.size(); i++) {
            ResourceLocation location = (ResourceLocation) objectIn.keySet().toArray()[i];
            JsonObject object = objectIn.get(location).getAsJsonObject();
            String name = object.getAsJsonPrimitive("source_attribute").getAsString();
            ResourceLocation sourceAttribute = ResourceLocation.tryParse(name);
            if (sourceAttribute != null && !BuiltInRegistries.ATTRIBUTE.containsKey(sourceAttribute)) {
                continue;
            }
            double consumptionRatio = object.has("ratio") ? object.getAsJsonPrimitive("ratio").getAsDouble() : 1;
            JsonArray targetAttributes = object.getAsJsonArray("target_attributes");
            List<Pair<Attribute, Double>> attributeList = new ArrayList<>();
            for (JsonElement attribute : targetAttributes) {
                JsonObject attributeObject = attribute.getAsJsonObject();
                ResourceLocation attributeName = ResourceLocation.tryParse(attributeObject.getAsJsonPrimitive("attribute").getAsString());
                if (attributeName != null && !BuiltInRegistries.ATTRIBUTE.containsKey(attributeName)) {
                    continue;
                }
                double ratio = attributeObject.getAsJsonPrimitive("ratio").getAsDouble();
                attributeList.add(Pair.of(BuiltInRegistries.ATTRIBUTE.get(attributeName), ratio));
            }
            CONVERSION_DATA.put(sourceAttribute, new MalignantConversionData(BuiltInRegistries.ATTRIBUTE.get(sourceAttribute), consumptionRatio, attributeList));
        }
    }

    public record MalignantConversionData(Attribute sourceAttribute, double consumptionRatio, List<Pair<Attribute, Double>> targetAttributes) {
    }
}
