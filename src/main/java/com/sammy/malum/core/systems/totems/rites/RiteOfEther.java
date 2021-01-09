package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.modcontent.MalumRunes;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;

import java.awt.*;

public class RiteOfEther extends AffectEntitiesRite
{
    public RiteOfEther(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        if (entity instanceof PlayerEntity)
        {
            return;
        }
        int count = SpiritHelper.totalSpirits(entity);
        entity.world.addEntity(new ItemEntity(entity.world, entity.getPosXRandom(1),entity.getPosYRandom(), entity.getPosZRandom(1), new ItemStack(MalumItems.ETHER.get(), Math.min(count, 64))));
        entity.onKillCommand();
        Color color = MalumConstants.bright();
        entity.world.playSound(null, entity.getPosition(), MalumSounds.SPIRIT_HARVEST, SoundCategory.NEUTRAL,1,1.5f + entity.world.rand.nextFloat() * 0.5f);
        ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(60).setScale(0.075f, 0).setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getRed() / 255f, (color.getGreen() * 0.5f) / 255f, (color.getBlue() * 0.5f) / 255f).randomOffset(0.1f, entity.getHeight()).randomVelocity(0.01f, 0.025f).enableNoClip().repeat(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), 40);
    
        super.effect(entity);
    }
}
