package com.sammy.malum.core.systems.tileentityrendering.modules;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.core.systems.multiblock.BoundingBlockTileEntity;
import com.sammy.malum.core.systems.multiblock.MultiblockTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;

import static net.minecraft.util.math.RayTraceResult.Type.BLOCK;

public class TextModule extends RendererModule
{
    public int renderTime;
    public Direction direction;
    public BlockPos pos;
    public boolean adjustWithDirection;
    public int maxRenderTime;
    public TextModule(boolean adjustPosition, int maxRenderTime)
    {
        this.adjustWithDirection = adjustPosition;
        this.maxRenderTime = maxRenderTime;
    }
    @Override
    public void render(SimpleTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, TileEntityRendererDispatcher renderDispatcher, int combinedLightIn, int combinedOverlayIn)
    {
        if (renderDispatcher.renderInfo != null)
        {
            Minecraft minecraft = Minecraft.getInstance();
            World world = minecraft.world;
            if (minecraft.objectMouseOver == null || world == null)
            {
                nullify();
                return;
            }
            if (minecraft.objectMouseOver.getType().equals(BLOCK))
            {
                BlockRayTraceResult mouseOver = (BlockRayTraceResult) minecraft.objectMouseOver;
                BlockPos pos = mouseOver.getPos();
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof BoundingBlockTileEntity)
                {
                    BoundingBlockTileEntity boundingBlockTileEntity = (BoundingBlockTileEntity) tileEntity;
                    pos = boundingBlockTileEntity.ownerPos;
                }
                boolean isMatchingPos = pos.equals(tileEntityIn.getPos());
                if (isMatchingPos)
                {
                    ArrayList<ITextComponent> components = this.components(tileEntityIn);
                    if (!components.isEmpty())
                    {
                        if (adjustWithDirection)
                        {
                            Direction direction = mouseOver.getFace();
                            if (this.direction == null || !this.direction.equals(direction))
                            {
                                renderTime = 0;
                                this.direction = direction;
                            }
                        }
                        if (this.pos == null || !this.pos.equals(pos))
                        {
                            renderTime = 0;
                            this.pos = pos;
                        }
                        if (renderTime < maxRenderTime)
                        {
                            renderTime++;
                        }
                        FontRenderer fontrenderer = renderDispatcher.getFontRenderer();
                        float spacing = 0.2f;
                        int current = components.size() + 1;
                        for (ITextComponent component : components)
                        {
                            String text = component.getString();
                            float xOffset = (float) (-fontrenderer.getStringWidth(text) / 2);
                            matrixStackIn.push();
        
                            Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        
                            matrixStackIn.translate(0.5, 0.5, 0.5);
                            Vector3f offset = offset(direction);
                            matrixStackIn.translate(offset.getX(), offset.getY(), offset.getZ());
        
                            matrixStackIn.rotate(renderDispatcher.renderInfo.getRotation());
                            float positionOffset = (current * spacing) - (float) components.size() * (spacing / 2);
                            matrixStackIn.translate(0, positionOffset, 0);
                            float scale = 0.025f * (renderTime / (float) maxRenderTime);
                            matrixStackIn.scale(-scale, -scale, -scale);
        
                            fontrenderer.func_243247_a(component, xOffset, 0, -1, true, matrix4f, bufferIn, false, (int) 0f << 24, 192);
                            matrixStackIn.pop();
                            current--;
                        }
                    }
                }
            }
            else
            {
                nullify();
            }
        }
        super.render(tileEntityIn, partialTicks, matrixStackIn, bufferIn, renderDispatcher, combinedLightIn, combinedOverlayIn);
    }
    public void nullify()
    {
        renderTime = 0;
        direction = null;
        pos = null;
    }
    public ArrayList<ITextComponent> components(SimpleTileEntity tileEntityIn)
    {
        return new ArrayList<>();
    }
    public Vector3f offset(Direction direction)
    {
        return new Vector3f(0,0,0);
    }
}
