package com.sammy.malum.common.data.listener.spellweaving;

import com.google.gson.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.server.packs.resources.*;
import net.minecraft.util.profiling.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.event.*;

import java.util.*;

public class SpellweavingEqualityReloadListener extends SimpleJsonResourceReloadListener {
    public static List<SpellweavingEqualityData> EQUALITY_DATA = new ArrayList<>();
    private static final Gson GSON = (new GsonBuilder()).create();

    public SpellweavingEqualityReloadListener() {
        super(GSON, "spellweaving_data");
    }

    public static void register(AddReloadListenerEvent event) {
        event.addListener(new SpellweavingEqualityReloadListener());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        EQUALITY_DATA.clear();
        for (int i = 0; i < objectIn.size(); i++) {
            var location = (ResourceLocation) objectIn.keySet().toArray()[i];
            var array = objectIn.get(location).getAsJsonArray();
            var blocks = new ArrayList<Holder<Block>>();
            for (JsonElement block : array) {
                var name = block.getAsString();
                ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, ResourceLocation.parse(name));
                var holder = BuiltInRegistries.BLOCK.getHolder(key);
                holder.ifPresent(blocks::add);
            }
            EQUALITY_DATA.add(new SpellweavingEqualityData(blocks));
        }
    }

}
