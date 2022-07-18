package com.sammy.malum.mixin;

import com.sammy.malum.common.item.equipment.curios.CurioFortuneBelt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import se.mickelus.tetra.loot.FortuneBonusCondition;

import javax.annotation.Nullable;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @Shadow @Nullable public abstract LivingEntity getSourceMob();

    @ModifyArg(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getDrops(Lnet/minecraft/world/level/storage/loot/LootContext$Builder;)Ljava/util/List;"))
    private LootContext.Builder malum$addBlockDrops(LootContext.Builder builder) {
        return CurioFortuneBelt.applyFortune(getSourceMob(), builder);
    }
}
