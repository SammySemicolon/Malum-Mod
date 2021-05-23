package com.sammy.malum.common.rites;

import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;

import static com.sammy.malum.core.modcontent.MalumSpiritTypes.*;

public class RiteOfProtection extends MalumRiteType
{
    public RiteOfProtection()
    {
        super("rite_of_protection", false, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }
}
