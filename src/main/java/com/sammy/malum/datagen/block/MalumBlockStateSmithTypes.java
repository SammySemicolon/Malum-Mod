package com.sammy.malum.datagen.block;

import com.sammy.malum.*;
import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.blight.scarstone.*;
import com.sammy.malum.common.block.building.banner.MalumBannerBlock;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.aerial.GustIgniterBlock;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.base.ElementalArtificeBlock;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.base.PrimaryArtificeBlock;
import com.sammy.malum.common.block.curiosities.artifice.soul_link.SoulLinkBlock;
import com.sammy.malum.common.block.curiosities.artifice.waveform.SpiritDiodeBlock;
import com.sammy.malum.common.block.curiosities.artifice.repair_pylon.*;
import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlock;
import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlock;
import com.sammy.malum.common.block.curiosities.totem.channel.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.common.block.building.ColumnBlock;
import com.sammy.malum.common.block.dungeon.WrithingFleshBlock;
import com.sammy.malum.common.block.ether.*;
import com.sammy.malum.common.block.flora.EbonyStalkBlock;
import com.sammy.malum.common.block.flora.wood.MalumHangingLeavesBlock;
import com.sammy.malum.common.block.flora.wood.MalumLeavesBlock;
import com.sammy.malum.common.block.geode.GeodeCrystalClusterBlock;
import com.sammy.malum.common.block.soulstone.ArchaicSoulstoneBudBlock;
import com.sammy.malum.common.block.soulstone.SoulstoneBudBlock;
import com.sammy.malum.datagen.item.MalumItemModelSmithTypes;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.client.model.generators.*;
import team.lodestar.lodestone.modules.datagen.ItemModelSmithTypes;
import team.lodestar.lodestone.modules.datagen.providers.block.LodestoneBlockStateSystem;
import team.lodestar.lodestone.modules.datagen.smith.blockstate.BlockStateSmith;

import java.util.HashMap;
import java.util.function.Function;

import static com.sammy.malum.MalumMod.malumPath;

@SuppressWarnings("rawtypes")
public class MalumBlockStateSmithTypes {

    public static BlockStateSmith<Block> STORAGE_BLOCK = new BlockStateSmith<>(Block.class, (block, provider) -> {
        provider.directionalBlock(block, provider.models().cubeBottomTop(block));
    });

    public static BlockStateSmith<Block> METALLIC_STORAGE_BLOCK = new BlockStateSmith<>(Block.class, (block, provider) -> {
        String name = provider.getBlockName(block);
        boolean isDerealized = name.contains("derealized");
        var metalName = name.replace("block_of_", "").replace(isDerealized ? "derealized_" : "harmonized_", "");
        var affixedTextureName = isDerealized ? metalName + "_derealized" : metalName;
        var topTexture = provider.getBlockTexture(metalName + "_top");
        var sideTexture = provider.getBlockTexture(affixedTextureName + "_side");
        var bottomTexture = provider.getBlockTexture(affixedTextureName + "_bottom");
        var model = provider.models().cubeBottomTop(name, sideTexture, bottomTexture, topTexture);
        provider.directionalBlock(block, model);
    });

    public static BlockStateSmith<Block> CUT_STONE_BLOCK = new BlockStateSmith<>(Block.class, (block, provider) -> {
        var name = provider.getBlockName(block);
        var material = name.substring(name.indexOf("_")+1);
        var sideTexture = provider.getBlockTexture(name);
        var bottomTexture = provider.getBlockTexture(material + "_mosaic");
        var topTexture = provider.getBlockTexture("polished_" + material);
        var model = provider.models().cubeBottomTop(name, sideTexture, bottomTexture, topTexture);
        provider.simpleBlock(block, model);
    });

    public static BlockStateSmith<Block> SOULSTONE_BUD = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.GENERATED_ITEM, (block, provider) -> {
        var name = provider.getBlockName(block);
        var modelPath = "block/soulstone/" + name;
        provider.getVariantBuilder(block).forAllStates(s -> {
            var direction = s.getValue(SoulstoneBudBlock.FACING);
            var stageProperty = block instanceof SoulstoneBudBlock ? SoulstoneBudBlock.STAGE : ArchaicSoulstoneBudBlock.STAGE;
            int stage = s.getValue(stageProperty);
            var model = provider.models().getExistingFile(malumPath(modelPath + stage));
            return ConfiguredModel.builder().modelFile(model)
                    .rotationX(direction == Direction.DOWN ? 180 : direction.getAxis().isHorizontal() ? 90 : 0)
                    .rotationY(direction.getAxis().isVertical() ? 0 : (((int) direction.toYRot() + 180)) % 360)
                    .build();
        });
    });

    public static BlockStateSmith<GeodeCrystalClusterBlock> GEODE_CRYSTAL_CLUSTER = new BlockStateSmith<>(GeodeCrystalClusterBlock.class, ItemModelSmithTypes.GENERATED_ITEM, (block, provider) -> {
        var name = provider.getBlockName(block);
        provider.getVariantBuilder(block).forAllStatesExcept(s -> {
            var direction = s.getValue(SoulstoneBudBlock.FACING);
            int value = s.getValue(GeodeCrystalClusterBlock.AGE);
            String indexed = name + "_" + value;
            ResourceLocation texture = provider.getBlockTexture(indexed);
            var model = provider.models().cross(indexed, texture);
            return ConfiguredModel.builder().modelFile(model)
                    .rotationX(direction == Direction.DOWN ? 180 : direction.getAxis().isHorizontal() ? 90 : 0)
                    .rotationY(direction.getAxis().isVertical() ? 0 : (((int) direction.toYRot() + 180)) % 360)
                    .build();

        }, MalumLeavesBlock.WATERLOGGED);
    });

    public static BlockStateSmith<MalumLeavesBlock> STAGED_LEAVES = new BlockStateSmith<>(MalumLeavesBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM.addModelParentAffix("3"), (block, provider) -> {
        var name = provider.getBlockName(block);

        provider.getVariantBuilder(block).forAllStatesExcept(s -> {
            int value = s.getValue(block.getColorProperty());
            String indexed = name + value;
            ResourceLocation texture = provider.getBlockTexture(indexed);
            var model = provider.models().leaves(indexed, texture);
            return ConfiguredModel.builder().modelFile(model).build();

        }, MalumLeavesBlock.WATERLOGGED, MalumLeavesBlock.DISTANCE, MalumLeavesBlock.PERSISTENT);
    });

    public static BlockStateSmith<MalumHangingLeavesBlock> STAGED_HANGING_LEAVES = new BlockStateSmith<>(MalumHangingLeavesBlock.class, ItemModelSmithTypes.BLOCK_TEXTURE_ITEM.addTextureNameAffix("3"), (block, provider) -> {
        var name = provider.getBlockName(block);

        provider.getVariantBuilder(block).forAllStatesExcept(s -> {
            int value = s.getValue(block.getColorProperty());
            String indexed = name + value;
            ResourceLocation texture = provider.getBlockTexture(indexed);
            var model = provider.models().cross(indexed, texture);
            return ConfiguredModel.builder().modelFile(model).build();

        }, MalumLeavesBlock.WATERLOGGED);
    });

    public static BlockStateSmith<BlightedCoverageBlock> COVERING_BLOCK = new BlockStateSmith<>(BlightedCoverageBlock.class, ItemModelSmithTypes.BLOCK_TEXTURE_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        ModelFile model = provider.models().withExistingParent(name, MalumMod.malumPath("block/templates/template_covering"))
                .texture("covering", provider.getBlockTexture(name));
        MultiPartBlockStateBuilder multipartBuilder = provider.getMultipartBuilder(block);
        for (Direction direction : Direction.values()) {
            BooleanProperty property = (BooleanProperty) block.defaultBlockState().getProperties().stream().filter(p -> p.getName().equals(direction.getName())).findFirst().orElseThrow();
            int yRotation = ((int) direction.toYRot() + 180) % 360;
            int xRotation = 0;
            if (direction.getAxis().isVertical()) {
                xRotation = direction.equals(Direction.UP) ? 270 : 90;
            }
            multipartBuilder.part().modelFile(model).rotationY(yRotation).rotationX(xRotation).addModel()
                    .condition(property, true).end();

            //handles the situation where the block is all alone, not connected to anything
            final MultiPartBlockStateBuilder.PartBuilder partBuilder = multipartBuilder.part().modelFile(model).rotationY(yRotation).rotationX(xRotation).addModel();
            for (Direction again : Direction.values()) {
                property = (BooleanProperty) block.defaultBlockState().getProperties().stream().filter(p -> p.getName().equals(again.getName())).findFirst().orElseThrow();
                partBuilder.condition(property, false);
            }
            partBuilder.end();
        }
    });

    public static BlockStateSmith<TotemPoleBlock> TOTEM_POLE = new BlockStateSmith<>(TotemPoleBlock.class, ItemModelSmithTypes.NO_DATAGEN, (block, provider) -> {
        String name = provider.getBlockName(block);
        String woodName = name.substring(0, 8);
        ResourceLocation parent = malumPath("block/templates/template_totem_pole");
        ResourceLocation side = provider.getBlockTexture(woodName + "_log");
        ResourceLocation top = provider.getBlockTexture(woodName + "_log_top");
        provider.getVariantBuilder(block).forAllStates(s -> {
            String type = s.getValue(TotemPoleBlock.SPIRIT);
            ResourceLocation front = MalumMod.malumPath("block/totem_poles/" + type + "_" + woodName + "_cutout");
            ModelFile pole = provider.models().withExistingParent(name + "_" + type, parent)
                    .texture("side", side)
                    .texture("top", top)
                    .texture("front", front);
            return ConfiguredModel.builder().modelFile(pole).rotationY(((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build();
        });
    });

    public static BlockStateSmith<EbonyStalkBlock> EBONY = new BlockStateSmith<>(EbonyStalkBlock.class, ItemModelSmithTypes.GENERATED_ITEM, (block, provider) -> {
        provider.getVariantBuilder(block).forAllStates(s -> {
            var leaves = s.getValue(EbonyStalkBlock.LEAVES);
            if (leaves.equals(BambooLeaves.NONE)) {
                var model = provider.models().getExistingFile(MalumMod.malumPath("block/flora/ebony"));
                return ConfiguredModel.builder().modelFile(model).build();
            }
            var leavesType = leaves + "_leaves";
            var builder = ConfiguredModel.builder();
            for (int i = 0; i < 4; i++) {
                var path = malumPath("block/flora/ebony_" + leavesType + i);
                var model = provider.models().getExistingFile(path);
                builder.modelFile(model);
                if (i != 3) {
                    builder = builder.nextModel();
                }
            }
            return builder.build();
        });
    });

    public static BlockStateSmith<MalumBannerBlock> BANNER = new BlockStateSmith<>(MalumBannerBlock.class, MalumItemModelSmithTypes.NO_DATAGEN, (block, provider) -> {
        var hanging = malumPath("block/banner/banner_holder");
        var mounted = malumPath("block/banner/banner_holder_mounted");
        provider.getVariantBuilder(block).forAllStates(s -> {
            var value = s.getValue(MalumBannerBlock.BANNER_TYPE);
            boolean isVertical = value.direction.getAxis().isVertical();
            var direction = isVertical ? (value.equals(MalumBannerBlock.BannerType.HANGING_Z) ? Direction.NORTH : Direction.WEST) : value.direction;
            var model = isVertical ? hanging : mounted;
            return ConfiguredModel.builder().modelFile(provider.models().getExistingFile(model)).rotationY(((int) direction.toYRot()) % 360).build();
        });
    });


    public static BlockStateSmith<ColumnBlock> COLUMN = new BlockStateSmith<>(ColumnBlock.class, MalumItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        provider.getVariantBuilder(block).forAllStates(s -> {
            var upper = s.getValue(ColumnBlock.TOP);
            var lower = s.getValue(ColumnBlock.BOTTOM);
            var axis = s.getValue(ColumnBlock.AXIS);
            String affix = "";
            if (upper && lower) {
                affix = "_segment";
            } else if (upper) {
                affix = "_lower";
            } else if (lower) {
                affix = "_upper";
            }
            ResourceLocation side = provider.getBlockTexture(name + affix);
            ResourceLocation end = provider.getBlockTexture(name + "_end");
            BlockModelBuilder model = provider.models().cubeColumn(name + affix, side, end);
            int y = axis.equals(Direction.Axis.X) ? 90 : 180;
            int x = axis.equals(Direction.Axis.Y) ? 0 : 90;
            return ConfiguredModel.builder().modelFile(model).rotationX(x).rotationY(y).build();
        });
    });

    public static BlockStateSmith<SpiritDiodeBlock> SPIRIT_DIODE = new BlockStateSmith<>(SpiritDiodeBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        ResourceLocation top = provider.getBlockTexture("runewood_frame_top");
        ResourceLocation opened = provider.getBlockTexture("runewood_frame_top_open");
        ResourceLocation bottom = provider.getBlockTexture("runewood_frame_bottom");
        ResourceLocation locked = provider.getBlockTexture("runewood_frame_locked");
        ResourceLocation input = provider.getBlockTexture("runewood_frame_input");
        ResourceLocation output = provider.getBlockTexture(name + "_output");
        BlockModelBuilder model = provider.models().cube(name, bottom, top, output, input, locked, locked).texture("particle", output);
        BlockModelBuilder openModel = provider.models().cube(name + "_open", bottom, opened, output, input, locked, locked).texture("particle", output);
        provider.getVariantBuilder(block).forAllStates(s -> {
            var direction = s.getValue(SpiritDiodeBlock.FACING);
            return ConfiguredModel.builder().modelFile(s.getValue(SpiritDiodeBlock.OPEN) ? openModel : model).rotationY(((int) direction.toYRot()) % 360).build();
        });
    });

    public static BlockStateSmith<ElementalArtificeBlock> ELEMENTAL_ARTIFICE_BLOCK = new BlockStateSmith<>(ElementalArtificeBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        var name = provider.getBlockName(block);
        var bottom = provider.getBlockTexture(name + "_bottom");
        boolean isPrimaryArtifice = block instanceof PrimaryArtificeBlock<?>;

        var models = new HashMap<String, BlockModelBuilder>();
        provider.getVariantBuilder(block).forAllStates(s -> {
            var isOpen = s.getValue(ElementalArtificeBlock.OPEN);
            var isPowered = s.getValue(ElementalArtificeBlock.POWERED);
            var direction = s.getValue(ElementalArtificeBlock.FACING);
            var modelName = name;
            if (isPrimaryArtifice) {
                var isCaptured = s.getValue(GustIgniterBlock.CAPTURED);
                if (isCaptured) {
                    modelName += "_captured";
                }
            }
            if (isOpen) {
                modelName += "_open";
            } else if (isPowered) {
                modelName += "_active";
            }
            BlockModelBuilder model;
            if (!models.containsKey(modelName)) {
                var side = provider.getBlockTexture(modelName + "_side");
                var top = provider.getBlockTexture(modelName + "_top");
                model = provider.models().cubeBottomTop(modelName, side, bottom, top).texture("particle", side);
                models.put(modelName, model);
            } else {
                model = models.get(modelName);
            }

            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            builder.modelFile(model)
                    .rotationX(direction == Direction.DOWN ? 180 : direction.getAxis().isHorizontal() ? 90 : 0)
                    .rotationY(direction.getAxis().isVertical() ? 0 : (((int) direction.toYRot() + 180)) % 360);
            return builder.build();
        });
    });

    public static BlockStateSmith<SoulLinkBlock> SOUL_LINK = new BlockStateSmith<>(SoulLinkBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        var name = provider.getBlockName(block);

        var base = malumPath("block/soul_link/soul_link");
        var slotted = malumPath("block/soul_link/soul_link_slotted");

        var models = new HashMap<String, BlockModelBuilder>();
        provider.getVariantBuilder(block).forAllStates(s -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();

            if (!SoulLinkBlock.OPTIONAL_SPIRIT.hasSpirit(s)) {
                return builder.modelFile(provider.models().getExistingFile(base)).build();
            }

            if (s.getValue(SoulLinkBlock.OPEN)) {
                return builder.modelFile(provider.models().getExistingFile(base)).build();
            }
            var spirit = s.getValue(SoulLinkBlock.OPTIONAL_SPIRIT);
            var modelName = name + spirit;
            if (!models.containsKey(modelName)) {
                var insideTexture = "glow_" + spirit;
                var model = provider.models().withExistingParent(modelName, slotted)
                        .texture("glow", provider.getBlockTexture(insideTexture));
                models.put(modelName, model);
            }
            return builder.modelFile(models.get(modelName)).build();
        });
    });

    public static BlockStateSmith<RepairPylonComponentBlock> REPAIR_PYLON_COMPONENT = new BlockStateSmith<>(RepairPylonComponentBlock.class, ItemModelSmithTypes.NO_DATAGEN, (block, provider) -> {
        ModelFile model = provider.models().getExistingFile(malumPath("block/repair_pylon_component_middle"));
        ModelFile topModel = provider.models().getExistingFile(malumPath("block/repair_pylon_component_top"));
        provider.getVariantBuilder(block).forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(RepairPylonComponentBlock.TOP) ? topModel : model).build());
    });

    public static BlockStateSmith<LargeStrangeCrystalBlock> LARGE_STRANGE_CRYSTAL = new BlockStateSmith<>(LargeStrangeCrystalBlock.class, ItemModelSmithTypes.GENERATED_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        provider.getVariantBuilder(block)
                .forAllStates(state -> {
                    ResourceLocation upper = provider.getBlockTexture(name + "_upper");
                    ResourceLocation lower = provider.getBlockTexture(name + "_lower");
                    final DoubleBlockHalf half = state.getValue(LargeStrangeCrystalBlock.HALF);
                    boolean isTop = half.equals(DoubleBlockHalf.UPPER);
                    var model = provider.models().cross(name + "_" + half.getSerializedName(), isTop ? upper : lower);
                    return ConfiguredModel.builder().modelFile(model).build();
                });
    });

    public static BlockStateSmith<CreepingBlightBlock> CREEPING_BLIGHT = new BlockStateSmith<>(CreepingBlightBlock.class, ItemModelSmithTypes.GENERATED_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        var roots = malumPath("block/templates/blight/template_soulwood_roots");
        var spike = malumPath("block/templates/blight/template_soulwood_spike");
        var clinging = malumPath("block/templates/blight/template_clinging_blight");
        var hanging = malumPath("block/templates/blight/template_hanging_blight");

        provider.getVariantBuilder(block).forAllStates(s -> {
            CreepingBlightBlock.BlightType value = s.getValue(CreepingBlightBlock.BLIGHT_TYPE);
            String valueName = value.getSerializedName();
            var parent = switch (value) {
                case SOULWOOD_ROOTS -> roots;
                case SOULWOOD_SPIKE -> spike;
                case CLINGING_BLIGHT -> clinging;
                case HANGING_BLIGHT -> hanging;
            };
            var largeStart = provider.getBlockTexture(valueName + "_large_start");
            var largeEnd = provider.getBlockTexture(valueName + "_large_end");
            var model = provider.models().withExistingParent(name + "_" + valueName, parent)
                    .texture("large_start", largeStart)
                    .texture("large_end", largeEnd)
                    .texture("particle", largeStart);
            if (parent.equals(roots)) {
                var smallStart = provider.getBlockTexture(valueName + "_small_start");
                var smallEnd = provider.getBlockTexture(valueName + "_small_end");
                model.texture("small_start", smallStart);
                model.texture("small_end", smallEnd);
            } else {
                var bracing = provider.getBlockTexture(valueName + "_bracing");
                var small = provider.getBlockTexture(valueName + "_small");
                model.texture("bracing", bracing);
                model.texture("small", small);
            }
            return ConfiguredModel.builder().modelFile(model).rotationY(((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build();
        });
    });

    public static BlockStateSmith<Block> BLIGHTED_GROWTH = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.NO_DATAGEN, (block, provider) -> {
        String name = provider.getBlockName(block);
        Function<Integer, ModelFile> tumorFunction = (i) -> provider.models().withExistingParent(name + "_" + i, ResourceLocation.withDefaultNamespace("block/cross")).texture("cross", malumPath("block/" + name + "_" + i));

        ConfiguredModel.Builder<VariantBlockStateBuilder> builder = provider.getVariantBuilder(block).partialState().modelForState();
        for (int i = 0; i < 10; i++) {
            builder = builder.modelFile(tumorFunction.apply(i));
            if (i != 9) {
                builder = builder.nextModel();
            }
        }
        builder.addModel();
    });


    public static BlockStateSmith<WrithingFleshBlock> WRITHING_FLESH = new BlockStateSmith<>(WrithingFleshBlock.class, ItemModelSmithTypes.GENERATED_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        var writhing = malumPath("block/templates/flesh/template_writhing_flesh");
        var clinging = malumPath("block/templates/flesh/template_clinging_flesh");
        var hanging = malumPath("block/templates/flesh/template_hanging_flesh");

        provider.getVariantBuilder(block).forAllStates(s -> {
            WrithingFleshBlock.FleshType value = s.getValue(WrithingFleshBlock.FLESH_TYPE);
            String valueName = value.getSerializedName();
            var parent = switch (value) {
                case WRITHING_FLESH -> writhing;
                case CLINGING_FLESH -> clinging;
                case HANGING_FLESH -> hanging;
            };
            var largeStart = provider.getBlockTexture(valueName + "_large_start");
            var largeEnd = provider.getBlockTexture(valueName + "_large_end");
            var model = provider.models().withExistingParent(name + "_" + valueName, parent)
                    .texture("large_start", largeStart)
                    .texture("large_end", largeEnd)
                    .texture("particle", largeStart);
            if (parent.equals(writhing)) {
                var smallStart = provider.getBlockTexture(valueName + "_small_start");
                var smallEnd = provider.getBlockTexture(valueName + "_small_end");
                model.texture("small_start", smallStart);
                model.texture("small_end", smallEnd);
            } else {
                var small = provider.getBlockTexture(valueName + "_small");
                model.texture("small", small);
            }
            return ConfiguredModel.builder().modelFile(model).rotationY(((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build();
        });
    });

    public static BlockStateSmith<RiteChannelBlock> RITE_CHANNEL = new BlockStateSmith<>(RiteChannelBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM, (block, provider) -> {
        var name = provider.getBlockName(block);

        var side = provider.getBlockTexture("rite_channel_side");
        var bottom = provider.getBlockTexture("rite_channel_bottom");

        provider.getVariantBuilder(block).forAllStates(state -> {
            var type = state.getValue(RiteChannelBlock.CHANNEL_TYPE);

            var suffix = switch (type) {
                case BASE -> "base";
                case STRAIGHT_X, STRAIGHT_Z -> "straight";
                case END_NORTH, END_EAST, END_SOUTH, END_WEST -> "end";
                case BEND_NORTH_EAST, BEND_EAST_SOUTH, BEND_SOUTH_WEST, BEND_WEST_NORTH -> "curve";
            };

            int rotationY = switch (type) {
                case BASE, STRAIGHT_Z, END_SOUTH, BEND_SOUTH_WEST -> 0;
                case STRAIGHT_X, END_WEST, BEND_WEST_NORTH -> 90;

                case END_NORTH, BEND_NORTH_EAST -> 180;
                case END_EAST, BEND_EAST_SOUTH -> 270;
            };

            var top = provider.getBlockTexture(name + "_" + suffix);

            var modelName = name + (type.equals(RiteChannelBlock.RiteChannelType.BASE) ? "" : "_" + type.getSerializedName());
            var model = provider.models().cubeBottomTop(modelName, side, bottom, top);
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(rotationY)
                    .build();
        });
    });

    public static BlockStateSmith<PrimordialSoupBlock> PRIMORDIAL_SOUP = new BlockStateSmith<>(PrimordialSoupBlock.class, ItemModelSmithTypes.BLOCK_MODEL_ITEM.addTextureNameAffix("_top"), (block, provider) -> {
        String name = provider.getBlockName(block);
        var model = provider.models().withExistingParent(name, ResourceLocation.withDefaultNamespace("block/powder_snow")).texture("texture", malumPath("block/enscription/" + name));
        var topModel = provider.models().getExistingFile(malumPath("block/" + name + "_top"));
        provider.getVariantBuilder(block).forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(PrimordialSoupBlock.TOP) ? topModel : model).build());
    });


    public static BlockStateSmith<EtherTorchBlock> ETHER_TORCH_BLOCK = new BlockStateSmith<>(EtherTorchBlock.class, MalumItemModelSmithTypes.ETHER_CONTAINING_ITEM.apply("ether_torch"), MalumBlockStateSmithTypes::makeEtherTorch);
    public static BlockStateSmith<EtherWallTorchBlock> ETHER_WALL_TORCH_BLOCK = new BlockStateSmith<>(EtherWallTorchBlock.class, MalumItemModelSmithTypes.NO_DATAGEN, MalumBlockStateSmithTypes::makeEtherWallTorch);
    public static BlockStateSmith<EtherCandleBlock> ETHER_CANDLE_BLOCK = new BlockStateSmith<>(EtherCandleBlock.class, MalumItemModelSmithTypes.ETHER_CONTAINING_ITEM.apply("ether_candle"), MalumBlockStateSmithTypes::makeEtherCandles);
    public static BlockStateSmith<EtherBrazierBlock> ETHER_BRAZIER_BLOCK = new BlockStateSmith<>(EtherBrazierBlock.class, MalumItemModelSmithTypes.ETHER_CONTAINING_ITEM.apply("ether_brazier"), MalumBlockStateSmithTypes::makeEtherBrazier);
    public static BlockStateSmith<EtherCressetBlock> ETHER_CRESSET_BLOCK = new BlockStateSmith<>(EtherCressetBlock.class, MalumItemModelSmithTypes.ETHER_CONTAINING_ITEM.apply("ether_cresset"), MalumBlockStateSmithTypes::makeEtherCresset);

    public static void makeEtherTorch(EtherTorchBlock block, LodestoneBlockStateSystem provider) {
        provider.simpleBlock(block, provider.models().getExistingFile(malumPath("block/ether_torch")));
    }

    public static void makeEtherWallTorch(EtherWallTorchBlock block, LodestoneBlockStateSystem provider) {
        provider.horizontalBlock(block, provider.models().getExistingFile(malumPath("block/ether_torch_wall")), 90);
    }

    public static void makeEtherCandles(EtherCandleBlock block, LodestoneBlockStateSystem provider) {
        var one = malumPath("block/ether_candle_one_candle");
        var two = malumPath("block/ether_candle_two_candles");
        var three = malumPath("block/ether_candle_three_candles");
        var four = malumPath("block/ether_candle_four_candles");
        provider.getVariantBuilder(block).forAllStates(s -> {
            int candles = s.getValue(EtherCandleBlock.CANDLES);
            var path = switch (candles) {
                case 1 -> one;
                case 2 -> two;
                case 3 -> three;
                default -> four;
            };
            var model = provider.models().getExistingFile(path);
            return ConfiguredModel.builder().modelFile(model)
                    .nextModel().modelFile(model).rotationY(90)
                    .nextModel().modelFile(model).rotationY(180)
                    .nextModel().modelFile(model).rotationY(270).build();
        });
    }

    public static void makeEtherBrazier(EtherBrazierBlock block, LodestoneBlockStateSystem provider) {
        var model = provider.models().getExistingFile(malumPath("block/ether_brazier"));
        var hanging = provider.models().getExistingFile(malumPath("block/ether_brazier_hanging"));

        provider.getVariantBuilder(block)
                .partialState().with(EtherBrazierBlock.HANGING, false).modelForState().modelFile(model).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, false).modelForState().modelFile(hanging).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, true).modelForState().modelFile(hanging).rotationY(90).addModel();
    }

    public static void makeEtherCresset(EtherCressetBlock block, LodestoneBlockStateSystem provider) {
        var bottom = provider.models().getExistingFile(malumPath("block/ether_cresset_bottom"));
        var top = provider.models().getExistingFile(malumPath("block/ether_cresset_top"));
        provider.getVariantBuilder(block)
                .partialState().with(EtherCressetBlock.HALF, DoubleBlockHalf.UPPER).modelForState().modelFile(top).addModel()
                .partialState().with(EtherCressetBlock.HALF, DoubleBlockHalf.LOWER).modelForState().modelFile(bottom).addModel();
    }
}