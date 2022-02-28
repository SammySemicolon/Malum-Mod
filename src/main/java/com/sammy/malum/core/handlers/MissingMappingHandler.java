package com.sammy.malum.core.handlers;

import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class MissingMappingHandler {

    public static void correctMissingMappings(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings()) {
            if (mapping.key.equals(DataHelper.prefix("brilliance_cluster")))
            {
                mapping.remap(ItemRegistry.CLUSTER_OF_BRILLIANCE.get());
            }
            if (mapping.key.equals(DataHelper.prefix("brilliance_chunk")))
            {
                mapping.remap(ItemRegistry.CHUNK_OF_BRILLIANCE.get());
            }
            if (mapping.key.equals(DataHelper.prefix("soulstone_cluster")))
            {
                mapping.remap(ItemRegistry.RAW_SOULSTONE.get());
            }
        }
    }
}
