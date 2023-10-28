package com.sammy.malum.data.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.cosmetic.weaves.AbstractWeaveItem;
import com.sammy.malum.common.item.cosmetic.weaves.PrideweaveItem;
import com.sammy.malum.common.item.curiosities.weapons.MalumScytheItem;
import com.sammy.malum.common.item.impetus.CrackedImpetusItem;
import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.common.item.impetus.NodeItem;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.common.item.spirit.TunedOpticItem;
import com.sammy.malum.registry.common.item.ArmorSkinRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.data.ExistingFileHelper;
import team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes;
import team.lodestar.lodestone.systems.datagen.itemsmith.AbstractItemModelSmith;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneItemModelProvider;
import team.lodestar.lodestone.systems.item.ModCombatItem;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class MalumItemModels extends LodestoneItemModelProvider {
    public MalumItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ArmorSkinRegistry.registerItemSkins(null);
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
        MalumItemModelSmithTypes.SPIRIT_OPTIC_ITEM.act(data, items.stream().filter(i -> i.get() instanceof TunedOpticItem).toList());

        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof DiggerItem).toList());
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof SwordItem).toList());
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, items.stream().filter(i -> i.get() instanceof ModCombatItem).toList());
        ItemModelSmithTypes.HANDHELD_ITEM.act(data, SOULWOOD_STAVE, SOUL_STAINED_STEEL_KNIFE);

        MalumItemModelSmithTypes.ARMOR_ITEM.act(data,
                SOUL_HUNTER_CLOAK, SOUL_HUNTER_ROBE, SOUL_HUNTER_LEGGINGS, SOUL_HUNTER_BOOTS,
                SOUL_STAINED_STEEL_HELMET, SOUL_STAINED_STEEL_CHESTPLATE, SOUL_STAINED_STEEL_LEGGINGS, SOUL_STAINED_STEEL_BOOTS);


        ItemModelSmithTypes.GENERATED_ITEM.act(data, items);
    }

    @Override
    public String getName() {
        return "Malum Item Models";
    }
}
