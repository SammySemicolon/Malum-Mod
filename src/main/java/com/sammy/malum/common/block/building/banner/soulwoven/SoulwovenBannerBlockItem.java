package com.sammy.malum.common.block.building.banner.soulwoven;

import com.sammy.malum.common.data.component.*;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.*;
import team.lodestar.lodestone.modules.toolkit.creative_tab.CreativeTabCategoryBuilder;
import team.lodestar.lodestone.modules.toolkit.item.LodestoneItemProperties;

import java.util.*;

import static com.sammy.malum.common.data.component.SoulwovenBannerPatternDataComponent.*;
import static net.minecraft.world.item.CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;

@SuppressWarnings("NullableProblems")
public class SoulwovenBannerBlockItem extends BlockItem {

    public SoulwovenBannerBlockItem(Block block, LodestoneItemProperties properties) {
        super(block, properties.component(MalumDataComponents.SOULWOVEN_BANNER_PATTERN, DEFAULT));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        var pattern = stack.get(MalumDataComponents.SOULWOVEN_BANNER_PATTERN);
        if (pattern != null && !pattern.equals(DEFAULT)) {
            tooltipComponents.add(Component.translatable(pattern.translationKey()).withStyle(ChatFormatting.GRAY));
        }
    }

    public static float getBannerPattern(ItemStack stack) {
        var pattern = stack.getOrDefault(MalumDataComponents.SOULWOVEN_BANNER_PATTERN, DEFAULT);
        return REGISTERED_PATTERNS.contains(pattern) ? REGISTERED_PATTERNS.indexOf(pattern) : 0;
    }

    public static void addBannerVariantsToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        var source = MalumContent.BuildingBlocks.SOULWOVEN_BANNER.getDefaultInstance();
        if (event.getParentEntries().contains(source)) {
            var patterns = new ArrayList<>(REGISTERED_PATTERNS);
            patterns.remove(DEFAULT);
            patterns.remove(COLORFUL_WORLD);
            Collections.reverse(patterns);

            tryAddBannerVariant(event, COLORFUL_WORLD, true);
            for (SoulwovenBannerPatternDataComponent pattern : patterns) {
                tryAddBannerVariant(event, pattern, false);
            }
        }
    }

    public static void addBannerPatterns(CreativeTabCategoryBuilder builder) {
        var patterns = new ArrayList<>(REGISTERED_PATTERNS);
        Collections.reverse(patterns);
        for (SoulwovenBannerPatternDataComponent pattern : patterns) {
            builder.addItemStack(pattern.getDefaultStack());
        }
    }

    public static void tryAddBannerVariant(BuildCreativeModeTabContentsEvent event, SoulwovenBannerPatternDataComponent pattern, boolean before) {
        var source = MalumContent.BuildingBlocks.SOULWOVEN_BANNER.getDefaultInstance();
        var stack = pattern.getDefaultStack();
        if (!event.getParentEntries().contains(stack)) {
            if (before) {
                event.insertBefore(source, stack, PARENT_AND_SEARCH_TABS);
            }
            else {
                event.insertAfter(source, stack, PARENT_AND_SEARCH_TABS);
            }
        }
    }
}