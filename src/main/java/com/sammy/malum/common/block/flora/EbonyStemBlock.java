
package com.sammy.malum.common.block.flora;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class EbonyStemBlock extends Block {

    // Branch toggles
    public static final BooleanProperty BRANCH_NORTH =
            BooleanProperty.create("branch_north");
    public static final BooleanProperty BRANCH_EAST =
            BooleanProperty.create("branch_east");
    public static final BooleanProperty BRANCH_SOUTH =
            BooleanProperty.create("branch_south");
    public static final BooleanProperty BRANCH_WEST =
            BooleanProperty.create("branch_west");

    // Leaf toggles
    public static final BooleanProperty NORTH =
            BooleanProperty.create("north");
    public static final BooleanProperty EAST =
            BooleanProperty.create("east");
    public static final BooleanProperty SOUTH =
            BooleanProperty.create("south");
    public static final BooleanProperty WEST =
            BooleanProperty.create("west");

    public EbonyStemBlock(Properties properties) {
        super(properties);

        registerDefaultState(
                stateDefinition.any()
                        .setValue(BRANCH_NORTH, true)
                        .setValue(BRANCH_EAST, true)
                        .setValue(BRANCH_SOUTH, true)
                        .setValue(BRANCH_WEST, true)
                        .setValue(NORTH, false)
                        .setValue(EAST, false)
                        .setValue(SOUTH, false)
                        .setValue(WEST, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(
                BRANCH_NORTH,
                BRANCH_EAST,
                BRANCH_SOUTH,
                BRANCH_WEST,
                NORTH,
                EAST,
                SOUTH,
                WEST
        );
    }
}
