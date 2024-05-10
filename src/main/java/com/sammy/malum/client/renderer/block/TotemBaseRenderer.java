package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.RenderUtils;
import com.sammy.malum.client.SpiritBasedWorldVFXBuilder;
import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.spiritrite.TotemicRiteEffect;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;

import static com.sammy.malum.client.RenderUtils.drawCube;


public class TotemBaseRenderer implements BlockEntityRenderer<TotemBaseBlockEntity> {

    public static final ResourceLocation AREA_COVERAGE_TEXTURE = MalumMod.malumPath("textures/vfx/area_coverage.png");


    public TotemBaseRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TotemBaseBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (blockEntityIn.rite == null) {
            return;
        }
        MalumSpiritType spiritType = blockEntityIn.rite.getIdentifyingSpirit();
        TotemicRiteEffect riteEffect = blockEntityIn.rite.getRiteEffect(blockEntityIn.corrupted);
        BlockPos riteEffectCenter = riteEffect.getRiteEffectCenter(blockEntityIn);
        BlockPos offset = riteEffectCenter.subtract(blockEntityIn.getBlockPos());
        Level level = Minecraft.getInstance().level;

        poseStack.pushPose();
        var builder = SpiritBasedWorldVFXBuilder.create(spiritType)
                .setRenderType(LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(AREA_COVERAGE_TEXTURE));

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

        drawCube(poseStack, builder.setColor(spiritType.getPrimaryColor(), 0.85f), 1f, cubeVertexData);
        drawCube(poseStack, builder.setColor(spiritType.getSecondaryColor(), 0.6f), 1f, inverse);

        poseStack.popPose();
    }
}
