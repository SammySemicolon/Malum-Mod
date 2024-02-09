package com.sammy.malum.common.item.curiosities.runes;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;

public class RuneReactiveShieldingItem extends MalumRuneCurioItem implements IEventResponderItem {

    public RuneReactiveShieldingItem(Properties builder) {
        super(builder, SpiritTypeRegistry.EARTHEN_SPIRIT);
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        
    }

    @Override
    public Optional<String> getEffectDescriptor() {
        return Optional.of("reactive_shielding");
    }
}