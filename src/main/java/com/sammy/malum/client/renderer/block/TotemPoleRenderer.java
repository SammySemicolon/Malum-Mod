package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.sammy.malum.common.blockentity.totem.TotemPoleTileEntity;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.awt.*;
import java.util.HashMap;


public class TotemPoleRenderer implements BlockEntityRenderer<TotemPoleTileEntity> {
    public static HashMap<MalumSpiritType, Material> overlayHashmap = new HashMap<>();

    public TotemPoleRenderer(BlockEntityRendererProvider.Context context) {
        SpiritTypeRegistry.SPIRITS.forEach(s ->
                overlayHashmap.put(s, new Material(TextureAtlas.LOCATION_BLOCKS, s.getOverlayTexture()))
        );
    }

    @Override
    public void render(TotemPoleTileEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = blockEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (blockEntityIn.type == null) {
            return;
        }
        renderQuad(overlayHashmap.get(blockEntityIn.type), color(blockEntityIn), combinedLightIn, direction, poseStack, bufferIn);
    }

    public Color color(TotemPoleTileEntity totemPoleTileEntity) {
        Color color1 = new Color(12, 8, 7);
        Color color2 = totemPoleTileEntity.type.color;
        int red = (int) Mth.lerp(totemPoleTileEntity.currentColor / 20f, color1.getRed(), color2.getRed());
        int green = (int) Mth.lerp(totemPoleTileEntity.currentColor / 20f, color1.getGreen(), color2.getGreen());
        int blue = (int) Mth.lerp(totemPoleTileEntity.currentColor / 20f, color1.getBlue(), color2.getBlue());
        return new Color(red, green, blue);
    }

    public void renderQuad(Material material, Color color, int light, Direction direction, PoseStack poseStack, MultiBufferSource bufferIn) {
        poseStack.pushPose();
        TextureAtlasSprite sprite = material.sprite();
        VertexConsumer builder = material.buffer(bufferIn, r -> RenderType.cutout());
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation(direction)));
        poseStack.translate(-0.5, 0, -0.5);
        Matrix4f last = poseStack.last().pose();
        RenderUtilities.vertex(builder, last, 1.0001f, 1, 1,color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha(),sprite.getU0(), sprite.getV0(), 15728880, 1, 0,0);
        RenderUtilities.vertex(builder, last, 1.0001f, 0, 1,color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha(),sprite.getU0(), sprite.getV1(), 15728880, 1, 0,0);
        RenderUtilities.vertex(builder, last, 1.0001f, 0, 0,color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha(),sprite.getU1(), sprite.getV1(), 15728880, 1, 0,0);
        RenderUtilities.vertex(builder, last, 1.0001f, 1, 0,color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha(),sprite.getU1(), sprite.getV0(), 15728880, 1, 0,0);

        poseStack.popPose();
    }

    public float rotation(Direction direction) {
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            direction = direction.getOpposite();
        }
        return 90 + direction.toYRot();
    }
}