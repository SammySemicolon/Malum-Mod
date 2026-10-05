package com.sammy.malum.common.block.storage;

import com.sammy.malum.common.item.spirit.SpiritShardItem;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.inventory.ItemStackHandlerItemDisplayData;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackBlockHandler;
import team.lodestar.lodestone.modules.toolkit.inventory.display.ItemDisplayDataEntry;

import java.util.Objects;

public class MalumItemHolderItemDisplayData extends ItemStackHandlerItemDisplayData {

    public MalumItemHolderItemDisplayData(LodestoneItemStackBlockHandler handler) {
        super(handler);
    }

    @Override
    public ItemDisplayDataEntry addNewItem(int index, ItemStack stack) {
        var random = Objects.requireNonNull(handler.getParent().getLevel()).random;
        return super.addNewItem(index, stack).setAngle(random.nextFloat() * 6.28f).setDistance(0.2f);
    }

    @Override
    public float getTurnRate() {
        return 0.2f;
    }

    @Override
    public float getDistanceForItem(ItemDisplayDataEntry item, int index, float total) {
        float rand = (item.getSeed()%50f)/50f;
        float timing = Easing.SINE_IN_OUT.asValueDistribution(rand, 12f, 16f);

        float delta = Math.min(item.getAge() / timing, 1f);
        return Easing.SINE_IN_OUT.lerp(delta, 0.1f, 0f);
    }

    @Override
    public float getItemRotationRateForItem(ItemDisplayDataEntry item, int index, float total) {
        float rand = (item.getSeed()%40f)/40f;
        float timing = Easing.SINE_IN_OUT.asValueDistribution(rand, 24f, 40f);

        float delta = Math.min(item.getAge() / timing, 1f);
        return Easing.BACK_IN.lerp(delta, 0.3f, 0.05f);
    }

    @Override
    public float getItemScaleForItem(ItemDisplayDataEntry item, int index, float total) {
        float delta = Math.min(item.getAge() / 6f, 1f);
        return Easing.SINE_IN_OUT.lerp(delta, 0f, 0.55f);
    }

    @Override
    public float getLiftForItem(ItemDisplayDataEntry item, int index, float total) {
        float delta = Math.min(item.getAge() / 8f, 1f);
        float drop = Easing.QUINTIC_IN_OUT.asValueDistribution(delta, 0.15f, 0.2f, 0f);
        return addSpiritLift(handler.getParent(), item, drop);
    }

    public static float addSpiritLift(LodestoneBlockEntity blockEntity, ItemDisplayDataEntry item, float base) {
        if (item.getStack().getItem() instanceof SpiritShardItem) {
            long gameTime = blockEntity.getLevel().getGameTime();
            return base + Mth.sin((gameTime * 0.05f) % 6.28f) * 0.075f;
        }
        return base;
    }
}