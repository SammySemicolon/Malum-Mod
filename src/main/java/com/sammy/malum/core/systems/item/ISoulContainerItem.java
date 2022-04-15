package com.sammy.malum.core.systems.item;

import com.sammy.malum.common.entity.spirit.SoulEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import java.util.ArrayList;

public interface ISoulContainerItem {
    public InteractionResultHolder<ItemStack> interactWithSoul(Player pPlayer, InteractionHand pHand, SoulEntity soul);

    public default InteractionResultHolder<ItemStack> fetchSoul(Player player, InteractionHand pHand) {
        ArrayList<SoulEntity> entities = new ArrayList<>(player.level.getEntitiesOfClass(SoulEntity.class, player.getBoundingBox().inflate(player.getAttributeValue(ForgeMod.REACH_DISTANCE.get()) * 0.5f)));
        double biggestAngle = 0;
        SoulEntity chosenEntity = null;
        for (SoulEntity entity : entities) {
            Vec3 lookDirection = player.getLookAngle();
            Vec3 directionToEntity = entity.position().subtract(player.position()).normalize();
            double angle = lookDirection.dot(directionToEntity);
            if (angle > biggestAngle && angle > 0.1f) {
                biggestAngle = angle;
                chosenEntity = entity;
            }
        }
        if (chosenEntity != null) {
            return interactWithSoul(player, pHand, chosenEntity);
        }
        return InteractionResultHolder.pass(player.getItemInHand(pHand));
    }
}