package com.sammy.malum.client.renderer.texture.dynamic;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.*;
import com.sammy.malum.client.renderer.texture.dynamic.request.DynamicTextureRenderRequest;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"unused"})
public class DynamicTextureRenderer {

    protected final DynamicTextureRenderRequest request;
    protected int width = 16, height = 16;
    protected float hScale = 1, vScale = 1;

    protected boolean isTicking = false;

    public static DynamicTextureRenderer create(DynamicTextureRenderRequest request) {
        return new DynamicTextureRenderer(request);
    }

    private DynamicTextureRenderer(DynamicTextureRenderRequest request) {
        this.request = request;
    }

    public DynamicTextureRenderer setTextureSize(int size) {
        return setTextureSize(size, size);
    }

    public DynamicTextureRenderer setTextureSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public DynamicTextureRenderer setScale(float scale) {
        this.hScale = scale;
        this.vScale = scale;
        return this;
    }

    public DynamicTextureRenderer setTicking(boolean isTicking) {
        this.isTicking = isTicking;
        return this;
    }

    public LodestoneDynamicTexture bakeTexture() {
        request.modify(this);
        var texture = DynamicTextureCache.pushTexture(request, this::createTexture);
        if (texture != null && isTicking) {
            texture.redraw();
        }

        return texture;
    }

    private CompletableFuture<LodestoneDynamicTexture> createTexture(ResourceLocation key) {
        var nested = new CompletableFuture<LodestoneDynamicTexture>();
        RenderSystem.recordRenderCall(() -> {
            try {
                var texture = new LodestoneDynamicTexture(request, width, height, hScale, vScale);
                nested.complete(texture);
            } catch (Throwable t) {
                MalumMod.LOGGER.error("Failed to create dynamic texture for id {}", request.getWriteLocation(), t);
                nested.completeExceptionally(t);
            }
        });
        return nested;
    }
}