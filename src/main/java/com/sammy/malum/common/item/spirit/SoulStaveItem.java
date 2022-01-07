package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.capability.PlayerDataCapability;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class SoulStaveItem extends Item {
    public SoulStaveItem(Properties pProperties) {
        super(pProperties);
    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel serverLevel) {
            PlayerDataCapability.getCapability(pPlayer).ifPresent(c -> {
                if (c.targetedSoulUUID != null) {
                    LivingEntity entity = (LivingEntity) serverLevel.getEntity(c.targetedSoulUUID);
                    if (entity != null && entity.isAlive()) {
                        pPlayer.startUsingItem(pUsedHand);
                    }
                }
            });
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player player)
        {
            player.getCooldowns().addCooldown(pStack.getItem(), 100);
        }
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }
}