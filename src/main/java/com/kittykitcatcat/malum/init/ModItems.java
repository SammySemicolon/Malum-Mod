package com.kittykitcatcat.malum.init;

import com.google.common.base.Preconditions;
import com.kittykitcatcat.malum.MalumMod;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

import java.util.function.Supplier;

import static com.kittykitcatcat.malum.MalumMod.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems
{
    static final ItemGroup MALUM_MOD_GROUP = new ModItemGroup(MalumMod.MODID, () -> new ItemStack(ModItems.evil_pumpkin));
    public static final class ModItemGroup extends ItemGroup
    {
        @Nonnull
        private final Supplier<ItemStack> iconSupplier;
        ModItemGroup(@Nonnull final String name, @Nonnull final Supplier<ItemStack> iconSupplier)
        {
            super(name);
            this.iconSupplier = iconSupplier;
        }
        @Override
        @Nonnull
        public ItemStack createIcon()
        {
            return iconSupplier.get();
        }
    }

    public static Item wooden_planks;
    public static Item wooden_planks_slab;
    public static Item wooden_planks_stairs;
    public static Item wooden_beam;
    public static Item wooden_casing;
    public static Item refined_bricks;
    public static Item refined_bricks_slab;
    public static Item refined_bricks_stairs;
    public static Item refined_pathway;
    public static Item refined_pathway_slab;
    public static Item refined_pathway_stairs;
    public static Item refined_smooth_stone;
    public static Item refined_smooth_stone_slab;
    public static Item refined_smooth_stone_stairs;
    public static Item evil_pumpkin;
    public static Item lit_evil_pumpkin;
    public static Item block_of_flesh;
    public static Item spirit_leaves;
    public static Item spirit_log;
    public static Item spirit_sapling;
    public static Item spirit_planks;
    public static Item spirit_planks_slab;
    public static Item spirit_planks_stairs;
    public static Item refined_glowstone_block;
    public static Item refined_glowstone_lamp;
    public static Item smooth_stone_stairs;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();
        registry.registerAll(
                wooden_planks = setup(new BlockItem(ModBlocks.wooden_planks, new Item.Properties().group(MALUM_MOD_GROUP)), "wooden_planks"),
                wooden_planks_slab = setup(new BlockItem(ModBlocks.wooden_planks_slab, new Item.Properties().group(MALUM_MOD_GROUP)), "wooden_planks_slab"),
                wooden_planks_stairs = setup(new BlockItem(ModBlocks.wooden_planks_stairs, new Item.Properties().group(MALUM_MOD_GROUP)), "wooden_planks_stairs"),

                wooden_beam = setup(new BlockItem(ModBlocks.wooden_beam, new Item.Properties().group(MALUM_MOD_GROUP)), "wooden_beam"),
                wooden_casing = setup(new BlockItem(ModBlocks.wooden_casing, new Item.Properties().group(MALUM_MOD_GROUP)), "wooden_casing"),

                refined_bricks = setup(new BlockItem(ModBlocks.refined_bricks, new Item.Properties().group(MALUM_MOD_GROUP)), "refined_bricks"),
                refined_bricks_slab = setup(new BlockItem(ModBlocks.refined_bricks_slab, new Item.Properties().group(MALUM_MOD_GROUP)), "refined_bricks_slab"),
                refined_bricks_stairs = setup(new BlockItem(ModBlocks.refined_bricks_stairs, new Item.Properties().group(MALUM_MOD_GROUP)), "refined_bricks_stairs"),

                spirit_planks = setup(new BlockItem(ModBlocks.spirit_planks, new Item.Properties().group(MALUM_MOD_GROUP)), "spirit_planks"),
                spirit_planks_slab = setup(new BlockItem(ModBlocks.spirit_planks_slab, new Item.Properties().group(MALUM_MOD_GROUP)), "spirit_planks_slab"),
                spirit_planks_stairs = setup(new BlockItem(ModBlocks.spirit_planks_stairs, new Item.Properties().group(MALUM_MOD_GROUP)), "spirit_planks_stairs"),

                refined_pathway = setup(new BlockItem(ModBlocks.refined_pathway, new Item.Properties().group(MALUM_MOD_GROUP)), "refined_pathway"),
                refined_pathway_slab = setup(new BlockItem(ModBlocks.refined_pathway_slab, new Item.Properties().group(MALUM_MOD_GROUP)), "refined_pathway_slab"),
                refined_pathway_stairs = setup(new BlockItem(ModBlocks.refined_pathway_stairs, new Item.Properties().group(MALUM_MOD_GROUP)), "refined_pathway_stairs"),

                refined_smooth_stone = setup(new BlockItem(ModBlocks.refined_smooth_stone, new Item.Properties().group(MALUM_MOD_GROUP)), "refined_smooth_stone"),
                refined_smooth_stone_slab = setup(new BlockItem(ModBlocks.refined_smooth_stone_slab, new Item.Properties().group(MALUM_MOD_GROUP)), "refined_smooth_stone_slab"),
                refined_smooth_stone_stairs = setup(new BlockItem(ModBlocks.refined_smooth_stone_stairs, new Item.Properties().group(MALUM_MOD_GROUP)), "refined_smooth_stone_stairs"),

                evil_pumpkin = setup(new BlockItem(ModBlocks.evil_pumpkin, new Item.Properties().group(MALUM_MOD_GROUP)), "evil_pumpkin"),
                lit_evil_pumpkin = setup(new BlockItem(ModBlocks.lit_evil_pumpkin, new Item.Properties().group(MALUM_MOD_GROUP)), "lit_evil_pumpkin"),
                block_of_flesh = setup(new BlockItem(ModBlocks.block_of_flesh, new Item.Properties().group(MALUM_MOD_GROUP)), "block_of_flesh"),

                spirit_leaves = setup(new BlockItem(ModBlocks.spirit_leaves, new Item.Properties().group(MALUM_MOD_GROUP)), "spirit_leaves"),
                spirit_log = setup(new BlockItem(ModBlocks.spirit_log, new Item.Properties().group(MALUM_MOD_GROUP)), "spirit_log"),
                spirit_sapling = setup(new BlockItem(ModBlocks.spirit_sapling, new Item.Properties().group(MALUM_MOD_GROUP)), "spirit_sapling"),

                refined_glowstone_block = setup(new BlockItem(ModBlocks.refined_glowstone_block, new Item.Properties().group(MALUM_MOD_GROUP)), "refined_glowstone_block"),
                refined_glowstone_lamp = setup(new BlockItem(ModBlocks.refined_glowstone_lamp, new Item.Properties().group(MALUM_MOD_GROUP)), "refined_glowstone_lamp")
        );
    }

    @Nonnull
    private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final String name)
    {
        Preconditions.checkNotNull(name, "Name to assign to entry cannot be null!");
        return setup(entry, new ResourceLocation(MODID, name));
    }

    @Nonnull
    private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName)
    {
        Preconditions.checkNotNull(entry, "Entry cannot be null!");
        Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
        entry.setRegistryName(registryName);
        return entry;
    }
}
