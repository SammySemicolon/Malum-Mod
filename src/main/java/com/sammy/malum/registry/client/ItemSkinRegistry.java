package com.sammy.malum.registry.client;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.item.ItemSkin;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.sammy.malum.MalumMod.malumPath;

@Mod.EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemSkinRegistry {

    public static final Map<String, ItemSkin> SKINS = new LinkedHashMap<>();

    @SubscribeEvent
    public static void registerItemSkins(FMLClientSetupEvent event) {
        registerPridewear("ace");
        registerPridewear("agender");
        registerPridewear("aro");
        registerPridewear("aroace");
        registerPridewear("bi");
        registerPridewear("demiboy");
        registerPridewear("demigirl");
        registerPridewear("enby");
        registerPridewear("gay");
        registerPridewear("genderfluid");
        registerPridewear("genderqueer");
        registerPridewear("intersex");
        registerPridewear("lesbian");
        registerPridewear("pan");
        registerPridewear("plural");
        registerPridewear("poly");
        registerPridewear("pride");
        registerPridewear("trans");

        registerSkin("executioner_drip", p -> malumPath("textures/armor/cosmetic/starstorm_executioner.png"), (p) -> ModelRegistry.EXECUTIONER)
                .addDatagenData(() -> new ItemSkin.DatagenData(malumPath("item/cosmetic/armor_icons/executioner_"),
                                malumPath("models/item/executioner_"),
                                List.of("boots", "leggings", "chestplate", "visor")));

        registerSkin("commando_drip", p -> malumPath("textures/armor/cosmetic/commando.png"), (p) -> ModelRegistry.COMMANDO)
                .addDatagenData(() -> new ItemSkin.DatagenData(malumPath("item/cosmetic/armor_icons/commando_"),
                                malumPath("models/item/commando_"),
                                List.of("boots", "leggings", "chestplate", "visor")));

        registerSkin("ancient_cloth", p -> malumPath("textures/armor/cosmetic/ancient_soul_hunter.png"), (p) -> ModelRegistry.ANCIENT_SOUL_HUNTER_ARMOR)
                .addDatagenData(() -> new ItemSkin.DatagenData(malumPath("item/cosmetic/armor_icons/ancient_soul_hunter_"),
                        malumPath("models/item/ancient_soul_hunter_"),
                        List.of("boots", "leggings", "robe", "cloak")));

        registerSkin("ancient_metal", p -> malumPath("textures/armor/cosmetic/ancient_soul_stained_steel.png"), (p) -> ModelRegistry.ANCIENT_SOUL_STAINED_STEEL_ARMOR)
                .addDatagenData(() -> new ItemSkin.DatagenData(malumPath("item/cosmetic/armor_icons/ancient_soul_stained_steel_"),
                        malumPath("models/item/ancient_soul_hunter_"),
                        List.of("boots", "leggings", "chestplate", "helmet")));
    }

    public static ItemSkin getSkin(ItemStack stack) {
        return stack.hasTag() ? SKINS.get(stack.getTag().getString(ItemSkin.MALUM_SKIN_TAG)) : null;
    }

    public static ItemSkin registerSkin(String tag, ItemSkin skin) {
        SKINS.put(tag, skin);
        return skin;
    }

    public static ItemSkin registerSkin(String tag, Function<LivingEntity, ResourceLocation> armorTextureLocation, Function<LivingEntity, LodestoneArmorModel> model) {
        return registerSkin(tag, new ItemSkin(tag, armorTextureLocation, model, SKINS.size()));
    }

    public static void registerPridewear(String tag) {
        String drip = tag + "_drip";
        String armorIconPath = "item/cosmetic/armor_icons/pride/";
        String armorSheetPath = "armor/cosmetic/pride/";
        ItemSkin skin = registerSkin(drip, p -> {
            if (p instanceof AbstractClientPlayer clientPlayer && clientPlayer.getModelName().equals("slim")) {
                return malumPath("textures/" + armorSheetPath + drip + "_slim.png");
            }
            return malumPath("textures/" + armorSheetPath + drip + ".png");
        }, (p) -> {
            if (p instanceof AbstractClientPlayer clientPlayer) {
                return clientPlayer.getModelName().equals("slim") ? ModelRegistry.SLIM_PRIDEWEAR : ModelRegistry.PRIDEWEAR;
            }
            return ModelRegistry.PRIDEWEAR;
        });
        skin.addDatagenData(() -> new ItemSkin.DatagenData(malumPath(armorIconPath + tag + "_"), malumPath("models/item/pridewear/armor_icons/" + tag + "_"), List.of("socks", "shorts", "hoodie", "beanie")));
    }
}
