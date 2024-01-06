package com.sammy.malum.registry.common;

import com.sammy.malum.common.spiritritual.*;
import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.core.systems.ritual.*;
import net.minecraft.resources.*;

import java.util.*;

public class RitualRegistry {
    public static Map<ResourceLocation, MalumRitualType> RITUALS = new HashMap<>();

    public static MalumRitualType RITUAL_OF_IDLE_MENDING = create(new IdleMendingRitualType());
    public static MalumRitualType RITUAL_OF_GROTESQUE_EXPULSION = create(new GrotesqueExpulsionRitualType());
    public static MalumRitualType RITUAL_OF_MANABOUND_ENHANCEMENT = create(new ManaboundEnhancementRitualType());

    public static MalumRitualType create(MalumRitualType type) {
        RITUALS.put(type.identifier, type);
        return type;
    }
}
