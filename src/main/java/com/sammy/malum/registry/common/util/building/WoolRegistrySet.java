package com.sammy.malum.registry.common.util.building;

import com.google.common.collect.ImmutableMap;
import com.sammy.malum.common.block.building.banner.fancy.FancyBannerBlock;
import com.sammy.malum.datagen.block.MalumBlockStateDatagen;
import com.sammy.malum.datagen.block.MalumBlockStateSmithTypes;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.block.properties.MalumBlockProperties;
import com.sammy.malum.registry.common.util.DyedVariantBundle;
import com.sammy.malum.registry.common.util.MalumRegistrySet;
import com.sammy.malum.registry.common.util.PoppetRegistrySet;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WoolCarpetBlock;
import team.lodestar.lodestone.modules.datagen.BlockStateSmithTypes;
import team.lodestar.lodestone.modules.datagen.DatagenSystemCommons;
import team.lodestar.lodestone.modules.datagen.smith.blockstate.BlockStateSystemData;
import team.lodestar.lodestone.modules.toolkit.block.BlockBlockItemHolder;
import team.lodestar.lodestone.modules.toolkit.creative_tab.CreativeTabCategoryBuilder;

import java.util.List;

public class WoolRegistrySet extends MalumRegistrySet {

    public static List<WoolRegistrySet> getMalumSets() {
        return List.of(MalumContent.BuildingBlocks.FANCY_WOOL);
    }

    private final DyedVariantBundle<BlockBlockItemHolder<Block, BlockItem>> fancyWool;
    private final DyedVariantBundle<BlockBlockItemHolder<Block, BlockItem>> fancyWoolCarpet;

    private final DyedVariantBundle<BlockBlockItemHolder<Block, BlockItem>> fancyBanner;

    public WoolRegistrySet(String id) {
        super(id);
        var woolProperties = MalumBlockProperties.POPPETRY_IMPLEMENT();
        fancyWool = new DyedVariantBundle<>(name("%s_%c_wool"), (s, c) -> MalumContent.registerBlock(s, () -> new Block(woolProperties)));
        fancyWoolCarpet = new DyedVariantBundle<>(name("%s_%c_wool_carpet"), (s, c) -> MalumContent.registerBlock(s, () -> new WoolCarpetBlock(c, woolProperties)));

        fancyBanner = new DyedVariantBundle<>(name("%s_%c_banner"), (s, c) -> MalumContent.registerBlock(s, () -> new FancyBannerBlock(c, woolProperties)));
    }

    public DyedVariantBundle<BlockBlockItemHolder<Block, BlockItem>> getFancyWool() {
        return fancyWool;
    }

    public DyedVariantBundle<BlockBlockItemHolder<Block, BlockItem>> getFancyWoolCarpet() {
        return fancyWoolCarpet;
    }

    public DyedVariantBundle<BlockBlockItemHolder<Block, BlockItem>> getFancyBanner() {
        return fancyBanner;
    }

    public static void addWools(CreativeTabCategoryBuilder builder) {
        for (WoolRegistrySet set : getMalumSets()) {
            set.addToCreativeTab(builder);
        }
    }

    public void addToCreativeTab(CreativeTabCategoryBuilder builder) {
        builder.nextLine();
        fancyWool.addToCreativeTab(builder::addItems);
        builder.nextLine();
        fancyWoolCarpet.addToCreativeTab(builder::addItems);
        builder.nextLine();
        fancyBanner.addToCreativeTab(builder::addItems);
    }

    public void addBlockStates(MalumBlockStateDatagen datagen, BlockStateSystemData<MalumBlockStateDatagen> data) {
        datagen.setTexturePath("building/wool");
        fancyWool.forEachVariant((c, b) -> BlockStateSmithTypes.FULL_BLOCK.act(data, b));
        fancyWoolCarpet.forEachVariant((c, b) -> BlockStateSmithTypes.CARPET_BLOCK.act(data, b));
        fancyBanner.forEachVariant((c, b) -> MalumBlockStateSmithTypes.BANNER.act(data, b));
        DatagenSystemCommons.BLOCK_TEXTURE.clearFolder();

    }
}