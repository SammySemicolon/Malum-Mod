package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.malum.common.entity.spirit.SoulEntity;
import com.sammy.malum.core.systems.rendering.RenderTypes;
import com.sammy.malum.core.systems.rendering.Shaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

import static com.sammy.malum.core.helper.DataHelper.prefix;
import static com.sammy.malum.core.systems.rendering.RenderManager.DELAYED_RENDER;
import static com.sammy.malum.core.systems.rendering.RenderUtilities.renderQuad;

public class SoulItemEntityRenderer extends EntityRenderer<SoulEntity>
{
    public final ItemRenderer itemRenderer;

    public SoulItemEntityRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    private static final ResourceLocation SOUL_NOISE = prefix("textures/vfx/soul_noise.png");
    public static final RenderType SOUL_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialNoiseQuadRenderType(SOUL_NOISE), ()->{
        ShaderInstance instance = Shaders.radialNoise.getInstance().get();
        instance.safeGetUniform("Speed").set(2500f);
        instance.safeGetUniform("Intensity").set(45f);
    });
    private static final ResourceLocation SECONDARY_SOUL_NOISE = prefix("textures/vfx/soul_noise_secondary.png");
    public static final RenderType SECONDARY_SOUL_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialScatterNoiseQuadRenderType(SECONDARY_SOUL_NOISE), ()->{

        ShaderInstance instance = Shaders.radialScatterNoise.getInstance().get();
        instance.safeGetUniform("Speed").set(-1500f);
        instance.safeGetUniform("ScatterPower").set(-15f);
        instance.safeGetUniform("Intensity").set(55f);
    });
    private static final ResourceLocation TRINARY_SOUL_NOISE = prefix("textures/vfx/soul_noise_trinary.png");
    public static final RenderType TRINARY_SOUL_NOISE_TYPE = RenderTypes.withShaderHandler(RenderTypes.createRadialScatterNoiseQuadRenderType(TRINARY_SOUL_NOISE), ()->{

        ShaderInstance instance = Shaders.radialScatterNoise.getInstance().get();
        instance.safeGetUniform("Speed").set(-2000f);
        instance.safeGetUniform("ScatterPower").set(20f);
        instance.safeGetUniform("Intensity").set(75f);
    });

    @Override
    public void render(SoulEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        float f1 = entityIn.getYOffset(partialTicks);

        poseStack.pushPose();
        poseStack.translate(0, 0.25+f1, 0);
        renderSoul(poseStack, entityIn.color.darker());
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(SoulEntity entity)
    {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    public static void renderSoul(PoseStack poseStack, Color color)
    {
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));

        VertexConsumer soulNoise = DELAYED_RENDER.getBuffer(SOUL_NOISE_TYPE);
        VertexConsumer secondarySoulNoise = DELAYED_RENDER.getBuffer(SECONDARY_SOUL_NOISE_TYPE);
        VertexConsumer trinarySoulNoise = DELAYED_RENDER.getBuffer(TRINARY_SOUL_NOISE_TYPE);
        float scale = 0.65f;
        renderQuad(soulNoise, poseStack, scale, scale, color.getRed(), color.getGreen(), color.getBlue(), 220, 0,0, 1, 1);
        color = color.brighter();
        scale = 0.85f;
        renderQuad(secondarySoulNoise, poseStack, scale, scale, color.getRed(), color.getGreen(), color.getBlue(), 180, 0,0, 1, 1);
        scale = 0.95f;
        renderQuad(trinarySoulNoise, poseStack, scale, scale, color.getRed(), color.getGreen(), color.getBlue(), 140, 0,0, 1, 1);
    }
}