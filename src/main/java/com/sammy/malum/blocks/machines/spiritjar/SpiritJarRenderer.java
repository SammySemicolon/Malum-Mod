package com.sammy.malum.blocks.machines.spiritjar;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.SpiritStorage;
import com.sammy.malum.blocks.utility.FancyRenderer;
import com.sammy.malum.blocks.utility.spiritstorage.SpiritStoringBlock;
import com.sammy.malum.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

import static com.sammy.malum.ClientHandler.makeImportantComponent;
import static com.sammy.malum.ClientHandler.renderTEdataInTheCoolFancyWay;
import static com.sammy.malum.SpiritDataHelper.getName;
import static net.minecraftforge.client.ForgeHooksClient.setRenderLayer;

@OnlyIn(value = Dist.CLIENT)
public class SpiritJarRenderer extends FancyRenderer<SpiritJarTileEntity>
{
    public SpiritJarRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }
    
    @Override
    public void render(SpiritJarTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (SpiritDataHelper.doesStorageHaveSpirit(blockEntity))
        {
            ArrayList<ITextComponent> components = new ArrayList<>();
            components.add(makeImportantComponent(blockEntity.count + "/" + ((SpiritStorage) blockEntity.getBlockState().getBlock()).capacity(), true).append(makeImportantComponent(getName(blockEntity.type), true)));
            renderTEdataInTheCoolFancyWay(blockEntity, this, matrixStack, iRenderTypeBuffer, renderDispatcher, true, components);
            
        }
    }
}