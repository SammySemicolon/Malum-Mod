package com.sammy.malum.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class RenderUtil
{
    
    public static final RenderState.TransparencyState ADDITIVE_TRANSPARENCY = new RenderState.TransparencyState("lightning_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });
    
    public static final RenderState.TransparencyState NORMAL_TRANSPARENCY = new RenderState.TransparencyState("lightning_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });
    
    public static RenderType GLOWING_SPRITE = RenderType.makeType(
            MalumMod.MODID + ":glowing_sprite",
            DefaultVertexFormats.POSITION_COLOR_TEX,
            GL11.GL_QUADS, 256,
            RenderType.State.getBuilder()
                    .shadeModel(new RenderState.ShadeModelState(false))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(ADDITIVE_TRANSPARENCY)
                    .texture(new RenderState.TextureState(AtlasTexture.LOCATION_BLOCKS_TEXTURE, false, false))
                    .build(false)
    ), GLOWING = RenderType.makeType(
            MalumMod.MODID + ":glowing",
            DefaultVertexFormats.POSITION_COLOR,
            GL11.GL_QUADS, 256,
            RenderType.State.getBuilder()
                    .shadeModel(new RenderState.ShadeModelState(true))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(ADDITIVE_TRANSPARENCY)
                    .build(false)
    ), DELAYED_PARTICLE = RenderType.makeType(
            MalumMod.MODID + ":delayed_particle",
            DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP,
            GL11.GL_QUADS, 256,
            RenderType.State.getBuilder()
                    .shadeModel(new RenderState.ShadeModelState(true))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(NORMAL_TRANSPARENCY)
                    .texture(new RenderState.TextureState(AtlasTexture.LOCATION_PARTICLES_TEXTURE, false, false))
                    .build(false)
    ), GLOWING_PARTICLE = RenderType.makeType(
            MalumMod.MODID + ":glowing_particle",
            DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP,
            GL11.GL_QUADS, 256,
            RenderType.State.getBuilder()
                    .shadeModel(new RenderState.ShadeModelState(true))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(ADDITIVE_TRANSPARENCY)
                    .texture(new RenderState.TextureState(AtlasTexture.LOCATION_PARTICLES_TEXTURE, false, false))
                    .build(false)
    ), GLOWING_BLOCK_PARTICLE = RenderType.makeType(
            MalumMod.MODID + ":glowing_particle",
            DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP,
            GL11.GL_QUADS, 256,
            RenderType.State.getBuilder()
                    .shadeModel(new RenderState.ShadeModelState(true))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(ADDITIVE_TRANSPARENCY)
                    .texture(new RenderState.TextureState(AtlasTexture.LOCATION_BLOCKS_TEXTURE, false, false))
                    .build(false)
    );
    
    static double ticks = 0;
    
    public static void litQuad(MatrixStack mStack, IRenderTypeBuffer buffer, double x, double y, double w, double h, float r, float g, float b, TextureAtlasSprite sprite) {
        IVertexBuilder builder = buffer.getBuffer(GLOWING_SPRITE);
        
        float f7 = sprite.getMinU();
        float f8 = sprite.getMaxU();
        float f5 = sprite.getMinV();
        float f6 = sprite.getMaxV();
        Matrix4f mat = mStack.getLast().getMatrix();
        builder.pos(mat, (float)x, (float)y + (float)h, 0).color(r, g, b, 1.0f).tex(f7, f6).endVertex();
        builder.pos(mat, (float)x + (float)w, (float)y + (float)h, 0).color(r, g, b, 1.0f).tex(f8, f6).endVertex();
        builder.pos(mat, (float)x + (float)w, (float)y, 0).color(r, g, b, 1.0f).tex(f8, f5).endVertex();
        builder.pos(mat, (float)x, (float)y, 0).color(r, g, b, 1.0f).tex(f7, f5).endVertex();
    }
    
    public static void litQuad(MatrixStack mStack, IRenderTypeBuffer buffer, double x, double y, double w, double h, float r, float g, float b, float u, float v, float uw, float vh) {
        IVertexBuilder builder = buffer.getBuffer(GLOWING_SPRITE);
        
        Matrix4f mat = mStack.getLast().getMatrix();
        builder.pos(mat, (float)x, (float)y + (float)h, 0).color(r, g, b, 1.0f).tex(u, v + vh).endVertex();
        builder.pos(mat, (float)x + (float)w, (float)y + (float)h, 0).color(r, g, b, 1.0f).tex(u + uw, v + vh).endVertex();
        builder.pos(mat, (float)x + (float)w, (float)y, 0).color(r, g, b, 1.0f).tex(u + uw, v).endVertex();
        builder.pos(mat, (float)x, (float)y, 0).color(r, g, b, 1.0f).tex(u, v).endVertex();
    }
    
    public static void litBillboard(MatrixStack mStack, IRenderTypeBuffer buffer, double x, double y, double z, float r, float g, float b, TextureAtlasSprite sprite) {
        IVertexBuilder builder = buffer.getBuffer(GLOWING_SPRITE);
        ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
        Vector3d vector3d = renderInfo.getProjectedView();
        float partialTicks = Minecraft.getInstance().getRenderPartialTicks();
        float f = (float)(x);
        float f1 = (float)(y);
        float f2 = (float)(z);
        Quaternion quaternion = renderInfo.getRotation();
        
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(-0.5f, 0.5f, 0.0f), new Vector3f(0.5f, 0.5f, 0.0f), new Vector3f(0.5f, -0.5f, 0.0f)};
        float f4 = 1.0f;
        
        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.transform(quaternion);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }
        
        float f7 = sprite.getMinU();
        float f8 = sprite.getMaxU();
        float f5 = sprite.getMinV();
        float f6 = sprite.getMaxV();
        Matrix4f mat = mStack.getLast().getMatrix();
        builder.pos(mat, avector3f[0].getX(), avector3f[0].getY(), avector3f[0].getZ()).color(r, g, b, 1.0f).tex(f8, f6).endVertex();
        builder.pos(mat, avector3f[1].getX(), avector3f[1].getY(), avector3f[1].getZ()).color(r, g, b, 1.0f).tex(f8, f5).endVertex();
        builder.pos(mat, avector3f[2].getX(), avector3f[2].getY(), avector3f[2].getZ()).color(r, g, b, 1.0f).tex(f7, f5).endVertex();
        builder.pos(mat, avector3f[3].getX(), avector3f[3].getY(), avector3f[3].getZ()).color(r, g, b, 1.0f).tex(f7, f6).endVertex();
    }
    
    public static void dragon(MatrixStack mStack, IRenderTypeBuffer buf, double x, double y, double z, float radius, float r, float g, float b) {
        float f5 = 0.5f; // max number of beams
        float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
        Random random = new Random(432L);
        IVertexBuilder builder = buf.getBuffer(GLOWING);
        mStack.push();
        mStack.translate(x, y, z);
        
        float rotation = (float)(ClientHelper.getClientTicks() / 200);
        
        for(int i = 0; (float)i < (f5 + f5 * f5) / 2.0F * 60.0F; ++i) {
            mStack.rotate(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.rotate(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.rotate(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.rotate(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.rotate(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
            mStack.rotate(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F + rotation * 90.0F));
            float f3 = random.nextFloat() * 20.0F + 5.0F + f7 * 10.0F;
            float f4 = random.nextFloat() * 2.0F + 1.0F + f7 * 2.0F;
            f3 *= 0.05f * radius;
            f4 *= 0.05f * radius;
            Matrix4f mat = mStack.getLast().getMatrix();
            float alpha = 1 - f7;
            
            builder.pos(mat, 0.0F, 0.0F, 0.0F).color(r, g, b, alpha).endVertex();
            builder.pos(mat, 0.0F, 0.0F, 0.0F).color(r, g, b, alpha).endVertex();
            builder.pos(mat, -ROOT_3 * f4, f3, -0.5F * f4).color(r, g, b, 0).endVertex();
            builder.pos(mat, ROOT_3 * f4, f3, -0.5F * f4).color(r, g, b, 0).endVertex();
            builder.pos(mat, 0.0F, 0.0F, 0.0F).color(r, g, b, alpha).endVertex();
            builder.pos(mat, 0.0F, 0.0F, 0.0F).color(r, g, b, alpha).endVertex();
            builder.pos(mat, ROOT_3 * f4, f3, -0.5F * f4).color(r, g, b, 0).endVertex();
            builder.pos(mat, 0.0F, f3, 1.0F * f4).color(r, g, b, 0).endVertex();
            builder.pos(mat, 0.0F, 0.0F, 0.0F).color(r, g, b, alpha).endVertex();
            builder.pos(mat, 0.0F, 0.0F, 0.0F).color(r, g, b, alpha).endVertex();
            builder.pos(mat, 0.0F, f3, 1.0F * f4).color(r, g, b, 0).endVertex();
            builder.pos(mat, -ROOT_3 * f4, f3, -0.5F * f4).color(r, g, b, 0).endVertex();
        }
        
        mStack.pop();
    }
    
    // copied from EnderDragonRenderer
    
    private static final float ROOT_3 = (float)(Math.sqrt(3.0D) / 2.0D);
}