package com.sammy.malum.common.item.curiosities.curios;

import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import team.lodestar.lodestone.helpers.CurioHelper;

public class CurioHarmonyNecklace extends MalumCurioItem {
    public CurioHarmonyNecklace(Properties builder) {
        super(builder);
    }

    @Override
    public boolean isGilded() {
        return true;
    }

    public static void preventDetection(LivingEvent.LivingVisibilityEvent event) {
        if (event.getLookingEntity() instanceof LivingEntity watcher) {
            LivingEntity target = event.getEntityLiving();
            if (CurioHelper.hasCurioEquipped(target, ItemRegistry.NECKLACE_OF_BLISSFUL_HARMONY.get())) {
                MalumEntitySpiritData data = SpiritHelper.getEntitySpiritData(watcher);
                float visibilityModifier = data == null ? 0.5f : 0.5f / (1+data.dataEntries.stream().map(s -> s.type.equals(SpiritTypeRegistry.WICKED_SPIRIT) ? 1 : 0).count());
                if (target.hasEffect(MobEffects.INVISIBILITY)) {
                    visibilityModifier *= 0.5f;
                }
                event.modifyVisibility(visibilityModifier);
            }
        }
    }
}