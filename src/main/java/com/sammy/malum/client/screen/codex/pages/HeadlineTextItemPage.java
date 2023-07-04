package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import net.minecraft.client.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class HeadlineTextItemPage extends BookPage {
    private final String headlineTranslationKey;
    private final String translationKey;
    private final ItemStack spiritStack;

    public HeadlineTextItemPage(String headlineTranslationKey, String translationKey, ItemStack spiritStack) {
        super(MalumMod.malumPath("textures/gui/book/pages/headline_item_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.translationKey = translationKey;
        this.spiritStack = spiritStack;
    }

    public HeadlineTextItemPage(String headlineTranslationKey, String translationKey, Item spirit) {
        this(headlineTranslationKey, translationKey, spirit.getDefaultInstance());
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, float yOffset, int mouseX, int mouseY, float partialTicks, float xOffset) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(headlineTranslationKey());
        renderText(poseStack, component, guiLeft + 75 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        renderWrappingText(poseStack, translationKey(), guiLeft + 14, guiTop + 76, 126);
        renderItem(screen, poseStack, spiritStack, guiLeft + 67, guiTop + 44, mouseX, mouseY);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, float yOffset, int mouseX, int mouseY, float partialTicks, float xOffset) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(headlineTranslationKey());
        renderText(poseStack, component, guiLeft + 218 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        renderWrappingText(poseStack, translationKey(), guiLeft + 156, guiTop + 76, 126);
        renderItem(screen, poseStack, spiritStack, guiLeft + 209, guiTop + 44, mouseX, mouseY);
    }
}