package com.sammy.malum.visual_effects.networked.attack.vindictive_brand.unleashed;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.modules.core.easing.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.spin.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.world.behaviors.*;
import team.lodestar.lodestone.systems.network.*;
import team.lodestar.lodestone.systems.network.particle.*;

public class VindictiveBrandUnleashedSlashParticleEffect extends MalumNetworkedWeaponParticleEffectType<WeaponParticleEffectType.WeaponParticleEffectData> {

    public VindictiveBrandUnleashedSlashParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void act(Level level, RandomSource random, NetworkedParticleEffectPositionData positionData, MalumNetworkedParticleEffectColorData colorData, WeaponParticleEffectData extraData) {
        var slash = WeaponParticleEffects.spawnSlashParticle(level, positionData.getAsVector(), MalumParticles.SLASH, colorData);
        var direction = extraData.getDirection();
        slash.getBuilder()
                .setMotion(direction.scale(Easing.SINE_IN_OUT.asWeighedRandom(random, 0.7f, 0.9f)))
                .setBehavior(PointyDirectionalParticleBehavior.pointyDirectional(direction));
        for (int i = 0; i < 5; i++) {
            var spin = Easing.SINE_IN_OUT.asWeighedRandom(random, -0.3f, 0.3f);
            var scale = Easing.SINE_IN_OUT.asWeighedRandom(random, 3f, 4f) * (1 + i * 0.15f);
            var transparency = 1f - i * 0.2f;
            slash.getBuilder()
                    .setSpinData(SpinParticleData.create(spin).setSpinOffset(extraData.getSlashRotation()+(extraData.isMirrored()?3.14f:0)).build())
                    .setTransparencyData(GenericParticleData.create(transparency).build())
                    .setScaleData(GenericParticleData.create(scale).build())
                    .setLifeDelay(i);
            slash.spawnParticles();

        }
    }
}