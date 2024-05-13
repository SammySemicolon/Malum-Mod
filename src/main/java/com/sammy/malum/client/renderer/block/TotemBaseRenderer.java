package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.*;
import net.minecraft.client.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;

import static com.sammy.malum.client.RenderUtils.*;


public class TotemBaseRenderer implements BlockEntityRenderer<TotemBaseBlockEntity> {

    public static final ResourceLocation AREA_COVERAGE_TEXTURE = MalumMod.malumPath("textures/vfx/area_coverage.png");
    public static final ResourceLocation LARGE_AREA_COVERAGE_TEXTURE = MalumMod.malumPath("textures/vfx/area_coverage_large.png");

    private static float totemicStaffHeldTimer = 0;
    private static boolean isHoldingStaff;

    public TotemBaseRenderer(BlockEntityRendererProvider.Context context) {
    }

    public static void checkForTotemicStaff(TickEvent.ClientTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.START)) {
            final LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }
            final Item totemicStaff = ItemRegistry.TOTEMIC_STAFF.get();
            if ((player.getMainHandItem().getItem().equals(totemicStaff) || player.getOffhandItem().getItem().equals(totemicStaff))) {
                if (TotemBaseRenderer.totemicStaffHeldTimer < 20) {
                    TotemBaseRenderer.totemicStaffHeldTimer++;
                }
                isHoldingStaff = true;
            } else if (TotemBaseRenderer.totemicStaffHeldTimer > 0) {
                TotemBaseRenderer.totemicStaffHeldTimer--;
                isHoldingStaff = false;
            }
        }
    }

    @Override
    public void render(TotemBaseBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (totemicStaffHeldTimer > 0f) {
            if (blockEntityIn.cachedRadiusRite == null) {
                return;
            }
            float staffTimer = Mth.clamp((totemicStaffHeldTimer + (isHoldingStaff ? 1 : -1) * partialTicks), 0, 20);
            float totemTimer = Mth.clamp((blockEntityIn.radiusVisibility + (blockEntityIn.isActiveOrAssembling() ? 1 : -1) * partialTicks), 0, 40);
            float scalar = Easing.SINE_IN_OUT.ease(staffTimer / 20f, 0, 1, 1) * Easing.SINE_IN_OUT.ease(totemTimer / 40f, 0, 1, 1);
            MalumSpiritType spiritType = blockEntityIn.cachedRadiusRite.getIdentifyingSpirit();
            TotemicRiteEffect riteEffect = blockEntityIn.cachedRadiusRite.getRiteEffect(blockEntityIn.isSoulwood);
            BlockPos riteEffectCenter = riteEffect.getRiteEffectCenter(blockEntityIn);
            BlockPos offset = riteEffectCenter.subtract(blockEntityIn.getBlockPos());

            poseStack.pushPose();
            var builder = SpiritBasedWorldVFXBuilder.create(spiritType)
                    .setRenderType(RenderTypeRegistry.ADDITIVE_DISTORTED_TEXTURE.applyWithModifierAndCache((riteEffect.category.equals(TotemicRiteEffect.MalumRiteEffectCategory.AURA) || riteEffect.category.equals(TotemicRiteEffect.MalumRiteEffectCategory.RADIAL_BLOCK_EFFECT)) ? LARGE_AREA_COVERAGE_TEXTURE : AREA_COVERAGE_TEXTURE, b -> b.setCullState(LodestoneRenderTypeRegistry.NO_CULL)))
                    .setColor(spiritType.getPrimaryColor(), 0.85f * scalar);

            poseStack.translate(offset.getX(), offset.getY(), offset.getZ());

            int width = riteEffect.getRiteEffectHorizontalRadius();
            if (width > 1) {
                width = width * 2 + 1;
            }
            int height = riteEffect.getRiteEffectVerticalRadius();
            if (height > 1) {
                height = height * 2 + 1;
            }
            RenderUtils.CubeVertexData cubeVertexData = RenderUtils.makeCubePositions(width, height)
                    .applyWobble(0, 0.5f, 0.01f);
            RenderUtils.CubeVertexData inverse = RenderUtils.makeCubePositions(-width, -height)
                    .applyWobble(0, 0.5f, 0.01f);

            drawCube(poseStack, builder, 1.05f, cubeVertexData);
            builder.setColor(spiritType.getSecondaryColor(), 0.6f * scalar);
            drawCube(poseStack, builder, 1.05f, inverse);

            poseStack.popPose();
        }
    }
}
