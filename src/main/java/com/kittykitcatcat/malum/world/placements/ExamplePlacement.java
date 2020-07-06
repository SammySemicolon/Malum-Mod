package com.kittykitcatcat.malum.world.placements;

import com.kittykitcatcat.malum.OpenSimplexNoise;
import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ExamplePlacement extends Placement<NoPlacementConfig>
{
    public ExamplePlacement(Function<Dynamic<?>, ? extends NoPlacementConfig> configFactoryIn)
    {
        super(configFactoryIn);
    }
    @Override
    public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generatorIn, Random random, NoPlacementConfig configIn, BlockPos pos)
    {
        int count = 4;
        return IntStream.range(0, count).mapToObj((p_227444_3_) ->
        {
            int j = random.nextInt(16) + pos.getX();
            int k = random.nextInt(16) + pos.getZ();
            int l = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, j, k);
            return new BlockPos(j, l, k);
        });
    }
}