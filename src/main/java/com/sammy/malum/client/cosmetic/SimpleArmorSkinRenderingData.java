package com.sammy.malum.client.cosmetic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

public class SimpleArmorSkinRenderingData extends ArmorSkinRenderingData {

    private final ResourceLocation texture;
    private final LodestoneArmorModel model;

    public SimpleArmorSkinRenderingData(ResourceLocation texture, LodestoneArmorModel model) {
        this.texture = texture;
        this.model = model;
    }

    @Override
    public ResourceLocation getTexture(LivingEntity livingEntity, boolean slim) {
        return texture;
    }

    @Override
    public LodestoneArmorModel getModel(LivingEntity livingEntity, boolean slim) {
        return model;
    }
}
