package com.sammy.malum.common.item.curiosities.tools;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class VisionaryScryglassItem extends Item {

    public VisionaryScryglassItem(Properties properties) {
        super(properties);
    }

    public static boolean isScopingScryglass(LivingEntity entity) {
        return entity.isUsingItem() && entity.getUseItem().getItem() instanceof VisionaryScryglassItem;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 1200;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPYGLASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
        player.awardStat(Stats.ITEM_USED.get(this));
        return ItemUtils.startUsingInstantly(level, player, usedHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        this.stopUsing(livingEntity);
        return stack;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        this.stopUsing(livingEntity);
    }

    private void stopUsing(LivingEntity user) {
        user.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0F, 1.0F);
    }
}