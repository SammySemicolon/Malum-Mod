package com.sammy.malum.client.screen.codex;

import com.sammy.malum.client.screen.codex.objects.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.sammy.malum.MalumMod.malumPath;

public abstract class AbstractProgressionCodexScreen extends AbstractMalumScreen {

    public static final ResourceLocation FRAME_TEXTURE = malumPath("textures/gui/book/frame.png");
    public static final ResourceLocation FRAME_FADE_TEXTURE = malumPath("textures/gui/book/frame_fade.png");

    public float xOffset;
    public float yOffset;
    public float cachedXOffset;
    public float cachedYOffset;
    public boolean ignoreNextMouseInput;

    public final List<BookObject> bookObjects = new ArrayList<>();

    public final int bookWidth;
    public final int bookHeight;
    public final int bookInsideWidth;
    public final int bookInsideHeight;

    public final int backgroundImageWidth;
    public final int backgroundImageHeight;

    protected AbstractProgressionCodexScreen(int backgroundImageWidth, int backgroundImageHeight) {
        this(378, 250, 344, 218, backgroundImageWidth, backgroundImageHeight);
    }

    protected AbstractProgressionCodexScreen(int bookWidth, int bookHeight, int bookInsideWidth, int bookInsideHeight, int backgroundImageWidth, int backgroundImageHeight) {
        super(Component.empty());
        this.bookWidth = bookWidth;
        this.bookHeight = bookHeight;
        this.bookInsideWidth = bookInsideWidth;
        this.bookInsideHeight = bookInsideHeight;
        this.backgroundImageWidth = backgroundImageWidth;
        this.backgroundImageHeight = backgroundImageHeight;
        minecraft = Minecraft.getInstance();
    }

    public void setupObjects() {
        bookObjects.clear();
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        int guiLeft = getGuiLeft();
        int guiTop = getGuiTop();
        int coreX = guiLeft + bookInsideWidth;
        int coreY = guiTop + bookInsideHeight;
        int width = 40;
        int height = 48;
        for (BookEntry entry : getEntries()) {
            final EntryObject bookObject = entry.widgetSupplier.getBookObject(this, entry, coreX + entry.xOffset * width, coreY - entry.yOffset * height);
            if (entry.widgetConfig != null) {
                entry.widgetConfig.accept(bookObject);
            }
            bookObjects.add(bookObject);
        }
        faceObject(bookObjects.get(1));
    }

    public abstract void openScreen(boolean ignoreNextMouseClick);

    public abstract Collection<BookEntry> getEntries();

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        xOffset += dragX;
        yOffset += dragY;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        cachedXOffset = xOffset;
        cachedYOffset = yOffset;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (ignoreNextMouseInput) {
            ignoreNextMouseInput = false;
            return super.mouseReleased(mouseX, mouseY, button);
        }
        if (xOffset != cachedXOffset || yOffset != cachedYOffset) {
            return super.mouseReleased(mouseX, mouseY, button);
        }
        for (BookObject object : bookObjects) {
            if (object.isHovering(this, xOffset, yOffset, mouseX, mouseY)) {
                object.click(xOffset, yOffset, mouseX, mouseY);
                break;
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean isHovering(double mouseX, double mouseY, int posX, int posY, int width, int height) {
        if (!isInView(mouseX, mouseY)) {
            return false;
        }
        return ArcanaCodexHelper.isHovering(mouseX, mouseY, posX, posY, width, height);
    }

    public void faceObject(BookObject object) {
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        xOffset = -object.posX + getGuiLeft() + bookInsideWidth;
        yOffset = -object.posY + getGuiTop() + bookInsideHeight;
    }

    public void renderEntries(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        for (int i = bookObjects.size() - 1; i >= 0; i--) {
            BookObject object = bookObjects.get(i);
            boolean isHovering = object.isHovering(this, xOffset, yOffset, mouseX, mouseY);
            object.isHovering = isHovering;
            object.hover = isHovering ? Math.min(object.hover + 1, object.hoverCap()) : Math.max(object.hover - 1, 0);
            object.render(minecraft, guiGraphics, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public void lateEntryRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        for (int i = bookObjects.size() - 1; i >= 0; i--) {
            BookObject object = bookObjects.get(i);
            object.lateRender(minecraft, guiGraphics, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public boolean isInView(double mouseX, double mouseY) {
        return mouseX >= getInsideLeft()
                && mouseY >= getInsideTop()
                && mouseX <= (getInsideLeft() + bookInsideWidth)
                && mouseY <= (getInsideTop() + bookInsideHeight);
    }

    public void cut() {
        int scale = (int) getMinecraft().getWindow().getGuiScale();
        GL11.glScissor(
                getInsideLeft() * scale,
                getMinecraft().getWindow().getHeight() - (getInsideTop() + bookInsideHeight) * scale,
                bookInsideWidth * scale,
                bookInsideHeight * scale);
    }

    public int getInsideLeft() {
        return getGuiLeft() + 17;
    }

    public int getInsideTop() {
        return getGuiTop() + 14;
    }

    public int getGuiLeft() {
        return (width - bookWidth) / 2;
    }

    public int getGuiTop() {
        return (height - bookHeight) / 2;
    }
}