package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.core.easing.Easing;

import java.util.function.*;

public class CurioEchoingArcanaRing extends MalumCurioItem implements IVoidItem, IMalumEventResponderBody {
    public CurioEchoingArcanaRing(Properties builder) {
        super(builder, MalumTrinketFamily.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("spirits_weave_resonance"));
    }

    @Override
    public void spiritCollectionEvent(CollectSpiritEvent event, LivingEntity collector, double arcaneResonance) {
        var echoingArcana = MalumMobEffects.ECHOING_ARCANA;
        var effect = collector.getEffect(echoingArcana);
        int addedDuration = (int) (150 * arcaneResonance);
        if (effect == null) {
            collector.addEffect(new MobEffectInstance(echoingArcana, addedDuration*4, 0, true, true, true));
        } else {
            EntityHelper.extendEffect(effect, collector, addedDuration, 72000);
            EntityHelper.amplifyEffect(effect, collector, 1, 19);
        }
        float pitch = Easing.SINE_IN_OUT.asWeighedRandom(collector.getRandom(), 1.5f, 2f);
        collector.playSound(MalumGearSoundEvents.ECHOING_RING_ABSORBS.get(), 0.3f, pitch);
    }
}
