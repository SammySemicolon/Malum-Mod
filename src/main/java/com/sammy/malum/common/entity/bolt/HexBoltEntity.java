package com.sammy.malum.common.entity.bolt;

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

public class HexBoltEntity extends AbstractBoltProjectileEntity {

    public HexBoltEntity(Level level) {
        super(EntityRegistry.HEX_BOLT.get(), level);
        noPhysics = false;
    }

    public HexBoltEntity(Level level, double pX, double pY, double pZ) {
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
        return ParticleEffectTypeRegistry.HEX_BOLT_IMPACT;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.SOUL_STAINED_STEEL_STAFF.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnParticles() {
        float scalar = age > getMaxAge() ? 1f - (age - getMaxAge() + 10) / 10f : 1f;
        if (age < 5) {
            scalar = age / 5f;
        }
        Vec3 norm = getDeltaMovement().normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level(), position(), SpiritTypeRegistry.WICKED_SPIRIT);
        lightSpecs.getBuilder().multiplyLifetime(1.25f).setMotion(norm);
        lightSpecs.getBloomBuilder().multiplyLifetime(1.25f).setMotion(norm);
        lightSpecs.spawnParticles();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, RandomHelper.randomBetween(random, 0.25f, 0.5f)).randomSpinOffset(random).build();
        final Consumer<LodestoneWorldParticleActor> behavior = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.95f));
        DirectionalParticleBuilder.create(ParticleRegistry.SAW)
                .setTransparencyData(GenericParticleData.create(0.9f * scalar, 0.4f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setSpinData(spinData)
                .setScaleData(GenericParticleData.create(0.4f * scalar, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(SpiritTypeRegistry.WICKED_SPIRIT.createMainColorData().build())
                .setLifetime(Math.min(6 + age * 3, 30))
                .setDirection(getDeltaMovement().normalize())
                .enableNoClip()
                .enableForcedSpawn()
                .disableCull()
                .addTickActor(behavior)
                .spawn(level(), position().x, position().y, position().z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(level(), position().x, position().y, position().z);
    }
}