package com.sammy.malum.registry.common;

import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.blight.scarstone.LargeStrangeCrystalBlock;
import com.sammy.malum.common.block.blight.scarstone.ScarstoneBlock;
import com.sammy.malum.common.block.blight.scarstone.StrangeCrystalBlock;
import com.sammy.malum.common.block.blight.scarstone.StrangeRootBlock;
import com.sammy.malum.common.block.curiosities.artifice.crystallarium.*;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.aerial.GustIgniterBlock;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.aerial.WindTunnelBlock;
import com.sammy.malum.common.block.curiosities.artifice.soul_link.SoulLinkBlock;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavebanker.*;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavebreaker.*;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavecharger.*;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavemaker.*;
import com.sammy.malum.common.block.building.*;
import com.sammy.malum.common.block.curiosities.escription.MyriadGatewayBlock;
import com.sammy.malum.common.block.curiosities.sorcery.soul_brazier.*;
import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlock;
import com.sammy.malum.common.block.curiosities.fluid.SapFilledCauldronBlock;
import com.sammy.malum.common.block.curiosities.obelisk.ObeliskComponentBlock;
import com.sammy.malum.common.block.curiosities.obelisk.brilliant.BrillianceObeliskCoreBlock;
import com.sammy.malum.common.block.curiosities.obelisk.brilliant.BrilliantObeliskBlockEntity;
import com.sammy.malum.common.block.curiosities.obelisk.rite_pylon.ArcanaPylonBlockEntity;
import com.sammy.malum.common.block.curiosities.obelisk.rite_pylon.ArcanaPylonComponentBlock;
import com.sammy.malum.common.block.curiosities.obelisk.rite_pylon.ArcanaPylonCoreBlock;
import com.sammy.malum.common.block.curiosities.obelisk.runewood.RunewoodObeliskBlockEntity;
import com.sammy.malum.common.block.curiosities.obelisk.runewood.RunewoodObeliskCoreBlock;
import com.sammy.malum.common.block.curiosities.artifice.repair_pylon.RepairPylonComponentBlock;
import com.sammy.malum.common.block.curiosities.artifice.repair_pylon.RepairPylonCoreBlock;
import com.sammy.malum.common.block.curiosities.artifice.repair_pylon.RepairPylonCoreBlockEntity;
import com.sammy.malum.common.block.curiosities.sorcery.runic_workbench.RunicWorkbenchBlock;
import com.sammy.malum.common.block.curiosities.sorcery.spirit_altar.SpiritAltarBlock;
import com.sammy.malum.common.block.curiosities.artifice.spirit_catalyzer.SpiritCatalyzerComponentBlock;
import com.sammy.malum.common.block.curiosities.artifice.spirit_catalyzer.SpiritCatalyzerCoreBlock;
import com.sammy.malum.common.block.curiosities.artifice.spirit_catalyzer.SpiritCatalyzerCoreBlockEntity;
import com.sammy.malum.common.block.curiosities.artifice.spirit_crucible.SpiritCrucibleComponentBlock;
import com.sammy.malum.common.block.curiosities.artifice.spirit_crucible.SpiritCrucibleCoreBlock;
import com.sammy.malum.common.block.curiosities.artifice.spirit_crucible.SpiritCrucibleCoreBlockEntity;
import com.sammy.malum.common.block.curiosities.sorcery.wand_tinkerer.WandTinkererBlock;
import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlock;
import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlock;
import com.sammy.malum.common.block.curiosities.totem.anchor.RiteAnchorBlock;
import com.sammy.malum.common.block.curiosities.totem.channel.RiteChannelBlock;
import com.sammy.malum.common.block.curiosities.totem.spreader.RiteSpreaderBlock;
import com.sammy.malum.common.block.curiosities.totem.unweaver.RiteUnweaverBlock;
import com.sammy.malum.common.block.curiosities.totem.waveform.WaveformTotemBaseBlock;
import com.sammy.malum.common.block.curiosities.weavers_workbench.WeaversWorkbenchBlock;
import com.sammy.malum.common.block.curiosities.weeping_well.PrimordialSoupBlock;
import com.sammy.malum.common.block.curiosities.weeping_well.VoidConduitBlock;
import com.sammy.malum.common.block.dungeon.*;
import com.sammy.malum.common.block.ether.*;
import com.sammy.malum.common.block.flora.EbonySaplingBlock;
import com.sammy.malum.common.block.flora.EbonyStalkBlock;
import com.sammy.malum.common.block.flora.wood.runewood.HangingRunewoodLeavesBlock;
import com.sammy.malum.common.block.flora.wood.runewood.RunewoodLeavesBlock;
import com.sammy.malum.common.block.flora.wood.soulwood.HangingSoulwoodLeavesBlock;
import com.sammy.malum.common.block.flora.wood.soulwood.SoulwoodLeavesBlock;
import com.sammy.malum.common.block.flora.wood.soulwood.SoulwoodSaplingBlock;
import com.sammy.malum.common.block.flora.wood.MalumSaplingBlock;
import com.sammy.malum.common.block.soulstone.*;
import com.sammy.malum.common.block.storage.jar.SpiritJarBlock;
import com.sammy.malum.common.block.the_device.TheDevice;
import com.sammy.malum.common.block.the_device.TheVessel;
import com.sammy.malum.common.data.component.ItemSkinComponent;
import com.sammy.malum.common.entity.nitrate.EthericNitrate;
import com.sammy.malum.common.item.BlightedGunkItem;
import com.sammy.malum.common.item.BrillianceChunkItem;
import com.sammy.malum.common.item.GeasItem;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.augment.core.*;
import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlockItem;
import com.sammy.malum.common.item.codex.EncyclopediaArcanaItem;
import com.sammy.malum.common.item.codex.EncyclopediaEsotericaItem;
import com.sammy.malum.common.item.curiosities.TemporarilyDisabledItem;
import com.sammy.malum.common.item.curiosities.armor.MalignantStrongholdArmorItem;
import com.sammy.malum.common.item.curiosities.armor.SoulHunterArmorItem;
import com.sammy.malum.common.item.curiosities.armor.SoulStainedSteelArmorItem;
import com.sammy.malum.common.item.curiosities.curios.CurioGildedBelt;
import com.sammy.malum.common.item.curiosities.curios.CurioGildedRing;
import com.sammy.malum.common.item.curiosities.curios.CurioOrnateNecklace;
import com.sammy.malum.common.item.curiosities.curios.CurioOrnateRing;
import com.sammy.malum.common.item.curiosities.curios.brooches.CurioElaborateBrooch;
import com.sammy.malum.common.item.curiosities.curios.brooches.CurioGlassBrooch;
import com.sammy.malum.common.item.curiosities.curios.brooches.CurioGluttonousBrooch;
import com.sammy.malum.common.item.curiosities.curios.brooches.CurioRunicBrooch;
import com.sammy.malum.common.item.curiosities.curios.runes.madness.*;
import com.sammy.malum.common.item.curiosities.curios.runes.miracle.*;
import com.sammy.malum.common.item.curiosities.curios.runes.totemic.*;
import com.sammy.malum.common.item.curiosities.curios.sets.alchemical.*;
import com.sammy.malum.common.item.curiosities.curios.sets.elemental.*;
import com.sammy.malum.common.item.curiosities.curios.sets.esoteric.CurioArcaneSpoilRing;
import com.sammy.malum.common.item.curiosities.curios.sets.esoteric.CurioConcealingRing;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioDischargeRing;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioHeartyAvariceRing;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioProspectorBelt;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.CurioStarvedBelt;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.CurioSwarmingRing;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.CurioVoraciousRing;
import com.sammy.malum.common.item.curiosities.curios.sets.scythe.CurioHowlingMaelstromRing;
import com.sammy.malum.common.item.curiosities.curios.sets.scythe.CurioNarrowEdgeNecklace;
import com.sammy.malum.common.item.curiosities.curios.sets.scythe.CurioRisingEdgeRing;
import com.sammy.malum.common.item.curiosities.curios.sets.soulward.CurioMagebaneBelt;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.*;
import com.sammy.malum.common.item.curiosities.pouch.RavenousPouchItem;
import com.sammy.malum.common.item.curiosities.pouch.SoulwovenPouchItem;
import com.sammy.malum.common.item.curiosities.tools.CatalystLobberItem;
import com.sammy.malum.common.item.curiosities.tools.TinkeringToolItem;
import com.sammy.malum.common.item.curiosities.tools.VisionaryScryglassItem;
import com.sammy.malum.common.item.curiosities.tools.spellweaver.SpellweavingAxeItem;
import com.sammy.malum.common.item.curiosities.tools.spellweaver.SpellweavingPickaxeItem;
import com.sammy.malum.common.item.curiosities.weapons.*;
import com.sammy.malum.common.item.curiosities.weapons.greatsword.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.EdgeOfDeliveranceItem;
import com.sammy.malum.common.item.curiosities.weapons.scythe.MagicScytheItem;
import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.common.item.curiosities.weapons.scythe.RavenousScytheItem;
import com.sammy.malum.common.item.curiosities.weapons.staff.ErosionScepterItem;
import com.sammy.malum.common.item.curiosities.weapons.staff.HexStaffItem;
import com.sammy.malum.common.item.curiosities.weapons.staff.UnwindingChaosStaffItem;
import com.sammy.malum.common.item.disc.AestheticaMusicDiscItem;
import com.sammy.malum.common.item.disc.ArcaneElegyMusicDiscItem;
import com.sammy.malum.common.item.ether.*;
import com.sammy.malum.common.item.food.BottledDrinkItem;
import com.sammy.malum.common.item.impetus.FracturedImpetusItem;
import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.common.item.nucleus.PyreNucleusItem;
import com.sammy.malum.common.item.nucleus.WindNucleusItem;
import com.sammy.malum.common.item.soulTags.SoulTagItem;
import com.sammy.malum.common.item.spirit.FusedConsciousnessItem;
import com.sammy.malum.common.item.spirit.SpiritJarItem;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.common.item.spirit.UmbralSpiritShardItem;
import com.sammy.malum.compat.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.core.enumextension.MalumEnumParams;
import com.sammy.malum.registry.common.block.MalumBlockSetTypes;

import com.sammy.malum.registry.common.block.properties.*;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import com.sammy.malum.registry.common.item.MalumFoodProperties;
import com.sammy.malum.registry.common.item.MalumItemProperties;
import com.sammy.malum.registry.common.item.MalumItemTiers;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import com.sammy.malum.registry.common.sound.MalumBlockSoundEvents;
import com.sammy.malum.registry.common.util.*;
import com.sammy.malum.registry.common.util.building.CommonStoneBuildingSet;
import com.sammy.malum.registry.common.util.building.RockBlockSet;
import com.sammy.malum.registry.common.util.building.WoodBlockSet;
import com.sammy.malum.registry.common.util.building.WoolRegistrySet;
import com.sammy.malum.registry.common.worldgen.MalumTreeGrowers;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.modules.toolkit.block.BlockBlockItemHolder;
import team.lodestar.lodestone.modules.toolkit.block.LodestoneBlockProperties;
import team.lodestar.lodestone.modules.toolkit.block.LodestoneDirectionalBlock;
import team.lodestar.lodestone.modules.toolkit.item.LodestoneItemProperties;
import team.lodestar.lodestone.modules.toolkit.multiblock.MultiBlockItem;
import team.lodestar.lodestone.modules.toolkit.multiblock.MultiBlockStructure;
import team.lodestar.wayward_attributes.util.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.sammy.malum.MalumMod.MALUM;
import static com.sammy.malum.registry.common.MalumTags.Blocks.*;
import static com.sammy.malum.registry.common.block.properties.MalumBlockProperties.*;
import static com.sammy.malum.registry.common.item.MalumItemTiers.*;
import static net.minecraft.world.item.Rarity.*;

public class MalumContent {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MALUM);

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MALUM);

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        Spirits.init();
        CompactBlocks.init();
        Materials.init();
        Blight.init();
        BuildingBlocks.init();
        Sorcery.init();
        Poppetry.init();
        Totemancy.init();
        Artifice.init();
        Focusing.init();
        AlchemyAndMetallics.init();
        Gear.init();
        Enscription.init();
        Vanity.init();
    }

    public static final DeferredItem<Item> ENCYCLOPEDIA_ARCANA = register("encyclopedia_arcana", () -> MalumItemProperties.GEAR().rarity(UNCOMMON), EncyclopediaArcanaItem::new);
    public static final DeferredItem<Item> ENCYCLOPEDIA_ESOTERICA = register("encyclopedia_esoterica", () -> MalumItemProperties.GEAR().rarity(EPIC), EncyclopediaEsotericaItem::new);

    public static final DeferredItem<GeasItem> GEAS = register("geas", () -> MalumItemProperties.GEAR().rarity(RARE), GeasItem::new);

    public static final DeferredItem<Item> ARCANE_ELEGY = register("music_disc_arcane_elegy", () -> MalumItemProperties.GEAR().rarity(RARE), ArcaneElegyMusicDiscItem::new);
    public static final DeferredItem<Item> AESTHETICA = register("music_disc_aesthetica", () -> MalumItemProperties.GEAR().rarity(RARE), AestheticaMusicDiscItem::new);

    public static final DeferredItem<Item> SOUL_OF_A_SCYTHE = register("soul_of_a_scythe", MalumItemProperties::GEAR, TemporarilyDisabledItem::new);
    public static final DeferredItem<Item> SOUL_OF_THE_ANCHOR = register("soul_of_the_anchor", MalumItemProperties::GEAR, TemporarilyDisabledItem::new);

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<LodestoneItemProperties> propertySupplier, Function<LodestoneItemProperties, T> function) {
        return ITEMS.register(name, () -> {
            var properties = propertySupplier.get();
            return function.apply(properties);
        });
    }

    public static Item skinHoldingItem(Item.Properties properties, ItemSkinComponent skin) {
        return new Item(properties.component(MalumDataComponents.ITEM_SKIN, skin));
    }


    public static class Spirits {

        public static void init() {

        }

        public static final DeferredItem<SpiritShardItem> SACRED_SPIRIT = register("sacred_spirit", () -> MalumItemProperties.SPIRITS(MalumRarities.SACRED_RARITY_PROXY.getValue()), (p) -> new SpiritShardItem(p, MalumSpiritTypes.SACRED_SPIRIT));
        public static final DeferredItem<SpiritShardItem> WICKED_SPIRIT = register("wicked_spirit", () -> MalumItemProperties.SPIRITS(MalumRarities.WICKED_RARITY_PROXY.getValue()), (p) -> new SpiritShardItem(p, MalumSpiritTypes.WICKED_SPIRIT));
        public static final DeferredItem<SpiritShardItem> ARCANE_SPIRIT = register("arcane_spirit", () -> MalumItemProperties.SPIRITS(MalumRarities.ARCANE_RARITY_PROXY.getValue()), (p) -> new SpiritShardItem(p, MalumSpiritTypes.ARCANE_SPIRIT));
        public static final DeferredItem<SpiritShardItem> ELDRITCH_SPIRIT = register("eldritch_spirit", () -> MalumItemProperties.SPIRITS(MalumRarities.ELDRITCH_RARITY_PROXY.getValue()), (p) -> new SpiritShardItem(p, MalumSpiritTypes.ELDRITCH_SPIRIT));
        public static final DeferredItem<SpiritShardItem> AERIAL_SPIRIT = register("aerial_spirit", () -> MalumItemProperties.SPIRITS(MalumRarities.AERIAL_RARITY_PROXY.getValue()), (p) -> new SpiritShardItem(p, MalumSpiritTypes.AERIAL_SPIRIT));
        public static final DeferredItem<SpiritShardItem> AQUEOUS_SPIRIT = register("aqueous_spirit", () -> MalumItemProperties.SPIRITS(MalumRarities.AQUEOUS_RARITY_PROXY.getValue()), (p) -> new SpiritShardItem(p, MalumSpiritTypes.AQUEOUS_SPIRIT));
        public static final DeferredItem<SpiritShardItem> EARTHEN_SPIRIT = register("earthen_spirit", () -> MalumItemProperties.SPIRITS(MalumRarities.EARTHEN_RARITY_PROXY.getValue()), (p) -> new SpiritShardItem(p, MalumSpiritTypes.EARTHEN_SPIRIT));
        public static final DeferredItem<SpiritShardItem> INFERNAL_SPIRIT = register("infernal_spirit", () -> MalumItemProperties.SPIRITS(MalumRarities.INFERNAL_RARITY_PROXY.getValue()), (p) -> new SpiritShardItem(p, MalumSpiritTypes.INFERNAL_SPIRIT));
        public static final DeferredItem<SpiritShardItem> UMBRAL_SPIRIT = register("umbral_spirit", () -> MalumItemProperties.SPIRITS(MalumRarities.UMBRAL_RARITY_PROXY.getValue()), (p) -> new UmbralSpiritShardItem(p, MalumSpiritTypes.UMBRAL_SPIRIT));

    }

    public static class CompactBlocks {

        public static void init() {

        }

        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_RAW_SOULSTONE = registerBlock("block_of_raw_soulstone", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.SOULSTONE_BLOCK(true)));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_REFINED_SOULSTONE = registerBlock("block_of_refined_soulstone", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.SOULSTONE_BLOCK(false)));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_RAW_BRILLIANCE = registerBlock("block_of_raw_brilliance", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.BRILLIANCE_BLOCK(true)));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_BRILLIANCE = registerBlock("block_of_brilliance", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.BRILLIANCE_BLOCK(false)));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_CTHONIC_GOLD = registerBlock("block_of_cthonic_gold", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.CTHONIC_GOLD_BLOCK()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_BLAZING_QUARTZ = registerBlock("block_of_blazing_quartz", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.BLAZING_QUARTZ_BLOCK().lightLevel((b) -> 14)));


        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_ROTTING_ESSENCE = registerBlock("block_of_rotting_essence", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.GENERIC_STORAGE_BLOCK(SoundType.CORAL_BLOCK, DyeColor.GREEN).needsShovel()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_GRIM_TALC = registerBlock("block_of_grim_talc", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.GENERIC_STORAGE_BLOCK(SoundType.BONE_BLOCK, DyeColor.YELLOW).requiresCorrectToolForDrops().needsPickaxe()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_EERIE_WEAVE = registerBlock("block_of_eerie_weave", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.GENERIC_STORAGE_BLOCK(SoundType.WOOL, DyeColor.LIGHT_BLUE)));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_WARP_FLUX = registerBlock("block_of_warp_flux", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.GENERIC_STORAGE_BLOCK(MalumBlockSoundEvents.STRANGE_CRYSTAL, DyeColor.PURPLE).requiresCorrectToolForDrops().needsPickaxe().noOcclusion().lightLevel(b -> 8)));

        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_WIND_NUCLEI = registerBlock("block_of_wind_nuclei", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.GENERIC_STORAGE_BLOCK(SoundType.WOOL, DyeColor.LIGHT_BLUE).requiresCorrectToolForDrops().needsPickaxe()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_PYRE_NUCLEI = registerBlock("block_of_pyre_nuclei", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.GENERIC_STORAGE_BLOCK(SoundType.COPPER_BULB, DyeColor.YELLOW).requiresCorrectToolForDrops().needsPickaxe().noOcclusion().lightLevel(b -> 8)));

        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_HEX_ASH = registerBlock("block_of_hex_ash", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.GENERIC_STORAGE_BLOCK(SoundType.WOOL, DyeColor.PURPLE).needsHoe()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_LIVING_FLESH = registerBlock("block_of_living_flesh", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.GENERIC_STORAGE_BLOCK(SoundType.CORAL_BLOCK, DyeColor.RED).needsShovel()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_ALCHEMICAL_CALX = registerBlock("block_of_alchemical_calx", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.GENERIC_STORAGE_BLOCK(SoundType.CALCITE, DyeColor.YELLOW).requiresCorrectToolForDrops().needsPickaxe()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_ARCANE_CHARCOAL = registerBlock("block_of_arcane_charcoal", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.ARCANE_CHARCOAL_BLOCK()));

        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_IGNEOUS_CHITIN = registerBlock("block_of_igneous_chitin", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.GENERIC_STORAGE_BLOCK(SoundType.CALCITE, DyeColor.YELLOW).requiresCorrectToolForDrops().needsPickaxe()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_TORN_BRIMSTONE = registerBlock("block_of_torn_brimstone", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.ARCANE_CHARCOAL_BLOCK()));

        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_EBONY = registerBlock("block_of_ebony", () -> new LodestoneDirectionalBlock(MalumStorageBlockProperties.EBONY_BLOCK()));

        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_NULL_SLATE = registerBlock("block_of_null_slate", () -> new Block(MalumStorageBlockProperties.SOULSTONE_BLOCK(false)));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_VOID_SALTS = registerBlock("block_of_void_salts", () -> new Block(MalumStorageBlockProperties.GENERIC_STORAGE_BLOCK(SoundType.WOOL, DyeColor.PURPLE).needsHoe()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_MNEMONIC_FRAGMENT = registerBlock("block_of_mnemonic_fragment", () -> new Block(MalumStorageBlockProperties.BRILLIANCE_BLOCK(false)));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_AURIC_EMBERS = registerBlock("block_of_auric_embers", () -> new Block(MalumStorageBlockProperties.GENERIC_STORAGE_BLOCK(MalumBlockSoundEvents.STRANGE_CRYSTAL, DyeColor.YELLOW).requiresCorrectToolForDrops().needsPickaxe().noOcclusion().lightLevel(b -> 12)));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_MALIGNANT_LEAD = registerBlock("block_of_malignant_lead", () -> new Block(MalumStorageBlockProperties.MALIGNANT_LEAD_BLOCK()));

        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_SOUL_STAINED_STEEL = registerBlock("block_of_soul_stained_steel", () -> new Block(MalumStorageBlockProperties.SOUL_STAINED_STEEL_BLOCK()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_HALLOWED_GOLD = registerBlock("block_of_hallowed_gold", () -> new Block(MalumStorageBlockProperties.HALLOWED_GOLD()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLOCK_OF_MALIGNANT_PEWTER = registerBlock("block_of_malignant_pewter", () -> new Block(MalumStorageBlockProperties.MALIGNANT_PEWTER_BLOCK()));

    }

    public static class Materials {

        public static void init() {

        }


        public static final BlockBlockItemHolder<Block, BlockItem> DEEPSLATE_SOULSTONE_ORE = registerBlock("deepslate_soulstone_ore", () -> new DropExperienceBlock(UniformInt.of(14, 18), MalumOreBlockProperties.SOULSTONE_ORE(true)));
        public static final BlockBlockItemHolder<Block, BlockItem> SOULSTONE_ORE = registerBlock("soulstone_ore", () -> new DropExperienceBlock(UniformInt.of(14, 18), MalumOreBlockProperties.SOULSTONE_ORE(false)));
        public static final BlockBlockItemHolder<Block, BlockItem> BRILLIANT_DEEPSLATE = registerBlock("brilliant_deepslate", () -> new DropExperienceBlock(UniformInt.of(16, 26), MalumOreBlockProperties.BRILLIANCE_ORE(true).setCutout()));
        public static final BlockBlockItemHolder<Block, BlockItem> BRILLIANT_STONE = registerBlock("brilliant_stone", () -> new DropExperienceBlock(UniformInt.of(14, 18), MalumOreBlockProperties.BRILLIANCE_ORE(false).setCutout()));
        public static final BlockBlockItemHolder<Block, BlockItem> CTHONIC_GOLD_ORE = registerBlock("cthonic_gold_ore", () -> new DropExperienceBlock(UniformInt.of(10, 100), MalumOreBlockProperties.CTHONIC_GOLD_ORE()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLAZING_QUARTZ_ORE = registerBlock("blazing_quartz_ore", () -> new DropExperienceBlock(UniformInt.of(4, 7), MalumOreBlockProperties.BLAZING_QUARTZ_ORE().setCutout().lightLevel((b) -> 6)));

        public static final DeferredItem<Item> RAW_SOULSTONE = register("raw_soulstone", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> REFINED_SOULSTONE = register("refined_soulstone", MalumItemProperties::DEFAULT, Item::new);

        public static final DeferredBlock<ArchaicSoulstoneBudBlock> ARCHAIC_SOULSTONE_BUD = registerBlockNoItem("archaic_soulstone_bud", () -> new ArchaicSoulstoneBudBlock(MalumOreBlockProperties.ARCHAIC_SOULSTONE_BUD()));
        public static final BlockBlockItemHolder<SoulstoneBudBlock, BlockItem> SOULSTONE_BUD = registerBlock("soulstone_bud", () -> new SoulstoneBudBlock(MalumOreBlockProperties.SOULSTONE_BUD()));
        public static final DeferredItem<Item> REALIZED_SOULSTONE_BUD = register("realized_soulstone_bud", MalumItemProperties::DEFAULT, SoulstoneBudItem::new);

        public static final DeferredItem<Item> RAW_BRILLIANCE = register("raw_brilliance", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> REFINED_BRILLIANCE = register("refined_brilliance", MalumItemProperties::DEFAULT, (p) -> new BrillianceChunkItem(p.food((new FoodProperties.Builder()).fast().alwaysEdible().build())));

        public static final GeodeCrystalRegistrySet MUNDANE_QUARTZ = new GeodeCrystalRegistrySet("mundane_quartz");
        public static final GeodeCrystalRegistrySet VIVID_AMETRINE = new GeodeCrystalRegistrySet("vivid_ametrine");
        public static final GeodeCrystalRegistrySet MARINE_AGATE = new GeodeCrystalRegistrySet("marine_agate");
        public static final GeodeCrystalRegistrySet RUGGED_CITRINE = new GeodeCrystalRegistrySet("rugged_citrine");

//        public static final GeodeCrystalSet JAGGED_ONYX = new GeodeCrystalSet("jagged_onyx");
//        public static final GeodeCrystalSet PERFECT_QUARTZ = new GeodeCrystalSet("perfect_quartz");
//        public static final GeodeCrystalSet BLAZING_CARNELIAN = new GeodeCrystalSet("blazing_carnelian");


        public static final DeferredItem<Item> BLAZING_QUARTZ = register("blazing_quartz", MalumItemProperties::DEFAULT, Item::new);


        public static final DeferredItem<Item> CTHONIC_GOLD = register("cthonic_gold", () -> MalumItemProperties.DEFAULT().rarity(UNCOMMON), Item::new);
        public static final BlockBlockItemHolder<Block, BlockItem> CTHONIC_GOLD_FRAGMENT = registerItemNameBlock("cthonic_gold_cluster", "cthonic_gold_fragment", () -> new AmethystClusterBlock(4, 3, MalumOreBlockProperties.CTHONIC_GOLD_CLUSTER().setCutout()));

        public static final DeferredItem<Item> ROTTING_ESSENCE = register("rotting_essence", () -> MalumItemProperties.DEFAULT().food(MalumFoodProperties.ROTTING_ESSENCE), Item::new);
        public static final DeferredItem<Item> GRIM_TALC = register("grim_talc", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> EERIE_WEAVE = register("eerie_weave", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> WARP_FLUX = register("warp_flux", MalumItemProperties::DEFAULT, Item::new);

        public static final DeferredItem<Item> WIND_NUCLEUS = register("wind_nucleus", MalumItemProperties::DEFAULT, WindNucleusItem::new);
        public static final DeferredItem<Item> PYRE_NUCLEUS = register("pyre_nucleus", MalumItemProperties::DEFAULT, PyreNucleusItem::new);

        public static final DeferredItem<Item> HEX_ASH = register("hex_ash", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> LIVING_FLESH = register("living_flesh", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> ALCHEMICAL_CALX = register("alchemical_calx", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> ARCANE_CHARCOAL = register("arcane_charcoal", MalumItemProperties::DEFAULT, Item::new);

        public static final DeferredItem<Item> IGNEOUS_CHITIN = register("igneous_chitin", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> TORN_BRIMSTONE = register("torn_brimstone", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> ENTROPIC_ANOMALY = register("entropic_anomaly", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> BLADE_MEMORY = register("blade_memory", MalumItemProperties::DEFAULT, Item::new);

        public static final DeferredBlock<Block> EBONY_SAPLING = registerBlockNoItem("ebony_sapling", () -> new EbonySaplingBlock(MalumFloraBlockProperties.EBONY_SAPLING()));
        public static final BlockBlockItemHolder<Block, BlockItem> EBONY_STALK = registerItemNameBlock("ebony", "ebony_stalk", () -> new EbonyStalkBlock(MalumFloraBlockProperties.EBONY()));
        public static final DeferredItem<Item> CALCIFIED_EBONY = register("calcified_ebony", MalumItemProperties::DEFAULT, Item::new);

        public static final DeferredItem<Item> RUNIC_SAP_BOTTLE = register("runic_sap_bottle", MalumItemProperties::DEFAULT, (p) -> new BottledDrinkItem(p.food(MalumFoodProperties.RUNIC_SAP)));
        public static final DeferredItem<Item> RUNIC_SAPBALL = register("runic_sapball", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredBlock<Block> RUNIC_SAP_CAULDRON = registerBlockNoItem("runic_sap_cauldron", SapFilledCauldronBlock::new);

        public static final DeferredItem<Item> AZOIC_SAP_BOTTLE = register("azoic_sap_bottle", MalumItemProperties::DEFAULT, (p) -> new BottledDrinkItem(p.food(MalumFoodProperties.AZOIC_SAP)));
        public static final DeferredItem<Item> AZOIC_SAPBALL = register("azoic_sapball", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredBlock<Block> AZOIC_SAP_CAULDRON = registerBlockNoItem("azoic_sap_cauldron", SapFilledCauldronBlock::new);

        public static final DeferredItem<Item> NULL_SLATE = register("null_slate", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> VOID_SALTS = register("void_salts", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> MNEMONIC_FRAGMENT = register("mnemonic_fragment", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> AURIC_EMBERS = register("auric_embers", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> MALIGNANT_LEAD = register("malignant_lead", () -> MalumItemProperties.DEFAULT().rarity(RARE), Item::new);

        public static final DeferredItem<Item> SOUL_STAINED_STEEL_INGOT = register("soul_stained_steel_ingot", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> SOUL_STAINED_STEEL_PLATING = register("soul_stained_steel_plating", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> SOUL_STAINED_STEEL_NUGGET = register("soul_stained_steel_nugget", MalumItemProperties::DEFAULT, Item::new);

        public static final DeferredItem<Item> HALLOWED_GOLD_INGOT = register("hallowed_gold_ingot", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> HALLOWED_GOLD_INLAY = register("hallowed_gold_inlay", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> HALLOWED_GOLD_NUGGET = register("hallowed_gold_nugget", MalumItemProperties::DEFAULT, Item::new);

        public static final DeferredItem<Item> MALIGNANT_PEWTER_INGOT = register("malignant_pewter_ingot", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> MALIGNANT_PEWTER_PLATING = register("malignant_pewter_plating", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> MALIGNANT_PEWTER_NUGGET = register("malignant_pewter_nugget", MalumItemProperties::DEFAULT, Item::new);

        public static final DeferredItem<Item> SOULWOVEN_SILK = register("soulwoven_silk", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> PARACAUSAL_FLAME = register("paracausal_flame", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> CONVOLUTED_LENS = register("convoluted_lens", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> MIMICRY_RELAY = register("mimicry_relay", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> IMITATION_FLESH = register("imitation_flesh", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> IMITATION_HEART = register("imitation_heart", MalumItemProperties::DEFAULT, Item::new);

        public static final DeferredItem<Item> POPPET = register("poppet", MalumItemProperties::DEFAULT, Item::new);

        public static final DeferredItem<Item> ANOMALOUS_DESIGN = register("anomalous_design", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> COMPLETE_DESIGN = register("complete_design", MalumItemProperties::DEFAULT, Item::new);
        public static final DeferredItem<Item> FUSED_CONSCIOUSNESS = register("fused_consciousness", MalumItemProperties::DEFAULT, (p) -> new FusedConsciousnessItem(p.rarity(RARE)));
    }

    public static class Blight {

        public static void init() {

        }

        public static final BlockBlockItemHolder<Block, BlockItem> COLUMNAR_BLIGHT = registerBlock("columnar_blight", () -> new ColumnarBlightBlock(MalumBlightBlockProperties.BLIGHTED_EARTH()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLIGHTED_EARTH = registerBlock("blighted_earth", () -> new BlightedEarthBlock(MalumBlightBlockProperties.BLIGHTED_EARTH()));

        public static final BlockBlockItemHolder<Block, BlockItem> BLIGHT = registerBlock("blight", () -> new BlightedCoverageBlock(MalumBlightBlockProperties.BLIGHTED_COVERING()));
        public static final DeferredBlock<Block> CLINGING_BLIGHT = registerBlockNoItem("clinging_blight", () -> new CreepingBlightBlock(MalumBlightBlockProperties.CLINGING_BLIGHT()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLIGHTED_GUNK = registerBlock("blighted_growth", "blighted_gunk", () -> new BlightedPlantBlock(MalumBlightBlockProperties.BLIGHTED_PLANTS()), BlightedGunkItem::new);

        public static final BlockBlockItemHolder<Block, BlockItem> BLIGHTPEARL = registerBlock("blightpearl", () -> new BlightedPlantBlock(MalumBlightBlockProperties.BLIGHTED_PLANTS()));
        public static final BlockBlockItemHolder<Block, BlockItem> BLIGHTROOT = registerBlock("blightroot", () -> new BlightedPlantBlock(MalumBlightBlockProperties.BLIGHTED_PLANTS()));

        public static final BlockBlockItemHolder<Block, BlockItem> SCARSTONE = registerBlock("scarstone", () -> new ScarstoneBlock(MalumBlightBlockProperties.SCARSTONE()));
        public static final BlockBlockItemHolder<Block, BlockItem> STRANGE_CRYSTAL = registerBlock("strange_crystal", () -> new StrangeCrystalBlock(MalumBlightBlockProperties.STRANGE_CRYSTAL()));
        public static final BlockBlockItemHolder<Block, BlockItem> LARGE_STRANGE_CRYSTAL = registerBlock("large_strange_crystal", () -> new LargeStrangeCrystalBlock(MalumBlightBlockProperties.STRANGE_CRYSTAL()));
        public static final BlockBlockItemHolder<Block, BlockItem> STRANGEROOT = registerBlock("strangeroot", () -> new StrangeRootBlock(MalumBlightBlockProperties.STRANGEROOT()));

    }

    public static class BuildingBlocks {

        public static void init() {

        }

        public static final BlockBlockItemHolder<Block, BlockItem> SACRED_SPIRITED_GLASS = registerBlock("sacred_spirited_glass", () -> new SpiritedGlassBlock(SPIRITED_GLASS()));
        public static final BlockBlockItemHolder<Block, BlockItem> WICKED_SPIRITED_GLASS = registerBlock("wicked_spirited_glass", () -> new SpiritedGlassBlock(SPIRITED_GLASS()));
        public static final BlockBlockItemHolder<Block, BlockItem> ARCANE_SPIRITED_GLASS = registerBlock("arcane_spirited_glass", () -> new SpiritedGlassBlock(SPIRITED_GLASS()));
        public static final BlockBlockItemHolder<Block, BlockItem> ELDRITCH_SPIRITED_GLASS = registerBlock("eldritch_spirited_glass", () -> new SpiritedGlassBlock(SPIRITED_GLASS()));
        public static final BlockBlockItemHolder<Block, BlockItem> AERIAL_SPIRITED_GLASS = registerBlock("aerial_spirited_glass", () -> new SpiritedGlassBlock(SPIRITED_GLASS()));
        public static final BlockBlockItemHolder<Block, BlockItem> AQUEOUS_SPIRITED_GLASS = registerBlock("aqueous_spirited_glass", () -> new SpiritedGlassBlock(SPIRITED_GLASS()));
        public static final BlockBlockItemHolder<Block, BlockItem> EARTHEN_SPIRITED_GLASS = registerBlock("earthen_spirited_glass", () -> new SpiritedGlassBlock(SPIRITED_GLASS()));
        public static final BlockBlockItemHolder<Block, BlockItem> INFERNAL_SPIRITED_GLASS = registerBlock("infernal_spirited_glass", () -> new SpiritedGlassBlock(SPIRITED_GLASS()));
        public static final BlockBlockItemHolder<Block, BlockItem> NULL_SPIRITED_GLASS = registerBlock("null_spirited_glass", () -> new SpiritedGlassBlock(SPIRITED_GLASS()));

        public static final BlockBlockItemHolder<Block, BlockItem> SACRED_VARNISHED_TERRACOTTA = registerBlock("sacred_varnished_terracotta", () -> new VarnishedTerracottaBlock(VARNISHED_TERRACOTTA(DyeColor.RED)));
        public static final BlockBlockItemHolder<Block, BlockItem> WICKED_VARNISHED_TERRACOTTA = registerBlock("wicked_varnished_terracotta", () -> new VarnishedTerracottaBlock(VARNISHED_TERRACOTTA(DyeColor.PURPLE)));
        public static final BlockBlockItemHolder<Block, BlockItem> ARCANE_VARNISHED_TERRACOTTA = registerBlock("arcane_varnished_terracotta", () -> new VarnishedTerracottaBlock(VARNISHED_TERRACOTTA(DyeColor.PINK)));
        public static final BlockBlockItemHolder<Block, BlockItem> ELDRITCH_VARNISHED_TERRACOTTA = registerBlock("eldritch_varnished_terracotta", () -> new VarnishedTerracottaBlock(VARNISHED_TERRACOTTA(DyeColor.MAGENTA)));
        public static final BlockBlockItemHolder<Block, BlockItem> AERIAL_VARNISHED_TERRACOTTA = registerBlock("aerial_varnished_terracotta", () -> new VarnishedTerracottaBlock(VARNISHED_TERRACOTTA(DyeColor.LIGHT_BLUE)));
        public static final BlockBlockItemHolder<Block, BlockItem> AQUEOUS_VARNISHED_TERRACOTTA = registerBlock("aqueous_varnished_terracotta", () -> new VarnishedTerracottaBlock(VARNISHED_TERRACOTTA(DyeColor.BLUE)));
        public static final BlockBlockItemHolder<Block, BlockItem> EARTHEN_VARNISHED_TERRACOTTA = registerBlock("earthen_varnished_terracotta", () -> new VarnishedTerracottaBlock(VARNISHED_TERRACOTTA(DyeColor.GREEN)));
        public static final BlockBlockItemHolder<Block, BlockItem> INFERNAL_VARNISHED_TERRACOTTA = registerBlock("infernal_varnished_terracotta", () -> new VarnishedTerracottaBlock(VARNISHED_TERRACOTTA(DyeColor.YELLOW)));
        public static final BlockBlockItemHolder<Block, BlockItem> NULL_VARNISHED_TERRACOTTA = registerBlock("null_varnished_terracotta", () -> new VarnishedTerracottaBlock(VARNISHED_TERRACOTTA(DyeColor.BLACK)));

        public static final BlockBlockItemHolder<Block, BlockItem> SOULWOVEN_BANNER = registerBlock("soulwoven_banner", () -> new SoulwovenBannerBlock(SOULWOVEN_BANNER()), SoulwovenBannerBlockItem::new);

        public static final BlockBlockItemHolder<Block, BlockItem> ETHER = registerBlock("ether", () -> new EtherBlock<>(MalumEtherBlockProperties.ETHER()), EtherItem::ether);
        public static final BlockBlockItemHolder<Block, BlockItem> IRIDESCENT_ETHER = registerBlock("iridescent_ether", () -> new EtherBlock<>(MalumEtherBlockProperties.ETHER()), EtherItem::iridescent);

        public static final BlockBlockItemHolder<Block, BlockItem> ETHER_CANDLE = registerBlock("ether_candle", () -> new EtherCandleBlock<>(MalumEtherBlockProperties.ETHER_CANDLE()), EtherCandleItem::ether);
        public static final BlockBlockItemHolder<Block, BlockItem> IRIDESCENT_ETHER_CANDLE = registerBlock("iridescent_ether_candle", () -> new EtherCandleBlock<>(MalumEtherBlockProperties.ETHER_CANDLE()), EtherCandleItem::iridescent);

        public static final DeferredBlock<Block> WALL_ETHER_TORCH = registerBlockNoItem("wall_ether_torch", () -> new EtherWallTorchBlock<>(MalumEtherBlockProperties.WALL_ETHER_TORCH()));
        public static final BlockBlockItemHolder<Block, BlockItem> ETHER_TORCH = registerBlock("ether_torch", () -> new EtherTorchBlock<>(MalumEtherBlockProperties.ETHER_TORCH()), (b, p) -> EtherTorchItem.ether(b, WALL_ETHER_TORCH.get(), p));
        public static final DeferredBlock<Block> IRIDESCENT_WALL_ETHER_TORCH = registerBlockNoItem("iridescent_wall_ether_torch", () -> new EtherWallTorchBlock<>(MalumEtherBlockProperties.IRIDESCENT_WALL_ETHER_TORCH()));
        public static final BlockBlockItemHolder<Block, BlockItem> IRIDESCENT_ETHER_TORCH = registerBlock("iridescent_ether_torch", () -> new EtherTorchBlock<>(MalumEtherBlockProperties.ETHER_TORCH()), (b, p) -> EtherTorchItem.iridescent(b, IRIDESCENT_WALL_ETHER_TORCH.get(), p));

        public static final BlockBlockItemHolder<Block, BlockItem> ETHER_BRAZIER = MalumContent.registerBlock("ether_brazier",
                () -> new EtherBrazierBlock<>(MalumEtherBlockProperties.ETHER_BRAZIER()), EtherBrazierItem::ether);
        public static final BlockBlockItemHolder<Block, BlockItem> IRIDESCENT_ETHER_BRAZIER = MalumContent.registerBlock("iridescent_ether_brazier",
                () -> new EtherBrazierBlock<>(MalumEtherBlockProperties.ETHER_BRAZIER()), EtherBrazierItem::iridescent);

        public static final BlockBlockItemHolder<Block, BlockItem> ETHER_CRESSET = MalumContent.registerBlock("ether_cresset",
                () -> new EtherCressetBlock<>(MalumEtherBlockProperties.ETHER_CRESSET()), EtherCressetItem::ether);
        public static final BlockBlockItemHolder<Block, BlockItem> IRIDESCENT_ETHER_CRESSET = MalumContent.registerBlock("iridescent_ether_cresset",
                () -> new EtherCressetBlock<>(MalumEtherBlockProperties.ETHER_CRESSET()), EtherCressetItem::iridescent);


        public static final CommonStoneBuildingSet SEED_QUARTZ = new CommonStoneBuildingSet("seed_quartz", MalumStoneBlockProperties::SEED_QUARTZ);
        public static final CommonStoneBuildingSet TRODDEN_STONE = new CommonStoneBuildingSet("trodden_stone", MalumStoneBlockProperties::TRODDEN_STONE);
        public static final CommonStoneBuildingSet IGNEOUS_ROCK = new CommonStoneBuildingSet("igneous_rock", MalumStoneBlockProperties::IGNEOUS_ROCK);
        public static final CommonStoneBuildingSet COMPOSITE_STONE = new CommonStoneBuildingSet("composite_stone", MalumStoneBlockProperties::COMPOSITE_STONE);
        public static final CommonStoneBuildingSet EBONSTONE = new CommonStoneBuildingSet("ebonstone", MalumStoneBlockProperties::EBONSTONE);

        public static final RockBlockSet TAINTED_ROCK_SET = new RockBlockSet("tainted_rock", MalumStoneBlockProperties::TAINTED_ROCK, MalumStoneBlockProperties::TAINTED_ROCK_BRICKS, MalumStoneBlockProperties::CHISELED_TAINTED_ROCK);
        public static final RockBlockSet TWISTED_ROCK_SET = new RockBlockSet("twisted_rock", MalumStoneBlockProperties::TWISTED_ROCK, MalumStoneBlockProperties::TWISTED_ROCK_BRICKS, MalumStoneBlockProperties::CHISELED_TWISTED_ROCK);

        public static final WoolRegistrySet FANCY_WOOL = new WoolRegistrySet("fancy");

        public static final BlockBlockItemHolder<Block, BlockItem> STONE_BOOKSHELF = registerBlock("stone_bookshelf", () -> new StoneBookshelfBlock(MalumStoneBlockProperties.STONE_BOOKSHELF()));


        public static final BlockBlockItemHolder<Block, BlockItem> RUNEWOOD_SAPLING = registerBlock("runewood_sapling", () -> new MalumSaplingBlock(MalumTreeGrowers.RUNEWOOD, MalumWoodBlockProperties.RUNEWOOD_SAPLING()));
        public static final BlockBlockItemHolder<Block, BlockItem> RUNEWOOD_LEAVES = registerBlock("runewood_leaves", () -> new RunewoodLeavesBlock(MalumWoodBlockProperties.RUNEWOOD_LEAVES()));
        public static final BlockBlockItemHolder<Block, BlockItem> HANGING_RUNEWOOD_LEAVES = registerBlock("hanging_runewood_leaves", () -> new HangingRunewoodLeavesBlock(MalumWoodBlockProperties.HANGING_RUNEWOOD_LEAVES()));

        public static final BlockBlockItemHolder<Block, BlockItem> AZURE_RUNEWOOD_SAPLING = registerBlock("azure_runewood_sapling", () -> new MalumSaplingBlock(MalumTreeGrowers.AZURE_RUNEWOOD, MalumWoodBlockProperties.RUNEWOOD_SAPLING()));
        public static final BlockBlockItemHolder<Block, BlockItem> AZURE_RUNEWOOD_LEAVES = registerBlock("azure_runewood_leaves", () -> new RunewoodLeavesBlock(MalumWoodBlockProperties.RUNEWOOD_LEAVES()));
        public static final BlockBlockItemHolder<Block, BlockItem> HANGING_AZURE_RUNEWOOD_LEAVES = registerBlock("hanging_azure_runewood_leaves", () -> new HangingRunewoodLeavesBlock(MalumWoodBlockProperties.HANGING_RUNEWOOD_LEAVES()));

        public static final WoodBlockSet RUNEWOOD_SET = new WoodBlockSet("runewood", "gilded", () -> MalumBlockSetTypes.RUNEWOOD, MalumWoodBlockProperties::RUNEWOOD);
        public static final DeferredItem<Item> RUNEWOOD_BOAT = register("runewood_boat", () -> MalumItemProperties.DEFAULT().stacksTo(1), (p) -> new BoatItem(false, MalumEnumParams.RUNEWOOD_BOAT_TYPE.getValue(), p));
        public static final DeferredItem<Item> RUNEWOOD_CHEST_BOAT = register("runewood_chest_boat", () -> MalumItemProperties.DEFAULT().stacksTo(1), (p) -> new BoatItem(true, MalumEnumParams.RUNEWOOD_BOAT_TYPE.getValue(), p));

        public static final BlockBlockItemHolder<Block, BlockItem> SOULWOOD_SAPLING = registerBlock("soulwood_sapling", () -> new SoulwoodSaplingBlock(MalumTreeGrowers.SOULWOOD, MalumWoodBlockProperties.SOULWOOD_SAPLING()));
        public static final BlockBlockItemHolder<Block, BlockItem> SOULWOOD_LEAVES = registerBlock("soulwood_leaves", () -> new SoulwoodLeavesBlock(MalumWoodBlockProperties.SOULWOOD_LEAVES()));
        public static final BlockBlockItemHolder<Block, BlockItem> HANGING_SOULWOOD_LEAVES = registerBlock("hanging_soulwood_leaves", () -> new HangingSoulwoodLeavesBlock(MalumWoodBlockProperties.HANGING_SOULWOOD_LEAVES()));

        public static final WoodBlockSet SOULWOOD_SET = new WoodBlockSet("soulwood", "ornate", () -> MalumBlockSetTypes.SOULWOOD, MalumWoodBlockProperties::SOULWOOD);
        public static final BlockBlockItemHolder<Block, BlockItem> BLIGHTED_SOULWOOD = registerBlock("blighted_soulwood", () -> new BlightedSoulwoodBlock(MalumWoodBlockProperties.SOULWOOD()));
        public static final DeferredItem<Item> SOULWOOD_BOAT = register("soulwood_boat", () -> MalumItemProperties.DEFAULT().stacksTo(1), (p) -> new BoatItem(false, MalumEnumParams.SOULWOOD_BOAT_TYPE.getValue(), p));
        public static final DeferredItem<Item> SOULWOOD_CHEST_BOAT = register("soulwood_chest_boat", () -> MalumItemProperties.DEFAULT().stacksTo(1), (p) -> new BoatItem(true, MalumEnumParams.SOULWOOD_BOAT_TYPE.getValue(), p));

        public static final DeferredBlock<Block> POTTED_RUNEWOOD_SAPLING = registerBlockNoItem("potted_runewood_sapling", () -> flowerPot(RUNEWOOD_SAPLING));
        public static final DeferredBlock<Block> POTTED_AZURE_RUNEWOOD_SAPLING = registerBlockNoItem("potted_azure_runewood_sapling", () -> flowerPot(AZURE_RUNEWOOD_SAPLING));
        public static final DeferredBlock<Block> POTTED_SOULWOOD_SAPLING = registerBlockNoItem("potted_soulwood_sapling", () -> flowerPot(SOULWOOD_SAPLING));
        public static final DeferredBlock<Block> POTTED_BLIGHTROOT = registerBlockNoItem("potted_blightroot", () -> flowerPot(Blight.BLIGHTROOT));
        public static final DeferredBlock<Block> POTTED_BLIGHTPEARL = registerBlockNoItem("potted_blightpearl", () -> flowerPot(Blight.BLIGHTPEARL));
        public static final DeferredBlock<Block> POTTED_STRANGEROOT = registerBlockNoItem("potted_strangeroot", () -> flowerPot(Blight.STRANGEROOT));

        public static final BlockBlockItemHolder<Block, BlockItem> THE_DEVICE = registerBlock("the_device", () -> new TheDevice(MalumStoneBlockProperties.TAINTED_ROCK()));
        public static final BlockBlockItemHolder<Block, BlockItem> THE_VESSEL = registerBlock("the_vessel", () -> new TheVessel(MalumStoneBlockProperties.TWISTED_ROCK()));
    }

    public static class Sorcery {

        public static void init() {

        }


        public static final BlockBlockItemHolder<Block, BlockItem> SPIRIT_ALTAR = registerBlock("spirit_altar", () -> new SpiritAltarBlock<>(RUNEWOOD_IMPLEMENT()));
        public static final BlockBlockItemHolder<Block, BlockItem> SPIRIT_JAR = registerBlock("spirit_jar", () -> new SpiritJarBlock<>(SPIRIT_JAR()), SpiritJarItem::new);

        public static final BlockBlockItemHolder<Block, BlockItem> WAND_TINKERER = registerBlock("wand_tinkerer", () -> new WandTinkererBlock<>(RUNEWOOD_IMPLEMENT()));
        public static final BlockBlockItemHolder<Block, BlockItem> RUNIC_WORKBENCH = registerBlock("runic_workbench", () -> new RunicWorkbenchBlock<>(RUNEWOOD_IMPLEMENT()));
        public static final BlockBlockItemHolder<Block, BlockItem> WEAVERS_WORKBENCH = registerBlock("weavers_workbench", () -> new WeaversWorkbenchBlock<>(MalumWoodBlockProperties.RUNEWOOD().setCutout().noOcclusion()));

        public static final BlockBlockItemHolder<Block, BlockItem> SOUL_BRAZIER = registerBlock("soulbinding_brazier", () -> new SoulBrazierBlock<>(SOUL_BRAZIER()));

        public static final DeferredItem<Item> SOUL_TAG = register("soul_tag", MalumItemProperties::DEFAULT, SoulTagItem::new);

        public static final BlockBlockItemHolder<Block, MultiBlockItem> RUNEWOOD_OBELISK = registerMultiBlock("runewood_obelisk", () -> new RunewoodObeliskCoreBlock(MalumWoodBlockProperties.RUNEWOOD().setCutout().noOcclusion()), RunewoodObeliskBlockEntity.STRUCTURE);
        public static final DeferredHolder<Block, ObeliskComponentBlock> RUNEWOOD_OBELISK_COMPONENT = registerBlockNoItem("runewood_obelisk_component", () -> new ObeliskComponentBlock(MalumWoodBlockProperties.RUNEWOOD().setCutout().lootFrom(RUNEWOOD_OBELISK).noOcclusion()));

        public static final BlockBlockItemHolder<Block, MultiBlockItem> BRILLIANT_OBELISK = registerMultiBlock("brilliant_obelisk", () -> new BrillianceObeliskCoreBlock(MalumWoodBlockProperties.RUNEWOOD().setCutout().noOcclusion()), BrilliantObeliskBlockEntity.STRUCTURE);
        public static final DeferredHolder<Block, ObeliskComponentBlock> BRILLIANT_OBELISK_COMPONENT = registerBlockNoItem("brilliant_obelisk_component", () -> new ObeliskComponentBlock(MalumWoodBlockProperties.RUNEWOOD().setCutout().lootFrom(BRILLIANT_OBELISK).noOcclusion()));

        public static final BlockBlockItemHolder<Block, MultiBlockItem> ARCANA_PYLON = registerMultiBlock("arcana_pylon", () -> new ArcanaPylonCoreBlock(MalumWoodBlockProperties.SOULWOOD().setCutout().noOcclusion()), ArcanaPylonBlockEntity.STRUCTURE);
        public static final DeferredHolder<Block, ArcanaPylonComponentBlock> ARCANA_PYLON_COMPONENT = registerBlockNoItem("arcana_pylon_component", () -> new ArcanaPylonComponentBlock(MalumWoodBlockProperties.SOULWOOD().setCutout().lootFrom(ARCANA_PYLON).noOcclusion()));

        public static final DeferredItem<Item> VISIONARY_SCRYGLASS = register("visionary_scryglass", MalumItemProperties::GEAR, VisionaryScryglassItem::new);

    }

    public static class Poppetry {

        public static void init() {

        }

        public static final PoppetRegistrySet RUNEWOOD_POPPETWARE = new PoppetRegistrySet("runewood");
        public static final PoppetRegistrySet SOULWOOD_POPPETWARE = new PoppetRegistrySet("soulwood");
    }

    public static class Totemancy {

        public static void init() {

        }

        public static final DeferredItem<Item> TOTEMIC_STAFF = register("totemic_staff", MalumItemProperties::GEAR, TinkeringToolItem::new);

        public static final BlockBlockItemHolder<Block, BlockItem> RUNEWOOD_TOTEM_BASE = registerBlock("runewood_totem_base", () -> new TotemBaseBlock<>(MalumWoodBlockProperties.RUNEWOOD().addTag(IS_RITE_IMMUNE).noOcclusion(), false));
        public static final BlockBlockItemHolder<Block, BlockItem> SOULWOOD_TOTEM_BASE = registerBlock("soulwood_totem_base", () -> new TotemBaseBlock<>(MalumWoodBlockProperties.SOULWOOD().addTag(IS_RITE_IMMUNE).noOcclusion(), true));

        public static final BlockBlockItemHolder<Block, BlockItem> WAVEFORM_RUNEWOOD_TOTEM_BASE = registerBlock("waveform_runewood_totem_base", () -> new WaveformTotemBaseBlock<>(COPPER_ARTIFICE().addTag(IS_RITE_IMMUNE).noOcclusion(), false));
        public static final BlockBlockItemHolder<Block, BlockItem> WAVEFORM_SOULWOOD_TOTEM_BASE = registerBlock("waveform_soulwood_totem_base", () -> new WaveformTotemBaseBlock<>(COPPER_ARTIFICE().addTag(IS_RITE_IMMUNE).noOcclusion(), true));

        public static final DeferredBlock<Block> RUNEWOOD_TOTEM_POLE = registerBlockNoItem("runewood_totem_pole", () -> new TotemPoleBlock<>(MalumWoodBlockProperties.RUNEWOOD().addTag(IS_RITE_IMMUNE).noOcclusion(), BuildingBlocks.RUNEWOOD_SET.log));
        public static final DeferredBlock<Block> SOULWOOD_TOTEM_POLE = registerBlockNoItem("soulwood_totem_pole", () -> new TotemPoleBlock<>(MalumWoodBlockProperties.SOULWOOD().addTag(IS_RITE_IMMUNE).noOcclusion(), BuildingBlocks.SOULWOOD_SET.log));

        public static final BlockBlockItemHolder<Block, BlockItem> RITE_ANCHOR = registerBlock("rite_anchor", () -> new RiteAnchorBlock(MalumStoneBlockProperties.TAINTED_ROCK_TOTEMANCY()));
        public static final BlockBlockItemHolder<Block, BlockItem> RITE_UNWEAVER = registerBlock("rite_unweaver", () -> new RiteUnweaverBlock(MalumStoneBlockProperties.TWISTED_ROCK_TOTEMANCY()));
        public static final BlockBlockItemHolder<Block, BlockItem> RITE_SPREADER = registerBlock("rite_spreader", () -> new RiteSpreaderBlock(MalumStoneBlockProperties.TAINTED_ROCK_TOTEMANCY()));
        public static final BlockBlockItemHolder<Block, BlockItem> RITE_CHANNEL = registerBlock("rite_channel", () -> new RiteChannelBlock(MalumStoneBlockProperties.TAINTED_ROCK_TOTEMANCY()));
    }

    public static class Artifice {

        public static void init() {

        }

        public static final BlockBlockItemHolder<Block, BlockItem> CONJUNCTURE_CRYSTALLARIUM = registerBlock("conjuncture_crystallarium", () -> new ConjunctureCrystallariumBlock<>(COPPER_ARTIFICE()));

        public static final DeferredItem<Item> ARTIFICERS_CLAW = register("artificers_claw", MalumItemProperties::GEAR, TinkeringToolItem::new);

        public static final BlockBlockItemHolder<Block, BlockItem> SOUL_LINK = registerBlock("soul_link", () -> new SoulLinkBlock(COPPER_ARTIFICE()));

        public static final BlockBlockItemHolder<Block, BlockItem> WAVECHARGER = registerBlock("wavecharger", () -> new WaveChargerBlock(COPPER_ARTIFICE()));
        public static final BlockBlockItemHolder<Block, BlockItem> WAVEBANKER = registerBlock("wavebanker", () -> new WaveBankerBlock(COPPER_ARTIFICE()));
        public static final BlockBlockItemHolder<Block, BlockItem> WAVEMAKER = registerBlock("wavemaker", () -> new WaveMakerBlock(COPPER_ARTIFICE()));
        public static final BlockBlockItemHolder<Block, BlockItem> WAVEBREAKER = registerBlock("wavebreaker", () -> new WaveBreakerBlock(COPPER_ARTIFICE()));

        public static final BlockBlockItemHolder<Block, BlockItem> GUST_IGNITER = registerBlock("gust_igniter", () -> new GustIgniterBlock(COPPER_ARTIFICE()));
        public static final BlockBlockItemHolder<Block, BlockItem> WIND_TUNNEL = registerBlock("wind_tunnel", () -> new WindTunnelBlock(COPPER_ARTIFICE()));

    }


    public static class Focusing {

        public static void init() {

        }

        public static final DeferredItem<Item> TUNING_FORK = register("tuning_fork", MalumItemProperties::GEAR, TinkeringToolItem::new);

        public static final BlockBlockItemHolder<Block, MultiBlockItem> SPIRIT_CRUCIBLE = registerMultiBlock("spirit_crucible", () -> new SpiritCrucibleCoreBlock<>(MalumStoneBlockProperties.ARCANE_ROCK_ARTIFICE()), SpiritCrucibleCoreBlockEntity.STRUCTURE);
        public static final DeferredHolder<Block, SpiritCrucibleComponentBlock> SPIRIT_CRUCIBLE_COMPONENT = registerBlockNoItem("spirit_crucible_component", () -> new SpiritCrucibleComponentBlock(MalumStoneBlockProperties.ARCANE_ROCK_ARTIFICE().lootFrom(SPIRIT_CRUCIBLE)));

        public static final BlockBlockItemHolder<Block, MultiBlockItem> SPIRIT_CATALYZER = registerMultiBlock("spirit_catalyzer", () -> new SpiritCatalyzerCoreBlock<>(MalumStoneBlockProperties.ARCANE_ROCK_ARTIFICE()), SpiritCatalyzerCoreBlockEntity.STRUCTURE);
        public static final DeferredHolder<Block, SpiritCatalyzerComponentBlock> SPIRIT_CATALYZER_COMPONENT = registerBlockNoItem("spirit_catalyzer_component", () -> new SpiritCatalyzerComponentBlock(MalumStoneBlockProperties.ARCANE_ROCK_ARTIFICE().lootFrom(SPIRIT_CATALYZER)));

        public static final BlockBlockItemHolder<Block, MultiBlockItem> REPAIR_PYLON = registerMultiBlock("repair_pylon", () -> new RepairPylonCoreBlock<>(MalumStoneBlockProperties.ARCANE_ROCK_ARTIFICE()), RepairPylonCoreBlockEntity.STRUCTURE);
        public static final DeferredHolder<Block, RepairPylonComponentBlock> REPAIR_PYLON_COMPONENT = registerBlockNoItem("repair_pylon_component", () -> new RepairPylonComponentBlock(MalumStoneBlockProperties.ARCANE_ROCK_ARTIFICE().lootFrom(REPAIR_PYLON)));

        public static final DeferredItem<Item> MENDING_DIFFUSER = register("mending_diffuser", MalumItemProperties::DEFAULT, MendingDiffuserItem::new);
        public static final DeferredItem<Item> IMPURITY_STABILIZER = register("impurity_stabilizer", MalumItemProperties::DEFAULT, ImpurityStabilizer::new);
        public static final DeferredItem<Item> SHIELDING_APPARATUS = register("shielding_apparatus", MalumItemProperties::DEFAULT, ShieldingApparatusItem::new);
        public static final DeferredItem<Item> WARPING_ENGINE = register("warping_engine", MalumItemProperties::DEFAULT, WarpingEngineItem::new);
        public static final DeferredItem<Item> ACCELERATING_INLAY = register("accelerating_inlay", MalumItemProperties::DEFAULT, AcceleratingInlayItem::new);
        public static final DeferredItem<Item> PRISMATIC_FOCUS_LENS = register("prismatic_focus_lens", MalumItemProperties::DEFAULT, PrismaticFocusLensItem::new);
        public static final DeferredItem<Item> BLAZING_DIODE = register("blazing_diode", MalumItemProperties::DEFAULT, BlazingDiodeItem::new);
        public static final DeferredItem<Item> INTRICATE_ASSEMBLY = register("intricate_assembly", MalumItemProperties::DEFAULT, IntricateAssemblyItem::new);

        public static final DeferredItem<Item> SYMPATHY_DRIVE = register("sympathy_drive", MalumItemProperties::DEFAULT, SympathyDrive::new);
        public static final DeferredItem<Item> SUSPICIOUS_DEVICE = register("suspicious_device", MalumItemProperties::DEFAULT, SuspiciousDeviceItem::new);
        public static final DeferredItem<Item> CAUSTIC_CATALYST = register("caustic_catalyst", MalumItemProperties::DEFAULT, CausticCatalystItem::new);
        public static final DeferredItem<Item> RESONANCE_TUNER = register("resonance_tuner", MalumItemProperties::DEFAULT, ResonanceTuner::new);

        public static final DeferredItem<Item> STELLAR_MECHANISM = register("stellar_mechanism", MalumItemProperties::DEFAULT, StellarMechanismItem::new);
    }

    public static class AlchemyAndMetallics {

        public static void init() {

        }

        public static final DeferredItem<Item> ALCHEMICAL_IMPETUS = register("alchemical_impetus", MalumItemProperties::IMPETUS, ImpetusItem::new);
        public static final DeferredItem<Item> FRACTURED_ALCHEMICAL_IMPETUS = register("fractured_alchemical_impetus", MalumItemProperties::IMPETUS, FracturedImpetusItem::new);

        public static final DeferredItem<Item> ZEPHYR_IMPETUS = register("zephyr_impetus", MalumItemProperties::IMPETUS, ImpetusItem::new);
        public static final DeferredItem<Item> FRACTURED_ZEPHYR_IMPETUS = register("fractured_zephyr_impetus", MalumItemProperties::IMPETUS, FracturedImpetusItem::new);

        public static final DeferredItem<Item> IFRIT_IMPETUS = register("ifrit_impetus", MalumItemProperties::IMPETUS, ImpetusItem::new);
        public static final DeferredItem<Item> FRACTURED_IFRIT_IMPETUS = register("fractured_ifrit_impetus", MalumItemProperties::IMPETUS, FracturedImpetusItem::new);

        public static final MetallicsItemRegistryBundle IRON_METALLICS = new MetallicsItemRegistryBundle("iron");
        public static final MetallicsItemRegistryBundle COPPER_METALLICS = new MetallicsItemRegistryBundle("copper");
        public static final MetallicsItemRegistryBundle GOLD_METALLICS = new MetallicsItemRegistryBundle("gold");
        public static final MetallicsItemRegistryBundle ZINC_METALLICS = new MetallicsItemRegistryBundle("zinc");
        public static final MetallicsItemRegistryBundle LEAD_METALLICS = new MetallicsItemRegistryBundle("lead");
        public static final MetallicsItemRegistryBundle SILVER_METALLICS = new MetallicsItemRegistryBundle("silver");
        public static final MetallicsItemRegistryBundle ALUMINIUM_METALLICS = new MetallicsItemRegistryBundle("aluminium");
        public static final MetallicsItemRegistryBundle NICKEL_METALLICS = new MetallicsItemRegistryBundle("nickel");

    }

    public static class Gear {

        public static void init() {

        }

        public static final DeferredItem<Item> CATALYST_LOBBER = register("catalyst_lobber", MalumItemProperties::GEAR, (p) -> new CatalystLobberItem(p.durability(500), EthericNitrate::new));

        public static final DeferredItem<Item> SOULWOVEN_POUCH = register("soulwoven_pouch", MalumItemProperties::GEAR, SoulwovenPouchItem::new);
        public static final DeferredItem<Item> RAVENOUS_POUCH = register("ravenous_pouch", MalumItemProperties::GEAR, RavenousPouchItem::new);

        public static final DeferredItem<Item> CRUDE_SCYTHE = register("crude_scythe", MalumItemProperties::GEAR, (p) -> new MalumScytheItem(Tiers.IRON, 4, 1f, 0.5f, 1.5f, p.durability(500)));

        public static final DeferredItem<Item> SOUL_HUNTER_CLOAK = register("soul_hunter_cloak", MalumItemProperties::GEAR, (p) -> new SoulHunterArmorItem(ArmorItem.Type.HELMET, p));
        public static final DeferredItem<Item> SOUL_HUNTER_ROBE = register("soul_hunter_robe", MalumItemProperties::GEAR, (p) -> new SoulHunterArmorItem(ArmorItem.Type.CHESTPLATE, p));
        public static final DeferredItem<Item> SOUL_HUNTER_LEGGINGS = register("soul_hunter_leggings", MalumItemProperties::GEAR, (p) -> new SoulHunterArmorItem(ArmorItem.Type.LEGGINGS, p));
        public static final DeferredItem<Item> SOUL_HUNTER_BOOTS = register("soul_hunter_boots", MalumItemProperties::GEAR, (p) -> new SoulHunterArmorItem(ArmorItem.Type.BOOTS, p));

        public static final DeferredItem<Item> SOUL_STAINED_STEEL_HELMET = register("soul_stained_steel_helmet", MalumItemProperties::GEAR, (p) -> new SoulStainedSteelArmorItem(ArmorItem.Type.HELMET, p));
        public static final DeferredItem<Item> SOUL_STAINED_STEEL_CHESTPLATE = register("soul_stained_steel_chestplate", MalumItemProperties::GEAR, (p) -> new SoulStainedSteelArmorItem(ArmorItem.Type.CHESTPLATE, p));
        public static final DeferredItem<Item> SOUL_STAINED_STEEL_LEGGINGS = register("soul_stained_steel_leggings", MalumItemProperties::GEAR, (p) -> new SoulStainedSteelArmorItem(ArmorItem.Type.LEGGINGS, p));
        public static final DeferredItem<Item> SOUL_STAINED_STEEL_BOOTS = register("soul_stained_steel_boots", MalumItemProperties::GEAR, (p) -> new SoulStainedSteelArmorItem(ArmorItem.Type.BOOTS, p));

        public static final DeferredItem<Item> MALIGNANT_STRONGHOLD_HELMET = register("malignant_stronghold_helmet", MalumItemProperties::GEAR, (p) -> new MalignantStrongholdArmorItem(ArmorItem.Type.HELMET, p));
        public static final DeferredItem<Item> MALIGNANT_STRONGHOLD_CHESTPLATE = register("malignant_stronghold_chestplate", MalumItemProperties::GEAR, (p) -> new MalignantStrongholdArmorItem(ArmorItem.Type.CHESTPLATE, p));
        public static final DeferredItem<Item> MALIGNANT_STRONGHOLD_LEGGINGS = register("malignant_stronghold_leggings", MalumItemProperties::GEAR, (p) -> new MalignantStrongholdArmorItem(ArmorItem.Type.LEGGINGS, p));
        public static final DeferredItem<Item> MALIGNANT_STRONGHOLD_BOOTS = register("malignant_stronghold_boots", MalumItemProperties::GEAR, (p) -> new MalignantStrongholdArmorItem(ArmorItem.Type.BOOTS, p));

        public static final DeferredItem<Item> SOUL_STAINED_STEEL_SCYTHE = register("soul_stained_steel_scythe", MalumItemProperties::GEAR, (p) -> new MagicScytheItem(SOUL_STAINED_STEEL, 3f, 1.2f, 4, 0.5f, 1.5f, p));
        public static final DeferredItem<Item> SOUL_STAINED_STEEL_KNIFE = register("soul_stained_steel_knife", MalumItemProperties::GEAR, (p) -> FarmersDelightCompat.LOADED ? FarmersDelightCompat.LoadedOnly.makeMagicKnife(p) : new Item(p));
        public static final DeferredItem<Item> SOUL_STAINED_STEEL_SWORD = register("soul_stained_steel_sword", MalumItemProperties::GEAR, (p) -> new MagicSwordItem(SOUL_STAINED_STEEL, 3, 1.6f, 3, p));

        public static final DeferredItem<Item> SOUL_STAINED_STEEL_PICKAXE = register("soul_stained_steel_pickaxe", MalumItemProperties::GEAR, (p) -> new MagicPickaxeItem(SOUL_STAINED_STEEL, 2, 1.2f, 3, p));
        public static final DeferredItem<Item> SOUL_STAINED_STEEL_AXE = register("soul_stained_steel_axe", MalumItemProperties::GEAR, (p) -> new MagicAxeItem(SOUL_STAINED_STEEL, 4, 0.9f, 5, p));
        public static final DeferredItem<Item> SOUL_STAINED_STEEL_SHOVEL = register("soul_stained_steel_shovel", MalumItemProperties::GEAR, (p) -> new MagicShovelItem(SOUL_STAINED_STEEL, 2, 1, 3, p));
        public static final DeferredItem<Item> SOUL_STAINED_STEEL_HOE = register("soul_stained_steel_hoe", MalumItemProperties::GEAR, (p) -> new MagicHoeItem(SOUL_STAINED_STEEL, 1, 4f, 1, p));

        public static final DeferredItem<Item> SPELLWEAVING_PICKAXE = register("spellweaving_pickaxe", MalumItemProperties::GEAR, (p) -> new SpellweavingPickaxeItem(SPELLWEAVING_TOOLS, -3, 0, 4, p));
        public static final DeferredItem<Item> SPELLWEAVING_AXE = register("spellweaving_axe", MalumItemProperties::GEAR, (p) -> new SpellweavingAxeItem(SPELLWEAVING_TOOLS, -4, 0, 6, p));

        public static final DeferredItem<Item> RAVENOUS_SCYTHE = register("ravenous_scythe", MalumItemProperties::GEAR, (p) -> new RavenousScytheItem(RAVENOUS, -4f, 0.4f, 2.5f, p));
        public static final DeferredItem<Item> GLUTTONOUS_BLUDGEON = register("gluttonous_bludgeon", MalumItemProperties::GEAR, (p) -> new GluttonousBludgeonItem(RAVENOUS, -2.5f, -3f, 2, p));

        public static final DeferredItem<Item> TYRVING = register("tyrving", MalumItemProperties::GEAR, (p) -> new TyrvingItem(MalumItemTiers.TYRVING, 0, -0.3f, p));

        public static final DeferredItem<Item> MNEMONIC_HEX_STAFF = register("mnemonic_hex_staff", MalumItemProperties::GEAR, (p) -> new HexStaffItem(HEX_STAFF, 5, 1, 2, p));
        public static final DeferredItem<Item> EROSION_SCEPTER = register("erosion_scepter", MalumItemProperties::GEAR, (p) -> new ErosionScepterItem(MALIGNANT_ALLOY, 5, 0.5f, 1, p));

        public static final DeferredItem<Item> WEIGHT_OF_WORLDS = register("weight_of_worlds", MalumItemProperties::GEAR, (p) -> new WeightOfWorldsItem(MalumItemTiers.MALIGNANT_ALLOY, 1, -0.2f, p));
        public static final DeferredItem<Item> EDGE_OF_DELIVERANCE = register("edge_of_deliverance", MalumItemProperties::GEAR, (p) -> new EdgeOfDeliveranceItem(MalumItemTiers.MALIGNANT_ALLOY, 2, -0.1f, p));

        public static final DeferredItem<Item> UNWINDING_CHAOS = register("unwinding_chaos", MalumItemProperties::RELIC, (p) -> new UnwindingChaosStaffItem(HARNESSED_CHAOS, 5, 1.5f, 3, p));
        public static final DeferredItem<Item> SUNDERING_ANCHOR = register("sundering_anchor", MalumItemProperties::RELIC, (p) -> new SunderingAnchorItem(HARNESSED_CHAOS, 4, p));
        public static final DeferredItem<Item> VINDICTIVE_BRAND = register("vindictive_brand", MalumItemProperties::RELIC, VindictiveBrandSwordItem::new);

        public static final DeferredItem<Item> GILDED_RING = register("gilded_ring", MalumItemProperties::GEAR, CurioGildedRing::new);
        public static final DeferredItem<Item> GILDED_BELT = register("gilded_belt", MalumItemProperties::GEAR, CurioGildedBelt::new);
        public static final DeferredItem<Item> ORNATE_RING = register("ornate_ring", MalumItemProperties::GEAR, CurioOrnateRing::new);
        public static final DeferredItem<Item> ORNATE_NECKLACE = register("ornate_necklace", MalumItemProperties::GEAR, CurioOrnateNecklace::new);

        public static final DeferredItem<Item> RUNIC_BROOCH = register("runic_brooch", MalumItemProperties::GEAR, CurioRunicBrooch::new);
        public static final DeferredItem<Item> ELABORATE_BROOCH = register("elaborate_brooch", MalumItemProperties::GEAR, CurioElaborateBrooch::new);
        public static final DeferredItem<Item> GLASS_BROOCH = register("glass_brooch", MalumItemProperties::GEAR, CurioGlassBrooch::new);
        public static final DeferredItem<Item> GLUTTONOUS_BROOCH = register("gluttonous_brooch", MalumItemProperties::GEAR, CurioGluttonousBrooch::new);

        public static final DeferredItem<Item> RING_OF_ESOTERIC_SPOILS = register("ring_of_esoteric_spoils", MalumItemProperties::GEAR, CurioArcaneSpoilRing::new);
        public static final DeferredItem<Item> RING_OF_ESOTERIC_SHADOW = register("ring_of_esoteric_shadow", MalumItemProperties::GEAR, CurioConcealingRing::new);

        public static final DeferredItem<Item> RING_OF_CURATIVE_TALENT = register("ring_of_curative_talent", MalumItemProperties::GEAR, CurioCurativeRing::new);
        public static final DeferredItem<Item> RING_OF_ALCHEMICAL_MASTERY = register("ring_of_alchemical_mastery", MalumItemProperties::GEAR, CurioAlchemicalRing::new);
        public static final DeferredItem<Item> RING_OF_MANAWEAVING = register("ring_of_manaweaving", MalumItemProperties::GEAR, CurioManaweavingRing::new);
        public static final DeferredItem<Item> RING_OF_ARCANE_PROWESS = register("ring_of_arcane_prowess", MalumItemProperties::GEAR, CurioProwessRing::new);

        public static final DeferredItem<Item> RING_OF_DESPERATE_VORACITY = register("ring_of_desperate_voracity", MalumItemProperties::GEAR, CurioVoraciousRing::new);
        public static final DeferredItem<Item> RING_OF_SWARMING_ROT = register("ring_of_swarming_rot", MalumItemProperties::GEAR, CurioSwarmingRing::new);

        public static final DeferredItem<Item> RING_OF_THE_RISING_EDGE = register("ring_of_the_rising_edge", MalumItemProperties::GEAR, CurioRisingEdgeRing::new);
        public static final DeferredItem<Item> RING_OF_THE_HOWLING_MAELSTROM = register("ring_of_the_howling_maelstrom", MalumItemProperties::GEAR, CurioHowlingMaelstromRing::new);

        public static final DeferredItem<Item> RING_OF_HEARTY_AVARICE = register("ring_of_hearty_avarice", MalumItemProperties::GEAR, CurioHeartyAvariceRing::new);
        public static final DeferredItem<Item> RING_OF_HEAVY_DISCHARGE = register("ring_of_heavy_discharge", MalumItemProperties::GEAR, CurioDischargeRing::new);

        public static final DeferredItem<Item> NECKLACE_OF_MYSTIC_POTENCY = register("necklace_of_mystic_potency", MalumItemProperties::GEAR, CurioMysticNecklace::new);
        public static final DeferredItem<Item> NECKLACE_OF_THE_NARROW_EDGE = register("necklace_of_the_narrow_edge", MalumItemProperties::GEAR, CurioNarrowEdgeNecklace::new);
        public static final DeferredItem<Item> NECKLACE_OF_THE_WINDWEAVER = register("necklace_of_the_windweaver", MalumItemProperties::GEAR, CurioWindweaverNecklace::new);

        public static final DeferredItem<Item> BELT_OF_THE_STARVED = register("belt_of_the_starved", MalumItemProperties::GEAR, CurioStarvedBelt::new);
        public static final DeferredItem<Item> BELT_OF_THE_PROSPECTOR = register("belt_of_the_prospector", MalumItemProperties::GEAR, CurioProspectorBelt::new);
        public static final DeferredItem<Item> BELT_OF_THE_TIDEBOUND = register("belt_of_the_tidebound", MalumItemProperties::GEAR, CurioTideboundBelt::new);
        public static final DeferredItem<Item> BELT_OF_OPULENT_INOCULATION = register("belt_of_opulent_inoculation", MalumItemProperties::GEAR, CurioInoculationBelt::new);
        public static final DeferredItem<Item> BELT_OF_THE_MAGEBANE = register("belt_of_the_magebane", MalumItemProperties::GEAR, CurioMagebaneBelt::new);

        public static final DeferredItem<Item> RING_OF_THE_ENDLESS_WELL = register("ring_of_the_endless_well", MalumItemProperties::GEAR, CurioEndlessRing::new);
        public static final DeferredItem<Item> RING_OF_ECHOING_ARCANA = register("ring_of_echoing_arcana", MalumItemProperties::GEAR, CurioEchoingArcanaRing::new);
        public static final DeferredItem<Item> RING_OF_GROWING_FLESH = register("ring_of_growing_flesh", MalumItemProperties::GEAR, CurioGrowingFleshRing::new);
        public static final DeferredItem<Item> RING_OF_GRUESOME_CONCENTRATION = register("ring_of_gruesome_concentration", MalumItemProperties::GEAR, CurioGruesomeConcentrationRing::new);

        public static final DeferredItem<Item> NECKLACE_OF_THE_HIDDEN_BLADE = register("necklace_of_the_hidden_blade", MalumItemProperties::GEAR, CurioHiddenBladeNecklace::new);
        public static final DeferredItem<Item> NECKLACE_OF_THE_WATCHER = register("necklace_of_the_watcher", MalumItemProperties::GEAR, CurioWatcherNecklace::new);

        public static final DeferredItem<Item> BELT_OF_THE_LIMITLESS = register("belt_of_the_limitless", MalumItemProperties::GEAR, CurioLimitlessBelt::new);

        public static final DeferredItem<Item> RUNE_OF_VITALITY = register("rune_of_vitality", MalumItemProperties::GEAR, RuneVitalityItem::new);
        public static final DeferredItem<Item> RUNE_OF_CULLING = register("rune_of_culling", MalumItemProperties::GEAR, RuneCullingItem::new);
        public static final DeferredItem<Item> RUNE_OF_REINFORCEMENT = register("rune_of_reinforcement", MalumItemProperties::GEAR, RuneReinforcementItem::new);
        public static final DeferredItem<Item> RUNE_OF_VOLATILE_DISTORTION = register("rune_of_volatile_distortion", MalumItemProperties::GEAR, RuneVolatileDistortionItem::new);
        public static final DeferredItem<Item> RUNE_OF_DEXTERITY = register("rune_of_dexterity", MalumItemProperties::GEAR, RuneDexterityItem::new);
        public static final DeferredItem<Item> RUNE_OF_AILMENT_CLEANSING = register("rune_of_ailment_cleansing", MalumItemProperties::GEAR, RuneAilmentCleansingItem::new);
        public static final DeferredItem<Item> RUNE_OF_PROTECTION = register("rune_of_protection", MalumItemProperties::GEAR, RuneProtectionItem::new);
        public static final DeferredItem<Item> RUNE_OF_SCORCHING = register("rune_of_scorching", MalumItemProperties::GEAR, RuneScorchingItem::new);

        public static final DeferredItem<Item> RUNE_OF_HOWLING_GALE = register("rune_of_howling_gale", MalumItemProperties::GEAR, RuneHowlingGale::new);
        public static final DeferredItem<Item> RUNE_OF_FLOWING_GRASP = register("rune_of_flowing_grasp", MalumItemProperties::GEAR, RuneFlowingGrasp::new);
        public static final DeferredItem<Item> RUNE_OF_STONE_WARD = register("rune_of_stone_ward", MalumItemProperties::GEAR, RuneStoneWard::new);
        public static final DeferredItem<Item> RUNE_OF_BURNING_FERVOR = register("rune_of_burning_fervor", MalumItemProperties::GEAR, RuneBurningFervor::new);
        public static final DeferredItem<Item> RUNE_OF_SKY_TETHER = register("rune_of_sky_tether", MalumItemProperties::GEAR, RuneSkyTether::new);
        public static final DeferredItem<Item> RUNE_OF_GOOD_TIDES = register("rune_of_good_tides", MalumItemProperties::GEAR, RuneGoodTides::new);
        public static final DeferredItem<Item> RUNE_OF_OAKEN_MIGHT = register("rune_of_oaken_might", MalumItemProperties::GEAR, RuneOakenMight::new);
        public static final DeferredItem<Item> RUNE_OF_FIERY_EMBRACE = register("rune_of_fiery_embrace", MalumItemProperties::GEAR, RuneFieryEmbrace::new);

        public static final DeferredItem<Item> RUNE_OF_BOLSTERING = register("rune_of_bolstering", MalumItemProperties::GEAR, RuneBolsteringItem::new);
        public static final DeferredItem<Item> RUNE_OF_RADIAL_EMPOWERMENT = register("rune_of_radial_empowerment", MalumItemProperties::GEAR, RuneRadialEmpowermentItem::new);
        public static final DeferredItem<Item> RUNE_OF_SPELL_MASTERY = register("rune_of_spell_mastery", MalumItemProperties::GEAR, RuneSpellMasteryItem::new);
        public static final DeferredItem<Item> RUNE_OF_HERESY = register("rune_of_heresy", MalumItemProperties::GEAR, RuneHeresyItem::new);
        public static final DeferredItem<Item> RUNE_OF_UNNATURAL_STAMINA = register("rune_of_unnatural_stamina", MalumItemProperties::GEAR, RuneUnnaturalStaminaItem::new);
        public static final DeferredItem<Item> RUNE_OF_TWINNED_DURATION = register("rune_of_twinned_duration", MalumItemProperties::GEAR, RuneTwinnedDurationItem::new);
        public static final DeferredItem<Item> RUNE_OF_INDOMITABILITY = register("rune_of_indomitability", MalumItemProperties::GEAR, RuneIndomitabilityItem::new);
        public static final DeferredItem<Item> RUNE_OF_IGNEOUS_SOLACE = register("rune_of_igneous_solace", MalumItemProperties::GEAR, RuneIgneousSolaceItem::new);
    }

    public static class Enscription {

        public static void init() {

        }

        public static final BlockBlockItemHolder<Block, BlockItem> MYRIAD_GATEWAY = registerBlock("myriad_gateway", () -> new MyriadGatewayBlock<>(MalumEnscriptionBlockProperties.MYRIAD_GATEWAY()));

        public static final BlockBlockItemHolder<Block, BlockItem> MEDITATING_EFFIGY = registerBlock("meditating_effigy", () -> new MeditatingEffigyBlock(MalumEnscriptionBlockProperties.MEDITATING_EFFIGY()));

        public static final BlockBlockItemHolder<Block, BlockItem> COLUMNAR_FLESH = registerBlock("columnar_flesh", () -> new ColumnarFleshBlock(MalumEnscriptionBlockProperties.FLESH_BLOCK()));
        public static final BlockBlockItemHolder<Block, BlockItem> FLESHBULB = registerBlock("fleshbulb", () -> new FleshBulbBlock(MalumEnscriptionBlockProperties.FLESHBULB()));
        public static final BlockBlockItemHolder<Block, BlockItem> WRITHING_FLESH = registerBlock("writhing_flesh", () -> new WrithingFleshBlock(MalumEnscriptionBlockProperties.WRITHING_FLESH()));

        public static final DeferredItem<Item> SHAPED_SLAB = register("shaped_slab", MalumItemProperties::GEAR, (p) -> new ShapedSlabSwordItem(ARCHAIC_SLATE, 2.5f, -0.8f, p));
        public static final DeferredItem<Item> IRON_CROWN = register("iron_crown", MalumItemProperties::DEFAULT, Item::new);

        public static final BlockBlockItemHolder<Block, BlockItem> WEEPING_WELL = registerBlock("weeping_well", () -> new PrimordialSoupBlock(MalumEnscriptionBlockProperties.VOID_FLUID()));
        public static final BlockBlockItemHolder<Block, BlockItem> WEEPING_WELL_CENTERPIECE = registerBlock("weeping_well_centerpiece", () -> new VoidConduitBlock<>(MalumEnscriptionBlockProperties.VOID_FLUID()));

    }

    public static class Vanity {

        public static void init() {

        }

        public static final DeferredItem<Item> ESOTERIC_SPOOL = register("esoteric_spool", MalumItemProperties::DEFAULT, Item::new);

        public static final DeferredItem<Item> ACE_PRIDEWEAVE = register("ace_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.ACE));
        public static final DeferredItem<Item> AGENDER_PRIDEWEAVE = register("agender_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.AGENDER));
        public static final DeferredItem<Item> ARO_PRIDEWEAVE = register("aro_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.ARO));
        public static final DeferredItem<Item> AROACE_PRIDEWEAVE = register("aroace_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.AROACE));
        public static final DeferredItem<Item> BI_PRIDEWEAVE = register("bi_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.BI));
        public static final DeferredItem<Item> DEMIBOY_PRIDEWEAVE = register("demiboy_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.DEMIBOY));
        public static final DeferredItem<Item> DEMIGIRL_PRIDEWEAVE = register("demigirl_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.DEMIGIRL));
        public static final DeferredItem<Item> ENBY_PRIDEWEAVE = register("enby_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.ENBY));
        public static final DeferredItem<Item> GAY_PRIDEWEAVE = register("gay_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.GAY));
        public static final DeferredItem<Item> GENDERFLUID_PRIDEWEAVE = register("genderfluid_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.GENDERFLUID));
        public static final DeferredItem<Item> GENDERQUEER_PRIDEWEAVE = register("genderqueer_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.GENDERQUEER));
        public static final DeferredItem<Item> INTERSEX_PRIDEWEAVE = register("intersex_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.INTERSEX));
        public static final DeferredItem<Item> LESBIAN_PRIDEWEAVE = register("lesbian_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.LESBIAN));
        public static final DeferredItem<Item> PAN_PRIDEWEAVE = register("pan_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.PAN));
        public static final DeferredItem<Item> PLURAL_PRIDEWEAVE = register("plural_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.PLURAL));
        public static final DeferredItem<Item> POLY_PRIDEWEAVE = register("poly_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.POLY));
        public static final DeferredItem<Item> PRIDE_PRIDEWEAVE = register("pride_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.PRIDE));
        public static final DeferredItem<Item> TRANS_PRIDEWEAVE = register("trans_prideweave", MalumItemProperties::DEFAULT, p -> skinHoldingItem(p, ItemSkinComponent.TRANS));

    }

    public static <T extends Block> BlockBlockItemHolder<T, BlockItem> registerBlock(String name, Supplier<T> supplier) {
        return registerBlock(name, name, supplier, BlockItem::new);
    }

    public static <T extends Block> BlockBlockItemHolder<T, BlockItem> registerBlock(String name, LodestoneBlockProperties properties, Function<LodestoneBlockProperties, T> supplier) {
        return registerBlock(name, name, () -> supplier.apply(properties), BlockItem::new);
    }

    public static <T extends Block> BlockBlockItemHolder<T, MultiBlockItem> registerMultiBlock(String name, Supplier<T> supplier, Supplier<? extends MultiBlockStructure> structure) {
        return registerBlock(name, name, supplier, (b, p) -> new MultiBlockItem(b, p, structure));
    }

    public static <T extends Block> BlockBlockItemHolder<T, BlockItem> registerItemNameBlock(String blockName, String itemName, Supplier<T> supplier) {
        return registerBlock(blockName, itemName, supplier, ItemNameBlockItem::new);
    }

    public static <T extends Block, K extends BlockItem> BlockBlockItemHolder<T, K> registerBlock(String name, Supplier<T> blockSupplier, BiFunction<Block, LodestoneItemProperties, K> itemSupplier) {
        return registerBlock(name, name, blockSupplier, itemSupplier);
    }

    public static <T extends Block, K extends BlockItem> BlockBlockItemHolder<T, K> registerBlock(String blockName, String itemName, Supplier<T> blockSupplier, BiFunction<Block, LodestoneItemProperties, K> itemSupplier) {
        var block = BLOCKS.register(blockName, blockSupplier);
        var item = register(itemName, MalumItemProperties::DEFAULT, p -> itemSupplier.apply(block.get(), p));
        return new BlockBlockItemHolder<>(block, item);
    }

    public static <T extends Block> DeferredBlock<T> registerBlockNoItem(String name, Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }

    private static Block flowerPot(BlockBlockItemHolder<Block, BlockItem> potted) {
        return new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, potted, POTTED_PLANT());
    }

    public static void addPottedBlocks(FMLCommonSetupEvent event) {
        FlowerPotBlock flowerPot = (FlowerPotBlock) Blocks.FLOWER_POT;
        flowerPot.addPlant(BuildingBlocks.RUNEWOOD_SAPLING.block().getId(), BuildingBlocks.POTTED_RUNEWOOD_SAPLING);
        flowerPot.addPlant(BuildingBlocks.AZURE_RUNEWOOD_SAPLING.block().getId(), BuildingBlocks.POTTED_AZURE_RUNEWOOD_SAPLING);
        flowerPot.addPlant(BuildingBlocks.SOULWOOD_SAPLING.block().getId(), BuildingBlocks.POTTED_SOULWOOD_SAPLING);
        flowerPot.addPlant(Blight.BLIGHTROOT.block().getId(), BuildingBlocks.POTTED_BLIGHTROOT);
        flowerPot.addPlant(Blight.BLIGHTPEARL.block().getId(), BuildingBlocks.POTTED_BLIGHTPEARL);
        flowerPot.addPlant(Blight.STRANGEROOT.block().getId(), BuildingBlocks.POTTED_STRANGEROOT);
    }
}
