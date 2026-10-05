package com.sammy.malum.common.block.building;

import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class StoneBookshelfBlock extends Block {

    public StoneBookshelfBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader world, BlockPos pos) {
        return 1;
    }
}
