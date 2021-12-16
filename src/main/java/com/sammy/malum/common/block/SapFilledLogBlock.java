package com.sammy.malum.common.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.block.BlockRegistry;
import com.sammy.malum.core.registry.item.ItemRegistry;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.Level.Level;
import net.minecraftforge.items.ItemHandlerHelper;

import java.awt.*;

import net.minecraft.block.AbstractBlock.Properties;

public class SapFilledLogBlock extends RotatedPillarBlock
{
    public SapFilledLogBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResultType use(BlockState state, Level LevelIn, BlockPos pos, Player player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack itemstack = player.getItemInHand(handIn);
        if (itemstack.getItem() == Items.GLASS_BOTTLE)
        {
            itemstack.shrink(1);
            LevelIn.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemRegistry.HOLY_SAP.get()));
            if (LevelIn.random.nextBoolean())
            {
                MalumHelper.setBlockStateWithExistingProperties(LevelIn, pos, BlockRegistry.STRIPPED_RUNEWOOD_LOG.get().defaultBlockState(), 3);
            }
            if (MalumHelper.areWeOnClient(LevelIn))
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
                        .evenlyRepeatEdges(LevelIn, pos, 18, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);

                ParticleManager.create(ParticleRegistry.SMOKE_PARTICLE)
                        .setAlpha(0.05f, 0f)
                        .setLifetime(40)
                        .setSpin(0.1f)
                        .setScale(0.5f, 0.2f)
                        .setColor(color, color)
                        .randomOffset(0.1f, 0.1f)
                        .enableNoClip()
                        .randomVelocity(0.001f, 0.001f)
                        .evenlyRepeatEdges(LevelIn, pos, 18, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
            }
            return ActionResultType.SUCCESS;
        }
        return super.use(state, LevelIn, pos, player, handIn, hit);
    }
}