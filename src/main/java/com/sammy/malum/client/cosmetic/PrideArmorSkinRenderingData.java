package com.sammy.malum.client.cosmetic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import static com.sammy.malum.MalumMod.malumPath;
import static com.sammy.malum.common.item.cosmetic.skins.PrideArmorSkin.PRIDEWEAR;
import static com.sammy.malum.common.item.cosmetic.skins.PrideArmorSkin.SLIM_PRIDEWEAR;

public class PrideArmorSkinRenderingData extends ArmorSkinRenderingData {

    private final ResourceLocation slimTexture;
    private final ResourceLocation standardTexture;

    public PrideArmorSkinRenderingData(String type) {
        this.slimTexture = malumPath("textures/armor/cosmetic/pride/" + type + "_drip_slim.png");
        this.standardTexture = malumPath("textures/armor/cosmetic/pride/" + type + "_drip.png");
    }

    @Override
    public ResourceLocation getTexture(LivingEntity livingEntity, boolean slim) {
        return slim ? this.slimTexture : standardTexture;
    }

    @Override
    public LodestoneArmorModel getModel(LivingEntity livingEntity, boolean slim) {
        return slim ? SLIM_PRIDEWEAR : PRIDEWEAR;
    }
}
