package com.sammy.malum.client.renderer.texture.dynamic.request;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.sammy.malum.client.renderer.texture.dynamic.LodestoneDynamicTexture;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;
import team.lodestar.lodestone.systems.rendering.shader.ShaderHolder;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

public class VFXBuilderTextureRenderRequest extends DynamicTextureRenderRequest {

    private ShaderInstance shader = GameRenderer.getPositionTexShader();
    private UniformData data;
    private ResourceLocation drawnTexture;

    public static VFXBuilderTextureRenderRequest create(ResourceLocation writeLocation) {
        return new VFXBuilderTextureRenderRequest(writeLocation);
    }

    protected VFXBuilderTextureRenderRequest(ResourceLocation writeLocation) {
        super(writeLocation);
    }

    public VFXBuilderTextureRenderRequest setShader(ShaderHolder shader) {
        return setShader(shader.getShaderInstance());
    }

    public VFXBuilderTextureRenderRequest setShader(ShaderInstance shader) {
        this.shader = shader;
        return this;
    }

    public VFXBuilderTextureRenderRequest setUniforms(UniformData data) {
        this.data = data;
        return this;
    }

    public VFXBuilderTextureRenderRequest setDrawnTexture(ResourceLocation drawnTexture) {
        this.drawnTexture = drawnTexture;
        return this;
    }

    @Override
    public void drawTexture(LodestoneDynamicTexture texture, GuiGraphics guiGraphics) {
        var stack = guiGraphics.pose();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        data.setValues(shader);
        VFXBuilders.createScreen()
                .setPositionWithWidth(0, 0, texture.getWidth(), texture.getHeight())
                .setUV(0, 1, 1, 0)
                .setFormat(DefaultVertexFormat.POSITION_TEX_COLOR)
                .setShader(shader)
                .setTexture(drawnTexture)
                .blit(stack);
        if (shader instanceof ExtendedShaderInstance extendedShaderInstance) {
            extendedShaderInstance.applyUniformDefaults();
        }
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }
}
