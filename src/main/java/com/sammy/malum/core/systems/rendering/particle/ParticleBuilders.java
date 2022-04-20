package com.sammy.malum.core.systems.rendering.particle;

import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import com.sammy.malum.core.handlers.ScreenParticleHandler;
import com.sammy.malum.core.systems.easing.Easing;
import com.sammy.malum.core.systems.rendering.particle.screen.ScreenParticleOptions;
import com.sammy.malum.core.systems.rendering.particle.screen.ScreenParticleType;
import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.malum.core.systems.rendering.particle.screen.emitter.ParticleEmitter;
import com.sammy.malum.core.systems.rendering.particle.world.WorldParticleOptions;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.Random;

public class ParticleBuilders {

    public static WorldParticleBuilder create(ParticleType<?> type) {
        return new WorldParticleBuilder(type);
    }

    public static WorldParticleBuilder create(RegistryObject<?> type) {
        return new WorldParticleBuilder((ParticleType<?>) type.get());
    }

    public static class WorldParticleBuilder {
        static Random random = new Random();

        ParticleType<?> type;
        WorldParticleOptions data;
        double vx = 0, vy = 0, vz = 0;
        double dx = 0, dy = 0, dz = 0;
        double maxXSpeed = 0, maxYSpeed = 0, maxZSpeed = 0;
        double maxXDist = 0, maxYDist = 0, maxZDist = 0;

        protected WorldParticleBuilder(ParticleType<?> type) {
            this.type = type;
            this.data = new WorldParticleOptions(type);
        }

        public WorldParticleBuilder overwriteAnimator(SimpleParticleOptions.Animator animator) {
            data.animator = animator;
            return this;
        }
        public WorldParticleBuilder overwriteRenderType(ParticleRenderType renderType) {
            data.renderType = renderType;
            return this;
        }
        public WorldParticleBuilder setColorEasing(Easing easing) {
            data.colorCurveEasing = easing;
            return this;
        }

        public WorldParticleBuilder setColorCurveMultiplier(float colorCurveMultiplier) {
            data.colorCurveMultiplier = colorCurveMultiplier;
            return this;
        }

        public WorldParticleBuilder setColor(float r, float g, float b) {
            return setColor(r, g, b, data.alpha1, r, g, b, data.alpha2);
        }

        public WorldParticleBuilder setColor(float r, float g, float b, float a) {
            return setColor(r, g, b, a, r, g, b, a);
        }

        public WorldParticleBuilder setColor(float r, float g, float b, float a1, float a2) {
            return setColor(r, g, b, a1, r, g, b, a2);
        }

        public WorldParticleBuilder setColor(float r1, float g1, float b1, float r2, float g2, float b2) {
            return setColor(r1, g1, b1, data.alpha1, r2, g2, b2, data.alpha2);
        }

        public WorldParticleBuilder setColor(float r1, float g1, float b1, float r2, float g2, float b2, float a) {
            return setColor(r1, g1, b1, a, r2, g2, b2, a);
        }

        public WorldParticleBuilder setColor(float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
            data.r1 = r1;
            data.g1 = g1;
            data.b1 = b1;
            data.alpha1 = a1;
            data.r2 = r2;
            data.g2 = g2;
            data.b2 = b2;
            data.alpha2 = a2;
            return this;
        }

        public WorldParticleBuilder setColor(Color c1, Color c2) {
            data.r1 = c1.getRed() / 255f;
            data.g1 = c1.getGreen() / 255f;
            data.b1 = c1.getBlue() / 255f;
            data.r2 = c2.getRed() / 255f;
            data.g2 = c2.getGreen() / 255f;
            data.b2 = c2.getBlue() / 255f;
            return this;
        }

        public WorldParticleBuilder setAlphaEasing(Easing startEasing, Easing endEasing) {
            data.alphaCurveStartEasing = startEasing;
            data.alphaCurveEndEasing = endEasing;
            return this;
        }

        public WorldParticleBuilder setAlphaEasing(Easing easing) {
            data.alphaCurveStartEasing = easing;
            return this;
        }

        public WorldParticleBuilder setAlphaCurveMultiplier(float alphaCurveMultiplier) {
            data.alphaCurveMultiplier = alphaCurveMultiplier;
            return this;
        }

        public WorldParticleBuilder setAlpha(float alpha) {
            return setAlpha(alpha, alpha);
        }

        public WorldParticleBuilder setAlpha(float alpha1, float alpha2) {
            return setAlpha(alpha1, alpha2, alpha2);
        }

        public WorldParticleBuilder setAlpha(float alpha1, float alpha2, float alpha3) {
            data.alpha1 = alpha1;
            data.alpha2 = alpha2;
            data.alpha3 = alpha3;
            return this;
        }

        public WorldParticleBuilder setScaleEasing(Easing startEasing, Easing endEasing) {
            data.scaleCurveStartEasing = startEasing;
            data.scaleCurveEndEasing = endEasing;
            return this;
        }

        public WorldParticleBuilder setScaleEasing(Easing easing) {
            data.scaleCurveStartEasing = easing;
            return this;
        }

        public WorldParticleBuilder setScaleCurveMultiplier(float scaleCurveMultiplier) {
            data.scaleCurveMultiplier = scaleCurveMultiplier;
            return this;
        }

        public WorldParticleBuilder setScale(float scale) {
            return setScale(scale, scale);
        }

        public WorldParticleBuilder setScale(float scale1, float scale2) {
            return setScale(scale1, scale2, scale2);
        }

        public WorldParticleBuilder setScale(float scale1, float scale2, float scale3) {
            data.scale1 = scale1;
            data.scale2 = scale2;
            data.scale3 = scale3;
            return this;
        }

        public WorldParticleBuilder enableGravity() {
            data.gravity = true;
            return this;
        }

        public WorldParticleBuilder disableGravity() {
            data.gravity = false;
            return this;
        }

        public WorldParticleBuilder enableNoClip() {
            data.noClip = true;
            return this;
        }

        public WorldParticleBuilder disableNoClip() {
            data.noClip = false;
            return this;
        }

        public WorldParticleBuilder setSpinEasing(Easing easing) {
            data.spinEasing = easing;
            return this;
        }

        public WorldParticleBuilder setSpinCurveMultiplier(float spinCurveMultiplier) {
            data.spinCurveMultiplier = spinCurveMultiplier;
            return this;
        }

        public WorldParticleBuilder setSpinOffset(float spinOffset) {
            data.spinOffset = spinOffset;
            return this;
        }

        public WorldParticleBuilder setSpin(float spin) {
            return setSpin(spin, spin);
        }

        public WorldParticleBuilder setSpin(float spin1, float spin2) {
            data.spin1 = spin1;
            data.spin2 = spin2;
            return this;
        }

        public WorldParticleBuilder setLifetime(int lifetime) {
            data.lifetime = lifetime;
            return this;
        }

        public WorldParticleBuilder randomMotion(double maxSpeed) {
            return randomMotion(maxSpeed, maxSpeed, maxSpeed);
        }

        public WorldParticleBuilder randomMotion(double maxHSpeed, double maxVSpeed) {
            return randomMotion(maxHSpeed, maxVSpeed, maxHSpeed);
        }

        public WorldParticleBuilder randomMotion(double maxXSpeed, double maxYSpeed, double maxZSpeed) {
            this.maxXSpeed = maxXSpeed;
            this.maxYSpeed = maxYSpeed;
            this.maxZSpeed = maxZSpeed;
            return this;
        }

        public WorldParticleBuilder addMotion(double vx, double vy, double vz) {
            this.vx += vx;
            this.vy += vy;
            this.vz += vz;
            return this;
        }

        public WorldParticleBuilder setMotion(double vx, double vy, double vz) {
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
            return this;
        }

        public WorldParticleBuilder setForcedMotion(Vector3f startingMotion, Vector3f endingMotion) {
            data.forcedMotion = true;
            data.startingMotion = startingMotion;
            data.endingMotion = endingMotion;
            return this;
        }

        public WorldParticleBuilder disableForcedMotion() {
            data.forcedMotion = false;
            return this;
        }

        public WorldParticleBuilder randomOffset(double maxDistance) {
            return randomOffset(maxDistance, maxDistance, maxDistance);
        }

        public WorldParticleBuilder randomOffset(double maxHDist, double maxVDist) {
            return randomOffset(maxHDist, maxVDist, maxHDist);
        }

        public WorldParticleBuilder randomOffset(double maxXDist, double maxYDist, double maxZDist) {
            this.maxXDist = maxXDist;
            this.maxYDist = maxYDist;
            this.maxZDist = maxZDist;
            return this;
        }

        public WorldParticleBuilder spawnCircle(Level level, double x, double y, double z, double distance, double currentCount, double totalCount) {
            double xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed, zSpeed = random.nextFloat() * maxZSpeed;
            double theta = (Math.PI * 2) / totalCount;
            double finalAngle = (currentCount / totalCount) + (theta * currentCount);
            double dx2 = (distance * Math.cos(finalAngle));
            double dz2 = (distance * Math.sin(finalAngle));

            Vector3d vector2f = new Vector3d(dx2, 0, dz2);
            this.vx = vector2f.x * xSpeed;
            this.vz = vector2f.z * zSpeed;

            double yaw2 = random.nextFloat() * Math.PI * 2, pitch2 = random.nextFloat() * Math.PI - Math.PI / 2, xDist = random.nextFloat() * maxXDist, yDist = random.nextFloat() * maxYDist, zDist = random.nextFloat() * maxZDist;
            this.dx = Math.sin(yaw2) * Math.cos(pitch2) * xDist;
            this.dy = Math.sin(pitch2) * yDist;
            this.dz = Math.cos(yaw2) * Math.cos(pitch2) * zDist;
            level.addParticle(data, x + dx + dx2, y + dy, z + dz + dz2, vx, ySpeed, vz);
            return this;
        }

        public WorldParticleBuilder spawn(Level level, double x, double y, double z) {
            double yaw = random.nextFloat() * Math.PI * 2, pitch = random.nextFloat() * Math.PI - Math.PI / 2, xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed, zSpeed = random.nextFloat() * maxZSpeed;
            this.vx += Math.sin(yaw) * Math.cos(pitch) * xSpeed;
            this.vy += Math.sin(pitch) * ySpeed;
            this.vz += Math.cos(yaw) * Math.cos(pitch) * zSpeed;
            double yaw2 = random.nextFloat() * Math.PI * 2, pitch2 = random.nextFloat() * Math.PI - Math.PI / 2, xDist = random.nextFloat() * maxXDist, yDist = random.nextFloat() * maxYDist, zDist = random.nextFloat() * maxZDist;
            this.dx = Math.sin(yaw2) * Math.cos(pitch2) * xDist;
            this.dy = Math.sin(pitch2) * yDist;
            this.dz = Math.cos(yaw2) * Math.cos(pitch2) * zDist;

            level.addParticle(data, x + dx, y + dy, z + dz, vx, vy, vz);
            return this;
        }

        public WorldParticleBuilder evenlySpawnAtEdges(Level level, BlockPos pos) {
            for (Direction direction : Direction.values()) {
                double yaw = random.nextFloat() * Math.PI * 2, pitch = random.nextFloat() * Math.PI - Math.PI / 2, xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed, zSpeed = random.nextFloat() * maxZSpeed;
                this.vx += Math.sin(yaw) * Math.cos(pitch) * xSpeed;
                this.vy += Math.sin(pitch) * ySpeed;
                this.vz += Math.cos(yaw) * Math.cos(pitch) * zSpeed;

                Direction.Axis direction$axis = direction.getAxis();
                double d0 = 0.5625D;
                this.dx = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getStepX() : (double) random.nextFloat();
                this.dy = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getStepY() : (double) random.nextFloat();
                this.dz = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getStepZ() : (double) random.nextFloat();

                level.addParticle(data, pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz, vx, vy, vz);

            }
            return this;
        }

        public WorldParticleBuilder evenlySpawnAtEdges(Level level, BlockPos pos, Direction... directions) {
            for (Direction direction : directions) {
                double yaw = random.nextFloat() * Math.PI * 2, pitch = random.nextFloat() * Math.PI - Math.PI / 2, xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed, zSpeed = random.nextFloat() * maxZSpeed;
                this.vx += Math.sin(yaw) * Math.cos(pitch) * xSpeed;
                this.vy += Math.sin(pitch) * ySpeed;
                this.vz += Math.cos(yaw) * Math.cos(pitch) * zSpeed;

                Direction.Axis direction$axis = direction.getAxis();
                double d0 = 0.5625D;
                this.dx = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getStepX() : (double) random.nextFloat();
                this.dy = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getStepY() : (double) random.nextFloat();
                this.dz = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getStepZ() : (double) random.nextFloat();

                level.addParticle(data, pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz, vx, vy, vz);

            }
            return this;
        }

        public WorldParticleBuilder spawnAtEdges(Level level, BlockPos pos) {
            Direction direction = Direction.values()[level.random.nextInt(Direction.values().length)];
            double yaw = random.nextFloat() * Math.PI * 2, pitch = random.nextFloat() * Math.PI - Math.PI / 2, xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed, zSpeed = random.nextFloat() * maxZSpeed;
            this.vx += Math.sin(yaw) * Math.cos(pitch) * xSpeed;
            this.vy += Math.sin(pitch) * ySpeed;
            this.vz += Math.cos(yaw) * Math.cos(pitch) * zSpeed;

            Direction.Axis direction$axis = direction.getAxis();
            double d0 = 0.5625D;
            this.dx = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getStepX() : (double) random.nextFloat();
            this.dy = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getStepY() : (double) random.nextFloat();
            this.dz = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getStepZ() : (double) random.nextFloat();

            level.addParticle(data, pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz, vx, vy, vz);
            return this;
        }

        public WorldParticleBuilder repeat(Level level, double x, double y, double z, int n) {
            for (int i = 0; i < n; i++) spawn(level, x, y, z);
            return this;
        }

        public WorldParticleBuilder repeatEdges(Level level, BlockPos pos, int n) {
            for (int i = 0; i < n; i++) spawnAtEdges(level, pos);
            return this;
        }

        public WorldParticleBuilder evenlyRepeatEdges(Level level, BlockPos pos, int n) {
            for (int i = 0; i < n; i++) evenlySpawnAtEdges(level, pos);
            return this;
        }

        public WorldParticleBuilder evenlyRepeatEdges(Level level, BlockPos pos, int n, Direction... directions) {
            for (int i = 0; i < n; i++) evenlySpawnAtEdges(level, pos, directions);
            return this;
        }

        public WorldParticleBuilder repeatCircle(Level level, double x, double y, double z, double distance, int times) {
            for (int i = 0; i < times; i++) spawnCircle(level, x, y, z, distance, i, times);
            return this;
        }
    }

    public static ScreenParticleBuilder create(ScreenParticleType<?> type) {
        return new ScreenParticleBuilder(type);
    }

    public static class ScreenParticleBuilder {
        static Random random = new Random();

        ScreenParticleType<?> type;
        ScreenParticleOptions data;
        double vx = 0, vy = 0;
        double dx = 0, dy = 0;
        double maxXSpeed = 0, maxYSpeed = 0;
        double maxXDist = 0, maxYDist = 0;

        protected ScreenParticleBuilder(ScreenParticleType<?> type) {
            this.type = type;
            this.data = new ScreenParticleOptions(type);
        }
        public ScreenParticleBuilder overwriteAnimator(SimpleParticleOptions.Animator animator) {
            data.animator = animator;
            return this;
        }
        public ScreenParticleBuilder overwriteRenderType(ParticleRenderType renderType) {
            data.renderType = renderType;
            return this;
        }
        public ScreenParticleBuilder overwriteRenderOrder(ScreenParticle.RenderOrder renderOrder) {
            data.renderOrder = renderOrder;
            return this;
        }
        public ScreenParticleBuilder centerOnStack(ItemStack stack) {
            data.stack = stack;
            return this;
        }
        public ScreenParticleBuilder centerOnStack(ItemStack stack, float xOffset, float yOffset) {
            data.stack = stack;
            data.xOffset = xOffset;
            data.yOffset = yOffset;
            return this;
        }
        public ScreenParticleBuilder setColorEasing(Easing easing) {
            data.colorCurveEasing = easing;
            return this;
        }

        public ScreenParticleBuilder setColorCurveMultiplier(float colorCurveMultiplier) {
            data.colorCurveMultiplier = colorCurveMultiplier;
            return this;
        }

        public ScreenParticleBuilder setColor(float r, float g, float b) {
            return setColor(r, g, b, data.alpha1, r, g, b, data.alpha2);
        }

        public ScreenParticleBuilder setColor(float r, float g, float b, float a) {
            return setColor(r, g, b, a, r, g, b, a);
        }

        public ScreenParticleBuilder setColor(float r, float g, float b, float a1, float a2) {
            return setColor(r, g, b, a1, r, g, b, a2);
        }

        public ScreenParticleBuilder setColor(float r1, float g1, float b1, float r2, float g2, float b2) {
            return setColor(r1, g1, b1, data.alpha1, r2, g2, b2, data.alpha2);
        }

        public ScreenParticleBuilder setColor(float r1, float g1, float b1, float r2, float g2, float b2, float a) {
            return setColor(r1, g1, b1, a, r2, g2, b2, a);
        }

        public ScreenParticleBuilder setColor(float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
            data.r1 = r1;
            data.g1 = g1;
            data.b1 = b1;
            data.alpha1 = a1;
            data.r2 = r2;
            data.g2 = g2;
            data.b2 = b2;
            data.alpha2 = a2;
            return this;
        }

        public ScreenParticleBuilder setColor(Color c1, Color c2) {
            data.r1 = c1.getRed() / 255f;
            data.g1 = c1.getGreen() / 255f;
            data.b1 = c1.getBlue() / 255f;
            data.r2 = c2.getRed() / 255f;
            data.g2 = c2.getGreen() / 255f;
            data.b2 = c2.getBlue() / 255f;
            return this;
        }

        public ScreenParticleBuilder setAlphaEasing(Easing startEasing, Easing endEasing) {
            data.alphaCurveStartEasing = startEasing;
            data.alphaCurveEndEasing = endEasing;
            return this;
        }

        public ScreenParticleBuilder setAlphaEasing(Easing easing) {
            data.alphaCurveStartEasing = easing;
            return this;
        }

        public ScreenParticleBuilder setAlphaCurveMultiplier(float alphaCurveMultiplier) {
            data.alphaCurveMultiplier = alphaCurveMultiplier;
            return this;
        }

        public ScreenParticleBuilder setAlpha(float alpha) {
            return setAlpha(alpha, alpha);
        }

        public ScreenParticleBuilder setAlpha(float alpha1, float alpha2) {
            return setAlpha(alpha1, alpha2, alpha2);
        }

        public ScreenParticleBuilder setAlpha(float alpha1, float alpha2, float alpha3) {
            data.alpha1 = alpha1;
            data.alpha2 = alpha2;
            data.alpha3 = alpha3;
            return this;
        }

        public ScreenParticleBuilder setScaleEasing(Easing startEasing, Easing endEasing) {
            data.scaleCurveStartEasing = startEasing;
            data.scaleCurveEndEasing = endEasing;
            return this;
        }

        public ScreenParticleBuilder setScaleEasing(Easing easing) {
            data.scaleCurveStartEasing = easing;
            return this;
        }

        public ScreenParticleBuilder setScaleCurveMultiplier(float scaleCurveMultiplier) {
            data.scaleCurveMultiplier = scaleCurveMultiplier;
            return this;
        }

        public ScreenParticleBuilder setScale(float scale) {
            return setScale(scale, scale);
        }

        public ScreenParticleBuilder setScale(float scale1, float scale2) {
            return setScale(scale1, scale2, scale2);
        }

        public ScreenParticleBuilder setScale(float scale1, float scale2, float scale3) {
            data.scale1 = scale1;
            data.scale2 = scale2;
            data.scale3 = scale3;
            return this;
        }

        public ScreenParticleBuilder enableGravity() {
            data.gravity = true;
            return this;
        }

        public ScreenParticleBuilder disableGravity() {
            data.gravity = false;
            return this;
        }

        public ScreenParticleBuilder enableNoClip() {
            data.noClip = true;
            return this;
        }

        public ScreenParticleBuilder disableNoClip() {
            data.noClip = false;
            return this;
        }

        public ScreenParticleBuilder setSpinEasing(Easing easing) {
            data.spinEasing = easing;
            return this;
        }

        public ScreenParticleBuilder setSpinCurveMultiplier(float spinCurveMultiplier) {
            data.spinCurveMultiplier = spinCurveMultiplier;
            return this;
        }

        public ScreenParticleBuilder setSpinOffset(float spinOffset) {
            data.spinOffset = spinOffset;
            return this;
        }

        public ScreenParticleBuilder setSpin(float spin) {
            return setSpin(spin, spin);
        }

        public ScreenParticleBuilder setSpin(float spin1, float spin2) {
            data.spin1 = spin1;
            data.spin2 = spin2;
            return this;
        }

        public ScreenParticleBuilder setLifetime(int lifetime) {
            data.lifetime = lifetime;
            return this;
        }

        public ScreenParticleBuilder setMotionCurveMultiplier(float motionCurveMultiplier) {
            data.motionCurveMultiplier = motionCurveMultiplier;
            return this;
        }
        public ScreenParticleBuilder randomMotion(double maxSpeed) {
            return randomMotion(maxSpeed, maxSpeed);
        }

        public ScreenParticleBuilder randomMotion(double maxXSpeed, double maxYSpeed) {
            this.maxXSpeed = maxXSpeed;
            this.maxYSpeed = maxYSpeed;
            return this;
        }

        public ScreenParticleBuilder addMotion(double vx, double vy) {
            this.vx += vx;
            this.vy += vy;
            return this;
        }

        public ScreenParticleBuilder setMotion(double vx, double vy) {
            this.vx = vx;
            this.vy = vy;
            return this;
        }

        public ScreenParticleBuilder setForcedMotion(Vector3f startingMotion, Vector3f endingMotion) {
            data.forcedMotion = true;
            data.startingMotion = startingMotion;
            data.endingMotion = endingMotion;
            return this;
        }

        public ScreenParticleBuilder disableForcedMotion() {
            data.forcedMotion = false;
            return this;
        }

        public ScreenParticleBuilder randomOffset(double maxDistance) {
            return randomOffset(maxDistance, maxDistance);
        }

        public ScreenParticleBuilder randomOffset(double maxXDist, double maxYDist) {
            this.maxXDist = maxXDist;
            this.maxYDist = maxYDist;
            return this;
        }

        public ScreenParticleBuilder spawnCircle(double x, double y, double distance, double currentCount, double totalCount) {
            double xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed;
            double theta = (Math.PI * 2) / totalCount;
            double finalAngle = (currentCount / totalCount) + (theta * currentCount);
            double dx2 = (distance * Math.cos(finalAngle));
            double dz2 = (distance * Math.sin(finalAngle));

            Vector3d vector2f = new Vector3d(dx2, 0, dz2);
            this.vx = vector2f.x * xSpeed;

            double yaw2 = random.nextFloat() * Math.PI * 2, pitch2 = random.nextFloat() * Math.PI - Math.PI / 2, xDist = random.nextFloat() * maxXDist, yDist = random.nextFloat() * maxYDist;
            this.dx = Math.sin(yaw2) * Math.cos(pitch2) * xDist;
            this.dy = Math.sin(pitch2) * yDist;
            data.xOrigin = (float) x;
            data.yOrigin = (float) y;
            ScreenParticleHandler.addParticle(data, x + dx + dx2, y + dy + dz2, vx, ySpeed);
            return this;
        }

        public ScreenParticleBuilder spawn(double x, double y) {
            double yaw = random.nextFloat() * Math.PI * 2, pitch = random.nextFloat() * Math.PI - Math.PI / 2, xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed;
            this.vx += Math.sin(yaw) * Math.cos(pitch) * xSpeed;
            this.vy += Math.sin(pitch) * ySpeed;
            double yaw2 = random.nextFloat() * Math.PI * 2, pitch2 = random.nextFloat() * Math.PI - Math.PI / 2, xDist = random.nextFloat() * maxXDist, yDist = random.nextFloat() * maxYDist;
            this.dx = Math.sin(yaw2) * Math.cos(pitch2) * xDist;
            this.dy = Math.sin(pitch2) * yDist;
            data.xOrigin = (float) x;
            data.yOrigin = (float) y;
            ScreenParticleHandler.addParticle(data, x + dx, y + dy, vx, vy);
            return this;
        }

        public ScreenParticleBuilder repeat(double x, double y, int n) {
            for (int i = 0; i < n; i++) spawn(x, y);
            return this;
        }

        public ScreenParticleBuilder repeatCircle(double x, double y, double distance, int times) {
            for (int i = 0; i < times; i++) spawnCircle(x, y, distance, i, times);
            return this;
        }
    }
}