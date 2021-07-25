package com.sammy.malum.core.systems.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.client.RenderUtilities;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

//all of this code is taken directly from Elucent's Eidolon.
public class ParticleRendering
{
    @OnlyIn(Dist.CLIENT)
    static IRenderTypeBuffer.Impl DELAYED_RENDER = null;
    
    @OnlyIn(Dist.CLIENT)
    public static IRenderTypeBuffer.Impl getDelayedRender()
    {
        if (DELAYED_RENDER == null)
        {
            Map<RenderType, BufferBuilder> buffers = new HashMap<>();
            for (RenderType type : new RenderType[]{RenderUtilities.DELAYED_PARTICLE, RenderUtilities.GLOWING_PARTICLE, RenderUtilities.GLOWING_BLOCK_PARTICLE, RenderUtilities.GLOWING, RenderUtilities.GLOWING_SPRITE})
            {
                buffers.put(type, new BufferBuilder(type.getBufferSize()));
            }
            DELAYED_RENDER = IRenderTypeBuffer.getImpl(buffers, new BufferBuilder(256));
        }
        return DELAYED_RENDER;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event)
    {
        RenderSystem.pushMatrix(); // this feels...cheaty
        RenderSystem.multMatrix(event.getMatrixStack().getLast().getMatrix());
        getDelayedRender().finish(RenderUtilities.DELAYED_PARTICLE);
        getDelayedRender().finish(RenderUtilities.GLOWING_PARTICLE);
        getDelayedRender().finish(RenderUtilities.GLOWING_BLOCK_PARTICLE);
        RenderSystem.popMatrix();
    
        getDelayedRender().finish(RenderUtilities.GLOWING_SPRITE);
        getDelayedRender().finish(RenderUtilities.GLOWING);
    }
}