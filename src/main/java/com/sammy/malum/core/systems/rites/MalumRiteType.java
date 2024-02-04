package com.sammy.malum.core.systems.rites;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MalumRiteType {

    public final List<MalumSpiritType> spirits;
    public final String identifier;
    public final MalumRiteEffect effect;
    public final MalumRiteEffect corruptedEffect;

    public MalumRiteType(String identifier, MalumSpiritType... spirits) {
        this.identifier = identifier;
        this.spirits = new ArrayList<>(Arrays.asList(spirits));
        this.effect = getNaturalRiteEffect();
        this.corruptedEffect = getCorruptedEffect();
    }

    public List<Component> makeDetailedDescriptor(boolean corrupted) {
        List<Component> tooltip = new ArrayList<>();
        var spiritStyleModifier = getEffectSpirit().getItemRarity().getStyleModifier();
        var riteEffect = getRiteEffect(corrupted);
        var riteCategory = riteEffect.category;
        tooltip.add(Component.translatable(translationIdentifier(corrupted)).withStyle(spiritStyleModifier));
        tooltip.add(makeDescriptorComponent("malum.gui.rite.type", riteCategory.getTranslationKey()));
        if (!riteCategory.equals(MalumRiteEffect.MalumRiteEffectCategory.ONE_TIME_EFFECT)) {
            tooltip.add(makeDescriptorComponent("malum.gui.rite.coverage", riteEffect.getRiteCoverageDescriptor()));
        }
        tooltip.add(makeDescriptorComponent("malum.gui.effect", "malum.gui.book.entry.page.text." + (corrupted ? "corrupt_" : "") + identifier + ".hover"));
        return tooltip;
    }

    public final Component makeDescriptorComponent(String translationKey1, String translationKey2) {
        return Component.translatable(translationKey1).withStyle(ChatFormatting.GOLD)
                .append(Component.translatable(translationKey2).withStyle(ChatFormatting.YELLOW));
    }

    public String translationIdentifier(boolean corrupt) {
        return "malum.gui.rite." + (corrupt ? "corrupted_" : "") + identifier;
    }

    public ResourceLocation getIcon() {
        return MalumMod.malumPath("textures/vfx/rite/" + identifier.replace("greater_", "").replace("_rite", "") + ".png");
    }

    public MalumSpiritType getEffectSpirit() {
        return spirits.get(spirits.size() - 1);
    }

    protected abstract MalumRiteEffect getNaturalRiteEffect();

    protected abstract MalumRiteEffect getCorruptedEffect();

    public final MalumRiteEffect getRiteEffect(boolean corrupted) {
        return corrupted ? corruptedEffect : effect;
    }

    public void executeRite(TotemBaseBlockEntity totemBase) {
        getRiteEffect(totemBase.corrupted).doRiteEffect(totemBase);
    }
}
