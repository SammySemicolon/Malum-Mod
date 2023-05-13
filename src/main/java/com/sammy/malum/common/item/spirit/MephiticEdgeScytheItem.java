package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.item.tools.magic.*;
import net.minecraft.world.item.*;

public class MephiticEdgeScytheItem extends MagicScytheItem {
    public MephiticEdgeScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, float magicDamage, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, magicDamage, builderIn);
    }

//    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
//        ItemStack itemstack = playerIn.getItemInHand(handIn);
//
//        if (playerIn.getCooldowns().isOnCooldown(ItemRegistry.NIGHT_TERROR.get())) {
//            return InteractionResultHolder.pass(itemstack);
//        }
//
//        if (!worldIn.isClientSide) {
//            for (int i = -1; i < 2; i++) {
//                NightTerrorSeekerEntity nightTerrorSeekerEntity = new NightTerrorSeekerEntity(playerIn, worldIn);
//                int angle = handIn == InteractionHand.MAIN_HAND ? 225 : 90;
//                Vec3 pos = playerIn.position().add(playerIn.getLookAngle().scale(0.5)).add(0.5 * Math.sin(Math.toRadians(angle - playerIn.yHeadRot)), playerIn.getBbHeight() * 2 / 3, 0.5 * Math.cos(Math.toRadians(angle - playerIn.yHeadRot)));
//                float pitch = -24.0F + i * 8f;
//                float velocity = 1.1f + i * 0.1f;
//                nightTerrorSeekerEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), pitch, velocity, 0.9F);
//                nightTerrorSeekerEntity.setPos(pos);
//                worldIn.addFreshEntity(nightTerrorSeekerEntity);
//            }
//        }
//        playerIn.getCooldowns().addCooldown(ItemRegistry.NIGHT_TERROR.get(), 80);
//        playerIn.swing(handIn, true);
//        playerIn.awardStat(Stats.ITEM_USED.get(this));
//
//        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
//    }
}
