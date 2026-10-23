package com.sammy.malum.common.item.ether;

import com.sammy.malum.common.block.ether.*;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.rendering.particle.standard.builder.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.color.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.spin.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.screen.*;

import java.awt.*;
import java.util.List;

public class EtherItem extends BlockItem implements ParticleEmitterHandler.ItemParticleSupplier {

    public static final DyedItemColor DEFAULT_FIRST_COLOR = new DyedItemColor(15712278, false);
    public static final DyedItemColor DEFAULT_SECOND_COLOR = new DyedItemColor(4607909, false);

    public final boolean isIridescent;

    public static EtherItem ether(Block block, Properties properties) {
        return new EtherItem(block, properties, false);
    }

    public static EtherItem iridescent(Block block, Properties properties) {
        return new EtherItem(block, properties, true);
    }

    public EtherItem(Block block, Properties properties, boolean isIridescent) {
        super(block, properties
                .component(DataComponents.DYED_COLOR, DEFAULT_FIRST_COLOR)
                .component(MalumDataComponents.SECONDARY_DYED_COLOR, DEFAULT_SECOND_COLOR));
        this.isIridescent = isIridescent;
    }

    public static boolean isIridescent(EtherBlockEntity blockEntity) {
        return isIridescent(blockEntity.getBlockState().getBlock().asItem().getDefaultInstance());
    }

    public static boolean isIridescent(ItemStack stack) {
        return stack.getItem() instanceof EtherItem ether && ether.isIridescent;
    }

    @SuppressWarnings("DataFlowIssue")
    public static int getSecondaryColor(ItemStack stack) {
        if (stack.has(MalumDataComponents.SECONDARY_DYED_COLOR)) {
            return stack.get(MalumDataComponents.SECONDARY_DYED_COLOR).rgb();
        }
        return DEFAULT_SECOND_COLOR.rgb();
    }

    @SuppressWarnings("DataFlowIssue")
    public static int getPrimaryColor(ItemStack stack) {
        if (stack.has(DataComponents.DYED_COLOR)) {
            return stack.get(DataComponents.DYED_COLOR).rgb();
        }
        return DEFAULT_FIRST_COLOR.rgb();
    }

    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        var firstColor = new Color(EtherItem.getPrimaryColor(stack));
        var secondColor = new Color(EtherItem.getSecondaryColor(stack));
        float alphaMultiplier = isIridescent ? 0.75f : 0.5f;
        int yOffset = isIridescent ? 3 : 4;
        int xOffset = isIridescent ? -1 : 0;
        float time = level.getGameTime() + partialTick;
        var spinDataBuilder = SpinParticleData.create(0, 1).setSpinOffset(0.025f * time % 6.28f).setEasing(Easing.EXPO_IN_OUT);
        ScreenParticleBuilder.create(LodestoneScreenParticleTypes.STAR, target)
                .setTransparencyData(GenericParticleData.create(0.08f * alphaMultiplier, 0f).setEasing(Easing.QUINTIC_IN).build())
                .setScaleData(GenericParticleData.create((float) (1.5f + Math.sin(time * 0.1f) * 0.125f), 0).build())
                .setColorData(ColorParticleData.create(firstColor, secondColor).setCoefficient(1.25f).build())
                .setLifetime(7)
                .setRandomOffset(0.05f)
                .setSpinData(spinDataBuilder.build())
                .spawnOnStack(xOffset, yOffset)
                .setScaleData(GenericParticleData.create((float) (1.4f - Math.sin(time * 0.075f) * 0.125f), 0).build())
                .setColorData(ColorParticleData.create(secondColor, firstColor).build())
                .setSpinData(spinDataBuilder.setSpinOffset(0.785f - 0.01f * time % 6.28f).build())
                .spawnOnStack(xOffset, yOffset);
    }

    public static boolean canApplySecondaryColor(ItemStack stack) {
        return stack.getItem() instanceof EtherItem ether && ether.isIridescent;
    }

    public static ItemStack applyDyesToSecondaryColor(ItemStack stack, List<DyeItem> dyes) {
        if (!stack.is(ItemTags.DYEABLE)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack = stack.copyWithCount(1);
            int i = 0;
            int j = 0;
            int k = 0;
            int l = 0;
            int i1 = 0;
            DyedItemColor dyeditemcolor = itemstack.get(MalumDataComponents.SECONDARY_DYED_COLOR);
            if (dyeditemcolor != null) {
                int j1 = FastColor.ARGB32.red(dyeditemcolor.rgb());
                int k1 = FastColor.ARGB32.green(dyeditemcolor.rgb());
                int l1 = FastColor.ARGB32.blue(dyeditemcolor.rgb());
                l += Math.max(j1, Math.max(k1, l1));
                i += j1;
                j += k1;
                k += l1;
                i1++;
            }

            for (DyeItem dyeitem : dyes) {
                int j3 = dyeitem.getDyeColor().getTextureDiffuseColor();
                int i2 = FastColor.ARGB32.red(j3);
                int j2 = FastColor.ARGB32.green(j3);
                int k2 = FastColor.ARGB32.blue(j3);
                l += Math.max(i2, Math.max(j2, k2));
                i += i2;
                j += j2;
                k += k2;
                i1++;
            }

            int l2 = i / i1;
            int i3 = j / i1;
            int k3 = k / i1;
            float f = (float) l / (float) i1;
            float f1 = (float) Math.max(l2, Math.max(i3, k3));
            l2 = (int) ((float) l2 * f / f1);
            i3 = (int) ((float) i3 * f / f1);
            k3 = (int) ((float) k3 * f / f1);
            int l3 = FastColor.ARGB32.color(0, l2, i3, k3);
            boolean flag = dyeditemcolor == null || dyeditemcolor.showInTooltip();
            itemstack.set(MalumDataComponents.SECONDARY_DYED_COLOR, new DyedItemColor(l3, flag));
            return itemstack;
        }
    }
}