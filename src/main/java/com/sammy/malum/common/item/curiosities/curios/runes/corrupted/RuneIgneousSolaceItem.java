package com.sammy.malum.common.item.curiosities.curios.runes.corrupted;

import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.function.*;

public class RuneIgneousSolaceItem extends MalumRuneCurioItem implements IEventResponderItem {

    public RuneIgneousSolaceItem(Properties builder) {
        super(builder, SpiritTypeRegistry.INFERNAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.rune.effect.igneous_solace"));
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked.isOnFire()) {
            event.setAmount(event.getAmount()*0.75f);
        }
    }
}