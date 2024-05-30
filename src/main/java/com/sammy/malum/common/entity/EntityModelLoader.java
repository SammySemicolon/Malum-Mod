package com.sammy.malum.common.entity;

import com.sammy.malum.client.model.cosmetic.GenericArmorModel;
import com.sammy.malum.client.model.cosmetic.GenericSlimArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class EntityModelLoader implements ResourceManagerReloadListener {


    public static GenericSlimArmorModel GENERIC_SLIM_ARMOR;
    public static GenericArmorModel GENERIC_ARMOR;


    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        GENERIC_SLIM_ARMOR = new GenericSlimArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(GenericSlimArmorModel.LAYER));
        GENERIC_ARMOR = new GenericArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(GenericArmorModel.LAYER));

    }
}
