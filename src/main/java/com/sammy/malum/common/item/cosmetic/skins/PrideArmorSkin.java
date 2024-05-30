package com.sammy.malum.common.item.cosmetic.skins;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.cosmetic.PrideArmorSkinRenderingData;
import com.sammy.malum.client.model.cosmetic.pride.PridewearArmorModel;
import com.sammy.malum.client.model.cosmetic.pride.SlimPridewearArmorModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

public class PrideArmorSkin extends ArmorSkin {

    public static PridewearArmorModel PRIDEWEAR;
    public static SlimPridewearArmorModel SLIM_PRIDEWEAR;

    public PrideArmorSkin(String id, Class<? extends LodestoneArmorItem> validArmorClass, Item weaveItem) {
        super(id, validArmorClass, weaveItem);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        PRIDEWEAR = new PridewearArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(PridewearArmorModel.LAYER));
        SLIM_PRIDEWEAR = new SlimPridewearArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(SlimPridewearArmorModel.LAYER));

        return new PrideArmorSkinRenderingData(id);
    }
}
