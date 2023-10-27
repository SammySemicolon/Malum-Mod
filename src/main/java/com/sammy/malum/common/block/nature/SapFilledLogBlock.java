package com.sammy.malum.common.block.nature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemHandlerHelper;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;
import java.util.function.Supplier;

public class SapFilledLogBlock extends RotatedPillarBlock {
    public final Supplier<Block> drained;
    public final Supplier<Item> sap;
    public final Color sapColor;

    public SapFilledLogBlock(Properties properties, Supplier<Block> drained, Supplier<Item> sap, Color sapColor) {
        super(properties);
        this.drained = drained;
        this.sap = sap;
        this.sapColor = sapColor;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack itemstack = player.getItemInHand(handIn);
        if (itemstack.getItem() == Items.GLASS_BOTTLE) {
            if (!level.isClientSide) {
                itemstack.shrink(1);
                collectSap(level, pos, player);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, handIn, hit);
    }

    public void collectSap(Level level, BlockPos pos, Player player) {
        level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(sap.get()));
        if (level.random.nextBoolean()) {
            BlockHelper.setBlockStateWithExistingProperties(level, pos, drained.get().defaultBlockState(), 3);
        }
        if (level.isClientSide) {
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.16f, 0f).build())
                    .setSpinData(SpinParticleData.create(0.2f).build())
                    .setScaleData(GenericParticleData.create(0.2f, 0).build())
                    .setColorData(ColorParticleData.create(sapColor, sapColor).build())
                    .setLifetime(20)
                    .enableNoClip()
                    .setRandomOffset(0.1f, 0.1f)
                    .setRandomMotion(0.001f, 0.001f)
                    .repeatSurroundBlock(level, pos, 8, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.08f, 0f).build())
                    .setSpinData(SpinParticleData.create(0.1f).build())
                    .setScaleData(GenericParticleData.create(0.4f, 0).build())
                    .setColorData(ColorParticleData.create(sapColor, sapColor).build())
                    .setLifetime(40)
                    .setRandomOffset(0.2f)
                    .enableNoClip()
                    .setRandomMotion(0.001f, 0.001f)
                    .repeatSurroundBlock(level, pos, 12, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
        }
    }
}