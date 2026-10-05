package com.sammy.malum.registry.common.util;

import com.google.common.collect.ImmutableMap;
import com.sammy.malum.common.block.curiosities.poppetry.PoppetPillowBlock;
import com.sammy.malum.registry.common.block.properties.MalumBlockProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import team.lodestar.lodestone.modules.toolkit.block.BlockBlockItemHolder;
import team.lodestar.lodestone.modules.toolkit.creative_tab.CreativeTabCategoryBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.function.*;

import static com.sammy.malum.registry.common.MalumContent.Materials;
import static com.sammy.malum.registry.common.MalumContent.registerBlock;

public class DyedVariantBundle<T> extends MalumRegistrySet {

    protected static final List<DyeColor> ORDERED = List.of(
            DyeColor.WHITE,
            DyeColor.LIGHT_GRAY,
            DyeColor.GRAY,
            DyeColor.BLACK,
            DyeColor.BROWN,
            DyeColor.RED,
            DyeColor.ORANGE,
            DyeColor.YELLOW,
            DyeColor.LIME,
            DyeColor.GREEN,
            DyeColor.CYAN,
            DyeColor.LIGHT_BLUE,
            DyeColor.BLUE,
            DyeColor.PURPLE,
            DyeColor.MAGENTA,
            DyeColor.PINK
    );
    protected final ImmutableMap<DyeColor, T> variants;

    public DyedVariantBundle(String id, BiFunction<String, DyeColor, T> registry) {
        super(id.replace("%c_", ""));
        var builder = ImmutableMap.<DyeColor, T>builder();
        for (DyeColor color : DyeColor.values()) {
            String entryId = id.replace("%c", color.getName());
            T object = registry.apply(entryId, color);
            builder.put(color, object);
        }
        variants = builder.build();
    }

    public void addToCreativeTab(Consumer<T> acceptor) {
        for (DyeColor color : ORDERED) {
            acceptor.accept(variants.get(color));
        }
    }

    public ImmutableMap<DyeColor, T> getVariants() {
        return variants;
    }

    public T getVariant(DyeColor color) {
        return variants.get(color);
    }

    public void forEachVariant(BiConsumer<DyeColor, T> consumer) {
        for (DyeColor dyeColor : variants.keySet()) {
            consumer.accept(dyeColor, variants.get(dyeColor));
        }
    }
}