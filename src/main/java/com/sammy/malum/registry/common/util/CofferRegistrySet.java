package com.sammy.malum.registry.common.util;

import com.sammy.malum.common.block.storage.coffer.CofferBlock;
import com.sammy.malum.common.block.storage.coffer.DyedCofferBlock;
import com.sammy.malum.datagen.block.MalumBlockStateDatagen;
import com.sammy.malum.datagen.block.MalumBlockStateSmithTypes;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.block.properties.MalumBlockProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import team.lodestar.lodestone.modules.datagen.DatagenSystemCommons;
import team.lodestar.lodestone.modules.datagen.smith.blockstate.BlockStateSystemData;
import team.lodestar.lodestone.modules.toolkit.block.BlockBlockItemHolder;
import team.lodestar.lodestone.modules.toolkit.creative_tab.CreativeTabCategoryBuilder;

import java.util.List;

public class CofferRegistrySet extends MalumRegistrySet {

    public static List<CofferRegistrySet> getMalumSets() {
        return List.of(MalumContent.Sorcery.RUNEWOOD_COFFERS);
    }

    private final BlockBlockItemHolder<Block, BlockItem> coffer;
    private final DyedVariantBundle<BlockBlockItemHolder<Block, BlockItem>> dyedCoffers;

    public CofferRegistrySet(String id) {
        super(id);
        var cofferProperties = MalumBlockProperties.RUNEWOOD_IMPLEMENT();
        coffer = MalumContent.registerBlock(name("%s_coffer"), () -> new CofferBlock(cofferProperties));
        dyedCoffers = new DyedVariantBundle<>(name("%c_%s_coffer"), (s, c) -> MalumContent.registerBlock(s, () -> new DyedCofferBlock(cofferProperties, c)));
    }

    public BlockBlockItemHolder<Block, BlockItem> getCoffer() {
        return coffer;
    }

    public DyedVariantBundle<BlockBlockItemHolder<Block, BlockItem>> getDyedCoffers() {
        return dyedCoffers;
    }

    public void addToCreativeTab(CreativeTabCategoryBuilder builder) {
        builder.nextLine();
        builder.addItem(coffer);
        dyedCoffers.addToCreativeTab(builder::addItems);
    }

    public void addBlockStates(MalumBlockStateDatagen datagen, BlockStateSystemData<MalumBlockStateDatagen> data) {
        datagen.setTexturePath("coffer");
        MalumBlockStateSmithTypes.COFFER.act(data, coffer);
        datagen.setTexturePath("coffer/dyed");
        dyedCoffers.forEachVariant((c, b) -> MalumBlockStateSmithTypes.COFFER.act(data, b));
        DatagenSystemCommons.BLOCK_TEXTURE.clearFolder();
    }
}