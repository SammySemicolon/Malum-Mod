package com.sammy.malum.common.geas.pact.infernal;

import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;

import java.util.function.*;

public class ProspectorGeas extends GeasEffect {

    public ProspectorGeas() {
        super(MalumGeasEffectTypes.PACT_OF_THE_PROSPECTOR.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
//        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("ore_prospecting"));
//        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("avarice_healing"));
//        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("avarice_combustion"));
    }

    @Override
    public void finalizedIncomingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
//        var source = event.getSource();
//        if (!source.is(DamageTypeTags.IS_FIRE) && !source.is(DamageTypeTags.IS_EXPLOSION)) {
//            var effect = target.getEffect(MalumMobEffects.AVARICE);
//            if (effect != null) {
//                if (target.level() instanceof ServerLevel level) {
//                    target.igniteForSeconds((effect.amplifier + 1) * 0.5f);
//                    MalumParticleEffectTypes.PROSPECTORS_STREAK_BURN.createEffect(target)
//                            .color(new MalumNetworkedParticleEffectColorData(MalumSpiritTypes.INFERNAL_SPIRIT))
//                            .spawn(level);
//                }
//            }
//        }
    }

    public static boolean hasProspectorPact(LivingEntity entity) {
        return GeasEffectHandler.hasGeas(entity, MalumGeasEffectTypes.PACT_OF_THE_PROSPECTOR);
    }
}