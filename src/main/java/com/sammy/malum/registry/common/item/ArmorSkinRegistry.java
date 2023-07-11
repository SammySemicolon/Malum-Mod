package com.sammy.malum.registry.common.item;

import com.sammy.malum.*;
import com.sammy.malum.client.cosmetic.*;
import com.sammy.malum.common.cosmetic.*;
import com.sammy.malum.common.item.curiosities.armor.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.world.item.*;
import net.minecraftforge.data.loading.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;

import static com.sammy.malum.MalumMod.*;

@Mod.EventBusSubscriber(modid = MalumMod.MALUM, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArmorSkinRegistry {

    public static final Map<String, ArmorSkin> SKINS = new LinkedHashMap<>();
    public static Map<ArmorSkin, ArmorSkin.ArmorSkinDatagenData> SKIN_DATAGEN_DATA = new HashMap<>();

    @SubscribeEvent
    public static void wipeCache(InterModEnqueueEvent event) {
        SKIN_DATAGEN_DATA = null;
    }

    @SubscribeEvent
    public static void registerRenderingData(FMLClientSetupEvent event) {
        ClientOnly.addRenderingData();
    }

    @SubscribeEvent
    public static void registerItemSkins(FMLCommonSetupEvent event) {
        registerPridewear("ace", ItemRegistry.ACE_PRIDEWEAVE.get());
        registerPridewear("agender", ItemRegistry.AGENDER_PRIDEWEAVE.get());
        registerPridewear("aro", ItemRegistry.ARO_PRIDEWEAVE.get());
        registerPridewear("aroace", ItemRegistry.AROACE_PRIDEWEAVE.get());
        registerPridewear("bi", ItemRegistry.BI_PRIDEWEAVE.get());
        registerPridewear("demiboy", ItemRegistry.DEMIBOY_PRIDEWEAVE.get());
        registerPridewear("demigirl", ItemRegistry.DEMIGIRL_PRIDEWEAVE.get());
        registerPridewear("enby", ItemRegistry.ENBY_PRIDEWEAVE.get());
        registerPridewear("gay", ItemRegistry.GAY_PRIDEWEAVE.get());
        registerPridewear("genderfluid", ItemRegistry.GENDERFLUID_PRIDEWEAVE.get());
        registerPridewear("genderqueer", ItemRegistry.GENDERQUEER_PRIDEWEAVE.get());
        registerPridewear("intersex", ItemRegistry.INTERSEX_PRIDEWEAVE.get());
        registerPridewear("lesbian", ItemRegistry.LESBIAN_PRIDEWEAVE.get());
        registerPridewear("pan", ItemRegistry.PAN_PRIDEWEAVE.get());
        registerPridewear("plural", ItemRegistry.PLURAL_PRIDEWEAVE.get());
        registerPridewear("poly", ItemRegistry.POLY_PRIDEWEAVE.get());
        registerPridewear("pride", ItemRegistry.PRIDE_PRIDEWEAVE.get());
        registerPridewear("trans", ItemRegistry.TRANS_PRIDEWEAVE.get());

        registerItemSkin("executioner_drip",
                new ArmorSkin(LodestoneArmorItem.class, ItemRegistry.DREADED_WEAVE.get()),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/executioner_",
                        "malum:models/item/executioner_",
                        "visor", "chestplate", "leggings", "boots"
                ));

        registerItemSkin("commando_drip",
                new ArmorSkin(LodestoneArmorItem.class, ItemRegistry.CORNERED_WEAVE.get()),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/commando_",
                        "malum:models/item/commando_",
                        "visor", "chestplate", "leggings", "boots"
                ));

        registerItemSkin("ultrakill_v1",
                new ArmorSkin(LodestoneArmorItem.class, ItemRegistry.MECHANICAL_WEAVE_V1.get()),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/v1_",
                        "malum:models/item/v1_",
                        "visor", "chestplate", "leggings", "boots"
                ));

        registerItemSkin("ultrakill_v2",
                new ArmorSkin(LodestoneArmorItem.class, ItemRegistry.MECHANICAL_WEAVE_V2.get()),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/v2_",
                        "malum:models/item/v2_",
                        "visor", "chestplate", "leggings", "boots"
                ));

        registerItemSkin("ancient_cloth",
                new ArmorSkin(SoulHunterArmorItem.class, ItemRegistry.ANCIENT_WEAVE.get()),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/ancient_soul_hunter_",
                        "malum:models/item/ancient_soul_hunter_",
                        "cloak", "robe", "leggings", "boots"
                ));

        registerItemSkin("ancient_metal",
                new ArmorSkin(SoulStainedSteelArmorItem.class, ItemRegistry.ANCIENT_WEAVE.get()),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/ancient_soul_stained_steel_",
                        "malum:models/item/ancient_soul_stained_steel_",
                        "helmet", "chestplate", "leggings", "boots"
                ));
    }

    public static ArmorSkin registerItemSkin(String tag, ArmorSkin skin) {
        SKINS.put(tag, skin);
        return skin;
    }

    public static ArmorSkin registerItemSkin(String tag, ArmorSkin skin, ArmorSkin.ArmorSkinDatagenData datagenData) {
        if (DatagenModLoader.isRunningDataGen()) {
            SKIN_DATAGEN_DATA.put(skin, datagenData);
        }
        return registerItemSkin(tag, skin);
    }

    public static void registerPridewear(String tag, Item prideweave) {
        String drip = tag + "_drip";
        registerItemSkin(drip,
                new PrideArmorSkin(LodestoneArmorItem.class, prideweave),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/pride/" + tag + "_",
                        "malum:models/item/pridewear" + tag + "_",
                        "beanie", "hoodie", "shorts", "socks"
                ));
    }

    public static class ClientOnly {
        public static Map<ArmorSkin, ArmorSkinRenderingData> SKIN_RENDERING_DATA = new HashMap<>();

        public static void addRenderingData() {
            registerPridewearRenderingData("ace");
            registerPridewearRenderingData("agender");
            registerPridewearRenderingData("aro");
            registerPridewearRenderingData("aroace");
            registerPridewearRenderingData("bi");
            registerPridewearRenderingData("demiboy");
            registerPridewearRenderingData("demigirl");
            registerPridewearRenderingData("enby");
            registerPridewearRenderingData("gay");
            registerPridewearRenderingData("genderfluid");
            registerPridewearRenderingData("genderqueer");
            registerPridewearRenderingData("intersex");
            registerPridewearRenderingData("lesbian");
            registerPridewearRenderingData("pan");
            registerPridewearRenderingData("plural");
            registerPridewearRenderingData("poly");
            registerPridewearRenderingData("pride");
            registerPridewearRenderingData("trans");

            registerItemSkinRenderingData("executioner_drip", new SimpleArmorSkinRenderingData(
                    malumPath("textures/armor/cosmetic/starstorm_executioner.png"),
                    ModelRegistry.EXECUTIONER
            ));

            registerItemSkinRenderingData("commando_drip", new SimpleArmorSkinRenderingData(
                    malumPath("textures/armor/cosmetic/commando.png"),
                    ModelRegistry.COMMANDO
            ));

            registerItemSkinRenderingData("ultrakill_v1", new SimpleArmorSkinRenderingData(
                    malumPath("textures/armor/cosmetic/v1.png"),
                    ModelRegistry.ULTRAKILL_MACHINE
            ));

            registerItemSkinRenderingData("ultrakill_v2", new SimpleArmorSkinRenderingData(
                    malumPath("textures/armor/cosmetic/v2.png"),
                    ModelRegistry.ULTRAKILL_MACHINE
            ));

            registerItemSkinRenderingData("ancient_cloth", new SimpleArmorSkinRenderingData(
                    malumPath("textures/armor/cosmetic/ancient_soul_hunter.png"),
                    ModelRegistry.ANCIENT_SOUL_HUNTER_ARMOR
            ));

            registerItemSkinRenderingData("ancient_metal", new SimpleArmorSkinRenderingData(
                    malumPath("textures/armor/cosmetic/ancient_soul_stained_steel.png"),
                    ModelRegistry.ANCIENT_SOUL_STAINED_STEEL_ARMOR
            ));
        }

        public static void registerPridewearRenderingData(String tag) {
            String drip = tag + "_drip";
            registerItemSkinRenderingData(drip, new PrideArmorSkinRenderingData(drip));
        }

        public static void registerItemSkinRenderingData(String tag, ArmorSkinRenderingData data) {
            SKIN_RENDERING_DATA.put(SKINS.get(tag), data);
        }
    }
}