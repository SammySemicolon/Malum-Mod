package com.sammy.malum.blocks.machines.mirror;

import com.sammy.malum.ClientHandler;
import com.sammy.malum.blocks.utility.FancyRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

import static com.sammy.malum.ClientHandler.makeImportantComponent;
import static com.sammy.malum.ClientHandler.makeTranslationComponent;
import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;
import static net.minecraft.state.properties.AttachFace.CEILING;
import static net.minecraft.state.properties.AttachFace.FLOOR;
import static net.minecraft.state.properties.BlockStateProperties.FACE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@OnlyIn(value = Dist.CLIENT)
public class BasicMirrorRenderer extends TileEntityRenderer<BasicMirrorTileEntity> implements FancyRenderer
{

    public BasicMirrorRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
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
    public void render(BasicMirrorTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (!(blockEntity instanceof HolderMirrorTileEntity))
        {
            ArrayList<ITextComponent> components = new ArrayList<>();
            components.add(makeTranslationComponent("malum.tooltip.transferamount").append(makeImportantComponent("" + blockEntity.transferAmounts[blockEntity.transferAmount], true)));
    
            ClientHandler.renderTEdataInTheCoolFancyWayWithoutCaringAboutSides(blockEntity, this, matrixStack, iRenderTypeBuffer, renderDispatcher, components);
        }
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack item = blockEntity.inventory.getStackInSlot(0);
        if (!item.isEmpty())
        {
            BlockState blockState = blockEntity.getBlockState();
            Vector3i direction = blockEntity.getBlockState().get(HORIZONTAL_FACING).getDirectionVec();
            matrixStack.push();
            float directionMultiplier = 0.3f;
            if (blockState.get(FACE) == FLOOR || blockState.get(FACE) == CEILING)
            {
                directionMultiplier = 0f;
            }
            matrixStack.translate(0.5f - direction.getX() * directionMultiplier, 0.5f, 0.5f - direction.getZ() * directionMultiplier);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(blockEntity.getWorld().getGameTime() * 3));
            matrixStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, light, NO_OVERLAY, matrixStack, iRenderTypeBuffer);
            matrixStack.pop();
        }
    }
}