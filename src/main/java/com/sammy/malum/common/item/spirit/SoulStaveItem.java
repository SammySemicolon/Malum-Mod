package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.capability.PlayerDataCapability;
import com.sammy.malum.common.entity.spirit.SoulEntity;
import com.sammy.malum.core.systems.item.ISoulContainerItem;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import static net.minecraft.world.InteractionHand.MAIN_HAND;
import static net.minecraft.world.InteractionHand.OFF_HAND;

public class SoulStaveItem extends Item implements ISoulContainerItem {
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
    public InteractionResultHolder<ItemStack> interactWithSoul(Player pPlayer, InteractionHand pHand, SoulEntity soul) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        ItemStack otherStack = pPlayer.getItemInHand(pHand.equals(MAIN_HAND) ? OFF_HAND : MAIN_HAND);
        if (otherStack.getItem() instanceof ISoulContainerItem) {
            return InteractionResultHolder.fail(stack);
        }
        if (!soul.spiritData.equals(MalumEntitySpiritData.EMPTY) && (!stack.hasTag() || (stack.hasTag() && !stack.getOrCreateTag().contains(MalumEntitySpiritData.NBT)))) {
            soul.spiritData.saveTo(stack.getOrCreateTag());
            soul.discard();
            pPlayer.swing(pHand, true);
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.fail(stack);
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
                } else {
                    fetchSoul(pPlayer, pUsedHand);
                }
            });
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player player) {
            player.getCooldowns().addCooldown(pStack.getItem(), 40);
        }
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }
}