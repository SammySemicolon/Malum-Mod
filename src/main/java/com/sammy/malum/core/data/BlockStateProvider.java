package com.sammy.malum.core.data;


import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.blocks.MalumLeavesBlock;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;

import java.util.*;
import java.util.function.Function;

import static com.sammy.malum.MalumHelper.prefix;
import static com.sammy.malum.core.init.MalumBlocks.*;
import static net.minecraft.state.properties.DoubleBlockHalf.LOWER;
import static net.minecraft.state.properties.DoubleBlockHalf.UPPER;

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
        return "Malum BlockStates";
    }
    
    @Override
    protected void registerStatesAndModels()
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        
        MalumHelper.takeAll(blocks, b -> b.get() instanceof StairsBlock).forEach(this::stairsBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof RotatedPillarBlock).forEach(this::logBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof WallBlock).forEach(this::wallBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof FenceBlock).forEach(this::fenceBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof FenceGateBlock).forEach(this::fenceGateBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(this::doorBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(this::trapdoorBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof PressurePlateBlock).forEach(this::pressurePlateBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof AbstractButtonBlock).forEach(this::buttonBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(this::tallPlantBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(this::plantBlock);
        
        Collection<RegistryObject<Block>> slabs = MalumHelper.takeAll(blocks, b -> b.get() instanceof SlabBlock);
        blocks.forEach(this::basicBlock);
        slabs.forEach(this::slabBlock);
        
    }
    
    public void basicBlock(RegistryObject<Block> blockRegistryObject)
    {
        if (blockRegistryObject.get() instanceof MalumLeavesBlock)
        {
            String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
            ModelFile leaves = models().withExistingParent(name,new ResourceLocation("block/leaves")).texture("all", prefix("block/" + name));
            
            getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(leaves).build());
            return;
        }
        if (blockRegistryObject.get().getTranslationKey().endsWith("_wood"))
        {
            String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
            String baseName = name.substring(0, name.length() - 4) + "log";
            simpleBlock(blockRegistryObject.get(), models().cubeAll(blockRegistryObject.get().getRegistryName().getPath(), prefix("block/"+ baseName)));
            return;
        }
        simpleBlock(blockRegistryObject.get());
    }
    public void trapdoorBlock(RegistryObject<Block> blockRegistryObject)
    {
        trapdoorBlock((TrapDoorBlock)blockRegistryObject.get(), blockTexture(blockRegistryObject.get()), true);
    }
    public void doorBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        doorBlock((DoorBlock) blockRegistryObject.get(),prefix("block/"+ name + "_bottom"),prefix("block/"+ name + "_top"));
    }
    public void fenceGateBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 11);
        fenceGateBlock((FenceGateBlock) blockRegistryObject.get(), prefix("block/"+ baseName));
    }
    public void fenceBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 6);
        fenceBlock((FenceBlock) blockRegistryObject.get(), prefix("block/"+ baseName));
    }
    public void wallBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        wallBlock((WallBlock) blockRegistryObject.get(), prefix("block/"+ baseName));
    }
    public void stairsBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        stairsBlock((StairsBlock) blockRegistryObject.get(), prefix("block/" + baseName));
    }
    
    public void pressurePlateBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 15);
        ModelFile pressurePlateDown = models().withExistingParent(name+"_down",new ResourceLocation("block/pressure_plate_down")).texture("texture", prefix("block/" + baseName));
        ModelFile pressurePlateUp = models().withExistingParent(name+"_up",new ResourceLocation("block/pressure_plate_up")).texture("texture", prefix("block/" + baseName));
    
        getVariantBuilder(blockRegistryObject.get())
                .partialState().with(PressurePlateBlock.POWERED, true)
                .modelForState().modelFile(pressurePlateDown).addModel()
                .partialState().with(PressurePlateBlock.POWERED, false)
                .modelForState().modelFile(pressurePlateUp).addModel();
    }
    
    public void buttonBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        ModelFile buttom = models().withExistingParent(name,new ResourceLocation("block/button")).texture("texture", prefix("block/" + baseName));
        ModelFile buttonPressed = models().withExistingParent(name+"_pressed",new ResourceLocation("block/button_pressed")).texture("texture", prefix("block/" + baseName));
        Function<BlockState, ModelFile> modelFunc = $ -> buttom;
        Function<BlockState, ModelFile> pressedModelFunc = $ -> buttonPressed;
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder()
                .modelFile(s.get(BlockStateProperties.POWERED) ? pressedModelFunc.apply(s) : modelFunc.apply(s))
                .uvLock(s.get(BlockStateProperties.FACE).equals(AttachFace.WALL))
                .rotationX(s.get(BlockStateProperties.FACE).ordinal() * 90)
                .rotationY((((int) s.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalAngle() + 180) + (s.get(BlockStateProperties.FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                .build());
        models().withExistingParent(name+"_inventory",new ResourceLocation("block/button_inventory")).texture("texture", prefix("block/" + baseName));
    
    }
    
    public void tallPlantBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile bottom = models().withExistingParent(name+"_bottom",new ResourceLocation("block/cross")).texture("cross", prefix("block/" + name + "_bottom"));
        ModelFile top = models().withExistingParent(name+"_top",new ResourceLocation("block/cross")).texture("cross", prefix("block/" + name + "_top"));
        
        getVariantBuilder(blockRegistryObject.get())
                .partialState().with(DoublePlantBlock.HALF, LOWER)
                .modelForState().modelFile(bottom).addModel()
                .partialState().with(DoublePlantBlock.HALF, UPPER)
                .modelForState().modelFile(top).addModel();
    }
    public void plantBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile cross = models().withExistingParent(name,new ResourceLocation("block/cross")).texture("cross", prefix("block/" + name));
        
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(cross).build());
    }
    public void slabBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        slabBlock((SlabBlock) blockRegistryObject.get(), prefix(baseName), prefix("block/" + baseName));
    }
    
    public void logBlock(RegistryObject<Block> blockRegistryObject)
    {
        logBlock((RotatedPillarBlock) blockRegistryObject.get());
    }
    
}