package com.sammy.malum.mixin;

import com.sammy.malum.common.item.curiosities.curios.prospector.CurioDemolitionistRing;
import com.sammy.malum.common.item.curiosities.curios.prospector.CurioHoarderRing;
import com.sammy.malum.common.item.curiosities.curios.prospector.CurioProspectorBelt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @Unique
    boolean malum$hasHoarderRing;
    @Unique
    ItemStack malum$droppedItem;

    @Shadow
    @Nullable
    public abstract LivingEntity getSourceMob();

    @Mutable
    @Shadow
    @Final
    private float radius;

    @ModifyArg(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getDrops(Lnet/minecraft/world/level/storage/loot/LootParams$Builder;)Ljava/util/List;"))
    private LootParams.Builder malum$getBlockDrops(LootParams.Builder builder) {
        return CurioProspectorBelt.applyFortune(getSourceMob(), builder);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Explosion$BlockInteraction;)V", at = @At(value = "RETURN"))
    private void malum$modifyExplosionStats(Level pLevel, Entity pSource, DamageSource pDamageSource, ExplosionDamageCalculator pDamageCalculator, double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire, Explosion.BlockInteraction pBlockInteraction, CallbackInfo ci) {
        radius = CurioDemolitionistRing.increaseExplosionRadius(getSourceMob(), radius);
    }

    @Inject(method = "finalizeExplosion", at = @At(value = "HEAD"))
    private void malum$finalizeExplosion(boolean pSpawnParticles, CallbackInfo ci) {
        malum$hasHoarderRing = CurioHoarderRing.hasHoarderRing(getSourceMob());
    }

    @ModifyArg(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V"), index = 2)
    private ItemStack malum$popResourceCache(ItemStack pStack) {
        return malum$droppedItem = pStack;
    }

    @ModifyArg(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V"), index = 1)
    private BlockPos malum$popResource(BlockPos value) {
        return CurioHoarderRing.getExplosionPos(malum$hasHoarderRing, value, getSourceMob(), malum$droppedItem);
    }
}