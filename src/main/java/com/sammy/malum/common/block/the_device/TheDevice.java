package com.sammy.malum.common.block.the_device;

import com.sammy.malum.common.components.MalumPlayerDataCapability;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.network.screenshake.PositionedScreenshakePacket;
import team.lodestar.lodestone.registry.common.LodestonePacketRegistry;
import team.lodestar.lodestone.systems.easing.Easing;

import static com.sammy.malum.MalumMod.RANDOM;

public class TheDevice extends Block {
    private final int funnyComparatorNumber;

    public TheDevice(Properties properties) {
        super(properties);
        funnyComparatorNumber = RANDOM.nextInt(16);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pHand.equals(InteractionHand.MAIN_HAND)) {
            pPlayer.swing(pHand, true);
            playSound(pLevel, pPos);
            if (pPlayer.isCreative())
                MalumPlayerDataCapability.getCapabilityOptional(pPlayer).ifPresent(it -> it.hasBeenRejected = false);
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return funnyComparatorNumber;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        boolean flag = pLevel.hasNeighborSignal(pPos);
        if (flag) {
            playSound(pLevel, pPos);
        }
    }

    public void playSound(Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            LodestonePacketRegistry.LODESTONE_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> serverLevel.getChunkAt(pos)), new PositionedScreenshakePacket(40, BlockHelper.fromBlockPos(pos), 4f, 10f, Easing.EXPO_OUT).setIntensity(4f, 0));
        }
        level.playSound(null, pos, SoundRegistry.THE_DEEP_BECKONS.get(), SoundSource.BLOCKS, 1, 1);
    }
}
