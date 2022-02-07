package com.sammy.malum.core.systems.multiblock;

import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MultiBlockComponentEntity extends SimpleBlockEntity {

    public BlockPos corePos;
    public MultiBlockComponentEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public MultiBlockComponentEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.MULTIBLOCK_COMPONENT.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        BlockHelper.saveBlockPos(tag, corePos);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        corePos = BlockHelper.loadBlockPos(tag);
        super.load(tag);
    }


    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (level.getBlockEntity(corePos) instanceof MultiBlockCoreEntity core)
        {
            return core.onUse(player, hand);
        }
        return super.onUse(player, hand);
    }

    @Override
    public void onBreak() {
        if (level.getBlockEntity(corePos) instanceof MultiBlockCoreEntity core)
        {
            core.onBreak();
        }
        super.onBreak();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (level.getBlockEntity(corePos) instanceof MultiBlockCoreEntity core)
        {
            return core.getCapability(cap);
        }
        return super.getCapability(cap);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (level.getBlockEntity(corePos) instanceof MultiBlockCoreEntity core)
        {
            return core.getCapability(cap, side);
        }
        return super.getCapability(cap, side);
    }
}
