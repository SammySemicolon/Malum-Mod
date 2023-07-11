package com.sammy.malum.client.cosmetic;

import net.minecraft.client.player.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.systems.model.*;

public abstract class ArmorSkinRenderingData {

    public abstract ResourceLocation getTexture(LivingEntity livingEntity, boolean slim);

    public abstract LodestoneArmorModel getModel(LivingEntity livingEntity, boolean slim);

    public ResourceLocation getTexture(LivingEntity livingEntity) {
        return getTexture(livingEntity, isSlim(livingEntity));
    }

    public LodestoneArmorModel getModel(LivingEntity livingEntity) {
        return getModel(livingEntity, isSlim(livingEntity));
    }

    public static boolean isSlim(LivingEntity livingEntity) {
        return livingEntity instanceof AbstractClientPlayer player && player.getModelName().equals("slim");
    }
}
