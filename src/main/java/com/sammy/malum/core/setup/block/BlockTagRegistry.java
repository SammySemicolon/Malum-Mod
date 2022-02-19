package com.sammy.malum.core.setup.block;

import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Supplier;

public class BlockTagRegistry {

    public static final Tag.Named<Block> TRAY_HEAT_SOURCES = modTag("farmersdelight:tray_heat_sources");
    public static final Tag.Named<Block> HEAT_SOURCES = modTag("farmersdelight:heat_sources");


    private static Tags.IOptionalNamedTag<Block> forgeTag(String name, @Nullable Set<Supplier<Block>> defaults) {
        return BlockTags.createOptional(new ResourceLocation("forge", name), defaults);
    }

    private static Tags.IOptionalNamedTag<Block> forgeTag(String name) {
        return forgeTag(name, null);
    }

    private static Tag.Named<Block> tag(String name, @Nullable Set<Supplier<Block>> defaults) {
        return BlockTags.createOptional(DataHelper.prefix(name), defaults);
    }

    private static Tag.Named<Block> tag(String name) {
        return tag(name, null);
    }

    private static Tag.Named<Block> modTag(String name, @Nullable Set<Supplier<Block>> defaults) {
        return BlockTags.createOptional(new ResourceLocation(name), defaults);
    }

    private static Tag.Named<Block> modTag(String name) {
        return modTag(name, null);
    }
}