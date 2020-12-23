package com.sammy.malum.core.recipes;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;

public class TaintTransfusion
{
    public static final ArrayList<TaintTransfusion> conversions = new ArrayList<>();
    public static final ArrayList<TaintTransfusion> advancedConversions = new ArrayList<>();
    public final Block input;
    public final Block output;
    public final Item inputItem;
    public final Item outputItem;
    public final boolean isBlocky;
    
    public TaintTransfusion(RegistryObject<Block> inputBlock, RegistryObject<Block> outputBlock)
    {
        this(inputBlock.get(), outputBlock.get());
    }
    public TaintTransfusion(Block inputBlock, Block outputBlock)
    {
        this.input = inputBlock;
        this.output = outputBlock;
        this.inputItem = inputBlock.asItem();
        this.outputItem = outputBlock.asItem();
        isBlocky = true;
        conversions.add(this);
    }
    public TaintTransfusion(Item inputItem, Item outputItem)
    {
        this.input = null;
        this.output = null;
        this.inputItem = inputItem;
        this.outputItem = outputItem;
        isBlocky = false;
        advancedConversions.add(this);
    }
    public static void init()
    {
        new TaintTransfusion(Blocks.STONE, MalumBlocks.TAINTED_ROCK.get());
        new TaintTransfusion(Blocks.STONE_SLAB, MalumBlocks.TAINTED_ROCK_SLAB.get());
        new TaintTransfusion(Blocks.STONE_STAIRS, MalumBlocks.TAINTED_ROCK_STAIRS.get());
        new TaintTransfusion(Blocks.SMOOTH_STONE, MalumBlocks.SMOOTH_TAINTED_ROCK.get());
        new TaintTransfusion(Blocks.SMOOTH_STONE_SLAB, MalumBlocks.SMOOTH_TAINTED_ROCK_SLAB.get());
        
        new TaintTransfusion(Blocks.IRON_BLOCK, MalumBlocks.RUIN_PLATING_BLOCK.get());
        new TaintTransfusion(Items.IRON_INGOT, MalumItems.RUIN_PLATING.get());
        new TaintTransfusion(Items.IRON_NUGGET, MalumItems.RUIN_SCRAPS.get());
    
        new TaintTransfusion(Blocks.GLOWSTONE, MalumBlocks.FLARED_GLOWSTONE_BlOCK.get());
        new TaintTransfusion(Items.GLOWSTONE_DUST, MalumItems.DARK_FLARES.get());
    
        new TaintTransfusion(Items.FLINT, Items.GUNPOWDER);
        new TaintTransfusion(Items.CLAY_BALL, Items.COAL);
        new TaintTransfusion(Items.WHEAT, Items.NETHER_WART);
        new TaintTransfusion(Items.HAY_BLOCK, Items.NETHER_WART_BLOCK);
        
        
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
        new TaintTransfusion(Blocks.GRAVEL, Blocks.SPONGE);
        new TaintTransfusion(Blocks.BASALT, Blocks.BONE_BLOCK);
        new TaintTransfusion(Blocks.HONEY_BLOCK, Blocks.SLIME_BLOCK);
    }
    
    public static TaintTransfusion getTransfusion(Block block)
    {
        for (TaintTransfusion conversion : conversions)
        {
            if (conversion.isBlocky)
            {
                if (conversion.input.equals(block))
                {
                    return conversion;
                }
            }
        }
        return null;
    }
    public static TaintTransfusion getTransfusion(Item item)
    {
        for (TaintTransfusion conversion : conversions)
        {
            if (conversion.inputItem.equals(item))
            {
                return conversion;
            }
        }
        for (TaintTransfusion conversion : advancedConversions)
        {
            if (conversion.inputItem.equals(item))
            {
                return conversion;
            }
        }
        return null;
    }
    
    public static void spread(World worldIn, BlockPos pos, TaintTransfusion conversion)
    {
        BlockState state = worldIn.getBlockState(pos);
        Block block = conversion.output;
        if (conversion.output instanceof DoublePlantBlock)
        {
            DoublePlantBlock plantBlock = (DoublePlantBlock) conversion.output;
            BlockPos bottomPos = state.get(DoublePlantBlock.HALF).equals(DoubleBlockHalf.LOWER) ? pos : pos.down();
            plantBlock.placeAt(worldIn, bottomPos, 16);
            worldIn.notifyBlockUpdate(bottomPos, worldIn.getBlockState(bottomPos) , worldIn.getBlockState(bottomPos), 3);
            worldIn.notifyBlockUpdate(bottomPos.up(), worldIn.getBlockState(bottomPos.up()), worldIn.getBlockState(bottomPos.up()), 3);
            issueSpread(worldIn, pos);
            issueSpread(worldIn, pos.up());
            return;
        }
        if (conversion.output instanceof DoorBlock)
        {
            BlockPos bottomPos = state.get(DoorBlock.HALF).equals(DoubleBlockHalf.LOWER) ? pos : pos.down();
            MalumHelper.setBlockStateWithExistingProperties(worldIn, bottomPos, block.getDefaultState(), 16,false);
            MalumHelper.setBlockStateWithExistingProperties(worldIn, bottomPos.up(), block.getDefaultState(), 16,false);
            worldIn.notifyBlockUpdate(bottomPos, worldIn.getBlockState(bottomPos), worldIn.getBlockState(bottomPos), 3);
            worldIn.notifyBlockUpdate(bottomPos.up(), worldIn.getBlockState(bottomPos.up()), worldIn.getBlockState(bottomPos.up()), 3);
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