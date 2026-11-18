package com.sammy.malum.common.data.attachment.abilities;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.config.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.sound.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;

public class SoulWardData {

    public static Codec<SoulWardData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.DOUBLE.optionalFieldOf("soul_ward", 0d).forGetter(SoulWardData::getSoulWard),
            Codec.DOUBLE.optionalFieldOf("soul_ward_cooldown", 0d).forGetter(SoulWardData::getSoulWardCooldown)
    ).apply(obj, SoulWardData::new));

    public static StreamCodec<ByteBuf, SoulWardData> STREAM_CODEC = ByteBufCodecs.fromCodec(SoulWardData.CODEC);

    private double soulWard;
    private double soulWardCooldown;

    private boolean isDirty;

    public SoulWardData() {
    }

    public SoulWardData(double soulWard, double soulWardCooldown) {
        this.soulWard = soulWard;
        this.soulWardCooldown = soulWardCooldown;
    }

    public void tickData(LivingEntity living) {
        var capacity = living.getAttributeValue(MalumAttributes.SOUL_WARD_CAPACITY);
            if (soulWard < capacity) {
                if (soulWardCooldown > 0) {
                    var recoveryRate = living.getAttributeValue(MalumAttributes.SOUL_WARD_RECOVERY_RATE);
                    soulWardCooldown -= recoveryRate;
                }
                if (soulWardCooldown <= 0) {
                    recoverSoulWard(living, 1);
                }
            }
            else if (soulWard > capacity) {
                setSoulWard(capacity);
            }

        if (isDirty) {
            if (!living.level().isClientSide) {
                living.syncData(MalumAttachmentTypes.SOUL_WARD);
            }
            isDirty = false;
        }
    }

    public void recoverSoulWard(LivingEntity entity, double amount) {
        addCooldown(1f);
        var capacity = entity.getAttributeValue(MalumAttributes.SOUL_WARD_CAPACITY);
        if (soulWard >= capacity) {
            return;
        }
        var multiplier = entity.getAttributeValue(MalumAttributes.SOUL_WARD_RECOVERY_GAIN);
        var previousSoulward = soulWard;
        addSoulWard(amount * multiplier);
        if (soulWard > previousSoulward) {
            if (!(entity instanceof Player player) || !player.isCreative()) {
                var sound = soulWard >= capacity ? MalumSoundEvents.SOUL_WARD_FULLY_CHARGED : MalumSoundEvents.SOUL_WARD_GROW;
                double pitchOffset = (soulWard / capacity) * 0.5f + (Mth.ceil(soulWard) % 3) * 0.25f;
                SoundPlayer.create(sound).volume(0.25f).pitch(1f + pitchOffset).play(entity);
            }
        }
    }

    public void addSoulWard(double added) {
        setSoulWard(soulWard + added);
    }

    public void reduceSoulWard(double removed) {
        setSoulWard(soulWard - removed);
    }

    public void setSoulWard(double soulWard) {
        this.soulWard = Math.max(soulWard, 0);
        isDirty = true;
    }

    public void addCooldown(double multiplier) {
        double newCooldown = getRecoveryDuration() * multiplier;
        if (soulWardCooldown < newCooldown) {
            soulWardCooldown = Mth.floor(newCooldown);
            isDirty = true;
        }
    }

    public double getSoulWard() {
        return soulWard;
    }

    public double getSoulWardCooldown() {
        return soulWardCooldown;
    }

    public boolean isDepleted() {
        return soulWard <= 0;
    }

    public float getRecoveryDuration() {
        return Mth.floor(CommonConfig.SOUL_WARD_RECOVERY_DURATION.getConfigValue());
    }
}