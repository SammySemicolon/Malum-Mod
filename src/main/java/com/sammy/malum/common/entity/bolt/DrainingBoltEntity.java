package com.sammy.malum.common.entity.bolt;

import com.sammy.malum.common.item.curiosities.weapons.staff.ErosionScepterItem;
import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.visual_effects.SpiritLightSpecs;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.behaviors.DirectionalParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.DirectionalBehaviorComponent;

import java.util.function.Consumer;

public class DrainingBoltEntity extends AbstractBoltProjectileEntity {

    public DrainingBoltEntity(Level level) {
        super(EntityRegistry.DRAINING_BOLT.get(), level);
        noPhysics = false;
    }

    public DrainingBoltEntity(Level level, double pX, double pY, double pZ) {
        this(level);
        setPos(pX, pY, pZ);
        noPhysics = false;
    }

    @Override
    public void onDealDamage(LivingEntity target) {
        MobEffect silenced = MobEffectRegistry.SILENCED.get();
        MobEffectInstance effect = target.getEffect(silenced);
        if (effect == null) {
            target.addEffect(new MobEffectInstance(silenced, 300, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, target, 1, 9);
            EntityHelper.extendEffect(effect, target, 30, 600);
        }
    }

    @Override
    public void playSound(SoundEvent pSound, float pVolume, float pPitch) {
        super.playSound(pSound, pVolume, pPitch - 0.2f);
        super.playSound(SoundRegistry.DRAINING_MOTIF.get(), pVolume, pPitch - 0.1f);
    }

    @Override
    public int getMaxAge() {
        return 30;
    }

    @Override
    public ParticleEffectType getImpactParticleEffect() {
        return ParticleEffectTypeRegistry.DRAINING_BOLT_IMPACT;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.EROSION_SCEPTER.get();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void spawnParticles() {
        Level level = level();
        Vec3 position = position();
        float scalar = getVisualEffectScalar();
        Vec3 norm = getDeltaMovement().normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, position, ErosionScepterItem.MALIGNANT_COLOR_DATA);
        lightSpecs.getBuilder().multiplyLifetime(1.5f).setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).setMotion(norm);
        lightSpecs.getBloomBuilder().multiplyLifetime(1.5f).setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).setMotion(norm);
        lightSpecs.spawnParticles();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, RandomHelper.randomBetween(random, 0.25f, 0.5f)).randomSpinOffset(random).build();
        final Consumer<LodestoneWorldParticle> behavior = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f));
        WorldParticleBuilder.create(ParticleRegistry.SAW, new DirectionalBehaviorComponent(getDeltaMovement().normalize()))
                .setTransparencyData(GenericParticleData.create(0.4f * scalar, 0.2f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setSpinData(spinData)
                .setScaleData(GenericParticleData.create(0.3f * scalar, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(ErosionScepterItem.MALIGNANT_COLOR_DATA)
                .setLifetime(Math.min(6 + age * 3, 24))
                .enableNoClip()
                .enableForcedSpawn()
                .addTickActor(behavior)
                .spawn(level, position.x, position.y, position.z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .setTransparencyData(GenericParticleData.create(0.9f * scalar, 0.4f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .spawn(level, position.x, position.y, position.z);
    }
}