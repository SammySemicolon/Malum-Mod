package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.CurioHelper;

public class CurioHoarderRing extends MalumCurioItem {

    public CurioHoarderRing(Properties builder) {
        super(builder);
    }

    public static boolean hasEarthenRing(LivingEntity entity) {
        return entity != null && CurioHelper.hasCurioEquipped(entity, ItemRegistry.RING_OF_THE_HOARDER.get());
    }

    public static BlockPos getExplosionPos(boolean hasTheRing, BlockPos originalPos, LivingEntity entity) {
        return hasTheRing ? entity.blockPosition().above() : originalPos;
    }

    @Override
    public boolean isGilded() {
        return true;
    }
}