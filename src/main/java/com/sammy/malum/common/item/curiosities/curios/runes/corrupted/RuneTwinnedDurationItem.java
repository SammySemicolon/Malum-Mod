package com.sammy.malum.common.item.curiosities.curios.runes.corrupted;

import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class RuneTwinnedDurationItem extends MalumRuneCurioItem {

    public RuneTwinnedDurationItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AQUEOUS_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.rune.effect.twinned_duration"));
    }

    public static void onPotionApplied(MobEffectEvent.Added event) {
        LivingEntity entity = event.getEntity();
        if (event.getOldEffectInstance() == null && CurioHelper.hasCurioEquipped(entity, ItemRegistry.RUNE_OF_TWINNED_DURATION.get())) {
            MobEffectInstance effect = event.getEffectInstance();
            MobEffect type = effect.getEffect();
            float multiplier = MobEffectRegistry.ALCHEMICAL_PROFICIENCY_MAP.getOrDefault(ForgeRegistries.MOB_EFFECTS.getKey(type), 1f);
            if (type.isBeneficial()) {
                EntityHelper.extendEffect(effect, entity, (int) (effect.getDuration() * multiplier));
            }
        }
    }
}