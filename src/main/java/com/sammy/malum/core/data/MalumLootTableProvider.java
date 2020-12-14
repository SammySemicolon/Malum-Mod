package com.sammy.malum.core.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.systems.multiblock.BoundingBlock;
import net.minecraft.advancements.criterion.*;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.*;
import net.minecraft.state.Property;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sammy.malum.MalumHelper.*;
import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;

public class MalumLootTableProvider extends LootTableProvider
{
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = new ArrayList<>();
    private static final Set<Item> IMMUNE_TO_EXPLOSIONS = Stream.of(MalumBlocks.ARCANE_CRAFTING_TABLE, MalumBlocks.BLIGHTING_FURNACE, MalumBlocks.BLIGHTING_FURNACE_TOP).map(c -> c.get().asItem()).collect(ImmutableSet.toImmutableSet());
    
    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder SHEARS = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
    private static final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.alternative(SILK_TOUCH);
    private static final ILootCondition.IBuilder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.inverted();
    private static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
    
    public MalumLootTableProvider(DataGenerator dataGeneratorIn)
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
        map.forEach((loc, table) -> LootTableManager.validateLootTable(validationtracker, loc, table));
    }
    
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        blocks.remove(MalumBlocks.ABSTRUSE_BLOCK);
        takeAll(blocks, b -> b.get() instanceof BoundingBlock);
        takeAll(blocks, b -> b.get() instanceof LeavesBlock);
        takeAll(blocks, b -> b.get() instanceof DoublePlantBlock).forEach(b -> registerLootTable(b.get(),onlyWithShears(b.get().asItem())));
    
        takeAll(blocks, b -> b.get() instanceof GrassBlock).forEach(b -> registerLootTable(b.get(),droppingWithSilkTouch(b.get(), Items.DIRT)));
        takeAll(blocks, b -> b.get() instanceof SlabBlock).forEach(b -> registerLootTable(b.get(),droppingSlab(b.get())));
        takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(b -> registerLootTable(b.get(),droppingDoor(b.get())));
    
        takeAll(blocks, b -> true).forEach(b -> registerLootTable(b.get(), dropping(b.get().asItem())));
    
        registerLootTable(MalumBlocks.SUN_KISSED_LEAVES.get(),droppingWithChancesAndSticks(MalumBlocks.SUN_KISSED_LEAVES.get(), MalumBlocks.SUN_KISSED_SAPLING.get(), RARE_SAPLING_DROP_RATES));
        registerLootTable(MalumBlocks.TAINTED_LEAVES.get(),droppingWithChancesAndSticks(MalumBlocks.TAINTED_LEAVES.get(), MalumBlocks.TAINTED_SAPLING.get(), RARE_SAPLING_DROP_RATES));
        return tables;
    }
    
    protected static <T> T withExplosionDecay(IItemProvider item, ILootFunctionConsumer<T> function)
    {
        return (T) (!IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? function.acceptFunction(ExplosionDecay.builder()) : function.cast());
    }
    
    protected static <T> T withSurvivesExplosion(IItemProvider item, ILootConditionConsumer<T> condition)
    {
        return (T) (!IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? condition.acceptCondition(SurvivesExplosion.builder()) : condition.cast());
    }
    
    protected static LootTable.Builder dropping(IItemProvider item)
    {
        return LootTable.builder().addLootPool(withSurvivesExplosion(item, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item))));
    }
    
    protected static LootTable.Builder dropping(Block block, ILootCondition.IBuilder conditionBuilder, LootEntry.Builder<?> p_218494_2_)
    {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptCondition(conditionBuilder).alternatively(p_218494_2_)));
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
    
    protected static LootTable.Builder droppingWithSilkTouch(Block block, IItemProvider noSilkTouch)
    {
        return droppingWithSilkTouch(block, withSurvivesExplosion(block, ItemLootEntry.builder(noSilkTouch)));
    }
    
    protected static LootTable.Builder droppingRandomly(IItemProvider item, IRandomRange range)
    {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(withExplosionDecay(item, ItemLootEntry.builder(item).acceptFunction(SetCount.builder(range)))));
    }
    
    protected static LootTable.Builder droppingWithSilkTouchOrRandomly(Block block, IItemProvider item, IRandomRange range)
    {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.builder(item).acceptFunction(SetCount.builder(range))));
    }
    
    protected static LootTable.Builder onlyWithSilkTouchOrShears(IItemProvider item)
    {
        return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(SILK_TOUCH_OR_SHEARS).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item)));
    }
    protected static LootTable.Builder onlyWithSilkTouch(IItemProvider item)
    {
        return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(SILK_TOUCH).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item)));
    }
    
    protected static LootTable.Builder droppingAndFlowerPot(IItemProvider flower)
    {
        return LootTable.builder().addLootPool(withSurvivesExplosion(Blocks.FLOWER_POT, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Blocks.FLOWER_POT)))).addLootPool(withSurvivesExplosion(flower, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(flower))));
    }
    
    protected static LootTable.Builder droppingSlab(Block slab)
    {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(withExplosionDecay(slab, ItemLootEntry.builder(slab).acceptFunction(SetCount.builder(ConstantRange.of(2)).acceptCondition(BlockStateProperty.builder(slab).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }
    
    protected static <T extends Comparable<T> & IStringSerializable> LootTable.Builder droppingWhen(Block block, Property<T> property, T value)
    {
        return LootTable.builder().addLootPool(withSurvivesExplosion(block, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(property, value))))));
    }
    
    protected static LootTable.Builder droppingWithName(Block block)
    {
        return LootTable.builder().addLootPool(withSurvivesExplosion(block, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY)))));
    }
    
    protected static LootTable.Builder droppingWithContents(Block shulker)
    {
        return LootTable.builder().addLootPool(withSurvivesExplosion(shulker, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(shulker).acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY)).acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY).replaceOperation("Lock", "BlockEntityTag.Lock").replaceOperation("LootTable", "BlockEntityTag.LootTable").replaceOperation("LootTableSeed", "BlockEntityTag.LootTableSeed")).acceptFunction(SetContents.builderIn().addLootEntry(DynamicLootEntry.func_216162_a(ShulkerBoxBlock.CONTENTS))))));
    }
    
    protected static LootTable.Builder droppingWithPatterns(Block banner)
    {
        return LootTable.builder().addLootPool(withSurvivesExplosion(banner, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(banner).acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY)).acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY).replaceOperation("Patterns", "BlockEntityTag.Patterns")))));
    }
    
    private static LootTable.Builder droppingAndBees(Block nest)
    {
        return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(SILK_TOUCH).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(nest).acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY).replaceOperation("Bees", "BlockEntityTag.Bees")).acceptFunction(CopyBlockState.func_227545_a_(nest).func_227552_a_(BeehiveBlock.HONEY_LEVEL))));
    }
    
    private static LootTable.Builder droppingAndBeesWithAlternative(Block hive)
    {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(hive).acceptCondition(SILK_TOUCH).acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY).replaceOperation("Bees", "BlockEntityTag.Bees")).acceptFunction(CopyBlockState.func_227545_a_(hive).func_227552_a_(BeehiveBlock.HONEY_LEVEL)).alternatively(ItemLootEntry.builder(hive))));
    }
    
    protected static LootTable.Builder droppingItemWithFortune(Block block, Item item)
    {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.builder(item).acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))));
    }
    
    /**
     * Creates a builder that drops the given IItemProvider in amounts between 0 and 2, most often 0. Only used in
     * vanilla for huge mushroom blocks.
     */
    protected static LootTable.Builder droppingItemRarely(Block block, IItemProvider item)
    {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.builder(item).acceptFunction(SetCount.builder(RandomValueRange.of(-6.0F, 2.0F))).acceptFunction(LimitCount.func_215911_a(IntClamper.func_215848_a(0)))));
    }
    
    protected static LootTable.Builder droppingSeeds(Block block)
    {
        return droppingWithShears(block, withExplosionDecay(block, ItemLootEntry.builder(Items.WHEAT_SEEDS).acceptCondition(RandomChance.builder(0.125F)).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE, 2))));
    }
    
    /**
     * Creates a builder that drops the given IItemProvider in amounts between 0 and 3, based on the AGE property. Only
     * used in vanilla for pumpkin and melon stems.
     */
    protected static LootTable.Builder droppingByAge(Block stemFruit, Item item)
    {
        return LootTable.builder().addLootPool(withExplosionDecay(stemFruit, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.06666667F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 0)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.13333334F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 1)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.2F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 2)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.26666668F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 3)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.33333334F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 4)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.4F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 5)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.46666667F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 6)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.53333336F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 7)))))));
    }
    
    private static LootTable.Builder dropSeedsForStem(Block stem, Item stemSeed)
    {
        return LootTable.builder().addLootPool(withExplosionDecay(stem, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(stemSeed).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.53333336F))))));
    }
    
    protected static LootTable.Builder onlyWithShears(IItemProvider item)
    {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(SHEARS).addEntry(ItemLootEntry.builder(item)));
    }
    
    /**
     * Used for all leaves, drops self with silk touch, otherwise drops the second Block param with the passed chances
     * for fortune levels, adding in sticks.
     */
    protected static LootTable.Builder droppingWithChancesAndSticks(Block block, Block sapling, float... chances)
    {
        return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.builder(sapling)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, chances))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(NOT_SILK_TOUCH_OR_SHEARS).addEntry(withExplosionDecay(block, ItemLootEntry.builder(Items.STICK).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }
    
    /**
     * Used for oak and dark oak, same as droppingWithChancesAndSticks but adding in apples.
     */
    protected static LootTable.Builder droppingWithChancesSticksAndApples(Block block, Block sapling, float... chances)
    {
        return droppingWithChancesAndSticks(block, sapling, chances).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(NOT_SILK_TOUCH_OR_SHEARS).addEntry(withSurvivesExplosion(block, ItemLootEntry.builder(Items.APPLE)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }
    
    /**
     * Drops the first item parameter always, and the second item parameter plus more of the first when the loot
     * condition is met, applying fortune to only the second argument.
     */
    protected static LootTable.Builder droppingAndBonusWhen(Block block, Item itemConditional, Item withBonus, ILootCondition.IBuilder conditionBuilder)
    {
        return withExplosionDecay(block, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(itemConditional).acceptCondition(conditionBuilder).alternatively(ItemLootEntry.builder(withBonus)))).addLootPool(LootPool.builder().acceptCondition(conditionBuilder).addEntry(ItemLootEntry.builder(withBonus).acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 3)))));
    }
    
    private static LootTable.Builder droppingSheared(Block sheared)
    {
        return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(SHEARS).addEntry(ItemLootEntry.builder(sheared).acceptFunction(SetCount.builder(ConstantRange.of(2)))));
    }
    
    private static LootTable.Builder droppingSeedsTall(Block block, Block sheared)
    {
        LootEntry.Builder<?> builder = ItemLootEntry.builder(sheared).acceptFunction(SetCount.builder(ConstantRange.of(2))).acceptCondition(SHEARS).alternatively(withSurvivesExplosion(block, ItemLootEntry.builder(Items.WHEAT_SEEDS)).acceptCondition(RandomChance.builder(0.125F)));
        return LootTable.builder().addLootPool(LootPool.builder().addEntry(builder).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).acceptCondition(LocationCheck.func_241547_a_(LocationPredicate.Builder.builder().block(BlockPredicate.Builder.createBuilder().setBlock(block).setStatePredicate(StatePropertiesPredicate.Builder.newBuilder().withProp(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).addLootPool(LootPool.builder().addEntry(builder).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).acceptCondition(LocationCheck.func_241547_a_(LocationPredicate.Builder.builder().block(BlockPredicate.Builder.createBuilder().setBlock(block).setStatePredicate(StatePropertiesPredicate.Builder.newBuilder().withProp(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }
    
    public static LootTable.Builder blockNoDrop()
    {
        return LootTable.builder();
    }
    
    private void droppingNetherVines(Block vines, Block plant)
    {
        LootTable.Builder loottable$builder = droppingWithSilkTouchOrShears(vines, ItemLootEntry.builder(vines).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.33F, 0.55F, 0.77F, 1.0F)));
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