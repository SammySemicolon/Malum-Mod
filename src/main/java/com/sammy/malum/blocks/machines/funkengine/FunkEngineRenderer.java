package com.sammy.malum.blocks.machines.funkengine;

import com.sammy.malum.blocks.utility.FancyRenderer;
import com.sammy.malum.blocks.utility.IFancyRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

import static com.sammy.malum.ClientHandler.*;

@OnlyIn(value = Dist.CLIENT)
public class FunkEngineRenderer extends FancyRenderer<FunkEngineTileEntity>
{

    public FunkEngineRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }
    
    @Override
    public void render(FunkEngineTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (blockEntity.inventory.getStackInSlot(0).getItem() instanceof MusicDiscItem)
        {
            MusicDiscItem discItem = (MusicDiscItem) blockEntity.inventory.getStackInSlot(0).getItem();
            ArrayList<ITextComponent> components = new ArrayList<>();
            components.add(makeTranslationComponent("malum.tooltip.playing").append(makeImportantComponent(discItem.getDescription().getString(), true)));
            renderTEdataInTheCoolFancyWay(blockEntity, this, matrixStack, iRenderTypeBuffer, renderDispatcher,true, components);
        }
    }
}