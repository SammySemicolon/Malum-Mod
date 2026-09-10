package com.sammy.malum.client.screen.codex.display.texture;

import com.sammy.malum.client.screen.codex.display.texture.request.DynamicTextureRequest;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class DynamicTextureCache {

    public static final ConcurrentHashMap<ResourceLocation, CompletableFuture<RenderedDynamicTexture>> TEXTURES = new ConcurrentHashMap<>();

    public static RenderedDynamicTexture pushTexture(DynamicTextureRequest request, Function<ResourceLocation, CompletableFuture<RenderedDynamicTexture>> textureSupplier) {
        return pushTexture(request, textureSupplier, false);
    }

    public static RenderedDynamicTexture pushTexture(DynamicTextureRequest request, Function<ResourceLocation, CompletableFuture<RenderedDynamicTexture>> textureSupplier, boolean isTicking) {
        var key = request.getWriteLocation();
        var future = DynamicTextureCache.TEXTURES.computeIfAbsent(key, textureSupplier);
        if (!future.isDone()) {
            return null;
        }
        var texture = future.join();
        if (texture.isClosed()) {
            TEXTURES.remove(key);
            return null;
        }
        if (isTicking) {
            texture.redraw();
        }
        return texture;
    }
}
