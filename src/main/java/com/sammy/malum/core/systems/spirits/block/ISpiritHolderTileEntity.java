package com.sammy.malum.core.systems.spirits.block;

public interface ISpiritHolderTileEntity
{
    int maxSpirits();
    String getSpiritType();
    int currentSpirits();
    void setSpiritType(String spiritType);
    void setSpirits(int count);
}
