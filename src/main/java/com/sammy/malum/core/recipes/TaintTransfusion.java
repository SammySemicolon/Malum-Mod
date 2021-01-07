package com.sammy.malum.core.recipes;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.*;
import net.minecraft.item.Items;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;

public class TaintTransfusion
{
    public static final ArrayList<TaintTransfusion> transfusions = new ArrayList<>();
    public final Block inputBlock;
    public final Block outputBlock;
    
    public TaintTransfusion(RegistryObject<Block> inputBlock, RegistryObject<Block> outputBlock)
    {
        this(inputBlock.get(), outputBlock.get());
    }
    public TaintTransfusion(Block inputBlock, Block outputBlock)
    {
        this.inputBlock = inputBlock;
        this.outputBlock = outputBlock;
        transfusions.add(this);
    }
    public static void init()
    {
        new TaintTransfusion(Blocks.STONE, MalumBlocks.TAINTED_ROCK.get());
        new TaintTransfusion(Blocks.DANDELION, MalumBlocks.MARIGOLD.get());
        new TaintTransfusion(Blocks.SAND, Blocks.GRAVEL);
        new TaintTransfusion(Blocks.MELON, Blocks.CAKE);
        new TaintTransfusion(Blocks.GRANITE, Blocks.ANDESITE);
        new TaintTransfusion(Blocks.ANDESITE, Blocks.DIORITE);
        new TaintTransfusion(Blocks.DIORITE, Blocks.GRANITE);
        new TaintTransfusion(Blocks.COBBLESTONE, Blocks.NETHERRACK);
        new TaintTransfusion(Blocks.MAGMA_BLOCK, Blocks.OBSIDIAN);
        new TaintTransfusion(Blocks.COAL_BLOCK, Blocks.LAPIS_BLOCK);
    }
    
    public static TaintTransfusion getTransfusion(Block block)
    {
        for (TaintTransfusion transfusion : transfusions)
        {
            if (transfusion.inputBlock.equals(block))
            {
                return transfusion;
            }
        }
        return null;
    }
    public static void spread(World worldIn, BlockPos pos, TaintTransfusion conversion)
    {
        BlockState state = worldIn.getBlockState(pos);
        Block block = conversion.outputBlock;
        if (conversion.outputBlock instanceof DoublePlantBlock)
        {
            DoublePlantBlock plantBlock = (DoublePlantBlock) conversion.outputBlock;
            BlockPos bottomPos = state.get(DoublePlantBlock.HALF).equals(DoubleBlockHalf.LOWER) ? pos : pos.down();
            plantBlock.placeAt(worldIn, bottomPos, 16);
            MalumHelper.updateState(worldIn.getBlockState(pos.up()), worldIn, pos.up());
            MalumHelper.updateState(worldIn.getBlockState(pos), worldIn, pos);
            issueSpread(worldIn, pos);
            issueSpread(worldIn, pos.up());
            return;
        }
        if (conversion.outputBlock instanceof DoorBlock)
        {
            BlockPos bottomPos = state.get(DoorBlock.HALF).equals(DoubleBlockHalf.LOWER) ? pos : pos.down();
            MalumHelper.setBlockStateWithExistingProperties(worldIn, bottomPos, block.getDefaultState(), 16,false);
            MalumHelper.setBlockStateWithExistingProperties(worldIn, bottomPos.up(), block.getDefaultState(), 16,false);
            MalumHelper.updateState(worldIn.getBlockState(pos.up()), worldIn, pos.up());
            MalumHelper.updateState(worldIn.getBlockState(pos), worldIn, pos);
            issueSpread(worldIn, pos);
            issueSpread(worldIn, pos.up());
            return;
        }
        MalumHelper.setBlockStateWithExistingProperties(worldIn, pos, block.getDefaultState(), 3, true);
        issueSpread(worldIn, pos);
    }
    
    public static void issueSpread(World worldIn, BlockPos pos)
    {
        worldIn.getPendingBlockTicks().scheduleTick(pos, worldIn.getBlockState(pos).getBlock(), 30 + MalumMod.RANDOM.nextInt(60));
    }
}