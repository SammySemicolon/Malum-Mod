package com.sammy.malum.client.screen.codex.display;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.spirit.SpiritLike;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import team.lodestar.lodestone.registry.client.LodestoneShaders;
import team.lodestar.lodestone.systems.rendering.builder.ScreenVFXBuilder;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;

import java.awt.*;

public class CodexIconRenderer {

    protected final ResourceLocation texture;
    protected final int width;
    protected final int height;

    protected Color primaryColor = Color.WHITE;
    protected Color secondaryColor = Color.WHITE;

    protected float distortion = 35f;
    protected boolean corrupted = false;
    protected int offset;

    public static CodexIconRenderer create(String texture, int width, int height) {
        var modId = MalumMod.MALUM;
        if (texture.contains(":")) {
            int index = texture.indexOf(":");
            modId = texture.substring(0, index);
            texture = texture.substring(index+1);
        }

        var full = "textures/gui/book/icons/" + texture + ".png";

        return create(ResourceLocation.fromNamespaceAndPath(modId, full), width, height);
    }

    public static CodexIconRenderer create(ResourceLocation texture, int width, int height) {
        return new CodexIconRenderer(texture, width, height);
    }

    protected CodexIconRenderer(ResourceLocation texture, int width, int height) {
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    public CodexIconRenderer setDistortion(float distortion) {
        this.distortion = distortion;
        return this;
    }

    public CodexIconRenderer setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public CodexIconRenderer setCorrupted(boolean corrupted) {
        this.corrupted = corrupted;
        return this;
    }

    public CodexIconRenderer setSpiritColors(SpiritLike spirit) {
        return setPrimaryColor(spirit.getPrimaryColor()).setSecondaryColor(spirit.getSecondaryColor());
    }

    public CodexIconRenderer setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }

    public CodexIconRenderer setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
        return this;
    }

    public void renderIcon(PoseStack stack, int x, int y) {
        var shader = LodestoneShaders.SCREEN_DISTORTED_TEXTURE.getShaderInstance();
        shader.safeGetUniform("YFrequency").set(10f);
        shader.safeGetUniform("XFrequency").set(20f);
        shader.safeGetUniform("Speed").set(1500f);
        shader.safeGetUniform("Intensity").set(distortion);
        shader.safeGetUniform("UVCoordinates").set(new Vector4f(0f, 1f, 0f, 1f));
        ScreenVFXBuilder builder = VFXBuilders.createScreen()
                .setTexture(texture)
                .setShader(shader)
                .setZLevel(20);

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);


        builder.setColor(primaryColor).setAlpha(0.7f);

        builder.setPositionWithWidth(x, y, width, height).blit(stack);

        builder.setAlpha(0.2f);

        builder.setPositionWithWidth(x+1, y, width, height).blit(stack);
        builder.setPositionWithWidth(x-1, y, width, height).blit(stack);

        builder.setColor(secondaryColor).setAlpha(0.05f);
        shader.safeGetUniform("Intensity").set(-distortion);

        builder.setPositionWithWidth(x, y+1, width, height).blit(stack);
        builder.setPositionWithWidth(x, y-1, width, height).blit(stack);

        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
        shader.applyUniformDefaults();
    }
}
