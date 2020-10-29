package com.sammy.malum.items.staves;

import com.sammy.malum.SpiritStorage;
import com.sammy.malum.items.staves.effects.ModEffect;

public class SpiritwoodStave extends BasicStave implements SpiritStorage
{
    public SpiritwoodStave(Properties builder, ModEffect effect)
    {
        super(builder, effect);
    }
    
    @Override
    public int capacity()
    {
        return 5;
    }
}