package com.sammy.malum.blocks.machines.spiritsmeltery;

import com.sammy.malum.blocks.utility.multiblock.MultiblockBlock;
import com.sammy.malum.blocks.utility.multiblock.MultiblockStructure;
import net.hypherionmc.hypcore.api.ColoredLightBlock;
import net.hypherionmc.hypcore.api.ColoredLightEvent;
import net.hypherionmc.hypcore.api.Light;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

@ColoredLightBlock
public class SpiritSmelteryBlock extends MultiblockBlock
{
    //region structure
    public static final MultiblockStructure structure = new MultiblockStructure(
            new BlockPos(0,0,1),
            new BlockPos(1,0,1),
            new BlockPos(1,0,0),
            new BlockPos(1,0,-1),
            new BlockPos(0,0,-1),
            new BlockPos(-1,0,-1),
            new BlockPos(-1,0,0),
            new BlockPos(-1,0,1),
        
            new BlockPos(0,1,0),
            
            new BlockPos(0,1,1),
            new BlockPos(1,1,1),
            new BlockPos(1,1,0),
            new BlockPos(1,1,-1),
            new BlockPos(0,1,-1),
            new BlockPos(-1,1,-1),
            new BlockPos(-1,1,0),
            new BlockPos(-1,1,1),
        
            new BlockPos(0,2,0),
            
            new BlockPos(0,2,1),
            new BlockPos(1,2,1),
            new BlockPos(1,2,0),
            new BlockPos(1,2,-1),
            new BlockPos(0,2,-1),
            new BlockPos(-1,2,-1),
            new BlockPos(-1,2,0),
            new BlockPos(-1,2,1)
    );
    //endregion
    
    public SpiritSmelteryBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new SpiritSmelteryTileEntity();
    }
    @ColoredLightEvent
    public Light produceColoredLight(BlockPos pos, BlockState state) {
        return Light.builder().pos(pos).color(0.95f,0.5f,0.9f, 1.0f) .radius(7).build();
    }
}