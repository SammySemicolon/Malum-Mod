package com.sammy.malum.common.rites;

import com.sammy.malum.core.systems.spirits.MalumSpiritType;

import static com.sammy.malum.core.modcontent.MalumSpiritTypes.*;
import static com.sammy.malum.core.modcontent.MalumSpiritTypes.INFERNAL_SPIRIT;

public class RiteOfAquaticAssembly extends RiteOfAssembly
{
    public RiteOfAquaticAssembly()
    {
        super(AQUATIC_SPIRIT, "rite_of_aquatic_assembly", true, ELDRITCH_SPIRIT, ARCANE_SPIRIT, AQUATIC_SPIRIT, AQUATIC_SPIRIT);
    }
}
