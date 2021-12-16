package com.sammy.malum.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.MalumMod;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.sammy.malum.core.systems.particle.ParticleRendering.getDelayedRender;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.FORGE)
public class GenericEvents {

    @SubscribeEvent
    public static void onRenderLast(RenderLevelLastEvent event) {
        if (ClientConfig.BETTER_LAYERING.get()) {
            RenderSystem.pushMatrix(); // eidolon stuff once again
            RenderSystem.multMatrix(event.getMatrixStack().last().pose());
            getDelayedRender().endBatch(RenderUtilities.DELAYED_PARTICLE);
            getDelayedRender().endBatch(RenderUtilities.GLOWING_PARTICLE);
            getDelayedRender().endBatch(RenderUtilities.GLOWING_BLOCK_PARTICLE);
            RenderSystem.popMatrix();

            getDelayedRender().endBatch(RenderUtilities.GLOWING_SPRITE);
            getDelayedRender().endBatch(RenderUtilities.GLOWING);
        }
    }
}
