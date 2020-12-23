package com.sammy.malum.core.recipes;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.*;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;

public class TaintTransfusion
{
    public static final ArrayList<TaintTransfusion> conversions = new ArrayList<>();
    public final Block inputBlock;
    public final Block outputBlock;
    
    public TaintTransfusion(RegistryObject<Block> inputBlock, RegistryObject<Block> outputBlock)
    {
        this.inputBlock = inputBlock.get();
        this.outputBlock = outputBlock.get();
        conversions.add(this);
    }
    public TaintTransfusion(Block inputBlock, Block outputBlock)
    {
        this.inputBlock = inputBlock;
        this.outputBlock = outputBlock;
        conversions.add(this);
    }
    
    public static void init()
    {
        new TaintTransfusion(Blocks.STONE, MalumBlocks.TAINTED_ROCK.get());
        new TaintTransfusion(Blocks.IRON_BLOCK, MalumBlocks.RUIN_PLATING_BLOCK.get());
        new TaintTransfusion(Blocks.GLOWSTONE, MalumBlocks.FLARED_GLOWSTONE_BlOCK.get());
        new TaintTransfusion(Blocks.DANDELION, MalumBlocks.MARIGOLD.get());
        
        new TaintTransfusion(MalumBlocks.SUN_KISSED_GRASS_BLOCK, MalumBlocks.TAINTED_GRASS_BLOCK);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_LEAVES, MalumBlocks.TAINTED_LEAVES);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_LOG, MalumBlocks.TAINTED_LOG);
        new TaintTransfusion(MalumBlocks.STRIPPED_SUN_KISSED_LOG, MalumBlocks.STRIPPED_TAINTED_LOG);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_WOOD, MalumBlocks.TAINTED_WOOD);
        new TaintTransfusion(MalumBlocks.STRIPPED_SUN_KISSED_WOOD, MalumBlocks.STRIPPED_TAINTED_WOOD);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_PLANKS, MalumBlocks.TAINTED_PLANKS);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_PLANKS_SLAB, MalumBlocks.TAINTED_PLANKS_SLAB);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_PLANKS_STAIRS, MalumBlocks.TAINTED_PLANKS_STAIRS);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_DOOR, MalumBlocks.TAINTED_DOOR);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_TRAPDOOR, MalumBlocks.TAINTED_TRAPDOOR);
        new TaintTransfusion(MalumBlocks.SOLID_SUN_KISSED_TRAPDOOR, MalumBlocks.SOLID_TAINTED_TRAPDOOR);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_PLANKS_BUTTON, MalumBlocks.TAINTED_PLANKS_BUTTON);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_PLANKS_FENCE, MalumBlocks.TAINTED_PLANKS_FENCE);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_PLANKS_FENCE_GATE, MalumBlocks.TAINTED_PLANKS_FENCE_GATE);
        new TaintTransfusion(MalumBlocks.SHORT_SUN_KISSED_GRASS, MalumBlocks.SHORT_TAINTED_GRASS);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_GRASS, MalumBlocks.TAINTED_GRASS);
        new TaintTransfusion(MalumBlocks.TALL_SUN_KISSED_GRASS, MalumBlocks.TALL_TAINTED_GRASS);
        new TaintTransfusion(MalumBlocks.LAVENDER, MalumBlocks.TAINTED_LAVENDER);
        new TaintTransfusion(MalumBlocks.SUN_KISSED_SAPLING, MalumBlocks.TAINTED_SAPLING);
    
        new TaintTransfusion(Blocks.PUMPKIN, Blocks.MELON);
        new TaintTransfusion(Blocks.GRANITE, Blocks.ANDESITE);
        new TaintTransfusion(Blocks.DIORITE, Blocks.ANDESITE);
        new TaintTransfusion(Blocks.COBBLESTONE, Blocks.NETHERRACK);
        new TaintTransfusion(Blocks.MAGMA_BLOCK, Blocks.OBSIDIAN);
        new TaintTransfusion(Blocks.COAL_BLOCK, Blocks.LAPIS_BLOCK);
        new TaintTransfusion(Blocks.NETHER_BRICKS, Blocks.PRISMARINE);
        new TaintTransfusion(Blocks.HAY_BLOCK, Blocks.NETHER_WART_BLOCK);
        new TaintTransfusion(Blocks.GRAVEL, Blocks.SPONGE);
        new TaintTransfusion(Blocks.BASALT, Blocks.BONE_BLOCK);
        new TaintTransfusion(Blocks.HONEY_BLOCK, Blocks.SLIME_BLOCK);
    }
    
    public static TaintTransfusion getConversion(Block block)
    {
        for (TaintTransfusion conversion : conversions)
        {
            if (conversion.inputBlock.equals(block))
            {
                return conversion;
            }
        }
        return null;
    }
    
    public static void spread(World worldIn, BlockPos pos, TaintTransfusion conversion)
    {
        BlockState state = worldIn.getBlockState(pos);
        if (conversion.outputBlock instanceof DoublePlantBlock)
        {
            DoublePlantBlock plantBlock = (DoublePlantBlock) conversion.outputBlock;
            BlockPos bottomPos = state.get(DoublePlantBlock.HALF).equals(DoubleBlockHalf.LOWER) ? pos : pos.down();
            plantBlock.placeAt(worldIn, bottomPos, 16);
            worldIn.notifyBlockUpdate(bottomPos, worldIn.getBlockState(bottomPos) , worldIn.getBlockState(bottomPos), 3);
            worldIn.notifyBlockUpdate(bottomPos.up(), worldIn.getBlockState(bottomPos.up()), worldIn.getBlockState(bottomPos.up()), 3);
            issueSpread(worldIn, pos);
            issueSpread(worldIn, pos.up());
            return;
        }
        if (conversion.outputBlock instanceof DoorBlock)
        {
            BlockPos bottomPos = state.get(DoorBlock.HALF).equals(DoubleBlockHalf.LOWER) ? pos : pos.down();
            MalumHelper.setBlockStateWithExistingProperties(worldIn, bottomPos, conversion.outputBlock.getDefaultState(), 16,false);
            MalumHelper.setBlockStateWithExistingProperties(worldIn, bottomPos.up(), conversion.outputBlock.getDefaultState(), 16,false);
            worldIn.notifyBlockUpdate(bottomPos, worldIn.getBlockState(bottomPos), worldIn.getBlockState(bottomPos), 3);
            worldIn.notifyBlockUpdate(bottomPos.up(), worldIn.getBlockState(bottomPos.up()), worldIn.getBlockState(bottomPos.up()), 3);
            issueSpread(worldIn, pos);
            issueSpread(worldIn, pos.up());
            return;
        }
        MalumHelper.setBlockStateWithExistingProperties(worldIn, pos, conversion.outputBlock.getDefaultState(), 3, true);
        issueSpread(worldIn, pos);
    }
    
    public static void issueSpread(World worldIn, BlockPos pos)
    {
        worldIn.getPendingBlockTicks().scheduleTick(pos, worldIn.getBlockState(pos).getBlock(), 30 + MalumMod.RANDOM.nextInt(60));
    }
}