package com.sammy.malum.core.systems.tileentityrendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.core.systems.tileentityrendering.modules.RendererModule;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
public class AdjustableTileEntityRenderer extends TileEntityRenderer<TileEntity>
{
    public ArrayList<RendererModule> modules;
    public AdjustableTileEntityRenderer(Object rendererDispatcherIn, ArrayList<RendererModule> modules)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
        this.modules = modules;
    }
    @Override
    public void render(TileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        for (RendererModule module : modules)
        {
            module.render((SimpleTileEntity) tileEntityIn, partialTicks, matrixStackIn, bufferIn, renderDispatcher, combinedLightIn, combinedOverlayIn);
        }
    }
}
