package com.sammy.malum.common.entity.bolt;

import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

import java.util.function.*;

public class AuricFlameBoltEntity extends AbstractBoltProjectileEntity {

    public AuricFlameBoltEntity(Level level) {
        super(EntityRegistry.AURIC_FLAME_BOLT.get(), level);
        noPhysics = false;
    }

    public AuricFlameBoltEntity(Level level, double pX, double pY, double pZ) {
        this(level);
        setPos(pX, pY, pZ);
        noPhysics = false;
    }

    @Override
    public int getMaxAge() {
        return 40;
    }

    @Override
    public ParticleEffectType getOnHitVisualEffect() {
        return ParticleEffectTypeRegistry.AURIC_BOLT_IMPACT;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.STAFF_OF_THE_AURIC_FLAME.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnParticles() {
        float scalar = age > getMaxAge() ? 1f - (age - getMaxAge() + 10) / 10f : 1f;
        if (age < 5) {
            scalar = age / 5f;
        }
        Vec3 norm = getDeltaMovement().normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level(), position(), AuricFlameStaffItem.AURIC_COLOR_DATA, AuricFlameStaffItem.REVERSE_AURIC_COLOR_DATA);
        lightSpecs.getBuilder().multiplyLifetime(1.25f).setMotion(norm);
        lightSpecs.getBloomBuilder().multiplyLifetime(1.25f).setMotion(norm);
        lightSpecs.spawnParticles();
        final Consumer<LodestoneWorldParticleActor> behavior = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.98f));
        SparkParticleBuilder.create(ParticleRegistry.SLASH)
                .setTransparencyData(GenericParticleData.create(0.9f * scalar, 0.2f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.5f * scalar).build())
                .setLengthData(GenericParticleData.create(2.4f * Math.min(1f, scalar*2)).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(level().getGameTime() % 6L == 0 ? AuricFlameStaffItem.REVERSE_AURIC_COLOR_DATA : AuricFlameStaffItem.AURIC_COLOR_DATA)
                .setLifetime(Math.min(4 + age * 2, 20))
                .setMotion(norm)
                .enableNoClip()
                .enableForcedSpawn()
                .disableCull()
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .addTickActor(behavior)
                .spawn(level(), position().x, position().y, position().z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(level(), position().x, position().y, position().z);
    }
}