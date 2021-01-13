package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.equipment.poppets.BlessedPoppet;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.systems.particles.ParticleManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.awt.*;

public interface IPoppetBlessing
{
    public void blessingEffect(PlayerEntity entity);
    public default void blessPoppet(BlockPos pos, World world, MalumRites.MalumRite rite)
    {
        world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos).grow(3)).forEach(e -> {
            if (e.getItem().getItem().equals(MalumItems.POPPET.get()))
            {
                if (!world.getBlockState(e.getPosition().down()).getBlock().equals(MalumBlocks.IMPERVIOUS_ROCK.get()))
                {
                    ItemStack stack = new ItemStack(MalumItems.BLESSED_POPPET.get());
                    stack.getOrCreateTag().putString("blessing", rite.identifier);
                    
                    world.addEntity(new ItemEntity(world,e.getPosX(),e.getPosY(),e.getPosZ(),stack));
                    if (MalumHelper.areWeOnClient(world))
                    {
                        Color color1 = MalumConstants.darkest();
                        ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(30).setScale(0.15f, 0).setColor(color1.getRed() / 255f, color1.getGreen() / 255f, color1.getBlue() / 255f, color1.getRed() / 255f, (color1.getGreen() * 0.5f) / 255f, (color1.getBlue() * 0.5f) / 255f).randomOffset(0.2).randomVelocity(0.02f, 0.01f).enableNoClip().repeat(world, e.getPosX(), e.getPosY(), e.getPosZ(), 80);
                    }
                    world.playSound(null, e.getPosition(), SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST_FAR, SoundCategory.BLOCKS, 1, 1.5f);
                    e.remove();
                }
            }
        }
        );
    }
}
