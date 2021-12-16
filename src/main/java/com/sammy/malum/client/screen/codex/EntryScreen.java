package com.sammy.malum.client.screen.codex;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.codex.objects.EntryObject;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.SoundEvents;
import org.lwjgl.glfw.GLFW;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.*;

public class EntryScreen extends Screen
{
    public static final ResourceLocation BOOK_TEXTURE = MalumHelper.prefix("textures/gui/book/entry.png");

    public static EntryScreen screen;
    public static EntryObject openObject;

    public final int bookWidth = 292;
    public final int bookHeight = 190;

    public int grouping;

    public EntryScreen()
    {
        super(ClientHelper.simpleTranslatableComponent("malum.gui.entry.title"));
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        BookEntry openEntry = openObject.entry;
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        renderTexture(BOOK_TEXTURE, matrixStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        if (!openEntry.pages.isEmpty())
        {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++)
            {
                if (i < openEntry.pages.size())
                {
                    BookPage page = openEntry.pages.get(i);
                    if (i % 2 == 0)
                    {
                        page.renderBackgroundLeft(minecraft, matrixStack, ProgressionBookScreen.screen.xOffset, ProgressionBookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    }
                    else
                    {
                        page.renderBackgroundRight(minecraft, matrixStack, ProgressionBookScreen.screen.xOffset, ProgressionBookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    }
                }
            }
        }
        renderTexture(BOOK_TEXTURE, matrixStack, guiLeft - 13, guiTop + 150, 1, 193, 28, 18, 512, 512);
        if (isHovering(mouseX, mouseY, guiLeft - 13, guiTop + 150, 28, 18))
        {
            renderTexture(BOOK_TEXTURE, matrixStack, guiLeft - 13, guiTop + 150, 1, 232, 28, 18, 512, 512);
        }
        else
        {
            renderTexture(BOOK_TEXTURE, matrixStack, guiLeft - 13, guiTop + 150, 1, 213, 28, 18, 512, 512);
        }
        if (grouping < openEntry.pages.size() / 2f - 1)
        {
            renderTexture(BOOK_TEXTURE, matrixStack, guiLeft + bookWidth - 15, guiTop + 150, 30, 193, 28, 18, 512, 512);
            if (isHovering(mouseX, mouseY, guiLeft + bookWidth - 15, guiTop + 150, 28, 18))
            {
                renderTexture(BOOK_TEXTURE, matrixStack, guiLeft + bookWidth - 15, guiTop + 150, 30, 232, 28, 18, 512, 512);
            }
            else
            {
                renderTexture(BOOK_TEXTURE, matrixStack, guiLeft + bookWidth - 15, guiTop + 150, 30, 213, 28, 18, 512, 512);
            }
        }
        if (!openEntry.pages.isEmpty())
        {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++)
            {
                if (i < openEntry.pages.size())
                {
                    BookPage page = openEntry.pages.get(i);
                    if (i % 2 == 0)
                    {
                        page.renderLeft(minecraft, matrixStack, ProgressionBookScreen.screen.xOffset, ProgressionBookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    }
                    else
                    {
                        page.renderRight(minecraft, matrixStack, ProgressionBookScreen.screen.xOffset, ProgressionBookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    }
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        if (isHovering(mouseX, mouseY, guiLeft - 13, guiTop + 150, 28, 18))
        {
            if (grouping > 0)
            {
                grouping -= 1;
                screen.playSound();
            }
            else
            {
                ProgressionBookScreen.openScreen(true);
            }
            return true;
        }
        if (isHovering(mouseX, mouseY, guiLeft + bookWidth - 15, guiTop + 150, 28, 18))
        {
            if (grouping < openObject.entry.pages.size()/2f-1)
            {
                grouping += 1;
                screen.playSound();
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (keyCode == GLFW.GLFW_KEY_E)
        {
            onClose();
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    @Override
    public void onClose()
    {
        ProgressionBookScreen.openScreen(false);
        openObject.exit();
    }

    public void playSound()
    {
        Player playerEntity = Minecraft.getInstance().player;
        playerEntity.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);
    }
    public static void openScreen(EntryObject newObject)
    {
        Minecraft.getInstance().setScreen(getInstance(newObject));
        screen.playSound();
    }
    public static EntryScreen getInstance(EntryObject newObject)
    {
        if (screen == null || !newObject.equals(openObject))
        {
            screen = new EntryScreen();
            openObject = newObject;
        }
        return screen;
    }
}
