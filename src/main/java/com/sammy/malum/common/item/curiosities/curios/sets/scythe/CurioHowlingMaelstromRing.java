package com.sammy.malum.common.item.curiosities.curios.sets.scythe;

import com.sammy.malum.common.entity.scythe.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class CurioHowlingMaelstromRing extends MalumCurioItem implements IMalumEventResponderBody {
    public CurioHowlingMaelstromRing(Properties builder) {
        super(builder, MalumTrinketFamily.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("rebound_maelstrom"));
        consumer.accept(TooltipComponentHelper.negativeCurioEffect("longer_rebound_cooldown"));
    }

    public static void handleMaelstrom(ServerLevel serverLevel, LivingEntity scytheOwner, AbstractScytheProjectileEntity projectile) {
        if (serverLevel.getGameTime() % 5L == 0) {
            boolean dealtDamage = false;
            final AABB aabb = projectile.getBoundingBox().inflate(2);
            float damage = projectile.damage * 0.2f;
            float magicDamage = projectile.magicDamage * 0.2f;
            var damageSource = DamageTypeHelper.create(serverLevel, MalumDamageTypes.SCYTHE_MAELSTROM, projectile, scytheOwner);
            var magicDamageSource = DamageTypeHelper.create(serverLevel, MalumDamageTypes.VOODOO, projectile, scytheOwner);
            
            var heldItem = scytheOwner.getMainHandItem();
            scytheOwner.setItemInHand(InteractionHand.MAIN_HAND, projectile.getItem());
            for (Entity target : serverLevel.getEntities(projectile, aabb, t -> maelstromCanHitEntity(scytheOwner, t))) {

                target.invulnerableTime = 0;
                boolean success = target.hurt(damageSource, damage);
                if (success && target instanceof LivingEntity livingentity) {
                    if (projectile.magicDamage > 0) {
                        if (!livingentity.isDeadOrDying()) {
                            livingentity.invulnerableTime = 0;
                            livingentity.hurt(magicDamageSource, magicDamage);
                        }
                    }
                    dealtDamage = true;
                }
            }
            scytheOwner.setItemInHand(InteractionHand.MAIN_HAND, heldItem);
            if (dealtDamage) {
                projectile.returnTimer += 1;
                projectile.setDeltaMovement(projectile.getDeltaMovement().scale(0.7f));
            }
        }
    }

    protected static boolean maelstromCanHitEntity(LivingEntity scytheOwner, Entity pTarget) {
        if (!pTarget.canBeHitByProjectile()) {
            return false;
        } else {
            return pTarget != scytheOwner && !scytheOwner.isPassengerOfSameVehicle(pTarget);
        }
    }
}