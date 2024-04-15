package com.sammy.malum.client.screen.codex.objects.progression;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderTexture;

public class ProgressionEntryObject<T extends AbstractProgressionCodexScreen<T>> extends BookObject<T> {

    public final BookEntry<T> entry;
    public BookWidgetStyle style = BookWidgetStyle.RUNEWOOD;
    public Predicate<T> isValid = t -> true;
    public ItemStack iconStack;

    public ProgressionEntryObject(BookEntry<T> entry, int posX, int posY) {
        super(posX, posY, 32, 32);
        this.entry = entry;
    }

    @Override
    public boolean isValid(T screen) {
        return isValid.test(screen);
    }

    @Override
    public void click(T screen, double mouseX, double mouseY) {
        EntryScreen.openScreen(screen, this);
    }

    @Override
    public void render(T screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        final PoseStack poseStack = guiGraphics.pose();
        int posX = getOffsetXPosition() - (style.textureWidth() - 32) / 2;
        int posY = getOffsetYPosition() - (style.textureHeight() - 32) / 2;
        renderTexture(WIDGET_FADE_TEXTURE, poseStack, posX - 13, posY - 13, 0, 0, 58, 58);
        renderTexture(style.frameTexture(), poseStack, posX, posY, 0, 0, style.textureWidth(), style.textureHeight());
        renderTexture(style.fillingTexture(), poseStack, posX, posY, 0, 0, style.textureWidth(), style.textureHeight());
        if (iconStack != null) {
            guiGraphics.renderItem(iconStack, posX + 8, posY + 8);
        }
    }

    @Override
    public void renderLate(T screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (isHoveredOver) {
            final List<Component> list = Arrays.asList(
                    Component.translatable(entry.translationKey()),
                    Component.translatable(entry.descriptionTranslationKey()).withStyle(ChatFormatting.GRAY));
            guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, list, mouseX, mouseY);
        }
    }

    public ProgressionEntryObject<T> setIcon(Supplier<? extends Item> item) {
        return setIcon(item.get());
    }

    public ProgressionEntryObject<T> setIcon(Item item) {
        iconStack = item.getDefaultInstance();
        return this;
    }

    public ProgressionEntryObject<T> setStyle(BookWidgetStyle style) {
        this.style = style;
        return this;
    }

    public ProgressionEntryObject<T> setValidityChecker(Predicate<T> isValid) {
        this.isValid = isValid;
        return this;
    }
}