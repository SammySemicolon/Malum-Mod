package com.sammy.malum.common.item.curiosities.curios.weeping;

import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.systems.item.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.helpers.*;

public class CurioGrowingFleshRing extends MalumCurioItem implements IVoidItem, IMalumEventResponderItem {
    public CurioGrowingFleshRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void pickupSpirit(LivingEntity collector, ItemStack stack, double arcaneResonance) {
        MobEffect cancerousGrowth = MobEffectRegistry.CANCEROUS_GROWTH.get();
        MobEffectInstance effect = collector.getEffect(cancerousGrowth);
        if (effect == null) {
            collector.addEffect(new MobEffectInstance(cancerousGrowth, 1200, 0, true, true, true));
        } else {
            EntityHelper.extendEffect(effect, collector, (int) (1200+arcaneResonance*600), 144000);
            EntityHelper.amplifyEffect(effect, collector, 1, 19);
        }
        Level level = collector.level;
        level.playSound(null, collector.blockPosition(), SoundRegistry.FLESH_RING_ABSORBS.get(), SoundSource.PLAYERS, 0.3f, 1.5f + level.random.nextFloat() * 0.5f);
    }
}