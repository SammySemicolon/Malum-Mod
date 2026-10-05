package com.sammy.malum.common.data.listener.malignant_conversion;

import com.google.gson.*;
import com.sammy.malum.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.server.packs.resources.*;
import net.minecraft.util.profiling.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.neoforge.event.*;

import java.util.*;
import java.util.stream.*;

public class MalignantConversionReloadListener extends SimpleJsonResourceReloadListener {

    public static Map<Holder<Attribute>, MalignantConversionData> CONVERSION_DATA = new HashMap<>();

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
            var location = (ResourceLocation) objectIn.keySet().toArray()[i];
            var object = objectIn.get(location).getAsJsonObject();
            double consumptionRatio = object.has("ratio") ? object.get("ratio").getAsDouble() : 1;
            boolean ignoreBaseValue = object.has("ignore_base_value") && object.get("ignore_base_value").getAsBoolean();

            var sourceAttributes = getSourceAttributes(object);
            var payoutData = getPayoutData(object);

            for (Holder<Attribute> sourceAttribute : sourceAttributes) {
                MalignantConversionData data = new MalignantConversionData(sourceAttribute, consumptionRatio, ignoreBaseValue, payoutData);
                CONVERSION_DATA.put(sourceAttribute, data);
            }
        }
    }

    protected static ArrayList<Holder<Attribute>> getSourceAttributes(JsonObject object) {
        ArrayList<String> ids = new ArrayList<>();

        if (object.has("source_attribute")) {
            var name = object.getAsJsonPrimitive("source_attribute").getAsString();
            ids.add(name);
        }
        else if (object.has("source_attributes")) {
            JsonArray sourceAttributes = object.getAsJsonArray("source_attributes");
            for (JsonElement sourceAttribute : sourceAttributes) {
                ids.add(sourceAttribute.getAsString());
            }
        }
        return ids.stream().map(MalignantConversionReloadListener::mapAttribute)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected static List<MalignantConversionAttributePayout> getPayoutData(JsonObject object) {
        List<MalignantConversionAttributePayout> payouts = new ArrayList<>();
        if (object.has("target_attribute")) {
            var payout = getPayout(object.get("target_attribute").getAsJsonObject());
            payout.ifPresent(payouts::add);
        }
        else if (object.has("target_attributes")) {
            var array = object.getAsJsonArray("target_attributes");
            for (JsonElement jsonElement : array) {
                if (!jsonElement.isJsonObject()) {
                    continue;
                }
                var payout = getPayout(jsonElement.getAsJsonObject());
                payout.ifPresent(payouts::add);
            }
        }
        return payouts;
    }

    public static Optional<MalignantConversionAttributePayout> getPayout(JsonObject object) {
        if (object.has("attribute")) {
            var attribute = object.getAsJsonPrimitive("attribute").getAsString();
            var ratio = object.has("ratio") ? object.getAsJsonPrimitive("ratio").getAsDouble() : 1.0;
            return mapAttribute(attribute).map(a -> new MalignantConversionAttributePayout(a, ratio));
        }
        return Optional.empty();
    }

    public static Optional<Holder<Attribute>> mapAttribute(String id) {
        ResourceKey<Attribute> attributeKey = ResourceKey.create(Registries.ATTRIBUTE, ResourceLocation.parse(id));
        var attributeHolder = BuiltInRegistries.ATTRIBUTE.getHolder(attributeKey);
        if (attributeHolder.isEmpty()) {
            MalumMod.LOGGER.warn("Malignant conversion data references an attribute that does not exist: {}", id);
            return Optional.empty();
        }
        return Optional.of(attributeHolder.get());
    }

}
