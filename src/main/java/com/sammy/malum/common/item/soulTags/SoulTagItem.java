package com.sammy.malum.common.item.soulTags;

import com.sammy.malum.common.data.component.SoulTagDataComponent;
import com.sammy.malum.common.entity.soulTag.SoulTagEntity;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.wayward_attributes.WaywardTags;

import java.util.List;

public class SoulTagItem extends Item {

    public SoulTagItem(Properties properties) {
        super(properties);
    }

    public static void setTarget(ItemStack stack, SoulTagDataComponent data) {
        stack.set(MalumDataComponents.SOUL_TAG_DATA.get(), data);
    }

    public static SoulTagDataComponent getTarget(ItemStack stack) {
        return stack.get(
                MalumDataComponents.SOUL_TAG_DATA.get()
        );
    }

    public static boolean hasTarget(ItemStack stack) {
        return stack.has(
                MalumDataComponents.SOUL_TAG_DATA.get()
        );
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack stack, Player player) {
        if (!player.level().isClientSide()) {
            SoulTagEntity entity = new SoulTagEntity(player.level());

            ItemStack thrownStack = stack.copy();
            thrownStack.setCount(1);

            entity.setItem(thrownStack);

            entity.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());

            Vec3 look = player.getLookAngle();

            entity.setDeltaMovement(look.scale(0.35D));

            player.level().addFreshEntity(entity);
        }

        return false;
    }
    @Override
    public Component getName(ItemStack stack) {
        if (hasTarget(stack)) {
            return Component.translatable("item.malum.bound_soul_tag");
        }
        return super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        SoulTagDataComponent data = getTarget(stack);

        if (data != null) {
            tooltip.add(Component.translatable("item.malum.soul_tag.target", data.targetName()).withStyle(ChatFormatting.DARK_GREEN));
        }
    }
    public static void extractSoul(LivingDamageEvent.Post event) {
        double RANGE = 8.0D;
        LivingEntity damagedEntity = event.getEntity();

        if (damagedEntity.level().isClientSide()) {
            return;
        }
        if(!event.getSource().is(WaywardTags.DamageTypeTags.IS_MAGIC)) {
            return;
        }
        if (event.getNewDamage() <= 0.0F) {
            return;
        }

        double rangeSquared = RANGE * RANGE;

        List<SoulTagEntity> soulTags =
                damagedEntity.level().getEntitiesOfClass(
                        SoulTagEntity.class,
                        damagedEntity.getBoundingBox().inflate(RANGE),
                        SoulTagEntity::isAlive
                );

        for (SoulTagEntity soulTag : soulTags) {
            if (soulTag.distanceToSqr(damagedEntity) <= rangeSquared) {
                soulTag.createSoulTag(damagedEntity);
                break;
            }
        }
    }
}