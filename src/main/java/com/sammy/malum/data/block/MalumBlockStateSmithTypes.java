package com.sammy.malum.data.block;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.common.block.ether.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.data.item.*;
import com.sammy.malum.registry.MalumRegistries;
import com.sammy.malum.registry.common.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.*;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.statesmith.*;

import java.util.Objects;
import java.util.function.*;

import static com.sammy.malum.MalumMod.*;

public class MalumBlockStateSmithTypes {

    public static BlockStateSmith<TotemPoleBlock> TOTEM_POLE = new BlockStateSmith<>(TotemPoleBlock.class, ItemModelSmithTypes.NO_MODEL, (block, provider) -> {
        String name = provider.getBlockName(block);
        String woodName = name.substring(0, 8);
        ResourceLocation parent = malumPath("block/templates/template_totem_pole");
        ResourceLocation side = provider.getBlockTexture(woodName + "_log");
        ResourceLocation top = provider.getBlockTexture(woodName + "_log_top");
        provider.getVariantBuilder(block).forAllStates(s -> {
            String type = s.getValue(SpiritTypeRegistry.SPIRIT_TYPE_PROPERTY);
            MalumSpiritType spiritType = MalumRegistries.SPIRITS.getValue(MalumRegistries.SPIRITS.getRegistryName());
            assert spiritType != null;
            ResourceLocation front = provider.modLoc("block/totem_poles/" + Objects.requireNonNull(spiritType.getRegistryName()).getNamespace() + "_" + woodName + "_cutout");
            ModelFile pole = provider.models().withExistingParent(name + "_" + spiritType.getRegistryName().getNamespace(), parent)
                    .texture("side", side)
                    .texture("top", top)
                    .texture("front", front);
            return ConfiguredModel.builder().modelFile(pole).rotationY(((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build();
        });
    });
    public static BlockStateSmith<PrimordialSoupBlock> PRIMORDIAL_SOUP = new BlockStateSmith<>(PrimordialSoupBlock.class, ItemModelSmithTypes.NO_MODEL, (block, provider) -> {
        String name = provider.getBlockName(block);
        ModelFile model = provider.models().withExistingParent(name, new ResourceLocation("block/powder_snow")).texture("texture", malumPath("block/weeping_well/" + name));
        ModelFile topModel = provider.models().getExistingFile(malumPath("block/" + name + "_top"));
        provider.getVariantBuilder(block).forAllStates(s -> ConfiguredModel.builder().modelFile(s.getValue(PrimordialSoupBlock.TOP) ? topModel : model).build());
    });

    public static BlockStateSmith<Block> BLIGHTED_BLOCK = new BlockStateSmith<>(Block.class, (block, provider) -> {
        String name = provider.getBlockName(block);

        ModelFile soil0 = provider.models().cubeAll(name, malumPath("block/" + name + "_0"));
        ModelFile soil1 = provider.models().cubeAll(name + "_1", malumPath("block/" + name + "_1"));

        provider.getVariantBuilder(block).partialState().modelForState()
                .modelFile(soil0)
                .nextModel().modelFile(soil0).rotationY(90)
                .nextModel().modelFile(soil0).rotationY(180)
                .nextModel().modelFile(soil0).rotationY(270)

                .nextModel().modelFile(soil1)
                .nextModel().modelFile(soil1).rotationY(90)
                .nextModel().modelFile(soil1).rotationY(180)
                .nextModel().modelFile(soil1).rotationY(270)
                .addModel();
    });

    public static BlockStateSmith<Block> BLIGHTED_TUMOR = new BlockStateSmith<>(Block.class, ItemModelSmithTypes.AFFIXED_MODEL.apply("_0"), (block, provider) -> {
        String name = provider.getBlockName(block);
        Function<Integer, ModelFile> tumorFunction = (i) -> provider.models().withExistingParent(name + "_" + i, new ResourceLocation("block/cross")).texture("cross", malumPath("block/" + name + "_" + i));

        provider.getVariantBuilder(block).partialState().modelForState()
                .modelFile(tumorFunction.apply(0))
                .nextModel().modelFile(tumorFunction.apply(1))
                .nextModel().modelFile(tumorFunction.apply(2))
                .nextModel().modelFile(tumorFunction.apply(3))
                .addModel();
    });

    public static BlockStateSmith<EtherBrazierBlock> BRAZIER_BLOCK = new BlockStateSmith<>(EtherBrazierBlock.class, MalumItemModelSmithTypes.ETHER_BRAZIER_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        String textureName = name.replaceFirst("_iridescent", "");
        String particleName = textureName.replaceFirst("_ether_brazier", "") + "_rock";
        ModelFile brazier = provider.models().withExistingParent(name, malumPath("block/templates/template_ether_brazier")).texture("brazier", malumPath("block/" + textureName)).texture("particle", malumPath("block/" + particleName));
        ModelFile brazier_hanging = provider.models().withExistingParent(name + "_hanging", malumPath("block/templates/template_ether_brazier_hanging")).texture("brazier", malumPath("block/" + textureName)).texture("particle", malumPath("block/" + particleName));

        provider.getVariantBuilder(block)
                .partialState().with(EtherBrazierBlock.HANGING, false).modelForState().modelFile(brazier).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, false).modelForState().modelFile(brazier_hanging).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, true).modelForState().modelFile(brazier_hanging).rotationY(90).addModel();
    });

    public static BlockStateSmith<EtherBrazierBlock> IRIDESCENT_BRAZIER_BLOCK = new BlockStateSmith<>(EtherBrazierBlock.class, MalumItemModelSmithTypes.IRIDESCENT_ETHER_BRAZIER_ITEM, (block, provider) -> {
        String name = provider.getBlockName(block);
        String textureName = name.replaceFirst("_iridescent", "");
        String particleName = textureName.replaceFirst("_ether_brazier", "") + "_rock";
        ModelFile brazier = provider.models().withExistingParent(name, malumPath("block/templates/template_ether_brazier")).texture("brazier", malumPath("block/" + textureName)).texture("particle", malumPath("block/" + particleName));
        ModelFile brazier_hanging = provider.models().withExistingParent(name + "_hanging", malumPath("block/templates/template_ether_brazier_hanging")).texture("brazier", malumPath("block/" + textureName)).texture("particle", malumPath("block/" + particleName));

        provider.getVariantBuilder(block)
                .partialState().with(EtherBrazierBlock.HANGING, false).modelForState().modelFile(brazier).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, false).modelForState().modelFile(brazier_hanging).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, true).modelForState().modelFile(brazier_hanging).rotationY(90).addModel();
    });
}
