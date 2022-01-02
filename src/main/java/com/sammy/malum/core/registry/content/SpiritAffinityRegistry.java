package com.sammy.malum.core.registry.content;

import com.sammy.malum.common.spiritaffinity.ArcaneAffinity;
import com.sammy.malum.common.spiritaffinity.EarthenAffinity;
import com.sammy.malum.core.systems.spirit.MalumSpiritAffinity;

import java.util.HashMap;

public class SpiritAffinityRegistry
{
    public static HashMap<String, MalumSpiritAffinity> AFFINITIES = new HashMap<>();

    public static final MalumSpiritAffinity ARCANE_AFFINITY = create(new ArcaneAffinity());
    public static final MalumSpiritAffinity EARTHEN_AFFINITY = create(new EarthenAffinity());

    public static MalumSpiritAffinity create(MalumSpiritAffinity affinity)
    {
        AFFINITIES.put(affinity.identifier, affinity);
        return affinity;
    }
}
