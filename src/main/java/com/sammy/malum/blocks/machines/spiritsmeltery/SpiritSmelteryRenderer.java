package com.sammy.malum.blocks.machines.spiritsmeltery;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
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

import static com.mojang.blaze3d.platform.GlStateManager.DestFactor.ONE;
import static com.mojang.blaze3d.platform.GlStateManager.SourceFactor.SRC_ALPHA;
import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;
import static org.lwjgl.opengl.GL11.*;

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
    
    public ResourceLocation texture = new ResourceLocation(MalumMod.MODID, "textures/other/field_square.png");
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
    
    public int getThing(double double1, double double2)
    {
        setSeed(999999);
        
        double d0 = noiseGen.eval(double1 * 0.01D, double2 * 0.01D) * 2;
        return (int) (d0 * 9999999D);
    }
    
    public static int lightx = 0xF000F0;
    public static int lighty = 0xF000F0;
    
    @Override
    public void render(SpiritSmelteryTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        matrixStack.push();
        int x = blockEntity.getPos().getX();
        int y = blockEntity.getPos().getY();
        int z = blockEntity.getPos().getZ();
        matrixStack.translate(0.5,1.5,0.5);
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SRC_ALPHA.param, ONE.param);
        GlStateManager.disableLighting();
        GlStateManager.enableAlphaTest();
        int dfunc = glGetInteger(GL_DEPTH_FUNC);
        GlStateManager.depthFunc(GL_LEQUAL);
        int func = glGetInteger(GL_ALPHA_TEST_FUNC);
        float ref = glGetFloat(GL_ALPHA_TEST_REF);
        GlStateManager.alphaFunc(GL_ALWAYS, 0);
        Tessellator tess = Tessellator.getInstance();
        GlStateManager.shadeModel(GL_SMOOTH);
        GlStateManager.depthMask(false);
        renderChart(blockEntity, x, y, z, tess, (cx, cz) -> getThing(x,z));
        GlStateManager.depthMask(true);
        GlStateManager.shadeModel(GL_FLAT);
        GlStateManager.alphaFunc(func, ref);
        GlStateManager.depthFunc(dfunc);
        GlStateManager.enableLighting();
        GlStateManager.blendFunc(SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param);
        GlStateManager.disableAlphaTest();
        GlStateManager.disableBlend();
        matrixStack.pop();
    }
    
    public void renderChart(SpiritSmelteryTileEntity blockEntity, double x, double y, double z, Tessellator tess, IChartSource source)
    {
        BufferBuilder buffer = tess.getBuffer();
        buffer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX_LIGHTMAP_COLOR);
        for (float i = -160; i < 160; i += 32)
        {
            for (float j = -160; j < 160; j += 32)
            {
                float amountul = source.get(blockEntity.getPos().getX() + (int) i / 2, blockEntity.getPos().getZ() + (int) j / 2);
                float amountur = source.get(blockEntity.getPos().getX() + (int) i / 2 + 16, blockEntity.getPos().getZ() + (int) j / 2);
                float amountdr = source.get(blockEntity.getPos().getX() + (int) i / 2 + 16, blockEntity.getPos().getZ() + (int) j / 2 + 16);
                float amountdl = source.get(blockEntity.getPos().getX() + (int) i / 2, blockEntity.getPos().getZ() + (int) j / 2 + 16);
                buffer.pos(x + 0.5f + 1.25f * (i / 160f), y + 0.5f + amountul * 0.25f, z + 0.5f + 1.25f * (j / 160f)).tex(0, 0).lightmap(lightx, lighty).color(1,1,1,1).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f) + 0.25f, y + 0.5f + amountur * 0.25f, z + 0.5f + 1.25f * (j / 160f)).tex(1.0f, 0).lightmap(lightx, lighty).color(1,1,1,1).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f) + 0.25f, y + 0.5f + amountdr * 0.25f, z + 0.5f + 1.25f * (j / 160f) + 0.25f).tex(1.0f, 1.0f).lightmap(lightx, lighty).color(1,1,1,1).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f), y + 0.5f + amountdl * 0.25f, z + 0.5f + 1.25f * (j / 160f) + 0.25f).tex(0, 1.0f).lightmap(lightx, lighty).color(1,1,1,1).endVertex();
            }
        }
        for (float i = -160; i < 160; i += 32)
        {
            for (float j = -160; j < 160; j += 32)
            {
                float amountul = source.get(blockEntity.getPos().getX() + (int) i / 2, blockEntity.getPos().getZ() + (int) j / 2);
                float amountur = source.get(blockEntity.getPos().getX() + (int) i / 2 + 16, blockEntity.getPos().getZ() + (int) j / 2);
                float amountdr = source.get(blockEntity.getPos().getX() + (int) i / 2 + 16, blockEntity.getPos().getZ() + (int) j / 2 + 16);
                float amountdl = source.get(blockEntity.getPos().getX() + (int) i / 2, blockEntity.getPos().getZ() + (int) j / 2 + 16);
                buffer.pos(x + 0.5f + 1.25f * (i / 160f), y + 0.5f + amountul * 0.25f, z + 0.5f + 1.25f * (j / 160f)).tex(0, 0).lightmap(lightx, lighty).color(1,1,1,1).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f) + 0.25f, y + 0.5f + amountur * 0.25f, z + 0.5f + 1.25f * (j / 160f)).tex(1.0f, 0).lightmap(lightx, lighty).color(1,1,1,1).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f) + 0.25f, y + 0.5f + amountdr * 0.25f, z + 0.5f + 1.25f * (j / 160f) + 0.25f).tex(1.0f, 1.0f).lightmap(lightx, lighty).color(1,1,1,1).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f), y + 0.5f + amountdl * 0.25f, z + 0.5f + 1.25f * (j / 160f) + 0.25f).tex(0, 1.0f).lightmap(lightx, lighty).color(1,1,1,1).endVertex();
            }
        }
        for (float i = -160; i < 160; i += 32)
        {
            for (float j = -160; j < 160; j += 32)
            {
                float amountul = source.get(blockEntity.getPos().getX() + (int) i / 2, blockEntity.getPos().getZ() + (int) j / 2);
                float amountur = source.get(blockEntity.getPos().getX() + (int) i / 2 + 16, blockEntity.getPos().getZ() + (int) j / 2);
                float amountdr = source.get(blockEntity.getPos().getX() + (int) i / 2 + 16, blockEntity.getPos().getZ() + (int) j / 2 + 16);
                float amountdl = source.get(blockEntity.getPos().getX() + (int) i / 2, blockEntity.getPos().getZ() + (int) j / 2 + 16);
                buffer.pos(x + 0.5f + 1.25f * (i / 160f), y + 0.5f + amountul * 0.25f, z + 0.5f + 1.25f * (j / 160f)).tex(0, 0).lightmap(lightx, lighty).color(1,1,1,1).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f) + 0.25f, y + 0.5f + amountur * 0.25f, z + 0.5f + 1.25f * (j / 160f)).tex(1.0f, 0).lightmap(lightx, lighty).color(1,1,1,1).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f) + 0.25f, y + 0.5f + amountdr * 0.25f, z + 0.5f + 1.25f * (j / 160f) + 0.25f).tex(1.0f, 1.0f).lightmap(lightx, lighty).color(1,1,1,1).endVertex();
                buffer.pos(x + 0.5f + 1.25f * (i / 160f), y + 0.5f + amountdl * 0.25f, z + 0.5f + 1.25f * (j / 160f) + 0.25f).tex(0, 1.0f).lightmap(lightx, lighty).color(1,1,1,1).endVertex();
            }
        }
        tess.draw();
    }
}