package com.sammy.malum.core.listeners;

import com.google.gson.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class SpiritDataReloadListener extends SimpleJsonResourceReloadListener {
    public static final Map<ResourceLocation, MalumEntitySpiritData> SPIRIT_DATA = new HashMap<>();
    public static final Set<ResourceLocation> HAS_NO_DATA = new HashSet<>();

    public static final MalumEntitySpiritData DEFAULT_MONSTER_SPIRIT_DATA = MalumEntitySpiritData
        .builder(SpiritTypeRegistry.WICKED_SPIRIT)
        .build();
    public static final MalumEntitySpiritData DEFAULT_CREATURE_SPIRIT_DATA = MalumEntitySpiritData
        .builder(SpiritTypeRegistry.SACRED_SPIRIT)
        .build();
    public static final MalumEntitySpiritData DEFAULT_AMBIENT_SPIRIT_DATA = MalumEntitySpiritData
        .builder(SpiritTypeRegistry.AERIAL_SPIRIT)
        .build();
    public static final MalumEntitySpiritData DEFAULT_WATER_CREATURE_SPIRIT_DATA = MalumEntitySpiritData
        .builder(SpiritTypeRegistry.AQUEOUS_SPIRIT)
        .withSpirit(SpiritTypeRegistry.SACRED_SPIRIT)
        .build();
    public static final MalumEntitySpiritData DEFAULT_WATER_AMBIENT_SPIRIT_DATA = MalumEntitySpiritData
        .builder(SpiritTypeRegistry.AQUEOUS_SPIRIT)
        .build();
    public static final MalumEntitySpiritData DEFAULT_UNDERGROUND_WATER_CREATURE_SPIRIT_DATA = MalumEntitySpiritData
        .builder(SpiritTypeRegistry.AQUEOUS_SPIRIT)
        .withSpirit(SpiritTypeRegistry.EARTHEN_SPIRIT)
        .build();
    public static final MalumEntitySpiritData DEFAULT_AXOLOTL_SPIRIT_DATA = MalumEntitySpiritData // They're their own category
        .builder(SpiritTypeRegistry.AQUEOUS_SPIRIT, 2)
        .withSpirit(SpiritTypeRegistry.SACRED_SPIRIT)
        .build();
    public static final MalumEntitySpiritData DEFAULT_BOSS_SPIRIT_DATA = MalumEntitySpiritData
        .builder(SpiritTypeRegistry.ELDRITCH_SPIRIT, 2)
        .build();

    private static final Gson GSON = (new GsonBuilder()).create();

    public SpiritDataReloadListener() {
        super(GSON, "spirit_data/entity");
    }

    public static void register(AddReloadListenerEvent event) {
        event.addListener(new SpiritDataReloadListener());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        SPIRIT_DATA.clear();
        HAS_NO_DATA.clear();
        for (JsonElement entry : objectIn.values()) {
            JsonObject object = entry.getAsJsonObject();
            String name = object.getAsJsonPrimitive("registry_name").getAsString();
            ResourceLocation resourceLocation = new ResourceLocation(name);
            if (!ForgeRegistries.ENTITY_TYPES.containsKey(resourceLocation)) {
                continue;
            }
            if (!object.has("primary_type")) {
                MalumMod.LOGGER.info("Entity with registry name: " + name + " lacks a primary spirit type. Skipping file.");
                continue;
            }
            String primaryType = object.getAsJsonPrimitive("primary_type").getAsString();
            boolean isEmpty = primaryType.equals("none");
            if (SPIRIT_DATA.containsKey(resourceLocation)) {
                if (isEmpty)
                    MalumMod.LOGGER.info("Entity with registry name: " + name + " already has spirit data associated with it. Removing.");
                else
                    MalumMod.LOGGER.info("Entity with registry name: " + name + " already has spirit data associated with it. Overwriting.");
            } else if (HAS_NO_DATA.contains(resourceLocation) && !isEmpty) {
                MalumMod.LOGGER.info("Entity with registry name: " + name + " already has empty spirit data associated with it. Overwriting.");
            }
            if (primaryType.equals("none")) {
                SPIRIT_DATA.remove(resourceLocation);
                HAS_NO_DATA.add(resourceLocation);
            } else {
                JsonArray array = object.getAsJsonArray("spirits");
                SPIRIT_DATA.put(resourceLocation, new MalumEntitySpiritData(SpiritHelper.getSpiritType(primaryType), getSpiritData(array), getSpiritItem(object)));
                HAS_NO_DATA.remove(resourceLocation);
            }
        }
    }

    private static List<SpiritWithCount> getSpiritData(JsonArray array) {
        List<SpiritWithCount> spiritData = new ArrayList<>();
        for (JsonElement spiritElement : array) {
            JsonObject spiritObject = spiritElement.getAsJsonObject();
            String spiritName = spiritObject.getAsJsonPrimitive("spirit").getAsString();
            int count = spiritObject.getAsJsonPrimitive("count").getAsInt();
            spiritData.add(new SpiritWithCount(SpiritHelper.getSpiritType(spiritName), count));
        }
        return spiritData;
    }

    private static Ingredient getSpiritItem(JsonObject object) {
        if (!object.has("spirit_item")) {
            return null;
        }

        try {
            return Ingredient.fromJson(object.get("spirit_item"));
        } catch (JsonParseException ignored) {
            return null;
        }
    }
}