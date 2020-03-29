package com.kittykitcatcat.malum.blocks.soulbinder;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

@OnlyIn(value = Dist.CLIENT)
public class SoulBinderRenderer extends TileEntityRenderer<SoulBinderTileEntity>
{

    public SoulBinderRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(SoulBinderTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (this.renderDispatcher.renderInfo != null && blockEntity.getDistanceSq(this.renderDispatcher.renderInfo.getProjectedView().x, this.renderDispatcher.renderInfo.getProjectedView().y, this.renderDispatcher.renderInfo.getProjectedView().z) < 128d)
        {
            BlockPos blockpos = blockEntity.getPos();
            BlockRendererDispatcher blockDispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            ItemStack item = blockEntity.getInputStack(blockEntity.inventory);
            matrixStack.push();
            matrixStack.translate(0.5, 1.2, 0.5);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(blockEntity.getWorld().getGameTime() * 3));
            matrixStack.scale(0.5f, 0.5f, 0.5f);
            itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, light, NO_OVERLAY, matrixStack, iRenderTypeBuffer);
            matrixStack.pop();
            /*int animationProgress = blockEntity.animationProgress;
            if (animationProgress > 0)
            {
                float AP20 = cappedAP(animationProgress, 20);
                float AP40 = cappedAP(animationProgress, 40);
                float AP80 = cappedAP(animationProgress, 40);
                double y = 1.25 + AP20 * 0.05f;
                if (!item.isEmpty())
                {
                    matrixStack.push();
                    matrixStack.translate(0.5, y, 0.5);
                    matrixStack.rotate(Vector3f.YP.rotationDegrees(blockEntity.getWorld().getGameTime() * 3));
                    matrixStack.scale(0.5f, 0.5f, 0.5f);
                    itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, light, NO_OVERLAY, matrixStack, iRenderTypeBuffer);
                    matrixStack.pop();
                }
                if (animationProgress > 20)
                {
                    for (float f = 0; f < 8; f++)
                    {
                        BlockState renderState = ModBlocks.soul_binder.getDefaultState().with(SoulBinderBlock.RENDER, (int)f+1).with(HORIZONTAL_FACING, Direction.EAST);
                        RenderType renderType = RenderTypeLookup.getRenderType(renderState);
                        matrixStack.push();
                        float scale = (AP40 - 20) / 40f;
                        double rot = f / 8 * (Math.PI * 2);
                        double distance = (4.6 - (AP40 - 20) / 5f);
                        double x = (Math.cos(rot) * distance);
                        double z = (Math.sin(rot) * distance);

                        matrixStack.translate((-scale / 2) + 0.5, -0.6, (-scale / 2) + 0.5);
                        matrixStack.translate(x, y, z);
                        matrixStack.scale(scale, scale, scale);
                        net.minecraftforge.client.ForgeHooksClient.setRenderLayer(renderType);
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
                }
                if (animationProgress > 40)
                {
                    for (float f = 0; f < 2; f++)
                    {
                        BlockState renderState = ModBlocks.soul_binder.getDefaultState().with(SoulBinderBlock.RENDER, 9);
                        RenderType renderType = RenderTypeLookup.getRenderType(renderState);
                        matrixStack.push();
                        float scale = (AP80 - 40) / 20f;
                        matrixStack.translate((-scale / 2) + 0.5, y + f - 0.6, (-scale / 2) + 0.5);
                        matrixStack.scale(scale, scale, scale);
                        net.minecraftforge.client.ForgeHooksClient.setRenderLayer(renderType);
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
                }
            }*/
        }
    }
}