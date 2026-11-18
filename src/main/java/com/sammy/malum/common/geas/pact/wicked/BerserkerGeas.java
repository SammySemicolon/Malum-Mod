package com.sammy.malum.common.geas.pact.wicked;

import com.sammy.malum.common.worldevent.DelayedDamageWorldEvent;
import com.sammy.malum.core.helpers.TooltipComponentHelper;
import com.sammy.malum.core.systems.geas.GeasEffect;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.registry.common.sound.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.network.chat.Component;
import net.minecraft.util.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.modules.toolkit.worldevent.WorldEventHandler;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class BerserkerGeas extends GeasEffect {

    private float storedDamage;
    private boolean resetDamageNextTick;

    public BerserkerGeas() {
        super(MalumGeasEffectTypes.PACT_OF_THE_REAPER.get());
    }

    @Override
    public void incomingDamageEvent(LivingIncomingDamageEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        storedDamage = (storedDamage + 4 + event.getAmount()) * 0.8f;
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, @Nullable LivingEntity attacker, LivingEntity target, ItemStack stack) {
        event.setNewDamage(event.getNewDamage() * 2f);
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("damage_buildup"));
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("damage_release"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("more_damage"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        if (entity.level().isClientSide) {
            return;
        }
        if (resetDamageNextTick) {
            //Damage Reset is delayed by one tick to better function with AOE damage such as sweeping
            storedDamage = 0;
            resetDamageNextTick = false;
        }
        else if (entity.level().getGameTime() % 40L == 0) {
            storedDamage *= 0.95f;
        }
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (target.level().isClientSide) {
            return;
        }
        if (event.getSource().is(MalumDamageTypes.BERSERKER_SPIRIT_IMPACT)) {
            return;
        }
        if (storedDamage >= 4) {
            int hits = Mth.ceil(storedDamage/4);
            for (int i = 0; i < hits; i++) {
                WorldEventHandler.addWorldEvent(target.level(),
                        new DelayedDamageWorldEvent(target)
                                .setAttacker(attacker)
                                .setDamageData(0, 2, 10+i*2)
                                .setMagicDamageType(MalumDamageTypes.BERSERKER_SPIRIT_IMPACT)
                                .setImpactParticleEffect(MalumParticleEffectTypes.BERSERKER_IMPACT, new MalumNetworkedParticleEffectColorData(MalumSpiritTypes.WICKED_SPIRIT, MalumSpiritTypes.ELDRITCH_SPIRIT))
                                .setSound(MalumSoundEvents.BERSERKER_WRATH, 0.7f, 1.3f, 1));
            }
            resetDamageNextTick = true;
        }
    }
}