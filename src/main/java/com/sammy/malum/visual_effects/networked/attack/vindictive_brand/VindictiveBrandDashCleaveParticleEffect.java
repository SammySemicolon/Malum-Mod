package com.sammy.malum.visual_effects.networked.attack.vindictive_brand;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.modules.core.easing.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.spin.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.world.behaviors.*;
import team.lodestar.lodestone.systems.network.*;
import team.lodestar.lodestone.systems.network.particle.*;

public class VindictiveBrandDashCleaveParticleEffect extends MalumNetworkedWeaponParticleEffectType<WeaponParticleEffectType.WeaponParticleEffectData> {

    public VindictiveBrandDashCleaveParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void act(Level level, RandomSource random, NetworkedParticleEffectPositionData positionData, MalumNetworkedParticleEffectColorData colorData, WeaponParticleEffectData extraData) {
        var direction = extraData.getDirection();
        float yRot = ((float) (Mth.atan2(direction.x, direction.z) * (double) (180F / (float) Math.PI)));
        float yaw = (float) Math.toRadians(yRot);
        Vec3 left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));



        var roundaboutSlash = WeaponParticleEffects.spawnSlashParticle(level, positionData.getAsVector(), MalumParticles.ROUNDABOUT_SLASH, colorData);
        roundaboutSlash.getBuilder()
                .setSpinData(SpinParticleData.create(0).setSpinOffset(extraData.getSlashRotation() + (extraData.isMirrored() ? 3.14f : 0)).build())
                .setScaleData(GenericParticleData.create(Easing.SINE_IN_OUT.asWeighedRandom(random, 4f, 6f)).build())
                .setBehavior(PointyDirectionalParticleBehavior.pointyDirectional(direction));

        for (int i = 0; i < 4; i++) {
            var transparency = 1f - i * 0.3f;
            roundaboutSlash.getBuilder().setTransparencyData(GenericParticleData.create(transparency).build()).setLifeDelay(i * 2);
            roundaboutSlash.spawnParticles();
        }

        for (int i = 0; i < 6; i++) {
            float angle = i / 6f * 6.28f;
            var slashDirection = left.scale(Math.sin(angle))
                    .add(direction.scale(Math.cos(angle)))
                    .normalize();
            var slash = WeaponParticleEffects.spawnSlashParticle(level, positionData.getAsVector(), MalumParticles.SLASH, colorData);
            slash.getBuilder()
                    .setSpinData(SpinParticleData.create(0).setSpinOffset(extraData.getSlashRotation() + (extraData.isMirrored() ? 3.14f : 0)).build())
                    .setScaleData(GenericParticleData.create(Easing.SINE_IN_OUT.asWeighedRandom(random, 4f, 6f)).build())
                    .setMotion(slashDirection.scale(Easing.SINE_IN_OUT.asWeighedRandom(random, 0.5f, 0.7f)))
                    .setBehavior(PointyDirectionalParticleBehavior.pointyDirectional(slashDirection));

            for (int j = 0; j < 3; j++) {

                var transparency = 1f - j * 0.4f;
                slash.getBuilder()
                        .setTransparencyData(GenericParticleData.create(transparency).build())
                        .setLifeDelay(j * 2);
                slash.spawnParticles();

            }
        }
    }
}