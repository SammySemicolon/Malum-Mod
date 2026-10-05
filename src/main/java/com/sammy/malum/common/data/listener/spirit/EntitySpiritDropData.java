package com.sammy.malum.common.data.listener.spirit;

import com.sammy.malum.config.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.core.systems.registry.*;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

public class EntitySpiritDropData {

    public static final EntitySpiritDropData EMPTY = new EntitySpiritDropData(MalumSpiritTypes.SACRED_SPIRIT, new ArrayList<>(), null);

    protected final SpiritHolder<SpiritArcanaType> primaryType;
    protected final int totalSpirits;
    protected final List<SpiritIngredient> spirits;
    @Nullable
    protected final Ingredient itemAsSoul;

    public EntitySpiritDropData(SpiritHolder<SpiritArcanaType> primaryType, List<SpiritIngredient> spirits, @Nullable Ingredient itemAsSoul) {
        this.primaryType = primaryType;
        this.totalSpirits = spirits.stream().mapToInt(SpiritIngredient::count).sum();
        this.spirits = spirits;
        this.itemAsSoul = itemAsSoul;
    }

    public SpiritHolder<SpiritArcanaType> getPrimaryType() {
        return primaryType;
    }

    public int getTotalSpirits() {
        return totalSpirits;
    }

    public List<SpiritIngredient> getSpirits() {
        return spirits;
    }

    public List<ItemStack> getSpiritStacks() {
        return spirits.stream().map(SpiritIngredient::asItemStack).collect(Collectors.toList());
    }

    public int countSpirit(SpiritHolder<SpiritArcanaType> type) {
        for (SpiritIngredient spirit : spirits) {
            if (type.is(spirit)) {
                return spirit.count();
            }
        }
        return 0;
    }

    @Nullable
    public Ingredient getItemAsSoul() {
        return itemAsSoul;
    }

    public static Optional<EntitySpiritDropData> getSpiritData(LivingEntity entity) {
        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (SpiritDataReloadListener.HAS_NO_DATA.contains(key))
            return Optional.empty();

        EntitySpiritDropData spiritData = SpiritDataReloadListener.SPIRIT_DATA.get(key);
        if (spiritData != null)
            return Optional.of(spiritData);

        if (entity.getMaxHealth() >= 60f)
            return Optional.of(SpiritDataReloadListener.DEFAULT_BOSS_SPIRIT_DATA);

        if (!CommonConfig.USE_DEFAULT_SPIRIT_VALUES.getConfigValue())
            return Optional.empty();

        return switch (entity.getType().getCategory()) {
            case MONSTER -> Optional.of(SpiritDataReloadListener.DEFAULT_MONSTER_SPIRIT_DATA);
            case CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_CREATURE_SPIRIT_DATA);
            case AMBIENT -> Optional.of(SpiritDataReloadListener.DEFAULT_AMBIENT_SPIRIT_DATA);
            case AXOLOTLS -> Optional.of(SpiritDataReloadListener.DEFAULT_AXOLOTL_SPIRIT_DATA);
            case UNDERGROUND_WATER_CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_UNDERGROUND_WATER_CREATURE_SPIRIT_DATA);
            case WATER_CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_WATER_CREATURE_SPIRIT_DATA);
            case WATER_AMBIENT -> Optional.of(SpiritDataReloadListener.DEFAULT_WATER_AMBIENT_SPIRIT_DATA);
            default -> Optional.empty();
        };
    }

    public static Builder builder(SpiritHolder<SpiritArcanaType> type) {
        return builder(type, 1);
    }

    public static Builder builder(SpiritHolder<SpiritArcanaType> type, int count) {
        return new Builder(type).withSpirit(type, count);
    }

    public static class Builder {
        private final SpiritHolder<SpiritArcanaType> primaryType;
        private final List<SpiritIngredient> spirits = new ArrayList<>();
        private Ingredient itemAsSoul = null;

        public Builder(SpiritHolder<SpiritArcanaType> primaryType) {
            this.primaryType = primaryType;
        }

        public Builder withSpirit(SpiritHolder<SpiritArcanaType> spiritType) {
            return withSpirit(spiritType, 1);
        }

        public Builder withSpirit(SpiritHolder<SpiritArcanaType> spirit, int count) {
            spirits.add(new SpiritIngredient(spirit, count));
            return this;
        }

        public Builder withItemAsSoul(Ingredient itemAsSoul) {
            this.itemAsSoul = itemAsSoul;
            return this;
        }

        public EntitySpiritDropData build() {
            return new EntitySpiritDropData(primaryType, spirits, itemAsSoul);
        }
    }
}