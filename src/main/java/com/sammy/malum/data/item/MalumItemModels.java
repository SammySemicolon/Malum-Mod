package com.sammy.malum.data.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.cosmetic.weaves.AbstractWeaveItem;
import com.sammy.malum.common.item.cosmetic.weaves.PrideweaveItem;
import com.sammy.malum.common.item.curiosities.trinkets.runes.AbstractRuneTrinketsItem;
import com.sammy.malum.common.item.curiosities.weapons.WeightOfWorldsItem;
import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.common.item.curiosities.weapons.staff.AbstractStaffItem;
import com.sammy.malum.common.item.impetus.CrackedImpetusItem;
import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.common.item.impetus.NodeItem;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.registry.common.item.ArmorSkinRegistry;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes;
import team.lodestar.lodestone.systems.datagen.itemsmith.AbstractItemModelSmith;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneItemModelProvider;
import team.lodestar.lodestone.systems.item.ModCombatItem;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class MalumItemModels extends LodestoneItemModelProvider {

    public MalumItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ArmorSkinRegistry.registerItemSkins();
        Set<Supplier<? extends Item>> items = new HashSet<>(ITEMS.getEntries());

        items.removeIf(i -> i.get() instanceof BlockItem);
        items.removeIf(i -> i.get() instanceof MalumScytheItem);
        items.removeIf(i -> i.get() instanceof WeightOfWorldsItem);

        AbstractItemModelSmith.ItemModelSmithData data = new AbstractItemModelSmith.ItemModelSmithData(this, items::remove);

        setTexturePath("cosmetic/weaves/pride/");
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof PrideweaveItem).collect(Collectors.toList()));
        setTexturePath("cosmetic/weaves/");
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof AbstractWeaveItem).collect(Collectors.toList()));

        setTexturePath("runes/");
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof AbstractRuneTrinketsItem).collect(Collectors.toList()));

        setTexturePath("impetus/");
        MalumItemModelSmithTypes.IMPETUS_ITEM.act(data, items.stream().filter(i -> i.get() instanceof ImpetusItem || i.get() instanceof CrackedImpetusItem).collect(Collectors.toList()));
        ItemModelSmithTypes.GENERATED_ITEM.act(data, items.stream().filter(i -> i.get() instanceof NodeItem).collect(Collectors.toList()));

        setTexturePath("");
        MalumItemModelSmithTypes.SPIRIT_ITEM.act(data, items.stream().filter(i -> i.get() instanceof SpiritShardItem).collect(Collectors.toList()));

        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof DiggerItem).collect(Collectors.toList()));
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof SwordItem).collect(Collectors.toList()));
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof ModCombatItem).collect(Collectors.toList()));
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof AbstractStaffItem).collect(Collectors.toList()));
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, TUNING_FORK, TOTEMIC_STAFF);

        MalumItemModelSmithTypes.ARMOR_ITEM.act(data,
                SOUL_HUNTER_CLOAK, SOUL_HUNTER_ROBE, SOUL_HUNTER_LEGGINGS, SOUL_HUNTER_BOOTS,
                SOUL_STAINED_STEEL_HELMET, SOUL_STAINED_STEEL_CHESTPLATE, SOUL_STAINED_STEEL_LEGGINGS, SOUL_STAINED_STEEL_BOOTS);

        MalumItemModelSmithTypes.RITUAL_SHARD_ITEM.act(data, RITUAL_SHARD);

        ItemModelSmithTypes.GENERATED_ITEM.act(data, items);
    }

    @Override
    public String getName() {
        return "Malum Item Models";
    }
}
