package com.sammy.malum.common.item.curiosities.nitrate;

import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import java.util.function.*;

public abstract class AbstractNitrateItem extends Item {

    public final Function<Player, AbstractNitrateEntity> entitySupplier;

    public AbstractNitrateItem(Properties pProperties, Function<Player, AbstractNitrateEntity> entitySupplier) {
        super(pProperties);
        this.entitySupplier = entitySupplier;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundRegistry.NITRATE_THROWN.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (worldIn.random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isClientSide) {
            AbstractNitrateEntity bombEntity = entitySupplier.apply(playerIn);
            int angle = handIn == InteractionHand.MAIN_HAND ? 225 : 90;
            Vec3 pos = playerIn.position().add(playerIn.getLookAngle().scale(0.5)).add(0.5 * Math.sin(Math.toRadians(angle - playerIn.yHeadRot)), playerIn.getBbHeight() * 2 / 3, 0.5 * Math.cos(Math.toRadians(angle - playerIn.yHeadRot)));
            float pitch = -10.0F;
            bombEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), pitch, 1.25F, 0.9F);
            bombEntity.setPos(pos);
            worldIn.addFreshEntity(bombEntity);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        if (!playerIn.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
    }
}