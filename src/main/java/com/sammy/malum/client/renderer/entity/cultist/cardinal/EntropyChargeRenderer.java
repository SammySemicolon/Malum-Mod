package com.sammy.malum.client.renderer.entity.cultist.cardinal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.renderer.entity.AbstractBoltEntityRenderer;
import com.sammy.malum.common.entity.mob.cultist.altar.projectile.CursedBoltProjectile;
import com.sammy.malum.common.entity.mob.cultist.cardinal.projectile.EntropyChargeProjectile;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.modules.rendering.LodestoneRenderingSystem;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.LodestoneRenderTypeBuilder;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

public class EntropyChargeRenderer extends AbstractBoltEntityRenderer<EntropyChargeProjectile> {

    public static final RenderTypeToken ENTROPY_CHARGE = RenderTypeToken.createToken(MalumMod.malumPath("textures/vfx/entropy_charge.png"));
    public static final RenderTypeToken ENTROPY_CHARGE_SECONDARY = RenderTypeToken.createToken(MalumMod.malumPath("textures/vfx/entropy_charge_secondary.png"));
    public static final RenderTypeToken ENTROPY_CHARGE_TRINARY = RenderTypeToken.createToken(MalumMod.malumPath("textures/vfx/entropy_charge_trinary.png"));

    public EntropyChargeRenderer(EntityRendererProvider.Context context) {
        super(context, CursedBoltProjectile.CULTIST_RED, CursedBoltProjectile.CULTIST_CRIMSON);
    }

    @Override
    public LodestoneRenderTypeBuilder getTrailRenderType(boolean isTransparent) {
        return LodestoneRenderTypes.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL).addUniformData(UniformData.LUMITRANSPARENT);
    }

    @Override
    public float getScaleMultiplier() {
        return 0.2f;
    }

    @Override
    public void render(EntropyChargeProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
        if (entity.isAwaitingSpawn()) {
            return;
        }

        var builder = VFXBuilders.createWorld().replaceBufferSource(LodestoneRenderingSystem.LATE_DEFERRED_RENDER);
        float delta = Math.min(entity.primedTime / 40f, 1) * entity.getVisualEffectScalar();
        float timeDelta = ((entity.level().getGameTime() + partialTicks) % 80L) / 80f;
        for (int i = 0; i < 6; i++) {
            float angleDelay = i * 0.75f;
            float offset = (0.8f - i * 0.1f) * delta;
            float angle = (timeDelta * 6.28f - angleDelay) % 6.28f;
            float x = Mth.sin(angle) * offset;
            float y = Mth.sin(angle) * offset*0.4f;
            float z = Mth.cos(angle) * offset;
            float falloff = (1 - i * 0.1f);
            var primaryColor = ColorHelper.darker(this.primaryColor, i);
            var secondaryColor = ColorHelper.darker(this.secondaryColor, i);
            float scale = 0.4f * delta * falloff;
            var texture = switch (Mth.floor(i/2f)) {
                case 0 -> ENTROPY_CHARGE;
                case 1 -> ENTROPY_CHARGE_SECONDARY;
                default -> ENTROPY_CHARGE_TRINARY;
            };

            poseStack.pushPose();
            poseStack.translate(x, y, z);
            poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            for (int j = 0; j < 2; j++) {
                float alpha = (j == 1 ? 0.3f : 1.0f) * falloff;
                var renderType = j == 1 ? LodestoneRenderTypes.ADDITIVE_DISTORTED_TEXTURE : LodestoneRenderTypes.TRANSPARENT_DISTORTED_TEXTURE;
                var wawawawa = renderType.apply(texture).addUniformData(UniformData.LUMITRANSPARENT);

                builder.setRenderType(wawawawa)
                        .setColor(primaryColor, 0.8f * alpha).renderQuad(poseStack, scale)
                        .setColor(secondaryColor, 0.6f * alpha).renderQuad(poseStack, scale)
                        .setColor(secondaryColor, 0.4f * alpha).renderQuad(poseStack, scale);
            }
            poseStack.popPose();
        }
    }
}