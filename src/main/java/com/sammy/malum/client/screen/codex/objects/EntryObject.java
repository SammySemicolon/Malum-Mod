package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderTexture;

public class EntryObject<T extends AbstractProgressionCodexScreen> extends BookObject<T> {

    public final BookEntry<T> entry;
    public BookWidgetStyle style = BookWidgetStyle.RUNEWOOD;
    public Predicate<T> isValid = t -> true;
    public ItemStack iconStack;

    public EntryObject(T screen, BookEntry<T> entry, int posX, int posY) {
        super(screen, posX, posY, 32, 32);
        this.entry = entry;
    }

    @Override
    public boolean isValid() {
        return isValid.test(screen);
    }

    @Override
    public void click(float xOffset, float yOffset, double mouseX, double mouseY) {
        EntryScreen.openScreen(this);
    }

    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset) - (style.textureWidth()-32)/2;
        int posY = offsetPosY(yOffset) - (style.textureHeight()-32)/2;
        final PoseStack poseStack = guiGraphics.pose();
        renderTexture(WIDGET_FADE_TEXTURE, poseStack, posX - 13, posY - 13, 0, 0, 58, 58);
        renderTexture(style.frameTexture(), poseStack, posX, posY, 0, 0, style.textureWidth(), style.textureHeight());
        renderTexture(style.fillingTexture(), poseStack, posX, posY, 0, 0, style.textureWidth(), style.textureHeight());
        if (iconStack != null) {
            guiGraphics.renderItem(iconStack, posX + 8, posY + 8);
        }
    }

    @Override
    public void lateRender(Minecraft minecraft, GuiGraphics guiGraphics, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (isHovering) {
            final List<Component> list = Arrays.asList(
                    Component.translatable(entry.translationKey()),
                    Component.translatable(entry.descriptionTranslationKey()).withStyle(ChatFormatting.GRAY));
            guiGraphics.renderComponentTooltip(minecraft.font, list, mouseX, mouseY);
        }
    }

    public EntryObject<T> setIcon(Supplier<? extends Item> item) {
        return setIcon(item.get());
    }

    public EntryObject<T> setIcon(Item item) {
        iconStack = item.getDefaultInstance();
        return this;
    }

    public EntryObject<T> setStyle(BookWidgetStyle style) {
        this.style = style;
        return this;
    }

    public EntryObject<T> setValidityChecker(Predicate<T> isValid) {
        this.isValid = isValid;
        return this;
    }
}