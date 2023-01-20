package com.sammy.malum.registry.common;

import com.sammy.malum.core.systems.spirit.MalumSpiritAffinity;

import java.util.HashMap;
import java.util.Map;

public class SpiritAffinityRegistry
{
    public static Map<String, MalumSpiritAffinity> AFFINITIES = new HashMap<>();

    public static MalumSpiritAffinity create(MalumSpiritAffinity affinity)
    {
        AFFINITIES.put(affinity.identifier, affinity);
        return affinity;
    }
}
