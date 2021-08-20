package com.sammy.malum.core.mod_systems.spirit;

import com.google.gson.*;
import com.sammy.malum.MalumHelper;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SpiritReloadListener extends JsonReloadListener
{
    private static final Gson GSON = (new GsonBuilder()).create();

    public SpiritReloadListener()
    {
        super(GSON, "spirit_data");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn)
    {
        for (int i = 0; i < objectIn.size(); i++)
        {
            ResourceLocation location = (ResourceLocation) objectIn.keySet().toArray()[i];
            JsonObject object = objectIn.get(location).getAsJsonObject();
            String name = object.getAsJsonPrimitive("registry_name").getAsString();
            JsonArray array = object.getAsJsonArray("spirits");

            ArrayList<MalumSpiritType.MalumEntitySpiritData> spiritData = new ArrayList<>();
            for (JsonElement spiritElement : array)
            {
                JsonObject spiritObject = spiritElement.getAsJsonObject();
                String spiritName = spiritObject.getAsJsonPrimitive("spirit").getAsString();
                int count = spiritObject.getAsJsonPrimitive("count").getAsInt();
                spiritData.add(new MalumSpiritType.MalumEntitySpiritData(SpiritHelper.spiritType(spiritName), count));
            }

            MalumSpiritType.SPIRIT_DATA.put(MalumHelper.moddedLocation(name), new MalumSpiritType.MalumEntitySpiritDataBundle(spiritData));
        }
    }
}