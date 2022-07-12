package com.sammy.malum.common.entity.nitrate;

import com.sammy.malum.common.item.EthericNitrateItem;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.content.entity.EntityRegistry;
import com.sammy.ortus.helpers.EntityHelper;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.ortus.systems.rendering.particle.ParticleRenderTypes;
import net.mehvahdjukaar.supplementaries.common.entities.BombEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.world.ExplosionEvent;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.util.Mth.nextFloat;

public class EthericNoduleEntity extends ThrowableProjectile {

    public final ArrayList<EntityHelper.PastPosition> pastPositions = new ArrayList<>();
    public int maxAge = 1000;
    public int age;
    public float windUp;
    public int pierce = 3;

    public EthericNoduleEntity(Level level) {
        super(EntityRegistry.ETHERIC_NODULE.get(), level);
    }

    public EthericNoduleEntity(double x, double y, double z, Level level) {
        super(EntityRegistry.ETHERIC_NODULE.get(), x, y, z, level);
    }

    public EthericNoduleEntity(LivingEntity owner, Level level) {
        super(EntityRegistry.ETHERIC_NODULE.get(), owner, level);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("maxAge", maxAge);
        compound.putInt("age", age);
        compound.putFloat("windUp", windUp);
        compound.putInt("pierce", pierce);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        maxAge = compound.getInt("maxAge");
        age = compound.getInt("age");
        windUp = compound.getFloat("windUp");
        pierce = compound.getInt("pierce");
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);

        EthericExplosion.explode(level, this, getX(), getY(0.0625D), getZ(), 2.5F, Explosion.BlockInteraction.BREAK);
        if (pierce <= 0) {
            discard();
        }
        else {
            pierce--;

        }
    }

    @Override
    public void tick() {
        super.tick();
        trackPastPositions();
        age++;
        if (age > maxAge) {
            discard();
        }
        if (windUp < 1f) {
            windUp += 0.1f;
        }
        if (level.isClientSide) {
            double x = getX(), y = getY() + getYOffset(0) + 0.25f, z = getZ();
            Vec3 motion = getDeltaMovement();
            Vec3 norm = motion.normalize().scale(0.1f);
            float extraAlpha = (float) motion.length();
            float cycles = 4;
            Color firstColor = EthericNitrateItem.FIRST_COLOR.brighter();
            Random rand = level.getRandom();
            for (int i = 0; i < cycles; i++) {
                float pDelta = i / cycles;
                double lerpX = Mth.lerp(pDelta, x - motion.x, x) + norm.x * pDelta;
                double lerpY = Mth.lerp(pDelta, y - motion.y, y) + norm.y * pDelta;
                double lerpZ = Mth.lerp(pDelta, z - motion.z, z) + norm.z * pDelta;
                float alphaMultiplier = (0.20f + extraAlpha) * Math.min(1, windUp * 2);
                SpiritHelper.spawnSpiritParticles(level, lerpX, lerpY, lerpZ, alphaMultiplier, norm, firstColor, EthericNitrateItem.SECOND_COLOR);

                ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                        .setAlpha(0.3f, 0.7f * alphaMultiplier, 0f)
                        .setAlphaEasing(Easing.SINE_IN, Easing.SINE_OUT)
                        .setLifetime(65 + rand.nextInt(15))
                        .setSpin(nextFloat(rand, -0.1f, 0.1f))
                        .setSpinOffset(rand.nextFloat()*6.28f)
                        .setScale(0.15f + rand.nextFloat() * 0.05f, 0f)
                        .setColor(EthericNitrateItem.SECOND_COLOR, Color.DARK_GRAY)
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

    public void trackPastPositions() {
        EntityHelper.trackPastPositions(pastPositions, position().add(0, getYOffset(0) + 0.25F, 0), 0.01f);
        removeOldPositions(pastPositions);
    }

    public void removeOldPositions(ArrayList<EntityHelper.PastPosition> pastPositions) {
        int amount = pastPositions.size() - 1;
        ArrayList<EntityHelper.PastPosition> toRemove = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            EntityHelper.PastPosition excess = pastPositions.get(i);
            if (excess.time > 25) {
                toRemove.add(excess);
            }
        }
        pastPositions.removeAll(toRemove);
    }

    public float getYOffset(float partialTicks) {
        return Mth.sin(((float) age + partialTicks) / 10.0F) * 0.2F + 0.1F;
    }

    @Override
    protected void defineSynchedData() {
    }
}