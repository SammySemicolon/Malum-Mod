package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;

import java.awt.*;

public class RiteOfSacrifice extends AffectEntitiesRite
{
    public RiteOfSacrifice(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    @Override
    public int range()
    {
        return 8;
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        if (entity instanceof PlayerEntity)
        {
            return;
        }
        if (entity.getMaxHealth() > 30)
        {
            return;
        }
        SpiritHelper.summonSpirits(entity);
        entity.onKillCommand();
    }
}
