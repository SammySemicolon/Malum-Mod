package com.sammy.malum.common.items.equipment.curios;

import java.util.UUID;

public class CurioArcaneSpoilRing extends MalumCurioItem
{
    public CurioArcaneSpoilRing(Properties builder)
    {
        super(builder);
    }

    @Override
    public boolean isOrnate()
    {
        return true;
    }

    @Override
    public int spiritYieldBonus()
    {
        return 1;
    }
}