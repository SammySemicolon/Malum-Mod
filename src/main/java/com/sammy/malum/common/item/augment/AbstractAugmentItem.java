package com.sammy.malum.common.item.augment;

import com.sammy.malum.common.block.curiosities.spirit_crucible.CrucibleTuning;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public class AbstractAugmentItem extends Item {
    public final MalumSpiritType spiritType;

    public AbstractAugmentItem(Properties pProperties, MalumSpiritType spiritType) {
        super(pProperties);
        this.spiritType = spiritType;
    }

    public float getSpeedIncrease() {
        return 0f;
    }

    public float getFuelUsageRateIncrease() {
        return 0f;
    }

    public float getInstabilityIncrease() {
        return 0f;
    }

    public float getFortuneChance() {
        return 0f;
    }

    public float getChainFocusingChance() {
        return 0f;
    }

    public float getShieldingChance() {
        return 0f;
    }

    public float getRestorationChance() {
        return 0f;
    }

    public float getWeakestAttributeMultiplier() {
        return 0f;
    }

    public float getTuningStrengthIncrease() {
        return 0f;
    }

    public static Optional<AbstractAugmentItem> getAugmentType(ItemStack stack) {
        return stack.getItem() instanceof AbstractAugmentItem augmentItem ? Optional.of(augmentItem) : Optional.empty();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("malum.gui.augment.slot").withStyle(ChatFormatting.GOLD)
                .append(Component.translatable("malum.gui.augment.type." + getAugmentTypeTranslator()).withStyle(ChatFormatting.YELLOW)));
    }

    public static void addAugmentAttributeTooltip(ItemStack itemStack, Player player, List<Component> components, TooltipFlag tooltipFlag) {
        if (itemStack.getItem() instanceof AbstractAugmentItem augmentItem) {
            List<Component> tooltip = components;
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("malum.gui.augment.installed").withStyle(ChatFormatting.GOLD));
            addAugmentStatComponent(tooltip, "malum.gui.crucible.attribute.tuning_potency", augmentItem.getTuningStrengthIncrease());
            addAugmentStatComponent(tooltip, "malum.gui.crucible.attribute.weakest_boost", augmentItem.getWeakestAttributeMultiplier());
            addAugmentStatComponent(tooltip, CrucibleTuning.CrucibleAttributeType.RESTORATION_CHANCE, augmentItem.getRestorationChance());
            addAugmentStatComponent(tooltip, CrucibleTuning.CrucibleAttributeType.SHIELDING_CHANCE, augmentItem.getShieldingChance());
            addAugmentStatComponent(tooltip, CrucibleTuning.CrucibleAttributeType.CHAIN_FOCUSING_CHANCE, augmentItem.getChainFocusingChance());
            addAugmentStatComponent(tooltip, CrucibleTuning.CrucibleAttributeType.FORTUNE_CHANCE, augmentItem.getFortuneChance());
            addAugmentStatComponent(tooltip, CrucibleTuning.CrucibleAttributeType.INSTABILITY, augmentItem.getInstabilityIncrease());
            addAugmentStatComponent(tooltip, CrucibleTuning.CrucibleAttributeType.FUEL_USAGE_RATE, augmentItem.getFuelUsageRateIncrease());
            addAugmentStatComponent(tooltip, CrucibleTuning.CrucibleAttributeType.FOCUSING_SPEED, augmentItem.getSpeedIncrease());
        }
    }

    public String getAugmentTypeTranslator() {
        return "augment";
    }

    public static void addAugmentStatComponent(List<Component> tooltip, CrucibleTuning.CrucibleAttributeType attributeType, float value) {
        boolean inverse = attributeType.equals(CrucibleTuning.CrucibleAttributeType.FUEL_USAGE_RATE) || attributeType.equals(CrucibleTuning.CrucibleAttributeType.INSTABILITY);
        makeAugmentStatComponent(attributeType, value, inverse).ifPresent(tooltip::add);
    }

    public static void addAugmentStatComponent(List<Component> tooltip, String id, float value) {
        makeAugmentStatComponent(id, value, false).ifPresent(tooltip::add);
    }

    public static Optional<Component> makeAugmentStatComponent(CrucibleTuning.CrucibleAttributeType attributeType, float value, boolean inverse) {
        return makeAugmentStatComponent(attributeType.translation(), value, inverse);
    }

    public static Optional<Component> makeAugmentStatComponent(String id, float value, boolean inverse) {
        if (value == 0) {
            return Optional.empty();
        }
        boolean isPositive = value > 0f;
        String modifierSign = isPositive ? "attribute.modifier.plus.0" : "attribute.modifier.take.0";
        ChatFormatting style = ChatFormatting.BLUE;
        if ((inverse && isPositive) || (!inverse && !isPositive)) {
            style = ChatFormatting.RED;
        }
        return Optional.of(Component.translatable(
                modifierSign,
                ATTRIBUTE_MODIFIER_FORMAT.format(Math.abs(value)),
                Component.translatable(id)).withStyle(
                style)
        );
    }


}
