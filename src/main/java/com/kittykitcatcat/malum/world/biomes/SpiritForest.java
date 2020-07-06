package com.kittykitcatcat.malum.world.biomes;

import com.google.common.collect.ImmutableList;
import com.kittykitcatcat.malum.OpenSimplexNoise;
import com.kittykitcatcat.malum.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;

import java.awt.*;

import static net.minecraft.world.biome.DefaultBiomeFeatures.DARK_OAK_TREE_CONFIG;

public class SpiritForest extends Biome
{
    public static final TreeFeatureConfig spiritwood_tree_config = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.spirit_log.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.spirit_leaves.getDefaultState()), new BlobFoliagePlacer(2, 0)).baseHeight(6).foliageHeightRandom(1).foliageHeight(7).trunkHeight(2).trunkHeightRandom(2).trunkTopOffsetRandom(2).ignoreVines().setSapling((IPlantable) ModBlocks.spirit_sapling).build();

    public static void addSpiritwoodTree(Biome biomeIn)
    {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.withConfiguration(spiritwood_tree_config).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.02F, 1))));
    }

    public static void addSpiritwoodTreeCommon(Biome biomeIn)
    {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.NORMAL_TREE.withConfiguration(spiritwood_tree_config).withChance(0.1F), Feature.DARK_OAK_TREE.withConfiguration(DARK_OAK_TREE_CONFIG).withChance(0.9F)), Feature.DARK_OAK_TREE.withConfiguration(DARK_OAK_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(5, 0.3F, 3))));
    }

    public SpiritForest()
    {
        super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.SAND.getDefaultState())))
                .precipitation(RainType.RAIN).category(Category.PLAINS).depth(0.1F).scale(0.2F).temperature(0.7F).downfall(0.8F).waterColor(4159204).waterFogColor(329011).parent(null));

        this.addStructure(Feature.MINESHAFT.withConfiguration(new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL)));
        this.addStructure(Feature.STRONGHOLD.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addStructures(this);
        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addTaigaLargeFerns(this);
        DefaultBiomeFeatures.addTaigaRocks(this);
        DefaultBiomeFeatures.addSparseBerryBushes(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addSedimentDisks(this);
        addSpiritwoodTreeCommon(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addTaigaGrassAndMushrooms(this);
        DefaultBiomeFeatures.addReedsAndPumpkins(this);
        DefaultBiomeFeatures.addSprings(this);
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.PIG, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.COW, 8, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.WOLF, 8, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.RABBIT, 4, 2, 3));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.FOX, 8, 2, 4));
        this.addSpawn(EntityClassification.AMBIENT, new SpawnListEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.WITCH, 5, 1, 1));

    }

    protected long noiseSeed;
    protected OpenSimplexNoise noiseGen;

    public void setSeed(long seed)
    {
        if (this.noiseSeed != seed || this.noiseGen == null)
        {
            this.noiseGen = new OpenSimplexNoise(seed);
        }
        this.noiseSeed = seed;
    }


    /*
     * set grass color
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public int getGrassColor(double double1, double double2)
    {
        setSeed(631);
        double d0 = noiseGen.eval(double1 * 0.02D, double2 * 0.02D);
        if (d0 < 0)
        {
            d0++;
        }
        Color baseColor = new Color(15, 104, 24);
        Color targetColor = new Color(63, 118, 20);
        int red = Math.max(0, baseColor.getRed() + (int)(targetColor.getRed() * d0));
        int green = Math.max(0, baseColor.getGreen() + (int)(targetColor.getGreen() * d0));
        int blue = Math.max(0, baseColor.getBlue() + (int)(targetColor.getBlue() * d0));
        Color color = new Color(Math.min(red,255),Math.min(green,255),Math.min(blue,255));
        return (-16777216 | color.getRed() << 16 | color.getGreen() << 8 | color.getBlue());
    }

    @Override
    public int getFoliageColor()
    {
        return 0x305B2A;
    }
}