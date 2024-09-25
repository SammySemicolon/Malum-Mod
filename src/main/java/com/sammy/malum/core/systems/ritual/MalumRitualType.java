package com.sammy.malum.core.systems.ritual;

import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;

import java.util.*;
import java.util.function.*;

public abstract class MalumRitualType {
    public final MalumSpiritType spirit;
    public final ResourceLocation identifier;
    protected MalumRitualRecipeData recipeData;

    public MalumRitualType(ResourceLocation identifier, MalumSpiritType spirit) {
        this.identifier = identifier;
        this.spirit = spirit;
    }

    public ItemInteractionResult onUsePlinth(RitualPlinthBlockEntity ritualPlinth, Player player, InteractionHand hand) {
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
    public boolean isItemStackValid(RitualPlinthBlockEntity ritualPlinth, ItemStack stack) {
        return false;
    }

    public abstract void triggerRitualEffect(RitualPlinthBlockEntity ritualPlinth);

    public void setRecipeData(MalumRitualRecipeData recipeData) {
        this.recipeData = recipeData;
    }

    public MalumRitualRecipeData getRecipeData() {
        return recipeData;
    }

    public String translationIdentifier() {
        return identifier.getNamespace() + ".gui.ritual." + identifier.getPath();
    }

    public ResourceLocation getIcon() {
        return identifier.withPrefix("textures/vfx/ritual/").withSuffix(".png");
    }

    public RitualShardItem.Props createShardProps(MalumRitualTier tier) {
        return new RitualShardItem.Props(identifier.toString(), tier.spiritThreshold);
    }

    public List<Component> makeRitualShardDescriptor(MalumRitualTier ritualTier) {
        List<Component> tooltip = new ArrayList<>();
        var spiritStyleModifier = spirit.getItemRarity().getStyleModifier();
        tooltip.add(makeDescriptorComponent("malum.gui.ritual.type", translationIdentifier(), spiritStyleModifier));
        tooltip.add(makeDescriptorComponent("malum.gui.ritual.tier", ritualTier.translationIdentifier(), spiritStyleModifier));
        return tooltip;
    }

    public List<Component> makeCodexDetailedDescriptor() {
        List<Component> tooltip = new ArrayList<>();
        var spiritStyleModifier = spirit.getItemRarity().getStyleModifier();
        tooltip.add(Component.translatable(translationIdentifier()).withStyle(spiritStyleModifier));
        tooltip.add(makeDescriptorComponent("malum.gui.rite.effect", "malum.gui.book.entry.page.text." + identifier + ".hover"));
        return tooltip;
    }

    public final Component makeDescriptorComponent(String translationKey1, String translationKey2) {
        return Component.translatable(translationKey1).withStyle(ChatFormatting.GOLD)
                .append(Component.translatable(translationKey2).withStyle(ChatFormatting.YELLOW));
    }

    public final Component makeDescriptorComponent(String translationKey1, String translationKey2, UnaryOperator<Style> style) {
        return Component.translatable(translationKey1).withStyle(ChatFormatting.GOLD)
                .append(Component.translatable(translationKey2).withStyle(style));
    }
}