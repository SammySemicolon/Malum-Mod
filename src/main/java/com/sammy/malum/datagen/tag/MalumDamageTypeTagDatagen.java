package com.sammy.malum.datagen.tag;

import com.mojang.datafixers.util.*;
import com.sammy.malum.*;
import net.minecraft.core.HolderLookup.*;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.neoforged.neoforge.common.data.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

import static com.sammy.malum.registry.common.MalumDamageTypes.*;
import static com.sammy.malum.registry.common.MalumTags.DamageTypes.*;
import static net.minecraft.tags.DamageTypeTags.*;
import static net.minecraft.world.damagesource.DamageTypes.*;
import static net.neoforged.neoforge.common.Tags.DamageTypes.*;
import static team.lodestar.wayward_attributes.WaywardTags.DamageTypeTags.CAN_TRIGGER_MAGIC_DAMAGE;

public class MalumDamageTypeTagDatagen extends DamageTypeTagsProvider {

    private static MalumDamageTypeTagDatagen DATAGEN;

    public MalumDamageTypeTagDatagen(PackOutput pOutput, CompletableFuture<Provider> pProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, MalumMod.MALUM, existingFileHelper);
        DATAGEN = this;
    }

    private final Function<ResourceKey<DamageType>, TagBuilder> MAGIC = type -> {
        final TagBuilder builder = addTag(type);
        builder.add(IS_MAGIC, BYPASSES_HALF_ARMOR, SOUL_SHATTER_DAMAGE, BYPASSES_COOLDOWN, AVOIDS_GUARDIAN_THORNS, PANIC_CAUSES);
        builder.remove(BYPASSES_SHIELD);
        return builder;
    };
    private final Function<ResourceKey<DamageType>, TagBuilder> PHYSICAL = type -> {
        final TagBuilder builder = addTag(type);
        builder.add(IS_PHYSICAL, PANIC_CAUSES, CAN_BREAK_ARMOR_STAND);
        return builder;
    };
    private final Function<ResourceKey<DamageType>, TagBuilder> SCYTHE = type -> {
        final TagBuilder builder = addTag(type);
        builder.add(IS_PHYSICAL, IS_SCYTHE, SOUL_SHATTER_DAMAGE, PANIC_CAUSES, CAN_BREAK_ARMOR_STAND);
        return builder;
    };
    private final Function<ResourceKey<DamageType>, TagBuilder> TRUE_DAMAGE = type -> {
        final TagBuilder builder = addTag(type);
        builder.add(BYPASSES_ARMOR, BYPASSES_SHIELD, BYPASSES_INVULNERABILITY, BYPASSES_COOLDOWN, BYPASSES_EFFECTS, BYPASSES_RESISTANCE, BYPASSES_ENCHANTMENTS);
        return builder;
    };


    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(Provider pProvider) {
        MAGIC.apply(VOODOO).add(NO_KNOCKBACK);
        MAGIC.apply(VOODOO_PLAYERLESS).add(NO_KNOCKBACK);

        MAGIC.apply(NITRATE).add(IS_NITRATE, IS_EXPLOSION);
        MAGIC.apply(NITRATE_PLAYERLESS).add(IS_NITRATE, IS_EXPLOSION);

        TRUE_DAMAGE.apply(VOID).add(IS_MAGIC, NO_KNOCKBACK);

        MAGIC.apply(KARMIC).add(NO_KNOCKBACK);
        MAGIC.apply(ROT).add(NO_KNOCKBACK);

        SCYTHE.apply(SCYTHE_MELEE).add(CAN_TRIGGER_MAGIC_DAMAGE, IS_SCYTHE_MELEE, IS_PLAYER_ATTACK, TRIGGERS_SCYTHE_COMBO);
        SCYTHE.apply(SCYTHE_SWEEP).add(CAN_TRIGGER_MAGIC_DAMAGE, IS_SCYTHE_MELEE, IS_PLAYER_ATTACK);
        SCYTHE.apply(SCYTHE_REBOUND).add(CAN_TRIGGER_MAGIC_DAMAGE, IS_PROJECTILE, TRIGGERS_SCYTHE_COMBO);
        SCYTHE.apply(SCYTHE_ASCENSION).add(CAN_TRIGGER_MAGIC_DAMAGE, IS_SCYTHE_MELEE, TRIGGERS_SCYTHE_COMBO);
        SCYTHE.apply(SCYTHE_COMBO).add(BYPASSES_COOLDOWN, NO_KNOCKBACK);
        SCYTHE.apply(SCYTHE_MAELSTROM).add(BYPASSES_COOLDOWN, NO_KNOCKBACK);

        SCYTHE.apply(HIDDEN_BLADE_PHYSICAL_COUNTER).add(IS_HIDDEN_BLADE, BYPASSES_COOLDOWN, NO_KNOCKBACK);
        MAGIC.apply(HIDDEN_BLADE_MAGIC_COUNTER).add(IS_HIDDEN_BLADE, BYPASSES_COOLDOWN, NO_KNOCKBACK);

        MAGIC.apply(TYRVING);

        PHYSICAL.apply(SUNDERING_ANCHOR_PHYSICAL_COMBO).add(IS_SUNDERING_ANCHOR_COMBO, NO_KNOCKBACK).remove(BYPASSES_SHIELD);
        MAGIC.apply(SUNDERING_ANCHOR_MAGIC_COMBO).add(IS_SUNDERING_ANCHOR_COMBO, NO_KNOCKBACK).remove(BYPASSES_SHIELD);

        PHYSICAL.apply(VINDICTIVE_BRAND_MELEE).add(CAN_TRIGGER_MAGIC_DAMAGE, IS_PLAYER_ATTACK);
        PHYSICAL.apply(VINDICTIVE_BRAND_SWEEP).add(CAN_TRIGGER_MAGIC_DAMAGE, IS_PLAYER_ATTACK);
        MAGIC.apply(VINDICTIVE_BRAND_COMBO).add(NO_KNOCKBACK).remove(BYPASSES_SHIELD);

        PHYSICAL.apply(UNMAKERS_DISDAIN_COMBO).add(NO_KNOCKBACK);

        MAGIC.apply(WARLOCK_SPIRIT_IMPACT);
        MAGIC.apply(BERSERKER_SPIRIT_IMPACT);

        MAGIC.apply(INVERTED_HEART_RETALIATION).add(IS_INVERTED_HEART);
        MAGIC.apply(INVERTED_HEART_PROPAGATION).add(IS_INVERTED_HEART);

        addToTag(BYPASSES_SOUL_WARD).add(IS_TECHNICAL);
        addToTag(BYPASSES_MALIGNANT_AEGIS).add(IS_TECHNICAL);

        addToTag(INVERTED_HEART_PROPAGATION_BLACKLIST).add(SCYTHE_SWEEP, THORNS).add(IS_INVERTED_HEART, IS_TECHNICAL);
        addToTag(INVERTED_HEART_RETALIATION_BLACKLIST).add(IS_INVERTED_HEART, IS_TECHNICAL);

        addToTag(GLEEFUL_TARGET_BLACKLIST).add(IS_DROWNING, IS_TECHNICAL);
    }

    public TagBuilder addTag(ResourceKey<DamageType> target) {
        return new TagBuilder(target);
    }
    public TagBuilder addToTag(TagKey<DamageType> target) {
        return new TagBuilder(target);
    }

    public static class TagBuilder {
        private final Either<ResourceKey<DamageType>, TagKey<DamageType>> target;

        public TagBuilder(ResourceKey<DamageType> target) {
            this.target = Either.left(target);
        }

        public TagBuilder(TagKey<DamageType> target) {
            this.target = Either.right(target);
        }

        @SafeVarargs
        public final TagBuilder add(TagKey<DamageType>... tags) {
            return add(List.of(tags));
        }

        public final TagBuilder add(List<TagKey<DamageType>> tags) {
            for (TagKey<DamageType> tag : tags) {
                target.ifLeft(left -> DATAGEN.tag(tag).add(left));
                target.ifRight(right -> DATAGEN.tag(right).addTag(tag));
            }
            return this;
        }

        @SafeVarargs
        public final TagBuilder remove(TagKey<DamageType>... damageTypes) {
            for (TagKey<DamageType> tag : damageTypes) {
                target.ifLeft(left -> DATAGEN.tag(tag).remove(left));
                target.ifRight(right -> DATAGEN.tag(right).remove(tag));
            }
            return this;
        }

        @SafeVarargs
        public final TagBuilder add(ResourceKey<DamageType>... damageType) {
            for (ResourceKey<DamageType> type : damageType) {
                target.ifRight(right -> DATAGEN.tag(right).add(type));
            }
            return this;
        }
    }
}