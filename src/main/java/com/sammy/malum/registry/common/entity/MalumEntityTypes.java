package com.sammy.malum.registry.common.entity;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entity.*;
import com.sammy.malum.common.entity.activator.*;
import com.sammy.malum.common.entity.activator.gluttony.*;
import com.sammy.malum.common.entity.activator.rite.*;
import com.sammy.malum.common.entity.activator.vindictive_brand.*;
import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.common.entity.hidden_blade.*;
import com.sammy.malum.common.entity.nitrate.EthericNitrate;
import com.sammy.malum.common.entity.nitrate.VividNitrate;
import com.sammy.malum.common.entity.scythe.*;
import com.sammy.malum.common.entity.soulTag.SoulTagEntity;
import com.sammy.malum.common.entity.spirit.SpiritItemEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class MalumEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MalumMod.MALUM);

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
        MalumCultistEntityTypes.init();
    }

    public static final DeferredHolder<EntityType<?>, EntityType<SoulTagEntity>> SOUL_TAG_ENTITY = register("soul_tag_entity", SoulTagEntity::new, 0.98F, 0.98F, 10, 20);

    public static final DeferredHolder<EntityType<?>, EntityType<AscendingBlock>> ASCENDING_BLOCK = register("ascending_block", AscendingBlock::new, 0.98F, 0.98F, 10, 20);
    public static final DeferredHolder<EntityType<?>, EntityType<PillowSeatEntity>> PILLOW_SEAT = register("pillow_seat", PillowSeatEntity::new, 0.25F, 0.25F, 2, 20);

    public static final DeferredHolder<EntityType<?>, EntityType<SpiritItemEntity>> NATURAL_SPIRIT = register("natural_spirit", SpiritItemEntity::new, 0.5F, 0.75F, 10);

    public static final DeferredHolder<EntityType<?>, EntityType<EthericNitrate>> ETHERIC_NITRATE = register("etheric_nitrate", EthericNitrate::new, 0.5F, 0.5F, 20);
    public static final DeferredHolder<EntityType<?>, EntityType<VividNitrate>> VIVID_NITRATE = register("vivid_nitrate", VividNitrate::new, 0.5F, 0.5F, 20);

    public static final DeferredHolder<EntityType<?>, EntityType<ScytheBoomerang>> SCYTHE_BOOMERANG = register("scythe_boomerang", ScytheBoomerang::new, 2f, 2f, 20);
    public static final DeferredHolder<EntityType<?>, EntityType<LocalizedMaelstrom>> SCYTHE_MAELSTROM = register("scythe_maelstrom", LocalizedMaelstrom::new, 2f, 2f, 20);

    public static final DeferredHolder<EntityType<?>, EntityType<GluttonyDamageActivator>> GLUTTONY_LOCUST = register("gluttony_locust", GluttonyDamageActivator::new, 0.25f, 0.25f, 4, 1);

    public static final DeferredHolder<EntityType<?>, EntityType<SpellweaverToolEffectActivator>> SPELLWEAVER_TOOL_EFFECT_ACTIVATOR = register("spellweavers_locus", SpellweaverToolEffectActivator::new, 0.25f, 0.25f, 4, 1);
    public static final DeferredHolder<EntityType<?>, EntityType<EntityRiteEffectActivator>> RITE_ENTITY_EFFECT_ACTIVATOR = register("seeking_rite_locus", EntityRiteEffectActivator::new, 1f, 1f, 4, 1);
    public static final DeferredHolder<EntityType<?>, EntityType<BlockRiteEffectActivator>> RITE_BLOCK_EFFECT_ACTIVATOR = register("bound_rite_locus", BlockRiteEffectActivator::new, 1f, 1f, 1, 1);
    public static final DeferredHolder<EntityType<?>, EntityType<BlockRiteEffectWaveActivator>> RITE_BLOCK_WAVE_EFFECT_ACTIVATOR = register("locus_wave_projection", BlockRiteEffectWaveActivator::new, 1f, 1f, 1, 1);

    public static final DeferredHolder<EntityType<?>, EntityType<SpiritCollectionActivator>> SPIRIT_COLLECTION_ACTIVATOR = register("pneuma_void", SpiritCollectionActivator::new, 1f, 1f, 10);

    public static final DeferredHolder<EntityType<?>, EntityType<HiddenBladeDelayedImpact>> HIDDEN_BLADE_DELAYED_IMPACT = register("hidden_blade_delayed_impact", HiddenBladeDelayedImpact::new, 8F, 8F, 10);

    public static final DeferredHolder<EntityType<?>, EntityType<HexBolt>> HEX_BOLT = register("hex_bolt", HexBolt::new, 1.5F, 1.5F, 10);
    public static final DeferredHolder<EntityType<?>, EntityType<DrainingBolt>> DRAINING_BOLT = register("draining_bolt", DrainingBolt::new, 1.5F, 1.5F, 10);
    public static final DeferredHolder<EntityType<?>, EntityType<EntropicFlameBolt>> ENTROPIC_FLAME_BOLT = register("entropic_flame_bolt", EntropicFlameBolt::new, 3F, 3F, 10);

    public static final DeferredHolder<EntityType<?>, EntityType<SunderingAnchorProjectile>> SUNDERING_ANCHOR = register("sundering_anchor", SunderingAnchorProjectile::new, 2f, 2f, 10);

    public static final DeferredHolder<EntityType<?>, EntityType<ResentmentRitualActivator>> RESENTMENT_RITUAL = register("resentment_ritual", ResentmentRitualActivator::new, 0.25f, 0.25f, 4, 1);

    protected static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, MalumEntityFactory<T> factory, float width, float height, int trackingRange) {
        return register(name, factory, width, height, trackingRange, 3);
    }

    protected static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, MalumEntityFactory<T> factory, float width, float height, int trackingRange, int updateInterval) {
        return register(name, factory, MobCategory.MISC, b -> b
                .sized(width, height)
                .clientTrackingRange(trackingRange)
                .updateInterval(updateInterval)
        );
    }

    protected static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, MalumEntityFactory<T> factory, MobCategory category, Consumer<EntityType.Builder<T>> builder) {
        EntityType.EntityFactory<T> entityFactory = (e, level) -> factory.create(level);
        return ENTITY_TYPES.register(name, () -> {
            EntityType.Builder<T> b = EntityType.Builder.of(entityFactory, category);
            builder.accept(b);
            return b.build(MalumMod.malumPath(name).toString());
        });
    }

    public interface MalumEntityFactory<T extends Entity> {
        T create(Level level);
    }
}