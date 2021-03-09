package com.sammy.malum.core.modcontent;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.totems.rites.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

import static com.sammy.malum.core.modcontent.MalumSpiritTypes.*;

public class MalumRites
{
    public static ArrayList<MalumRite> RITES = new ArrayList<>();
    
    public static void init()
    {
        //new RiteOfIgnition("rite_of_ignition",true, MAGIC_SPIRIT, FIRE_SPIRIT, FIRE_SPIRIT);
        
        new RiteOfLife("rite_of_life",false, MAGIC_SPIRIT, LIFE_SPIRIT, LIFE_SPIRIT); //entity effects
        new RiteOfDeath("rite_of_death",false, MAGIC_SPIRIT, DEATH_SPIRIT, DEATH_SPIRIT);
        new RiteOfMagic("rite_of_warding",false, MAGIC_SPIRIT, MAGIC_SPIRIT, MAGIC_SPIRIT);
        new RiteOfFire("rite_of_fire",false, MAGIC_SPIRIT, FIRE_SPIRIT, FIRE_SPIRIT);
        new RiteOfEarth("rite_of_earth",false, MAGIC_SPIRIT, EARTH_SPIRIT, EARTH_SPIRIT);
        new RiteOfAir("rite_of_air",false, MAGIC_SPIRIT, AIR_SPIRIT, AIR_SPIRIT);
        new RiteOfWater("rite_of_water",false, MAGIC_SPIRIT, WATER_SPIRIT, WATER_SPIRIT);
        
        new RiteOfLevitation("rite_of_levitation",false, AIR_SPIRIT);
        
        //block effects
        new RiteOfGrowth("rite_of_growth",false, MAGIC_SPIRIT, EARTH_SPIRIT, LIFE_SPIRIT, EARTH_SPIRIT, LIFE_SPIRIT);
        
        //instant effects
        new RiteOFDrought("rite_of_drought",true, MAGIC_SPIRIT, WATER_SPIRIT, DEATH_SPIRIT);
        new RiteOfRain("rite_of_rain",true, MAGIC_SPIRIT, WATER_SPIRIT, LIFE_SPIRIT);
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
    
    public static MalumRite getRite(ArrayList<MalumSpiritType> spirits)
    {
        for (MalumRite rite : RITES)
        {
            if (rite.spirits.equals(spirits))
            {
                return rite;
            }
        }
        return null;
    }
    
    public static class MalumRite
    {
        public final ArrayList<MalumSpiritType> spirits;
        public final boolean isInstant;
        public final String identifier;
        public final String translationKey;
        public final String description;
    
        public MalumRite(String identifier, boolean isInstant, MalumSpiritType... spirits)
        {
            this.identifier = identifier;
            this.translationKey = "malum.tooltip.rite." + identifier;
            this.description = translationKey + "_description";
            this.isInstant = isInstant;
            this.spirits = MalumHelper.toArrayList(spirits);
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