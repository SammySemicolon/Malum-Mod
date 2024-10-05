package com.sammy.malum.common.entity.spirit;

import com.sammy.malum.common.entity.FloatingItemEntity;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.core.handlers.SpiritHarvestHandler;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import com.sammy.malum.visual_effects.SpiritLightSpecs;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.ItemHelper;

import java.util.UUID;

public class SpiritItemEntity extends FloatingItemEntity {

    public int soundCooldown = 20 + random.nextInt(100);

    public SpiritItemEntity(Level level) {
        super(EntityRegistry.NATURAL_SPIRIT.get(), level);
        maxAge = 4000;
    }

    public SpiritItemEntity(Level level, UUID ownerUUID, ItemStack stack, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        this(level);
        setOwner(ownerUUID);
        setItem(stack);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);
        maxAge = 800;
        if (stack.getItem() instanceof SpiritShardItem spiritShardItem) {
            setSpirit(spiritShardItem.type);
        }
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    @Override
    public void collect() {
        ItemStack stack = getItem();
        if (stack.getItem() instanceof SpiritShardItem) {
            SpiritHarvestHandler.pickupSpirit(owner, stack);
        } else {
            ItemHelper.giveItemToEntity(owner, stack);
        }
        if (random.nextFloat() < 0.6f) {
            SoundHelper.playSound(this, SoundRegistry.SPIRIT_PICKUP.get(), 0.3f, Mth.nextFloat(random, 1.1f, 2f));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide()) {
            if (soundCooldown-- == 0) {
                if (random.nextFloat() < 0.4f) {
                    SoundHelper.playSound(this, SoundRegistry.ARCANE_WHISPERS.get(), 0.3f, Mth.nextFloat(random, 0.8f, 2f));
                }
                soundCooldown = 40 + random.nextInt(40);
            }
        }
    }

    @Override
    public void spawnParticles(double x, double y, double z) {
        Vec3 motion = getDeltaMovement();
        Vec3 norm = motion.normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level(), new Vec3(x, y, z), spiritType);
        lightSpecs.getBuilder().setMotion(norm);
        lightSpecs.getBloomBuilder().setMotion(norm);
        lightSpecs.spawnParticles();
    }

    @Override
    public float getMotionCoefficient() {
        return 0.01f;
    }
}