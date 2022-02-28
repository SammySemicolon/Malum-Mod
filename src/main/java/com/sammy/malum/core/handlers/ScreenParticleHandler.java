package com.sammy.malum.core.handlers;

import com.mojang.blaze3d.vertex.Tesselator;
import com.sammy.malum.core.helper.ParticleHelper;
import com.sammy.malum.core.setup.client.ScreenParticleRegistry;
import com.sammy.malum.core.systems.rendering.particle.options.ScreenParticleOptions;
import com.sammy.malum.core.systems.rendering.screenparticle.ScreenParticle;
import com.sammy.malum.core.systems.rendering.screenparticle.ScreenParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ScreenParticleHandler {

    public static Map<ParticleRenderType, ArrayList<ScreenParticle>> PARTICLES;

    public static void clientTick(TickEvent.ClientTickEvent event) {

        PARTICLES.forEach((type, particles) -> {
            Iterator<ScreenParticle> iterator = particles.iterator();
            while (iterator.hasNext()) {
                ScreenParticle particle = iterator.next();
                particle.tick();
                if (!particle.isAlive()) {
                    iterator.remove();
                }
            }
        });
    }
    public static final Tesselator TESSELATOR = new Tesselator();

    public static void renderParticles(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            PARTICLES.forEach((type, particles) -> {
                event.getMatrixStack().pushPose();
                type.begin(TESSELATOR.getBuilder(), Minecraft.getInstance().textureManager);
                particles.forEach(p -> p.render(TESSELATOR.getBuilder(), event));
                type.end(TESSELATOR);
                event.getMatrixStack().popPose();
            });
        }
    }

    @SuppressWarnings("ALL")
    public static <T extends ScreenParticleOptions> ScreenParticle addParticle(T options, double pX, double pY, double pXSpeed, double pYSpeed) {
        Minecraft minecraft = Minecraft.getInstance();
        ScreenParticleType<T> type = (ScreenParticleType<T>) options.type;
        ScreenParticleType.ParticleProvider<T> provider = type.provider;
        ScreenParticle particle = provider.createParticle(minecraft.level, options, pX, pY, pXSpeed, pYSpeed);
        ArrayList<ScreenParticle> list = PARTICLES.computeIfAbsent(particle.getRenderType(), (a) -> new ArrayList<>());
        list.add(particle);
        return particle;
    }
}