package com.sammy.malum.common.item.cosmetic.skins.ancient;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.cosmetic.SimpleArmorSkinRenderingData;
import com.sammy.malum.client.model.cosmetic.ancient.AncientSoulHunterArmorModel;
import com.sammy.malum.common.entity.EntityModelLoader;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.common.item.curiosities.armor.SoulHunterArmorItem;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static com.sammy.malum.MalumMod.malumPath;

public class AncientClothArmorSkin extends ArmorSkin {

    public static AncientSoulHunterArmorModel ANCIENT_SOUL_HUNTER_ARMOR;

    public AncientClothArmorSkin(String id, Item weaveItem) {
        super(id, SoulHunterArmorItem.class, weaveItem);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        ANCIENT_SOUL_HUNTER_ARMOR = new AncientSoulHunterArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(AncientSoulHunterArmorModel.LAYER));

        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/ancient_soul_hunter.png"), ANCIENT_SOUL_HUNTER_ARMOR);
    }
}
