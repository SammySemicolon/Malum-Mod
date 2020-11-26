package com.sammy.malum.core.systems.essences;

public interface IEssenceHolderTileEntity
{
    int getMaxEssence();
    String getEssenceType();
    int getEssenceCount();
    void setType(String type);
    void setCount(int count);
}
