package com.sammy.malum.common.item;

import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class CalcifiedBlightItem extends ItemNameBlockItem {
    public CalcifiedBlightItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void registerBlocks(Map<Block, Item> pBlockToItemMap, Item pItem) {
        super.registerBlocks(pBlockToItemMap, pItem);
        pBlockToItemMap.put(BlockRegistry.TALL_CALCIFIED_BLIGHT.get(), pItem);
    }

    @Override
    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
        super.removeFromBlockToItemMap(blockToItemMap, itemIn);
        blockToItemMap.remove(BlockRegistry.TALL_CALCIFIED_BLIGHT.get());
    }
}
