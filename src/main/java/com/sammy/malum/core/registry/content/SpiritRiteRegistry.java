package com.sammy.malum.core.registry.content;

import com.sammy.malum.common.rites.*;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.awt.*;
import java.util.ArrayList;

public class SpiritRiteRegistry
{
    public static ArrayList<MalumRiteType> RITES = new ArrayList<>();

    public static MalumRiteType SACRED_RITE = create(new RiteOfGrowth());
    public static MalumRiteType WICKED_RITE = create(new RiteOfDeath());
    public static MalumRiteType EARTHEN_RITE = create(new RiteOfWarding());
    public static MalumRiteType AERIAL_RITE = create(new RiteOfCelerity());
    public static MalumRiteType ARCANE_RITE = create(new RiteOfAssembly());


    public static MalumRiteType create(MalumRiteType type)
    {
        RITES.add(type);
        return type;
    }

    public static MalumRiteType getRite(String identifier) {
        for (MalumRiteType rite : RITES) {
            if (rite.identifier.equals(identifier)) {
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
