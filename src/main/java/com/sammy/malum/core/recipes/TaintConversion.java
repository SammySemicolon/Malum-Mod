package com.sammy.malum.core.recipes;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;

public class TaintConversion
{
    public static final ArrayList<TaintConversion> conversions = new ArrayList<>();
    public final Block inputBlock;
    public final Block outputBlock;
    
    public TaintConversion(RegistryObject<Block> inputBlock, RegistryObject<Block> outputBlock)
    {
        this.inputBlock = inputBlock.get();
        this.outputBlock = outputBlock.get();
        conversions.add(this);
    }
    
    public static void init()
    {
        new TaintConversion(MalumBlocks.SUN_KISSED_GRASS_BLOCK, MalumBlocks.TAINTED_GRASS_BLOCK);
        new TaintConversion(MalumBlocks.SUN_KISSED_LEAVES, MalumBlocks.TAINTED_LEAVES);
        new TaintConversion(MalumBlocks.SUN_KISSED_LOG, MalumBlocks.TAINTED_LOG);
        new TaintConversion(MalumBlocks.STRIPPED_SUN_KISSED_LOG, MalumBlocks.STRIPPED_TAINTED_LOG);
        new TaintConversion(MalumBlocks.SUN_KISSED_WOOD, MalumBlocks.TAINTED_WOOD);
        new TaintConversion(MalumBlocks.STRIPPED_SUN_KISSED_WOOD, MalumBlocks.STRIPPED_TAINTED_WOOD);
        new TaintConversion(MalumBlocks.SUN_KISSED_PLANKS, MalumBlocks.TAINTED_PLANKS);
        new TaintConversion(MalumBlocks.SUN_KISSED_PLANKS_SLAB, MalumBlocks.TAINTED_PLANKS_SLAB);
        new TaintConversion(MalumBlocks.SUN_KISSED_PLANKS_STAIRS, MalumBlocks.TAINTED_PLANKS_STAIRS);
        new TaintConversion(MalumBlocks.SUN_KISSED_DOOR, MalumBlocks.TAINTED_DOOR);
        new TaintConversion(MalumBlocks.SUN_KISSED_TRAPDOOR, MalumBlocks.TAINTED_TRAPDOOR);
        new TaintConversion(MalumBlocks.SOLID_SUN_KISSED_TRAPDOOR, MalumBlocks.SOLID_TAINTED_TRAPDOOR);
        new TaintConversion(MalumBlocks.SUN_KISSED_PLANKS_BUTTON, MalumBlocks.TAINTED_PLANKS_BUTTON);
        new TaintConversion(MalumBlocks.SUN_KISSED_PLANKS_FENCE, MalumBlocks.TAINTED_PLANKS_FENCE);
        new TaintConversion(MalumBlocks.SUN_KISSED_PLANKS_FENCE_GATE, MalumBlocks.TAINTED_PLANKS_FENCE_GATE);
        new TaintConversion(MalumBlocks.SHORT_SUN_KISSED_GRASS, MalumBlocks.SHORT_TAINTED_GRASS);
        new TaintConversion(MalumBlocks.SUN_KISSED_GRASS, MalumBlocks.TAINTED_GRASS);
        new TaintConversion(MalumBlocks.TALL_SUN_KISSED_GRASS, MalumBlocks.TALL_TAINTED_GRASS);
        new TaintConversion(MalumBlocks.SUN_KISSED_SAPLING, MalumBlocks.TAINTED_SAPLING);
    }
    
    public static TaintConversion getConversion(Block block)
    {
        for (TaintConversion conversion : conversions)
        {
            if (conversion.inputBlock.equals(block))
            {
                return conversion;
            }
        }
        return null;
    }
    
    public static void spread(World worldIn, BlockPos pos, TaintConversion conversion)
    {
        BlockState state = worldIn.getBlockState(pos);
        if (conversion.outputBlock instanceof DoublePlantBlock)
        {
            DoublePlantBlock plantBlock = (DoublePlantBlock) conversion.outputBlock;
            BlockPos bottomPos = state.get(DoublePlantBlock.HALF).equals(DoubleBlockHalf.LOWER) ? pos : pos.down();
            plantBlock.placeAt(worldIn, bottomPos, 16);
            worldIn.notifyBlockUpdate(bottomPos, worldIn.getBlockState(bottomPos), worldIn.getBlockState(bottomPos), 3);
            worldIn.notifyBlockUpdate(bottomPos.up(), worldIn.getBlockState(bottomPos.up()), worldIn.getBlockState(bottomPos.up()), 3);
        }
        else
        {
            MalumHelper.setBlockStateWithExistingProperties(worldIn, pos, conversion.outputBlock.getDefaultState(), 3);
        }
        issueSpread(worldIn, pos);
    }
    
    public static void issueSpread(World worldIn, BlockPos pos)
    {
        worldIn.getPendingBlockTicks().scheduleTick(pos, worldIn.getBlockState(pos).getBlock(), 30 + MalumMod.RANDOM.nextInt(60));
    }
}