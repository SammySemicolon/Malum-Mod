package com.sammy.malum.core.systems.spirit;

import java.util.ArrayList;

public class MalumEntitySpiritData
{
    public static final MalumEntitySpiritData EMPTY = new MalumEntitySpiritData(new ArrayList<>());
    public final int totalCount;
    public final ArrayList<DataEntry> data;

    public MalumEntitySpiritData(ArrayList<DataEntry> data)
    {
        this.totalCount = data.stream().mapToInt(d -> d.count).sum();
        this.data = data;
    }

    public static class DataEntry
    {
        public final MalumSpiritType type;
        public final int count;
        public DataEntry(MalumSpiritType type, int count)
        {
            this.type = type;
            this.count = count;
        }
    }
}
