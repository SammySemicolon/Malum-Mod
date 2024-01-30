package com.sammy.malum.client.screen.codex;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import org.lwjgl.opengl.*;

import java.util.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;
import static org.lwjgl.opengl.GL11C.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractProgressionCodexScreen extends AbstractMalumScreen {

    public static final ResourceLocation FRAME_TEXTURE = malumPath("textures/gui/book/frame.png");
    public static final ResourceLocation FRAME_FADE_TEXTURE = malumPath("textures/gui/book/frame_fade.png");

    public float xOffset;
    public float yOffset;
    public float cachedXOffset;
    public float cachedYOffset;
    public boolean ignoreNextMouseInput;
    public int transitionTimer;

    public final List<BookObject<?>> bookObjects = new ArrayList<>();

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

    public abstract void renderBackground(PoseStack poseStack);

    public abstract Collection<BookEntry> getEntries();

    @Override
    public void onClose() {
        super.onClose();
        playSweetenedSound(SoundRegistry.ARCANA_CODEX_CLOSE, 0.75f);
    }

    @Override
    public void tick() {
        if (transitionTimer > 0) {
            transitionTimer--;
        }

        super.tick();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        PoseStack poseStack = guiGraphics.pose();

        renderBackground(poseStack);
        GL11.glEnable(GL_SCISSOR_TEST);
        cut();

        renderEntries(guiGraphics, mouseX, mouseY, partialTicks);
        GL11.glDisable(GL_SCISSOR_TEST);

        renderTexture(FRAME_FADE_TEXTURE, poseStack, guiLeft, guiTop, 0, 0, bookWidth, bookHeight);
        if (transitionTimer > 0) {
            ArcanaCodexHelper.renderTransitionFade(this, poseStack);
        }
        renderTexture(FRAME_TEXTURE, poseStack, guiLeft, guiTop, 400, 0, 0, bookWidth, bookHeight);
        lateEntryRender(guiGraphics, mouseX, mouseY, partialTicks);
    }

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
        for (BookObject<?> object : bookObjects) {
            if (object.isValid() && object.isHovering(this, xOffset, yOffset, mouseX, mouseY)) {
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

    public void openScreen(boolean ignoreNextMouseClick) {
        Minecraft.getInstance().setScreen(this);
        this.ignoreNextMouseInput = ignoreNextMouseClick;
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

    public void faceObject(BookObject<?> object) {
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        xOffset = -object.posX + getGuiLeft() + bookInsideWidth;
        yOffset = -object.posY + getGuiTop() + bookInsideHeight;
    }

    public void renderBackground(PoseStack poseStack, ResourceLocation texture, float xModifier, float yModifier) {
        int insideLeft = getInsideLeft();
        int insideTop = getInsideTop();
        float uOffset = (bookInsideWidth / 4f - xOffset * xModifier);
        float vOffset = (backgroundImageHeight - bookInsideHeight - yOffset * yModifier);
        if (uOffset <= 0) {
            uOffset = 0;
        }
        if (uOffset > bookInsideWidth / 2f) {
            uOffset = bookInsideWidth / 2f;
        }
        if (vOffset <= backgroundImageHeight / 2f) {
            vOffset = backgroundImageHeight / 2f;
        }
        if (vOffset > backgroundImageHeight - bookInsideHeight) {
            vOffset = backgroundImageHeight - bookInsideHeight;
        }
        renderTexture(texture, poseStack, insideLeft, insideTop, uOffset, vOffset, bookInsideWidth, bookInsideHeight, backgroundImageWidth / 2, backgroundImageHeight / 2);
    }

    public void renderEntries(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        for (int i = bookObjects.size() - 1; i >= 0; i--) {
            BookObject<?> object = bookObjects.get(i);
            if (object.isValid()) {
                boolean isHovering = object.isHovering(this, xOffset, yOffset, mouseX, mouseY);
                object.isHovering = isHovering;
                object.hover = isHovering ? Math.min(object.hover + 1, object.hoverCap()) : Math.max(object.hover - 1, 0);
                object.render(minecraft, guiGraphics, xOffset, yOffset, mouseX, mouseY, partialTicks);
            }
        }
    }

    public void lateEntryRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        for (int i = bookObjects.size() - 1; i >= 0; i--) {
            BookObject<?> object = bookObjects.get(i);
            if (object.isValid()) {
                object.lateRender(minecraft, guiGraphics, xOffset, yOffset, mouseX, mouseY, partialTicks);
            }
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