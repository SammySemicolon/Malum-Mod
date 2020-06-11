package com.kittykitcatcat.malum.blocks.machines.spiritbellows;

import com.kittykitcatcat.malum.init.ModBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.kittykitcatcat.malum.blocks.machines.spiritbellows.SpiritBellowsBlock.RENDER;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@OnlyIn(value = Dist.CLIENT)
public class SpiritBellowsRenderer extends TileEntityRenderer<SpiritBellowsTileEntity>
{

    public SpiritBellowsRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }


    @Override
    public void render(SpiritBellowsTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (this.renderDispatcher.renderInfo != null && blockEntity.getDistanceSq(this.renderDispatcher.renderInfo.getProjectedView().x, this.renderDispatcher.renderInfo.getProjectedView().y, this.renderDispatcher.renderInfo.getProjectedView().z) < 128d)
        {
            BlockPos blockpos = blockEntity.getPos();
            BlockRendererDispatcher blockDispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
            BlockState renderState = ModBlocks.spirit_bellows.getDefaultState().with(HORIZONTAL_FACING, blockEntity.getBlockState().get(HORIZONTAL_FACING)).with(RENDER, true);
            RenderType renderType = RenderTypeLookup.getRenderType(renderState);
            net.minecraftforge.client.ForgeHooksClient.setRenderLayer(renderType);
            float scaleY = blockEntity.stretch;
            matrixStack.push();
            matrixStack.translate(0,(-scaleY/2)+0.5,0);
            matrixStack.scale(1,scaleY,1);
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
}