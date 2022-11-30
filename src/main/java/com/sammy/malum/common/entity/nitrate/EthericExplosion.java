package com.sammy.malum.common.entity.nitrate;

import com.sammy.malum.core.setup.content.DamageSourceRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.ExplosionEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EthericExplosion extends Explosion {
    public EthericExplosion(Level pLevel, @Nullable Entity pSource, double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius) {
        super(pLevel, pSource, pToBlowX, pToBlowY, pToBlowZ, pRadius);
    }

    public EthericExplosion(Level pLevel, @Nullable Entity pSource, double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, List<BlockPos> pPositions) {
        super(pLevel, pSource, pToBlowX, pToBlowY, pToBlowZ, pRadius, pPositions);
    }

    public EthericExplosion(Level pLevel, @Nullable Entity pSource, double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire, BlockInteraction pBlockInteraction, List<BlockPos> pPositions) {
        super(pLevel, pSource, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction, pPositions);
    }

    public EthericExplosion(Level pLevel, @Nullable Entity pSource, double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire, BlockInteraction pBlockInteraction) {
        super(pLevel, pSource, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction);
    }

    public EthericExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire, BlockInteraction pBlockInteraction) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction);
    }

    @Override
    public DamageSource getDamageSource() {
        if (getSourceMob() != null) {
            return DamageSourceRegistry.SOUL_STRIKE;
        }
        return DamageSourceRegistry.causeSoulStrikeDamage(getSourceMob());
    }

    public static void processExplosion(ExplosionEvent.Detonate event) {
        if (event.getExplosion() instanceof EthericExplosion) {
            event.getAffectedEntities().removeIf(e -> e instanceof AbstractNitrateEntity);
        }
    }

    public static EthericExplosion explode(Level level, @Nullable Entity pEntity, double pX, double pY, double pZ, float pExplosionRadius, Explosion.BlockInteraction pMode) {
        return explode(level, pEntity, null, null, pX, pY, pZ, pExplosionRadius, false, pMode);
    }

    public static EthericExplosion explode(Level level, @Nullable Entity pEntity, double pX, double pY, double pZ, float pExplosionRadius, boolean pCausesFire, Explosion.BlockInteraction pMode) {
        return explode(level, pEntity, null, null, pX, pY, pZ, pExplosionRadius, pCausesFire, pMode);
    }

    public static EthericExplosion explode(Level level, @Nullable Entity pExploder, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pContext, double pX, double pY, double pZ, float pSize, boolean pCausesFire, Explosion.BlockInteraction pMode) {
        EthericExplosion explosion = new EthericExplosion(level, pExploder, pDamageSource, pContext, pX, pY, pZ, pSize, pCausesFire, pMode);
        if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(level, explosion)) return explosion;
        if (!level.isClientSide) {
            explosion.explode();
        }
        explosion.finalizeExplosion(true);
        return explosion;
    }
}
