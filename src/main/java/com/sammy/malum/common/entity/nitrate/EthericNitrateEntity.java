package com.sammy.malum.common.entity.nitrate;

import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.setup.content.entity.EntityRegistry;
import com.sammy.ortus.helpers.EntityHelper;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.ortus.systems.rendering.particle.ParticleRenderTypes;
import net.minecraft.nbt.CompoundTag;
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
    public static final Color SECOND_COLOR = new Color(225, 54, 46);

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

            ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.3f, 0.7f * alphaMultiplier, 0f)
                    .setAlphaEasing(Easing.SINE_IN, Easing.SINE_OUT)
                    .setLifetime(65 + rand.nextInt(15))
                    .setSpin(nextFloat(rand, -0.1f, 0.1f))
                    .setSpinOffset(rand.nextFloat() * 6.28f)
                    .setScale(0.2f + rand.nextFloat() * 0.05f, 0f)
                    .setColor(SECOND_COLOR, Color.DARK_GRAY)
                    .setColorEasing(Easing.QUINTIC_OUT)
                    .setColorCoefficient(1.25f)
                    .randomOffset(0.02f)
                    .enableNoClip()
                    .addMotion(norm.x, norm.y, norm.z)
                    .randomMotion(0.01f, 0.01f)
                    .overwriteRenderType(ParticleRenderTypes.TRANSPARENT)
                    .repeat(level, lerpX, lerpY, lerpZ, 2);
        }
    }
}