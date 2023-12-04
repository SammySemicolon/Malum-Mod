package com.sammy.malum.common.item.curiosities.weapons;

import com.google.common.collect.*;
import com.sammy.malum.common.entity.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.registry.common.*;

public class MagicStaveItem extends MalumStaveItem {

    public final float magicDamage;

    public MagicStaveItem(Tier tier, float attackDamageIn, float attackSpeedIn, float magicDamage, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.magicDamage = magicDamage;
    }

    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(LodestoneAttributeRegistry.MAGIC_DAMAGE.get(), new AttributeModifier(LodestoneAttributeRegistry.UUIDS.get(LodestoneAttributeRegistry.MAGIC_DAMAGE), "Weapon magic damage", magicDamage, AttributeModifier.Operation.ADDITION));
        return builder;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        Level level = pPlayer.level();
        if (!level.isClientSide && !pPlayer.getCooldowns().isOnCooldown(stack.getItem())) {
            float magicDamage = (float) pPlayer.getAttributes().getValue(LodestoneAttributeRegistry.MAGIC_DAMAGE.get());
            int angle = pUsedHand == InteractionHand.MAIN_HAND ? 225 : 90;
            Vec3 pos = pPlayer.position().add(pPlayer.getLookAngle().scale(0.5)).add(0.5 * Math.sin(Math.toRadians(angle - pPlayer.yHeadRot)), pPlayer.getBbHeight() * 2 / 3, 0.5 * Math.cos(Math.toRadians(angle - pPlayer.yHeadRot)));
            HexProjectileEntity entity = new HexProjectileEntity(level, pos.x, pos.y, pos.z);
            entity.setData(pPlayer, magicDamage);
            entity.setItem(stack);

            entity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.75f, 0F);
            level.addFreshEntity(entity);
            stack.hurtAndBreak(1, pPlayer, (p_220009_1_) -> {
                p_220009_1_.broadcastBreakEvent(pUsedHand);
            });
            pPlayer.getCooldowns().addCooldown(stack.getItem(), 15);
            return InteractionResultHolder.success(stack);
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
