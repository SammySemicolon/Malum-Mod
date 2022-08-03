package com.sammy.malum.common.entity.nitrate;

import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.setup.content.entity.EntityRegistry;
import com.sammy.ortus.helpers.EntityHelper;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.ortus.systems.rendering.particle.ParticleRenderTypes;
import com.sammy.ortus.systems.rendering.particle.SimpleParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.util.Mth.nextFloat;

public class EthericNitrateEntity extends AbstractNitrateEntity {

    public static final Color FIRST_COLOR = SpiritTypeRegistry.INFERNAL_SPIRIT.getColor();
    public static final Color SECOND_COLOR = new Color(178, 28, 73);

    public EthericNitrateEntity(Level level) {
        super(EntityRegistry.ETHERIC_NITRATE.get(), level);
    }

    public EthericNitrateEntity(double x, double y, double z, Level level) {
        super(EntityRegistry.ETHERIC_NITRATE.get(), x, y, z, level);
    }

    public EthericNitrateEntity(LivingEntity owner, Level level) {
        super(EntityRegistry.ETHERIC_NITRATE.get(), owner, level);
    }

    @Override
    public int getPierce() {
        return 3;
    }

    @Override
    public float getExplosionRadius() {
        return 2.75f;
    }

    @Override
    public void spawnParticles() {
        double x = getX(), y = getY() + getYOffset(0) + 0.25f, z = getZ();
        Vec3 motion = getDeltaMovement();
        Vec3 norm = motion.normalize().scale(0.1f);
        float extraAlpha = (float) motion.length();
        float cycles = 4;
        Color firstColor = FIRST_COLOR.brighter();
        Random rand = level.getRandom();
        for (int i = 0; i < cycles; i++) {
            float pDelta = i / cycles;
            double lerpX = Mth.lerp(pDelta, x - motion.x, x) + norm.x * pDelta;
            double lerpY = Mth.lerp(pDelta, y - motion.y, y) + norm.y * pDelta;
            double lerpZ = Mth.lerp(pDelta, z - motion.z, z) + norm.z * pDelta;
            float alphaMultiplier = (0.20f + extraAlpha) * Math.min(1, windUp * 2);
            SpiritHelper.spawnSpiritParticles(level, lerpX, lerpY, lerpZ, alphaMultiplier, norm, firstColor, SECOND_COLOR);

            ParticleBuilders.create(OrtusParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(0.4f * alphaMultiplier, 0f)
                    .setLifetime(5 + rand.nextInt(4))
                    .setScale(0.25f + rand.nextFloat() * 0.1f, 0)
                    .setColor(FIRST_COLOR, SECOND_COLOR)
                    .setColorCoefficient(2f)
                    .setColorEasing(Easing.EXPO_OUT)
                    .randomOffset(0.05f)
                    .enableNoClip()
                    .addMotion(norm.x, norm.y, norm.z)
                    .randomMotion(0.02f, 0.02f)
                    .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.INVISIBLE)
                    .repeat(level, lerpX, lerpY, lerpZ, 1);

            ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.25f * alphaMultiplier, 0f)
                    .setLifetime(15 + rand.nextInt(4))
                    .setSpin(nextFloat(rand, 0.05f, 0.1f))
                    .setScale(0.05f + rand.nextFloat() * 0.025f, 0)
                    .setColor(FIRST_COLOR, SECOND_COLOR)
                    .setColorEasing(Easing.EXPO_OUT)
                    .setColorCoefficient(1.5f)
                    .randomOffset(0.02f)
                    .enableNoClip()
                    .addMotion(norm.x, norm.y, norm.z)
                    .randomMotion(0.01f, 0.01f)
                    .repeat(level, x, y, z, 1)
                    .setAlpha(0.2f * alphaMultiplier, 0f)
                    .setLifetime(10 + rand.nextInt(2))
                    .setSpin(nextFloat(rand, 0.05f, 0.1f))
                    .setScale(0.15f + rand.nextFloat() * 0.05f, 0f)
                    .randomMotion(0.01f, 0.01f)
                    .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.INVISIBLE)
                    .repeat(level, lerpX, lerpY, lerpZ, 1);

            ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.3f, 0.7f * alphaMultiplier, 0f)
                    .setAlphaEasing(Easing.SINE_IN, Easing.SINE_OUT)
                    .setLifetime(65 + rand.nextInt(15))
                    .setSpin(nextFloat(rand, -0.1f, 0.1f))
                    .setSpinOffset(rand.nextFloat() * 6.28f)
                    .setScale(0.2f + rand.nextFloat() * 0.05f, 0f)
                    .setColor(SECOND_COLOR, SECOND_SMOKE_COLOR)
                    .setColorEasing(Easing.QUINTIC_OUT)
                    .setColorCoefficient(1.25f)
                    .randomOffset(0.02f)
                    .enableNoClip()
                    .addMotion(norm.x, norm.y, norm.z)
                    .randomMotion(0.01f, 0.01f)
                    .overwriteRenderType(ParticleRenderTypes.TRANSPARENT)
                    .repeat(level, lerpX, lerpY, lerpZ, 1)
                    .setColorCoefficient(2f)
                    .repeat(level, lerpX, lerpY, lerpZ, 1);
        }
    }
}