package com.sammy.malum.core.systems.spirit;

import com.google.gson.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.helper.SpiritHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;

import java.util.ArrayList;
import java.util.Map;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.SPIRIT_DATA;

public class SpiritDataReloadListener extends SimpleJsonResourceReloadListener
{
    private static final Gson GSON = (new GsonBuilder()).create();

    public SpiritDataReloadListener()
    {
        super(GSON, "spirit_data");
    }

    public static void register(AddReloadListenerEvent event) {
        event.addListener(new SpiritDataReloadListener());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn)
    {
        SPIRIT_DATA.clear();
        for (int i = 0; i < objectIn.size(); i++)
        {
            ResourceLocation location = (ResourceLocation) objectIn.keySet().toArray()[i];
            JsonObject object = objectIn.get(location).getAsJsonObject();
            String name = object.getAsJsonPrimitive("registry_name").getAsString();
            ResourceLocation resourceLocation = new ResourceLocation(name);
            if (!Registry.ENTITY_TYPE.containsKey(resourceLocation))
            {
                continue;
            }
            if (!object.has("primary_type"))
            {
                MalumMod.LOGGER.info("entity with registry name: " + name + " lacks a primary type. Skipping file.");
                continue;
            }
            if (SPIRIT_DATA.containsKey(resourceLocation))
            {
                MalumMod.LOGGER.info("entity with registry name: " + name + " already has spirit data associated with it. Overwriting.");
            }
            String primaryType = object.getAsJsonPrimitive("primary_type").getAsString();
            JsonArray array = object.getAsJsonArray("spirits");

            ArrayList<MalumEntitySpiritData.DataEntry> spiritData = new ArrayList<>();
            for (JsonElement spiritElement : array)
            {
                JsonObject spiritObject = spiritElement.getAsJsonObject();
                String spiritName = spiritObject.getAsJsonPrimitive("spirit").getAsString();
                int count = spiritObject.getAsJsonPrimitive("count").getAsInt();
                spiritData.add(new MalumEntitySpiritData.DataEntry(SpiritHelper.getSpiritType(spiritName), count));
            }

            SPIRIT_DATA.put(resourceLocation, new MalumEntitySpiritData(SpiritHelper.getSpiritType(primaryType), spiritData));
        }
    }
}