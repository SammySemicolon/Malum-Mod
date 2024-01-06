package com.sammy.malum.core.systems.ritual;

import com.sammy.malum.*;
import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;

import java.util.*;

public abstract class MalumRitualType {

    public final MalumSpiritType spirit;
    public final ResourceLocation identifier;
    protected MalumRitualRecipeData recipeData;

    public MalumRitualType(ResourceLocation identifier, MalumSpiritType spirit) {
        this.identifier = identifier;
        this.spirit = spirit;
    }

    public abstract void triggerRitualEffect(RitualPlinthBlockEntity ritualPlinth);
    public void setRecipeData(MalumRitualRecipeData recipeData) {
        this.recipeData = recipeData;
    }

    public MalumRitualRecipeData getRecipeData() {
        return recipeData;
    }

    public String translationIdentifier() {
        return "malum.gui.ritual." + identifier;
    }

    public ResourceLocation getIcon() {
        return new ResourceLocation(identifier.getNamespace(), "textures/vfx/ritual/" + identifier.getPath() + ".png");
    }

    public List<Component> makeDetailedDescriptor() {
        List<Component> tooltip = new ArrayList<>();
        var spiritStyleModifier = spirit.getItemRarity().getStyleModifier();
        tooltip.add(Component.translatable(translationIdentifier()).withStyle(spiritStyleModifier));
        tooltip.add(makeDescriptorComponent("malum.gui.ritual.effect", "malum.gui.book.entry.page.text." + identifier + ".hover"));
        return tooltip;
    }

    public final Component makeDescriptorComponent(String translationKey1, String translationKey2) {
        return Component.translatable(translationKey1).withStyle(ChatFormatting.GOLD)
                .append(Component.translatable(translationKey2).withStyle(ChatFormatting.YELLOW));
    }
}