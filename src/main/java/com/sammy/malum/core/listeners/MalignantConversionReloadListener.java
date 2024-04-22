package com.sammy.malum.core.listeners;

import com.google.gson.*;
import com.mojang.datafixers.util.*;
import net.minecraft.resources.*;
import net.minecraft.server.packs.resources.*;
import net.minecraft.util.profiling.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraftforge.event.*;
import net.minecraftforge.registries.*;

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
            ResourceLocation sourceAttribute = new ResourceLocation(name);
            if (!ForgeRegistries.ATTRIBUTES.containsKey(sourceAttribute)) {
                continue;
            }
            JsonArray targetAttributes = object.getAsJsonArray("target_attributes");
            List<Pair<Attribute, Double>> attributeList = new ArrayList<>();
            for (JsonElement attribute : targetAttributes) {
                JsonObject attributeObject = attribute.getAsJsonObject();
                ResourceLocation attributeName = new ResourceLocation(attributeObject.getAsJsonPrimitive("attribute").getAsString());
                if (!ForgeRegistries.ATTRIBUTES.containsKey(attributeName)) {
                    continue;
                }
                double ratio = attributeObject.getAsJsonPrimitive("ratio").getAsDouble();
                attributeList.add(Pair.of(ForgeRegistries.ATTRIBUTES.getValue(attributeName), ratio));
            }
            CONVERSION_DATA.put(sourceAttribute, new MalignantConversionData(ForgeRegistries.ATTRIBUTES.getValue(sourceAttribute), attributeList));
        }
    }

    public record MalignantConversionData(Attribute sourceAttribute, List<Pair<Attribute, Double>> targetAttributes) {
    }
}
