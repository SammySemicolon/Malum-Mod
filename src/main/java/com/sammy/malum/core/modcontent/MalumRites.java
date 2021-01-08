package com.sammy.malum.core.modcontent;

import com.sammy.malum.MalumHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

import static com.sammy.malum.core.modcontent.MalumRunes.RUNE_OF_DEATH;
import static com.sammy.malum.core.modcontent.MalumRunes.RUNE_OF_LIFE;

public class MalumRites
{
    public static ArrayList<MalumRite> recipes = new ArrayList<>();
    
    public static void init()
    {
        new MalumRite(true, RUNE_OF_DEATH, RUNE_OF_LIFE);
    }
    public static MalumRite getRite(ArrayList<MalumRunes.MalumRune> runes)
    {
        for (MalumRite recipe : recipes)
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
        public MalumRite(boolean isInstant, MalumRunes.MalumRune... runes)
        {
            this.runes = MalumHelper.toArrayList(runes);
            this.isInstant = isInstant;
            recipes.add(this);
        }
        public void effect(BlockPos pos, World world)
        {
    
        }
    }
}