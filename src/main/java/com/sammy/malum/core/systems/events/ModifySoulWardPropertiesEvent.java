package com.sammy.malum.core.systems.events;

import com.sammy.malum.common.data.attachment.abilities.SoulWardData;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;

public class ModifySoulWardPropertiesEvent extends SoulWardEvent {

    private final double integrity;
    private final double cooldownRatio;
    private final double absorptionRatio;

    private double newIntegrity;
    private double newCooldownRatio;
    private double newAbsorptionRatio;

    public ModifySoulWardPropertiesEvent(LivingEntity entity, SoulWardData soulWardData, DamageSource source, double integrity, double cooldownRatio, double absorptionRatio) {
        super(entity, soulWardData, source);
        this.integrity = integrity;
        this.newIntegrity = integrity;

        this.cooldownRatio = cooldownRatio;
        this.newCooldownRatio = cooldownRatio;

        this.absorptionRatio = absorptionRatio;
        this.newAbsorptionRatio = absorptionRatio;
    }

    public double getOriginalIntegrity() {
        return integrity;
    }

    public double getNewIntegrity() {
        return newIntegrity;
    }

    public void modifyIntegrity(double multiplier) {
        this.newIntegrity *= multiplier;
    }

    public double getOriginalCooldownRatio() {
        return cooldownRatio;
    }

    public double getNewCooldownRatio() {
        return newCooldownRatio;
    }

    public void modifyCooldownRatio(double multiplier) {
        this.newCooldownRatio *= multiplier;
    }

    public double getOriginalAbsorptionRatio() {
        return absorptionRatio;
    }

    public double getNewAbsorptionRatio() {
        return newAbsorptionRatio;
    }

    public void modifyAbsorptionRatio(double multiplier) {
        this.newAbsorptionRatio *= multiplier;
    }

}