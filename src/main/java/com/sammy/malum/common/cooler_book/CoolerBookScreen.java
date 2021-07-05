package com.sammy.malum.common.cooler_book;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.categories.BookCategory;
import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.entries.BookEntryGrouping;
import com.sammy.malum.common.book.objects.*;
import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.core.modcontent.MalumBookCategories;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11C.GL_SCISSOR_TEST;

public class CoolerBookScreen extends Screen
{
    public static final ResourceLocation BOOK_TEXTURE = MalumHelper.prefix("textures/gui/book/encyclopedia_arcana.png");
    public static final ResourceLocation BACKGROUND_TEXTURE = MalumHelper.prefix("textures/gui/book/encyclopedia_arcana_background.png");

    public final int bookWidth = 450;
    public final int bookHeight = 242;
    public final int bookInsideWidth = 424;
    public final int bookInsideHeight = 217;
    public final int bookInsideTextureSize = 256;
    public static CoolerBookScreen screen;
    float xOffset;
    float yOffset;
    float zoom;
    protected CoolerBookScreen()
    {
        super(ClientHelper.simpleTranslatableComponent("malum.gui.book.title"));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        Minecraft mc = Minecraft.getInstance();

        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 13;
        int insideTop = guiTop + 12;
        int scale = (int)getMinecraft().getMainWindow().getGuiScaleFactor();
        float uOffset = -(xOffset + 512) / 2;
        float vOffset = (yOffset + 512) / 2;

        GL11.glScissor(insideLeft * scale, insideTop * scale, bookInsideWidth * scale, bookInsideHeight * scale);
        GL11.glEnable(GL_SCISSOR_TEST);

        mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        blit(matrixStack, guiLeft, guiTop, uOffset, vOffset, bookInsideTextureSize, bookInsideTextureSize, bookInsideTextureSize, bookInsideTextureSize);

        GL11.glDisable(GL_SCISSOR_TEST);

        mc.getTextureManager().bindTexture(BOOK_TEXTURE);
        blit(matrixStack, guiLeft, guiTop, 0, 0, bookWidth, bookHeight, 512, 512);

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta)
    {
        zoom *= delta > 0 ? 1.25f : 0.75f;
        if (zoom > 1f)
        {
            zoom = 1f;
        }
        if (zoom < 0.5f)
        {
            zoom = 0.5f;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {
        xOffset += dragX;
        yOffset -= dragY;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    public void playSound()
    {
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        playerEntity.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }

    public static void openScreen()
    {
        Minecraft.getInstance().displayGuiScreen(getInstance());
        screen.playSound();
    }

    public static CoolerBookScreen getInstance()
    {
        if (screen == null)
        {
            screen = new CoolerBookScreen();
        }
        return screen;
    }
}