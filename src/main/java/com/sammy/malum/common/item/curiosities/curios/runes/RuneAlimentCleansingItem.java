package com.sammy.malum.common.item.curiosities.curios.runes;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class RuneAlimentCleansingItem extends MalumRuneCurioItem {

    public RuneAlimentCleansingItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AQUEOUS_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.rune.effect.aliment_cleansing"));
    }

    public static void onPotionApplied(MobEffectEvent.Added event) {
        LivingEntity entity = event.getEntity();
        if (event.getOldEffectInstance() == null && CurioHelper.hasCurioEquipped(entity, ItemRegistry.RUNE_OF_ALIMENT_CLEANSING.get())) {
            MobEffectInstance effect = event.getEffectInstance();
            MobEffect type = effect.getEffect();
            float multiplier = MobEffectRegistry.ALCHEMICAL_PROFICIENCY_MAP.getOrDefault(ForgeRegistries.MOB_EFFECTS.getKey(type), 1f);
            if (type.getCategory().equals(MobEffectCategory.HARMFUL)) {
                EntityHelper.shortenEffect(effect, entity, (int) (effect.getDuration() * 0.5f * multiplier));
            }
        }
    }
    public static void onPotionExpired(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();
        if (event.getEffectInstance().getEffect().getCategory().equals(MobEffectCategory.HARMFUL) && CurioHelper.hasCurioEquipped(entity, ItemRegistry.RUNE_OF_ALIMENT_CLEANSING.get())) {
            entity.heal(Math.max(entity.getMaxHealth()/10f, 4));
        }
    }
}