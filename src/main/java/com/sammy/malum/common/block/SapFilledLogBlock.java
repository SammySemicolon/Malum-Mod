package com.sammy.malum.common.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.block.BlockRegistry;
import com.sammy.malum.core.registry.items.ItemRegistry;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
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
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemRegistry.HOLY_SAP.get()));
            if (worldIn.rand.nextBoolean())
            {
                MalumHelper.setBlockStateWithExistingProperties(worldIn, pos, BlockRegistry.STRIPPED_RUNEWOOD_LOG.get().getDefaultState(), 3);
            }
            if (MalumHelper.areWeOnClient(worldIn))
            {
                Color color = SpiritTypeRegistry.INFERNAL_SPIRIT_COLOR;

                ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                        .setAlpha(0.03f, 0f)
                        .setLifetime(20)
                        .setSpin(0.1f)
                        .setScale(0.4f, 0.3f)
                        .setColor(color, color)
                        .randomOffset(0.1f, 0.1f)
                        .enableNoClip()
                        .randomVelocity(0.001f, 0.001f)
                        .evenlyRepeatEdges(worldIn, pos, 18, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);

                ParticleManager.create(ParticleRegistry.SMOKE_PARTICLE)
                        .setAlpha(0.05f, 0f)
                        .setLifetime(40)
                        .setSpin(0.1f)
                        .setScale(0.5f, 0.2f)
                        .setColor(color, color)
                        .randomOffset(0.1f, 0.1f)
                        .enableNoClip()
                        .randomVelocity(0.001f, 0.001f)
                        .evenlyRepeatEdges(worldIn, pos, 18, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
            }
            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}