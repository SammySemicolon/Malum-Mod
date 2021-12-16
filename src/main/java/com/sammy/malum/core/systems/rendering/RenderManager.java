package com.sammy.malum.core.systems.rendering;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class RenderManager {
    @OnlyIn(Dist.CLIENT)
    public static MultiBufferSource.BufferSource DELAYED_RENDER = MultiBufferSource.immediate(new BufferBuilder(256));

    @SubscribeEvent
    public static void onRenderLast(RenderLevelLastEvent event) {
        event.getPoseStack().pushPose();
        DELAYED_RENDER.endBatch();
        event.getPoseStack().popPose();
    }
}