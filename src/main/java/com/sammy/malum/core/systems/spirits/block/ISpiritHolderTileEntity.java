package com.sammy.malum.core.systems.spirits.block;

public interface ISpiritHolderTileEntity
{
    int getMaxEssence();
    String getEssenceType();
    int getEssenceCount();
    void setType(String type);
    void setCount(int count);
}
