package com.sammy.malum.client.cosmetic;

import com.sammy.malum.registry.client.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.systems.model.*;

import static com.sammy.malum.MalumMod.*;

public class PrideArmorSkinRenderingData extends ArmorSkinRenderingData {

    private final ResourceLocation slimTexture;
    private final ResourceLocation standardTexture;

    public PrideArmorSkinRenderingData(String type) {
        this.slimTexture = malumPath("textures/armor/cosmetic/pride/" + type + "_slim.png");
        this.standardTexture = malumPath("textures/armor/cosmetic/pride/" + type + ".png");
    }
    @Override
    public ResourceLocation getTexture(LivingEntity livingEntity, boolean slim) {
        return slim ? this.slimTexture : standardTexture;
    }

    @Override
    public LodestoneArmorModel getModel(LivingEntity livingEntity, boolean slim) {
        return slim ? ModelRegistry.SLIM_PRIDEWEAR : ModelRegistry.PRIDEWEAR;
    }
}
