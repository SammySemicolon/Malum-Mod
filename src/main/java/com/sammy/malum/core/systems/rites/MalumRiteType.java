package com.sammy.malum.core.systems.rites;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.helpers.DataHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MalumRiteType {
    public final List<MalumSpiritType> spirits;
    public final String identifier;
    public final String basicName;
    public final String corruptName;
    public final MalumRiteEffect effect;
    public final MalumRiteEffect corruptedEffect;

    public MalumRiteType(String identifier, String basicName, String corruptName, MalumSpiritType... spirits) {
        this.identifier = identifier;
        this.basicName = basicName;
        this.corruptName = corruptName;
        this.spirits = new ArrayList<>(Arrays.asList(spirits));
        effect = getNaturalRiteEffect();
        corruptedEffect = getCorruptedEffect();
    }

    public MalumRiteType(String identifier, String basicName, MalumSpiritType... spirits) {
        this(identifier,
            basicName,
            ("Twisted " + basicName)
                .replaceAll("Twisted Greater", "Warped"),
            spirits);
    }

    public MalumRiteType(String identifier, MalumSpiritType... spirits) {
        this(identifier,
            DataHelper.toTitleCase(identifier, "_"),
            spirits);
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

    public boolean isOneAndDone(boolean corrupted) {
        return getRiteEffect(corrupted).isOneAndDone();
    }

    public BlockPos getRiteEffectCenter(TotemBaseBlockEntity totemBase) {
        return getRiteEffect(totemBase.corrupted).getRiteEffectCenter(totemBase);
    }

    public int getRiteRadius(boolean corrupted) {
        return getRiteEffect(corrupted).getRiteEffectRadius();
    }

    public int getRiteTickRate(boolean corrupted) {
        return getRiteEffect(corrupted).getRiteEffectTickRate();
    }

    public abstract MalumRiteEffect getNaturalRiteEffect();

    public abstract MalumRiteEffect getCorruptedEffect();

    public final MalumRiteEffect getRiteEffect(boolean corrupted) {
        return corrupted ? corruptedEffect : effect;
    }

    public void executeRite(TotemBaseBlockEntity totemBase) {
        getRiteEffect(totemBase.corrupted).riteEffect(totemBase);
    }
}
