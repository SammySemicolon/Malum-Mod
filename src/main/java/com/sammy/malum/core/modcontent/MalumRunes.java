package com.sammy.malum.core.modcontent;

import com.sammy.malum.common.blocks.totems.TotemPoleBlock;
import com.sammy.malum.common.items.MalumRuneItem;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;

public class MalumRunes
{
    public static ArrayList<MalumRune> RUNES = new ArrayList<>();
    public static MalumRune RUNE_OF_DEATH;
    public static MalumRune RUNE_OF_LIFE;
    public static MalumRune RUNE_OF_WATER;
    public static MalumRune RUNE_OF_EARTH;
    public static MalumRune RUNE_OF_SOUL;
    
    public static void init()
    {
        RUNE_OF_DEATH = new MalumRune("rune_of_death", MalumItems.RUNE_OF_DEATH.get(), MalumBlocks.TOTEM_POLE_RUNE_OF_DEATH.get(), MalumBlocks.TOTEM_POLE_RUNE_OF_DEATH.get());
        RUNE_OF_LIFE = new MalumRune("rune_of_life", MalumItems.RUNE_OF_LIFE.get(), MalumBlocks.TOTEM_POLE_RUNE_OF_LIFE.get(), MalumBlocks.TOTEM_POLE_RUNE_OF_LIFE.get());
        RUNE_OF_WATER = new MalumRune("rune_of_water", MalumItems.RUNE_OF_WATER.get(), MalumBlocks.TOTEM_POLE_RUNE_OF_WATER.get(), MalumBlocks.TOTEM_POLE_RUNE_OF_WATER.get());
        RUNE_OF_EARTH = new MalumRune("rune_of_earth", MalumItems.RUNE_OF_EARTH.get(), MalumBlocks.TOTEM_POLE_RUNE_OF_EARTH.get(), MalumBlocks.TOTEM_POLE_RUNE_OF_EARTH.get());
        RUNE_OF_SOUL = new MalumRune("rune_of_soul", MalumItems.RUNE_OF_SOUL.get(), MalumBlocks.TOTEM_POLE_RUNE_OF_SOUL.get(), MalumBlocks.TOTEM_POLE_RUNE_OF_SOUL.get());
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
        public final Item item;
        public final Block carvedForm;
        public final Block necroticForm;
        
        public MalumRune(String id, Item item, Block carvedForm, Block necroticForm)
        {
            this.id = id;
            this.item = item;
            MalumRuneItem runeItem = (MalumRuneItem) item;
            runeItem.rune = this;
            this.carvedForm = carvedForm;
            this.necroticForm = necroticForm;
            TotemPoleBlock carvedTotemPole = (TotemPoleBlock) carvedForm;
            TotemPoleBlock necroticTotemPole = (TotemPoleBlock) necroticForm;
            carvedTotemPole.rune = this;
            necroticTotemPole.rune = this;
            RUNES.add(this);
        }
    }
}
