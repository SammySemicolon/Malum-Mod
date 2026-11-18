package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.entity.hidden_blade.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;
import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;

import javax.annotation.Nullable;
import java.util.function.*;

public class CurioHiddenBladeNecklace extends MalumCurioItem implements IMalumEventResponderBody, IVoidItem {

    public static final int COOLDOWN_DURATION = 200;

    public CurioHiddenBladeNecklace(Properties builder) {
        super(builder, MalumTrinketFamily.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("scythe_counterattack"));
        consumer.accept(TooltipComponentHelper.negativeCurioEffect("pacifist_recharge"));
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, @Nullable LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked.level().isClientSide()) {
            return;
        }
        if (attacked.getData(MalumAttachmentTypes.CURIO_DATA).hiddenBladeNecklaceCooldown == 0) {
            float damage = event.getOriginalDamage();
            int amplifier = Math.min(Mth.floor(damage / 3), 9);
            attacked.addEffect(new MobEffectInstance(MalumMobEffects.WICKED_INTENT, 100, amplifier));
            SoundPlayer.create(MalumGearSoundEvents.HIDDEN_BLADE_PRIMED).pitch(1.4f, 1.6f).play(attacked);
        }
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker.level() instanceof ServerLevel level) {
            var source = event.getSource();
            if (!source.is(MalumDamageTypes.SCYTHE_MELEE)) {
                return;
            }
            if (CurioHelper.hasCurioEquipped(attacker, MalumContent.Gear.NECKLACE_OF_THE_HIDDEN_BLADE.get())) {
                var data = attacker.getData(MalumAttachmentTypes.CURIO_DATA);
                var random = level.getRandom();
                if (data.hiddenBladeNecklaceCooldown != 0) {
                    if (data.hiddenBladeNecklaceCooldown <= COOLDOWN_DURATION) {
                        SoundPlayer.create(MalumGearSoundEvents.HIDDEN_BLADE_DISRUPTED).pitch(0.7f, 0.8f).play(attacker);
                    }
                    data.hiddenBladeNecklaceCooldown = (int) (COOLDOWN_DURATION * 1.5);
                    attacker.syncData(MalumAttachmentTypes.CURIO_DATA);
                    return;
                }
                var effect = attacker.getEffect(MalumMobEffects.WICKED_INTENT);
                if (effect == null) {
                    return;
                }
                var scytheWeapon = SoulDataHandler.getScytheWeapon(source, attacker);
                var direction = attacker.getLookAngle();
                var damageCenter = attacker.position().add(direction);
                var attributes = attacker.getAttributes();
                int slashDuration = 25;
                float baseDamage = 10;
                float damageBonus = 1 + (effect.amplifier + 1) * 0.15f;
                float physicalDamage = (float) (event.getNewDamage() + attributes.getValue(Attributes.ATTACK_DAMAGE));
                float magicDamage = (float) (attributes.getValue(WaywardAttributeTypes.MAGIC_DAMAGE));
                float totalDamage = physicalDamage + magicDamage;
                float physicalPct = physicalDamage / totalDamage;
                float magicPct = magicDamage / totalDamage;

                // Physical damage is set to the minimum of the damage dealt or player's attack damage
                // We then add the base damage bonus, which is potentially split between physical and magic damage based on how much of both the scythe deals
                // Finally, we amplify the damage by the Wicked Intent's effect amplifier, before dividing it by the amount of hits
                // TLDR: (Damage Dealt + Base Damage Split Across The Two Damage Types) * Damage Bonus / Slash Duration
                physicalDamage = Math.min(event.getNewDamage(), physicalDamage);
                physicalDamage += baseDamage * physicalPct;
                physicalDamage *= damageBonus;
                physicalDamage /= slashDuration;

                //Magic damage is calculated similarly, but avoids the first step
                magicDamage += baseDamage * magicPct;
                magicDamage *= damageBonus;
                magicDamage /= slashDuration;

                var entity = new HiddenBladeDelayedImpact(level, damageCenter.x, damageCenter.y - 3f + attacker.getBbHeight() / 2f, damageCenter.z);
                entity.setData(attacker, physicalDamage, magicDamage, slashDuration);
                entity.setItem(scytheWeapon);
                level.addFreshEntity(entity);
                attacker.syncData(MalumAttachmentTypes.CURIO_DATA);
                if (!effect.isInfiniteDuration()) {
                    data.hiddenBladeNecklaceCooldown = COOLDOWN_DURATION;
                    attacker.removeEffect(effect.getEffect());
                }
                for (int i = 0; i < 3; i++) {
                    SoundPlayer.create(MalumGearSoundEvents.HIDDEN_BLADE_UNLEASHED).volume(3f).pitch(0.75f, 1.25f).play(attacker);
                }
                MalumParticleEffectTypes.HIDDEN_BLADE_COUNTER_FLURRY.createEffect()
                        .originatesFrom(attacker)
                        .aimedAt(direction)
                        .color(scytheWeapon)
                        .mirroredRandomly(random)
                        .randomSlashRotation(random)
                        .spawn(level);
                event.setNewDamage(0);
            }
        }
    }
    public static void entityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            var level = entity.level();
            var data = entity.getData(MalumAttachmentTypes.CURIO_DATA);
            if (data.hiddenBladeNecklaceCooldown > 0) {
                data.hiddenBladeNecklaceCooldown--;
                if (!level.isClientSide()) {
                    if (data.hiddenBladeNecklaceCooldown == 0) {
                        SoundPlayer.create(MalumGearSoundEvents.HIDDEN_BLADE_CHARGED).pitch(1.0f, 1.2f).play(entity);
                    }
                }
            }
        }
    }
}
