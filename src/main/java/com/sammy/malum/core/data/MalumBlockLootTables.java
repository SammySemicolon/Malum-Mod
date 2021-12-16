package com.sammy.malum.core.data;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.core.registry.block.BlockRegistry;
import com.sammy.malum.core.registry.item.ItemRegistry;
import com.sammy.malum.core.systems.multiblock.BoundingBlock;
import net.minecraft.advancements.criterion.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.state.Property;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ItemLike;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraftforge.fmllegacy.RegistryObject;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.sammy.malum.MalumHelper.takeAll;
import static com.sammy.malum.core.registry.block.BlockRegistry.BLOCKS;

public class MalumBlockLootTables extends LootTableProvider
{
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = new ArrayList<>();
    private static final Set<Item> IMMUNE_TO_EXPLOSIONS = Stream.of(BlockRegistry.SPIRIT_ALTAR, BlockRegistry.SPIRIT_JAR).map(c -> c.get().asItem()).collect(ImmutableSet.toImmutableSet());
    
    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.or(SILK_TOUCH);
    private static final ILootCondition.IBuilder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.invert();
    private static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
    
    public MalumBlockLootTables(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
    }

    @Override
    public String getName()
    {
        return "Malum Loot Tables";
    }
    
    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker)
    {
        map.forEach((loc, table) -> LootTableManager.validate(validationtracker, loc, table));
    }
    
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        blocks.remove(BlockRegistry.BLAZING_QUARTZ_ORE);
        registerLootTable(BlockRegistry.BLAZING_QUARTZ_ORE.get(), droppingItemWithFortune(BlockRegistry.BLAZING_QUARTZ_ORE.get(), ItemRegistry.BLAZING_QUARTZ.get()));
        blocks.remove(BlockRegistry.SOULSTONE_ORE);
        registerLootTable(BlockRegistry.SOULSTONE_ORE.get(), droppingItemWithFortune(BlockRegistry.SOULSTONE_ORE.get(), ItemRegistry.SOULSTONE_CLUSTER.get()));
        blocks.remove(BlockRegistry.BRILLIANT_STONE);
        registerLootTable(BlockRegistry.BRILLIANT_STONE.get(), droppingItemWithFortune(BlockRegistry.BRILLIANT_STONE.get(), ItemRegistry.BRILLIANCE_CLUSTER.get()));


        takeAll(blocks, b -> b.get() instanceof TotemPoleBlock);
        takeAll(blocks, b -> b.get() instanceof EtherBlock);
        takeAll(blocks, b -> b.get() instanceof WallTorchBlock);
        takeAll(blocks, b -> b.get() instanceof BoundingBlock);
        takeAll(blocks, b -> b.get() instanceof LeavesBlock);
        takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(b -> registerLootTable(b.get(), dropping(b.get().asItem())));
        takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(b -> registerLootTable(b.get(), onlyWithSilkTouchOrShears(b.get().asItem())));
        takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(b -> registerLootTable(b.get(), onlyWithSilkTouchOrShears(b.get().asItem())));

        takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(b -> registerLootTable(b.get(), droppingWithSilkTouch(b.get(), Items.DIRT)));
        takeAll(blocks, b -> b.get() instanceof SlabBlock).forEach(b -> registerLootTable(b.get(), droppingSlab(b.get())));
        takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(b -> registerLootTable(b.get(), droppingDoor(b.get())));
    
        takeAll(blocks, b -> true).forEach(b -> registerLootTable(b.get(), dropping(b.get().asItem())));
    
        registerLootTable(BlockRegistry.RUNEWOOD_LEAVES.get(), droppingWithChancesAndSticks(BlockRegistry.RUNEWOOD_LEAVES.get(), BlockRegistry.RUNEWOOD_SAPLING.get(), RARE_SAPLING_DROP_RATES));
        return tables;
    }
    
    protected static <T> T withExplosionDecay(ItemLike item, ILootFunctionConsumer<T> function)
    {
        return (T) (!IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? function.apply(ExplosionDecay.explosionDecay()) : function.unwrap());
    }
    
    protected static <T> T withSurvivesExplosion(ItemLike item, ILootConditionConsumer<T> condition)
    {
        return (T) (!IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? condition.when(SurvivesExplosion.survivesExplosion()) : condition.unwrap());
    }
    
    protected static LootTable.Builder dropping(ItemLike item)
    {
        return LootTable.lootTable().withPool(withSurvivesExplosion(item, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(item))));
    }
    
    protected static LootTable.Builder dropping(Block block, ILootCondition.IBuilder conditionBuilder, LootEntry.Builder<?> p_218494_2_)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).when(conditionBuilder).otherwise(p_218494_2_)));
    }
    
    protected static LootTable.Builder droppingWithSilkTouch(Block block, LootEntry.Builder<?> builder)
    {
        return dropping(block, SILK_TOUCH, builder);
    }
    
    protected static LootTable.Builder droppingWithShears(Block block, LootEntry.Builder<?> noShearAlternativeEntry)
    {
        return dropping(block, SHEARS, noShearAlternativeEntry);
    }
    
    protected static LootTable.Builder droppingWithSilkTouchOrShears(Block block, LootEntry.Builder<?> alternativeLootEntry)
    {
        return dropping(block, SILK_TOUCH_OR_SHEARS, alternativeLootEntry);
    }
    
    protected static LootTable.Builder droppingWithSilkTouch(Block block, ItemLike noSilkTouch)
    {
        return droppingWithSilkTouch(block, withSurvivesExplosion(block, ItemLootEntry.lootTableItem(noSilkTouch)));
    }
    
    protected static LootTable.Builder droppingRandomly(ItemLike item, IRandomRange range)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(withExplosionDecay(item, ItemLootEntry.lootTableItem(item).apply(SetCount.setCount(range)))));
    }
    
    protected static LootTable.Builder droppingWithSilkTouchOrRandomly(Block block, ItemLike item, IRandomRange range)
    {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.lootTableItem(item).apply(SetCount.setCount(range))));
    }
    
    protected static LootTable.Builder onlyWithSilkTouchOrShears(ItemLike item)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SILK_TOUCH_OR_SHEARS).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(item)));
    }
    
    protected static LootTable.Builder onlyWithSilkTouch(ItemLike item)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SILK_TOUCH).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(item)));
    }
    
    protected static LootTable.Builder droppingAndFlowerPot(ItemLike flower)
    {
        return LootTable.lootTable().withPool(withSurvivesExplosion(Blocks.FLOWER_POT, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Blocks.FLOWER_POT)))).withPool(withSurvivesExplosion(flower, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(flower))));
    }
    
    protected static LootTable.Builder droppingSlab(Block slab)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(withExplosionDecay(slab, ItemLootEntry.lootTableItem(slab).apply(SetCount.setCount(ConstantRange.exactly(2)).when(BlockStateProperty.hasBlockStateProperties(slab).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }
    
    protected static <T extends Comparable<T> & IStringSerializable> LootTable.Builder droppingWhen(Block block, Property<T> property, T value)
    {
        return LootTable.lootTable().withPool(withSurvivesExplosion(block, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))))));
    }
    
    protected static LootTable.Builder droppingWithName(Block block)
    {
        return LootTable.lootTable().withPool(withSurvivesExplosion(block, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)))));
    }
    
    protected static LootTable.Builder droppingWithContents(Block shulker)
    {
        return LootTable.lootTable().withPool(withSurvivesExplosion(shulker, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(shulker).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Lock", "BlockEntityTag.Lock").copy("LootTable", "BlockEntityTag.LootTable").copy("LootTableSeed", "BlockEntityTag.LootTableSeed")).apply(SetContents.setContents().withEntry(DynamicLootEntry.dynamicEntry(ShulkerBoxBlock.CONTENTS))))));
    }
    
    protected static LootTable.Builder droppingWithPatterns(Block banner)
    {
        return LootTable.lootTable().withPool(withSurvivesExplosion(banner, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(banner).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Patterns", "BlockEntityTag.Patterns")))));
    }
    
    private static LootTable.Builder droppingAndBees(Block nest)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SILK_TOUCH).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(nest).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Bees", "BlockEntityTag.Bees")).apply(CopyBlockState.copyState(nest).copy(BeehiveBlock.HONEY_LEVEL))));
    }
    
    private static LootTable.Builder droppingAndBeesWithAlternative(Block hive)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(hive).when(SILK_TOUCH).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Bees", "BlockEntityTag.Bees")).apply(CopyBlockState.copyState(hive).copy(BeehiveBlock.HONEY_LEVEL)).otherwise(ItemLootEntry.lootTableItem(hive))));
    }
    
    protected static LootTable.Builder droppingItemWithFortune(Block block, Item item)
    {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.lootTableItem(item).apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
    
    /**
     * Creates a builder that drops the given ItemLike in amounts between 0 and 2, most often 0. Only used in
     * vanilla for huge mushroom blocks.
     */
    protected static LootTable.Builder droppingItemRarely(Block block, ItemLike item)
    {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.lootTableItem(item).apply(SetCount.setCount(RandomValueRange.between(-6.0F, 2.0F))).apply(LimitCount.limitCount(IntClamper.lowerBound(0)))));
    }
    
    protected static LootTable.Builder droppingSeeds(Block block)
    {
        return droppingWithShears(block, withExplosionDecay(block, ItemLootEntry.lootTableItem(Items.WHEAT_SEEDS).when(RandomChance.randomChance(0.125F)).apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))));
    }
    
    /**
     * Creates a builder that drops the given ItemLike in amounts between 0 and 3, based on the AGE property. Only
     * used in vanilla for pumpkin and melon stems.
     */
    protected static LootTable.Builder droppingByAge(Block stemFruit, Item item)
    {
        return LootTable.lootTable().withPool(withExplosionDecay(stemFruit, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(item).apply(SetCount.setCount(BinomialRange.binomial(3, 0.06666667F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 0)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.13333334F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 1)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.2F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 2)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.26666668F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 3)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.33333334F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 4)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.4F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 5)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.46666667F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 6)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.53333336F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 7)))))));
    }
    
    private static LootTable.Builder dropSeedsForStem(Block stem, Item stemSeed)
    {
        return LootTable.lootTable().withPool(withExplosionDecay(stem, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(stemSeed).apply(SetCount.setCount(BinomialRange.binomial(3, 0.53333336F))))));
    }
    
    protected static LootTable.Builder onlyWithShears(ItemLike item)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(SHEARS).add(ItemLootEntry.lootTableItem(item)));
    }
    
    /**
     * Used for all leaves, drops self with silk touch, otherwise drops the second Block param with the passed chances
     * for fortune levels, adding in sticks.
     */
    protected static LootTable.Builder droppingWithChancesAndSticks(Block block, Block sapling, float... chances)
    {
        return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.lootTableItem(sapling)).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances))).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(NOT_SILK_TOUCH_OR_SHEARS).add(withExplosionDecay(block, ItemLootEntry.lootTableItem(Items.STICK).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)))).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }
    
    /**
     * Used for oak and dark oak, same as droppingWithChancesAndSticks but adding in apples.
     */
    protected static LootTable.Builder droppingWithChancesSticksAndApples(Block block, Block sapling, float... chances)
    {
        return droppingWithChancesAndSticks(block, sapling, chances).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(NOT_SILK_TOUCH_OR_SHEARS).add(withSurvivesExplosion(block, ItemLootEntry.lootTableItem(Items.APPLE)).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }
    
    /**
     * Drops the first item parameter always, and the second item parameter plus more of the first when the loot
     * condition is met, applying fortune to only the second argument.
     */
    protected static LootTable.Builder droppingAndBonusWhen(Block block, Item itemConditional, Item withBonus, ILootCondition.IBuilder conditionBuilder)
    {
        return withExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool().add(ItemLootEntry.lootTableItem(itemConditional).when(conditionBuilder).otherwise(ItemLootEntry.lootTableItem(withBonus)))).withPool(LootPool.lootPool().when(conditionBuilder).add(ItemLootEntry.lootTableItem(withBonus).apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
    }
    
    private static LootTable.Builder droppingSheared(Block sheared)
    {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SHEARS).add(ItemLootEntry.lootTableItem(sheared).apply(SetCount.setCount(ConstantRange.exactly(2)))));
    }
    
    private static LootTable.Builder droppingSeedsTall(Block block, Block sheared)
    {
        LootEntry.Builder<?> builder = ItemLootEntry.lootTableItem(sheared).apply(SetCount.setCount(ConstantRange.exactly(2))).when(SHEARS).otherwise(withSurvivesExplosion(block, ItemLootEntry.lootTableItem(Items.WHEAT_SEEDS)).when(RandomChance.randomChance(0.125F)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }
    
    public static LootTable.Builder blockNoDrop()
    {
        return LootTable.lootTable();
    }
    
    private void droppingNetherVines(Block vines, Block plant)
    {
        LootTable.Builder loottable$builder = droppingWithSilkTouchOrShears(vines, ItemLootEntry.lootTableItem(vines).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
        registerLootTable(vines, loottable$builder);
        registerLootTable(plant, loottable$builder);
    }
    
    public static LootTable.Builder droppingDoor(Block door)
    {
        return droppingWhen(door, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }
    
    
    protected void registerLootTable(Block blockIn, LootTable.Builder table)
    {
        addTable(blockIn.getLootTable(), table);
    }
    
    void addTable(ResourceLocation path, LootTable.Builder lootTable)
    {
        tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), LootParameterSets.BLOCK));
    }
}