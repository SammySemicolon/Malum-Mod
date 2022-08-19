package com.sammy.malum.common.block.blight;

import com.sammy.malum.common.block.MalumSaplingBlock;
import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.core.setup.content.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
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

import static com.sammy.malum.core.setup.content.block.BlockTagRegistry.BLIGHTED_BLOCKS;

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
    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return false;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (itemInHand.getItem() instanceof MalumSpiritItem spiritItem) {
            if (pLevel instanceof ServerLevel serverLevel) {
                float weight = spiritItem.type.weight;
                for (int i = 0; i < weight; i++) {
                    performBonemeal(serverLevel, pLevel.random, pPos, pState);
                }
                pLevel.playSound(null, pPos, SoundRegistry.MINOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 0.9f + pLevel.random.nextFloat() * 0.25f);
            }
            if (!pPlayer.isCreative()) {
                itemInHand.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}