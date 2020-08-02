package com.kittykitcatcat.malum.items.staves;

import com.kittykitcatcat.malum.SpiritStorage;
import com.kittykitcatcat.malum.items.staves.effects.ModEffect;

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