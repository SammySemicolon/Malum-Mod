package com.sammy.malum.mixin;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.common.item.equipment.curios.CurioDelverBelt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.Nullable;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @Shadow @Nullable public abstract LivingEntity getSourceMob();

    @ModifyArg(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getDrops(Lnet/minecraft/world/level/storage/loot/LootContext$Builder;)Ljava/util/List;"))
    private LootContext.Builder malum$getBlockDrops(LootContext.Builder builder) {
        return CurioDelverBelt.applyFortune(getSourceMob(), builder);
    }
}