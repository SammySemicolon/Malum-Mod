package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.malum.common.entity.spirit.SoulEntity;
import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.helper.RenderHelper;
import com.sammy.malum.core.systems.rendering.RenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

import static com.sammy.malum.core.handlers.RenderHandler.DELAYED_RENDER;
import static com.sammy.malum.core.helper.DataHelper.prefix;
import static com.sammy.malum.core.helper.RenderHelper.FULL_BRIGHT;
import static com.sammy.malum.core.systems.rendering.RenderTypes.queueUniformChanges;

public class SoulEntityRenderer extends EntityRenderer<SoulEntity> {
    public final ItemRenderer itemRenderer;

    public SoulEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    private static final ResourceLocation SOUL_NOISE = prefix("textures/vfx/soul_noise.png");
    private static final RenderType SOUL_NOISE_TYPE = RenderTypes.RADIAL_NOISE.apply(SOUL_NOISE);
    private static final ResourceLocation SECONDARY_SOUL_NOISE = prefix("textures/vfx/soul_noise_secondary.png");
    private static final RenderType SECONDARY_SOUL_NOISE_TYPE = RenderTypes.RADIAL_SCATTER_NOISE.apply(SECONDARY_SOUL_NOISE);
    private static final ResourceLocation TRINARY_SOUL_NOISE = prefix("textures/vfx/soul_noise_trinary.png");
    private static final RenderType TRINARY_SOUL_NOISE_TYPE = RenderTypes.RADIAL_SCATTER_NOISE.apply(TRINARY_SOUL_NOISE);

    @Override
    public void render(SoulEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        poseStack.translate(0, 0.25 + entityIn.getYOffset(partialTicks), 0);
        renderSoul(poseStack, entityIn.color.darker());
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(SoulEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    public static void renderSoul(PoseStack poseStack, Color color) {
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));

        VertexConsumer soulNoise = DELAYED_RENDER.getBuffer(queueUniformChanges(SOUL_NOISE_TYPE,
                (instance) -> {
                    instance.safeGetUniform("Speed").set(2500f);
                    instance.safeGetUniform("Intensity").set(25f);
                }));

        VertexConsumer secondarySoulNoise = DELAYED_RENDER.getBuffer(queueUniformChanges(SECONDARY_SOUL_NOISE_TYPE,
                (instance -> {
                    instance.safeGetUniform("Speed").set(-1500f);
                    instance.safeGetUniform("ScatterPower").set(-20f);
                    instance.safeGetUniform("Intensity").set(35f);
                })));
        VertexConsumer trinarySoulNoise = DELAYED_RENDER.getBuffer(queueUniformChanges(TRINARY_SOUL_NOISE_TYPE,
                (instance -> {
                    instance.safeGetUniform("Speed").set(-2000f);
                    instance.safeGetUniform("ScatterPower").set(30f);
                    instance.safeGetUniform("Intensity").set(55f);
                })));

        RenderHelper.create()
                .setColor(color.brighter())
                .setAlpha(1)
                .setLight(FULL_BRIGHT)
                .renderQuad(soulNoise, poseStack, 0.6f)
                .setColor(ColorHelper.brighter(color, 2))
                .renderQuad(secondarySoulNoise, poseStack, 0.7f)
                .renderQuad(trinarySoulNoise, poseStack, 0.8f);
    }
}