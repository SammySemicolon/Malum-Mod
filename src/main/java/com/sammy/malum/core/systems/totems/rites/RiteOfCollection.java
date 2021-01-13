package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.modcontent.MalumRunes;
import com.sammy.malum.core.systems.particles.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class RiteOfCollection extends MalumRite
{
    public RiteOfCollection(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    
    String tag = MalumMod.MODID + ":collected";
    public void effect(BlockPos pos, ItemEntity entity)
    {
        if (!entity.getTags().contains(tag))
        {
            Vector3d desiredPos = MalumHelper.offsets[entity.world.rand.nextInt(MalumHelper.offsets.length)].add(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            BlockPos extractionFocus = MalumHelper.extractionFocus(entity.world, pos);
            if (extractionFocus != null)
            {
                desiredPos = MalumHelper.pos(extractionFocus).add(0.5, 0.5, 0.5);
            }
            if (MalumHelper.areWeOnClient(entity.world))
            {
                Color color1 = MalumConstants.faded();
                ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(30).setScale(0.1f, 0).setColor(color1.getRed() / 255f, color1.getGreen() / 255f, color1.getBlue() / 255f, color1.getRed() / 255f, (color1.getGreen() * 0.5f) / 255f, (color1.getBlue() * 0.5f) / 255f).randomOffset(entity.getWidth() / 2, entity.getHeight() / 2).randomVelocity(0.01f, 0.01f).enableNoClip().repeat(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), 20);
                ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(30).setScale(0.15f, 0).setColor(color1.getRed() / 255f, color1.getGreen() / 255f, color1.getBlue() / 255f, color1.getRed() / 255f, (color1.getGreen() * 0.5f) / 255f, (color1.getBlue() * 0.5f) / 255f).randomOffset(entity.getWidth() / 2, entity.getHeight() / 2).randomVelocity(0.01f, 0.01f).enableNoClip().repeat(entity.world, desiredPos.x, desiredPos.y, desiredPos.z, 20);
            }
            entity.addTag(tag);
            entity.setPositionAndUpdate(desiredPos.x, desiredPos.y, desiredPos.z);
            
        }
    }
    
    @Override
    public int cooldown()
    {
        return 25;
    }
    
    @Override
    public int range()
    {
        return 8;
    }
    
    @Override
    public void effect(BlockPos pos, World world)
    {
        world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos).grow(range())).stream().filter(e -> !e.getItem().getItem().equals(MalumItems.IMPERVIOUS_ROCK.get()) && !e.getTags().contains(tag) && !world.getBlockState(e.getPosition().down()).getBlock().equals(MalumBlocks.IMPERVIOUS_ROCK.get())).collect(Collectors.toList()).forEach(e -> effect(pos, e));
        super.effect(pos, world);
    }
}