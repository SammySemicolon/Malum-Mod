package com.sammy.malum.init;

import com.google.common.base.Preconditions;
import com.sammy.malum.MalumMod;
import com.sammy.malum.blocks.machines.crystallineaccelerator.CrystallineAccelerator;
import com.sammy.malum.blocks.machines.spiritfurnace.SpiritFurnaceBlock;
import com.sammy.malum.blocks.machines.spiritsmeltery.SpiritSmelteryBlock;
import com.sammy.malum.items.equipment.armor.ItemSpiritHunterArmor;
import com.sammy.malum.items.equipment.armor.ItemSpiritedSteelBattleArmor;
import com.sammy.malum.items.equipment.armor.ItemUmbraSteelBattleArmor;
import com.sammy.malum.items.staves.CreativeStave;
import com.sammy.malum.items.staves.SpiritwoodStave;
import com.sammy.malum.items.staves.effects.MatingEffect;
import com.sammy.malum.items.staves.effects.ResonantBlinkEffect;
import com.sammy.malum.items.*;
import com.sammy.malum.items.equipment.curios.*;
import com.sammy.malum.items.tools.ModBusterSwordItem;
import com.sammy.malum.items.tools.ModExcavatorItem;
import com.sammy.malum.items.tools.UltimateWeaponItem;
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

import static com.sammy.malum.MalumMod.MODID;
import static com.sammy.malum.init.ModItemTiers.ItemTier.SPIRITED_STEEL_ITEM;
import static com.sammy.malum.init.ModItemTiers.ItemTier.UMBRAL_ALLOY_ITEM;
import static com.sammy.malum.init.ModSounds.*;

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
    public static Item runic_ash;
    
    public static Item enriched_crystal;
    public static Item crystalline_compound;
    public static Item crystalline_catalyst;
    public static Item ectoplasm;
    public static Item resonant_lens;
    public static Item penumbral_adhesive;
    public static Item umbral_steel_ingot;
    public static Item umbral_steel_nugget;
    public static Item archaic_quartz;
    
    public static Item runic_mechanism;
    public static Item arcane_apparatus;
    public static Item cursed_nebulous;
    public static Item stellar_apparatus;

    public static Item spiritwood_stave;
    public static Item resonant_stave;
    public static Item fiery_stave;
    public static Item bone_stave;

    public static Item spirit_vault;
    public static Item spirit_capacitor;
    //TOOLS
    
    public static Item spirit_hunter_shoes;
    public static Item spirit_hunter_leggings;
    public static Item spirit_hunter_chestplate;
    public static Item spirit_hunter_helm;
    
    public static Item spirited_steel_battle_shoes;
    public static Item spirited_steel_battle_leggings;
    public static Item spirited_steel_battle_chestplate;
    public static Item spirited_steel_battle_helm;
    
    public static Item umbral_steel_shoes;
    public static Item umbral_steel_leggings;
    public static Item umbral_steel_chestplate;
    public static Item umbral_steel_helm;
    
    public static Item spirited_steel_buster_sword;
    public static Item spirited_steel_excavator;
    
    public static Item umbral_steel_buster_sword;
    public static Item umbral_steel_excavator;
    
    public static Item vacant_rapier;
    public static Item ultimate_weapon;
    public static Item bow_of_lost_souls;
    public static Item breaker_blade;
    public static Item ender_artifact;
    public static Item shulker_storage;
    //CURIOS
    public static Item spiritwood_bark_necklace;
    public static Item enchanted_lectern;
    public static Item vacant_aegis;
    public static Item vampire_necklace;
    public static Item ethereal_bulwark;
    public static Item miracle_pearl;
    public static Item jester_hat;
    public static Item necrotic_catalyst;
    public static Item phantom_ring;
    public static Item phantom_wings;
    public static Item ignition_reactor;
    public static Item netherborne_capacitor;
    public static Item totem_of_eternal_life;
    public static Item good_luck_charm;
    public static Item sinister_mask;
    public static Item gilded_gauntlet;
    public static Item band_of_friendship;

    //FUNCTIONAL BLOCKS
    public static Item spirit_jar;
    public static Item spirit_furnace;
    public static Item basic_mirror;
    public static Item input_mirror;
    public static Item output_mirror;
    public static Item redstone_battery;
    public static Item redstone_clock;
    public static Item funk_engine;
    public static Item spirit_smeltery;
    public static Item crystalline_accelerator;
    //BLOCKS
    public static Item archaic_quartz_ore;
    public static Item brittle_bedrock;
    
    public static Item spirit_glass;
    
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
    public static Item spirit_slab;
    public static Item spirit_stairs;

    //HIDDEN

    public static Item jei_spirit;

    //CREATIVE
    public static Item creative_spiritwood_stave;

    //MISC
    public static Item music_disc_redstone_pulse;
    public static Item music_disc_netherborne;
    public static Item music_disc_skeletons_in_the_night;
    public static Item music_disc_prismatropolis;
    public static Item music_disc_aetherborne;
    public static Item music_disc_the_bone_brigade_blues;
    public static Item music_disc_ender_alley;

    static Item.Properties basic_properties = new Item.Properties().group(MALUM_MOD_GROUP).maxStackSize(64);
    static Item.Properties rare_properties = new Item.Properties().group(MALUM_MOD_GROUP).maxStackSize(64).rarity(Rarity.UNCOMMON);
    static Item.Properties tool_properties = new Item.Properties().group(MALUM_MOD_GROUP).maxStackSize(1);
    static Item.Properties curio_properties = new Item.Properties().group(MALUM_MOD_GROUP).maxStackSize(1).maxDamage(1);
    static Item.Properties music_disc_properties = new Item.Properties().maxStackSize(1).group(ItemGroup.MISC).rarity(Rarity.RARE);
    static Item.Properties hidden_properties = new Item.Properties().maxStackSize(1);
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
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
                stygian_pearl = setup(new Item(basic_properties), "stygian_pearl"),
                runic_ash = setup(new Item(basic_properties), "runic_ash"),
        
                enriched_crystal = setup(new Item(basic_properties), "enriched_crystal"),
                crystalline_compound = setup(new Item(basic_properties), "crystalline_compound"),
                crystalline_catalyst = setup(new Item(basic_properties), "crystalline_catalyst"),
                ectoplasm = setup(new Item(basic_properties), "ectoplasm"),
                resonant_lens = setup(new Item(basic_properties), "resonant_lens"),
                penumbral_adhesive = setup(new Item(basic_properties), "penumbral_adhesive"),
                umbral_steel_ingot = setup(new Item(basic_properties), "umbral_steel_ingot"),
                umbral_steel_nugget = setup(new Item(basic_properties), "umbral_steel_nugget"),
                archaic_quartz = setup(new Item(basic_properties), "archaic_quartz"),
        
                runic_mechanism = setup(new Item(basic_properties), "runic_mechanism"),
                arcane_apparatus = setup(new Item(basic_properties), "arcane_apparatus"),
                cursed_nebulous = setup(new SimpleFoiledItem(rare_properties), "cursed_nebulous"),
                stellar_apparatus = setup(new SimpleFoiledItem(rare_properties), "stellar_apparatus"),

                spiritwood_stave = setup(new SpiritwoodStave(tool_properties, null), "spiritwood_stave"),
                resonant_stave = setup(new SpiritwoodStave(tool_properties, new ResonantBlinkEffect()), "resonant_stave"),
                fiery_stave = setup(new SpiritwoodStave(tool_properties, new ResonantBlinkEffect()), "fiery_stave"),
                bone_stave = setup(new SpiritwoodStave(tool_properties, new MatingEffect()), "bone_stave"),
                
                spirit_vault = setup(new SpiritVault(tool_properties), "spirit_vault"),
                spirit_capacitor = setup(new SpiritCapacitor(tool_properties), "spirit_capacitor"),
                
                spirit_hunter_shoes = setup(new ItemSpiritHunterArmor(ModItemTiers.ArmorMaterial.SPIRIT_HUNTER_ARMOR, EquipmentSlotType.FEET, tool_properties), "spirit_hunter_shoes"),
                spirit_hunter_leggings = setup(new ItemSpiritHunterArmor(ModItemTiers.ArmorMaterial.SPIRIT_HUNTER_ARMOR, EquipmentSlotType.LEGS, tool_properties), "spirit_hunter_leggings"),
                spirit_hunter_chestplate = setup(new ItemSpiritHunterArmor(ModItemTiers.ArmorMaterial.SPIRIT_HUNTER_ARMOR, EquipmentSlotType.CHEST, tool_properties), "spirit_hunter_chestplate"),
                spirit_hunter_helm = setup(new ItemSpiritHunterArmor(ModItemTiers.ArmorMaterial.SPIRIT_HUNTER_ARMOR, EquipmentSlotType.HEAD, tool_properties), "spirit_hunter_helm"),

                spirited_steel_battle_shoes = setup(new ItemSpiritedSteelBattleArmor(ModItemTiers.ArmorMaterial.SPIRITED_STEEL_BATTLE_ARMOR, EquipmentSlotType.FEET, tool_properties), "spirited_steel_battle_shoes"),
                spirited_steel_battle_leggings = setup(new ItemSpiritedSteelBattleArmor(ModItemTiers.ArmorMaterial.SPIRITED_STEEL_BATTLE_ARMOR, EquipmentSlotType.LEGS, tool_properties), "spirited_steel_battle_leggings"),
                spirited_steel_battle_chestplate = setup(new ItemSpiritedSteelBattleArmor(ModItemTiers.ArmorMaterial.SPIRITED_STEEL_BATTLE_ARMOR, EquipmentSlotType.CHEST, tool_properties), "spirited_steel_battle_chestplate"),
                spirited_steel_battle_helm = setup(new ItemSpiritedSteelBattleArmor(ModItemTiers.ArmorMaterial.SPIRITED_STEEL_BATTLE_ARMOR, EquipmentSlotType.HEAD, tool_properties), "spirited_steel_battle_helm"),

                umbral_steel_shoes = setup(new ItemUmbraSteelBattleArmor(ModItemTiers.ArmorMaterial.UMBRAL_ALLOY_BATTLE_ARMOR, EquipmentSlotType.FEET, tool_properties), "umbral_steel_shoes"),
                umbral_steel_leggings = setup(new ItemUmbraSteelBattleArmor(ModItemTiers.ArmorMaterial.UMBRAL_ALLOY_BATTLE_ARMOR, EquipmentSlotType.LEGS, tool_properties), "umbral_steel_leggings"),
                umbral_steel_chestplate = setup(new ItemUmbraSteelBattleArmor(ModItemTiers.ArmorMaterial.UMBRAL_ALLOY_BATTLE_ARMOR, EquipmentSlotType.CHEST, tool_properties), "umbral_steel_chestplate"),
                umbral_steel_helm = setup(new ItemUmbraSteelBattleArmor(ModItemTiers.ArmorMaterial.UMBRAL_ALLOY_BATTLE_ARMOR, EquipmentSlotType.HEAD, tool_properties), "umbral_steel_helm"),
        
                spirited_steel_buster_sword = setup(new ModBusterSwordItem(SPIRITED_STEEL_ITEM, 6, -0.8f, tool_properties), "spirited_steel_buster_sword"),
                spirited_steel_excavator = setup(new ModExcavatorItem( 3, -0.4f, SPIRITED_STEEL_ITEM, Collections.emptySet(), tool_properties), "spirited_steel_excavator"),
        
                umbral_steel_buster_sword = setup(new ModBusterSwordItem(UMBRAL_ALLOY_ITEM, 9, -0.8f, tool_properties), "umbral_steel_buster_sword"),
                umbral_steel_excavator = setup(new ModExcavatorItem( 4, -0.4f, UMBRAL_ALLOY_ITEM, Collections.emptySet(), tool_properties), "umbral_steel_excavator"),

                vacant_rapier = setup(new VacantRapier(ItemTier.DIAMOND, -4, 1.2f, tool_properties), "vacant_rapier"),
                ultimate_weapon = setup(new UltimateWeaponItem(ItemTier.DIAMOND, 0, 0.4f, tool_properties), "ultimate_weapon"),
                bow_of_lost_souls = setup(new BowofLostSouls(tool_properties), "bow_of_lost_souls"),
                breaker_blade = setup(new ModBusterSwordItem(UMBRAL_ALLOY_ITEM, 12, -0.9f, tool_properties), "breaker_blade"),
        
                ender_artifact = setup(new EnderArtifactItem(tool_properties), "ender_artifact"),
                shulker_storage = setup(new ShulkerStorage(tool_properties), "shulker_storage"),
        
                spiritwood_bark_necklace = setup(new CurioSpiritwoodNecklace(curio_properties), "spiritwood_bark_necklace"),
                enchanted_lectern = setup(new CurioEnchantedLectern(curio_properties), "enchanted_lectern"),
                vacant_aegis = setup(new CurioVacantAegis(curio_properties), "vacant_aegis"),
                vampire_necklace = setup(new CurioVampireNecklace(curio_properties), "vampire_necklace"),
                ethereal_bulwark = setup(new CurioEtherealBulwark(curio_properties), "ethereal_bulwark"),
                miracle_pearl = setup(new CurioMiraclePearl(curio_properties), "miracle_pearl"),
                jester_hat = setup(new CurioJesterHat(curio_properties), "jester_hat"),
                necrotic_catalyst = setup(new CurioNecroticCatalyst(curio_properties), "necrotic_catalyst"),
                phantom_ring = setup(new CurioPhantomRing(curio_properties), "phantom_ring"),
                phantom_wings = setup(new CurioPhantomWings(curio_properties), "phantom_wings"),
                ignition_reactor = setup(new CurioIgnitionReactor(curio_properties), "ignition_reactor"),
                netherborne_capacitor = setup(new CurioNetherborneCapacitor(curio_properties), "netherborne_capacitor"),
                totem_of_eternal_life = setup(new CurioTotemOfEternalLife(curio_properties), "totem_of_eternal_life"),
                good_luck_charm = setup(new CurioGoodLuckCharm(curio_properties), "good_luck_charm"),
                sinister_mask = setup(new CurioSinisterMask(curio_properties), "sinister_mask"),
                gilded_gauntlet = setup(new CurioGildedGauntlet(curio_properties), "gilded_gauntlet"),
                band_of_friendship = setup(new CurioBandOfFriendship(curio_properties), "band_of_friendship"),

                spirit_jar = setup(new SpiritJar(ModBlocks.spirit_jar, tool_properties), "spirit_jar"),
                spirit_furnace = setup(new MultiblockItem(ModBlocks.spirit_furnace, ModBlocks.spirit_furnace_bounding_block, basic_properties, SpiritFurnaceBlock.structure), "spirit_furnace"),
                basic_mirror = setup(new BlockItem(ModBlocks.basic_mirror, basic_properties), "basic_mirror"),
                input_mirror = setup(new BlockItem(ModBlocks.input_mirror, basic_properties), "input_mirror"),
                output_mirror = setup(new MirrorBlockItem(ModBlocks.output_mirror, basic_properties), "output_mirror"),
                redstone_battery = setup(new BlockItem(ModBlocks.redstone_battery, basic_properties), "redstone_battery"),
                redstone_clock = setup(new BlockItem(ModBlocks.redstone_clock, basic_properties), "redstone_clock"),
                funk_engine = setup(new BlockItem(ModBlocks.funk_engine, basic_properties), "funk_engine"),
                spirit_smeltery = setup(new MultiblockItem(ModBlocks.spirit_smeltery, ModBlocks.spirit_smeltery_bounding_block, basic_properties, SpiritSmelteryBlock.structure), "spirit_smeltery"),
                crystalline_accelerator = setup(new MultiblockItem(ModBlocks.crystalline_accelerator, ModBlocks.crystalline_accelerator_bounding_block, basic_properties, CrystallineAccelerator.structure), "crystalline_accelerator"),
        
                archaic_quartz_ore = setup(new BlockItem(ModBlocks.archaic_quartz_ore,basic_properties), "archaic_quartz_ore"),
                brittle_bedrock = setup(new BlockItem(ModBlocks.brittle_bedrock,basic_properties), "brittle_bedrock"),
        
                spirit_glass = setup(new BlockItem(ModBlocks.spirit_glass, basic_properties), "spirit_glass"),
        
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
                spirit_slab = setup(new BlockItem(ModBlocks.spirit_slab, basic_properties), "spirit_slab"),
                spirit_stairs = setup(new BlockItem(ModBlocks.spirit_stairs, basic_properties), "spirit_stairs"),

                block_of_flesh = setup(new BlockItem(ModBlocks.block_of_flesh, basic_properties), "block_of_flesh"),

                spirit_leaves = setup(new BlockItem(ModBlocks.spirit_leaves, basic_properties), "spirit_leaves"),
                spirit_log = setup(new BlockItem(ModBlocks.spirit_log, basic_properties), "spirit_log"),
                spirit_sapling = setup(new BlockItem(ModBlocks.spirit_sapling, basic_properties), "spirit_sapling"),
        
                jei_spirit = setup(new JeiSpirit(basic_properties), "jei_spirit"),
                creative_spiritwood_stave = setup(new CreativeStave(tool_properties), "creative_spiritwood_stave"),

                music_disc_redstone_pulse = setup(new MusicDiscItem(1, () -> redstone_pulse,music_disc_properties), "music_disc_redstone_pulse"),
                music_disc_netherborne = setup(new MusicDiscItem(2,() -> netherborne,music_disc_properties), "music_disc_netherborne"),
                music_disc_skeletons_in_the_night = setup(new MusicDiscItem(3,() -> skeletons_in_the_night,music_disc_properties), "music_disc_skeletons_in_the_night"),
                music_disc_prismatropolis = setup(new MusicDiscItem(4,() -> prismatropolis,music_disc_properties), "music_disc_prismatropolis"),
                music_disc_aetherborne = setup(new MusicDiscItem(5,() -> aetherborne,music_disc_properties), "music_disc_aetherborne"),
                music_disc_the_bone_brigade_blues = setup(new MusicDiscItem(6,() -> the_bone_brigade_blues,music_disc_properties), "music_disc_the_bone_brigade_blues"),
                music_disc_ender_alley = setup(new MusicDiscItem(6,() -> ender_alley,music_disc_properties), "music_disc_ender_alley")
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
