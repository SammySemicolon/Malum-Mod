package com.sammy.malum.blocks.machines.spiritsmeltery;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.blocks.machines.redstonebattery.RedstoneBatteryTileEntity;
import com.sammy.malum.blocks.utility.FancyRenderer;
import com.sammy.malum.blocks.utility.multiblock.BoundingBlockTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

import static com.sammy.malum.ClientHandler.*;

@OnlyIn(value = Dist.CLIENT)
public class SpiritSmelteryBoundingBlockRenderer extends FancyRenderer<BoundingBlockTileEntity>
{
    public SpiritSmelteryBoundingBlockRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }
    
    @Override
    public int maxOptions()
    {
        return 2;
    }
    
    @Override
    public void render(BoundingBlockTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (blockEntity.getWorld().getTileEntity(blockEntity.ownerPos) instanceof SpiritSmelteryTileEntity)
        {
            SpiritSmelteryTileEntity tileEntity = (SpiritSmelteryTileEntity) blockEntity.getWorld().getTileEntity(blockEntity.ownerPos);
            ArrayList<ITextComponent> components = new ArrayList<>();
            components.add(makeTranslationComponent("malum.tooltip.processing").append(makeImportantComponent(tileEntity.vanilla_furnace ? "malum.tooltip.smelting" : "malum.tooltip.magic_infusion", true)));
    
            renderTEdataInTheCoolFancyWay(blockEntity, this, matrixStack, iRenderTypeBuffer, renderDispatcher, true, components);
        }
    }
    
}