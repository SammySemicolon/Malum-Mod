package com.sammy.malum.blocks.machines.funkengine.discholder;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHandler;
import com.sammy.malum.blocks.machines.mirror.HolderMirrorTileEntity;
import com.sammy.malum.blocks.utility.FancyRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
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
public class DiscHolderRenderer extends TileEntityRenderer<DiscHolderTileEntity> implements FancyRenderer
{
    
    public DiscHolderRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
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
    public void render(DiscHolderTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        components.add(makeTranslationComponent("malum.tooltip.type").append(makeImportantComponent(blockEntity.shuffle ? "malum.tooltip.shuffled" : "malum.tooltip.ordered", true)));
    
        ClientHandler.renderTEdataInTheCoolFancyWayWithoutCaringAboutSides(blockEntity, this, matrixStack, iRenderTypeBuffer, renderDispatcher, components);
    
        int items = 0;
        for (int i = 0; i < blockEntity.inventory.getSlots(); i++)
        {
            ItemStack item = blockEntity.inventory.getStackInSlot(i);
            if (!item.isEmpty())
            {
                items++;
            }
            else
            {
                break;
            }
        }
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        float[] angles = new float[items];
    
        float anglePer = 360F / items;
        float totalAngle = 0F;
        for (int i = 0; i < angles.length; i++)
        {
            angles[i] = totalAngle += anglePer;
        }
    
        for (int i = 0; i < items; i++)
        {
            ItemStack item = blockEntity.inventory.getStackInSlot(i);
            if (!item.isEmpty())
            {
                float gameTime = blockEntity.getWorld().getGameTime();
                matrixStack.push();
                matrixStack.translate(0.5F, 0.1f, 0.5F);
                matrixStack.rotate(Vector3f.YP.rotationDegrees(angles[i] + gameTime));
                float range = 0.325f;
                matrixStack.translate(0.225F, 0F, 0.05F);
                matrixStack.rotate(Vector3f.YP.rotationDegrees(90F));
                matrixStack.scale(0.8F, 0.8f, 0.8F);
                itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.GROUND, light, overlay, matrixStack, iRenderTypeBuffer);
                matrixStack.pop();
            }
        }
    }
}