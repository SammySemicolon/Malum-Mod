package com.sammy.malum.core.systems.spirit;

import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;

public class MalumEntitySpiritData
{
    public static final String NBT = "spirit_data";
    public static final MalumEntitySpiritData EMPTY = new MalumEntitySpiritData(new ArrayList<>());
    public final int totalCount;
    public final ArrayList<DataEntry> data;

    public MalumEntitySpiritData(ArrayList<DataEntry> data)
    {
        this.totalCount = data.stream().mapToInt(d -> d.count).sum();
        this.data = data;
    }

    public void saveTo(CompoundTag tag)
    {
        tag.put(NBT, save());
    }
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("dataAmount", data.size());
        for (int i = 0; i < data.size(); i++) {
            CompoundTag dataTag = data.get(i).save(new CompoundTag());
            dataTag.put("dataEntry"+i, dataTag);
        }
        return tag;
    }

    public static MalumEntitySpiritData load(CompoundTag tag) {
        CompoundTag nbt = tag.getCompound(NBT);

        int dataAmount = nbt.getInt("dataAmount");
        if (dataAmount == 0)
        {
            return EMPTY;
        }
        ArrayList<DataEntry> data = new ArrayList<>();
        for (int i = 0; i < dataAmount; i++) {
            data.add(DataEntry.load(nbt.getCompound("dataEntry"+i)));
        }
        return new MalumEntitySpiritData(data);
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

        public CompoundTag save(CompoundTag tag)
        {
            tag.putString("type", type.identifier);
            tag.putInt("count", count);
            return tag;
        }
        public static DataEntry load(CompoundTag tag)
        {
            MalumSpiritType type = SpiritHelper.getSpiritType(tag.getString("type"));
            int count = tag.getInt("count");
            return new DataEntry(type, count);
        }
    }
}
