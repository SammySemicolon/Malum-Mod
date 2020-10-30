package com.sammy.malum.blocks.machines.spiritsmeltery;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.MalumMod;
import com.sammy.malum.OpenSimplexNoise;
import com.sammy.malum.blocks.machines.spiritfurnace.SpiritFurnaceBottomTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

@OnlyIn(value = Dist.CLIENT)
public class SpiritSmelteryRenderer extends TileEntityRenderer<SpiritSmelteryTileEntity>
{
    
    interface IChartSource
    {
        float get(int x, int z);
    }
    
    public SpiritSmelteryRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }
    
    public ResourceLocation texture = new ResourceLocation(MalumMod.MODID + "textures/other/field_square.png");
    protected long noiseSeed;
    protected OpenSimplexNoise noiseGen;
    
    public void setSeed(long seed)
    {
        if (this.noiseSeed != seed || this.noiseGen == null)
        {
            this.noiseGen = new OpenSimplexNoise(seed);
        }
        
        this.noiseSeed = seed;
    }
    
    public int getGrassColor(double double1, double double2)
    {
        setSeed(999999);
        
        double d0 = noiseGen.eval(double1 * 0.01D, double2 * 0.01D) * 2;
        return (int) (d0 * 9999999D);
    }
    
    @Override
    public void render(SpiritSmelteryTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE.param);
        GlStateManager.disableLighting();
        GlStateManager.enableAlphaTest();
        int dfunc = GL11.glGetInteger(GL11.GL_DEPTH_FUNC);
        GlStateManager.depthFunc(GL11.GL_LEQUAL);
        int func = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
        float ref = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
        GlStateManager.alphaFunc(GL11.GL_ALWAYS, 0);
        Tessellator tess = Tessellator.getInstance();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.depthMask(false);
        int seed = blockEntity.getWorld().getSeed();
        renderChart(blockEntity, x, y, z, tess, (cx, cz) -> EmberGenUtil.getEmberDensity(seed, cx, cz), new Color(255, 64, 16), new Color(255, 192, 16), new Color(255, 255, 8));
        renderChart(blockEntity, x, y, z, tess, (cx, cz) -> {
            float v = EmberGenUtil.getEmberStability(seed, cx, cz);
            return v * v * v;
        }, new Color(16, 64, 255), new Color(16, 192, 255), new Color(8, 255, 255));
        GlStateManager.depthMask(true);
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.alphaFunc(func, ref);
        GlStateManager.depthFunc(dfunc);
        GlStateManager.enableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param);
        GlStateManager.disableAlphaTest();
        GlStateManager.disableBlend();
        
    }
    
    public void renderChart(SpiritSmelteryTileEntity blockEntity, double x, double y, double z, Tessellator tess, IChartSource source, Color color1, Color color2, Color color3)
    {
        BufferBuilder buffer = tess.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LIGHTMAP_COLOR);
        float red1 = color1.getRed() / 255f;
        float green1 = color1.getGreen() / 255f;
        float blue1 = color1.getBlue() / 255f;
        float alpha1 = color1.getAlpha() / 255f;
        for (float i = -160; i < 160; i += 32)
        {
            for (float j = -160; j < 160; j += 32)
            {
                float amountul = source.get(blockEntity.getPos().getX() + (int) i / 2, blockEntity.getPos().getZ() + (int) j / 2);
                float amountur = source.get(blockEntity.getPos().getX() + (int) i / 2 + 16, blockEntity.getPos().getZ() + (int) j / 2);
                float amountdr = source.get(blockEntity.getPos().getX() + (int) i / 2 + 16, blockEntity.getPos().getZ() + (int) j / 2 + 16);
                float amountdl = source.get(blockEntity.getPos().getX() + (int) i / 2, blockEntity.getPos().getZ() + (int) j / 2 + 16);
                float alphaul = Math.min(1.0f, Math.max(0.0f, 1.0f - Math.max(Math.abs(i), Math.abs(j)) / 160f));
                float alphaur = Math.min(1.0f, Math.max(0.0f, 1.0f - Math.max(Math.abs(i + 32f), Math.abs(j)) / 160f));
                float alphadr = Math.min(1.0f, Math.max(0.0f, 1.0f - Math.max(Math.abs(i + 32f), Math.abs(j + 32f)) / 160f));
                float alphadl = Math.min(1.0f, Math.max(0.0f, 1.0f - Math.max(Math.abs(i), Math.abs(j + 32f)) / 160f));
                buffer.pos(x + 0.5f + 1.25f * (i / 160f), y + 0.5f + amountul * 0.25f, z + 0.5f + 1.25f * (j / 160f)).tex(0, 0).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(red1, green1, blue1, alpha1 * alphaul).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f) + 0.25f, y + 0.5f + amountur * 0.25f, z + 0.5f + 1.25f * (j / 160f)).tex(1.0f, 0).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(red1, green1, blue1, alpha1 * alphaur).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f) + 0.25f, y + 0.5f + amountdr * 0.25f, z + 0.5f + 1.25f * (j / 160f) + 0.25f).tex(1.0f, 1.0f).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(red1, green1, alpha1 * blue1, alphadr).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f), y + 0.5f + amountdl * 0.25f, z + 0.5f + 1.25f * (j / 160f) + 0.25f).tex(0, 1.0f).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(red1, green1, blue1, alpha1 * alphadl).endVertex();
            }
        }
            /*for (float i = -160; i < 160; i += 32){
            	for (float j = -160; j < 160; j += 32){
            		float amountul = source.get(tile.getPos().getX()+(int)i/2, tile.getPos().getZ()+(int)j/2);
            		float amountur = source.get(tile.getPos().getX()+(int)i/2+16, tile.getPos().getZ()+(int)j/2);
            		float amountdr = source.get(tile.getPos().getX()+(int)i/2+16, tile.getPos().getZ()+(int)j/2+16);
            		float amountdl = source.get(tile.getPos().getX()+(int)i/2, tile.getPos().getZ()+(int)j/2+16);
            		float alphaul = Math.min(1.0f,Math.max(0.0f,1.0f-Math.max(Math.abs(i), Math.abs(j))/160f))*amountul;
            		float alphaur = Math.min(1.0f,Math.max(0.0f,1.0f-Math.max(Math.abs(i+32f), Math.abs(j))/160f))*amountur;
            		float alphadr = Math.min(1.0f,Math.max(0.0f,1.0f-Math.max(Math.abs(i+32f), Math.abs(j+32f))/160f))*amountdr;
            		float alphadl = Math.min(1.0f,Math.max(0.0f,1.0f-Math.max(Math.abs(i), Math.abs(j+32f))/160f))*amountdl;
            		buffer.pos(x+0.5f+1.25f*(i/160f), y+0.5f+amountul*0.25f, z+0.5f+1.25f*(j/160f)).tex(0, 0).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(1.0f, 0.5f, 0.0625f, alphaul).endVertex();
            		buffer.pos(x+0.5f+1.25f*(i/160f)+0.25f, y+0.5f+amountur*0.25f, z+0.5f+1.25f*(j/160f)).tex(1.0, 0).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(1.0f, 0.5f, 0.0625f, alphaur).endVertex();
            		buffer.pos(x+0.5f+1.25f*(i/160f)+0.25f, y+0.5f+amountdr*0.25f, z+0.5f+1.25f*(j/160f)+0.25f).tex(1.0, 1.0).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(1.0f, 0.5f, 0.0625f, alphadr).endVertex();
            		buffer.pos(x+0.5f+1.25f*(i/160f), y+0.5f+amountdl*0.25f, z+0.5f+1.25f*(j/160f)+0.25f).tex(0, 1.0).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(1.0f, 0.5f, 0.0625f, alphadl).endVertex();
                }
            }*/
        float red2 = color2.getRed() / 255f;
        float green2 = color2.getGreen() / 255f;
        float blue2 = color2.getBlue() / 255f;
        float alpha2 = color2.getAlpha() / 255f;
        for (float i = -160; i < 160; i += 32)
        {
            for (float j = -160; j < 160; j += 32)
            {
                float amountul = source.get(blockEntity.getPos().getX() + (int) i / 2, blockEntity.getPos().getZ() + (int) j / 2);
                float amountur = source.get(blockEntity.getPos().getX() + (int) i / 2 + 16, blockEntity.getPos().getZ() + (int) j / 2);
                float amountdr = source.get(blockEntity.getPos().getX() + (int) i / 2 + 16, blockEntity.getPos().getZ() + (int) j / 2 + 16);
                float amountdl = source.get(blockEntity.getPos().getX() + (int) i / 2, blockEntity.getPos().getZ() + (int) j / 2 + 16);
                float alphaul = Math.min(1.0f, Math.max(0.0f, 1.0f - Math.max(Math.abs(i), Math.abs(j)) / 160f) * amountul * amountul);
                float alphaur = Math.min(1.0f, Math.max(0.0f, 1.0f - Math.max(Math.abs(i + 32f), Math.abs(j)) / 160f) * amountur * amountur);
                float alphadr = Math.min(1.0f, Math.max(0.0f, 1.0f - Math.max(Math.abs(i + 32f), Math.abs(j + 32f)) / 160f) * amountdr * amountdr);
                float alphadl = Math.min(1.0f, Math.max(0.0f, 1.0f - Math.max(Math.abs(i), Math.abs(j + 32f)) / 160f) * amountdl * amountdl);
                buffer.pos(x + 0.5f + 1.25f * (i / 160f), y + 0.5f + amountul * 0.25f, z + 0.5f + 1.25f * (j / 160f)).tex(0, 0).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(red2, green2, blue2, alpha2 * 0.875f * alphaul).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f) + 0.25f, y + 0.5f + amountur * 0.25f, z + 0.5f + 1.25f * (j / 160f)).tex(1.0f, 0).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(red2, green2, blue2, alpha2 * 0.875f * alphaur).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f) + 0.25f, y + 0.5f + amountdr * 0.25f, z + 0.5f + 1.25f * (j / 160f) + 0.25f).tex(1.0f, 1.0f).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(red2, green2, blue2, alpha2 * 0.875f * alphadr).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f), y + 0.5f + amountdl * 0.25f, z + 0.5f + 1.25f * (j / 160f) + 0.25f).tex(0, 1.0f).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(red2, green2, blue2, alpha2 * 0.875f * alphadl).endVertex();
            }
        }
        float red3 = color3.getRed() / 255f;
        float green3 = color3.getGreen() / 255f;
        float blue3 = color3.getBlue() / 255f;
        float alpha3 = color3.getAlpha() / 255f;
        for (float i = -160; i < 160; i += 32)
        {
            for (float j = -160; j < 160; j += 32)
            {
                float amountul = source.get(blockEntity.getPos().getX() + (int) i / 2, blockEntity.getPos().getZ() + (int) j / 2);
                float amountur = source.get(blockEntity.getPos().getX() + (int) i / 2 + 16, blockEntity.getPos().getZ() + (int) j / 2);
                float amountdr = source.get(blockEntity.getPos().getX() + (int) i / 2 + 16, blockEntity.getPos().getZ() + (int) j / 2 + 16);
                float amountdl = source.get(blockEntity.getPos().getX() + (int) i / 2, blockEntity.getPos().getZ() + (int) j / 2 + 16);
                float alphaul = Math.min(1.0f, Math.max(0.0f, 1.0f - Math.max(Math.abs(i), Math.abs(j)) / 160f) * amountul * amountul * amountul);
                float alphaur = Math.min(1.0f, Math.max(0.0f, 1.0f - Math.max(Math.abs(i + 32f), Math.abs(j)) / 160f) * amountur * amountur * amountur);
                float alphadr = Math.min(1.0f, Math.max(0.0f, 1.0f - Math.max(Math.abs(i + 32f), Math.abs(j + 32f)) / 160f) * amountdr * amountdr * amountdr);
                float alphadl = Math.min(1.0f, Math.max(0.0f, 1.0f - Math.max(Math.abs(i), Math.abs(j + 32f)) / 160f) * amountdl * amountdl * amountdl);
                buffer.pos(x + 0.5f + 1.25f * (i / 160f), y + 0.5f + amountul * 0.25f, z + 0.5f + 1.25f * (j / 160f)).tex(0, 0).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(red3, green3, blue3, alpha3 * alphaul).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f) + 0.25f, y + 0.5f + amountur * 0.25f, z + 0.5f + 1.25f * (j / 160f)).tex(1.0f, 0).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(red3, green3, blue3, alpha3 * alphaur).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f) + 0.25f, y + 0.5f + amountdr * 0.25f, z + 0.5f + 1.25f * (j / 160f) + 0.25f).tex(1.0f, 1.0f).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(red3, green3, blue3, alpha3 * alphadr).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f), y + 0.5f + amountdl * 0.25f, z + 0.5f + 1.25f * (j / 160f) + 0.25f).tex(0, 1.0f).lightmap(RenderUtil.lightx, RenderUtil.lighty).color(red3, green3, blue3, alpha3 * alphadl).endVertex();
            }
        }
        tess.draw();
    }
}