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

    public static LivingEntity findWickedTarget(LivingEntity owner) {
        var level = owner.level();
        var area = owner.getBoundingBox().inflate(16f, 4f, 16f);
        var predicate = LodestoneEnchantmentEffectCommonsHelper.attackPredicate(owner).and(t -> !t.isDeadOrDying() && owner.hasLineOfSight(t) && hasWicked(t));
        var targets = level.getEntitiesOfClass(LivingEntity.class, area, predicate);
        if (targets.isEmpty()) {
            return null;
        }
        int index = owner.getRandom().nextInt(targets.size());
        return targets.get(index);
    }

    private static boolean hasWicked(LivingEntity target) {
        return EntitySpiritDropData.getSpiritData(target).map(e -> e.countSpirit(MalumSpiritTypes.WICKED_SPIRIT) > 0).orElse(false);
    }
}