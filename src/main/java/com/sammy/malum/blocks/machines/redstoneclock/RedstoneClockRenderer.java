package com.sammy.malum.blocks.machines.redstoneclock;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.blocks.utility.FancyRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

import static com.sammy.malum.ClientHandler.*;

@OnlyIn(value = Dist.CLIENT)
public class RedstoneClockRenderer extends FancyRenderer<RedstoneClockTileEntity>
{
    public RedstoneClockRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }
    
    @Override
    public int maxOptions()
    {
        return 2;
    }
    
    @Override
    public void render(RedstoneClockTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        components.add(makeTranslationComponent("malum.tooltip.tickRate").append(makeImportantComponent("" + blockEntity.cooldown[blockEntity.tickMultiplier], true)));
        components.add(makeTranslationComponent("malum.tooltip.type").append(makeImportantComponent(blockEntity.type == 0 ? "malum.tooltip.toggle" : "malum.tooltip.pulse", true)));
        
        renderTEdataInTheCoolFancyWay(blockEntity, this, matrixStack, iRenderTypeBuffer, renderDispatcher, true, components);
    }
    
}