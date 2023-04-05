package com.sammy.malum.data;

import com.sammy.malum.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.cosmetic.*;
import com.sammy.malum.common.item.ether.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.item.tools.*;
import com.sammy.malum.core.systems.item.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.itemsmith.*;
import team.lodestar.lodestone.systems.datagen.providers.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes.GENERATED;

public class MalumItemModels extends LodestoneItemModelProvider {
    public MalumItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ItemSkinRegistry.registerItemSkins(null);
        Set<Supplier<Item>> items = new HashSet<>(ITEMS.getEntries());

        items.removeIf(i -> i.get() instanceof BlockItem);
        items.removeIf(i -> i.get() instanceof MalumScytheItem);

        AbstractItemModelSmith.ItemModelSmithData data = new AbstractItemModelSmith.ItemModelSmithData(this, items::remove);

//        blightedSpireItem(take(items, ItemRegistry.BLIGHTED_TUMOR));

//        takeAll(items, i -> i.get() instanceof MultiBlockItem).forEach(this::multiBlockItem);

        setTexturePath("cosmetic/weaves/pride");
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof PrideweaveItem).toList());
        setTexturePath("cosmetic/weaves");
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof AbstractWeaveItem).toList());

        setTexturePath("impetus/");
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof ImpetusItem || i.get() instanceof CrackedImpetusItem || i.get() instanceof NodeItem).toList());




//        takeAll(blockItems, i -> ((BlockItem) i.get()).getBlock() instanceof EtherBrazierBlock).forEach(this::etherBrazierItem);
//        takeAll(blockItems, i -> ((BlockItem) i.get()).getBlock() instanceof EtherSconceBlock).forEach(this::etherSconceItem);
//        takeAll(blockItems, i -> ((BlockItem) i.get()).getBlock() instanceof EtherTorchBlock).forEach(this::etherTorchItem);
//        takeAll(blockItems, i -> ((BlockItem) i.get()).getBlock() instanceof EtherBlock).forEach(this::etherItem);

        setTexturePath("");
        SPIRIT_ITEM.act(data, items.stream().filter(i -> i.get() instanceof MalumSpiritItem).toList());
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof DiggerItem).toList());
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof SwordItem).toList());
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof ModCombatItem).toList());
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, SOULWOOD_STAVE, SOUL_STAINED_STEEL_KNIFE);

        ItemModelSmithTypes.GENERATED_ITEM.act(data, NATURAL_QUARTZ);
//
//        takeAll(items, i -> i.get() instanceof LodestoneArmorItem).forEach(this::armorItem);
       // ItemModelSmithTypes.GENERATED_ITEM.act(data, items);
    }

    public static ItemModelSmith SPIRIT_ITEM = new ItemModelSmith((item, provider) -> {
        provider.createGenericModel(item, GENERATED, provider.modLoc("item/spirit_shard"));
    });

    private void blightedSpireItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", malumPath("block/" + name+"_0"));
    }

    private void multiBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(malumPath("item/" + name + "_item")));
    }

    private void nodeItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/impetus/" + name));
    }

    private void prideweaveItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/cosmetic/weaves/pride/" + "prideweave_" + name.replace("_prideweave", "")));
    }

    private void cosmeticItem(RegistryObject<Item> i, String extraPath) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/cosmetic/" + extraPath + name));
    }

    private void armorItem(RegistryObject<Item> i) {
        String name = getItemName(i.get());
        createGenericModel(i.get(), GENERATED, getItemTexture(name));
        for (Map.Entry<String, ItemSkin> entry : ItemSkinRegistry.SKINS.entrySet()) {
            ItemSkin skin = entry.getValue();
            int value = skin.index;
            ItemSkin.ItemSkinDatagenData datagenData = ItemSkinRegistry.SKIN_DATAGEN_DATA.get(skin);
            if (datagenData == null) {
                continue;
            }
            String itemSuffix = datagenData.getSuffix((LodestoneArmorItem) i.get());
            ResourceLocation itemTexturePath = new ResourceLocation(datagenData.itemTexturePrefix+itemSuffix);
            getBuilder(i.get().getRegistryName().getPath()).override()
                    .predicate(new ResourceLocation(ItemSkin.MALUM_SKIN_TAG), value)
                    .model(withExistingParent(entry.getKey() + "_" + itemSuffix, GENERATED).texture("layer0", itemTexturePath))
                    .end();
        }

    }
    private void impetusItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        List<String> split = DataHelper.reverseOrder(new ArrayList<>(), Arrays.asList(name.split("_")));
        split.remove(0);
        String alteredName = String.join("_", split);
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/impetus/" + alteredName));
    }

    private void spiritSplinterItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/spirit_shard"));
    }

    private void etherBrazierItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        String textureName = name.substring(0, 8) + "ether_brazier";
        AbstractEtherItem etherItem = (AbstractEtherItem) i.get();
        if (etherItem.iridescent) {
            withExistingParent(name, GENERATED).texture("layer0", malumPath("item/iridescent_ether_brazier")).texture("layer1", malumPath("item/" + textureName)).texture("layer2", malumPath("item/iridescent_ether_brazier_overlay"));
            return;
        }
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/ether_brazier_overlay")).texture("layer1", malumPath("item/" + textureName));
    }

    private void etherTorchItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        AbstractEtherItem etherItem = (AbstractEtherItem) i.get();
        if (etherItem.iridescent) {
            withExistingParent(name, GENERATED).texture("layer0", malumPath("item/iridescent_ether_torch")).texture("layer1", malumPath("item/ether_torch")).texture("layer2", malumPath("item/iridescent_ether_torch_overlay"));
            return;
        }
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/ether_torch_overlay")).texture("layer1", malumPath("item/ether_torch"));
    }

    private void etherSconceItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        AbstractEtherItem etherItem = (AbstractEtherItem) i.get();
        if (etherItem.iridescent) {
            withExistingParent(name, GENERATED).texture("layer0", malumPath("item/iridescent_ether_sconce")).texture("layer1", malumPath("item/ether_sconce")).texture("layer2", malumPath("item/iridescent_ether_sconce_overlay"));
            return;
        }
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/ether_sconce_overlay")).texture("layer1", malumPath("item/ether_sconce"));
    }

    private void etherItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        AbstractEtherItem etherItem = (AbstractEtherItem) i.get();
        if (etherItem.iridescent) {
            withExistingParent(name, GENERATED).texture("layer0", malumPath("item/iridescent_ether")).texture("layer1", malumPath("item/iridescent_ether_overlay"));
            return;
        }
        withExistingParent(name, GENERATED).texture("layer0", malumPath("item/ether"));
    }
    @Override
    public String getName() {
        return "Malum Item Models";
    }
}
