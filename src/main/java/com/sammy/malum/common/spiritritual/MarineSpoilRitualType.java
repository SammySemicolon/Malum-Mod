package com.sammy.malum.common.spiritritual;

import com.sammy.malum.*;
import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.registry.common.*;

public class MarineSpoilRitualType extends MalumRitualType {
    public MarineSpoilRitualType() {
        super(MalumMod.malumPath("marine_spoil"), SpiritTypeRegistry.AQUEOUS_SPIRIT);
    }

    @Override
    public void triggerRitualEffect(RitualPlinthBlockEntity ritualPlinth) {

    }
}
