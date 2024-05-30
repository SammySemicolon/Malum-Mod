package com.sammy.malum.common.item.spirit;

import com.sammy.malum.core.systems.ritual.MalumRitualTier;
import com.sammy.malum.core.systems.ritual.MalumRitualType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.RitualRegistry;
import com.sammy.malum.visual_effects.ScreenParticleEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler.ItemParticleSupplier;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.registry.common.particle.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.ScreenParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;

import java.util.List;

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
                            .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(rand, 0.2f, 0.3f) * scalar, 0).setEasing(Easing.EXPO_OUT).build())
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
