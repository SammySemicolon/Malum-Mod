package com.sammy.malum.client.renderer.texture;

import com.sammy.malum.client.renderer.texture.request.render.DynamicTextureRenderRequest;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class DynamicTextureCache {

    public static final ConcurrentHashMap<ResourceLocation, CompletableFuture<LodestoneDynamicTexture>> TEXTURES = new ConcurrentHashMap<>();

    public static LodestoneDynamicTexture pushTexture(DynamicTextureRenderRequest request, Function<ResourceLocation, CompletableFuture<LodestoneDynamicTexture>> textureSupplier) {
        return pushTexture(request, textureSupplier, false);
    }

    public static LodestoneDynamicTexture pushTexture(DynamicTextureRenderRequest request, Function<ResourceLocation, CompletableFuture<LodestoneDynamicTexture>> textureSupplier, boolean isTicking) {
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
