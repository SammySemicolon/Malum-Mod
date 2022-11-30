package com.sammy.malum.core.data;


import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.MalumLeavesBlock;
import com.sammy.malum.common.block.ether.*;
import com.sammy.malum.common.block.mirror.WallMirrorBlock;
import com.sammy.malum.common.block.storage.ItemPedestalBlock;
import com.sammy.malum.common.block.storage.ItemStandBlock;
import com.sammy.malum.common.block.totem.TotemBaseBlock;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.mehvahdjukaar.supplementaries.common.block.blocks.SconceBlock;
import net.mehvahdjukaar.supplementaries.common.block.blocks.SconceWallBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.helpers.DataHelper;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;

import static com.sammy.malum.MalumMod.malumPath;
import static com.sammy.malum.core.setup.content.block.BlockRegistry.*;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.UPPER;
import static team.lodestar.lodestone.helpers.DataHelper.take;
import static team.lodestar.lodestone.helpers.DataHelper.takeAll;

public class MalumBlockStates extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public MalumBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MalumMod.MALUM, exFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Malum BlockStates";
    }

    @Override
    protected void registerStatesAndModels() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());

        quartzClusterBlock(take(blocks, NATURAL_QUARTZ_CLUSTER));
        blightedSoilBlock(take(blocks, BLIGHTED_SOIL));
        blightedEarthBlock(take(blocks, BLIGHTED_EARTH));
        blightedTumorBlock(take(blocks, BLIGHTED_TUMOR));
        blightedSoulwoodBlock(take(blocks, BLIGHTED_SOULWOOD));
        sconceBlock(take(blocks, BLAZING_SCONCE));
        wallSconceBlock(take(blocks, WALL_BLAZING_SCONCE));

        List<RegistryObject<Block>> customModels = new ArrayList<>(List.of(TWISTED_TABLET, ALTERATION_PLINTH, SOULWOOD_FUSION_PLATE_COMPONENT, SPIRIT_CATALYZER, SPIRIT_CATALYZER_COMPONENT));

        List<RegistryObject<Block>> predefinedModels = new ArrayList<>(List.of(
                SOULWOOD_FUSION_PLATE, SPIRIT_ALTAR, SOUL_VIAL, SPIRIT_JAR, BRILLIANT_OBELISK, BRILLIANT_OBELISK_COMPONENT, RUNEWOOD_OBELISK,
                RUNEWOOD_OBELISK_COMPONENT, SPIRIT_CRUCIBLE, SPIRIT_CRUCIBLE_COMPONENT, SOULWOOD_PLINTH, SOULWOOD_PLINTH_COMPONENT));

        List<RegistryObject<Block>> layeredModels = new ArrayList<>(List.of(BRILLIANT_STONE, BRILLIANT_STONE, BLAZING_QUARTZ_ORE));

        takeAll(blocks, customModels::contains);
        takeAll(blocks, predefinedModels::contains).forEach(this::customBlock);
        takeAll(blocks, layeredModels::contains).forEach(this::layeredBlock);

        DataHelper.takeAll(blocks, b -> ForgeRegistries.BLOCKS.getKey(b.get()).getPath().startsWith("cut_") && ForgeRegistries.BLOCKS.getKey(b.get()).getPath().endsWith("_planks")).forEach(this::cutPlanksBlock);
        DataHelper.takeAll(blocks, b -> ForgeRegistries.BLOCKS.getKey(b.get()).getPath().startsWith("cut_")).forEach(this::cutBlock);
        DataHelper.takeAll(blocks, b -> b.get().getDescriptionId().endsWith("_cap")).forEach(this::pillarCapBlock);

        DataHelper.takeAll(blocks, b -> b.get() instanceof EtherBrazierBlock).forEach(this::brazierBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof EtherWallSconceBlock).forEach(this::wallEtherSconceBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof EtherSconceBlock).forEach(this::etherSconceBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof EtherWallTorchBlock).forEach(this::wallEtherTorchBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof EtherTorchBlock).forEach(this::etherTorchBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof EtherBlock).forEach(this::etherBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof WallMirrorBlock).forEach(this::wallMirrorBlock);

        DataHelper.takeAll(blocks, b -> b.get() instanceof TotemBaseBlock).forEach(this::totemBaseBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof TotemPoleBlock).forEach(this::totemPoleBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof ItemPedestalBlock).forEach(this::itemPedestalBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof ItemStandBlock).forEach(this::itemStandBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof MalumLeavesBlock).forEach(this::malumLeavesBlock);

        DataHelper.takeAll(blocks, b -> b.get() instanceof SconceWallBlock).forEach(this::wallSconceBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof SconceBlock).forEach(this::sconceBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof SignBlock).forEach(this::signBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(this::grassBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof StairBlock).forEach(this::stairsBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof RotatedPillarBlock).forEach(this::logBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof WallBlock).forEach(this::wallBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof FenceBlock).forEach(this::fenceBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof FenceGateBlock).forEach(this::fenceGateBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(this::doorBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(this::trapdoorBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof PressurePlateBlock).forEach(this::pressurePlateBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof ButtonBlock).forEach(this::buttonBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(this::tallPlantBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(this::plantBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(this::lanternBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof WallTorchBlock).forEach(this::wallTorchBlock);
        DataHelper.takeAll(blocks, b -> b.get() instanceof TorchBlock).forEach(this::torchBlock);

        Collection<RegistryObject<Block>> slabs = DataHelper.takeAll(blocks, b -> b.get() instanceof SlabBlock);
        blocks.forEach(this::basicBlock);
        slabs.forEach(this::slabBlock);

    }

    public void basicBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get());
    }

    public void signBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String particleName = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath().replaceFirst("_wall", "").replaceFirst("_sign", "") + "_planks";

        ModelFile sign = models().withExistingParent(name, new ResourceLocation("block/air")).texture("particle", malumPath("block/" + particleName));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(sign).build());
    }

    public void customBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile model = models().getExistingFile(malumPath("block/" + name));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(model).build());
    }

    public void layeredBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String glow = name + "_glow";
        ModelFile glowModel = models().withExistingParent(name, malumPath("block/templates/template_glowing_block")).texture("all", malumPath("block/" + name)).texture("particle", malumPath("block/" + name)).texture("glow", malumPath("block/" + glow));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(glowModel).build());
    }

    public void rotatedBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile file = models().cubeAll(name, malumPath("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).partialState().modelForState()
                .modelFile(file)
                .nextModel().modelFile(file).rotationY(90)
                .nextModel().modelFile(file).rotationY(180)
                .nextModel().modelFile(file).rotationY(270)
                .addModel();
    }

    public void carpetBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile file = models().carpet(name, malumPath("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).partialState().modelForState().modelFile(file).addModel();
    }

    public void quartzClusterBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        directionalBlock(blockRegistryObject.get(), models().cross(name, malumPath("block/"+name)));
    }


    public void blightedSoilBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile soil0 = models().cubeAll(name, malumPath("block/" + name + "_0"));
        ModelFile soil1 = models().cubeAll(name+"_1", malumPath("block/" + name + "_1"));

        getVariantBuilder(blockRegistryObject.get()).partialState().modelForState()
                .modelFile(soil0)
                .nextModel().modelFile(soil0).rotationY(90)
                .nextModel().modelFile(soil0).rotationY(180)
                .nextModel().modelFile(soil0).rotationY(270)

                .nextModel().modelFile(soil1)
                .nextModel().modelFile(soil1).rotationY(90)
                .nextModel().modelFile(soil1).rotationY(180)
                .nextModel().modelFile(soil1).rotationY(270)
                .addModel();
    }

    public void blightedTumorBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        Function<Integer, ModelFile> tumorFunction = (i) -> models().withExistingParent(name + "_" + i, new ResourceLocation("block/cross")).texture("cross", malumPath("block/" + name + "_" + i));

        getVariantBuilder(blockRegistryObject.get()).partialState().modelForState()
                .modelFile(tumorFunction.apply(0))
                .nextModel().modelFile(tumorFunction.apply(1))
                .nextModel().modelFile(tumorFunction.apply(2))
                .nextModel().modelFile(tumorFunction.apply(3))
                .nextModel().modelFile(tumorFunction.apply(4))
                .nextModel().modelFile(tumorFunction.apply(5))
                .nextModel().modelFile(tumorFunction.apply(6))
                .nextModel().modelFile(tumorFunction.apply(7))
                .addModel();
    }

    public void blightedSoulwoodBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(), models().cubeBottomTop("blighted_soulwood", malumPath("block/blighted_soulwood"), malumPath("block/blighted_soil_0"), malumPath("block/soulwood_log_top")));
    }

    public void blightedEarthBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(), models().cubeBottomTop("blighted_earth", malumPath("block/blighted_earth"),  new ResourceLocation("block/dirt"), malumPath("block/blighted_soil_0")));
    }

    public void wallMirrorBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String particleName = "block_of_hallowed_gold";
        ModelFile stand = models().withExistingParent(name, malumPath("block/templates/template_mirror")).texture("mirror", malumPath("block/"+name)).texture("particle", malumPath("block/" + particleName));

        getVariantBuilder(blockRegistryObject.get()).partialState()
                .partialState().with(BlockStateProperties.FACING, Direction.NORTH)
                .modelForState().modelFile(stand).rotationX(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.WEST)
                .modelForState().modelFile(stand).rotationX(90).rotationY(270).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.SOUTH)
                .modelForState().modelFile(stand).rotationX(90).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.EAST)
                .modelForState().modelFile(stand).rotationX(90).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.DOWN)
                .modelForState().modelFile(stand).rotationX(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.UP)
                .modelForState().modelFile(stand).addModel();
    }

    public void sconceBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile sconce = models().withExistingParent(name, malumPath("block/templates/template_sconce")).texture("sconce", malumPath("block/"+name)).texture("fire", malumPath("block/" + name + "_fire")).texture("particle", malumPath("block/"+name));

        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(sconce).build());
    }

    public void wallSconceBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String textureName = name.replaceFirst("wall_", "");
        ModelFile sconce = models().withExistingParent(name, malumPath("block/templates/template_sconce_wall")).texture("sconce", malumPath("block/"+textureName)).texture("fire", malumPath("block/"+textureName+"_fire")).texture("particle", malumPath("block/"+textureName));

        getVariantBuilder(blockRegistryObject.get())
                .partialState().with(WallTorchBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(sconce).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.WEST)
                .modelForState().modelFile(sconce).rotationY(270).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(sconce).rotationY(180).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.EAST)
                .modelForState().modelFile(sconce).rotationY(90).addModel();
    }
    public void etherBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile empty = models().withExistingParent(name, new ResourceLocation("block/air")).texture("particle", malumPath("item/ether"));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(empty).build());
    }

    public void etherSconceBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile sconce = models().withExistingParent(name, malumPath("block/templates/template_ether_sconce")).texture("sconce", malumPath("block/ether_sconce")).texture("overlay", malumPath("block/ether_sconce_overlay")).texture("fire", malumPath("block/ether_sconce_fire")).texture("particle", malumPath("block/ether_sconce"));

        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(sconce).build());
    }

    public void wallEtherSconceBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile sconce = models().withExistingParent(name, malumPath("block/templates/template_ether_sconce_wall")).texture("sconce", malumPath("block/ether_sconce")).texture("overlay", malumPath("block/ether_sconce_overlay")).texture("fire", malumPath("block/ether_sconce_fire")).texture("particle", malumPath("block/ether_sconce"));

        getVariantBuilder(blockRegistryObject.get())
                .partialState().with(WallTorchBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(sconce).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.WEST)
                .modelForState().modelFile(sconce).rotationY(270).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(sconce).rotationY(180).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.EAST)
                .modelForState().modelFile(sconce).rotationY(90).addModel();
    }

    public void etherTorchBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile torch = models().withExistingParent(name, malumPath("block/templates/template_ether_torch")).texture("torch", malumPath("block/ether_torch")).texture("colored", malumPath("block/ether_torch_overlay")).texture("particle", malumPath("block/runewood_planks"));

        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(torch).build());
    }

    public void wallEtherTorchBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile torch = models().withExistingParent(name, malumPath("block/templates/template_ether_torch_wall")).texture("torch", malumPath("block/ether_torch")).texture("colored", malumPath("block/ether_torch_overlay")).texture("particle", malumPath("block/runewood_planks"));

        getVariantBuilder(blockRegistryObject.get())
                .partialState().with(WallTorchBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(torch).rotationY(270).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.WEST)
                .modelForState().modelFile(torch).rotationY(180).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(torch).rotationY(90).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.EAST)
                .modelForState().modelFile(torch).addModel();
    }

    public void torchBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile torch = models().torch(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), malumPath("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(torch).build());
    }

    public void wallTorchBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile torch = models().torchWall(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), malumPath("block/" + name.substring(5)));

        getVariantBuilder(blockRegistryObject.get())
                .partialState().with(WallTorchBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(torch).rotationY(270).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.WEST)
                .modelForState().modelFile(torch).rotationY(180).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(torch).rotationY(90).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.EAST)
                .modelForState().modelFile(torch).addModel();
    }

    public void itemStandBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String particleName = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath().replaceFirst("_item_stand", "");
        if (!particleName.endsWith("_rock")) {
            particleName += "_planks";
        }
        ModelFile stand = models().withExistingParent(name, malumPath("block/templates/template_item_stand")).texture("stand", malumPath("block/" + name)).texture("particle", malumPath("block/" + particleName));

        getVariantBuilder(blockRegistryObject.get()).partialState()
                .partialState().with(BlockStateProperties.FACING, Direction.NORTH)
                .modelForState().modelFile(stand).rotationX(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.WEST)
                .modelForState().modelFile(stand).rotationX(90).rotationY(270).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.SOUTH)
                .modelForState().modelFile(stand).rotationX(90).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.EAST)
                .modelForState().modelFile(stand).rotationX(90).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.DOWN)
                .modelForState().modelFile(stand).rotationX(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.UP)
                .modelForState().modelFile(stand).addModel();
    }

    public void itemPedestalBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String particleName = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath().replaceFirst("_item_pedestal", "");
        String modelLocation = "block/templates/template_rock_item_pedestal";
        if (!particleName.endsWith("_rock")) {
            particleName += "_planks";
            modelLocation = "block/templates/template_wood_item_pedestal";
        }
        ModelFile pedestal = models().withExistingParent(name, malumPath(modelLocation)).texture("pedestal", malumPath("block/" + name)).texture("particle", malumPath("block/" + particleName));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(pedestal).build());
    }

    public void cutBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = "polished_" + name.substring(4);
        simpleBlock(blockRegistryObject.get(), models().cubeBottomTop(name, malumPath("block/" + name), malumPath("block/" + baseName), malumPath("block/" + baseName)));
    }

    public void cutPlanksBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - "_planks".length()).substring(4) + "_panel";
        simpleBlock(blockRegistryObject.get(), models().cubeBottomTop(name, malumPath("block/" + name), malumPath("block/" + baseName), malumPath("block/" + baseName)));
    }

    public void grassBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile file = models().cubeBottomTop(name, malumPath("block/" + name + "_side"), new ResourceLocation("block/dirt"), malumPath("block/" + name + "_top"));

        getVariantBuilder(blockRegistryObject.get()).partialState().modelForState()
                .modelFile(file)
                .nextModel().modelFile(file).rotationY(90)
                .nextModel().modelFile(file).rotationY(180)
                .nextModel().modelFile(file).rotationY(270)
                .addModel();
    }


    public void totemBaseBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String woodName = name.substring(0, 8);
        ResourceLocation side = malumPath("block/" + woodName + "_log");
        ResourceLocation top = malumPath("block/" + woodName + "_log_top");
        ResourceLocation planks = malumPath("block/" + woodName + "_planks");
        ResourceLocation panel = malumPath("block/" + woodName + "_panel");
        ModelFile base = models().withExistingParent(name, malumPath("block/templates/template_totem_base")).texture("side", side).texture("top", top).texture("planks", planks).texture("panel", panel);

        simpleBlock(blockRegistryObject.get(), base);
    }

    public void totemPoleBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String woodName = name.substring(0, 8);
        ResourceLocation side = malumPath("block/" + woodName + "_log");
        ResourceLocation top = malumPath("block/" + woodName + "_log_top");

        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> {
            String type = s.getValue(SpiritTypeRegistry.SPIRIT_TYPE_PROPERTY);
            MalumSpiritType spiritType = SpiritTypeRegistry.SPIRITS.get(type);
            ModelFile pole = models().withExistingParent(name + "_" + spiritType.identifier, malumPath("block/templates/template_totem_pole")).texture("side", side).texture("top", top).texture("front", malumPath("spirit/" + spiritType.identifier + "_" + woodName + "_cutout"));
            return ConfiguredModel.builder().modelFile(pole).rotationY(((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build();
        });
    }

    public void malumLeavesBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile leaves = models().withExistingParent(name, new ResourceLocation("block/leaves")).texture("all", malumPath("block/" + name));
        simpleBlock(blockRegistryObject.get(), leaves);
    }

    public void pillarCapBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 11);
        String pillarName = name.substring(0, name.length() - 4) + "_top";
        directionalBlock(blockRegistryObject.get(), models().cubeBottomTop(name, malumPath("block/" + name), malumPath("block/" + pillarName), malumPath("block/smooth_" + baseName)));
    }

    public void trapdoorBlock(RegistryObject<Block> blockRegistryObject) {
        trapdoorBlock((TrapDoorBlock) blockRegistryObject.get(), blockTexture(blockRegistryObject.get()), true);
    }

    public void doorBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        doorBlock((DoorBlock) blockRegistryObject.get(), malumPath("block/" + name + "_bottom"), malumPath("block/" + name + "_top"));
    }

    public void fenceGateBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 11);
        fenceGateBlock((FenceGateBlock) blockRegistryObject.get(), malumPath("block/" + baseName));
    }

    public void fenceBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 6);
        fenceBlock((FenceBlock) blockRegistryObject.get(), malumPath("block/" + baseName));
    }

    public void wallBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        wallBlock((WallBlock) blockRegistryObject.get(), malumPath("block/" + baseName));
    }

    public void stairsBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        stairsBlock((StairBlock) blockRegistryObject.get(), malumPath("block/" + baseName));
    }

    public void pressurePlateBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 15);
        ModelFile pressurePlateDown = models().withExistingParent(name + "_down", new ResourceLocation("block/pressure_plate_down")).texture("texture", malumPath("block/" + baseName));
        ModelFile pressurePlateUp = models().withExistingParent(name + "_up", new ResourceLocation("block/pressure_plate_up")).texture("texture", malumPath("block/" + baseName));

        getVariantBuilder(blockRegistryObject.get()).partialState().with(PressurePlateBlock.POWERED, true).modelForState().modelFile(pressurePlateDown).addModel().partialState().with(PressurePlateBlock.POWERED, false).modelForState().modelFile(pressurePlateUp).addModel();
    }

    public void brazierBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String textureName = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath().replaceFirst("_iridescent", "");
        String particleName = textureName.replaceFirst("_ether_brazier", "") + "_rock";
        ModelFile brazier = models().withExistingParent(name, malumPath("block/templates/template_ether_brazier")).texture("brazier", malumPath("block/" + textureName)).texture("particle", malumPath("block/" + particleName));
        ModelFile brazier_hanging = models().withExistingParent(name + "_hanging", malumPath("block/templates/template_ether_brazier_hanging")).texture("brazier", malumPath("block/" + textureName)).texture("particle", malumPath("block/" + particleName));

        getVariantBuilder(blockRegistryObject.get())
                .partialState().with(EtherBrazierBlock.HANGING, false).modelForState().modelFile(brazier).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, false).modelForState().modelFile(brazier_hanging).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, true).modelForState().modelFile(brazier_hanging).rotationY(90).addModel();
    }

    public void lanternBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile lantern = models().withExistingParent(name, new ResourceLocation("block/templates/template_lantern")).texture("lantern", malumPath("block/" + name));
        ModelFile hangingLantern = models().withExistingParent(name + "_hanging", new ResourceLocation("block/templates/template_hanging_lantern")).texture("lantern", malumPath("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).partialState().with(LanternBlock.HANGING, true).modelForState().modelFile(hangingLantern).addModel().partialState().with(LanternBlock.HANGING, false).modelForState().modelFile(lantern).addModel();
    }

    public void buttonBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        ModelFile buttom = models().withExistingParent(name, new ResourceLocation("block/button")).texture("texture", malumPath("block/" + baseName));
        ModelFile buttonPressed = models().withExistingParent(name + "_pressed", new ResourceLocation("block/button_pressed")).texture("texture", malumPath("block/" + baseName));
        Function<BlockState, ModelFile> modelFunc = $ -> buttom;
        Function<BlockState, ModelFile> pressedModelFunc = $ -> buttonPressed;
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(BlockStateProperties.POWERED) ? pressedModelFunc.apply(s) : modelFunc.apply(s)).uvLock(s.getValue(BlockStateProperties.ATTACH_FACE).equals(AttachFace.WALL)).rotationX(s.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90).rotationY((((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (s.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360).build());
        models().withExistingParent(name + "_inventory", new ResourceLocation("block/button_inventory")).texture("texture", malumPath("block/" + baseName));
    }

    public void tallPlantBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile bottom = models().withExistingParent(name + "_bottom", new ResourceLocation("block/cross")).texture("cross", malumPath("block/" + name + "_bottom"));
        ModelFile top = models().withExistingParent(name + "_top", new ResourceLocation("block/cross")).texture("cross", malumPath("block/" + name + "_top"));
        getVariantBuilder(blockRegistryObject.get()).partialState().with(DoublePlantBlock.HALF, LOWER).modelForState().modelFile(bottom).addModel().partialState().with(DoublePlantBlock.HALF, UPPER).modelForState().modelFile(top).addModel();
    }

    public void plantBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile cross = models().withExistingParent(name, new ResourceLocation("block/cross")).texture("cross", malumPath("block/" + name));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(cross).build());
    }

    public void slabBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        slabBlock((SlabBlock) blockRegistryObject.get(), malumPath(baseName), malumPath("block/" + baseName));
    }

    public void logBlock(RegistryObject<Block> blockRegistryObject) {
        if (blockRegistryObject.equals(RUNEWOOD) || blockRegistryObject.equals(STRIPPED_RUNEWOOD) || blockRegistryObject.equals(SOULWOOD) || blockRegistryObject.equals(STRIPPED_SOULWOOD)) {
            woodBlock(blockRegistryObject);
            return;
        }
        logBlock((RotatedPillarBlock) blockRegistryObject.get());
    }

    public void woodBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name + "_log";
        axisBlock((RotatedPillarBlock) blockRegistryObject.get(), malumPath("block/" + baseName), malumPath("block/" + baseName));
    }
}
