package com.sammy.malum.client.renderer.block.enscription;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sammy.malum.common.block.curiosities.escription.MyriadGatewayBlockEntity;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import team.lodestar.lodestone.modules.rendering.LodestoneRenderingSystem;
import team.lodestar.lodestone.modules.toolkit.inventory.display.ItemStackDisplayDataRenderer;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.builder.WorldVFXBuilder;

public class MyriadGatewayRenderer implements BlockEntityRenderer<MyriadGatewayBlockEntity> {

    public MyriadGatewayRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(MyriadGatewayBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        var renderer = new ItemStackDisplayDataRenderer();
        renderer.render(blockEntityIn.inventory, poseStack, bufferIn, combinedLightIn, partialTicks);

        int layers = 32;
        float width = MyriadGatewayBlockEntity.TUNNEL_WIDTH;
        float height = MyriadGatewayBlockEntity.TUNNEL_HEIGHT;
        float depth = MyriadGatewayBlockEntity.TUNNEL_DEPTH;
        float step = depth / layers;

        var vertices = new Vector3f[]{new Vector3f(-width, height, 0), new Vector3f(width, height, 0), new Vector3f(width, -height, 0), new Vector3f(-width, -height, 0)};

        WorldVFXBuilder builder = VFXBuilders.createWorld();
        poseStack.pushPose();
        poseStack.translate(0.5f, height / 2f + 0.01f, 0.5f);
        poseStack.translate(0f, 0f, MyriadGatewayBlockEntity.TUNNEL_OFFSET);

        var vignette = LodestoneRenderTypes.TRANSPARENT_TEXTURE.apply(MalumRenderTypeTokens.VOID_VIGNETTE);
        builder.replaceBufferSource(LodestoneRenderingSystem.LATE_DEFERRED_RENDER)
                .setRenderType(vignette);

        poseStack.pushPose();
        for (int i = 0; i < layers; i++) {
            float alpha = (i+1) / (float)layers;
            builder.setAlpha(alpha).renderQuad(poseStack, vertices);
            poseStack.translate(0, 0, step);
        }
        poseStack.popPose();
        poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(MyriadGatewayBlockEntity blockEntity) {
        var pos = blockEntity.getBlockPos();
        var tunnelArea = blockEntity.getTunnelArea();
        AABB innateArea = new AABB(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 4, pos.getY() + 4, pos.getZ() + 4);
        if (tunnelArea != null) {
            innateArea = innateArea.minmax(tunnelArea);
        }
        return innateArea;
    }
}