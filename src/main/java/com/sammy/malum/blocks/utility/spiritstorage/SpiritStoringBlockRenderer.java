package com.sammy.malum.blocks.utility.spiritstorage;

import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.SpiritStorage;
import com.sammy.malum.blocks.utility.FancyRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

import static com.sammy.malum.ClientHandler.makeImportantComponent;
import static com.sammy.malum.ClientHandler.renderTEdataInTheCoolFancyWay;
import static com.sammy.malum.SpiritDataHelper.getName;

@OnlyIn(value = Dist.CLIENT)
public class SpiritStoringBlockRenderer extends TileEntityRenderer<SpiritStoringTileEntity> implements FancyRenderer
{
    public SpiritStoringBlockRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
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
    public void render(SpiritStoringTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (SpiritDataHelper.doesStorageHaveSpirit(blockEntity))
        {
            ArrayList<ITextComponent> components = new ArrayList<>();
            components.add(makeImportantComponent(blockEntity.count + "/" + ((SpiritStorage) blockEntity.getBlockState().getBlock()).capacity(), true) //[amount/max]
                    .append(makeImportantComponent(getName(blockEntity.type), true))); //[spiritType]
    
            renderTEdataInTheCoolFancyWay(blockEntity, this, matrixStack, iRenderTypeBuffer, renderDispatcher, components);
        }
    }
    
}