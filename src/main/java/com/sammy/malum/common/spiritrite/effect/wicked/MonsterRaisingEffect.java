package com.sammy.malum.common.spiritrite.effect.wicked;

import com.sammy.malum.core.systems.rite.effect.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.server.level.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.monster.*;
import team.lodestar.lodestone.helpers.*;

import java.util.*;

import static com.sammy.malum.registry.common.magic.MalumSpiritTypes.*;

public class MonsterRaisingEffect extends SpiritRiteEntityEffect<Monster> {

    public MonsterRaisingEffect() {
        super(List.of(SpiritRiteEffectTag.GREATER_RITE));
    }

    @Override
    public Class<Monster> getTargetClass() {
        return Monster.class;
    }

    @Override
    public void applyEffect(ServerLevel level, Monster target) {
        DamageSource damageSource = DamageTypeHelper.create(level, MalumDamageTypes.VOODOO_PLAYERLESS);
        target.hurt(damageSource, target.getMaxHealth() * 2); //For good measure
        createEffect(level, target, ELDRITCH_SPIRIT, WICKED_SPIRIT);
    }

    @Override
    public boolean canApplyEffect(ServerLevel level, Monster target) {
        DamageSource damageSource = DamageTypeHelper.create(level, MalumDamageTypes.VOODOO_PLAYERLESS);
        if (target.isInvulnerableTo(damageSource)) {
            return false;
        }
        return target.getHealth() <= Math.max( target.getMaxHealth() * 0.1f, 2.5f );
    }
}