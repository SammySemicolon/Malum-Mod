package com.sammy.malum.blocks.machines.funkengine;

import com.sammy.malum.blocks.utility.FancyRenderer;
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
public class FunkEngineRenderer extends TileEntityRenderer<FunkEngineTileEntity> implements FancyRenderer
{

    public FunkEngineRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }
    
    public Direction lookingAtFace;
    public BlockPos lookingAtPos;
    public float time;
    
    @Override
    public Direction lookingAtFace()
    {
        return lookingAtFace;
    }
    
    @Override
    public void setLookingAtFace(Direction direction)
    {
        lookingAtFace = direction;
    }
    
    @Override
    public BlockPos lookingAtPos()
    {
        return lookingAtPos;
    }
    
    @Override
    public void setLookingAtPos(BlockPos pos)
    {
        lookingAtPos = pos;
    }
    
    
    @Override
    public float time()
    {
        return time;
    }
    
    @Override
    public void setTime(float time)
    {
        this.time = time;
    }
    @Override
    public void render(FunkEngineTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (blockEntity.inventory.getStackInSlot(0).getItem() instanceof MusicDiscItem)
        {
            MusicDiscItem discItem = (MusicDiscItem) blockEntity.inventory.getStackInSlot(0).getItem();
            ArrayList<ITextComponent> components = new ArrayList<>();
            components.add(makeTranslationComponent("malum.tooltip.playing").append(makeImportantComponent(discItem.getDescription().getString(), true)));
            renderTEdataInTheCoolFancyWay(blockEntity, this, matrixStack, iRenderTypeBuffer, renderDispatcher, components);
        }
    }
}