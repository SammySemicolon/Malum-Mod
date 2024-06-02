package com.sammy.malum.client.renderer.entity.bolt;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.RenderUtils;
import com.sammy.malum.common.entity.bolt.AbstractBoltProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

import java.awt.*;

import static com.sammy.malum.MalumMod.malumPath;

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

    private static final RenderType TRAIL_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.apply(RenderTypeToken.createCachedToken(malumPath("textures/vfx/concentrated_trail.png")));

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
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setRenderType(getTrailRenderType());
        RenderUtils.renderEntityTrail(poseStack, builder, entity.trailPointBuilder, entity, primaryColor, secondaryColor, effectScalar, alphaScalar, partialTicks);
        RenderUtils.renderEntityTrail(poseStack, builder, entity.spinningTrailPointBuilder, entity, primaryColor, secondaryColor, effectScalar, alphaScalar, partialTicks);
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
