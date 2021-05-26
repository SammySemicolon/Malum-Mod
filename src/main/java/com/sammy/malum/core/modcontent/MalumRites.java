package com.sammy.malum.core.modcontent;

import com.sammy.malum.common.rites.RiteOfDeath;
import com.sammy.malum.common.rites.RiteOfGrowth;
import com.sammy.malum.common.rites.RiteOfProtection;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;

import java.util.ArrayList;

public class MalumRites
{
    public static ArrayList<MalumRiteType> RITES = new ArrayList<>();

    public static void init()
    {
        RITES.add(new RiteOfGrowth());
        RITES.add(new RiteOfDeath());
        RITES.add(new RiteOfProtection());
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
