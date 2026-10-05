package com.sammy.malum.common.creativetab;

import com.sammy.malum.registry.common.MalumContent;
import team.lodestar.lodestone.modules.toolkit.creative_tab.CategorizedBuilder;

public class MalumDungeonRegaliaTab extends AbstractMalumCreativeTab {

        public static final String DUNGEON_REGALIA = "dungeon_regalia";
        public static final String DUNGEON_ODDITIES = "dungeon_distortions";

        public MalumDungeonRegaliaTab(CategorizedBuilder categorizedBuilder) {
                super(categorizedBuilder);
        }

        public void buildCategories() {

                createCategory(DUNGEON_REGALIA)
                        .addItems(
                                MalumContent.Enscription.SHAPED_SLAB
                        ).nextLine()
                        .addItems(
                                MalumContent.Enscription.IRON_CROWN, MalumContent.Enscription.MEDITATING_EFFIGY
                        )
                        .bake();
                createCategory(DUNGEON_ODDITIES)
                        .addItems(
                                MalumContent.Enscription.WRITHING_FLESH, MalumContent.Enscription.COLUMNAR_FLESH, MalumContent.Enscription.FLESHBULB
                        )
                        .bake();
        }
}