package com.sammy.malum.common.geas.pact.wicked;

import com.sammy.malum.common.worldevent.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.registry.common.sound.*;
import com.sammy.malum.visual_effects.networked.MalumNetworkedParticleEffectColorData;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.modules.toolkit.worldevent.WorldEventHandler;

import java.util.function.*;

public class WarlockGeas extends GeasEffect {

    public WarlockGeas() {
        super(MalumGeasEffectTypes.PACT_OF_THE_WARLOCK.get());
    }

    public static void increaseDetection(LivingEvent.LivingVisibilityEvent event) {
        if (GeasEffectHandler.hasGeas(event.getEntity(), MalumGeasEffectTypes.PACT_OF_THE_WARLOCK)) {
            event.modifyVisibility(2f);
        }
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (target.level().isClientSide) {
            return;
        }
        if (event.getSource().is(MalumDamageTypes.WARLOCK_SPIRIT_IMPACT)) {
            return;
        }
        if (target.getHealth() > target.getMaxHealth() * 0.95f) {
            WorldEventHandler.addWorldEvent(target.level(),
                    new DelayedDamageWorldEvent(target)
                            .setAttacker(attacker)
                            .setDamageData(0, 4, 3)
                            .setMagicDamageType(MalumDamageTypes.WARLOCK_SPIRIT_IMPACT)
                            .setImpactParticleEffect(MalumParticleEffectTypes.WARLOCK_IMPACT, new MalumNetworkedParticleEffectColorData(MalumSpiritTypes.WICKED_SPIRIT))
                            .setSound(MalumSoundEvents.WARLOCK_BLAST, 1.5f, 1.75f, 1));
        }
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("first_hit_bonus"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("aggressive_enemies"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }
}
