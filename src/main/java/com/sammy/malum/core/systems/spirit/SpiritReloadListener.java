package com.sammy.malum.core.systems.spirit;

import com.google.gson.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpiritReloadListener extends SimpleJsonResourceReloadListener
{
    private static final Gson GSON = (new GsonBuilder()).create();

    public SpiritReloadListener()
    {
        super(GSON, "malum_spirit_data");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn)
    {
        SpiritTypeRegistry.SPIRIT_DATA = new HashMap<>();
        for (int i = 0; i < objectIn.size(); i++)
        {
            ResourceLocation location = (ResourceLocation) objectIn.keySet().toArray()[i];
            JsonObject object = objectIn.get(location).getAsJsonObject();
            String name = object.getAsJsonPrimitive("registry_name").getAsString();
            ResourceLocation resourceLocation = new ResourceLocation(name);
            if (SpiritTypeRegistry.SPIRIT_DATA.containsKey(resourceLocation))
            {
                MalumMod.LOGGER.info("entity with " + name + " already has spirit data associated with it. Skipping file");
                continue;
            }

            JsonArray array = object.getAsJsonArray("spirits");

            ArrayList<MalumEntitySpiritData.DataEntry> spiritData = new ArrayList<>();
            for (JsonElement spiritElement : array)
            {
                JsonObject spiritObject = spiritElement.getAsJsonObject();
                String spiritName = spiritObject.getAsJsonPrimitive("spirit").getAsString();
                int count = spiritObject.getAsJsonPrimitive("count").getAsInt();
                spiritData.add(new MalumEntitySpiritData.DataEntry(SpiritHelper.getSpiritType(spiritName), count));
            }

            SpiritTypeRegistry.SPIRIT_DATA.put(resourceLocation, new MalumEntitySpiritData(spiritData));
        }
    }
}