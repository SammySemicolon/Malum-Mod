package com.kittykitcatcat.malum.blocks.machines.redstoneclock;

import com.kittykitcatcat.malum.blocks.utility.FancyRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

import static com.kittykitcatcat.malum.ClientHandler.*;

@OnlyIn(value = Dist.CLIENT)
public class RedstoneClockRenderer extends TileEntityRenderer<RedstoneClockTileEntity> implements FancyRenderer
{
    public RedstoneClockRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
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
    public void render(RedstoneClockTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        components.add(makeTranslationComponent("malum.tooltip.tickRate")
                .append(makeImportantComponent("" + blockEntity.cooldown[blockEntity.tickMultiplier], true)));
        components.add(makeTranslationComponent("malum.tooltip.type")
                .append(makeImportantComponent(blockEntity.type == 0 ? "malum.tooltip.toggle" : "malum.tooltip.pulse", true)));
        
        renderTEdataInTheCoolFancyWay(blockEntity,this,matrixStack,iRenderTypeBuffer,renderDispatcher, components);
    }
    
}