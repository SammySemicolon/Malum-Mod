package com.sammy.malum.common.item.curiosities.weapons.staff;

import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.registry.client.*;
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
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

import java.awt.*;

public class AuricFlameStaffItem extends AbstractStaffItem {

    public static final Color AURIC_YELLOW = new Color(239, 215, 75);
    public static final Color AURIC_PURPLE = new Color(236, 54, 163);
    public static final ColorParticleData AURIC_COLOR_DATA = ColorParticleData.create(AURIC_YELLOW, AURIC_PURPLE).setEasing(Easing.SINE_IN_OUT).setCoefficient(0.9f).build();
    public static final ColorParticleData REVERSE_AURIC_COLOR_DATA = ColorParticleData.create(AURIC_PURPLE, AURIC_YELLOW).setEasing(Easing.SINE_IN_OUT).setCoefficient(0.9f).build();

    public AuricFlameStaffItem(Tier tier, float magicDamage, Properties builderIn) {
        super(tier, 20, magicDamage, builderIn);
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return super.isValidRepairItem(pToRepair, pRepair);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnChargeParticles(Level pLevel, LivingEntity pLivingEntity, Vec3 pos, ItemStack pStack, float pct) {
        RandomSource random = pLevel.random;
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, 0.25f, 0.5f).setSpinOffset(RandomHelper.randomBetween(random, 0f, 6.28f)).build();
        DirectionalParticleBuilder.create(ParticleRegistry.HEXAGON)
                .setTransparencyData(GenericParticleData.create(0.5f * pct, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.35f * pct, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setSpinData(spinData)
                .setColorData(AURIC_COLOR_DATA)
                .setLifetime(5)
                .setDirection(pLivingEntity.getLookAngle().normalize())
                .setMotion(pLivingEntity.getLookAngle().normalize().scale(0.05f))
                .enableNoClip()
                .enableForcedSpawn()
                .disableCull()
                .setLifeDelay(2)
                .spawn(pLevel, pos.x, pos.y, pos.z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(pLevel, pos.x, pos.y, pos.z);;
    }

    @Override
    public int getCooldownDuration(Level level, LivingEntity livingEntity) {
        return 160;
    }

    @Override
    public int getProjectileCount(Level level, LivingEntity livingEntity, float pct) {
        return pct == 1f ? 5 : 0;
    }

    @Override
    public void fireProjectile(LivingEntity player, ItemStack stack, Level level, InteractionHand hand, float chargePercentage, int count) {
        final float ceil = (float) Math.ceil(count / 2f);
        float spread = count > 0 ? ceil * 0.2f * (count % 2L == 0 ? 1 : -1) : 0f;
        float pitchOffset = 6f - (3f + ceil);
        int spawnDelay = (int) (ceil * 4);
        float velocity = 2f;
        float magicDamage = (float) player.getAttributes().getValue(LodestoneAttributeRegistry.MAGIC_DAMAGE.get());
        Vec3 pos = getProjectileSpawnPos(player, hand, 0.5f, 0.5f);
        AuricFlameBoltEntity entity = new AuricFlameBoltEntity(level, pos.x, pos.y, pos.z);
        entity.setData(player, magicDamage, spawnDelay);
        entity.setItem(stack);

        entity.shootFromRotation(player, player.getXRot(), player.getYRot(), -pitchOffset, velocity, 0f);
        Vec3 projectileDirection = entity.getDeltaMovement();
        float yRot = ((float) (Mth.atan2(projectileDirection.x, projectileDirection.z) * (double) (180F / (float) Math.PI)));
        float yaw = (float) Math.toRadians(yRot);
        Vec3 left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
        entity.setDeltaMovement(entity.getDeltaMovement().add(left.scale(spread)));
        level.addFreshEntity(entity);
    }
}
