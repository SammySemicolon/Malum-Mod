package com.sammy.malum.common.block.nature.soulwood;

import com.sammy.malum.common.block.nature.MalumSaplingBlock;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;

import static com.sammy.malum.registry.common.block.BlockTagRegistry.BLIGHTED_BLOCKS;

public class SoulwoodGrowthBlock extends MalumSaplingBlock {
    public SoulwoodGrowthBlock(Properties properties, RegistryObject<? extends Feature<NoneFeatureConfiguration>> tree) {
        super(properties, tree);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (pState.is(BLIGHTED_BLOCKS)) {
            return true;
        }
        return super.mayPlaceOn(pState, pLevel, pPos);
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRand, BlockPos pPos, BlockState pState) {
        super.performBonemeal(pLevel, pRand, pPos, pState);
        pLevel.levelEvent(1505, pPos, 0);
        pLevel.playSound(null, pPos, SoundRegistry.MINOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 0.9f + pLevel.random.nextFloat() * 0.25f);
        pLevel.playSound(null, pPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1, 0.9f + pLevel.random.nextFloat() * 0.25f);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (itemInHand.getItem() instanceof SpiritShardItem) {
            if (pLevel instanceof ServerLevel serverLevel) {
                performBonemeal(serverLevel, pLevel.random, pPos, pState);
            }
            if (!pPlayer.isCreative()) {
                itemInHand.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}