package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.RenderUtils;
import com.sammy.malum.client.SpiritBasedWorldVFXBuilder;
import com.sammy.malum.common.block.mana_mote.MoteOfManaBlockEntity;
import com.sammy.malum.common.block.mana_mote.SpiritMoteBlock;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

import static com.sammy.malum.client.RenderUtils.drawCube;


public class MoteOfManaRenderer implements BlockEntityRenderer<MoteOfManaBlockEntity> {

    public static final RenderTypeToken MOTE_OF_MANA = RenderTypeToken.createToken(MalumMod.malumPath("textures/block/mote_of_mana.png"));

    public MoteOfManaRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MoteOfManaBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        poseStack.pushPose();
        MalumSpiritType spiritType = ((SpiritMoteBlock) blockEntityIn.getBlockState().getBlock()).spiritType;

        var builder = SpiritBasedWorldVFXBuilder.create(spiritType)
                .setRenderType(LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(MOTE_OF_MANA));

        RenderUtils.CubeVertexData cubeVertexData = RenderUtils.makeCubePositions(1f)
                .applyWobble(0, 0.5f, 0.015f);
        RenderUtils.CubeVertexData inverse = RenderUtils.makeCubePositions(-1f)
                .applyWobble(0, 0.5f, 0.015f);
        drawCube(poseStack, builder.setColor(spiritType.getPrimaryColor(), 0.85f), 1f, cubeVertexData);
        drawCube(poseStack, builder.setColor(spiritType.getPrimaryColor(), 0.5f), 1.12f, cubeVertexData);

        drawCube(poseStack, builder.setColor(spiritType.getSecondaryColor(), 0.6f), 0.92f, inverse);
        poseStack.popPose();
    }
}