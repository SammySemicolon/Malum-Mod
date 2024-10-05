package com.sammy.malum.common.entity.activator;

import com.sammy.malum.common.entity.FloatingEntity;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import com.sammy.malum.visual_effects.SpiritLightSpecs;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.ItemHelper;

import java.util.UUID;

public class SpiritCollectionActivatorEntity extends FloatingEntity {

    public SpiritCollectionActivatorEntity(Level level) {
        super(EntityRegistry.SPIRIT_COLLECTION_ACTIVATOR.get(), level);
        maxAge = 4000;
    }

    public SpiritCollectionActivatorEntity(Level level, UUID ownerUUID, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        this(level);
        setOwner(ownerUUID);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);
        maxAge = 800;
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    @Override
    public void collect() {
        AttributeInstance instance = owner.getAttribute(AttributeRegistry.ARCANE_RESONANCE.get());
        ItemHelper.getEventResponders(owner).forEach(s -> {
            if (s.getItem() instanceof IMalumEventResponderItem eventItem) {
                eventItem.pickupSpirit(owner, instance != null ? instance.getValue() : 0);
            }
        });
        SoundHelper.playSound(this, SoundRegistry.SPIRIT_PICKUP.get(), 0.3f, Mth.nextFloat(random, 1.2f, 1.5f));
    }

    @Override
    public void spawnParticles(double x, double y, double z) {
        Vec3 motion = getDeltaMovement();
        Vec3 norm = motion.normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level(), new Vec3(x, y, z), SpiritTypeRegistry.ELDRITCH_SPIRIT);
        lightSpecs.getBuilder().setMotion(norm);
        lightSpecs.getBloomBuilder().setMotion(norm);
        lightSpecs.spawnParticles();
    }

    @Override
    public float getMotionCoefficient() {
        return 0.04f;
    }

    @Override
    public float getFriction() {
        return 0.9f;
    }

    @Override
    protected void defineSynchedData() {

    }
}