package com.sammy.malum.client.tile_renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.common.tile.TotemPoleTileEntity;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import java.awt.*;
import java.util.HashMap;


public class TotemPoleRenderer extends TileEntityRenderer<TotemPoleTileEntity> {
    public static HashMap<MalumSpiritType, RenderMaterial> overlayHashmap = new HashMap<>();
    public static HashMap<MalumSpiritType, RenderMaterial> cutoutHashmap = new HashMap<>();
    public static HashMap<MalumSpiritType, RenderMaterial> corruptedCutoutHashmap = new HashMap<>();
    public static final RenderMaterial RUNEWOOD_LOG = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, MalumHelper.prefix("block/runewood_log"));

    public TotemPoleRenderer(Object rendererDispatcherIn) {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
        SpiritTypeRegistry.SPIRITS.forEach(s ->
                {
                    overlayHashmap.put(s, new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, s.overlayTexture()));
                    cutoutHashmap.put(s, new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, s.runewoodCutoutTexture()));
                    corruptedCutoutHashmap.put(s, new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, s.soulwoodCutoutTexture()));
                }
        );
    }

    @Override
    public void render(TotemPoleTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = tileEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (direction.equals(Direction.WEST) || direction.equals(Direction.EAST)) {
            combinedLightIn -= combinedOverlayIn * 2;
        }
        if (tileEntityIn.type == null) {
            renderQuad(RUNEWOOD_LOG, Color.WHITE, combinedLightIn, direction, matrixStackIn, bufferIn);
            return;
        }
        if (((TotemPoleBlock) tileEntityIn.getBlockState().getBlock()).corrupted) {
            renderQuad(corruptedCutoutHashmap.get(tileEntityIn.type), Color.WHITE, combinedLightIn, direction, matrixStackIn, bufferIn);
        } else {
            renderQuad(cutoutHashmap.get(tileEntityIn.type), Color.WHITE, combinedLightIn, direction, matrixStackIn, bufferIn);
        }
        renderQuad(overlayHashmap.get(tileEntityIn.type), color(tileEntityIn), combinedLightIn, direction, matrixStackIn, bufferIn);
    }

    public Color color(TotemPoleTileEntity totemPoleTileEntity) {
        Color color1 = new Color(12, 8, 7);
        Color color2 = totemPoleTileEntity.type.color;
        int red = (int) MathHelper.lerp(totemPoleTileEntity.currentColor / 20f, color1.getRed(), color2.getRed());
        int green = (int) MathHelper.lerp(totemPoleTileEntity.currentColor / 20f, color1.getGreen(), color2.getGreen());
        int blue = (int) MathHelper.lerp(totemPoleTileEntity.currentColor / 20f, color1.getBlue(), color2.getBlue());
        return new Color(red, green, blue);
    }

    public void renderQuad(RenderMaterial material, Color color, int light, Direction direction, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn) {
        matrixStackIn.pushPose();
        TextureAtlasSprite sprite = material.sprite();
        IVertexBuilder builder = material.buffer(bufferIn, r -> RenderType.cutout());
        matrixStackIn.translate(0.5, 0, 0.5);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(rotation(direction)));
        matrixStackIn.translate(-0.5, 0, -0.5);
        add(builder, matrixStackIn, color, light, 1f, 1, 1, sprite.getU0(), sprite.getV0());
        add(builder, matrixStackIn, color, light, 1f, 0, 1, sprite.getU0(), sprite.getV1());
        add(builder, matrixStackIn, color, light, 1f, 0, 0, sprite.getU1(), sprite.getV1());
        add(builder, matrixStackIn, color, light, 1f, 1, 0, sprite.getU1(), sprite.getV0());

        matrixStackIn.popPose();
    }

    public float rotation(Direction direction) {
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            direction = direction.getOpposite();
        }
        return 90 + direction.toYRot();
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, Color color, int light, float x, float y, float z, float u, float v) {
        renderer.vertex(stack.last().pose(), x, y, z).color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1).uv(u, v).uv2(light & '\uffff', light >> 16 & '\uffff').normal(1, 0, 0).endVertex();
    }
}