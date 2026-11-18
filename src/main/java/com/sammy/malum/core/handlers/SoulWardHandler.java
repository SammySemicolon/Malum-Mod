package com.sammy.malum.core.handlers;

import com.sammy.malum.common.item.*;
import com.sammy.malum.config.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;
import team.lodestar.wayward_attributes.WaywardTags;

import static team.lodestar.lodestone.handlers.item_events.ItemEventHandler.createEventProcessor;

public class SoulWardHandler {

    public static void entityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity living) {
            var level = living.level();
            if (level.isClientSide) {
                return;
            }
            if (living.getAttributeValue(MalumAttributes.SOUL_WARD_CAPACITY) == 0) {
                return;
            }
            var data = living.getData(MalumAttachmentTypes.SOUL_WARD);
            data.tickData(living);
        }
    }

    public static void shieldPlayer(LivingDamageEvent.Pre event) {
        var wardedEntity = event.getEntity();
        if (wardedEntity.level().isClientSide()) {
            return;
        }
        if (event.getNewDamage() <= 0) {
            return;
        }
        var source = event.getSource();
        if (source.is(MalumTags.DamageTypes.BYPASSES_SOUL_WARD)) {
            return;
        }

        var data = wardedEntity.getData(MalumAttachmentTypes.SOUL_WARD);
        if (data.isDepleted()) {
            data.addCooldown(4f);
            return;
        }
        var eventProcessor = createEventProcessor(wardedEntity);

        float damage = event.getNewDamage();
        double soulWard = data.getSoulWard();
        double integrity = wardedEntity.getAttributeValue(MalumAttributes.SOUL_WARD_INTEGRITY);
        double cooldownRatio = 4;

        boolean isMagic = source.is(WaywardTags.DamageTypeTags.IS_MAGIC);
        double absorptionRatio = (isMagic ? CommonConfig.SOUL_WARD_MAGIC : CommonConfig.SOUL_WARD_PHYSICAL).getConfigValue();

        var propertiesEvent = new ModifySoulWardPropertiesEvent(wardedEntity, data, source, integrity, cooldownRatio, absorptionRatio);
        eventProcessor.run(IMalumEventResponderBody.class, (e, s) -> e.modifySoulWardPropertiesEvent(propertiesEvent, wardedEntity, s));
        NeoForge.EVENT_BUS.post(propertiesEvent);

        integrity = propertiesEvent.getNewIntegrity();
        cooldownRatio = propertiesEvent.getNewCooldownRatio();
        absorptionRatio = propertiesEvent.getNewAbsorptionRatio();

        double absorbedDamage = Math.min(damage * absorptionRatio, soulWard);
        if (absorbedDamage < 0) {
            return;
        }

        double soulWardDamage = absorbedDamage / Math.max(integrity, 0.25f);
        data.reduceSoulWard(soulWardDamage);
        data.addCooldown(cooldownRatio);



        var damageEvent = new SoulWardDamageEvent(wardedEntity, data, source, absorbedDamage, soulWardDamage);
        eventProcessor.run(IMalumEventResponderBody.class, (e, s) -> e.soulWardDamageEvent(damageEvent, wardedEntity, s));
        NeoForge.EVENT_BUS.post(propertiesEvent);

        var sound = data.getSoulWard() == 0 ? MalumSoundEvents.SOUL_WARD_DEPLETE : MalumSoundEvents.SOUL_WARD_HIT;
        var volume = source.is(MalumDamageTypes.KARMIC) ? 0.2f : 0.5f; //reduced volume for karmic damage, specifically with the pact of patience repaid in mind
        SoundPlayer.create(sound).volume(volume).pitch(1f, 1.5f).play(wardedEntity);
        event.setNewDamage((float) (damage - absorbedDamage));
    }
}