package com.sammy.malum.common.spiritrite;

import com.sammy.malum.core.systems.rites.MalumRiteType;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class ArcaneRiteType extends MalumRiteType
{
    public ArcaneRiteType()
    {
        super("arcane_rite", true, ELDRITCH_SPIRIT, ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT);
    }
}
