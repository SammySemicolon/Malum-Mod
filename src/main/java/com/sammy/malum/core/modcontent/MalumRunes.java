package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;

public class MalumRunes
{
    public static ArrayList<MalumRune> RUNES = new ArrayList<>();
    public static MalumRune RUNE_OF_DEATH;
    public static MalumRune RUNE_OF_LIFE;
    public static void init()
    {
        RUNE_OF_DEATH = new MalumRune("rune_of_death", MalumBlocks.TOTEM_POLE_RUNE_OF_DEATH.get(), MalumBlocks.TOTEM_POLE_RUNE_OF_DEATH.get());
        RUNE_OF_LIFE = new MalumRune("rune_of_life", MalumBlocks.TOTEM_POLE_RUNE_OF_LIFE.get(), MalumBlocks.TOTEM_POLE_RUNE_OF_LIFE.get());
    }
    public static MalumRune getRune(String id)
    {
        for (MalumRune rune : RUNES)
        {
            if (rune.id.equals(id))
            {
                return rune;
            }
        }
        return null;
    }
    public static MalumRune getRune(Block block)
    {
        for (MalumRune rune : RUNES)
        {
            if (rune.carvedForm.equals(block) || rune.necroticForm.equals(block))
            {
                return rune;
            }
        }
        return null;
    }
    public static class MalumRune
    {
        public final String id;
        public final Block carvedForm;
        public final Block necroticForm;
        
        public MalumRune(String id, Block carvedForm, Block necroticForm)
        {
            this.id = id;
            this.carvedForm = carvedForm;
            this.necroticForm = necroticForm;
            RUNES.add(this);
        }
    }
}
