package com.sammy.malum.common.spiritritual;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.ritual_plinth.RitualPlinthBlockEntity;
import com.sammy.malum.core.systems.ritual.MalumRitualType;
import com.sammy.malum.registry.common.SpiritTypeRegistry;

public class MarineSpoilRitualType extends MalumRitualType {
    public MarineSpoilRitualType() {
        super(MalumMod.malumPath("marine_spoil"), SpiritTypeRegistry.AQUEOUS_SPIRIT);
    }

    @Override
    public void triggerRitualEffect(RitualPlinthBlockEntity ritualPlinth) {

    }
}
