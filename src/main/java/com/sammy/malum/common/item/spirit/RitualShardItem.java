package com.sammy.malum.common.item.spirit;

import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.client.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.screen.*;

import java.util.*;

public class RitualShardItem extends Item implements ItemParticleSupplier {

    public static final String RITUAL_TYPE = "stored_ritual";
    public static final String STORED_SPIRITS = "stored_spirits";

    public RitualShardItem(Properties properties) {
        super(properties);
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        final MalumRitualType ritualType = getRitualType(pStack);
        if (ritualType != null) {
            return ritualType.spirit.getItemRarity();
        }
        return super.getRarity(pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        final MalumRitualType ritualType = getRitualType(pStack);
        final MalumRitualTier ritualTier = getRitualTier(pStack);
        if (ritualType != null && ritualTier != null) {
            pTooltip.addAll(ritualType.makeRitualShardDescriptor(ritualTier));
        }
    }

    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        final MalumRitualType ritualType = getRitualType(stack);
        final MalumRitualTier ritualTier = getRitualTier(stack);
        if (ritualType != null && ritualTier != null) {
            MalumSpiritType type = ritualType.spirit;
            ScreenParticleEffects.spawnSpiritShardScreenParticles(target, type);
            if (ritualTier.isGreaterThan(MalumRitualTier.DIM)) {
                float distance = 2f + ritualTier.potency;
                var rand = Minecraft.getInstance().level.getRandom();
                for (int i = 0; i < 2; i++) {
                    float time = (((i == 1 ? 3.14f : 0) + ((level.getGameTime() + partialTick) * 0.05f)) % 6.28f);
                    float scalar = 0.4f + 0.15f * ritualTier.potency;
                    if (time > 1.57f && time < 4.71f) {
                        scalar *= Easing.QUAD_IN.ease(Math.abs(3.14f - time) / 1.57f, 0, 1, 1);
                    }
                    double xOffset = Math.sin(time) * distance;
                    double yOffset = Math.cos(time) * distance * 0.5f;
                    ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.WISP, target)
                            .setTransparencyData(GenericParticleData.create(0.2f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                            .setSpinData(SpinParticleData.create(RandomHelper.randomBetween(rand, 0.2f, 0.4f)).setEasing(Easing.EXPO_OUT).build())
                            .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(rand, 0.2f, 0.3f)*scalar, 0).setEasing(Easing.EXPO_OUT).build())
                            .setColorData(type.createColorData().build())
                            .setLifetime(RandomHelper.randomBetween(rand, 80, 120))
                            .setRandomOffset(0.1f)
                            .spawnOnStack(xOffset, yOffset);
                    if (!ritualTier.isGreaterThan(MalumRitualTier.BRIGHT)) {
                        break;
                    }
                }
            }
        }
    }

    public static MalumRitualType getRitualType(ItemStack stack) {
        return stack.hasTag() ? RitualRegistry.get(new ResourceLocation(stack.getTag().getString(RITUAL_TYPE))) : null;
    }

    public static MalumRitualTier getRitualTier(ItemStack stack) {
        return stack.hasTag() ? MalumRitualTier.figureOutTier(stack.getTag().getInt(STORED_SPIRITS)) : null;
    }
}
