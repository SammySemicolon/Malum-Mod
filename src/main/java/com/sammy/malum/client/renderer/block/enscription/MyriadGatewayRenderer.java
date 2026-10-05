package com.sammy.malum.client.renderer.block.enscription;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.common.block.curiosities.escription.MyriadGatewayBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import team.lodestar.lodestone.modules.toolkit.inventory.display.ItemStackDisplayDataRenderer;

public class MyriadGatewayRenderer implements BlockEntityRenderer<MyriadGatewayBlockEntity> {

    public MyriadGatewayRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(MyriadGatewayBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        var renderer = new ItemStackDisplayDataRenderer();
        renderer.render(blockEntityIn.inventory, poseStack, bufferIn, combinedLightIn, partialTicks);
    }

    @Override
    public AABB getRenderBoundingBox(MyriadGatewayBlockEntity blockEntityIn) {
        var pos = blockEntityIn.getBlockPos();
        return new AABB(pos.getX() - 6, pos.getY() - 2, pos.getZ() - 6, pos.getX() + 7, pos.getY() + 3, pos.getZ() + 7);
    }
}