package com.sammy.malum.common.item.misc;

import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BlightedGunkItem extends ItemNameBlockItem {

    public final Block wallPlacement;

    public BlightedGunkItem(Properties pProperties) {
        this(BlockRegistry.BLIGHTED_GROWTH.get(), BlockRegistry.CLINGING_BLIGHT.get(), pProperties);
    }
    public BlightedGunkItem(Block defaultPlacement, Block wallPlacement, Properties pProperties) {
        super(defaultPlacement, pProperties);
        this.wallPlacement = wallPlacement;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext pContext) {
        final Player player = pContext.getPlayer();
        BlockState wallPlacementState = wallPlacement.getStateForPlacement(pContext);
        BlockState defaultState = getBlock().getStateForPlacement(pContext);
        BlockState placed = wallPlacementState;
        if ((player != null && player.isShiftKeyDown()) || placed == null) {
            placed = defaultState;
        }
        LevelReader levelreader = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        return placed != null && canPlace(pContext, placed) && levelreader.isUnobstructed(placed, blockpos, CollisionContext.empty()) ? placed : null;
    }

    @Override
    public void registerBlocks(Map<Block, Item> pBlockToItemMap, Item pItem) {
        super.registerBlocks(pBlockToItemMap, pItem);
        pBlockToItemMap.put(this.wallPlacement, pItem);
    }

    @Override
    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
        super.removeFromBlockToItemMap(blockToItemMap, itemIn);
        blockToItemMap.remove(this.wallPlacement);
    }
}
