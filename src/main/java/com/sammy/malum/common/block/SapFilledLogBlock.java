package com.sammy.malum.common.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.block.BlockRegistry;
import com.sammy.malum.core.registry.item.ItemRegistry;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockHitResult;
import net.minecraft.world.level.Level;
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        ItemStack itemstack = player.getItemInHand(handIn);
        if (itemstack.getItem() == Items.GLASS_BOTTLE)
        {
            itemstack.shrink(1);
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemRegistry.HOLY_SAP.get()));
            if (level.random.nextBoolean())
            {
                MalumHelper.setBlockStateWithExistingProperties(level, pos, BlockRegistry.STRIPPED_RUNEWOOD_LOG.get().defaultBlockState(), 3);
            }
            if (level.isClientSide)
            {
                Color color = SpiritTypeRegistry.INFERNAL_SPIRIT_COLOR;

                RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                        .setAlpha(0.03f, 0f)
                        .setLifetime(20)
                        .setSpin(0.1f)
                        .setScale(0.4f, 0.3f)
                        .setColor(color, color)
                        .randomOffset(0.1f, 0.1f)
                        .enableNoClip()
                        .randomVelocity(0.001f, 0.001f)
                        .evenlyRepeatEdges(level, pos, 18, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);

                RenderUtilities.create(ParticleRegistry.SMOKE_PARTICLE)
                        .setAlpha(0.05f, 0f)
                        .setLifetime(40)
                        .setSpin(0.1f)
                        .setScale(0.5f, 0.2f)
                        .setColor(color, color)
                        .randomOffset(0.1f, 0.1f)
                        .enableNoClip()
                        .randomVelocity(0.001f, 0.001f)
                        .evenlyRepeatEdges(level, pos, 18, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, handIn, hit);
    }
}