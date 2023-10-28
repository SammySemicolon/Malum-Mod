package com.sammy.malum.common.item.curiosities.curios.prospector;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.CurioHelper;

public class CurioDemolitionistRing extends MalumCurioItem {

    public CurioDemolitionistRing(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    public static float increaseExplosionRadius(LivingEntity source, float original) {
        if (source != null && CurioHelper.hasCurioEquipped(source, ItemRegistry.RING_OF_THE_DEMOLITIONIST.get())) {
            return original + 1;
        }
        return original;
    }
}