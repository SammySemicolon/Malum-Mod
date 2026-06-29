package com.sammy.malum.client.scarf;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.config.ClientConfig;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.client.event.*;
import org.joml.*;
import team.lodestar.lodestone.handlers.LodestoneRenderHandler;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.awt.*;
import java.lang.Math;
import java.util.*;
import java.util.List;
import java.util.function.*;

public class ScarfRenderHandler {
    public static final WeakHashMap<LivingEntity, List<ScarfRenderData>> SCARF_DATA = new WeakHashMap<>();

    public static void tickScarfData(ClientTickEvent event) {
        for (Map.Entry<LivingEntity, List<ScarfRenderData>> entry : SCARF_DATA.entrySet()) {
            ArrayList<ScarfRenderData> toRemove = new ArrayList<>();
            List<ScarfRenderData> scarfList = entry.getValue();
            for (ScarfRenderData data : scarfList) {
                LivingEntity entity = entry.getKey();
                data.tick(entity);
                if (!data.isValid(entity)) {
                    toRemove.add(data);
                }
            }
            toRemove.forEach(scarfList::remove);
        }
    }

    public static void renderScarfData(RenderLevelStageEvent event) {
        float partialTicks = event.getPartialTick().getGameTimeDeltaPartialTick(true);
        renderScarfData(partialTicks);
    }

    public static void renderScarfData(float partialTicks) {
        for (Map.Entry<LivingEntity, List<ScarfRenderData>> entry : SCARF_DATA.entrySet()) {
            List<ScarfRenderData> scarfList = entry.getValue();
            LivingEntity entity = entry.getKey();
            for (ScarfRenderData data : scarfList) {
                data.render(entity, partialTicks);
            }
        }
    }

    public static void addScarfRenderer(LivingEntity living, Consumer<Consumer<ScarfRenderData>> consumer) {
        if (SCARF_DATA.containsKey(living)) {
            if (SCARF_DATA.get(living).isEmpty()) {
                SCARF_DATA.remove(living);
            }
        }
        if (!SCARF_DATA.containsKey(living)) {
            List<ScarfRenderData> scarfList = new ArrayList<>();
            Consumer<ScarfRenderData> acceptor = scarfList::add;
            consumer.accept(acceptor);
            SCARF_DATA.put(living, scarfList);
        }
    }

    public static class ScarfRenderData {
        public final RenderTypeToken token;
        public final TrailPointBuilder points;
        public Supplier<Boolean> isValid = () -> true;

        public Color primaryColor = Color.WHITE;
        public Color secondaryColor = Color.WHITE;

        public float horizontalOffset;
        public float scale = 1;
        public float endingScale = 1;
        public float alpha = 1;

        public ScarfRenderData(RenderTypeToken token, int trailLength) {
            this.token = token;
            this.points = new TrailPointBuilder(trailLength);
        }

        public boolean isValid(LivingEntity entity) {
            return entity.isAlive() && !entity.isRemoved() && !entity.isDeadOrDying() && entity.isAddedToLevel() && isValid.get();
        }

        public ScarfRenderData setPrimaryColor(Color primaryColor) {
            this.primaryColor = primaryColor;
            return this;
        }

        public ScarfRenderData setSecondaryColor(Color secondaryColor) {
            this.secondaryColor = secondaryColor;
            return this;
        }

        public ScarfRenderData setPredicate(Supplier<Boolean> isValid) {
            this.isValid = isValid;
            return this;
        }

        public ScarfRenderData setHorizontalOffset(float horizontalOffset) {
            this.horizontalOffset = horizontalOffset;
            return this;
        }

        public ScarfRenderData setScale(float scale) {
            this.scale = scale;
            return this;
        }

        public ScarfRenderData setEndingScale(float endingScale) {
            this.endingScale = endingScale;
            return this;
        }

        public ScarfRenderData setAlpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        public void render(LivingEntity entity, float partialTicks) {
            var minecraft = Minecraft.getInstance();
            float alpha = this.alpha;
            if (entity.equals(minecraft.cameraEntity)) {
                if (minecraft.options.getCameraType().isFirstPerson()) {
                    alpha *= ClientConfig.SCARF_OPACITY.getConfigValue().floatValue();
                }
            }
            if (alpha <= 0) {
                return;
            }
            var blockpos = entity.blockPosition().above(2);
            int light = entity.level().hasChunkAt(blockpos) ? LevelRenderer.getLightColor(entity.level(), blockpos) : 0;
            var renderType = LodestoneRenderTypes.TEXTURE_FADE.apply(token);
            var builder = VFXBuilders.createWorld().setRenderType(renderType).setLight(light).setAlpha(alpha);
            var scarfStart = getScarfStart(entity, partialTicks);
            points.setOrigin(scarfStart);
            //TODO: actually giving it the partial tick makes it jitter when the player is stationary, but not doing so makes it jitter when the player is moving... for whatever reason
            builder.usePartialTicks(0).renderTrail(points,
                    f -> Mth.lerp(f, endingScale, scale),
                    f -> builder.setColor(ColorHelper.colorLerp(Easing.LINEAR, Mth.floor(f * 4) / 4f, secondaryColor, primaryColor))
            );
        }

        public void tick(LivingEntity entity) {
            var movement = getScarfPointMovement(entity);
            points.addTrailPoint(new TrailPoint(getScarfStart(entity, 0.5f)));
            points.run(t -> t.move(movement));
            final List<TrailPoint> list = points.getTrailPoints();
            if (list.size() > 2) {
                float age = points.getTrailLength();
                for (int i = 0; i < list.size() - 1; i++) {
                    var currentPoint = list.get(i);
                    var nextPoint = list.get(i + 1);
                    float delta = Mth.clamp(currentPoint.getAge() / age * 4, 0, 1);
                    var currentPos = currentPoint.getPosition();
                    var nextPos = nextPoint.getPosition();
                    float lerpX = (float) Mth.lerp(delta, currentPos.x, nextPos.x);
                    float lerpY = (float) Mth.lerp(delta, currentPos.y, nextPos.y);
                    float lerpZ = (float) Mth.lerp(delta, currentPos.z, nextPos.z);
                    currentPoint.setPosition(new Vec3(lerpX, lerpY, lerpZ));
                }
            }
            points.tickTrailPoints();
        }

        public Vec3 getScarfPointMovement(LivingEntity entity) {
            var lookDirection = entity.getForward().scale(Mth.clamp(entity.getDeltaMovement().length(), 0, 1));
            double y = -0.02f;
            if (lookDirection.length() < 0.1f) {
                lookDirection = entity.getForward().scale(0.3f);
                y = -0.08f;
            }
            double x = lookDirection.x * -0.1f;
            double z = lookDirection.z * -0.1f;
            return new Vec3(x, y, z);
        }

        public Vec3 getScarfStart(LivingEntity entity, float partialTicks) {
            float xLook = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
            float yLook = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
            var lookDirection = Vec3.directionFromRotation(new Vec2(xLook, yLook));
            float upwardsOffset = entity.getBbHeight() * 0.8f;
            var eyePosition = entity.getPosition(partialTicks).add(0, upwardsOffset, 0);
            float yRot = ((float) (Mth.atan2(lookDirection.x, lookDirection.z) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            var left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            final Vec3 offsetPosition = eyePosition.subtract(lookDirection.scale(0.2f).add(left.scale(horizontalOffset)));
            float angle = ((entity.level().getGameTime()+partialTicks) * 0.05f) % 6.28f;
            float offsetStrength = 0.01f;
            float xOffset = Mth.sin(angle * 4) * offsetStrength;
            float yOffset = Mth.sin(angle * 4) * offsetStrength;
            float zOffset = Mth.cos(angle * 4) * offsetStrength;
            return offsetPosition.add(xOffset, yOffset, zOffset);
        }
    }
}
