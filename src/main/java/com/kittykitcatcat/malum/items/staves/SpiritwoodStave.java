package com.kittykitcatcat.malum.items.staves;

import com.kittykitcatcat.malum.SpiritStorage;
import com.kittykitcatcat.malum.items.staves.effects.ModEffect;
import net.minecraft.item.ShieldItem;

public class SpiritwoodStave extends ModStave implements SpiritStorage
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