package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.curiosities.totem.*;

import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import org.jetbrains.annotations.Nullable;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class WickedRiteType extends MalumRiteType {
    public WickedRiteType() {
        super("wicked_rite", ARCANE_SPIRIT.get(), WICKED_SPIRIT.get(), WICKED_SPIRIT.get());
    }
    @Override
    public MalumRiteEffect getNaturalRiteEffect() {
        return new MalumRiteEffect(MalumRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, LivingEntity.class, e -> !(e instanceof Player)).forEach(e -> {
                    if (e.getHealth() > 2.5f && !e.isInvulnerableTo(DamageSourceRegistry.VOODOO)) {
                        ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(WICKED_SPIRIT.get().getPrimaryColor()));
                        e.hurt(DamageSourceRegistry.VOODOO, 2);
                    }
                });
            }
        };

    }

    @Override
    public MalumRiteEffect getCorruptedEffect() {
        return new MalumRiteEffect(MalumRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase) {
                getNearbyEntities(totemBase, Monster.class).forEach(e -> {
                    ParticleEffectTypeRegistry.MAJOR_HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(WICKED_SPIRIT.get().getPrimaryColor()));
                    e.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1));
                    e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1));
                    e.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1));
                });
            }
        };
    }

    @Override
    public MalumRiteType setRegistryName(ResourceLocation name) {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return null;
    }

    @Override
    public Class<MalumRiteType> getRegistryType() {
        return null;
    }
}