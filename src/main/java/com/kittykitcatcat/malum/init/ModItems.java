package com.kittykitcatcat.malum.init;

import com.google.common.base.Preconditions;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.items.*;
import com.kittykitcatcat.malum.items.armor.ItemSpiritHunterArmor;
import com.kittykitcatcat.malum.items.armor.ItemSpiritedSteelBattleArmor;
import com.kittykitcatcat.malum.items.armor.ItemUmbraSteelBattleArmor;
import com.kittykitcatcat.malum.items.curios.CurioEnchantedLectern;
import com.kittykitcatcat.malum.items.curios.CurioSpiritwoodNecklace;
import com.kittykitcatcat.malum.items.curios.CurioVacantAegis;
import com.kittykitcatcat.malum.items.shield.ItemTileEntityRenderer;
import com.kittykitcatcat.malum.items.shield.ModShieldItem;
import com.kittykitcatcat.malum.items.staves.CreativeStave;
import com.kittykitcatcat.malum.items.staves.SpiritwoodStave;
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
import java.util.Collections;
import java.util.function.Supplier;

import static com.kittykitcatcat.malum.MalumMod.MODID;
import static com.kittykitcatcat.malum.init.ModItemTiers.SPIRITED_STEEL_ITEM;
import static com.kittykitcatcat.malum.init.ModItemTiers.UMBRAL_ALLOY_ITEM;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems
{
    static final ItemGroup MALUM_MOD_GROUP = new ModItemGroup(MalumMod.MODID, () -> new ItemStack(ModItems.spirit_furnace));
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
    public static Item spirit_charcoal;
    public static Item spirit_stone;
    public static Item dark_spirit_stone;
    public static Item spirit_silk;
    public static Item spirit_fabric;
    public static Item evil_leather;
    public static Item spirited_steel_ingot;
    public static Item spirited_steel_nugget;
    public static Item transmissive_ingot;
    public static Item transmissive_nugget;
    public static Item enchanted_quartz;
    public static Item vacant_gemstone;
    public static Item stygian_pearl;
    public static Item stygian_shard;
    public static Item runic_ash;
    public static Item soul_steel_ingot;
    public static Item soul_steel_nugget;
    public static Item arcane_apparatus;
    public static Item cursed_nebulous;
    public static Item stellar_apparatus;

    public static Item spiritwood_stave;
    public static Item spirit_vault;
    public static Item spirit_capacitor;
    public static Item ender_artifact;
    //TOOLS
    public static Item spirited_steel_battle_shoes;
    public static Item spirited_steel_battle_leggings;
    public static Item spirited_steel_battle_chestplate;
    public static Item spirited_steel_battle_helm;

    public static Item spirit_hunter_shoes;
    public static Item spirit_hunter_leggings;
    public static Item spirit_hunter_chestplate;
    public static Item spirit_hunter_helm;

    public static Item spirited_steel_buster_sword;
    public static Item spirited_steel_excavator;

    public static Item soul_steel_hoe;
    public static Item soul_steel_axe;
    public static Item soul_steel_sword;
    public static Item soul_steel_shovel;
    public static Item soul_steel_pickaxe;
    public static Item soul_steel_shoes;
    public static Item soul_steel_leggings;
    public static Item soul_steel_chestplate;
    public static Item soul_steel_helm;

    public static Item vacant_rapier;
    public static Item ultimate_weapon;
    public static Item bow_of_lost_souls;
    public static Item skull_bulwark;
    //CURIOS
    public static Item spiritwood_bark_necklace;
    public static Item enchanted_lectern;
    public static Item vacant_aegis;

    //FUNCTIONAL BLOCKS
    public static Item spirit_jar;

    public static Item spirit_furnace;
    //BLOCKS
    public static Item spirit_stone_brick;
    public static Item dark_spirit_stone_brick;
    public static Item patterned_spirit_stone;
    public static Item patterned_dark_spirit_stone;
    public static Item smooth_spirit_stone;
    public static Item smooth_dark_spirit_stone;
    public static Item spirit_stone_pillar;
    public static Item dark_spirit_stone_pillar;

    public static Item spirit_stone_slab;
    public static Item dark_spirit_stone_slab;

    public static Item spirit_stone_brick_slab;
    public static Item dark_spirit_stone_brick_slab;
    public static Item patterned_spirit_stone_slab;
    public static Item patterned_dark_spirit_stone_slab;
    public static Item smooth_spirit_stone_slab;
    public static Item smooth_dark_spirit_stone_slab;

    public static Item spirit_stone_stairs;
    public static Item dark_spirit_stone_stairs;

    public static Item spirit_stone_brick_stairs;
    public static Item dark_spirit_stone_brick_stairs;
    public static Item patterned_spirit_stone_stairs;
    public static Item patterned_dark_spirit_stone_stairs;
    public static Item smooth_spirit_stone_stairs;
    public static Item smooth_dark_spirit_stone_stairs;

    public static Item block_of_flesh;
    public static Item spirit_leaves;
    public static Item spirit_log;
    public static Item spirit_sapling;
    public static Item spirit_planks;
    public static Item spirit_planks_slab;
    public static Item spirit_planks_stairs;

    //OTHER

    public static Item jei_spirit;

    //CREATIVE
    public static Item creative_spiritwood_stave;

    static Item.Properties basic_properties = new Item.Properties().group(MALUM_MOD_GROUP).maxStackSize(64);
    static Item.Properties tool_properties = new Item.Properties().group(MALUM_MOD_GROUP).maxStackSize(1);
    static Item.Properties hidden_properties = new Item.Properties().maxStackSize(1);
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        final IForgeRegistry<Item> registry = event.getRegistry();
        registry.registerAll(
                spirit_charcoal = setup(new Item(basic_properties), "spirit_charcoal"),
                spirit_stone = setup(new BlockItem(ModBlocks.spirit_stone, basic_properties), "spirit_stone"),
                dark_spirit_stone = setup(new BlockItem(ModBlocks.dark_spirit_stone, basic_properties), "dark_spirit_stone"),
                spirit_silk = setup(new Item(basic_properties), "spirit_silk"),
                spirit_fabric = setup(new Item(basic_properties), "spirit_fabric"),
                evil_leather = setup(new Item(basic_properties), "evil_leather"),
                spirited_steel_ingot = setup(new Item(basic_properties), "spirited_steel_ingot"),
                spirited_steel_nugget = setup(new Item(basic_properties), "spirited_steel_nugget"),
                transmissive_ingot = setup(new Item(basic_properties), "transmissive_ingot"),
                transmissive_nugget = setup(new Item(basic_properties), "transmissive_nugget"),
                enchanted_quartz = setup(new Item(basic_properties), "enchanted_quartz"),
                vacant_gemstone = setup(new Item(basic_properties), "vacant_gemstone"),
                runic_ash = setup(new Item(basic_properties), "runic_ash"),
                stygian_pearl = setup(new Item(basic_properties), "stygian_pearl"),
                stygian_shard = setup(new Item(basic_properties), "stygian_shard"),
                arcane_apparatus = setup(new Item(basic_properties), "arcane_apparatus"),
                soul_steel_ingot = setup(new Item(basic_properties), "soul_steel_ingot"),
                soul_steel_nugget = setup(new Item(basic_properties), "soul_steel_nugget"),
                cursed_nebulous = setup(new Item(basic_properties), "cursed_nebulous"),
                stellar_apparatus = setup(new Item(basic_properties), "stellar_apparatus"),

                spiritwood_stave = setup(new SpiritwoodStave(tool_properties, null), "spiritwood_stave"),
                spirit_vault = setup(new SpiritVault(tool_properties), "spirit_vault"),
                spirit_capacitor = setup(new SpiritCapacitor(tool_properties), "spirit_capacitor"),
                ender_artifact = setup(new EnderArtifactItem(tool_properties), "ender_artifact"),

                spirited_steel_battle_shoes = setup(new ItemSpiritedSteelBattleArmor(ModItemTiers.SPIRITED_STEEL_ARMOR, EquipmentSlotType.FEET, tool_properties), "spirited_steel_battle_shoes"),
                spirited_steel_battle_leggings = setup(new ItemSpiritedSteelBattleArmor(ModItemTiers.SPIRITED_STEEL_ARMOR, EquipmentSlotType.LEGS, tool_properties), "spirited_steel_battle_leggings"),
                spirited_steel_battle_chestplate = setup(new ItemSpiritedSteelBattleArmor(ModItemTiers.SPIRITED_STEEL_ARMOR, EquipmentSlotType.CHEST, tool_properties), "spirited_steel_battle_chestplate"),
                spirited_steel_battle_helm = setup(new ItemSpiritedSteelBattleArmor(ModItemTiers.SPIRITED_STEEL_ARMOR, EquipmentSlotType.HEAD, tool_properties), "spirited_steel_battle_helm"),

                spirit_hunter_shoes = setup(new ItemSpiritHunterArmor(ModItemTiers.SPIRIT_HUNTER_ARMOR, EquipmentSlotType.FEET, tool_properties), "spirit_hunter_shoes"),
                spirit_hunter_leggings = setup(new ItemSpiritHunterArmor(ModItemTiers.SPIRIT_HUNTER_ARMOR, EquipmentSlotType.LEGS, tool_properties), "spirit_hunter_leggings"),
                spirit_hunter_chestplate = setup(new ItemSpiritHunterArmor(ModItemTiers.SPIRIT_HUNTER_ARMOR, EquipmentSlotType.CHEST, tool_properties), "spirit_hunter_chestplate"),
                spirit_hunter_helm = setup(new ItemSpiritHunterArmor(ModItemTiers.SPIRIT_HUNTER_ARMOR, EquipmentSlotType.HEAD, tool_properties), "spirit_hunter_helm"),

                spirited_steel_buster_sword = setup(new ModSwordItem(SPIRITED_STEEL_ITEM, 6, -0.8f, tool_properties), "spirited_steel_buster_sword"),
                spirited_steel_excavator = setup(new ModExcavatorItem( 6, -0.8f, SPIRITED_STEEL_ITEM, Collections.emptySet(), tool_properties.maxDamage(1000)), "spirited_steel_excavator"),
                enchanted_lectern = setup(new CurioEnchantedLectern(tool_properties), "enchanted_lectern"),
                vacant_aegis = setup(new CurioVacantAegis(tool_properties), "vacant_aegis"),

                soul_steel_hoe = setup(new ModHoeItem(UMBRAL_ALLOY_ITEM, 0, tool_properties), "soul_steel_hoe"),
                soul_steel_axe = setup(new ModAxeItem(UMBRAL_ALLOY_ITEM, 0, 0, tool_properties), "soul_steel_axe"),
                soul_steel_sword = setup(new ModSwordItem(UMBRAL_ALLOY_ITEM, 0, 0, tool_properties), "soul_steel_sword"),
                soul_steel_shovel = setup(new ModShovelItem(UMBRAL_ALLOY_ITEM, 0, 0, tool_properties), "soul_steel_shovel"),
                soul_steel_pickaxe = setup(new ModPickaxeItem(UMBRAL_ALLOY_ITEM, 0, 0, tool_properties), "soul_steel_pickaxe"),

                soul_steel_shoes = setup(new ItemUmbraSteelBattleArmor(ModItemTiers.UMBRAL_ALLOY_ARMOR, EquipmentSlotType.FEET, tool_properties), "soul_steel_shoes"),
                soul_steel_leggings = setup(new ItemUmbraSteelBattleArmor(ModItemTiers.UMBRAL_ALLOY_ARMOR, EquipmentSlotType.LEGS, tool_properties), "soul_steel_leggings"),
                soul_steel_chestplate = setup(new ItemUmbraSteelBattleArmor(ModItemTiers.UMBRAL_ALLOY_ARMOR, EquipmentSlotType.CHEST, tool_properties), "soul_steel_chestplate"),
                soul_steel_helm = setup(new ItemUmbraSteelBattleArmor(ModItemTiers.UMBRAL_ALLOY_ARMOR, EquipmentSlotType.HEAD, tool_properties), "soul_steel_helm"),

                vacant_rapier = setup(new VacantRapier(ItemTier.DIAMOND, -4, 1.2f, tool_properties), "vacant_rapier"),
                ultimate_weapon = setup(new BonkItem(ItemTier.DIAMOND, 0, 0.4f, tool_properties), "ultimate_weapon"),
                bow_of_lost_souls = setup(new BowofLostSouls(tool_properties.maxDamage(576)), "bow_of_lost_souls"),
                skull_bulwark = setup(new ModShieldItem(tool_properties.setISTER(() -> ItemTileEntityRenderer::new)), "skull_bulwark"),

                spiritwood_bark_necklace = setup(new CurioSpiritwoodNecklace(tool_properties), "spiritwood_bark_necklace"),

                spirit_jar = setup(new SpiritJar(ModBlocks.spirit_jar, tool_properties), "spirit_jar"),
                spirit_furnace = setup(new BlockItem(ModBlocks.spirit_furnace, basic_properties), "spirit_furnace"),

                spirit_stone_brick = setup(new BlockItem(ModBlocks.spirit_stone_brick, basic_properties), "spirit_stone_brick"),
                dark_spirit_stone_brick = setup(new BlockItem(ModBlocks.dark_spirit_stone_brick, basic_properties), "dark_spirit_stone_brick"),
                patterned_spirit_stone = setup(new BlockItem(ModBlocks.patterned_spirit_stone, basic_properties), "patterned_spirit_stone"),
                patterned_dark_spirit_stone = setup(new BlockItem(ModBlocks.patterned_dark_spirit_stone, basic_properties), "patterned_dark_spirit_stone"),
                smooth_spirit_stone = setup(new BlockItem(ModBlocks.smooth_spirit_stone, basic_properties), "smooth_spirit_stone"),
                smooth_dark_spirit_stone = setup(new BlockItem(ModBlocks.smooth_dark_spirit_stone, basic_properties), "smooth_dark_spirit_stone"),
                spirit_stone_pillar = setup(new BlockItem(ModBlocks.spirit_stone_pillar, basic_properties), "spirit_stone_pillar"),
                dark_spirit_stone_pillar = setup(new BlockItem(ModBlocks.dark_spirit_stone_pillar, basic_properties), "dark_spirit_stone_pillar"),

                spirit_stone_slab = setup(new BlockItem(ModBlocks.spirit_stone_slab, basic_properties), "spirit_stone_slab"),
                dark_spirit_stone_slab = setup(new BlockItem(ModBlocks.dark_spirit_stone_slab, basic_properties), "dark_spirit_stone_slab"),

                spirit_stone_brick_slab = setup(new BlockItem(ModBlocks.spirit_stone_brick_slab, basic_properties), "spirit_stone_brick_slab"),
                dark_spirit_stone_brick_slab = setup(new BlockItem(ModBlocks.dark_spirit_stone_brick_slab, basic_properties), "dark_spirit_stone_brick_slab"),
                patterned_spirit_stone_slab = setup(new BlockItem(ModBlocks.patterned_spirit_stone_slab, basic_properties), "patterned_spirit_stone_slab"),
                patterned_dark_spirit_stone_slab = setup(new BlockItem(ModBlocks.patterned_dark_spirit_stone_slab, basic_properties), "patterned_dark_spirit_stone_slab"),
                smooth_spirit_stone_slab = setup(new BlockItem(ModBlocks.smooth_spirit_stone_slab, basic_properties), "smooth_spirit_stone_slab"),
                smooth_dark_spirit_stone_slab = setup(new BlockItem(ModBlocks.smooth_dark_spirit_stone_slab, basic_properties), "smooth_dark_spirit_stone_slab"),

                spirit_stone_stairs = setup(new BlockItem(ModBlocks.spirit_stone_stairs, basic_properties), "spirit_stone_stairs"),
                dark_spirit_stone_stairs = setup(new BlockItem(ModBlocks.dark_spirit_stone_stairs, basic_properties), "dark_spirit_stone_stairs"),

                spirit_stone_brick_stairs = setup(new BlockItem(ModBlocks.spirit_stone_brick_stairs, basic_properties), "spirit_stone_brick_stairs"),
                dark_spirit_stone_brick_stairs = setup(new BlockItem(ModBlocks.dark_spirit_stone_brick_stairs, basic_properties), "dark_spirit_stone_brick_stairs"),
                patterned_spirit_stone_stairs = setup(new BlockItem(ModBlocks.patterned_spirit_stone_stairs, basic_properties), "patterned_spirit_stone_stairs"),
                patterned_dark_spirit_stone_stairs = setup(new BlockItem(ModBlocks.patterned_dark_spirit_stone_stairs, basic_properties), "patterned_dark_spirit_stone_stairs"),
                smooth_spirit_stone_stairs = setup(new BlockItem(ModBlocks.smooth_spirit_stone_stairs, basic_properties), "smooth_spirit_stone_stairs"),
                smooth_dark_spirit_stone_stairs = setup(new BlockItem(ModBlocks.smooth_dark_spirit_stone_stairs, basic_properties), "smooth_dark_spirit_stone_stairs"),

                spirit_planks = setup(new BlockItem(ModBlocks.spirit_planks, basic_properties), "spirit_planks"),
                spirit_planks_slab = setup(new BlockItem(ModBlocks.spirit_planks_slab, basic_properties), "spirit_planks_slab"),
                spirit_planks_stairs = setup(new BlockItem(ModBlocks.spirit_planks_stairs, basic_properties), "spirit_planks_stairs"),
                block_of_flesh = setup(new BlockItem(ModBlocks.block_of_flesh, basic_properties), "block_of_flesh"),

                spirit_leaves = setup(new BlockItem(ModBlocks.spirit_leaves, basic_properties), "spirit_leaves"),
                spirit_log = setup(new BlockItem(ModBlocks.spirit_log, basic_properties), "spirit_log"),
                spirit_sapling = setup(new BlockItem(ModBlocks.spirit_sapling, basic_properties), "spirit_sapling"),

                creative_spiritwood_stave = setup(new CreativeStave(tool_properties), "creative_spiritwood_stave")
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
