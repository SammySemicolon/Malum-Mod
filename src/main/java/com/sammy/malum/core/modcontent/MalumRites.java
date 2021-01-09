package com.sammy.malum.core.modcontent;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.totems.rites.*;
import net.minecraft.nbt.CompoundNBT;
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
        new RiteOfEther("rite_of_ether",true, RUNE_OF_EARTH, RUNE_OF_DEATH);
        new RiteOfTransmutation("rite_of_transmutation",false, RUNE_OF_EARTH, RUNE_OF_SOUL);
        new RiteOfLife("rite_of_life",false, RUNE_OF_LIFE, RUNE_OF_LIFE,RUNE_OF_LIFE);
        new RiteOfDeath("rite_of_death",false, RUNE_OF_DEATH, RUNE_OF_DEATH,RUNE_OF_DEATH,RUNE_OF_DEATH, RUNE_OF_DEATH);
        new RiteOfFortitude("rite_of_fortitude",false, RUNE_OF_EARTH, RUNE_OF_EARTH, RUNE_OF_EARTH);
        new RiteOfGrowth("rite_of_growth",false, RUNE_OF_EARTH, RUNE_OF_LIFE,RUNE_OF_LIFE,RUNE_OF_LIFE);
        new RiteOFDrought("rite_of_drought",true, RUNE_OF_WATER, RUNE_OF_SOUL, RUNE_OF_DEATH);
        new RiteOfRain("rite_of_rain",true, RUNE_OF_WATER, RUNE_OF_SOUL, RUNE_OF_LIFE);
    }
    public static MalumRite getRite(ArrayList<MalumRunes.MalumRune> runes)
    {
        for (MalumRite recipe : RITES)
        {
            if (recipe.runes.equals(runes))
            {
                return recipe;
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
    }
}