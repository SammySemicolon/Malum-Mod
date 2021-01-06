package com.sammy.malum.core.data;


import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.blocks.MalumLeavesBlock;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.systems.multiblock.BoundingBlock;
import com.sammy.malum.core.systems.multiblock.IMultiblock;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static com.sammy.malum.MalumHelper.prefix;
import static com.sammy.malum.core.init.blocks.MalumBlocks.BLAZE_QUARTZ_ORE;
import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;
import static net.minecraft.state.properties.DoubleBlockHalf.LOWER;
import static net.minecraft.state.properties.DoubleBlockHalf.UPPER;

public class MalumBlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider
{
    public MalumBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
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
        blocks.remove(BLAZE_QUARTZ_ORE);
        blocks.remove(MalumBlocks.ITEM_STAND);
        blocks.remove(MalumBlocks.ARCANE_WORKBENCH);
        
        MalumHelper.takeAll(blocks, b -> b.get() instanceof IMultiblock || b.get() instanceof BoundingBlock);
    
        MalumHelper.takeAll(blocks, b -> b.get().getRegistryName().getPath().startsWith("cut_") && b.get().getRegistryName().getPath().endsWith("_planks")).forEach(this::cutPlanksBlock);
        MalumHelper.takeAll(blocks, b -> b.get().getRegistryName().getPath().startsWith("horizontal_flared_")).forEach(this::horizontalFlaredBlock);
        MalumHelper.takeAll(blocks, b -> b.get().getRegistryName().getPath().startsWith("cut_")).forEach(this::cutBlock);
        MalumHelper.takeAll(blocks, b -> b.get().getTranslationKey().endsWith("_cap")).forEach(this::pillarCapBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof FarmlandBlock).forEach(this::farmlandBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(this::grassBlock);
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
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(this::lanternBlock);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof MalumLeavesBlock).forEach(this::malumLeavesBlock);
        
        Collection<RegistryObject<Block>> slabs = MalumHelper.takeAll(blocks, b -> b.get() instanceof SlabBlock);
        blocks.forEach(this::basicBlock);
        slabs.forEach(this::slabBlock);
        
    }
    
    public void basicBlock(RegistryObject<Block> blockRegistryObject)
    {
        simpleBlock(blockRegistryObject.get());
    }
    
    public void farmlandBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile cross = models().withExistingParent(name, new ResourceLocation("block/farmland")).texture("top", prefix("block/" + name));
        
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(cross).build());
    }
    public void cutBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = "smooth_" + name.substring(4);
        simpleBlock(blockRegistryObject.get(), models().cubeBottomTop(name, prefix("block/" + name), prefix("block/" + baseName), prefix("block/" + baseName)));
    }
    public void horizontalFlaredBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = "smooth_" + name.substring(18);
        axisBlock((RotatedPillarBlock) blockRegistryObject.get(), prefix("block/" + name), prefix("block/" + baseName));
    }
    
    public void cutPlanksBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0,name.length() - "_planks".length()).substring(4) + "_panel";
        simpleBlock(blockRegistryObject.get(), models().cubeBottomTop(name, prefix("block/" + name), prefix("block/" + name), prefix("block/" + baseName)));
    }
    public void grassBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile file = models().cubeBottomTop(name, prefix("block/" + name + "_side"), new ResourceLocation("block/dirt"), prefix("block/" + name + "_top"));
    
        getVariantBuilder(blockRegistryObject.get()).partialState().modelForState()
                .modelFile(file)
                .nextModel().modelFile(file).rotationY(90)
                .nextModel().modelFile(file).rotationY(180)
                .nextModel().modelFile(file).rotationY(270)
                .addModel();
    }
    
    public void malumLeavesBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile leaves = models().withExistingParent(name, new ResourceLocation("block/leaves")).texture("all", prefix("block/" + name));
        simpleBlock(blockRegistryObject.get(), leaves);
    }
    
    public void pillarCapBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 11);
        String pillarName = name.substring(0, name.length() - 4) + "_top";
        directionalBlock(blockRegistryObject.get(), models().cubeBottomTop(name, prefix("block/" + name), prefix("block/smooth_" + baseName), prefix("block/" + pillarName)));
    }
    
    public void trapdoorBlock(RegistryObject<Block> blockRegistryObject)
    {
        trapdoorBlock((TrapDoorBlock) blockRegistryObject.get(), blockTexture(blockRegistryObject.get()), true);
    }
    
    public void doorBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        doorBlock((DoorBlock) blockRegistryObject.get(), prefix("block/" + name + "_bottom"), prefix("block/" + name + "_top"));
    }
    
    public void fenceGateBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 11);
        fenceGateBlock((FenceGateBlock) blockRegistryObject.get(), prefix("block/" + baseName));
    }
    
    public void fenceBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 6);
        fenceBlock((FenceBlock) blockRegistryObject.get(), prefix("block/" + baseName));
    }
    
    public void wallBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        wallBlock((WallBlock) blockRegistryObject.get(), prefix("block/" + baseName));
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
        ModelFile pressurePlateDown = models().withExistingParent(name + "_down", new ResourceLocation("block/pressure_plate_down")).texture("texture", prefix("block/" + baseName));
        ModelFile pressurePlateUp = models().withExistingParent(name + "_up", new ResourceLocation("block/pressure_plate_up")).texture("texture", prefix("block/" + baseName));
    
        getVariantBuilder(blockRegistryObject.get()).partialState().with(PressurePlateBlock.POWERED, true).modelForState().modelFile(pressurePlateDown).addModel().partialState().with(PressurePlateBlock.POWERED, false).modelForState().modelFile(pressurePlateUp).addModel();
    }
    
    public void lanternBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile lantern = models().withExistingParent(name, new ResourceLocation("block/template_lantern")).texture("lantern", prefix("block/" + name));
        ModelFile hangingLantern = models().withExistingParent(name + "_hanging", new ResourceLocation("block/template_hanging_lantern")).texture("lantern", prefix("block/" + name));
        
        getVariantBuilder(blockRegistryObject.get()).partialState().with(LanternBlock.HANGING, true).modelForState().modelFile(hangingLantern).addModel().partialState().with(LanternBlock.HANGING, false).modelForState().modelFile(lantern).addModel();
    }
    
    public void buttonBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        ModelFile buttom = models().withExistingParent(name, new ResourceLocation("block/button")).texture("texture", prefix("block/" + baseName));
        ModelFile buttonPressed = models().withExistingParent(name + "_pressed", new ResourceLocation("block/button_pressed")).texture("texture", prefix("block/" + baseName));
        Function<BlockState, ModelFile> modelFunc = $ -> buttom;
        Function<BlockState, ModelFile> pressedModelFunc = $ -> buttonPressed;
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(s.get(BlockStateProperties.POWERED) ? pressedModelFunc.apply(s) : modelFunc.apply(s)).uvLock(s.get(BlockStateProperties.FACE).equals(AttachFace.WALL)).rotationX(s.get(BlockStateProperties.FACE).ordinal() * 90).rotationY((((int) s.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalAngle() + 180) + (s.get(BlockStateProperties.FACE) == AttachFace.CEILING ? 180 : 0)) % 360).build());
        models().withExistingParent(name + "_inventory", new ResourceLocation("block/button_inventory")).texture("texture", prefix("block/" + baseName));
    
    }
    
    public void tallPlantBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile bottom = models().withExistingParent(name + "_bottom", new ResourceLocation("block/cross")).texture("cross", prefix("block/" + name + "_bottom"));
        ModelFile top = models().withExistingParent(name + "_top", new ResourceLocation("block/cross")).texture("cross", prefix("block/" + name + "_top"));
        
        getVariantBuilder(blockRegistryObject.get()).partialState().with(DoublePlantBlock.HALF, LOWER).modelForState().modelFile(bottom).addModel().partialState().with(DoublePlantBlock.HALF, UPPER).modelForState().modelFile(top).addModel();
    }
    
    public void plantBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile cross = models().withExistingParent(name, new ResourceLocation("block/cross")).texture("cross", prefix("block/" + name));
        
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
        if (blockRegistryObject.get().getTranslationKey().endsWith("_wood"))
        {
            woodBlock(blockRegistryObject);
            return;
        }
        logBlock((RotatedPillarBlock) blockRegistryObject.get());
    }
    
    public void woodBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 4) + "log";
        axisBlock((RotatedPillarBlock) blockRegistryObject.get(), prefix("block/" + baseName), prefix("block/" + baseName));
    }
}