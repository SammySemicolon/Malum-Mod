package com.kittykitcatcat.malum.init;

import com.google.common.base.Preconditions;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.items.*;
import com.kittykitcatcat.malum.items.armor.ItemArmorTier1;
import com.kittykitcatcat.malum.items.armor.ItemArmorTier2;
import com.kittykitcatcat.malum.items.curios.CrossbowReloadCurioItem;
import com.kittykitcatcat.malum.items.curios.PhantomWingsCurioItem;
import com.kittykitcatcat.malum.items.curios.ShulkerOnHealCurioItem;
import com.kittykitcatcat.malum.items.tools.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

import java.util.function.Supplier;

import static com.kittykitcatcat.malum.MalumMod.MODID;
import static com.kittykitcatcat.malum.init.ModItemTiers.TIER1_ITEM;
import static com.kittykitcatcat.malum.init.ModItemTiers.TIER2_ITEM;

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
    //MATERIALS
    public static Item blighted_bonemeal;
    public static Item spirit_charcoal;
    public static Item spirit_stone;
    public static Item dark_spirit_stone;
    public static Item spirited_steel_ingot;
    public static Item spirited_steel_nugget;
    public static Item transmissive_ingot;
    public static Item transmissive_nugget;
    public static Item enchanted_quartz;
    public static Item vacant_gemstone;
    public static Item stygian_pearl;
    public static Item runic_ash;
    public static Item royal_steel_ingot;
    public static Item arcane_apparatus;
    public static Item cursed_nebulous;
    public static Item stellar_apparatus;

    public static Item spiritwood_stave;
    public static Item block_transmutation_tool;
    public static Item ender_artifact;
    public static Item ender_stave;
    //TOOLS

    public static Item royal_steel_hoe;
    public static Item royal_steel_axe;
    public static Item royal_steel_sword;
    public static Item royal_steel_shovel;
    public static Item royal_steel_pickaxe;
    public static Item royal_steel_shoes;
    public static Item royal_steel_leggings;
    public static Item royal_steel_chestplate;
    public static Item royal_steel_helm;

    public static Item soul_steel_hoe;
    public static Item soul_steel_axe;
    public static Item soul_steel_sword;
    public static Item soul_steel_shovel;
    public static Item soul_steel_pickaxe;
    public static Item soul_steel_shoes;
    public static Item soul_steel_leggings;
    public static Item soul_steel_chestplate;
    public static Item soul_steel_helm;

    public static Item ultimate_weapon;
    //CURIOS
    public static Item phantom_wings_curio;
    public static Item crossbow_reload_curio;
    public static Item shulker_on_heal_curio;
    //FUNCTIONAL BLOCKS

    public static Item spirit_furnace;
    public static Item spirit_bellows;
    public static Item soul_jar;
    public static Item soul_binder;
    public static Item ritual_anchor;
    //BLOCKS
    public static Item blighted_dirt;
    public static Item blighted_grass;
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

    //OTHER

    public static Item jei_spirit;

    static Item.Properties basic_properties = new Item.Properties().group(MALUM_MOD_GROUP).maxStackSize(64);
    static Item.Properties tool_properties = new Item.Properties().group(MALUM_MOD_GROUP).maxStackSize(1);
    static Item.Properties hidden_properties = new Item.Properties().maxStackSize(1);
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();
        registry.registerAll(

                blighted_bonemeal = setup(new BlightedBonemealItem(basic_properties), "blighted_bonemeal"),
                spirit_charcoal = setup(new Item(basic_properties), "spirit_charcoal"),
                spirit_stone = setup(new BlockItem(ModBlocks.spirit_stone, basic_properties), "spirit_stone"),
                dark_spirit_stone = setup(new BlockItem(ModBlocks.dark_spirit_stone, basic_properties), "dark_spirit_stone"),
                spirited_steel_ingot = setup(new Item(basic_properties), "spirited_steel_ingot"),
                spirited_steel_nugget = setup(new Item(basic_properties), "spirited_steel_nugget"),
                transmissive_ingot = setup(new Item(basic_properties), "transmissive_ingot"),
                transmissive_nugget = setup(new Item(basic_properties), "transmissive_nugget"),
                enchanted_quartz = setup(new Item(basic_properties), "enchanted_quartz"),
                vacant_gemstone = setup(new Item(basic_properties), "vacant_gemstone"),
                runic_ash = setup(new Item(basic_properties), "runic_ash"),
                stygian_pearl = setup(new Item(basic_properties), "stygian_pearl"),
                royal_steel_ingot = setup(new Item(basic_properties), "royal_steel_ingot"),
                arcane_apparatus = setup(new Item(basic_properties), "arcane_apparatus"),
                cursed_nebulous = setup(new Item(basic_properties), "cursed_nebulous"),
                stellar_apparatus = setup(new Item(basic_properties), "stellar_apparatus"),

                spiritwood_stave = setup(new SpiritwoodStaveItem(tool_properties), "spiritwood_stave"),
                block_transmutation_tool = setup(new TransmutationGemItem(tool_properties), "block_transmutation_tool"),
                ender_artifact = setup(new EnderArtifactItem(tool_properties), "ender_artifact"),
                ender_stave = setup(new EnderStaveItem(tool_properties), "ender_stave"),

                royal_steel_hoe = setup(new ModHoeItem(TIER1_ITEM, 0, tool_properties), "royal_steel_hoe"),
                royal_steel_axe = setup(new ModAxeItem(TIER1_ITEM, 0, 0, tool_properties), "royal_steel_axe"),
                royal_steel_sword = setup(new ModSwordItem(TIER1_ITEM, 0, 0, tool_properties), "royal_steel_sword"),
                royal_steel_shovel = setup(new ModShovelItem(TIER1_ITEM, 0, 0, tool_properties), "royal_steel_shovel"),
                royal_steel_pickaxe = setup(new ModPickaxeItem(TIER1_ITEM, 0, 0, tool_properties), "royal_steel_pickaxe"),

                royal_steel_shoes = setup(new ItemArmorTier1(ModItemTiers.TIER1_ARMOR, EquipmentSlotType.FEET, tool_properties), "royal_steel_shoes"),
                royal_steel_leggings = setup(new ItemArmorTier1(ModItemTiers.TIER1_ARMOR, EquipmentSlotType.LEGS, tool_properties), "royal_steel_leggings"),
                royal_steel_chestplate = setup(new ItemArmorTier1(ModItemTiers.TIER1_ARMOR, EquipmentSlotType.CHEST, tool_properties), "royal_steel_chestplate"),
                royal_steel_helm = setup(new ItemArmorTier1(ModItemTiers.TIER1_ARMOR, EquipmentSlotType.HEAD, tool_properties), "royal_steel_helm"),

                soul_steel_hoe = setup(new ModHoeItem(TIER2_ITEM, 0, tool_properties), "soul_steel_hoe"),
                soul_steel_axe = setup(new ModAxeItem(TIER2_ITEM, 0, 0, tool_properties), "soul_steel_axe"),
                soul_steel_sword = setup(new ModSwordItem(TIER2_ITEM, 0, 0, tool_properties), "soul_steel_sword"),
                soul_steel_shovel = setup(new ModShovelItem(TIER2_ITEM, 0, 0, tool_properties), "soul_steel_shovel"),
                soul_steel_pickaxe = setup(new ModPickaxeItem(TIER2_ITEM, 0, 0, tool_properties), "soul_steel_pickaxe"),

                soul_steel_shoes = setup(new ItemArmorTier2(ModItemTiers.TIER2_ARMOR, EquipmentSlotType.FEET, tool_properties), "soul_steel_shoes"),
                soul_steel_leggings = setup(new ItemArmorTier2(ModItemTiers.TIER2_ARMOR, EquipmentSlotType.LEGS, tool_properties), "soul_steel_leggings"),
                soul_steel_chestplate = setup(new ItemArmorTier2(ModItemTiers.TIER2_ARMOR, EquipmentSlotType.CHEST, tool_properties), "soul_steel_chestplate"),
                soul_steel_helm = setup(new ItemArmorTier2(ModItemTiers.TIER2_ARMOR, EquipmentSlotType.HEAD, tool_properties), "soul_steel_helm"),

                ultimate_weapon = setup(new BonkItem(ItemTier.DIAMOND, 0, 0.4f, tool_properties), "ultimate_weapon"),

                phantom_wings_curio = setup(new PhantomWingsCurioItem(tool_properties), "phantom_wings_curio"),
                crossbow_reload_curio = setup(new CrossbowReloadCurioItem(tool_properties), "crossbow_reload_curio"),
                shulker_on_heal_curio = setup(new ShulkerOnHealCurioItem(tool_properties), "shulker_on_heal_curio"),

                spirit_furnace = setup(new BlockItem(ModBlocks.spirit_furnace, basic_properties), "spirit_furnace"),
                spirit_bellows = setup(new BlockItem(ModBlocks.spirit_bellows, basic_properties), "spirit_bellows"),
                soul_jar = setup(new BlockItem(ModBlocks.soul_jar, basic_properties), "soul_jar"),
                ritual_anchor = setup(new BlockItem(ModBlocks.ritual_anchor, basic_properties), "ritual_anchor"),
                soul_binder = setup(new BlockItem(ModBlocks.soul_binder, basic_properties), "soul_binder"),

                blighted_dirt = setup(new BlockItem(ModBlocks.blighted_dirt, basic_properties), "blighted_dirt"),
                blighted_grass = setup(new BlockItem(ModBlocks.blighted_grass, basic_properties), "blighted_grass"),

                wooden_planks = setup(new BlockItem(ModBlocks.wooden_planks, basic_properties), "wooden_planks"),
                wooden_planks_slab = setup(new BlockItem(ModBlocks.wooden_planks_slab, basic_properties), "wooden_planks_slab"),
                wooden_planks_stairs = setup(new BlockItem(ModBlocks.wooden_planks_stairs, basic_properties), "wooden_planks_stairs"),

                wooden_beam = setup(new BlockItem(ModBlocks.wooden_beam, basic_properties), "wooden_beam"),
                wooden_casing = setup(new BlockItem(ModBlocks.wooden_casing, basic_properties), "wooden_casing"),

                refined_bricks = setup(new BlockItem(ModBlocks.refined_bricks, basic_properties), "refined_bricks"),
                refined_bricks_slab = setup(new BlockItem(ModBlocks.refined_bricks_slab, basic_properties), "refined_bricks_slab"),
                refined_bricks_stairs = setup(new BlockItem(ModBlocks.refined_bricks_stairs, basic_properties), "refined_bricks_stairs"),

                spirit_planks = setup(new BlockItem(ModBlocks.spirit_planks, basic_properties), "spirit_planks"),
                spirit_planks_slab = setup(new BlockItem(ModBlocks.spirit_planks_slab, basic_properties), "spirit_planks_slab"),
                spirit_planks_stairs = setup(new BlockItem(ModBlocks.spirit_planks_stairs, basic_properties), "spirit_planks_stairs"),

                refined_pathway = setup(new BlockItem(ModBlocks.refined_pathway, basic_properties), "refined_pathway"),
                refined_pathway_slab = setup(new BlockItem(ModBlocks.refined_pathway_slab, basic_properties), "refined_pathway_slab"),
                refined_pathway_stairs = setup(new BlockItem(ModBlocks.refined_pathway_stairs, basic_properties), "refined_pathway_stairs"),

                refined_smooth_stone = setup(new BlockItem(ModBlocks.refined_smooth_stone, basic_properties), "refined_smooth_stone"),
                refined_smooth_stone_slab = setup(new BlockItem(ModBlocks.refined_smooth_stone_slab, basic_properties), "refined_smooth_stone_slab"),
                refined_smooth_stone_stairs = setup(new BlockItem(ModBlocks.refined_smooth_stone_stairs, basic_properties), "refined_smooth_stone_stairs"),

                evil_pumpkin = setup(new BlockItem(ModBlocks.evil_pumpkin, basic_properties), "evil_pumpkin"),
                lit_evil_pumpkin = setup(new BlockItem(ModBlocks.lit_evil_pumpkin, basic_properties), "lit_evil_pumpkin"),
                block_of_flesh = setup(new BlockItem(ModBlocks.block_of_flesh, basic_properties), "block_of_flesh"),

                spirit_leaves = setup(new BlockItem(ModBlocks.spirit_leaves, basic_properties), "spirit_leaves"),
                spirit_log = setup(new BlockItem(ModBlocks.spirit_log, basic_properties), "spirit_log"),
                spirit_sapling = setup(new BlockItem(ModBlocks.spirit_sapling, basic_properties), "spirit_sapling"),

                refined_glowstone_block = setup(new BlockItem(ModBlocks.refined_glowstone_block, basic_properties), "refined_glowstone_block"),
                refined_glowstone_lamp = setup(new BlockItem(ModBlocks.refined_glowstone_lamp, basic_properties), "refined_glowstone_lamp"),

                smooth_stone_stairs = setup(new BlockItem(ModBlocks.smooth_stone_stairs, basic_properties), "smooth_stone_stairs"),

                jei_spirit = setup(new JeiSpiritItem(hidden_properties), "jei_spirit")
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
