package com.sammy.malum.registry.common.util;

import com.sammy.malum.common.block.curiosities.poppetry.PoppetPillowBlock;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.block.properties.MalumBlockProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import team.lodestar.lodestone.modules.toolkit.block.BlockBlockItemHolder;
import team.lodestar.lodestone.modules.toolkit.creative_tab.CreativeTabCategoryBuilder;

public class PoppetRegistrySet extends MalumRegistrySet {

    private final DyedVariantBundle<BlockBlockItemHolder<Block, BlockItem>> pillow;

    public PoppetRegistrySet(String id) {
        super(id);
        var pillowProperties = MalumBlockProperties.POPPETRY_IMPLEMENT();
        pillow = new DyedVariantBundle<>(name("%c_%s_poppet_pillow"), (s, c) -> MalumContent.registerBlock(s, () -> new PoppetPillowBlock<>(pillowProperties, this, c)));
    }

    public DyedVariantBundle<BlockBlockItemHolder<Block, BlockItem>> getPillow() {
        return pillow;
    }

    public void addToCreativeTab(CreativeTabCategoryBuilder builder) {
        builder.nextLine().addItems(b -> pillow.addToCreativeTab(b::addItems));
    }
}