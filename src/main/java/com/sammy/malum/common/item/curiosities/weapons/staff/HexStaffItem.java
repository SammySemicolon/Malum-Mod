package com.sammy.malum.common.item.curiosities.weapons.staff;

import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

public class HexStaffItem extends AbstractStaffItem {

    public HexStaffItem(Tier tier, float magicDamage, Properties builderIn) {
        super(tier, 20, magicDamage, builderIn);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnChargeParticles(Level pLevel, LivingEntity pLivingEntity, Vec3 pos, ItemStack pStack, float pct) {
        RandomSource random = pLevel.random;
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, 0.25f, 0.5f).setSpinOffset(RandomHelper.randomBetween(random, 0f, 6.28f)).build();
        DirectionalParticleBuilder.create(ParticleRegistry.HEXAGON)
                .setTransparencyData(GenericParticleData.create(0.6f * pct, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setSpinData(spinData)
                .setScaleData(GenericParticleData.create(0.3f * pct, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(SpiritTypeRegistry.WICKED_SPIRIT.createMainColorData().build())
                .setLifetime(5)
                .setLifeDelay(2)
                .setDirection(pLivingEntity.getLookAngle().normalize())
                .setMotion(pLivingEntity.getLookAngle().normalize().scale(0.05f))
                .enableNoClip()
                .enableForcedSpawn()
                .spawn(pLevel, pos.x, pos.y, pos.z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(pLevel, pos.x, pos.y, pos.z);
    }

    @Override
    public int getCooldownDuration(Level level, LivingEntity livingEntity) {
        return 80;
    }

    @Override
    public int getProjectileCount(Level level, LivingEntity livingEntity, float pct) {
        return pct == 1f ? 3 : 0;
    }

    @Override
    public void fireProjectile(LivingEntity player, ItemStack stack, Level level, InteractionHand hand, float chargePercentage, int count) {
        float pitchOffset = 3f + count;
        int spawnDelay = count * 3;
        float velocity = 2f + 0.5f * count;
        float magicDamage = (float) player.getAttributes().getValue(LodestoneAttributeRegistry.MAGIC_DAMAGE.get());
        Vec3 pos = getProjectileSpawnPos(player, hand, 0.5f, 0.5f);
        HexBoltEntity entity = new HexBoltEntity(level, pos.x, pos.y, pos.z);
        entity.setData(player, magicDamage, spawnDelay);
        entity.setItem(stack);

        entity.shootFromRotation(player, player.getXRot(), player.getYRot(), -pitchOffset, velocity, 0F);
        level.addFreshEntity(entity);
    }
}
