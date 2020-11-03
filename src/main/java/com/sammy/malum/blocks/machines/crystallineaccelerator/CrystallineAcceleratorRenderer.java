package com.sammy.malum.blocks.machines.crystallineaccelerator;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.blocks.machines.redstoneclock.RedstoneClockTileEntity;
import com.sammy.malum.blocks.utility.FancyRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

import static com.sammy.malum.ClientHandler.*;
import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;
import static net.minecraft.state.properties.AttachFace.CEILING;
import static net.minecraft.state.properties.AttachFace.FLOOR;
import static net.minecraft.state.properties.BlockStateProperties.FACE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@OnlyIn(value = Dist.CLIENT)
public class CrystallineAcceleratorRenderer extends TileEntityRenderer<CrystallineAcceleratorTileEntity>
{
    public CrystallineAcceleratorRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }
    
    @Override
    public void render(CrystallineAcceleratorTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack item = blockEntity.inventory.getStackInSlot(0);
        if (!item.isEmpty())
        {
            matrixStack.push();
            matrixStack.translate(0.5f, 1.5f, 0.5f);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(blockEntity.getWorld().getGameTime() * 3));
            matrixStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, light, NO_OVERLAY, matrixStack, iRenderTypeBuffer);
            matrixStack.pop();
        }
    }
    
}