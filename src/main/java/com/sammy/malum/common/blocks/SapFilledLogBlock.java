package com.sammy.malum.common.blocks;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import java.awt.*;
import java.util.Random;

public class SapFilledLogBlock extends RotatedPillarBlock
{
    public SapFilledLogBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        particles(worldIn,pos,1);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack itemstack = player.getHeldItem(handIn);
        if (itemstack.getItem() == Items.GLASS_BOTTLE)
        {
            itemstack.shrink(1);
            worldIn.playSound(player, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(MalumItems.SOLAR_SAP_BOTTLE.get()));
            if (worldIn.rand.nextBoolean())
            {
                MalumHelper.setBlockStateWithExistingProperties(worldIn, pos, MalumBlocks.RUNEWOOD_LOG.get().getDefaultState(), 3);
            }
            particles(worldIn,pos,8);
            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
    public void particles(World worldIn, BlockPos pos, int countMultiplier)
    {
        if (MalumHelper.areWeOnClient(worldIn))
        {
            Color color = new Color(227, 154, 45);
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.5f, 0f).setLifetime(40).setScale(0.075f, 0).setColor(color, color.darker()).randomVelocity(0f, 0.01f).enableNoClip().evenlyRepeatEdges(worldIn, pos, (int) (1 * countMultiplier));
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.1f, 0f).setLifetime(80).setScale(0.4f, 0).setColor(color, color.darker()).randomVelocity(0.0025f, 0.0025f).enableNoClip().evenlyRepeatEdges(worldIn, pos, (int) (2 * countMultiplier));
        }
    }
}