package com.sammy.malum.common.blockentity.tablet;

import com.sammy.malum.core.helper.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.function.Predicate;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public interface ITabletTracker {
    void setTablet(TwistedTabletBlockEntity blockEntity);

    TwistedTabletBlockEntity getTablet();

    void setTabletPos(BlockPos pos);

    BlockPos getTabletPos();

    default Predicate<TwistedTabletBlockEntity> getLookupPredicate() {
        return t -> true;
    }

    default int getLookupRange() {
        return 4;
    }

    default void fetchTablet(Level level, BlockPos pos) {
        int range = getLookupRange();
        ArrayList<TwistedTabletBlockEntity> nearbyTablets = BlockHelper.getBlockEntities(TwistedTabletBlockEntity.class, level, pos, range, range, getLookupPredicate());
        TwistedTabletBlockEntity matchingTablet = null;
        for (TwistedTabletBlockEntity tabletBlockEntity : nearbyTablets) {
            Direction direction = tabletBlockEntity.getBlockState().getValue(FACING);
            BlockPos tabletPos = tabletBlockEntity.getBlockPos();
            if (tabletPos.getZ() == pos.getZ()) {
                boolean positive = tabletPos.getX() > pos.getX();
                if (direction != (positive ? Direction.WEST : Direction.EAST)) {
                    continue;
                }
                matchingTablet = tabletBlockEntity;
                break;
            } else if (tabletPos.getX() == pos.getX()) {
                boolean positive = tabletPos.getZ() > pos.getZ();
                if (direction != (positive ? Direction.NORTH : Direction.SOUTH)) {
                    continue;
                }
                matchingTablet = tabletBlockEntity;
                break;
            }
        }
        setTablet(matchingTablet);
        setTabletPos(matchingTablet == null ? null : matchingTablet.getBlockPos());
    }

    default void saveTwistedTabletData(CompoundTag compound) {
        CompoundTag twistedTabletTag = new CompoundTag();
        if (getTabletPos() != null) {
            BlockHelper.saveBlockPos(twistedTabletTag, getTabletPos());
            compound.put("twistedTabletData", twistedTabletTag);
        }
    }

    default void loadTwistedTabletData(Level level, CompoundTag compound) {
        if (compound.contains("twistedTabletData")) {
            CompoundTag twistedTabletTag = compound.getCompound("twistedTabletData");
            setTabletPos(BlockHelper.loadBlockPos(twistedTabletTag));
            if (level != null && getTabletPos() != null && level.getBlockEntity(getTabletPos()) instanceof TwistedTabletBlockEntity twistedTablet) {
                setTablet(twistedTablet);
            }
        }
    }
}