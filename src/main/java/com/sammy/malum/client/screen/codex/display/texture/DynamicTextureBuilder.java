package com.sammy.malum.client.screen.codex.display.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.display.texture.request.DynamicTextureRequest;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"unused"})
public class DynamicTextureBuilder {

    protected final DynamicTextureRequest request;
    protected int width = 16, height = 16;
    protected float hScale = 1, vScale = 1;

    protected boolean isTicking = false;

    public static DynamicTextureBuilder create(DynamicTextureRequest request) {
        return new DynamicTextureBuilder(request);
    }

    private DynamicTextureBuilder(DynamicTextureRequest request) {
        this.request = request;
    }

    public DynamicTextureBuilder setTextureSize(int size) {
        return setTextureSize(size, size);
    }

    public DynamicTextureBuilder setTextureSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public DynamicTextureBuilder setScale(float scale) {
        this.hScale = scale;
        this.vScale = scale;
        return this;
    }

    public DynamicTextureBuilder setTicking(boolean isTicking) {
        this.isTicking = isTicking;
        return this;
    }

//    public RenderableDynamicTexture bakeItemTexture(ItemLike item) {
//        return bakeItemTexture(item.asItem().getDefaultInstance());
//    }
//
//    public RenderableDynamicTexture bakeItemTexture(ItemStack stack) {
//        if (stack.getItem() instanceof BlockItem) {
//            setTextureSize(64);
//            setScale(4);
//        }
//        return bakeTexture(t -> drawItem(t, stack));
//    }
//


    public RenderedDynamicTexture bakeTexture() {
        var texture = DynamicTextureCache.pushTexture(request, this::createTexture);
        if (texture != null && isTicking) {
            texture.redraw();
        }

        return texture;
    }

    private CompletableFuture<RenderedDynamicTexture> createTexture(ResourceLocation key) {
        var nested = new CompletableFuture<RenderedDynamicTexture>();
        RenderSystem.recordRenderCall(() -> {
            try {
                var texture = new RenderedDynamicTexture(request, width, height, hScale, vScale);
                nested.complete(texture);
            } catch (Throwable t) {
                MalumMod.LOGGER.error("Failed to create dynamic texture for id {}", request.getWriteLocation(), t);
                nested.completeExceptionally(t);
            }
        });
        return nested;
    }

//    protected void drawTexture(ResourceLocation texture) {
//        drawTexture(GameRenderer::getPositionTexColorShader, texture);
//    }
//
//    protected void drawOutline(ResourceLocation texture, int sourceWidth, int sourceHeight, int outlineWidth) {
//        var outline = MalumShaders.OUTLINED_HUD_ELEMENT.getShaderInstance();
//        outline.safeGetUniform("OutlineWidth").set(outlineWidth);
//        outline.safeGetUniform("SourceTextureSize").set((float)sourceWidth, (float)sourceHeight);
//        outline.safeGetUniform("OutputTextureSize").set((float)width, (float)height);
//        drawTexture(MalumShaders.OUTLINED_HUD_ELEMENT::getShaderInstance, texture);
//        outline.applyUniformDefaults();
//    }
}