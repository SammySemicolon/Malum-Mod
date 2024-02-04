package com.sammy.malum.client.screen.codex;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.config.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class EntryScreen<T extends AbstractProgressionCodexScreen> extends AbstractMalumScreen {

    public static EntryScreen<?> entryScreen;

    public static final ResourceLocation BOOK_TEXTURE = MalumMod.malumPath("textures/gui/book/entry.png");

    public final int bookWidth = 292;
    public final int bookHeight = 190;
    public final EntryObject<T> openObject;

    public List<Runnable> lateRendering = new ArrayList<>();
    public int grouping;

    public EntryScreen(EntryObject<T> openObject) {
        super(Component.empty());
        this.openObject = openObject;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        BookEntry<T> openEntry = openObject.entry;
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        PoseStack poseStack = guiGraphics.pose();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        renderTexture(BOOK_TEXTURE, poseStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        if (!openEntry.pages.isEmpty()) {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage page = openEntry.pages.get(i);
                    if (i % 2 == 0) {
                        page.renderBackgroundLeft(poseStack);
                    } else {
                        page.renderBackgroundRight(poseStack);
                    }
                }
            }
        }
        renderTexture(BOOK_TEXTURE, poseStack, guiLeft - 13, guiTop + 150, 1, 193, 28, 18, 512, 512);
        if (isHovering(mouseX, mouseY, guiLeft - 13, guiTop + 150, 28, 18)) {
            renderTexture(BOOK_TEXTURE, poseStack, guiLeft - 13, guiTop + 150, 1, 232, 28, 18, 512, 512);
        } else {
            renderTexture(BOOK_TEXTURE, poseStack, guiLeft - 13, guiTop + 150, 1, 213, 28, 18, 512, 512);
        }
        if (grouping < openEntry.pages.size() / 2f - 1) {
            renderTexture(BOOK_TEXTURE, poseStack, guiLeft + bookWidth - 15, guiTop + 150, 30, 193, 28, 18, 512, 512);
            if (isHovering(mouseX, mouseY, guiLeft + bookWidth - 15, guiTop + 150, 28, 18)) {
                renderTexture(BOOK_TEXTURE, poseStack, guiLeft + bookWidth - 15, guiTop + 150, 30, 232, 28, 18, 512, 512);
            } else {
                renderTexture(BOOK_TEXTURE, poseStack, guiLeft + bookWidth - 15, guiTop + 150, 30, 213, 28, 18, 512, 512);
            }
        }
        if (!openEntry.pages.isEmpty()) {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage page = openEntry.pages.get(i);
                    boolean isRepeat = i % 2 != 0 && page.getClass().equals(openEntry.pages.get(i - 1).getClass());
                    page.render(minecraft, guiGraphics, this, mouseX, mouseY, partialTicks, isRepeat);
                }
            }
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage page = openEntry.pages.get(i);
                    if (i % 2 == 0) {
                        page.renderLeft(minecraft, guiGraphics, this, mouseX, mouseY, partialTicks);
                    } else {
                        page.renderRight(minecraft, guiGraphics, this, mouseX, mouseY, partialTicks);
                    }
                }
            }
            lateRendering.forEach(Runnable::run);
            lateRendering.clear();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        if (isHovering(mouseX, mouseY, guiLeft - 13, guiTop + 150, 28, 18)) {
            previousPage(true);
            return true;
        }
        if (isHovering(mouseX, mouseY, guiLeft + bookWidth - 15, guiTop + 150, 28, 18)) {
            nextPage();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (ClientConfig.SCROLL_DIRECTION.getConfigValue()) {
            scroll = -scroll;
        }
        if (scroll > 0) {
            nextPage();
        } else {
            previousPage(false);
        }
        return super.mouseScrolled(mouseX, mouseY, scroll);
    }

    @Override
    public void onClose() {
        close(false);
    }

    @Override
    public boolean isHovering(double mouseX, double mouseY, int posX, int posY, int width, int height) {
        return ArcanaCodexHelper.isHovering(mouseX, mouseY, posX, posY, width, height);
    }

    @Override
    public Supplier<SoundEvent> getSweetenerSound() {
        return openObject.screen.getSweetenerSound();
    }

    public void nextPage() {
        if (grouping < openObject.entry.pages.size() / 2f - 1) {
            grouping += 1;
            playPageFlipSound(SoundRegistry.ARCANA_PAGE_FLIP, getSweetenerPitch());
        }
    }

    public void previousPage(boolean ignore) {
        if (grouping > 0) {
            grouping -= 1;
            playPageFlipSound(SoundRegistry.ARCANA_PAGE_FLIP, getSweetenerPitch());
        } else {
            close(ignore);
        }
    }

    public void close(boolean ignoreNextInput) {
        openObject.screen.openScreen(ignoreNextInput);
        playSweetenedSound(SoundRegistry.ARCANA_ENTRY_CLOSE, 0.85f);
        openObject.exit();
    }

    public static void openScreen(EntryObject entryObject) {
        entryScreen = new EntryScreen(entryObject);
        entryScreen.playSweetenedSound(SoundRegistry.ARCANA_ENTRY_OPEN, 1.15f);
        Minecraft.getInstance().setScreen(entryScreen);
    }

    public float getSweetenerPitch() {
        return 1 + (float) grouping / openObject.entry.pages.size();
    }

    public void renderLate(Runnable runnable) {
        lateRendering.add(runnable);
    }
}