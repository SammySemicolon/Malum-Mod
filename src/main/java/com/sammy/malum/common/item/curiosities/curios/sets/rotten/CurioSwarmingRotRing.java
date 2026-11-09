package com.sammy.malum.common.item.curiosities.curios.sets.rotten;

import com.sammy.malum.common.data.listener.spirit.EntitySpiritDropData;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import team.lodestar.lodestone.modules.toolkit.enchanting.LodestoneEnchantmentEffectCommonsHelper;

import java.util.function.*;

public class CurioSwarmingRotRing extends MalumCurioItem {

    public CurioSwarmingRotRing(Properties builder) {
        super(builder, MalumTrinketFamily.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("rot_multiplicity"));
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("rot_aggression"));
    }

    public static LivingEntity findWickedTarget(LivingEntity attacker, LivingEntity target) {
        if (attacker.equals(target)) {
            return null;
        }
        if (target instanceof Player) {
            return target;
        }
        var level = attacker.level();
        int wickedAmount = -1;
        var area = target.getBoundingBox().inflate(8f, 3f, 8f);
        var predicate = LodestoneEnchantmentEffectCommonsHelper.attackPredicate(attacker).and(t -> !t.isDeadOrDying() && attacker.hasLineOfSight(t));
        var targets = level.getEntitiesOfClass(LivingEntity.class, area, predicate);

        LivingEntity highestWicked = null;
        for (LivingEntity otherTarget : targets) {
            int weight = EntitySpiritDropData.getSpiritData(otherTarget).map(e -> e.countSpirit(MalumSpiritTypes.WICKED_SPIRIT)).orElse(0);
            if (weight > wickedAmount) {
                highestWicked = otherTarget;
                wickedAmount = weight;
            }
        }
        return highestWicked;
    }
}