package com.sammy.malum.core.listeners;

import com.google.gson.*;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

import static com.sammy.malum.core.listeners.SpiritDataReloadListener.getSpiritData;

public class RepairDataReloadListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).create();

    public RepairDataReloadListener() {
        super(GSON, "spirit_data/item");
    }

    public static void register(AddReloadListenerEvent event) {
        event.addListener(new RepairDataReloadListener());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        for (int i = 0; i < objectIn.size(); i++) {
            ResourceLocation location = (ResourceLocation) objectIn.keySet().toArray()[i];
            JsonObject object = objectIn.get(location).getAsJsonObject();
            int minimum = object.getAsJsonPrimitive("minimum_durability").getAsInt();
            float percentage = object.getAsJsonPrimitive("recovery_percentage").getAsFloat();
        }
    }
}