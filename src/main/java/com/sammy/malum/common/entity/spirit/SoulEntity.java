package com.sammy.malum.common.entity.spirit;

import com.sammy.malum.common.entity.FloatingEntity;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.registry.AttributeRegistry;
import com.sammy.malum.core.registry.EntityRegistry;
import com.sammy.malum.core.systems.item.ISoulContainerItem;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

import static com.sammy.malum.core.systems.spirit.MalumEntitySpiritData.EMPTY;

public class SoulEntity extends FloatingEntity {
    public UUID thiefUUID;
    public MalumEntitySpiritData spiritData = EMPTY;
    public LivingEntity thief;
    public float erraticMovementCooldown = 30;

    public SoulEntity(Level level) {
        super(EntityRegistry.NATURAL_SOUL.get(), level);
        maxAge = 2000;
        range = 8;
    }

    public SoulEntity(Level level, MalumEntitySpiritData spiritData, UUID ownerUUID, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        super(EntityRegistry.NATURAL_SOUL.get(), level);
        this.spiritData = spiritData;
        if (!spiritData.dataEntries.isEmpty()) {
            this.color = spiritData.primaryType.color;
            getEntityData().set(DATA_COLOR, color.getRGB());
            this.endColor = spiritData.primaryType.endColor;
            getEntityData().set(DATA_END_COLOR, endColor.getRGB());
        }
        range = 8;
        setThief(ownerUUID);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);
        maxAge = 600;
    }


    public void setThief(UUID ownerUUID) {
        this.thiefUUID = ownerUUID;
        updateThief();
    }
    public void updateThief()
    {
        if (!level.isClientSide) {
            thief = (LivingEntity) ((ServerLevel) level).getEntity(thiefUUID);
            if (thief != null)
            {
                range = (int) (7*Math.exp(-0.1*(thief.getAttributeValue(AttributeRegistry.SPIRIT_REACH)-5)));
            }
        }
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.getItem() instanceof ISoulContainerItem containerItem)
        {
            return containerItem.interactWithSoul(pPlayer, pHand, this);
        }
        return super.interact(pPlayer, pHand);
    }

    @Override
    public void spawnParticles(double x, double y, double z) {
        Vec3 motion = getDeltaMovement();
        Vec3 norm = motion.normalize().scale(0.025f);
        for (int i = 0; i < 8; i ++) {
            double lerpX = Mth.lerp(i / 8.0f, x-motion.x, x);
            double lerpY = Mth.lerp(i / 8.0f, y-motion.y, y);
            double lerpZ = Mth.lerp(i / 8.0f, z-motion.z, z);
            SpiritHelper.spawnSoulParticles(level, lerpX, lerpY, lerpZ, 0.125f, norm, color, endColor);
        }
    }

    @Override
    public void remove(RemovalReason pReason) {
        if (pReason.equals(RemovalReason.KILLED))
        {
            SpiritHelper.createSpiritsFromSoul(spiritData, level, position(), thief);
        }
        super.remove(pReason);
    }

    @Override
    public void move() {
        setDeltaMovement(getDeltaMovement().multiply(0.95f, 0.97f, 0.95f));
        if (thief == null || !thief.isAlive()) {
            if (level.getGameTime() % 40L == 0)
            {
                Player playerEntity = level.getNearestPlayer(this, range*5f);
                if (playerEntity != null)
                {
                    setThief(playerEntity.getUUID());
                }
            }
            return;
        }
        Vec3 desiredLocation = thief.position().add(0, thief.getBbHeight() / 4, 0);
        float distance = (float) distanceToSqr(desiredLocation);
        float velocity = Mth.lerp(Math.min(moveTime, 20) / 20f, 0.1f, 0.025f + (range * 0.05f));
        if (distance < range) {
            moveTime++;
            Vec3 desiredMotion = position().subtract(desiredLocation).normalize().multiply(velocity, velocity, velocity).add(0, 0.075f, 0);
            float easing = 0.2f;
            float xMotion = (float) Mth.lerp(easing, getDeltaMovement().x, desiredMotion.x);
            float yMotion = (float) Mth.lerp(easing, getDeltaMovement().y, desiredMotion.y);
            float zMotion = (float) Mth.lerp(easing, getDeltaMovement().z, desiredMotion.z);
            Vec3 resultingMotion = new Vec3(xMotion, yMotion, zMotion);
            setDeltaMovement(resultingMotion);
            return;
        }
        else {
            boolean above = !level.noCollision(getBoundingBox().move(0, 1.5f, 0));
            boolean below = !level.noCollision(getBoundingBox().move(0, -2.5f, 0));
            if (above && below) {
                setDeltaMovement(getDeltaMovement().add(0, 0.002f, 0));
                return;
            }
            if (below) {
                setDeltaMovement(getDeltaMovement().add(0, 0.003f, 0));
            }
            if (above || !below) {
                setDeltaMovement(getDeltaMovement().add(0, -0.0015f, 0));
            }
        }
        erraticMovementCooldown--;
        if (erraticMovementCooldown <= 0)
        {
            erraticMovementCooldown = 10 + random.nextFloat(30);
            setDeltaMovement(Mth.nextFloat(random, -0.06f, 0.06f), Mth.nextFloat(random, -0.02f, 0.04f), Mth.nextFloat(random, -0.06f, 0.06f));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (!spiritData.equals(EMPTY))
        {
            spiritData.saveTo(compound);
        }
        if (thiefUUID != null) {
            compound.putUUID("thiefUUID", thiefUUID);
        }
        compound.putFloat("erraticMovementCooldown", erraticMovementCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        spiritData = MalumEntitySpiritData.load(compound);
        if (compound.contains("thiefUUID")) {
            setThief(compound.getUUID("thiefUUID"));
        }
        erraticMovementCooldown = compound.getFloat("erraticMovementCooldown");
    }
}
