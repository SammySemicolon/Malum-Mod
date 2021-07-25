package com.sammy.malum.common.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.block.MalumBlocks;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particle.ParticleManager;
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

public class SapFilledLogBlock extends RotatedPillarBlock
{
    public SapFilledLogBlock(Properties properties)
    {
        super(properties);
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
                MalumHelper.setBlockStateWithExistingProperties(worldIn, pos, MalumBlocks.STRIPPED_RUNEWOOD_LOG.get().getDefaultState(), 3);
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
            Color color = new Color(229, 177, 19);
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.3f, 0f).setLifetime(40).setScale(0.075f, 0).setColor(color, color.darker()).enableNoClip().evenlyRepeatEdges(worldIn, pos, countMultiplier);
            ParticleManager.create(MalumParticles.SMOKE_PARTICLE).setAlpha(0.1f, 0f).setLifetime(80).setScale(0.4f, 0).setColor(color, color.darker()).enableNoClip().evenlyRepeatEdges(worldIn, pos, 2 * countMultiplier);
        }
    }
}