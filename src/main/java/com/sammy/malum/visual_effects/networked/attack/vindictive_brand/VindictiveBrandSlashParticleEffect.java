package com.sammy.malum.visual_effects.networked.attack.vindictive_brand;

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

public class VindictiveBrandSlashParticleEffect extends MalumNetworkedWeaponParticleEffectType<WeaponParticleEffectType.WeaponParticleEffectData> {

    public VindictiveBrandSlashParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void act(Level level, RandomSource random, NetworkedParticleEffectPositionData positionData, MalumNetworkedParticleEffectColorData colorData, WeaponParticleEffectData extraData) {
        var slash = WeaponParticleEffects.spawnSlashParticle(level, positionData.getAsVector(), MalumParticles.SLASH, colorData);
        var direction = extraData.getDirection();
        slash.getBuilder()
                .setSpinData(SpinParticleData.create(0).setSpinOffset(extraData.getSlashRotation()+(extraData.isMirrored()?3.14f:0)).build())
                .setScaleData(GenericParticleData.create(Easing.SINE_IN_OUT.asWeighedRandom(random, 3f, 4f)).build())
                .setMotion(direction.scale(Easing.SINE_IN_OUT.asWeighedRandom(random, 0.5f, 0.7f)))
                .setBehavior(PointyDirectionalParticleBehavior.pointyDirectional(direction));
        for (int i = 0; i < 3; i++) {
            var transparency = 1f - i * 0.4f;
            slash.getBuilder()
                    .setTransparencyData(GenericParticleData.create(transparency).build())
                    .setLifeDelay(i * 2);
            slash.spawnParticles();

        }
    }
}