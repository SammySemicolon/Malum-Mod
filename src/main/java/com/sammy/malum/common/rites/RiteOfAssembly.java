package com.sammy.malum.common.rites;

import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;

import static com.sammy.malum.core.modcontent.MalumSpiritTypes.*;

public class RiteOfAssembly extends MalumRiteType
{
    public RiteOfAssembly()
    {
        super("rite_of_assembly", true, ELDRITCH_SPIRIT, ARCANE_SPIRIT, AQUATIC_SPIRIT, INFERNAL_SPIRIT);
    }
    public interface IAssembled
    {
        public void assemble();
    }
}
