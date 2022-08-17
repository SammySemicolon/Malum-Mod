package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
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
            if (CurioHelper.hasCurioEquipped(target, ItemRegistry.NECKLACE_OF_BLISSFUL_HARMONY)) {
                MalumEntitySpiritData data = SpiritHelper.getEntitySpiritData(watcher);
                float visibilityModifier = data == null ? 0.5f : getVisibilityMultiplier(data.weightedSpirits);
                event.modifyVisibility(visibilityModifier);
            }
        }
    }

    public static float getVisibilityMultiplier(double spirits) {
        if (spirits < 3.5f) {
            return 0.15f;
        }
        if (spirits < 5.5f) {
            return 0.5f;
        }
        return 0.75f;
    }
}