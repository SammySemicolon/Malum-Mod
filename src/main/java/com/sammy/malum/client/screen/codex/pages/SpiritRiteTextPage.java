package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

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
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        ProgressionBookScreen.renderText(poseStack, component, guiLeft + 75 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(poseStack, translationKey(), guiLeft + 14, guiTop + 76, 126);
        ProgressionBookScreen.renderRiteIcon(riteType, poseStack, isCorrupted(), guiLeft + 67, guiTop + 44);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        ProgressionBookScreen.renderText(poseStack, component, guiLeft + 218 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        ProgressionBookScreen.renderWrappingText(poseStack, translationKey(), guiLeft + 156, guiTop + 76, 126);
        ProgressionBookScreen.renderRiteIcon(riteType, poseStack, isCorrupted(), guiLeft + 209, guiTop + 44);
    }

    public boolean isCorrupted() {
        return translationKey.contains("corrupt");
    }
}
