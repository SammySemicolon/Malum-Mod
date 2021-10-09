package com.sammy.malum.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.MalumMod;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.core.mod_systems.particle.RenderUtilities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.sammy.malum.core.mod_systems.particle.ParticleRendering.getDelayedRender;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class GenericEvents {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderLast(RenderWorldLastEvent event) {
        if (ClientConfig.BETTER_LAYERING.get()) {
            RenderSystem.pushMatrix(); // eidolon stuff once again
            RenderSystem.multMatrix(event.getMatrixStack().getLast().getMatrix());
            getDelayedRender().finish(RenderUtilities.DELAYED_PARTICLE);
            getDelayedRender().finish(RenderUtilities.GLOWING_PARTICLE);
            getDelayedRender().finish(RenderUtilities.GLOWING_BLOCK_PARTICLE);
            RenderSystem.popMatrix();

            getDelayedRender().finish(RenderUtilities.GLOWING_SPRITE);
            getDelayedRender().finish(RenderUtilities.GLOWING);
        }
    }
}
