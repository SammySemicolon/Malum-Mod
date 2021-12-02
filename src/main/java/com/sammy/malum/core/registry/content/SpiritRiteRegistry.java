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

    static {
        addRite(new RiteOfGrowth());
        addRite(new RiteOfDeath());
        addRite(new RiteOfWarding());
        addRite(new RiteOfCelerity());
        addRite(new RiteOfAssembly());
    }

    public static void addRite(MalumRiteType type)
    {
        RITES.add(type);
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
