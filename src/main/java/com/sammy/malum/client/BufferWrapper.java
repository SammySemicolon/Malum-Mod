package com.sammy.malum.client;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.util.function.*;

public class BufferWrapper implements MultiBufferSource {

    public final Function<RenderStateShard.EmptyTextureStateShard, RenderType> provider;
    public final MultiBufferSource buffer;

    public BufferWrapper(Function<RenderStateShard.EmptyTextureStateShard, RenderType> provider, MultiBufferSource buffer) {
        this.provider = provider;
        this.buffer = buffer;
    }

    @Override
    public VertexConsumer getBuffer(RenderType renderType) {
        if (renderType instanceof RenderType.CompositeRenderType composite)
            return buffer.getBuffer(provider.apply(composite.state.textureState));
        return buffer.getBuffer(renderType);
    }
}
