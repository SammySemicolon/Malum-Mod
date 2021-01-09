package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.modcontent.MalumRunes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RiteOFDrought extends MalumRite
{
    public RiteOFDrought(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    @Override
    public void effect(BlockPos pos, World world)
    {
        if (MalumHelper.areWeOnServer(world))
        {
            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.func_241113_a_(18000, 0, false, false);
        }
        world.playSound(null, pos, SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.BLOCKS,1,1);
    }
}
