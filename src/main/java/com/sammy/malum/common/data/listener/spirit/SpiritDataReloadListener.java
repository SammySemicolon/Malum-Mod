package com.sammy.malum.common.data.listener.spirit;

import com.google.gson.*;
import com.mojang.datafixers.util.*;
import com.mojang.serialization.JsonOps;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import com.sammy.malum.core.systems.registry.*;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.*;

public class SpiritDataReloadListener extends SimpleJsonResourceReloadListener {
    public static final Map<ResourceLocation, EntitySpiritDropData> SPIRIT_DATA = new HashMap<>();
    public static final Set<ResourceLocation> HAS_NO_DATA = new HashSet<>();

    public static final EntitySpiritDropData DEFAULT_MONSTER_SPIRIT_DATA = EntitySpiritDropData
            .builder(MalumSpiritTypes.WICKED_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_CREATURE_SPIRIT_DATA = EntitySpiritDropData
            .builder(MalumSpiritTypes.SACRED_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_AMBIENT_SPIRIT_DATA = EntitySpiritDropData
            .builder(MalumSpiritTypes.AERIAL_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_WATER_CREATURE_SPIRIT_DATA = EntitySpiritDropData
            .builder(MalumSpiritTypes.AQUEOUS_SPIRIT)
            .withSpirit(MalumSpiritTypes.SACRED_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_WATER_AMBIENT_SPIRIT_DATA = EntitySpiritDropData
            .builder(MalumSpiritTypes.AQUEOUS_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_UNDERGROUND_WATER_CREATURE_SPIRIT_DATA = EntitySpiritDropData
            .builder(MalumSpiritTypes.AQUEOUS_SPIRIT)
            .withSpirit(MalumSpiritTypes.EARTHEN_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_AXOLOTL_SPIRIT_DATA = EntitySpiritDropData // They're their own category
            .builder(MalumSpiritTypes.AQUEOUS_SPIRIT, 2)
            .withSpirit(MalumSpiritTypes.SACRED_SPIRIT)
            .build();
    public static final EntitySpiritDropData DEFAULT_BOSS_SPIRIT_DATA = EntitySpiritDropData
            .builder(MalumSpiritTypes.ELDRITCH_SPIRIT, 4)
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
            var object = entry.getAsJsonObject();
            var name = object.getAsJsonPrimitive("registry_name").getAsString();
            var resourceLocation = ResourceLocation.tryParse(name);
            if (resourceLocation == null) {
                continue;
            }
            if (!BuiltInRegistries.ENTITY_TYPE.containsKey(resourceLocation)) {
                continue;
            }

            if (object.has("no_spirits") && object.get("no_spirits").getAsBoolean()) {
                MalumMod.LOGGER.info("Removed spirit drops for entity with registry name: {}", name);
                SPIRIT_DATA.remove(resourceLocation);
                HAS_NO_DATA.add(resourceLocation);
                continue;
            }
            var primaryType = object.getAsJsonPrimitive("primary_type").getAsString();
            if (!primaryType.equals("none")) {
                var holder = SpiritHolder.getSpiritType(primaryType);
                if (!holder.isBound()) {
                    MalumMod.LOGGER.info("No such spirit exists, this is a datapack error: {}", primaryType);
                    continue;
                }
                MalumMod.LOGGER.info("Added spirit drops for entity with registry name: {}", name);
                JsonArray array = object.getAsJsonArray("spirits");
                SPIRIT_DATA.put(resourceLocation, new EntitySpiritDropData(holder, getSpiritDrops(array), getItemAsSoul(object)));
                HAS_NO_DATA.remove(resourceLocation);
            }
        }
    }

    private static List<SpiritIngredient> getSpiritDrops(JsonArray array) {
        List<SpiritIngredient> spiritData = new ArrayList<>();
        for (JsonElement spiritElement : array) {
            JsonObject spiritObject = spiritElement.getAsJsonObject();
            String spiritName = spiritObject.getAsJsonPrimitive("spirit").getAsString();
            int count = spiritObject.has("count") ? spiritObject.getAsJsonPrimitive("count").getAsInt() : 1;
            spiritData.add(new SpiritIngredient(SpiritHolder.getSpiritType(spiritName), count));
        }
        return spiritData;
    }

    private static Ingredient getItemAsSoul(JsonObject object) {
        return object.has("spirit_item") ? Ingredient.CODEC.decode(JsonOps.INSTANCE, object.get("spirit_item")).map(Pair::getFirst).result().orElse(null) : null;
    }
}