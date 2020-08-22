package com.kittykitcatcat.malum.blocks.utility.spiritstorage;

import com.kittykitcatcat.malum.SpiritDataHelper;
import com.kittykitcatcat.malum.SpiritStorage;
import com.kittykitcatcat.malum.blocks.utility.FancyRenderer;
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

import static com.kittykitcatcat.malum.ClientHandler.makeImportantComponent;
import static com.kittykitcatcat.malum.ClientHandler.renderTEdataInTheCoolFancyWay;
import static com.kittykitcatcat.malum.SpiritDataHelper.getName;

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
                    .appendSibling(makeImportantComponent(getName(blockEntity.type), true))); //[spiritType]
    
            renderTEdataInTheCoolFancyWay(blockEntity, this, matrixStack, iRenderTypeBuffer, renderDispatcher, components);
        }
    }
    
}