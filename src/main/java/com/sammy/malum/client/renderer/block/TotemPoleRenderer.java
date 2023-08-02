package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.*;
import org.joml.*;
import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlockEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class TotemPoleRenderer implements BlockEntityRenderer<TotemPoleBlockEntity> {
    public static Map<MalumSpiritType, Material> overlayHashmap = new HashMap<>();

    public TotemPoleRenderer(BlockEntityRendererProvider.Context context) {
        SpiritTypeRegistry.SPIRITS.forEach((s, t) ->
                overlayHashmap.put(t, new Material(TextureAtlas.LOCATION_BLOCKS, t.getTotemGlowTexture()))
        );
    }

    @Override
    public void render(TotemPoleBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = blockEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (blockEntityIn.type == null) {
            return;
        }
        renderQuad(overlayHashmap.get(blockEntityIn.type), blockEntityIn.type.getPrimaryColor(), blockEntityIn.currentColor/20f, direction, poseStack);
    }

    public void renderQuad(Material material, Color color, float alpha, Direction direction, PoseStack poseStack) {
        TextureAtlasSprite sprite = material.sprite();
        VertexConsumer consumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.ADDITIVE_BLOCK);

        Vector3f[] positions = new Vector3f[]{new Vector3f(0, 0, 2.01f), new Vector3f(2, 0, 2.01f), new Vector3f(2, 2, 2.01f), new Vector3f(0, 2, 2.01f)};

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
        poseStack.translate(-0.5f, -0.5f, -0.5f);
        VFXBuilders.createWorld()
                .setPosColorTexLightmapDefaultFormat()
                .setColor(color, alpha)
                .setUV(sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1())
                .renderQuad(consumer, poseStack, positions, 0.5f);
        poseStack.popPose();
    }
}
