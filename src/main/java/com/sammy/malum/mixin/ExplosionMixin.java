package com.sammy.malum.mixin;

import com.sammy.malum.common.data.attachment.abilities.AvariceData;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.common.geas.pact.infernal.*;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioDischargeRing;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioHoarderRing;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioProspectorBelt;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import javax.annotation.Nullable;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @Unique
    boolean malum$hasHoarderRing;
    @Unique
    boolean malum$hasProspectorBelt;

    @Mutable
    @Shadow
    @Final
    private float radius;

    @Shadow
    @Nullable
    public abstract LivingEntity getIndirectSourceEntity();

    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Explosion$BlockInteraction;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/Holder;)V", at = @At(value = "RETURN"))
    private void malum$modifyExplosion(Level level, Entity source, DamageSource damageSource, ExplosionDamageCalculator damageCalculator, double x, double y, double z, float radius, boolean fire, Explosion.BlockInteraction blockInteraction, ParticleOptions smallExplosionParticles, ParticleOptions largeExplosionParticles, Holder explosionSound, CallbackInfo ci) {
        LivingEntity sourceEntity = getIndirectSourceEntity();
        this.radius = CurioDischargeRing.increaseExplosionRadius(sourceEntity, radius);
        this.radius = BlastweaverGeas.increaseExplosionRadius(sourceEntity, radius);
    }

    @Inject(method = "finalizeExplosion", at = @At(value = "HEAD"))
    private void malum$CacheHoarderRing(boolean pSpawnParticles, CallbackInfo ci) {
        LivingEntity entity = getIndirectSourceEntity();
        if (entity != null) {
            malum$hasHoarderRing = CurioHoarderRing.hasHoarderRing(entity);
            malum$hasProspectorBelt = CurioProspectorBelt.hasProspectorBelt(entity);
        }
    }

    //TODO: This shouldn't use a redirect, hopefully it's fine!
    @Redirect(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V"))
    private void malum$popResource(Level level, BlockPos pos, ItemStack stack) {
        pos = CurioHoarderRing.getExplosionPos(malum$hasHoarderRing, pos, getIndirectSourceEntity(), stack);
        if (malum$hasProspectorBelt) {
            var avarice = AvariceData.getAppliedAvarice(getIndirectSourceEntity());
            AvariceData.popResourceAndMarkEntity(level, pos, stack, avarice);
        } else {
            Block.popResource(level, pos, stack);
        }
    }

    @Inject(method = "getIndirectSourceEntityInternal", at = @At(value = "HEAD"), cancellable = true)
    private static void malum$modifyIndirectSourceEntity(Entity source, CallbackInfoReturnable<LivingEntity> cir) {
        AbstractNitrateEntity.getOwnerFromExplosion(source).ifPresent(cir::setReturnValue);
    }
}