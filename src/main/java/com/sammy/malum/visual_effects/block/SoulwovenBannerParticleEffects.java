package com.sammy.malum.visual_effects.block;

import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlock;
import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlockEntity;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.spiritLightSpecs;

public class SoulwovenBannerParticleEffects {

    public static void applyBannerGlow(Level level, MalumNetworkedParticleEffectColorData colorData, SoulwovenBannerBlockEntity banner) {
        var position = banner.getBlockPos().getCenter();
        var direction = banner.getBlockState().getValue(SoulwovenBannerBlock.BANNER_TYPE).direction;

        long gameTime = level.getGameTime();
        var random = level.random;
        float time = 16;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                float velocity = Easing.SINE_IN_OUT.asWeighedRandom(random, 0.005f, 0.015f);
                Vec3 offsetPosition = VecHelper.rotatingRadialOffset(position, 0.65f, j + i * 4, 32, gameTime, time);
                offsetPosition = offsetPosition.add(0, (Math.cos(((gameTime + j * 240) % time) / time) * 0.25f) - 0.2f * (j+1), 0);
                if (direction.getAxis().isHorizontal()) {
                    int stepX = direction.getStepX();
                    int stepZ = direction.getStepZ();
                    offsetPosition = new Vec3(
                            Mth.lerp(0.5f * Mth.abs(stepX), offsetPosition.x, position.x) - 0.35f * stepX,
                            offsetPosition.y,
                            Mth.lerp(0.5f * Mth.abs(stepZ), offsetPosition.z, position.z) - 0.35f * stepZ
                    );
                }

                Vec3 motion = offsetPosition.subtract(position).normalize().scale(velocity);
                var lightSpecs = spiritLightSpecs(level, offsetPosition, colorData.getSpirit());
                lightSpecs.getBuilder()
                        .multiplyLifetime(1.4f)
                        .setMotion(motion)
                        .setLifeDelay(i+j*4)
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.8f, 0f).build())
                        .modifyScaleData(d -> d.multiplyValue(Easing.SINE_IN_OUT.asWeighedRandom(random, 1f, 2f)));
                lightSpecs.getBloomBuilder()
                        .multiplyLifetime(0.8f)
                        .setMotion(motion)
                        .setLifeDelay(i+j*4)
                        .setTransparencyData(GenericParticleData.create(0.05f, 0.35f, 0f).build())
                        .modifyScaleData(d -> d.multiplyValue(Easing.SINE_IN_OUT.asWeighedRandom(random, 0.5f, 1f)));
                lightSpecs.spawnParticles();
            }
        }
    }

    public static void removeBannerGlow(Level level, MalumNetworkedParticleEffectColorData colorData, SoulwovenBannerBlockEntity banner) {
        var position = banner.getBlockPos().getCenter();
        var direction = banner.getBlockState().getValue(SoulwovenBannerBlock.BANNER_TYPE).direction;
        long gameTime = level.getGameTime();
        var random = level.random;
        float time = 16;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Vec3 offsetPosition = VecHelper.rotatingRadialOffset(position, 0.65f, j + i * 4, 32, gameTime, time);
                offsetPosition = offsetPosition.add(0, (Math.cos(((gameTime + j * 240) % time) / time) * 0.25f) - 1.6f +  0.15f * (j+1), 0);
                if (direction.getAxis().isHorizontal()) {
                    int stepX = direction.getStepX();
                    int stepZ = direction.getStepZ();
                    offsetPosition = new Vec3(
                            Mth.lerp(0.5f * Mth.abs(stepX), offsetPosition.x, position.x) - 0.35f * stepX,
                            offsetPosition.y,
                            Mth.lerp(0.5f * Mth.abs(stepZ), offsetPosition.z, position.z) - 0.35f * stepZ
                    );
                }

                float velocity = Easing.SINE_IN_OUT.asWeighedRandom(random, 0.02f, 0.05f);
                var lightSpecs = spiritLightSpecs(level, offsetPosition, colorData.getSpirit());
                lightSpecs.getBuilder()
                        .multiplyLifetime(2f)
                        .setMotion(0, -velocity,0)
                        .setLifeDelay(i+j*2)
                        .setTransparencyData(GenericParticleData.create(0.9f, 0.4f, 0f).build())
                        .modifyScaleData(d -> d.multiplyValue(Easing.SINE_IN_OUT.asWeighedRandom(random, 1f, 2f)));
                lightSpecs.getBloomBuilder()
                        .multiplyLifetime(1.2f)
                        .setMotion(0, -velocity, 0)
                        .setLifeDelay(i+j*2)
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.35f, 0f).build())
                        .modifyScaleData(d -> d.multiplyValue(Easing.SINE_IN_OUT.asWeighedRandom(random, 0.5f, 1f)));
                lightSpecs.spawnParticles();
            }
        }
    }
}
