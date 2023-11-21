package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.network.chat.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritRiteTextPage extends BookPage {
    public final MalumRiteType riteType;
    private final String translationKey;

    public SpiritRiteTextPage(MalumRiteType riteType, String translationKey) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_rite_page.png"));
        this.riteType = riteType;
        this.translationKey = translationKey;
    }

    public String headlineTranslationKey() {
        return riteType.translationIdentifier(isCorrupted());
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(headlineTranslationKey());
        renderText(poseStack, component, guiLeft + 75 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        renderWrappingText(poseStack, translationKey(), guiLeft + 16, guiTop + 84, 126);
        final int riteIconX = guiLeft + 67;
        final int riteIconY = guiTop + 44;
        renderRiteIcon(riteType, poseStack, isCorrupted(), 0.4f, riteIconX, riteIconY);
        if (screen.isHovering(mouseX, mouseY, riteIconX, riteIconY, 16, 16)) {
            screen.renderLate(()->screen.renderComponentTooltip(poseStack, riteType.makeDetailedDescriptor(isCorrupted()), mouseX, mouseY));
        }
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(headlineTranslationKey());
        renderText(poseStack, component, guiLeft + 218 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        renderWrappingText(poseStack, translationKey(), guiLeft + 156, guiTop + 76, 126);
        final int riteIconX = guiLeft + 209;
        final int riteIconY = guiTop + 44;
        renderRiteIcon(riteType, poseStack, isCorrupted(), 0.4f, riteIconX, riteIconY);
        if (screen.isHovering(mouseX, mouseY, riteIconX, riteIconY, 16, 16)) {
            screen.renderLate(()->screen.renderComponentTooltip(poseStack, riteType.makeDetailedDescriptor(isCorrupted()), mouseX, mouseY));
        }
    }

    public boolean isCorrupted() {
        return translationKey.contains("corrupt");
    }
}
