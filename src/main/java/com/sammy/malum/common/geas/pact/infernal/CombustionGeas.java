package com.sammy.malum.common.geas.pact.infernal;

import com.sammy.malum.common.worldevent.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.registry.common.sound.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.modules.toolkit.worldevent.WorldEventHandler;

import java.util.function.*;

public class CombustionGeas extends GeasEffect {


    public CombustionGeas() {
        super(MalumGeasEffectTypes.PACT_OF_COMBUSTION.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("hotter_fire"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("extinguish_hurt"));
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            target.setRemainingFireTicks(Math.max(target.getRemainingFireTicks()-8, 0));
            target.invulnerableTime = 0;
            if (attacker.level() instanceof ServerLevel level) {
                MalumParticleEffectTypes.COMBUSTION_BURN.createEffect(target)
                        .color(new MalumNetworkedParticleEffectColorData(MalumSpiritTypes.INFERNAL_SPIRIT))
                        .spawn(level);
            }
        }
    }

    public static void extinguish(LivingEntity entity) {
        if (GeasEffectHandler.hasGeas(entity, MalumGeasEffectTypes.PACT_OF_COMBUSTION)) {
            if (entity.wasOnFire) {
                for (int i = 0; i < 3; i++) {
                    WorldEventHandler.addWorldEvent(entity.level(),
                            new DelayedDamageWorldEvent(entity)
                                    .setDamageData(0, 4, (i+1)*2)
                                    .setMagicDamageType(MalumDamageTypes.KARMIC)
                                    .setImpactParticleEffect(MalumParticleEffectTypes.COMBUSTION_BURN, new MalumNetworkedParticleEffectColorData(MalumSpiritTypes.INFERNAL_SPIRIT, MalumSpiritTypes.WICKED_SPIRIT))
                                    .setSound(MalumSoundEvents.COMBUSTION_WHIPLASH, 0.5f, 0.4f, 1f));

                }
            }
        }
    }
}
