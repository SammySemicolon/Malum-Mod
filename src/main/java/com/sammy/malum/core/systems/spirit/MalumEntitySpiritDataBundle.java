package com.sammy.malum.core.systems.spirit;

import java.util.ArrayList;

public class MalumEntitySpiritDataBundle
{
    public final int totalCount;
    public final ArrayList<MalumEntitySpiritData> data;

    public MalumEntitySpiritDataBundle(ArrayList<MalumEntitySpiritData> data)
    {
        this.totalCount = data.stream().mapToInt(d -> d.count).sum();
        this.data = data;
    }

    public static class MalumEntitySpiritData
    {
        public final MalumSpiritType type;
        public final int count;
        public MalumEntitySpiritData(MalumSpiritType type, int count)
        {
            this.type = type;
            this.count = count;
        }
    }
}
