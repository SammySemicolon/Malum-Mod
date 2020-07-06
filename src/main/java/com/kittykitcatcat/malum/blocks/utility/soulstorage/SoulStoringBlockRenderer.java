package com.kittykitcatcat.malum.blocks.utility.soulstorage;

import com.kittykitcatcat.malum.SpiritDataHelper;
import com.kittykitcatcat.malum.SpiritStorage;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.kittykitcatcat.malum.MalumHelper.makeImportantComponent;
import static com.kittykitcatcat.malum.SpiritDataHelper.getName;
import static net.minecraft.util.math.RayTraceResult.Type.BLOCK;

@OnlyIn(value = Dist.CLIENT)
public class SoulStoringBlockRenderer extends TileEntityRenderer<SpiritStoringTileEntity>
{

    public SoulStoringBlockRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    public static BlockPos lookingAtPos;
    public static float time;

    @Override
    public void render(SpiritStoringTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (this.renderDispatcher.renderInfo != null && blockEntity.getDistanceSq(this.renderDispatcher.renderInfo.getProjectedView().x, this.renderDispatcher.renderInfo.getProjectedView().y, this.renderDispatcher.renderInfo.getProjectedView().z) < 128d)
        {
            Minecraft minecraft = Minecraft.getInstance();
            World world = minecraft.world;
            if (minecraft.objectMouseOver == null || world == null)
            {
                return;
            }
            if (minecraft.objectMouseOver.getType().equals(BLOCK))
            {
                BlockRayTraceResult mouseOver = (BlockRayTraceResult) minecraft.objectMouseOver;
                if (lookingAtPos == null || !lookingAtPos.equals(mouseOver.getPos()))
                {
                    lookingAtPos = mouseOver.getPos();
                    time = 0;
                }
                else if (time < 1)
                {
                    time += 0.1;
                }
                if (world.getTileEntity(mouseOver.getPos()) instanceof SpiritStoringTileEntity)
                {
                    SpiritStoringTileEntity tileEntity = (SpiritStoringTileEntity) world.getTileEntity(mouseOver.getPos());
                    if (tileEntity.getPos().equals(blockEntity.getPos()))
                    {
                        if (tileEntity.type != null)
                        {

                            ITextComponent component = makeImportantComponent(tileEntity.count + "/" + ((SpiritStorage) tileEntity.getBlockState().getBlock()).capacity(), true) //[amount/max]
                                    .appendSibling(makeImportantComponent(getName(tileEntity.type), true)); //[spiritType]

                            String text = component.getFormattedText();
                            matrixStack.push();
                            matrixStack.translate(0.5, 1.5, 0.5);
                            matrixStack.rotate(renderDispatcher.renderInfo.getRotation());
                            matrixStack.scale(-0.025F * time, -0.025F * time, 0.025F * time);
                            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
                            FontRenderer fontrenderer = renderDispatcher.getFontRenderer();
                            float xOffset = (float) (-fontrenderer.getStringWidth(text) / 2);
                            fontrenderer.renderString(text, xOffset, 0, -1, true, matrix4f, iRenderTypeBuffer, false, (int) 0f << 24, light);
                            matrixStack.pop();
                        }
                    }
                }
            }
        }
    }
}