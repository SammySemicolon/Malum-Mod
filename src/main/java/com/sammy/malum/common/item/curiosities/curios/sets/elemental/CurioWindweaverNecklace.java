 package com.sammy.malum.common.item.curiosities.curios.sets.elemental;

 import com.sammy.malum.common.item.*;
 import com.sammy.malum.common.item.curiosities.curios.*;
 import com.sammy.malum.core.helpers.*;
 import com.sammy.malum.registry.common.*;
 import net.minecraft.network.chat.*;
 import net.minecraft.world.effect.*;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.*;
 import net.minecraft.world.entity.player.*;
 import net.minecraft.world.item.*;
 import net.neoforged.neoforge.event.level.*;
 import team.lodestar.lodestone.helpers.*;
 import top.theillusivec4.curios.api.*;

 import java.util.function.*;

 public class CurioWindweaverNecklace extends MalumCurioItem implements IMalumEventResponderBody {

     public CurioWindweaverNecklace(Properties builder) {
         super(builder, MalumTrinketFamily.ELEMENTAL);
     }

     @Override
     public void addExtraTooltipLines(Consumer<Component> consumer) {
         consumer.accept(TooltipComponentHelper.positiveCurioEffect("windweaver_ascension"));
         consumer.accept(TooltipComponentHelper.positiveCurioEffect("windweaver_gliding"));
     }

     @Override
     public void curioTick(SlotContext slotContext, ItemStack stack) {
         var entity = slotContext.entity();
         if (entity.onGround() || entity.isInWater() || entity.isInLava()) {
             return;
         }
         if (entity.hasEffect(MalumMobEffects.ASCENSION) || entity.hasEffect(MalumMobEffects.LIFTED)) {
             if (entity instanceof Player player && player.level().isClientSide) {
                 var velocity = player.getDeltaMovement();
                 var angle = player.getLookAngle();
                 if (angle.y > -0.8f && angle.y < 0.8f) {
                     float delta = (float) Math.clamp(velocity.y * 3f, 0, 1);
                     if (delta > 0) {
                         float target = (float) (0.2f + player.getAttributeValue(Attributes.MOVEMENT_SPEED)) * 0.45f;
                         var added = angle.scale(target).add(0, 0.05f, 0).multiply(delta, 0.25f, delta);
                         player.setDeltaMovement(velocity.add(added).multiply(0.95f, 0.9f, 0.95f));
                     }
                 }
             }
         }
     }

     public static void onExplosionKnockback(ExplosionKnockbackEvent event) {
         var explosion = event.getExplosion();

         var entity = event.getAffectedEntity();
         if (event.getAffectedEntity() instanceof LivingEntity livingEntity) {
             if (!CurioHelper.hasCurioEquipped(livingEntity, MalumContent.Gear.NECKLACE_OF_THE_WINDWEAVER.asItem())) {
                 return;
             }
             if (explosion.damageCalculator.shouldDamageEntity(explosion, entity)) {
                 return;
             }
             float minimumUpwardsVelocity = 0.5f;
             double horizontalScalar = 2f;
             double verticalScalar = entity instanceof Player ? 1.25f : 1.75f;
             var knockbackVelocity = event.getKnockbackVelocity();
             if (knockbackVelocity.y < minimumUpwardsVelocity) {
                 final double length = knockbackVelocity.length();
                 knockbackVelocity = knockbackVelocity.normalize().multiply(length, minimumUpwardsVelocity, length);
             }
             event.setKnockbackVelocity(knockbackVelocity.multiply(horizontalScalar, verticalScalar, horizontalScalar));
             if (entity instanceof Player player) {
                 player.addEffect(new MobEffectInstance(MalumMobEffects.ASCENSION, 200, 3));
             }
         }
     }
 }
