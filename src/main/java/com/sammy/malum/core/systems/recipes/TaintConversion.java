package com.sammy.malum.core.systems.recipes;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.Block;
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
        new TaintConversion(MalumBlocks.SUN_KISSED_PLANKS, MalumBlocks.TAINTED_PLANKS);
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
        MalumHelper.setBlockStateWithExistingProperties(worldIn, pos, conversion.outputBlock.getDefaultState());
        worldIn.getPendingBlockTicks().scheduleTick(pos, worldIn.getBlockState(pos).getBlock(), 10 + MalumMod.RANDOM.nextInt(20));
    }
}