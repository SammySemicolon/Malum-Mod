package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.CurioHelper;

public class CurioDemolitionistRing extends MalumCurioItem {

    public CurioDemolitionistRing(Properties builder) {
        super(builder);
    }

    public static float increaseExplosionRadius(LivingEntity source, float original) {
        if (source != null && CurioHelper.hasCurioEquipped(source, ItemRegistry.RING_OF_THE_DEMOLITIONIST.get())) {
            return original + 1;
        }
        return original;
    }

    @Override
    public boolean isOrnate() {
        return true;
    }
}