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
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;

import java.util.*;

public class SoulWardData {

    public static Codec<SoulWardData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.DOUBLE.optionalFieldOf("soulWard", 0d).forGetter(SoulWardData::getSoulWard),
            Codec.INT.optionalFieldOf("soulWardProgress", 0).forGetter(SoulWardData::getSoulWardCooldown),
            Codec.FLOAT.optionalFieldOf("appliedCooldownMultiplier", 1f).forGetter(SoulWardData::getAppliedCooldownMultiplier)
    ).apply(obj, SoulWardData::new));

    public static StreamCodec<ByteBuf, SoulWardData> STREAM_CODEC = ByteBufCodecs.fromCodec(SoulWardData.CODEC);

    private double soulWard;
    private int soulWardCooldown;
    private float appliedCooldownMultiplier = 1f;

    private boolean isDirty;

    public SoulWardData() {
    }

    public SoulWardData(double soulWard, int soulWardCooldown, float appliedCooldownMultiplier) {
        this.soulWard = soulWard;
        this.soulWardCooldown = soulWardCooldown;
        this.appliedCooldownMultiplier = appliedCooldownMultiplier;
    }

    public void tickData(LivingEntity living) {
        var attribute = living.getAttribute(MalumAttributes.SOUL_WARD_CAPACITY);
        if (attribute != null) {
            if (getSoulWard() < attribute.getValue()) {
                if (soulWardCooldown > 0) {
                    soulWardCooldown--;
                    tryCorrectCooldown(living);
                }
                if (soulWardCooldown <= 0) {
                    recoverSoulWard(living, 1);
                }
            }
            if (getSoulWard() > attribute.getValue()) {
                setSoulWard(attribute.getValue());
            }
        }
        if (isDirty) {
            if (!living.level().isClientSide) {
                living.syncData(MalumAttachmentTypes.SOUL_WARD);
            }
            isDirty = false;
        }
    }

    @SuppressWarnings("DataFlowIssue")
    public void recoverSoulWard(LivingEntity entity, double amount) {
        var attribute = entity.getAttribute(MalumAttributes.SOUL_WARD_CAPACITY);
        if (getSoulWard() < attribute.getValue()) {
            var multiplier = Optional.ofNullable(entity.getAttribute(MalumAttributes.SOUL_WARD_RECOVERY_GAIN)).map(AttributeInstance::getValue).orElse(1.0);
            var previousSoulward = soulWard;
            addSoulWard(amount * multiplier);
            if (soulWard > previousSoulward) {
                if (!(entity instanceof Player player) || !player.isCreative()) {
                    var sound = soulWard >= attribute.getValue() ? MalumSoundEvents.SOUL_WARD_FULLY_CHARGED : MalumSoundEvents.SOUL_WARD_GROW;
                    double pitchOffset = (soulWard / attribute.getValue()) * 0.5f + (Mth.ceil(soulWard) % 3) * 0.25f;
                    SoundPlayer.create(sound).volume(0.25f).pitch(1f + pitchOffset).play(entity);
                }
            }
        }
        addCooldown(entity, 1f);
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

    /**
     * Attempts to correct the cooldown if the recovery rate has changed.
     * Mainly meant to curb any infinite-cooldown-duration states that can be achieved through Malignant Conversion or the Pact of Reciprocation
     */
    public void tryCorrectCooldown(LivingEntity entity) {
        double newCooldown = getSoulWardCooldown(entity) * appliedCooldownMultiplier;
        if (soulWardCooldown > newCooldown) {
            soulWardCooldown = Mth.floor(newCooldown);
            isDirty = true;
        }
    }

    public void addCooldown(LivingEntity entity, float multiplier) {
        double newCooldown = getSoulWardCooldown(entity) * multiplier;
        if (soulWardCooldown < newCooldown) {
            soulWardCooldown = Mth.floor(newCooldown);
            appliedCooldownMultiplier = multiplier;
            isDirty = true;
        }
    }

    public double getSoulWard() {
        return soulWard;
    }

    public int getSoulWardCooldown() {
        return soulWardCooldown;
    }

    public float getAppliedCooldownMultiplier() {
        return appliedCooldownMultiplier;
    }

    public boolean isDepleted() {
        return soulWard <= 0;
    }

    public float getSoulWardCooldown(LivingEntity entity) {
        double recoveryRate = entity.getAttributeValue(MalumAttributes.SOUL_WARD_RECOVERY_RATE);
        var cooldownDuration = CommonConfig.SOUL_WARD_RATE.getConfigValue();
        return Mth.floor(cooldownDuration / recoveryRate);
    }
}