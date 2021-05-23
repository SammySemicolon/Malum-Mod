package com.sammy.malum.common.rites;

import com.sammy.malum.core.systems.rites.MalumRiteType;

import static com.sammy.malum.core.modcontent.MalumSpiritTypes.*;

public class RiteOfGrowth extends MalumRiteType
{
    public RiteOfGrowth()
    {
        super("rite_of_growth", false, ARCANE_SPIRIT, HOLY_SPIRIT, HOLY_SPIRIT);
    }
}
