package com.sammy.malum.client.screen.cooler_book;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.MalumHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import org.lwjgl.glfw.GLFW;

import static com.sammy.malum.client.screen.cooler_book.CoolerBookScreen.*;

public class EntryScreen extends Screen
{
    public static final ResourceLocation BOOK_TEXTURE = MalumHelper.prefix("textures/gui/book/entry.png");

    public static EntryScreen screen;
    public static CoolerBookEntry openEntry;

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
                    CoolerBookPage page = openEntry.pages.get(i);
                    if (i % 2 == 0)
                    {
                        page.renderBackgroundLeft(minecraft, matrixStack, CoolerBookScreen.screen.xOffset, CoolerBookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    }
                    else
                    {
                        page.renderBackgroundRight(minecraft, matrixStack, CoolerBookScreen.screen.xOffset, CoolerBookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
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
                    CoolerBookPage page = openEntry.pages.get(i);
                    if (i % 2 == 0)
                    {
                        page.renderLeft(minecraft, matrixStack, CoolerBookScreen.screen.xOffset, CoolerBookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    }
                    else
                    {
                        page.renderRight(minecraft, matrixStack, CoolerBookScreen.screen.xOffset, CoolerBookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
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
                CoolerBookScreen.openScreen(false);
            }
            return true;
        }
        if (isHovering(mouseX, mouseY, guiLeft + bookWidth - 15, guiTop + 150, 28, 18))
        {
            if (grouping < openEntry.pages.size()/2f-1)
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
            CoolerBookScreen.openScreen(false);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    @Override
    public void closeScreen()
    {
        CoolerBookScreen.openScreen(true);
    }

    public void playSound()
    {
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        playerEntity.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
    public static void openScreen(CoolerBookEntry newEntry)
    {
        Minecraft.getInstance().displayGuiScreen(getInstance(newEntry));
        screen.playSound();
    }
    public static EntryScreen getInstance(CoolerBookEntry newEntry)
    {
        if (screen == null || !newEntry.equals(openEntry))
        {
            screen = new EntryScreen();
            openEntry = newEntry;
        }
        return screen;
    }
}
