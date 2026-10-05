package com.sammy.malum.common.creativetab;

import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlockItem;
import com.sammy.malum.registry.common.util.building.CommonStoneBuildingSet;
import com.sammy.malum.registry.common.util.building.WoolRegistrySet;
import team.lodestar.lodestone.modules.toolkit.creative_tab.CategorizedBuilder;

import static com.sammy.malum.registry.common.MalumContent.*;

public class MalumLesserSoulstuffTab extends AbstractMalumCreativeTab {

        public MalumLesserSoulstuffTab(CategorizedBuilder categorizedBuilder) {
                super(categorizedBuilder);
        }

        public void buildCategories() {
                createCategory("soulstuff")
                        .addItems(
                                BuildingBlocks.SACRED_SPIRITED_GLASS,
                                BuildingBlocks.WICKED_SPIRITED_GLASS,
                                BuildingBlocks.ARCANE_SPIRITED_GLASS,
                                BuildingBlocks.ELDRITCH_SPIRITED_GLASS,
                                BuildingBlocks.AERIAL_SPIRITED_GLASS,
                                BuildingBlocks.AQUEOUS_SPIRITED_GLASS,
                                BuildingBlocks.EARTHEN_SPIRITED_GLASS,
                                BuildingBlocks.INFERNAL_SPIRITED_GLASS,
                                BuildingBlocks.NULL_SPIRITED_GLASS
                        ).nextLine()
                        .addItems(
                                BuildingBlocks.SACRED_VARNISHED_TERRACOTTA,
                                BuildingBlocks.WICKED_VARNISHED_TERRACOTTA,
                                BuildingBlocks.ARCANE_VARNISHED_TERRACOTTA,
                                BuildingBlocks.ELDRITCH_VARNISHED_TERRACOTTA,
                                BuildingBlocks.AERIAL_VARNISHED_TERRACOTTA,
                                BuildingBlocks.AQUEOUS_VARNISHED_TERRACOTTA,
                                BuildingBlocks.EARTHEN_VARNISHED_TERRACOTTA,
                                BuildingBlocks.INFERNAL_VARNISHED_TERRACOTTA,
                                BuildingBlocks.NULL_VARNISHED_TERRACOTTA
                        ).nextLine()
                        .bake();
                createCategory("common_rock")
                        .addItems(CommonStoneBuildingSet::addCommonRock)
                        .bake();
                createCategory("textile_works")
                        .addItems(WoolRegistrySet::addWools)
                        .addItems(SoulwovenBannerBlockItem::addBannerPatterns)
                        .bake();
                createCategory("ether")
                        .addItems(
                                BuildingBlocks.ETHER,
                                BuildingBlocks.ETHER_CANDLE,
                                BuildingBlocks.ETHER_TORCH,
                                BuildingBlocks.ETHER_BRAZIER,
                                BuildingBlocks.ETHER_CRESSET
                        ).nextLine()
                        .addItems(
                                BuildingBlocks.IRIDESCENT_ETHER,
                                BuildingBlocks.IRIDESCENT_ETHER_CANDLE,
                                BuildingBlocks.IRIDESCENT_ETHER_TORCH,
                                BuildingBlocks.IRIDESCENT_ETHER_BRAZIER,
                                BuildingBlocks.IRIDESCENT_ETHER_CRESSET
                        )
                        .bake();
                createCategory("runewood")
                        .addItems(
                                Materials.RUNIC_SAP_BOTTLE,
                                Materials.RUNIC_SAPBALL,
                                BuildingBlocks.RUNEWOOD_SAPLING,
                                BuildingBlocks.RUNEWOOD_LEAVES,
                                BuildingBlocks.HANGING_RUNEWOOD_LEAVES,
                                BuildingBlocks.AZURE_RUNEWOOD_SAPLING,
                                BuildingBlocks.AZURE_RUNEWOOD_LEAVES,
                                BuildingBlocks.HANGING_AZURE_RUNEWOOD_LEAVES
                        ).nextLine()
                        .addItems(BuildingBlocks.RUNEWOOD_SET::addToCreativeTab)
                        .addItems(
                                BuildingBlocks.RUNEWOOD_BOAT,
                                BuildingBlocks.RUNEWOOD_CHEST_BOAT
                        )
                        .bake();
                createCategory("soulwood")
                        .addItems(
                                Materials.AZOIC_SAP_BOTTLE,
                                Materials.AZOIC_SAPBALL,
                                BuildingBlocks.SOULWOOD_SAPLING,
                                BuildingBlocks.SOULWOOD_LEAVES,
                                BuildingBlocks.HANGING_SOULWOOD_LEAVES
                        ).nextLine()
                        .addItems(BuildingBlocks.SOULWOOD_SET::addToCreativeTab)
                        .addItems(
                                BuildingBlocks.SOULWOOD_BOAT,
                                BuildingBlocks.SOULWOOD_CHEST_BOAT
                        )
                        .bake();
                createCategory("blight")
                        .addItems(
                                BuildingBlocks.BLIGHTED_SOULWOOD,
                                Blight.COLUMNAR_BLIGHT,
                                Blight.BLIGHTED_EARTH,
                                Blight.BLIGHT,
                                Blight.BLIGHTED_GUNK,
                                Blight.BLIGHTPEARL,
                                Blight.BLIGHTROOT
                        ).nextLine()
                        .addItems(
                                Blight.SCARSTONE,
                                Blight.LARGE_STRANGE_CRYSTAL,
                                Blight.STRANGE_CRYSTAL,
                                Blight.STRANGEROOT
                        )
                        .bake();

                createCategory("tainted_rock")
                        .addItems(BuildingBlocks.TAINTED_ROCK_SET::addToCreativeTab)
                        .bake();
                createCategory("twisted_rock")
                        .addItems(BuildingBlocks.TWISTED_ROCK_SET::addToCreativeTab)
                        .bake();
        }
}