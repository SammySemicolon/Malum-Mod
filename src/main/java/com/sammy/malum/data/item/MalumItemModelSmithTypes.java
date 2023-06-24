package com.sammy.malum.data.item;

import com.sammy.malum.core.systems.item.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.resources.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.datagen.itemsmith.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;

import static team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes.*;

public class MalumItemModelSmithTypes {

    public static ItemModelSmith IMPETUS_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        List<String> split = DataHelper.reverseOrder(new ArrayList<>(), Arrays.asList(name.split("_")));
        split.remove(0);
        String alteredName = String.join("_", split);
        provider.createGenericModel(item, GENERATED, provider.getItemTexture(alteredName));
    });

    public static ItemModelSmith SPIRIT_ITEM = new ItemModelSmith((item, provider) -> {
        provider.createGenericModel(item, GENERATED, provider.modLoc("item/spirit_shard"));
    });

    public static ItemModelSmith SPIRIT_MOTE_ITEM = new ItemModelSmith((item, provider) -> {
        provider.createGenericModel(item, GENERATED, provider.modLoc("item/spirit_mote"));
    });

    public static ItemModelSmith GENERATED_OVERLAY_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        provider.withExistingParent(name, GENERATED).texture("layer0", provider.modLoc("item/"+name)).texture("layer1", provider.modLoc("item/"+name+"_overlay"));
    });

    public static ItemModelSmith HANDHELD_OVERLAY_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        provider.withExistingParent(name, HANDHELD).texture("layer0", provider.modLoc("item/"+name)).texture("layer1", provider.modLoc("item/"+name+"_overlay"));
    });

    public static ItemModelSmith ETHER_BRAZIER_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        String rockType = name.split("_")[0];
        String brazierName = rockType + "_ether_brazier";
        String overlayName = name.replace(rockType+"_", "");
        provider.withExistingParent(name, GENERATED).texture("layer0", provider.modLoc("item/"+brazierName)).texture("layer1", provider.modLoc("item/"+overlayName+"_overlay"));
    });

    public static ItemModelSmith IRIDESCENT_ETHER_BRAZIER_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        String rockType = name.split("_")[0];
        String brazierName = rockType + "_ether_brazier";
        String overlayName = name.replace(rockType+"_", "");
        provider.withExistingParent(name, GENERATED).texture("layer0", provider.modLoc("item/"+brazierName)).texture("layer1", provider.modLoc("item/"+overlayName)).texture("layer2", provider.modLoc("item/"+overlayName+"_overlay"));
    });

    public static ItemModelSmith ARMOR_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        provider.createGenericModel(item, GENERATED, provider.getItemTexture(name));
        for (Map.Entry<String, ItemSkin> entry : ItemSkinRegistry.SKINS.entrySet()) {
            ItemSkin skin = entry.getValue();
            int value = skin.index;
            ItemSkin.ItemSkinDatagenData datagenData = ItemSkinRegistry.SKIN_DATAGEN_DATA.get(skin);
            if (datagenData == null) {
                continue;
            }
            String itemSuffix = datagenData.getSuffix((LodestoneArmorItem) item);
            ResourceLocation itemTexturePath = new ResourceLocation(datagenData.itemTexturePrefix + itemSuffix);
            provider.getBuilder(item.getRegistryName().getPath()).override()
                    .predicate(new ResourceLocation(ItemSkin.MALUM_SKIN_TAG), value)
                    .model(provider.withExistingParent(entry.getKey() + "_" + itemSuffix, GENERATED).texture("layer0", itemTexturePath))
                    .end();
        }
    });
}