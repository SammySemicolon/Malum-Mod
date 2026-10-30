package com.sammy.malum.common.data.listener.reaping;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.core.systems.rite.SpiritRiteType;
import com.sammy.malum.core.systems.rite.effect.SpiritRiteEffect;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Collections;
import java.util.List;

public record ReapingDropsData(ResourceLocation id, List<ReapingLoot> drops) {

    public static final Codec<ReapingDropsData> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(ReapingDropsData::id),
            ReapingLoot.CODEC.listOf().fieldOf("drops").forGetter(ReapingDropsData::drops)
    ).apply(instance, ReapingDropsData::new));


    public record ReapingLoot(Ingredient drop, IntProvider count, float chance) {

        public static final Codec<ReapingLoot> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Ingredient.CODEC.fieldOf("drops").forGetter(ReapingLoot::drop),
                IntProvider.POSITIVE_CODEC.fieldOf("is_soulwood").forGetter(ReapingLoot::count),
                Codec.FLOAT.optionalFieldOf("chance", 1f).forGetter(ReapingLoot::chance)
        ).apply(instance, ReapingLoot::new));

    }
}
