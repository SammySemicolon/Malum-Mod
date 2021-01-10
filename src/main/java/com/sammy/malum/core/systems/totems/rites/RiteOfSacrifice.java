package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.awt.*;

public class RiteOfSacrifice extends AffectEntitiesRite
{
    public RiteOfSacrifice(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
    {
        super(identifier, isInstant, runes);
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
        int count = SpiritHelper.totalSpirits(entity);
        entity.world.addEntity(new ItemEntity(entity.world, entity.getPosXRandom(1),entity.getPosYRandom(), entity.getPosZRandom(1), new ItemStack(MalumItems.ETHER.get(), Math.min(count, 64))));
        entity.onKillCommand();
        Color color1 = MalumConstants.faded();
        Color color2 = MalumConstants.darkest();
        entity.world.playSound(null, entity.getPosition(), MalumSounds.SPIRIT_HARVEST, SoundCategory.NEUTRAL,1,1.5f + entity.world.rand.nextFloat() * 0.5f);
        if (MalumHelper.areWeOnClient(entity.world))
        {
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(30).setScale(0.15f, 0).setColor(color1.getRed() / 255f, color1.getGreen() / 255f, color1.getBlue() / 255f, color2.getRed() / 255f, (color2.getGreen() * 0.5f) / 255f, (color2.getBlue() * 0.5f) / 255f).randomOffset(entity.getWidth() / 2, entity.getHeight() / 2).randomVelocity(0.01f, 0.01f).enableNoClip().repeat(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), 80);
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(30).setScale(0.2f, 0).setColor(color2.getRed() / 255f, color2.getGreen() / 255f, color2.getBlue() / 255f, color2.getRed() / 255f, (color2.getGreen() * 0.5f) / 255f, (color2.getBlue() * 0.5f) / 255f).randomOffset(entity.getWidth() / 2, entity.getHeight() / 2).randomVelocity(0.01f, 0.01f).enableNoClip().repeat(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), 80);
        }
        super.effect(entity);
    }
}
