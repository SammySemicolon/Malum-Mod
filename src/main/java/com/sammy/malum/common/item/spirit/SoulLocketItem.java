package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.entity.spirit.SoulEntity;
import com.sammy.malum.core.systems.item.ISoulContainerItem;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class SoulLocketItem extends Item implements ISoulContainerItem {
    public SoulLocketItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        Vec3 targetPosition = pPlayer.position().add(pPlayer.getLookAngle().normalize().multiply(4,4,4));
        EntityHitResult result = ProjectileUtil.getEntityHitResult(pPlayer.level, pPlayer, pPlayer.position(), targetPosition, pPlayer.getBoundingBox().expandTowards(targetPosition).inflate(4.0D), e -> e instanceof SoulEntity);
        if (result != null) {
            interactWithSoul(pPlayer, pUsedHand, (SoulEntity) result.getEntity());
        }
        pPlayer.swing(pUsedHand);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public InteractionResult interactWithSoul(Player pPlayer, InteractionHand pHand, SoulEntity soul) {
        if (!soul.spiritData.equals(MalumEntitySpiritData.EMPTY))
        {
            ItemStack stack = pPlayer.getItemInHand(pHand);
            soul.spiritData.saveTo(stack.getOrCreateTag());
            soul.discard();
            return InteractionResult.SUCCESS;
        }
        return null;
    }
}
