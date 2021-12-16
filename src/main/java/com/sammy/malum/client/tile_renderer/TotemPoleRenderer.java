package com.sammy.malum.client.tile_renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.common.tile.TotemPoleTileEntity;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
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
    public static HashMap<MalumSpiritType, Material> cutoutHashmap = new HashMap<>();
    public static HashMap<MalumSpiritType, Material> corruptedCutoutHashmap = new HashMap<>();
    public static final Material RUNEWOOD_LOG = new Material(TextureAtlas.LOCATION_BLOCKS, MalumHelper.prefix("block/runewood_log"));

    public TotemPoleRenderer(BlockEntityRendererProvider.Context context) {
        SpiritTypeRegistry.SPIRITS.forEach(s ->
                {
                    overlayHashmap.put(s, new Material(TextureAtlas.LOCATION_BLOCKS, s.overlayTexture()));
                    cutoutHashmap.put(s, new Material(TextureAtlas.LOCATION_BLOCKS, s.runewoodCutoutTexture()));
                    corruptedCutoutHashmap.put(s, new Material(TextureAtlas.LOCATION_BLOCKS, s.soulwoodCutoutTexture()));
                }
        );
    }

    @Override
    public void render(TotemPoleTileEntity tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = tileEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (direction.equals(Direction.WEST) || direction.equals(Direction.EAST)) {
            combinedLightIn -= combinedOverlayIn * 2;
        }
        if (tileEntityIn.type == null) {
            renderQuad(RUNEWOOD_LOG, Color.WHITE, combinedLightIn, direction, poseStack, bufferIn);
            return;
        }
        if (((TotemPoleBlock) tileEntityIn.getBlockState().getBlock()).corrupted) {
            renderQuad(corruptedCutoutHashmap.get(tileEntityIn.type), Color.WHITE, combinedLightIn, direction, poseStack, bufferIn);
        } else {
            renderQuad(cutoutHashmap.get(tileEntityIn.type), Color.WHITE, combinedLightIn, direction, poseStack, bufferIn);
        }
        renderQuad(overlayHashmap.get(tileEntityIn.type), color(tileEntityIn), combinedLightIn, direction, poseStack, bufferIn);
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
        add(builder, poseStack, color, light, 1f, 1, 1, sprite.getU0(), sprite.getV0());
        add(builder, poseStack, color, light, 1f, 0, 1, sprite.getU0(), sprite.getV1());
        add(builder, poseStack, color, light, 1f, 0, 0, sprite.getU1(), sprite.getV1());
        add(builder, poseStack, color, light, 1f, 1, 0, sprite.getU1(), sprite.getV0());

        poseStack.popPose();
    }

    public float rotation(Direction direction) {
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            direction = direction.getOpposite();
        }
        return 90 + direction.toYRot();
    }

    private void add(VertexConsumer renderer, PoseStack stack, Color color, int light, float x, float y, float z, float u, float v) {
        renderer.vertex(stack.last().pose(), x, y, z).color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1).uv(u, v).uv2(light & '\uffff', light >> 16 & '\uffff').normal(1, 0, 0).endVertex();
    }
}