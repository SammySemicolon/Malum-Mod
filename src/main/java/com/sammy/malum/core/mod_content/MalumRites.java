package com.sammy.malum.core.mod_content;

import com.sammy.malum.common.rites.*;
import com.sammy.malum.core.mod_systems.rites.MalumRiteType;
import com.sammy.malum.core.mod_systems.spirit.MalumSpiritType;

import java.util.ArrayList;

public class MalumRites
{
    public static ArrayList<MalumRiteType> RITES = new ArrayList<>();

    public static void init()
    {
        RITES.add(new RiteOfGrowth());
        RITES.add(new RiteOfDeath());
        RITES.add(new RiteOfWarding());
        RITES.add(new RiteOfCelerity());
        RITES.add(new RiteOfAssembly());
    }

    public static MalumRiteType getRite(String identifier)
    {
        for (MalumRiteType rite : RITES)
        {
            if (rite.identifier.equals(identifier))
            {
                return rite;
            }
        }
        return null;
    }
    public static MalumRiteType getRite(ArrayList<MalumSpiritType> spirits)
    {
        for (MalumRiteType rite : RITES)
        {
            if (rite.spirits.equals(spirits))
            {
                return rite;
            }
        }
        return null;
    }
}
