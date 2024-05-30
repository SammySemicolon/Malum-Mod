package com.sammy.malum.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

public class BufferWrapper implements MultiBufferSource {

    //TODO: move this to lodestone
    public final RenderTypeProvider provider;
    public final MultiBufferSource buffer;

    public BufferWrapper(RenderTypeProvider provider, MultiBufferSource buffer) {
        this.provider = provider;
        this.buffer = buffer;
    }

    @Override
    public VertexConsumer getBuffer(RenderType renderType) {
        if (renderType instanceof RenderType.CompositeRenderType composite)
            return buffer.getBuffer(provider.apply(RenderTypeToken.createCachedToken(composite.state.textureState)));
        return buffer.getBuffer(renderType);
    }
}
