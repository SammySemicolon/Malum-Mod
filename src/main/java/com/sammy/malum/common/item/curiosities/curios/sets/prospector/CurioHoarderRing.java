package com.sammy.malum.common.item.curiosities.curios.sets.prospector;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.helpers.CurioHelper;

import java.util.function.*;

public class CurioHoarderRing extends MalumCurioItem {

    public CurioHoarderRing(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<AttributeLikeTooltipEntry> consumer) {
        consumer.accept(positiveEffect("malum.gui.curio.effect.ring_of_the_hoarder"));
    }

    public static boolean hasHoarderRing(LivingEntity entity) {
        return entity != null && CurioHelper.hasCurioEquipped(entity, ItemRegistry.RING_OF_THE_HOARDER.get());
    }

    public static BlockPos getExplosionPos(boolean hasTheRing, BlockPos originalPos, LivingEntity entity, ItemStack droppedItem) {
        if (hasTheRing) {
            ItemStack itemInHand = entity.getItemInHand(InteractionHand.OFF_HAND);
            if (!itemInHand.isEmpty()) {
                if (!droppedItem.getItem().equals(itemInHand.getItem())) {
                    return originalPos;
                }
            }
        }
        return hasTheRing ? entity.blockPosition().above() : originalPos;
    }
}