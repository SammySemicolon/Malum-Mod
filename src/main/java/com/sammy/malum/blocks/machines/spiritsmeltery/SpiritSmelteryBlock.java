package com.sammy.malum.blocks.machines.spiritsmeltery;

import com.sammy.malum.blocks.utility.IConfigurableBlock;
import com.sammy.malum.blocks.utility.IConfigurableTileEntity;
import com.sammy.malum.blocks.utility.multiblock.MultiblockBlock;
import com.sammy.malum.blocks.utility.multiblock.MultiblockStructure;
import net.hypherionmc.hypcore.api.ColoredLightBlock;
import net.hypherionmc.hypcore.api.ColoredLightEvent;
import net.hypherionmc.hypcore.api.ColoredLightManager;
import net.hypherionmc.hypcore.api.Light;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

public class SpiritSmelteryBlock extends MultiblockBlock implements IConfigurableBlock
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
        if (ModList.get().isLoaded("hypcore")) {
            ColoredLightManager.registerProvider(this, this::produceColoredLight);
        }
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
    public Light produceColoredLight(BlockPos pos, BlockState state) {
        return Light.builder().pos(pos).color(0.65f,0.2f,0.7f, 1.0f) .radius(8).build();
    }
    
    @Override
    public ActionResultType activateBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, BlockPos boundingBlockSource)
    {
        return activateConfigurableBlock(state, worldIn, pos, player, handIn, hit);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        return activateConfigurableBlock(state, worldIn, pos, player, handIn, hit);
    }
    @Override
    public void configureTileEntity(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, IConfigurableTileEntity tileEntity, int option, boolean isSneaking)
    {
        if (worldIn.getTileEntity(pos) instanceof SpiritSmelteryTileEntity)
        {
            SpiritSmelteryTileEntity smelteryTileEntity = (SpiritSmelteryTileEntity) worldIn.getTileEntity(pos);
            if (smelteryTileEntity.itemCount == 0)
            {
                smelteryTileEntity.vanilla_furnace = !smelteryTileEntity.vanilla_furnace;
                player.world.notifyBlockUpdate(pos, state, state, 3);
            }
        }
    }
    @Override
    public int options()
    {
        return 1;
    }
}