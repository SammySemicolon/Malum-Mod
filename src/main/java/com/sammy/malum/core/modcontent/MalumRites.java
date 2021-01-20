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
        new RiteOfSacrifice("rite_of_sacrifice",true, LIFE_SPIRIT, DEATH_SPIRIT);
        new RiteOfImbuement("rite_of_imbuement",false, EARTH_SPIRIT, MAGIC_SPIRIT);
        new RiteOfLife("rite_of_life",false, LIFE_SPIRIT, LIFE_SPIRIT, LIFE_SPIRIT, LIFE_SPIRIT);
        new RiteOfDeath("rite_of_death",false, DEATH_SPIRIT, DEATH_SPIRIT, DEATH_SPIRIT, DEATH_SPIRIT);
        new RiteOfWater("rite_of_water",false, WATER_SPIRIT, WATER_SPIRIT, WATER_SPIRIT, WATER_SPIRIT);
        new RiteOfFortitude("rite_of_fortitude",false, EARTH_SPIRIT, EARTH_SPIRIT, EARTH_SPIRIT, EARTH_SPIRIT);
        new RiteOfAir("rite_of_air",true, AIR_SPIRIT, AIR_SPIRIT, AIR_SPIRIT, AIR_SPIRIT);
        new RiteOfAgility("rite_of_agility",false, AIR_SPIRIT, LIFE_SPIRIT, AIR_SPIRIT, AIR_SPIRIT, AIR_SPIRIT);
        new RiteOfLevitation("rite_of_levitation",false, AIR_SPIRIT);
        new RiteOfGrowth("rite_of_growth",false, EARTH_SPIRIT, LIFE_SPIRIT, LIFE_SPIRIT, LIFE_SPIRIT);
        new RiteOfWarding("rite_of_warding",false, WATER_SPIRIT, EARTH_SPIRIT, MAGIC_SPIRIT, WATER_SPIRIT, WATER_SPIRIT);
        new RiteOfDestruction("rite_of_destruction",false, DEATH_SPIRIT, EARTH_SPIRIT,EARTH_SPIRIT, MAGIC_SPIRIT, DEATH_SPIRIT);
        new RiteOfCollection("rite_of_collection",false, AIR_SPIRIT, EARTH_SPIRIT, MAGIC_SPIRIT, MAGIC_SPIRIT);
        new RiteOFDrought("rite_of_drought",true, WATER_SPIRIT, MAGIC_SPIRIT, DEATH_SPIRIT);
        new RiteOfRain("rite_of_rain",true, WATER_SPIRIT, MAGIC_SPIRIT, LIFE_SPIRIT);
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