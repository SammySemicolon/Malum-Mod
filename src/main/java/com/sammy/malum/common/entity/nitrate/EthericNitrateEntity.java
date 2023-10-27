package com.sammy.malum.common.entity.nitrate;

import com.sammy.malum.common.packets.particle.curiosities.nitrate.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.*;

import java.awt.*;
import java.util.*;

import static com.sammy.malum.registry.common.PacketRegistry.*;
import static net.minecraft.util.Mth.*;

public class EthericNitrateEntity extends AbstractNitrateEntity {

    public static final Color FIRST_COLOR = new Color(243, 185, 44);
    public static final Color SECOND_COLOR = new Color(194, 35, 137);

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
            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level().getChunkAt(blockPosition())), new EthericNitrateParticlePacket(getX(), getY(), getZ()));
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
        if (level().isClientSide) {
            ClientOnly.spawnParticles(this);
        }
    }

    public static class ClientOnly {
        public static void spawnParticles(EthericNitrateEntity ethericNitrateEntity) {
            double ox = ethericNitrateEntity.xOld, oy = ethericNitrateEntity.yOld + ethericNitrateEntity.getYOffset(0) + 0.25f, oz = ethericNitrateEntity.zOld;
            double x = ethericNitrateEntity.getX(), y = ethericNitrateEntity.getY() + ethericNitrateEntity.getYOffset(0) + 0.25f, z = ethericNitrateEntity.getZ();
            Vec3 motion = ethericNitrateEntity.getDeltaMovement();
            Vec3 norm = motion.normalize().scale(0.1f);
            float extraAlpha = (float) motion.length();
            float cycles = 3;
            Color firstColor = FIRST_COLOR.brighter();
            var rand = ethericNitrateEntity.level().getRandom();
            for (int i = 0; i < cycles; i++) {
                float pDelta = i / cycles;
                double lerpX = Mth.lerp(pDelta, ox, x) - motion.x / 4f;
                double lerpY = Mth.lerp(pDelta, oy, y) - motion.y / 4f;
                double lerpZ = Mth.lerp(pDelta, oz, z) - motion.z / 4f;
                float alphaMultiplier = (0.35f + extraAlpha) * Math.min(1, ethericNitrateEntity.windUp * 2);
                //SpiritLightSpecs.spiritLightSpecs(ethericNitrateEntity.level, new Vec3(lerpX, lerpY, lerpZ), firstColor, SECOND_COLOR, b -> b.setMotion(norm));

                final ColorParticleDataBuilder colorDataBuilder = ColorParticleData.create(SECOND_COLOR, SECOND_SMOKE_COLOR)
                        .setEasing(Easing.QUINTIC_OUT)
                        .setCoefficient(1.25f);
                WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(Math.min(1, 0.25f * alphaMultiplier), 0f).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build())
                        .setLifetime(65 + rand.nextInt(15))
                        .setSpinData(SpinParticleData.create(nextFloat(rand, -0.1f, 0.1f)).setSpinOffset(rand.nextFloat() * 6.28f).build())
                        .setScaleData(GenericParticleData.create(0.2f + rand.nextFloat() * 0.05f, 0.3f, 0f).build())
                        .setColorData(colorDataBuilder.build())
                        .setRandomOffset(0.02f)
                        .enableNoClip()
                        .addMotion(norm.x, norm.y, norm.z)
                        .setRandomMotion(0.01f, 0.01f)
                        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                        .spawn(ethericNitrateEntity.level(), lerpX, lerpY, lerpZ)
                        .setColorData(colorDataBuilder.setCoefficient(2f).build())
                        .spawn(ethericNitrateEntity.level(), lerpX, lerpY, lerpZ);
            }
        }
    }
}