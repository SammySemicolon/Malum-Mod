package com.sammy.malum.core.handlers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;
import com.sammy.malum.core.systems.rendering.particle.screen.ScreenParticleOptions;
import com.sammy.malum.core.systems.rendering.particle.screen.ScreenParticleType;
import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.malum.core.systems.rendering.particle.screen.emitter.ParticleEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;

import java.util.*;

import static com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle.RenderOrder.*;

public class ScreenParticleHandler {

    public static Map<ParticleRenderType, ArrayList<ScreenParticle>> PARTICLES = new HashMap<>();
    public static Map<Item, ParticleEmitter> EMITTERS = new HashMap<>();
    public static final Tesselator TESSELATOR = new Tesselator();
    public static boolean canSpawnParticles;
    public static boolean renderingHotbar;

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
        canSpawnParticles = true;
    }

    public static void renderItem(ItemStack stack) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null && minecraft.player != null) {
            if (minecraft.isPaused()) {
                return;
            }
            if (!stack.isEmpty()) {
                ParticleEmitter emitter = ScreenParticleHandler.EMITTERS.get(stack.getItem());
                if (emitter != null) {
                    PoseStack posestack = RenderSystem.getModelViewStack();
                    Matrix4f last = posestack.last().pose();
                    float x = last.m03;
                    float y = last.m13;
                    if (canSpawnParticles) {
                        ScreenParticle.RenderOrder renderOrder = AFTER_EVERYTHING;
                        if (minecraft.screen != null) {
                            renderOrder = BEFORE_TOOLTIPS;
                            if (renderingHotbar) {
                                renderOrder = BEFORE_UI;
                            }
                        }
                        emitter.tick(stack, x, y, renderOrder);
                    }
                    emitter.render(stack, x, y);
                }
            }
        }
    }

    public static void renderParticles(TickEvent.RenderTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            if (Minecraft.getInstance().screen == null) {
                renderParticles(AFTER_EVERYTHING, BEFORE_UI);
            }
            canSpawnParticles = false;
        }
    }

    public static void renderParticles(ScreenParticle.RenderOrder... renderOrders) {
        PARTICLES.forEach((type, particles) -> {
            type.begin(TESSELATOR.getBuilder(), Minecraft.getInstance().textureManager);
            particles.forEach(p -> {
                if (Arrays.stream(renderOrders).anyMatch(o -> o.equals(p.getRenderOrder()))) {
                    p.render(TESSELATOR.getBuilder());
                }
            });
            type.end(TESSELATOR);
        });
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

    public static void registerItemParticleEmitter(Item item, ParticleEmitter.EmitterSupplier emitter) {
        EMITTERS.put(item, new ParticleEmitter(emitter));
    }

    public static void registerItemParticleEmitter(ParticleEmitter.EmitterSupplier emitter, Item... items) {
        for (Item item : items) {
            EMITTERS.put(item, new ParticleEmitter(emitter));
        }
    }
}