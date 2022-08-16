package com.sammy.malum.mixin;

import com.sammy.malum.common.item.equipment.curios.CurioDelverBelt;
import com.sammy.malum.common.item.equipment.curios.CurioEarthenRing;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.helpers.CurioHelper;

import javax.annotation.Nullable;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @Unique
    boolean hasEarthenRing;

    @Shadow
    @Nullable
    public abstract LivingEntity getSourceMob();

    @ModifyArg(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getDrops(Lnet/minecraft/world/level/storage/loot/LootContext$Builder;)Ljava/util/List;"))
    private LootContext.Builder malum$getBlockDrops(LootContext.Builder builder) {
        return CurioDelverBelt.applyFortune(getSourceMob(), builder);
    }

    @Inject(method = "finalizeExplosion", at = @At(value = "HEAD"))
    private void malum$finalizeExplosion(boolean pSpawnParticles, CallbackInfo ci) {
        hasEarthenRing = CurioEarthenRing.hasEarthenRing(getSourceMob());
    }

    @ModifyArg(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V"), index = 1)
    private BlockPos malum$popResource(BlockPos value) {
        return CurioEarthenRing.getExplosionPos(hasEarthenRing, value, getSourceMob());
    }
}