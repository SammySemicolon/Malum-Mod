package com.sammy.malum.client.screen.book;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.cooler_book.CoolerBookEntry;
import com.sammy.malum.client.screen.cooler_book.CoolerBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import org.lwjgl.glfw.GLFW;

import static com.sammy.malum.client.screen.cooler_book.CoolerBookScreen.renderTexture;

public class EntryScreen extends Screen
{
    public static final ResourceLocation BOOK_TEXTURE = MalumHelper.prefix("textures/gui/book/entry.png");

    public static EntryScreen screen;
    public static CoolerBookEntry openEntry;

    public final int bookWidth = 292;
    public final int bookHeight = 190;

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
        renderTexture(BOOK_TEXTURE,matrixStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
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
            closeScreen();
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    @Override
    public void closeScreen()
    {
        CoolerBookScreen.openScreen();
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
