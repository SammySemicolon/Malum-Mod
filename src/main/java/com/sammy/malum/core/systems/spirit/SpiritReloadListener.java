package com.sammy.malum.core.systems.spirit;

import com.google.gson.*;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpiritReloadListener extends JsonReloadListener
{
    private static final Gson GSON = (new GsonBuilder()).create();

    public SpiritReloadListener()
    {
        super(GSON, "malum_spirit_data");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn)
    {
        MalumSpiritType.SPIRIT_DATA = new HashMap<>();
        for (int i = 0; i < objectIn.size(); i++)
        {
            ResourceLocation location = (ResourceLocation) objectIn.keySet().toArray()[i];
            JsonObject object = objectIn.get(location).getAsJsonObject();
            String name = object.getAsJsonPrimitive("registry_name").getAsString();
            ResourceLocation resourceLocation = MalumHelper.moddedLocation(name);
            if (MalumSpiritType.SPIRIT_DATA.containsKey(resourceLocation))
            {
                MalumMod.LOGGER.info("entity with " + name + " already has spirit data associated with it. Skipping file");
                continue;
            }

            JsonArray array = object.getAsJsonArray("spirits");

            ArrayList<MalumEntitySpiritDataBundle.MalumEntitySpiritData> spiritData = new ArrayList<>();
            for (JsonElement spiritElement : array)
            {
                JsonObject spiritObject = spiritElement.getAsJsonObject();
                String spiritName = spiritObject.getAsJsonPrimitive("spirit").getAsString();
                int count = spiritObject.getAsJsonPrimitive("count").getAsInt();
                spiritData.add(new MalumEntitySpiritDataBundle.MalumEntitySpiritData(SpiritHelper.getSpiritType(spiritName), count));
            }

            MalumSpiritType.SPIRIT_DATA.put(resourceLocation, new MalumEntitySpiritDataBundle(spiritData));
        }
    }
}