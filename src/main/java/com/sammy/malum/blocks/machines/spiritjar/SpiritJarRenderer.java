package com.sammy.malum.blocks.machines.spiritjar;

import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.SpiritStorage;
import com.sammy.malum.blocks.utility.FancyRenderer;
import com.sammy.malum.blocks.utility.spiritstorage.SpiritStoringBlock;
import com.sammy.malum.init.ModBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.OverlayTexture;
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
import static net.minecraftforge.client.ForgeHooksClient.*;

@OnlyIn(value = Dist.CLIENT)
public class SpiritJarRenderer extends TileEntityRenderer<SpiritJarTileEntity> implements FancyRenderer
{
    public SpiritJarRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
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
    public void render(SpiritJarTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (SpiritDataHelper.doesStorageHaveSpirit(blockEntity))
        {
            ArrayList<ITextComponent> components = new ArrayList<>();
            components.add(makeImportantComponent(blockEntity.count + "/" + ((SpiritStorage) blockEntity.getBlockState().getBlock()).capacity(), true).append(makeImportantComponent(getName(blockEntity.type), true)));
            renderTEdataInTheCoolFancyWay(blockEntity, this, matrixStack, iRenderTypeBuffer, renderDispatcher, components);
        }
        if (this.renderDispatcher.renderInfo != null)
        {
            BlockPos blockpos = blockEntity.getPos();
            BlockState renderState = ModBlocks.spirit_jar.getDefaultState().with(SpiritStoringBlock.TYPE, 1);
            float scale = blockEntity.count / (float) ((SpiritStoringBlock) renderState.getBlock()).capacity();
            matrixStack.push();
            matrixStack.scale(0.8f, scale * 0.8f, 0.8f);
            matrixStack.translate(0.2, 0, 0.2);
            RenderType renderType = RenderTypeLookup.func_239221_b_(renderState);
            setRenderLayer(renderType);
            BlockRendererDispatcher blockDispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
    
            blockDispatcher.getBlockModelRenderer().renderModel(blockEntity.getWorld(), blockDispatcher.getModelForState(renderState), renderState, blockpos, matrixStack, iRenderTypeBuffer.getBuffer(renderType), false, blockEntity.getWorld().rand, renderState.getPositionRandom(blockpos), OverlayTexture.NO_OVERLAY, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
            matrixStack.pop();
            
        }
    }
}