package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.sammy.malum.common.item.curiosities.curios.runes.AbstractRuneCurioItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.registries.ForgeRegistries;
import team.lodestar.lodestone.helpers.CurioHelper;
import team.lodestar.lodestone.helpers.EntityHelper;

import java.util.function.Consumer;

public class RuneTwinnedDurationItem extends AbstractRuneCurioItem {

    public RuneTwinnedDurationItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AQUEOUS_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("extend_positive_effect"));
    }

    public static void onPotionApplied(MobEffectEvent.Added event) {
        LivingEntity entity = event.getEntity();
        if (event.getOldEffectInstance() == null && CurioHelper.hasCurioEquipped(entity, ItemRegistry.RUNE_OF_TWINNED_DURATION.get())) {
            MobEffectInstance effect = event.getEffectInstance();
            MobEffect type = effect.getEffect();
            if (type.isBeneficial()) {
                EntityHelper.extendEffect(effect, entity, effect.getDuration());
            }
        }
    }
}
