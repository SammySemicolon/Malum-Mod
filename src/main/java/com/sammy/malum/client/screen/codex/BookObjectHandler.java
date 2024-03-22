package com.sammy.malum.client.screen.codex;

import com.sammy.malum.client.screen.codex.objects.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;

import java.util.*;

public class BookObjectHandler<T extends AbstractMalumScreen> {

    protected final List<BookObject<T>> bookObjects = new ArrayList<>();
    public final T screen;

    public BookObjectHandler(T screen) {
        this.screen = screen;
    }

    public List<BookObject<T>> getBookObjects() {
        return bookObjects;
    }

    public void click(double mouseX, double mouseY) {
        for (BookObject<T> object : bookObjects) {
            if (object.isValid() && object.isHoveredOver) {
                object.click(mouseX, mouseY);
                break;
            }
        }
    }

    public void renderObjects(GuiGraphics guiGraphics, float left, float top, int mouseX, int mouseY, float partialTicks) {
        for (int i = bookObjects.size() - 1; i >= 0; i--) {
            BookObject<T> object = bookObjects.get(i);
            if (object.isValid()) {
                object.isHoveredOver = object.isHovering(screen, left, top, mouseX, mouseY);
                object.xOffset = left;
                object.yOffset = top;
                object.render(guiGraphics, mouseX, mouseY, partialTicks);
            }
        }
    }

    public void renderObjectsLate(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        for (int i = bookObjects.size() - 1; i >= 0; i--) {
            BookObject<T> object = bookObjects.get(i);
            if (object.isValid()) {
                object.renderLate(guiGraphics, mouseX, mouseY, partialTicks);
            }
        }
    }
}