package com.sammy.malum.mixin;

import com.mojang.blaze3d.shaders.Program;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.registry.client.ShaderRegistry;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public class GameRenderMixin {

    @Inject(method = "reloadShaders", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void lodestone$registerShaders(ResourceProvider resourceProvider, CallbackInfo ci, List<Program> list, List<Pair<ShaderInstance, Consumer<ShaderInstance>>> list2) throws IOException {
        ShaderRegistry.init(resourceProvider);
        list2.addAll(ShaderRegistry.shaderList);
    }
}
