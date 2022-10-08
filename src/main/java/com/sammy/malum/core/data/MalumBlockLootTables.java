package com.sammy.malum.core.data;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.storage.SoulVialBlock;
import com.sammy.malum.common.block.storage.SpiritJarBlock;
import com.sammy.malum.common.item.ether.EtherItem;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.sammy.malum.core.setup.content.block.BlockRegistry.BLOCKS;
import static team.lodestar.lodestone.helpers.DataHelper.take;
import static team.lodestar.lodestone.helpers.DataHelper.takeAll;

public class MalumBlockLootTables extends LootTableProvider {

    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());
    private static final float[] MAGIC_SAPLING_DROP_CHANCE = new float[]{0.015F, 0.0225F, 0.033333336F, 0.05F};


    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = new ArrayList<>();

    public MalumBlockLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    public String getName() {
        return "Malum Loot Tables";
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());

        takeAll(blocks, b -> b.get().properties instanceof LodestoneBlockProperties && ((LodestoneBlockProperties) b.get().properties).getThrowawayData().hasCustomLoot);

        add(take(blocks, BlockRegistry.RUNEWOOD_LEAVES).get(), (b)->createLeavesDrops(b, BlockRegistry.RUNEWOOD_SAPLING.get(), MAGIC_SAPLING_DROP_CHANCE));
        add(take(blocks, BlockRegistry.SOULWOOD_LEAVES).get(), (b)->createLeavesDrops(b, BlockRegistry.SOULWOOD_GROWTH.get(), MAGIC_SAPLING_DROP_CHANCE));

        add(take(blocks, BlockRegistry.BLIGHTED_SOULWOOD).get(), createSingleItemTableWithSilkTouch(BlockRegistry.BLIGHTED_SOULWOOD.get(), ItemRegistry.SOULWOOD_LOG.get()));
        add(take(blocks, BlockRegistry.BRILLIANT_STONE).get(), createOreDrop(BlockRegistry.BRILLIANT_STONE.get(), ItemRegistry.CLUSTER_OF_BRILLIANCE.get()));
        add(take(blocks, BlockRegistry.BRILLIANT_DEEPSLATE).get(), createOreDrop(BlockRegistry.BRILLIANT_DEEPSLATE.get(), ItemRegistry.CLUSTER_OF_BRILLIANCE.get()));
        add(take(blocks, BlockRegistry.SOULSTONE_ORE).get(), createOreDrop(BlockRegistry.SOULSTONE_ORE.get(), ItemRegistry.RAW_SOULSTONE.get()));
        add(take(blocks, BlockRegistry.DEEPSLATE_SOULSTONE_ORE).get(), createOreDrop(BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get(), ItemRegistry.RAW_SOULSTONE.get()));
        add(take(blocks, BlockRegistry.BLAZING_QUARTZ_ORE).get(), createOreDrop(BlockRegistry.BLAZING_QUARTZ_ORE.get(), ItemRegistry.BLAZING_QUARTZ.get()));
        add(take(blocks, BlockRegistry.NATURAL_QUARTZ_ORE).get(), createOreDrop(BlockRegistry.NATURAL_QUARTZ_ORE.get(), ItemRegistry.NATURAL_QUARTZ.get()));
        add(take(blocks, BlockRegistry.DEEPSLATE_QUARTZ_ORE).get(), createOreDrop(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get(), ItemRegistry.NATURAL_QUARTZ.get()));
        add(take(blocks, BlockRegistry.BLOCK_OF_RARE_EARTHS).get(), createSingleItemTableWithSilkTouch(BlockRegistry.BLOCK_OF_RARE_EARTHS.get(), ItemRegistry.RARE_EARTHS.get()));

        takeAll(blocks, b -> b.get().asItem() instanceof EtherItem etherItem && etherItem.iridescent);

        takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(b -> add(b.get(), createSingleItemTable(b.get().asItem())));
        takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(b -> add(b.get(), createSingleItemTableWithSilkTouchOrShears(b.get(), b.get().asItem())));
        takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(b -> add(b.get(), createSingleItemTableWithSilkTouchOrShears(b.get(), b.get().asItem())));

        takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(b -> add(b.get(), createSingleItemTableWithSilkTouch(b.get(), Items.DIRT)));
        takeAll(blocks, b -> b.get() instanceof SlabBlock).forEach(b -> add(b.get(), createSlabItemTable(b.get())));
        takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(b -> add(b.get(), createDoorTable(b.get())));

        takeAll(blocks, b -> b.get() instanceof EtherBlock).forEach(b -> add(b.get(), createEtherDrop(b.get())));
        takeAll(blocks, b -> b.get() instanceof SpiritJarBlock).forEach(b -> add(b.get(), createJarDrop(b.get())));
        takeAll(blocks, b -> b.get() instanceof SoulVialBlock).forEach(b -> add(b.get(), createVialDrop(b.get())));

        takeAll(blocks, b -> true).forEach(b -> add(b.get(), createSingleItemTable(b.get().asItem())));

        return tables;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((loc, table) -> LootTables.validate(validationtracker, loc, table));
    }

    protected static <T> T applyExplosionDecay(ItemLike p_124132_, FunctionUserBuilder<T> p_124133_) {
        return (T)(!EXPLOSION_RESISTANT.contains(p_124132_.asItem()) ? p_124133_.apply(ApplyExplosionDecay.explosionDecay()) : p_124133_.unwrap());
    }

    protected static <T> T applyExplosionCondition(ItemLike p_124135_, ConditionUserBuilder<T> p_124136_) {
        return (T)(!EXPLOSION_RESISTANT.contains(p_124135_.asItem()) ? p_124136_.when(ExplosionCondition.survivesExplosion()) : p_124136_.unwrap());
    }

    protected static LootTable.Builder createEtherDrop(Block block) {
        return LootTable.lootTable().withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("firstColor", "display.firstColor").copy("secondColor", "display.secondColor")))));
    }

    protected static LootTable.Builder createJarDrop(Block block) {
        return LootTable.lootTable().withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("spirit", "spirit").copy("count", "count")))));
    }

    protected static LootTable.Builder createVialDrop(Block block) {
        return LootTable.lootTable().withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("spirit_data", "spirit_data")))));
    }

    protected static LootTable.Builder createSingleItemTable(ItemLike p_124127_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_124127_, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_124127_))));
    }

    protected static LootTable.Builder createSelfDropDispatchTable(Block p_124172_, LootItemCondition.Builder p_124173_, LootPoolEntryContainer.Builder<?> p_124174_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_124172_).when(p_124173_).otherwise(p_124174_)));
    }

    protected static LootTable.Builder createSilkTouchDispatchTable(Block p_124169_, LootPoolEntryContainer.Builder<?> p_124170_) {
        return createSelfDropDispatchTable(p_124169_, HAS_SILK_TOUCH, p_124170_);
    }

    protected static LootTable.Builder createShearsDispatchTable(Block p_124268_, LootPoolEntryContainer.Builder<?> p_124269_) {
        return createSelfDropDispatchTable(p_124268_, HAS_SHEARS, p_124269_);
    }

    protected static LootTable.Builder createSilkTouchOrShearsDispatchTable(Block p_124284_, LootPoolEntryContainer.Builder<?> p_124285_) {
        return createSelfDropDispatchTable(p_124284_, HAS_SHEARS_OR_SILK_TOUCH, p_124285_);
    }

    protected static LootTable.Builder createSingleItemTableWithSilkTouch(Block p_124258_, ItemLike p_124259_) {
        return createSilkTouchDispatchTable(p_124258_, applyExplosionCondition(p_124258_, LootItem.lootTableItem(p_124259_)));
    }

    protected static LootTable.Builder createSingleItemTableWithSilkTouchOrShears(Block p_124258_, ItemLike p_124259_) {
        return createSilkTouchOrShearsDispatchTable(p_124258_, applyExplosionCondition(p_124258_, LootItem.lootTableItem(p_124259_)));
    }

    protected static LootTable.Builder createSingleItemTable(ItemLike p_176040_, NumberProvider p_176041_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(p_176040_, LootItem.lootTableItem(p_176040_).apply(SetItemCountFunction.setCount(p_176041_)))));
    }

    protected static LootTable.Builder createSingleItemTableWithSilkTouch(Block p_176043_, ItemLike p_176044_, NumberProvider p_176045_) {
        return createSilkTouchDispatchTable(p_176043_, applyExplosionDecay(p_176043_, LootItem.lootTableItem(p_176044_).apply(SetItemCountFunction.setCount(p_176045_))));
    }

    protected static LootTable.Builder createSilkTouchOnlyTable(ItemLike p_124251_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SILK_TOUCH).setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_124251_)));
    }

    protected static LootTable.Builder createPotFlowerItemTable(ItemLike p_124271_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(Blocks.FLOWER_POT, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Blocks.FLOWER_POT)))).withPool(applyExplosionCondition(p_124271_, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_124271_))));
    }

    protected static LootTable.Builder createSlabItemTable(Block p_124291_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(p_124291_, LootItem.lootTableItem(p_124291_).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124291_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    protected static <T extends Comparable<T> & StringRepresentable> LootTable.Builder createSinglePropConditionTable(Block p_124162_, Property<T> p_124163_, T p_124164_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_124162_, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_124162_).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124162_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(p_124163_, p_124164_))))));
    }

    protected static LootTable.Builder createNameableBlockEntityTable(Block p_124293_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_124293_, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_124293_).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)))));
    }

    protected static LootTable.Builder createShulkerBoxDrop(Block p_124295_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_124295_, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_124295_).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Lock", "BlockEntityTag.Lock").copy("LootTable", "BlockEntityTag.LootTable").copy("LootTableSeed", "BlockEntityTag.LootTableSeed")).apply(SetContainerContents.setContents(BlockEntityType.SHULKER_BOX).withEntry(DynamicLoot.dynamicEntry(ShulkerBoxBlock.CONTENTS))))));
    }

    protected static LootTable.Builder createCopperOreDrops(Block p_176047_) {
        return createSilkTouchDispatchTable(p_176047_, applyExplosionDecay(p_176047_, LootItem.lootTableItem(Items.RAW_COPPER).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected static LootTable.Builder createLapisOreDrops(Block p_176049_) {
        return createSilkTouchDispatchTable(p_176049_, applyExplosionDecay(p_176049_, LootItem.lootTableItem(Items.LAPIS_LAZULI).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected static LootTable.Builder createRedstoneOreDrops(Block p_176051_) {
        return createSilkTouchDispatchTable(p_176051_, applyExplosionDecay(p_176051_, LootItem.lootTableItem(Items.REDSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 5.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected static LootTable.Builder createBannerDrop(Block p_124297_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_124297_, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_124297_).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Patterns", "BlockEntityTag.Patterns")))));
    }

    protected static LootTable.Builder createBeeNestDrop(Block p_124299_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SILK_TOUCH).setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_124299_).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Bees", "BlockEntityTag.Bees")).apply(CopyBlockState.copyState(p_124299_).copy(BeehiveBlock.HONEY_LEVEL))));
    }

    protected static LootTable.Builder createBeeHiveDrop(Block p_124301_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_124301_).when(HAS_SILK_TOUCH).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Bees", "BlockEntityTag.Bees")).apply(CopyBlockState.copyState(p_124301_).copy(BeehiveBlock.HONEY_LEVEL)).otherwise(LootItem.lootTableItem(p_124301_))));
    }

    protected static LootTable.Builder createCaveVinesDrop(Block p_176053_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.GLOW_BERRIES)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_176053_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CaveVines.BERRIES, true))));
    }

    protected static LootTable.Builder createOreDrop(Block p_124140_, Item p_124141_) {
        return createSilkTouchDispatchTable(p_124140_, applyExplosionDecay(p_124140_, LootItem.lootTableItem(p_124141_).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected static LootTable.Builder createMushroomBlockDrop(Block p_124278_, ItemLike p_124279_) {
        return createSilkTouchDispatchTable(p_124278_, applyExplosionDecay(p_124278_, LootItem.lootTableItem(p_124279_).apply(SetItemCountFunction.setCount(UniformGenerator.between(-6.0F, 2.0F))).apply(LimitCount.limitCount(IntRange.lowerBound(0)))));
    }

    protected static LootTable.Builder createGrassDrops(Block p_124303_) {
        return createShearsDispatchTable(p_124303_, applyExplosionDecay(p_124303_, LootItem.lootTableItem(Items.WHEAT_SEEDS).when(LootItemRandomChanceCondition.randomChance(0.125F)).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))));
    }

    protected static LootTable.Builder createStemDrops(Block p_124255_, Item p_124256_) {
        return LootTable.lootTable().withPool(applyExplosionDecay(p_124255_, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_124256_).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.06666667F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124255_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 0)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.13333334F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124255_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 1)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.2F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124255_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 2)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.26666668F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124255_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 3)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.33333334F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124255_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 4)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.4F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124255_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 5)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.46666667F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124255_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 6)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.53333336F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124255_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 7)))))));
    }

    protected static LootTable.Builder createAttachedStemDrops(Block p_124275_, Item p_124276_) {
        return LootTable.lootTable().withPool(applyExplosionDecay(p_124275_, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_124276_).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.53333336F))))));
    }

    protected static LootTable.Builder createShearsOnlyDrop(ItemLike p_124287_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_SHEARS).add(LootItem.lootTableItem(p_124287_)));
    }

    protected static LootTable.Builder createGlowLichenDrops(Block p_176055_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().add(applyExplosionDecay(p_176055_, LootItem.lootTableItem(p_176055_).when(HAS_SHEARS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_176055_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.EAST, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_176055_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.WEST, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_176055_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.NORTH, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_176055_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.SOUTH, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_176055_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.UP, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_176055_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.DOWN, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1.0F), true)))));
    }

    protected static LootTable.Builder createLeavesDrops(Block p_124158_, Block p_124159_, float... p_124160_) {
        return createSilkTouchOrShearsDispatchTable(p_124158_, applyExplosionCondition(p_124158_, LootItem.lootTableItem(p_124159_)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, p_124160_))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionDecay(p_124158_, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }

    protected static LootTable.Builder createOakLeavesDrops(Block p_124264_, Block p_124265_, float... p_124266_) {
        return createLeavesDrops(p_124264_, p_124265_, p_124266_).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionCondition(p_124264_, LootItem.lootTableItem(Items.APPLE)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

    protected static LootTable.Builder createCropDrops(Block p_124143_, Item p_124144_, Item p_124145_, LootItemCondition.Builder p_124146_) {
        return applyExplosionDecay(p_124143_, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(p_124144_).when(p_124146_).otherwise(LootItem.lootTableItem(p_124145_)))).withPool(LootPool.lootPool().when(p_124146_).add(LootItem.lootTableItem(p_124145_).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
    }

    protected static LootTable.Builder createDoublePlantShearsDrop(Block p_124305_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SHEARS).add(LootItem.lootTableItem(p_124305_).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))));
    }

    protected static LootTable.Builder createDoublePlantWithSeedDrops(Block p_124261_, Block p_124262_) {
        LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(p_124262_).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))).when(HAS_SHEARS).otherwise(applyExplosionCondition(p_124261_, LootItem.lootTableItem(Items.WHEAT_SEEDS)).when(LootItemRandomChanceCondition.randomChance(0.125F)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124261_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(p_124261_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124261_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(p_124261_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }

    protected static LootTable.Builder createCandleDrops(Block p_176057_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(p_176057_, LootItem.lootTableItem(p_176057_).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_176057_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CandleBlock.CANDLES, 2)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_176057_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CandleBlock.CANDLES, 3)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_176057_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CandleBlock.CANDLES, 4)))))));
    }

    protected static LootTable.Builder createCandleCakeDrops(Block p_176059_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_176059_)));
    }

    public static LootTable.Builder noDrop() {
        return LootTable.lootTable();
    }

    public static LootTable.Builder createDoorTable(Block p_124138_) {
        return createSinglePropConditionTable(p_124138_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }

    protected Iterable<Block> getKnownBlocks() {
        return Registry.BLOCK;
    }

    public void dropPottedContents(Block p_124253_) {
        this.add(p_124253_, (p_176061_) -> {
            return createPotFlowerItemTable(((FlowerPotBlock)p_176061_).getContent());
        });
    }

    public void otherWhenSilkTouch(Block p_124155_, Block p_124156_) {
        this.add(p_124155_, createSilkTouchOnlyTable(p_124156_));
    }

    public void dropOther(Block p_124148_, ItemLike p_124149_) {
        this.add(p_124148_, createSingleItemTable(p_124149_));
    }

    public void dropWhenSilkTouch(Block p_124273_) {
        this.otherWhenSilkTouch(p_124273_, p_124273_);
    }

    public void dropSelf(Block p_124289_) {
        this.dropOther(p_124289_, p_124289_);
    }

    protected void add(Block p_124176_, Function<Block, LootTable.Builder> p_124177_) {
        this.add(p_124176_, p_124177_.apply(p_124176_));
    }

    protected void add(Block block, LootTable.Builder builder) {
        this.add(block.getLootTable(), builder);
    }

    protected void add(ResourceLocation path, LootTable.Builder lootTable) {
        tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), LootContextParamSets.BLOCK));
    }
}