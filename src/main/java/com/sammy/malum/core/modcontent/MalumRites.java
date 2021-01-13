package com.sammy.malum.core.modcontent;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.totems.rites.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

import static com.sammy.malum.core.modcontent.MalumRunes.*;

public class MalumRites
{
    public static ArrayList<MalumRite> RITES = new ArrayList<>();
    
    //rune of death allows the rite to cause any form of harm
    //rune of life acts as the opposite of death
    //rune of water allows the rite to affect the world on a large scale
    //rune of earth allows the rite to affect the world on a tiny scale
    //rune of soul allows for much stronger rites, it's a progression point
    public static void init()
    {
        new RiteOfSacrifice("rite_of_sacrifice",true, RUNE_OF_LIFE, RUNE_OF_DEATH);
        new RiteOfImbuement("rite_of_imbuement",false, RUNE_OF_EARTH, RUNE_OF_SOUL);
        new RiteOfLife("rite_of_life",false, RUNE_OF_LIFE, RUNE_OF_LIFE,RUNE_OF_LIFE,RUNE_OF_LIFE);
        new RiteOfDeath("rite_of_death",false, RUNE_OF_DEATH, RUNE_OF_DEATH,RUNE_OF_DEATH,RUNE_OF_DEATH);
        new RiteOfWater("rite_of_water",false, RUNE_OF_WATER, RUNE_OF_WATER,RUNE_OF_WATER,RUNE_OF_WATER);
        new RiteOfFortitude("rite_of_fortitude",false, RUNE_OF_EARTH, RUNE_OF_EARTH, RUNE_OF_EARTH, RUNE_OF_EARTH);
        new RiteOfAir("rite_of_air",true, RUNE_OF_AIR, RUNE_OF_AIR, RUNE_OF_AIR, RUNE_OF_AIR);
        new RiteOfAgility("rite_of_agility",false, RUNE_OF_AIR, RUNE_OF_LIFE,RUNE_OF_AIR,RUNE_OF_AIR,RUNE_OF_AIR);
        new RiteOfLevitation("rite_of_levitation",false, RUNE_OF_AIR);
        new RiteOfGrowth("rite_of_growth",false, RUNE_OF_EARTH, RUNE_OF_LIFE,RUNE_OF_LIFE,RUNE_OF_LIFE);
        new RiteOfWarding("rite_of_warding",false, RUNE_OF_WATER, RUNE_OF_EARTH,RUNE_OF_SOUL,RUNE_OF_WATER,RUNE_OF_WATER);
        new RiteOfDestruction("rite_of_destruction",false, RUNE_OF_DEATH, RUNE_OF_EARTH,RUNE_OF_EARTH,RUNE_OF_SOUL,RUNE_OF_DEATH);
        new RiteOfCollection("rite_of_collection",false, RUNE_OF_AIR, RUNE_OF_EARTH,RUNE_OF_SOUL,RUNE_OF_SOUL);
        new RiteOFDrought("rite_of_drought",true, RUNE_OF_WATER, RUNE_OF_SOUL, RUNE_OF_DEATH);
        new RiteOfRain("rite_of_rain",true, RUNE_OF_WATER, RUNE_OF_SOUL, RUNE_OF_LIFE);
    }
    
    public static MalumRite getRite(String identifier)
    {
        for (MalumRite rite : RITES)
        {
            if (rite.identifier.equals(identifier))
            {
                return rite;
            }
        }
        return null;
    }
    public static MalumRite getRite(ArrayList<MalumRunes.MalumRune> runes)
    {
        for (MalumRite rite : RITES)
        {
            if (rite.runes.equals(runes))
            {
                return rite;
            }
        }
        return null;
    }
    public static class MalumRite
    {
        public final ArrayList<MalumRunes.MalumRune> runes;
        public final boolean isInstant;
        public final String identifier;
        public final String translationKey;
        public final String description;
        public MalumRite(String identifier,boolean isInstant, MalumRunes.MalumRune... runes)
        {
            this.identifier = identifier;
            this.translationKey = "malum.tooltip.rite." + identifier;
            this.description = translationKey + "_description";
            this.isInstant = isInstant;
            this.runes = MalumHelper.toArrayList(runes);
            RITES.add(this);
        }
        public void effect(BlockPos pos, World world)
        {
        }
        public int range()
        {
            return 1;
        }
        public int cooldown()
        {
            return 0;
        }
    }
}