package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.sammy.malum.common.blockentity.totem.TotemPoleTileEntity;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.helper.RenderHelper;
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

import static com.sammy.malum.core.helper.RenderHelper.FULL_BRIGHT;


public class TotemPoleRenderer implements BlockEntityRenderer<TotemPoleTileEntity> {
    public static final Color SOULWOOD = new Color(15, 7, 17);
    public static final Color RUNEWOOD = new Color(12, 8, 7);

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
        renderQuad(overlayHashmap.get(blockEntityIn.type), color(blockEntityIn), direction, poseStack, bufferIn);
    }

    public Color color(TotemPoleTileEntity totemPoleTileEntity) {
        Color color1 = totemPoleTileEntity.corrupted ? SOULWOOD : RUNEWOOD;
        Color color2 = totemPoleTileEntity.type.color;
        int red = (int) Mth.lerp(totemPoleTileEntity.currentColor / 20f, color1.getRed(), color2.getRed());
        int green = (int) Mth.lerp(totemPoleTileEntity.currentColor / 20f, color1.getGreen(), color2.getGreen());
        int blue = (int) Mth.lerp(totemPoleTileEntity.currentColor / 20f, color1.getBlue(), color2.getBlue());
        return new Color(red, green, blue);
    }

    public void renderQuad(Material material, Color color, Direction direction, PoseStack poseStack, MultiBufferSource bufferIn) {
        TextureAtlasSprite sprite = material.sprite();
        VertexConsumer consumer = material.buffer(bufferIn, r -> RenderType.cutout());
        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation(direction)));
        poseStack.translate(-0.5, 0, -0.5);
        RenderHelper.create()
                .setColor(color)
                .setLight(FULL_BRIGHT)
                .setUV(sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1())
                .renderQuad(consumer, poseStack, 1);

        poseStack.popPose();
    }

    public float rotation(Direction direction) {
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            direction = direction.getOpposite();
        }
        return 90 + direction.toYRot();
    }
}