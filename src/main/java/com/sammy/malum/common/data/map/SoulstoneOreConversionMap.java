package com.sammy.malum.common.data.map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.common.data.component.soulstone.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;
import java.util.Optional;

public record SoulstoneOreConversionMap(List<SoulstoneOreConversion> possibleConversions) {

    public static final Codec<SoulstoneOreConversionMap> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SoulstoneOreConversion.CODEC.listOf().fieldOf("possibleConversions").forGetter(SoulstoneOreConversionMap::possibleConversions)
    ).apply(instance, SoulstoneOreConversionMap::new));


    public record SoulstoneOreConversion(Optional<RuleTest> condition, BlockState result, StoredInSoulstoneMetal metalData) {

        public static final Codec<SoulstoneOreConversion> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RuleTest.CODEC.optionalFieldOf("condition").forGetter(SoulstoneOreConversion::condition),
                BlockState.CODEC.fieldOf("result").forGetter(SoulstoneOreConversion::result),
                StoredInSoulstoneMetal.CODEC.fieldOf("metalData").forGetter(SoulstoneOreConversion::metalData)
        ).apply(instance, SoulstoneOreConversion::new));
    }
}