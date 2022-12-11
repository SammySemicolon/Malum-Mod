package com.sammy.malum.common.entity.nitrate;

import com.sammy.malum.common.packets.particle.entity.EthericNitrateParticlePacket;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.ParticleRenderTypes;

import java.awt.*;
import java.util.Random;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
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
    public void onExplode() {
        if (level instanceof ServerLevel) {
            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(blockPosition())), new EthericNitrateParticlePacket(getX(), getY(), getZ()));
        }
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
        double ox = xOld, oy = yOld + getYOffset(0) + 0.25f, oz = zOld;
        double x = getX(), y = getY() + getYOffset(0) + 0.25f, z = getZ();
        Vec3 motion = getDeltaMovement();
        Vec3 norm = motion.normalize().scale(0.1f);
        float extraAlpha = (float) motion.length();
        float cycles = 3;
        Color firstColor = FIRST_COLOR.brighter();
        Random rand = level.getRandom();
        for (int i = 0; i < cycles; i++) {
            float pDelta = i / cycles;
            double lerpX = Mth.lerp(pDelta, ox, x)-motion.x/4f;
            double lerpY = Mth.lerp(pDelta, oy, y)-motion.y/4f;
            double lerpZ = Mth.lerp(pDelta, oz, z)-motion.z/4f;
            float alphaMultiplier = (0.35f + extraAlpha) * Math.min(1, windUp * 2);
            SpiritHelper.spawnSpiritParticles(level, lerpX, lerpY, lerpZ, alphaMultiplier, norm, firstColor, SECOND_COLOR);

            ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(Math.min(1, 0.25f * alphaMultiplier), 0f)
                .setAlphaEasing(Easing.SINE_IN, Easing.SINE_OUT)
                .setLifetime(65 + rand.nextInt(15))
                .setSpin(nextFloat(rand, -0.1f, 0.1f))
                .setSpinOffset(rand.nextFloat() * 6.28f)
                .setScale(0.2f + rand.nextFloat() * 0.05f, 0.3f, 0f)
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