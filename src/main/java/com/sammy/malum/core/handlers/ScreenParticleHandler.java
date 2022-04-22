package com.sammy.malum.core.handlers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix4f;
import com.sammy.malum.compability.jei.JeiCompat;
import com.sammy.malum.core.systems.rendering.particle.screen.GenericScreenParticle;
import com.sammy.malum.core.systems.rendering.particle.screen.ScreenParticleOptions;
import com.sammy.malum.core.systems.rendering.particle.screen.ScreenParticleType;
import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.malum.core.systems.rendering.particle.screen.emitter.ParticleEmitter;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;

import java.util.*;
import java.util.stream.Stream;

import static com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle.RenderOrder.*;

public class ScreenParticleHandler {

    public static Map<Pair<ParticleRenderType, ScreenParticle.RenderOrder>, ArrayList<ScreenParticle>> PARTICLES = new HashMap<>();
    public static ArrayList<StackTracker> RENDERED_STACKS = new ArrayList<>();
    public static Map<Item, ParticleEmitter> EMITTERS = new HashMap<>();
    public static final Tesselator TESSELATOR = new Tesselator();
    public static boolean canSpawnParticles;
    public static boolean renderingHotbar;

    public static void clientTick(TickEvent.ClientTickEvent event) {
        PARTICLES.forEach((pair, particles) -> {
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
                    ScreenParticle.RenderOrder renderOrder = AFTER_EVERYTHING;
                    Screen screen = minecraft.screen;
                    if (screen != null) {
                        if (!JeiCompat.LOADED || !JeiCompat.LoadedOnly.isRecipesUi(screen)) {
                            renderOrder = BEFORE_TOOLTIPS;
                        }
                        if (renderingHotbar) {
                            renderOrder = BEFORE_UI;
                        }
                    }
                    Matrix4f last = posestack.last().pose();
                    float x = last.m03;
                    float y = last.m13;
                    if (canSpawnParticles) {
                        emitter.tick(stack, x, y, renderOrder);
                    }
                    RENDERED_STACKS.add(new StackTracker(stack, renderOrder, x, y));
                }
            }
        }
    }

    public static void renderParticles(TickEvent.RenderTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            Screen screen = Minecraft.getInstance().screen;
            if (screen instanceof IRecipesGui) {
                renderParticles(AFTER_EVERYTHING);
            }
            if (screen == null || screen instanceof ChatScreen || screen instanceof GameModeSwitcherScreen) {
                renderParticles(AFTER_EVERYTHING, BEFORE_UI);
            }
            RENDERED_STACKS.clear();
            canSpawnParticles = false;
        }
    }

    public static void renderParticles(ScreenParticle.RenderOrder... renderOrders) {
        PARTICLES.forEach((pair, particles) -> {
            ParticleRenderType type = pair.getFirst();
            if (Arrays.stream(renderOrders).anyMatch(o -> o.equals(pair.getSecond()))) {
                type.begin(TESSELATOR.getBuilder(), Minecraft.getInstance().textureManager);
                for (ScreenParticle next : particles) {
                    if (next instanceof GenericScreenParticle genericScreenParticle) {
                        genericScreenParticle.trackStack();
                    }
                    next.render(TESSELATOR.getBuilder());
                }
                type.end(TESSELATOR);
            }
        });
    }

    @SuppressWarnings("ALL")
    public static <T extends ScreenParticleOptions> ScreenParticle addParticle(T options, double pX, double pY, double pXSpeed, double pYSpeed) {
        Minecraft minecraft = Minecraft.getInstance();
        ScreenParticleType<T> type = (ScreenParticleType<T>) options.type;
        ScreenParticleType.ParticleProvider<T> provider = type.provider;
        ScreenParticle particle = provider.createParticle(minecraft.level, options, pX, pY, pXSpeed, pYSpeed);
        ArrayList<ScreenParticle> list = PARTICLES.computeIfAbsent(Pair.of(particle.getRenderType(), particle.renderOrder), (a) -> new ArrayList<>());
        list.add(particle);
        return particle;
    }

    public static void wipeParticles(ScreenParticle.RenderOrder... renderOrders) {
        PARTICLES.forEach((pair, particles) -> {
            if (!particles.isEmpty()) {
                if (renderOrders.length == 0 || Arrays.stream(renderOrders).anyMatch(o -> o.equals(pair.getSecond()))) {
                    particles.clear();
                }
            }
        });
    }

    public static void registerItemParticleEmitter(Item item, ParticleEmitter.EmitterSupplier emitter) {
        EMITTERS.put(item, new ParticleEmitter(emitter));
    }

    public static void registerItemParticleEmitter(ParticleEmitter.EmitterSupplier emitter, Item... items) {
        for (Item item : items) {
            EMITTERS.put(item, new ParticleEmitter(emitter));
        }
    }

    public record StackTracker(ItemStack stack, ScreenParticle.RenderOrder order, float xOrigin, float yOrigin) {
    }
}