package com.sammy.malum.core.data;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.core.registry.block.BlockRegistry;
import com.sammy.malum.core.registry.item.ItemRegistry;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
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
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.sammy.malum.MalumHelper.takeAll;
import static com.sammy.malum.core.registry.block.BlockRegistry.BLOCKS;

public class MalumBlockLootTables extends LootTableProvider {
    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());
    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    private static final float[] JUNGLE_LEAVES_SAPLING_CHANGES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
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
        blocks.remove(BlockRegistry.BLAZING_QUARTZ_ORE);
        registerLootTable(BlockRegistry.BLAZING_QUARTZ_ORE.get(), createOreDrop(BlockRegistry.BLAZING_QUARTZ_ORE.get(), ItemRegistry.BLAZING_QUARTZ.get()));
        blocks.remove(BlockRegistry.SOULSTONE_ORE);
        registerLootTable(BlockRegistry.SOULSTONE_ORE.get(), createOreDrop(BlockRegistry.SOULSTONE_ORE.get(), ItemRegistry.SOULSTONE_CLUSTER.get()));
        blocks.remove(BlockRegistry.BRILLIANT_STONE);
        registerLootTable(BlockRegistry.BRILLIANT_STONE.get(), createOreDrop(BlockRegistry.BRILLIANT_STONE.get(), ItemRegistry.BRILLIANCE_CLUSTER.get()));


        takeAll(blocks, b -> b.get() instanceof TotemPoleBlock);
        takeAll(blocks, b -> b.get() instanceof EtherBlock);
        takeAll(blocks, b -> b.get() instanceof WallTorchBlock);
        takeAll(blocks, b -> b.get() instanceof LeavesBlock);
        takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(b -> registerLootTable(b.get(), createSingleItemTable(b.get().asItem())));
        takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(b -> registerLootTable(b.get(), createSilkTouchOrShearsTable(b.get().asItem())));
        takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(b -> registerLootTable(b.get(), createSilkTouchOrShearsTable(b.get().asItem())));

        takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(b -> registerLootTable(b.get(), createSingleItemTableWithSilkTouch(b.get(), Items.DIRT)));
        takeAll(blocks, b -> b.get() instanceof SlabBlock).forEach(b -> registerLootTable(b.get(), createSlabItemTable(b.get())));
        takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(b -> registerLootTable(b.get(), createDoorTable(b.get())));

        takeAll(blocks, b -> true).forEach(b -> registerLootTable(b.get(), createSingleItemTable(b.get().asItem())));
        return tables;
    }

    //everything below sucks a lot, don't go down there.

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((loc, table) -> LootTables.validate(validationtracker, loc, table));
    }

    protected static <T> T applyExplosionDecay(ItemLike p_218552_0_, FunctionUserBuilder<T> p_218552_1_) {
        return !EXPLOSION_RESISTANT.contains(p_218552_0_.asItem()) ? p_218552_1_.apply(ApplyExplosionDecay.explosionDecay()) : p_218552_1_.unwrap();
    }

    protected static <T> T applyExplosionCondition(ItemLike p_218560_0_, ConditionUserBuilder<T> p_218560_1_) {
        return !EXPLOSION_RESISTANT.contains(p_218560_0_.asItem()) ? p_218560_1_.when(ExplosionCondition.survivesExplosion()) : p_218560_1_.unwrap();
    }

    protected static LootTable.Builder createSingleItemTable(ItemLike p_218546_0_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_218546_0_, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_218546_0_))));
    }

    protected static LootTable.Builder createSelfDropDispatchTable(Block p_218494_0_, LootItemCondition.Builder p_218494_1_, LootPoolEntryContainer.Builder<?> p_218494_2_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_218494_0_).when(p_218494_1_).otherwise(p_218494_2_)));
    }

    protected static LootTable.Builder createSilkTouchDispatchTable(Block p_218519_0_, LootPoolEntryContainer.Builder<?> p_218519_1_) {
        return createSelfDropDispatchTable(p_218519_0_, HAS_SILK_TOUCH, p_218519_1_);
    }

    protected static LootTable.Builder createShearsDispatchTable(Block p_218511_0_, LootPoolEntryContainer.Builder<?> p_218511_1_) {
        return createSelfDropDispatchTable(p_218511_0_, HAS_SHEARS, p_218511_1_);
    }

    protected static LootTable.Builder createSilkTouchOrShearsDispatchTable(Block p_218535_0_, LootPoolEntryContainer.Builder<?> p_218535_1_) {
        return createSelfDropDispatchTable(p_218535_0_, HAS_SHEARS_OR_SILK_TOUCH, p_218535_1_);
    }

    protected static LootTable.Builder createSingleItemTableWithSilkTouch(Block p_218515_0_, ItemLike p_218515_1_) {
        return createSilkTouchDispatchTable(p_218515_0_, applyExplosionCondition(p_218515_0_, LootItem.lootTableItem(p_218515_1_)));
    }

    protected static LootTable.Builder createSingleItemTable(ItemLike p_218463_0_, UniformGenerator p_218463_1_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(applyExplosionDecay(p_218463_0_, LootItem.lootTableItem(p_218463_0_).apply(SetItemCountFunction.setCount(p_218463_1_)))));
    }

    protected static LootTable.Builder createSingleItemTableWithSilkTouch(Block p_218530_0_, ItemLike p_218530_1_, UniformGenerator p_218530_2_) {
        return createSilkTouchDispatchTable(p_218530_0_, applyExplosionDecay(p_218530_0_, LootItem.lootTableItem(p_218530_1_).apply(SetItemCountFunction.setCount(p_218530_2_))));
    }

    protected static LootTable.Builder createSilkTouchOnlyTable(ItemLike p_218561_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SILK_TOUCH).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_218561_0_)));
    }

    protected static LootTable.Builder createSilkTouchOrShearsTable(ItemLike p_218561_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SHEARS_OR_SILK_TOUCH).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_218561_0_)));
    }

    protected static LootTable.Builder createPotFlowerItemTable(ItemLike p_218523_0_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(Blocks.FLOWER_POT, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Blocks.FLOWER_POT)))).withPool(applyExplosionCondition(p_218523_0_, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_218523_0_))));
    }

    protected static LootTable.Builder createSlabItemTable(Block p_218513_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(applyExplosionDecay(p_218513_0_, LootItem.lootTableItem(p_218513_0_).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_218513_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    protected static <T extends Comparable<T> & StringRepresentable> LootTable.Builder createSinglePropConditionTable(Block p_218562_0_, EnumProperty<DoubleBlockHalf> p_218562_1_, DoubleBlockHalf p_218562_2_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_218562_0_, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_218562_0_).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_218562_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(p_218562_1_, p_218562_2_))))));
    }

    protected static LootTable.Builder createNameableBlockEntityTable(Block p_218481_0_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_218481_0_, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_218481_0_).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)))));
    }

    protected static LootTable.Builder createShulkerBoxDrop(Block p_218544_0_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_218544_0_, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_218544_0_).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Lock", "BlockEntityTag.Lock").copy("LootTable", "BlockEntityTag.LootTable").copy("LootTableSeed", "BlockEntityTag.LootTableSeed")).apply(SetContainerContents.setContents().withEntry(DynamicLoot.dynamicEntry(ShulkerBoxBlock.CONTENTS))))));
    }

    protected static LootTable.Builder createBannerDrop(Block p_218559_0_) {
        return LootTable.lootTable().withPool(applyExplosionCondition(p_218559_0_, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_218559_0_).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Patterns", "BlockEntityTag.Patterns")))));
    }

    private static LootTable.Builder createBeeNestDrop(Block p_229436_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SILK_TOUCH).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_229436_0_).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Bees", "BlockEntityTag.Bees")).apply(CopyBlockState.copyState(p_229436_0_).copy(BeehiveBlock.HONEY_LEVEL))));
    }

    private static LootTable.Builder createBeeHiveDrop(Block p_229437_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_229437_0_).when(HAS_SILK_TOUCH).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Bees", "BlockEntityTag.Bees")).apply(CopyBlockState.copyState(p_229437_0_).copy(BeehiveBlock.HONEY_LEVEL)).otherwise(LootItem.lootTableItem(p_229437_0_))));
    }

    protected static LootTable.Builder createOreDrop(Block p_218476_0_, Item p_218476_1_) {
        return createSilkTouchDispatchTable(p_218476_0_, applyExplosionDecay(p_218476_0_, LootItem.lootTableItem(p_218476_1_).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected static LootTable.Builder createMushroomBlockDrop(Block p_218491_0_, ItemLike p_218491_1_) {
        return createSilkTouchDispatchTable(p_218491_0_, applyExplosionDecay(p_218491_0_, LootItem.lootTableItem(p_218491_1_).apply(SetItemCountFunction.setCount(UniformGenerator.between(-6.0F, 2.0F))).apply(LimitCount.limitCount(IntRange.lowerBound(0)))));
    }

    protected static LootTable.Builder createGrassDrops(Block p_218570_0_) {
        return createShearsDispatchTable(p_218570_0_, applyExplosionDecay(p_218570_0_, LootItem.lootTableItem(Items.WHEAT_SEEDS).when(LootItemRandomChanceCondition.randomChance(0.125F)).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))));
    }

    protected static LootTable.Builder createStemDrops(Block p_218475_0_, Item p_218475_1_) {
        return LootTable.lootTable().withPool(applyExplosionDecay(p_218475_0_, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_218475_1_).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.06666667F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 0)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.13333334F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 1)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.2F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 2)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.26666668F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 3)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.33333334F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 4)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.4F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 5)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.46666667F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 6)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.53333336F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_218475_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 7)))))));
    }

    private static LootTable.Builder createAttachedStemDrops(Block p_229435_0_, Item p_229435_1_) {
        return LootTable.lootTable().withPool(applyExplosionDecay(p_229435_0_, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(p_229435_1_).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.53333336F))))));
    }

    protected static LootTable.Builder createShearsOnlyDrop(ItemLike p_218486_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(HAS_SHEARS).add(LootItem.lootTableItem(p_218486_0_)));
    }

    protected static LootTable.Builder createLeavesDrops(Block p_218540_0_, Block p_218540_1_, float... p_218540_2_) {
        return createSilkTouchOrShearsDispatchTable(p_218540_0_, applyExplosionCondition(p_218540_0_, LootItem.lootTableItem(p_218540_1_)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, p_218540_2_))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionDecay(p_218540_0_, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }

    protected static LootTable.Builder createOakLeavesDrops(Block p_218526_0_, Block p_218526_1_, float... p_218526_2_) {
        return createLeavesDrops(p_218526_0_, p_218526_1_, p_218526_2_).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionCondition(p_218526_0_, LootItem.lootTableItem(Items.APPLE)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

    protected static LootTable.Builder createCropDrops(Block p_218541_0_, Item p_218541_1_, Item p_218541_2_, LootItemCondition.Builder p_218541_3_) {
        return applyExplosionDecay(p_218541_0_, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(p_218541_1_).when(p_218541_3_).otherwise(LootItem.lootTableItem(p_218541_2_)))).withPool(LootPool.lootPool().when(p_218541_3_).add(LootItem.lootTableItem(p_218541_2_).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
    }

    private static LootTable.Builder createDoublePlantShearsDrop(Block p_241750_0_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SHEARS).add(LootItem.lootTableItem(p_241750_0_).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)))));
    }

    private static LootTable.Builder createDoublePlantWithSeedDrops(Block p_241749_0_, Block p_241749_1_) {
        LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(p_241749_1_).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))).when(HAS_SHEARS).otherwise(applyExplosionCondition(p_241749_0_, LootItem.lootTableItem(Items.WHEAT_SEEDS)).when(LootItemRandomChanceCondition.randomChance(0.125F)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_241749_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(p_241749_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_241749_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(p_241749_0_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }

    public static LootTable.Builder createDoorTable(Block p_239829_0_) {
        return createSinglePropConditionTable(p_239829_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }

    public static LootTable.Builder noDrop() {
        return LootTable.lootTable();
    }

    protected void registerLootTable(Block blockIn, LootTable.Builder table) {
        addTable(blockIn.getLootTable(), table);
    }

    protected void addTable(ResourceLocation path, LootTable.Builder lootTable) {
        tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), LootContextParamSets.BLOCK));
    }
}