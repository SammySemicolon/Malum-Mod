package com.sammy.malum.datagen.item;

import com.sammy.malum.*;
import com.sammy.malum.common.item.curiosities.*;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.datagen.set.MalumMetallicsDatagen;
import com.sammy.malum.registry.common.MalumContent;
import net.minecraft.data.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.data.*;
import team.lodestar.lodestone.modules.datagen.providers.item.LodestoneItemModelSystem;
import team.lodestar.lodestone.modules.datagen.smith.itemmodel.data.ItemModelSystemData;

import static com.sammy.malum.registry.common.MalumContent.Vanity.*;

public class MalumItemModelDatagen extends LodestoneItemModelSystem {

    public MalumItemModelDatagen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        ItemModelSystemData data = new ItemModelSystemData(this, MalumContent.ITEMS);

        WeaponItemModelSmithTypes.SOUL_OF_AN_ITEM.act(data, TemporarilyDisabledItem.class);
        setTexturePath("cosmetic/weaves/pride/");
        MalumItemModelSmithTypes.GENERATED_ITEM.act(data,
                ACE_PRIDEWEAVE, AGENDER_PRIDEWEAVE, ARO_PRIDEWEAVE, AROACE_PRIDEWEAVE, BI_PRIDEWEAVE,
                DEMIBOY_PRIDEWEAVE, DEMIGIRL_PRIDEWEAVE, ENBY_PRIDEWEAVE, GAY_PRIDEWEAVE, GENDERFLUID_PRIDEWEAVE,
                GENDERQUEER_PRIDEWEAVE, INTERSEX_PRIDEWEAVE, LESBIAN_PRIDEWEAVE, PAN_PRIDEWEAVE, PLURAL_PRIDEWEAVE,
                POLY_PRIDEWEAVE, PRIDE_PRIDEWEAVE, TRANS_PRIDEWEAVE
        );


        setTexturePath("spirit/");
        MalumItemModelSmithTypes.GENERATED_ITEM.act(data, SpiritShardItem.class);

        setTexturePath("runes/");
        MalumItemModelSmithTypes.GENERATED_ITEM.act(data, AbstractRuneCurioItem.class);

        setTexturePath("alchemy/");
        MalumItemModelSmithTypes.IMPETUS_ITEM.act(data, MalumContent.AlchemyAndMetallics.ALCHEMICAL_IMPETUS, MalumContent.AlchemyAndMetallics.FRACTURED_ALCHEMICAL_IMPETUS);
        MalumItemModelSmithTypes.IMPETUS_ITEM.act(data, MalumContent.AlchemyAndMetallics.ZEPHYR_IMPETUS, MalumContent.AlchemyAndMetallics.FRACTURED_ZEPHYR_IMPETUS);
        MalumItemModelSmithTypes.IMPETUS_ITEM.act(data, MalumContent.AlchemyAndMetallics.IFRIT_IMPETUS, MalumContent.AlchemyAndMetallics.FRACTURED_IFRIT_IMPETUS);

        setTexturePath("alchemy/metallics/");
        MalumMetallicsDatagen.MALUM.addItemModels(data);

        setTexturePath("");
        MalumItemModelSmithTypes.BUILTIN_ENTITY_ITEM.act(data, MalumContent.GEAS);
        MalumItemModelSmithTypes.HANDHELD_OVERLAY_ITEM.act(data, MalumContent.Gear.UNWINDING_CHAOS, MalumContent.Gear.SUNDERING_ANCHOR).forEach(result -> result.addModelLayerData().emissive(15, 15, 1));
        WeaponItemModelSmithTypes.LARGE_HANDHELD_ITEM.act(data, MalumContent.Gear.CRUDE_SCYTHE, MalumContent.Gear.SOUL_STAINED_STEEL_SCYTHE, MalumContent.Gear.RAVENOUS_SCYTHE, MalumContent.Gear.EDGE_OF_DELIVERANCE, MalumContent.Gear.WEIGHT_OF_WORLDS, MalumContent.Enscription.SHAPED_SLAB);
        MalumItemModelSmithTypes.HANDHELD_ITEM.act(data, MalumContent.Gear.SOUL_STAINED_STEEL_KNIFE, MalumContent.Gear.GLUTTONOUS_BLUDGEON, MalumContent.Focusing.TUNING_FORK, MalumContent.Artifice.ARTIFICERS_CLAW, MalumContent.Totemancy.TOTEMIC_STAFF);
        MalumItemModelSmithTypes.HANDHELD_ITEM.act(data, MalumContent.Gear.MNEMONIC_HEX_STAFF, MalumContent.Gear.EROSION_SCEPTER);
        MalumItemModelSmithTypes.SPELLWEAVING_TOOL.act(data, MalumContent.Gear.SPELLWEAVING_PICKAXE, MalumContent.Gear.SPELLWEAVING_AXE);
        WeaponItemModelSmithTypes.VINDICTIVE_BRAND.act(data, MalumContent.Gear.VINDICTIVE_BRAND);
        MalumItemModelSmithTypes.CATALYST_LOBBER.act(data, MalumContent.Gear.CATALYST_LOBBER);
        MalumItemModelSmithTypes.POUCH.act(data, MalumContent.Gear.SOULWOVEN_POUCH, MalumContent.Gear.RAVENOUS_POUCH);

        MalumItemModelSmithTypes.HANDHELD_ITEM.act(data, DiggerItem.class);
        MalumItemModelSmithTypes.HANDHELD_ITEM.act(data, SwordItem.class);

        MalumItemModelSmithTypes.SKIN_APPLICABLE_ARMOR_ITEM.act(data,
                MalumContent.Gear.SOUL_HUNTER_CLOAK, MalumContent.Gear.SOUL_HUNTER_ROBE, MalumContent.Gear.SOUL_HUNTER_LEGGINGS, MalumContent.Gear.SOUL_HUNTER_BOOTS,
                MalumContent.Gear.SOUL_STAINED_STEEL_HELMET, MalumContent.Gear.SOUL_STAINED_STEEL_CHESTPLATE, MalumContent.Gear.SOUL_STAINED_STEEL_LEGGINGS, MalumContent.Gear.SOUL_STAINED_STEEL_BOOTS,
                MalumContent.Gear.MALIGNANT_STRONGHOLD_HELMET, MalumContent.Gear.MALIGNANT_STRONGHOLD_CHESTPLATE, MalumContent.Gear.MALIGNANT_STRONGHOLD_LEGGINGS, MalumContent.Gear.MALIGNANT_STRONGHOLD_BOOTS);

        WeaponItemModelSmithTypes.LARGE_GENERATED_ITEM.act(data, MalumContent.Enscription.IRON_CROWN);

        MalumItemModelSmithTypes.GENERATED_ITEM.act(data, data.allRemaining());
    }

    @Override
    public String getName() {
        return "Malum Item Models";
    }
}
