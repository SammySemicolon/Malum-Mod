package com.sammy.malum.common.entity.bolt;

import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.visual_effects.SpiritLightSpecs;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.LodestoneWorldParticleActor;
import team.lodestar.lodestone.systems.particle.builder.DirectionalParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.util.function.Consumer;

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
    public ParticleEffectType getImpactParticleEffect() {
        return ParticleEffectTypeRegistry.HEX_BOLT_IMPACT;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.MNEMONIC_HEX_STAFF.get();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void spawnParticles() {
        Level level = level();
        Vec3 position = position();
        float scalar = getVisualEffectScalar();
        Vec3 norm = getDeltaMovement().normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, position, SpiritTypeRegistry.WICKED_SPIRIT);
        lightSpecs.getBuilder().multiplyLifetime(1.25f).setMotion(norm);
        lightSpecs.getBloomBuilder().multiplyLifetime(1.25f).setMotion(norm);
        lightSpecs.spawnParticles();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, RandomHelper.randomBetween(random, 0.25f, 0.5f)).randomSpinOffset(random).build();
        final Consumer<LodestoneWorldParticleActor> behavior = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.95f));
        DirectionalParticleBuilder.create(ParticleRegistry.SAW)
                .setTransparencyData(GenericParticleData.create(0.9f * scalar, 0.4f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setSpinData(spinData)
                .setScaleData(GenericParticleData.create(0.4f * scalar, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(SpiritTypeRegistry.WICKED_SPIRIT.createColorData().build())
                .setLifetime(Math.min(6 + age * 3, 30))
                .setDirection(getDeltaMovement().normalize())
                .enableNoClip()
                .enableForcedSpawn()
                .addTickActor(behavior)
                .spawn(level, position.x, position.y, position.z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(level, position.x, position.y, position.z);
    }
}