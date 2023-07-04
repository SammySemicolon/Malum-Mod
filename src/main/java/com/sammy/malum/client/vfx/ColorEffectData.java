package com.sammy.malum.client.vfx;

import com.sammy.malum.core.helper.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.network.*;

import javax.annotation.*;
import java.awt.*;

public class ColorEffectData {

    private final Color primaryColor;
    private final Color secondaryColor;
    @Nullable
    private final MalumSpiritType spiritType;

    public ColorEffectData(FriendlyByteBuf buf) {
        this(new Color(buf.readInt()), new Color(buf.readInt()), buf.readBoolean() ? SpiritHelper.getSpiritType(buf.readUtf()) : null);
    }

    public ColorEffectData(Color primaryColor, Color secondaryColor, @Nullable MalumSpiritType spiritType) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.spiritType = spiritType;
    }

    public ColorEffectData(MalumSpiritType spiritType) {
        this(spiritType.getPrimaryColor(), spiritType.getSecondaryColor(), spiritType);
    }

    public ColorEffectData(Color primaryColor, Color secondaryColor) {
        this(primaryColor, secondaryColor, null);
    }

    public ColorEffectData(Color primaryColor) {
        this(primaryColor, primaryColor);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(primaryColor.getRGB());
        buf.writeInt(secondaryColor.getRGB());
        boolean nonNullSpirit = spiritType != null;
        buf.writeBoolean(nonNullSpirit);
        if (nonNullSpirit) {
            buf.writeUtf(spiritType.identifier);
        }
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    @Nullable
    public MalumSpiritType getSpiritType() {
        return spiritType;
    }
}