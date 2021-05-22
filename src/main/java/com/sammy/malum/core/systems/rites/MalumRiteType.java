package com.sammy.malum.core.systems.rites;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class MalumRiteType
{
    public ArrayList<MalumSpiritType> spirits;
    public String identifier;
    public boolean isInstant;
    public MalumRiteType(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        this.identifier = identifier;
        this.isInstant = isInstant;
        this.spirits = MalumHelper.toArrayList(spirits);
    }
    public int defaultRange()
    {
        return 8;
    }
    public int range()
    {
        return defaultRange();
    }
    public void executeRite(BlockPos pos)
    {

    }
}
