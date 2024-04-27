package com.sammy.malum.client.renderer.entity.bolt;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.bolt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import team.lodestar.lodestone.registry.client.*;

import java.awt.*;

import static com.sammy.malum.MalumMod.*;

public abstract class AbstractBoltEntityRenderer<T extends AbstractBoltProjectileEntity> extends EntityRenderer<T> {
    public final Color primaryColor;
    public final Color secondaryColor;
    public AbstractBoltEntityRenderer(EntityRendererProvider.Context context, Color primaryColor, Color secondaryColor) {
        super(context);
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    protected static final ResourceLocation LIGHT_TRAIL = malumPath("textures/vfx/concentrated_trail.png");
    private static final RenderType TRAIL_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.apply(LIGHT_TRAIL);

    public RenderType getTrailRenderType() {
        return TRAIL_TYPE;
    }

    public float getAlphaMultiplier() {
        return 1f;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        if (entity.spawnDelay > 0) {
            return;
        }
        float effectScalar = entity.getVisualEffectScalar();
        final float alphaScalar = effectScalar * getAlphaMultiplier();
        RenderUtils.renderEntityTrail(entity, entity.trailPointBuilder, getTrailRenderType(), poseStack, primaryColor, secondaryColor, effectScalar, alphaScalar, partialTicks);
        RenderUtils.renderEntityTrail(entity, entity.spinningTrailPointBuilder, getTrailRenderType(), poseStack, primaryColor, secondaryColor, effectScalar, alphaScalar, partialTicks);
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
