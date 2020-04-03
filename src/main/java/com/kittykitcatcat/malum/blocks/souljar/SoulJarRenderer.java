package com.kittykitcatcat.malum.blocks.souljar;

import com.kittykitcatcat.malum.init.ModBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.kittykitcatcat.malum.blocks.souljar.SoulJarBlock.RENDER;

@OnlyIn(value = Dist.CLIENT)
public class SoulJarRenderer extends TileEntityRenderer<SoulJarTileEntity>
{
    public SoulJarRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }
    @Override
    public void render(SoulJarTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (this.renderDispatcher.renderInfo != null && blockEntity.getDistanceSq(this.renderDispatcher.renderInfo.getProjectedView().x, this.renderDispatcher.renderInfo.getProjectedView().y, this.renderDispatcher.renderInfo.getProjectedView().z) < 128d)
        {
            BlockPos blockpos = blockEntity.getPos();
            BlockRendererDispatcher blockDispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
            BlockState renderState = ModBlocks.soul_jar.getDefaultState().with(RENDER, 1);
            RenderType renderType = RenderTypeLookup.getRenderType(renderState);
            BlockState renderState2 = ModBlocks.soul_jar.getDefaultState().with(RENDER, 2);
            RenderType renderType2 = RenderTypeLookup.getRenderType(renderState);

            float scaleY = blockEntity.delayedPurity / 5f;
            if (scaleY != 0)
            {
                net.minecraftforge.client.ForgeHooksClient.setRenderLayer(renderType);
                matrixStack.push();
                matrixStack.translate(0, 0.0625, 0);
                matrixStack.scale(1, scaleY-0.0625f, 1);
                blockDispatcher.getBlockModelRenderer().renderModel(
                        blockEntity.getWorld(),
                        blockDispatcher.getModelForState(renderState),
                        renderState,
                        blockpos,
                        matrixStack,
                        iRenderTypeBuffer.getBuffer(renderType),
                        false,
                        blockEntity.getWorld().rand,
                        renderState.getPositionRandom(blockpos),
                        OverlayTexture.NO_OVERLAY,
                        net.minecraftforge.client.model.data.EmptyModelData.INSTANCE
                );
                matrixStack.pop();
            }
            net.minecraftforge.client.ForgeHooksClient.setRenderLayer(renderType2);
            matrixStack.push();
            float rotation = 320 * (blockEntity.delayedPurity / 5f)+180;
            matrixStack.translate(0.5,1,0.5);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-rotation));
            matrixStack.scale(1,1,0.5f);
            blockDispatcher.getBlockModelRenderer().renderModel(
                    blockEntity.getWorld(),
                    blockDispatcher.getModelForState(renderState2),
                    renderState2,
                    blockpos,
                    matrixStack,
                    iRenderTypeBuffer.getBuffer(renderType2),
                    false,
                    blockEntity.getWorld().rand,
                    renderState2.getPositionRandom(blockpos),
                    OverlayTexture.NO_OVERLAY,
                    net.minecraftforge.client.model.data.EmptyModelData.INSTANCE
            );
            matrixStack.pop();
        }
    }
}