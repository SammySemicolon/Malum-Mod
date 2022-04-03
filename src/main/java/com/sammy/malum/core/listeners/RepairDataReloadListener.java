package com.sammy.malum.core.listeners;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import com.sammy.malum.core.systems.spirit.MalumSpiritRepairData;
import com.sammy.malum.core.systems.spirit.MalumSpiritRepairData.RepairDataEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

import static com.sammy.malum.core.listeners.SpiritDataReloadListener.getSpiritData;

public class RepairDataReloadListener extends SimpleJsonResourceReloadListener {
    public static ArrayList<MalumSpiritRepairData> REPAIR_DATA = new ArrayList<>();
    public static ArrayList<MalumEntitySpiritData.SpiritDataEntry> DEFAULT_SPIRIT_DATA = new ArrayList<>();

    private static final Gson GSON = (new GsonBuilder()).create();

    public RepairDataReloadListener() {
        super(GSON, "spirit_data/item/repair");
    }

    public static void register(AddReloadListenerEvent event) {
        event.addListener(new RepairDataReloadListener());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        REPAIR_DATA.clear();
        DEFAULT_SPIRIT_DATA = getSpiritData(objectIn.remove(DataHelper.prefix("default")).getAsJsonObject().getAsJsonArray("spirits"));

        for (int i = 0; i < objectIn.size(); i++) {
            ResourceLocation location = (ResourceLocation) objectIn.keySet().toArray()[i];
            JsonObject object = objectIn.get(location).getAsJsonObject();
            float percentage = object.getAsJsonPrimitive("durability_percentage").getAsFloat();
            Ingredient ingredient = Ingredient.fromJson(object.get("repair_ingredient"));
            JsonArray targetItems = object.getAsJsonArray("target_items");

            ArrayList<RepairDataEntry> repairData = new ArrayList<>();
            ArrayList<Item> lookupBlacklist = new ArrayList<>();
            for (JsonElement repairElement : targetItems) {
                JsonObject repairObject = repairElement.getAsJsonObject();
                Item targetItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(repairObject.getAsJsonPrimitive("item").getAsString()));
                if (targetItem == null) {
                    continue;
                }
                lookupBlacklist.add(targetItem);
                int repairItemCount = repairObject.getAsJsonPrimitive("repair_item_count").getAsInt();
                repairData.add(new RepairDataEntry(targetItem, repairItemCount));
            }
            if (object.has("lookup_name")) {
                for (Item item : ForgeRegistries.ITEMS) {
                    if (!lookupBlacklist.contains(item)) {
                        if (item.getRegistryName().getPath().contains(object.getAsJsonPrimitive("lookup_name").getAsString())) {
                            int count = object.getAsJsonPrimitive("lookup_repair_item_count").getAsInt();
                            repairData.add(new RepairDataEntry(item, count));
                        }
                    }
                }
            }
            JsonArray spirits = object.getAsJsonArray("spirits");
            REPAIR_DATA.add(new MalumSpiritRepairData(percentage, ingredient, repairData, getSpiritData(spirits)));
        }
    }
}