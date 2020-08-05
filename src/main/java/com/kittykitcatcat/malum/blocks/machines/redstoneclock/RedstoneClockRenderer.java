package com.kittykitcatcat.malum.blocks.machines.redstoneclock;

import com.kittykitcatcat.malum.MalumMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.kittykitcatcat.malum.MalumHelper.makeImportantComponent;
import static net.minecraft.util.math.RayTraceResult.Type.BLOCK;

@OnlyIn(value = Dist.CLIENT)
public class RedstoneClockRenderer extends TileEntityRenderer<RedstoneClockTileEntity>
{
    public RedstoneClockRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }

    public static BlockPos lookingAtPos;
    public static float time;

    @Override
    public void render(RedstoneClockTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
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

                if (world.getTileEntity(mouseOver.getPos()) instanceof RedstoneClockTileEntity)
                {
                    RedstoneClockTileEntity tileEntity = (RedstoneClockTileEntity) world.getTileEntity(mouseOver.getPos());
                    if (tileEntity.getPos().equals(blockEntity.getPos()))
                    {
                        if (lookingAtPos != null && lookingAtPos.equals(mouseOver.getPos()) && time < 1)
                        {
                            time += 0.1;
                        }
                        FontRenderer fontrenderer = renderDispatcher.getFontRenderer();

                        ITextComponent pulseComponent = new TranslationTextComponent("malum.tooltip.tickRate.desc")
                                .appendSibling(makeImportantComponent("" + tileEntity.cooldown[tileEntity.tickMultiplier], true));
                        String pulseText = pulseComponent.getFormattedText();
                        float xPulseOffset = (float) (-fontrenderer.getStringWidth(pulseText) / 2);

                        ITextComponent toggleComponent = new TranslationTextComponent("malum.tooltip.type.desc")
                                .appendSibling(makeImportantComponent(tileEntity.type == 0 ? "malum.tooltip.toggle.desc" : "malum.tooltip.pulse.desc", true));
                        String toggleText = toggleComponent.getFormattedText();
                        float xToggleOffset = (float) (-fontrenderer.getStringWidth(toggleText) / 2);
                        matrixStack.push();
                        Matrix4f matrix4fPulse = matrixStack.getLast().getMatrix();
                        matrixStack.translate(0.5, 1.4, 0.5);
                        matrixStack.rotate(renderDispatcher.renderInfo.getRotation());
                        matrixStack.scale(-0.025F * time, -0.025F * time, 0.025F * time);
                        fontrenderer.renderString(pulseText, xPulseOffset, 0, -1, true, matrix4fPulse, iRenderTypeBuffer, false, (int) 0f << 24, 192);
                        matrixStack.pop();

                        matrixStack.push();
                        Matrix4f matrix4fToggle = matrixStack.getLast().getMatrix();
                        matrixStack.translate(0.5, 1.6, 0.5);
                        matrixStack.rotate(renderDispatcher.renderInfo.getRotation());
                        matrixStack.scale(-0.025F * time, -0.025F * time, 0.025F * time);
                        fontrenderer.renderString(toggleText, xToggleOffset, 0, -1, true, matrix4fToggle, iRenderTypeBuffer, false, (int) 0f << 24, 192);
                        matrixStack.pop();
                    }
                }
            }
        }
    }
}