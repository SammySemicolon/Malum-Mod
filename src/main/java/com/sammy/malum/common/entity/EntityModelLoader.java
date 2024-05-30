package com.sammy.malum.common.entity;

import com.sammy.malum.client.model.HeadOverlayModel;
import com.sammy.malum.client.model.TailModel;
import com.sammy.malum.client.model.TopHatModel;
import com.sammy.malum.client.model.cosmetic.GenericArmorModel;
import com.sammy.malum.client.model.cosmetic.GenericSlimArmorModel;
import com.sammy.malum.client.model.cosmetic.ScarfModel;
import com.sammy.malum.client.model.cosmetic.ancient.AncientSoulHunterArmorModel;
import com.sammy.malum.client.model.cosmetic.ancient.AncientSoulStainedSteelArmorModel;
import com.sammy.malum.client.model.cosmetic.pride.PridewearArmorModel;
import com.sammy.malum.client.model.cosmetic.pride.SlimPridewearArmorModel;
import com.sammy.malum.client.model.cosmetic.risky.CommandoArmorModel;
import com.sammy.malum.client.model.cosmetic.risky.ExecutionerArmorModel;
import com.sammy.malum.client.model.cosmetic.ultrakill.UltrakillMachineArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
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
