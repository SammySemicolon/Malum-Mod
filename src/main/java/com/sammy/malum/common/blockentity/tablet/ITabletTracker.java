package com.sammy.malum.common.blockentity.tablet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import team.lodestar.lodestone.helpers.BlockHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public interface ITabletTracker {
    List<TwistedTabletBlockEntity> getTablets();

    List<BlockPos> getTabletPositions();

    default int getLookupRange() {
        return 4;
    }

    default void fetchTablets(Level level, BlockPos pos) {
        getTablets().clear();
        getTabletPositions().clear();
        int range = getLookupRange();
        Collection<TwistedTabletBlockEntity> nearbyTablets = BlockHelper.getBlockEntities(TwistedTabletBlockEntity.class, level, pos, range);

        nearbyTablets = nearbyTablets.stream().filter(tabletBlockEntity -> {
            Direction direction = tabletBlockEntity.getBlockState().getValue(FACING);
            BlockPos tabletPos = tabletBlockEntity.getBlockPos();
            if (tabletPos.getZ() == pos.getZ() && tabletPos.getX() == pos.getX()) {
                return direction == (tabletPos.getY() > pos.getY() ? Direction.DOWN : Direction.UP);

            }
            if (tabletPos.getZ() == pos.getZ()) {
                return direction == (tabletPos.getX() > pos.getX() ? Direction.WEST : Direction.EAST);
            } else if (tabletPos.getX() == pos.getX()) {
                return direction == (tabletPos.getZ() > pos.getZ() ? Direction.NORTH : Direction.SOUTH);
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));

        getTabletPositions().addAll(nearbyTablets.stream().map(BlockEntity::getBlockPos).collect(Collectors.toList()));
        getTablets().addAll(nearbyTablets);

    }

    default void saveTwistedTabletData(CompoundTag compound) {
        CompoundTag twistedTabletTag = new CompoundTag();

        List<BlockPos> tabletPositions = getTabletPositions();
        if (!tabletPositions.isEmpty()) {
            twistedTabletTag.putInt("amount", tabletPositions.size());
            for (int i = 0; i < tabletPositions.size(); i++) {
                BlockHelper.saveBlockPos(twistedTabletTag, tabletPositions.get(i), "tablet_" + i);
            }
            compound.put("twistedTabletData", twistedTabletTag);
        }
    }

    default void loadTwistedTabletData(Level level, CompoundTag compound) {
        if (compound.contains("twistedTabletData")) {
            CompoundTag twistedTabletTag = compound.getCompound("twistedTabletData");
            int amount = twistedTabletTag.getInt("amount");
            for (int i = 0; i < amount; i++) {
                BlockPos pos = BlockHelper.loadBlockPos(twistedTabletTag, "tablet_" + i);
                if (level != null && level.getBlockEntity(pos) instanceof TwistedTabletBlockEntity tabletBlockEntity) {
                    getTabletPositions().add(pos);
                    getTablets().add(tabletBlockEntity);
                }
            }
        }
    }
}
