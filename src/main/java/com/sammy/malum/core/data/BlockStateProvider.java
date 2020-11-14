package com.sammy.malum.core.data;


import com.sammy.malum.MalumMod;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;

import static com.sammy.malum.MalumHelper.prefix;
import static com.sammy.malum.core.init.MalumBlocks.*;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider
{
    public BlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, MalumMod.MODID, exFileHelper);
    }
    
    @Nonnull
    @Override
    public String getName()
    {
        return "Malum Blockstates";
    }
    
    @Override
    protected void registerStatesAndModels()
    {
        basicBlock(SPIRIT_STONE);
        stairsBlock(SPIRIT_STONE_STAIRS);
        slabBlock(SPIRIT_STONE_SLAB);
    }
    
    public void basicBlock(RegistryObject<Block> blockRegistryObject)
    {
        simpleBlock(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    
    public void stairsBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        stairsBlock((StairsBlock) blockRegistryObject.get(), prefix("block/" + baseName));
    }
    
    public void slabBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        slabBlock((SlabBlock) blockRegistryObject.get(), prefix(baseName), prefix("block/" + baseName));
    }
}