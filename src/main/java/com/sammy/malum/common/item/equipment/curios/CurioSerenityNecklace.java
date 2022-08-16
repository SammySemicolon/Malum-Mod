package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class CurioSerenityNecklace extends MalumCurioItem {
    public CurioSerenityNecklace(Properties builder) {
        super(builder);
    }

    @Override
    public boolean isGilded() {
        return true;
    }
}