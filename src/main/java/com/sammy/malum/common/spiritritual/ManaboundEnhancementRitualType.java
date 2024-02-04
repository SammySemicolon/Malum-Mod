package com.sammy.malum.common.spiritritual;

import com.sammy.malum.*;
import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ManaboundEnhancementRitualType extends MalumRitualType {
    public ManaboundEnhancementRitualType() {
        super(MalumMod.malumPath("manabound_enhancement"), SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    @Override
    public void triggerRitualEffect(RitualPlinthBlockEntity ritualPlinth) {
    }

    @Override
    public boolean isItemStackValid(RitualPlinthBlockEntity ritualPlinth, ItemStack stack) {
        return stack.getItem() instanceof ICurioItem;
    }
}
