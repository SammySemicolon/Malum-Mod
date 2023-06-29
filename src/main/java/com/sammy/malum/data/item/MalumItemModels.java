package com.sammy.malum.data.item;

import com.sammy.malum.*;
import com.sammy.malum.common.item.cosmetic.*;
import com.sammy.malum.common.item.curiosities.weapons.*;
import com.sammy.malum.common.item.ether.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.itemsmith.*;
import team.lodestar.lodestone.systems.datagen.providers.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes.*;

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

        setTexturePath("cosmetic/weaves/pride/");
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof PrideweaveItem).toList());
        setTexturePath("cosmetic/weaves/");
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof AbstractWeaveItem).toList());

        setTexturePath("impetus/");
        MalumItemModelSmithTypes.IMPETUS_ITEM.act(data, items.stream().filter(i -> i.get() instanceof ImpetusItem || i.get() instanceof CrackedImpetusItem).toList());
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof NodeItem).toList());


        setTexturePath("");
        MalumItemModelSmithTypes.SPIRIT_ITEM.act(data, items.stream().filter(i -> i.get() instanceof SpiritShardItem).toList());

        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof DiggerItem).toList());
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof SwordItem).toList());
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof ModCombatItem).toList());
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, SOULWOOD_STAVE, SOUL_STAINED_STEEL_KNIFE);

        MalumItemModelSmithTypes.ARMOR_ITEM.act(data,
                SOUL_HUNTER_CLOAK, SOUL_HUNTER_ROBE, SOUL_HUNTER_LEGGINGS, SOUL_HUNTER_BOOTS,
                SOUL_STAINED_STEEL_HELMET, SOUL_STAINED_STEEL_CHESTPLATE, SOUL_STAINED_STEEL_LEGGINGS, SOUL_STAINED_STEEL_BOOTS);


        ItemModelSmithTypes.GENERATED_ITEM.act(data, items);
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
