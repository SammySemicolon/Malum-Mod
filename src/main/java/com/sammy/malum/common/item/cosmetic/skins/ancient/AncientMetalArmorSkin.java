package com.sammy.malum.common.item.cosmetic.skins.ancient;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.cosmetic.SimpleArmorSkinRenderingData;
import com.sammy.malum.client.model.cosmetic.ancient.AncientSoulStainedSteelArmorModel;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.common.item.curiosities.armor.SoulStainedSteelArmorItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;

import static com.sammy.malum.MalumMod.malumPath;

public class AncientMetalArmorSkin extends ArmorSkin {

    public static AncientSoulStainedSteelArmorModel ANCIENT_SOUL_STAINED_STEEL_ARMOR;

    public AncientMetalArmorSkin(String id, Item weaveItem) {
        super(id, SoulStainedSteelArmorItem.class, weaveItem);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        ANCIENT_SOUL_STAINED_STEEL_ARMOR = new AncientSoulStainedSteelArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(AncientSoulStainedSteelArmorModel.LAYER));

        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/ancient_soul_stained_steel.png"), ANCIENT_SOUL_STAINED_STEEL_ARMOR);
    }
}
