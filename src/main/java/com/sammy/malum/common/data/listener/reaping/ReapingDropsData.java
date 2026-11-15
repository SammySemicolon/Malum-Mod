package com.sammy.malum.common.data.listener.reaping;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.EitherCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import team.lodestar.lodestone.helpers.ItemHelper;

import java.util.List;
import java.util.Optional;

public record ReapingDropsData(ResourceLocation id, List<ReapingLoot> drops, Optional<HolderSet<EntityType<?>>> mobSource, Optional<RuleTest> blockSource) {


    public static final Codec<ReapingDropsData> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(ReapingDropsData::id),
            ReapingLoot.CODEC.listOf().fieldOf("drops").forGetter(ReapingDropsData::drops),
            RegistryCodecs.homogeneousList(Registries.ENTITY_TYPE).optionalFieldOf("mob_sources").forGetter(ReapingDropsData::mobSource),
            RuleTest.CODEC.optionalFieldOf("block_sources").forGetter(ReapingDropsData::blockSource)
    ).apply(instance, ReapingDropsData::new));


    public record ReapingLoot(Ingredient drop, IntProvider count, float chance) {

        public static final Codec<ReapingLoot> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Ingredient.CODEC.fieldOf("drops").forGetter(ReapingLoot::drop),
                IntProvider.POSITIVE_CODEC.fieldOf("count").forGetter(ReapingLoot::count),
                Codec.FLOAT.optionalFieldOf("chance", 1f).forGetter(ReapingLoot::chance)
        ).apply(instance, ReapingLoot::new));

        public Optional<ItemStack> roll(Level level) {
            var random = level.random;
            if (random.nextFloat() > chance) {
                return Optional.empty();
            }
            var possibleDrops = drop().getItems();
            int roll = random.nextInt(possibleDrops.length);
            int count = count().sample(random);
            return Optional.of(ItemHelper.copyWithNewCount(possibleDrops[roll], count));
        }
    }
}
