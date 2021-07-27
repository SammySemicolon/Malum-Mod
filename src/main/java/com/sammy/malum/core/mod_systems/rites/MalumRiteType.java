package com.sammy.malum.core.mod_systems.rites;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.mod_systems.spirit.MalumSpiritType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

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
    public int defaultInterval()
    {
        return 20;
    }
    public int interval()
    {
        return defaultInterval();
    }
    public void executeRite(ServerWorld world, BlockPos pos)
    {

    }
}
