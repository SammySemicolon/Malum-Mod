package com.sammy.malum.common.block.curiosities.sorcery.spirit_altar;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.inventory.ItemStackHandlerItemDisplayData;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackBlockHandler;
import team.lodestar.lodestone.modules.toolkit.inventory.display.ItemDisplayDataEntry;

import java.util.Objects;

public class SpiritAltarSpiritDisplayData extends ItemStackHandlerItemDisplayData {

    protected static final int WARMUP_DURATION = 30;

    protected final SpiritAltarBlockEntity altar;
    public int warmupTicks;

    public SpiritAltarSpiritDisplayData(LodestoneItemStackBlockHandler parent) {
        super(parent);
        altar = (SpiritAltarBlockEntity) parent.getParent();
    }

    @Override
    public ItemDisplayDataEntry addNewItem(int index, ItemStack stack) {
        var entry = super.addNewItem(index, stack);
        float neededAngle = getAngleForItem(entry, index-1, handler.getNonEmptyStacks().size());
        return entry.setAngle(neededAngle);
    }

    @Override
    public void tick(LodestoneBlockEntity parent, Level level, BlockPos pos, BlockState state) {
        int target = altar.possibleRecipes.isEmpty() ? 0 : WARMUP_DURATION;
        warmupTicks = DataHelper.approach(warmupTicks, target, 1);
        super.tick(parent, level, pos, state);
    }

    @Override
    public float getTurnRate() {
        return 0.01f + getSpinUp(Easing.SINE_IN_OUT) * 0.03f + altar.speed * 0.01f;
    }

    @Override
    public float handleAngleCorrection(ItemDisplayDataEntry item, int index, float total, float targetAngle) {
        float angle = item.getAngle(1);
        float difference = Math.abs(angle - targetAngle);
        float delta = Math.min(difference / 2f, 1f);
        float step = Easing.QUAD_IN_OUT.asValueDistribution(delta, 0.02f, 0.08f, 0.04f);
        if (angle > targetAngle) {
            step *= 0.5f;
        }
        return DataHelper.approach(angle, targetAngle, step);
    }

    @Override
    public float getDistanceForItem(ItemDisplayDataEntry item, int index, float total) {
        float angle = item.getAngle(0);
        float distanceOscillation = Mth.sin(angle) * 0.025f;
        return 1 - getSpinUp(Easing.SINE_OUT) * 0.25f + distanceOscillation;
    }

    @Override
    public float getLiftForItem(ItemDisplayDataEntry item, int index, float total) {
        float delta = Math.min(item.getAge() / 32f, 1f);
        float drop = Easing.QUINTIC_IN_OUT.asValueDistribution(delta, 0.3f, 0.4f, 0f);
        return drop + 0.25f + getSpinUp(Easing.QUARTIC_OUT) * getSpinUp(Easing.BACK_OUT) * 0.5f;
    }

    @Override
    public float getItemScaleForItem(ItemDisplayDataEntry item, int index, float total) {
        float delta = Math.min(item.getAge() / 6f, 1f);
        return Easing.SINE_IN_OUT.lerp(delta, 0f, 0.5f);
    }

    public float getSpinUp(Easing easing) {
        return easing.ease(warmupTicks / (float)WARMUP_DURATION);
    }
}