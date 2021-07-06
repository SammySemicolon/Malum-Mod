package com.sammy.malum.common.cooler_book;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.book.objects.BookObject;
import com.sammy.malum.common.cooler_book.objects.CoolerBookObject;
import com.sammy.malum.common.cooler_book.objects.EntryBookObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11C.GL_SCISSOR_TEST;

public class CoolerBookScreen extends Screen
{
    public static final ResourceLocation FRAME_TEXTURE = MalumHelper.prefix("textures/gui/book/frame.png");
    public static final ResourceLocation FADE_TEXTURE = MalumHelper.prefix("textures/gui/book/fade.png");

    public static final ResourceLocation SKY_TEXTURE = MalumHelper.prefix("textures/gui/book/sky.png");

    public int bookWidth = 458;
    public int bookHeight = 250;
    public int bookInsideWidth = 440;
    public int bookInsideHeight = 232;

    public final int texture_width = 512;
    public final int texture_height = 256;
    public final int parallax_width = 512;
    public final int parallax_height = 2560;
    public static CoolerBookScreen screen;
    float xOffset;
    float yOffset;
    float zoom = 1;

    public static ArrayList<CoolerBookObject> objects = new ArrayList<>();

    protected CoolerBookScreen()
    {
        super(ClientHelper.simpleTranslatableComponent("malum.gui.book.title"));
        for (int i = 0; i < 50; i++)
        {
            objects.add(new EntryBookObject(MalumMod.RANDOM.nextInt(500), MalumMod.RANDOM.nextInt(500)));
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;

        renderParallax(SKY_TEXTURE, matrixStack, 0.6f, 0.45f, 32, 32);

        renderEntries(matrixStack, mouseX, mouseY, partialTicks);

        renderTransparentTexture(FADE_TEXTURE, matrixStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        renderTexture(FRAME_TEXTURE, matrixStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta)
    {
        yOffset += delta > 0 ? 0.01f : - 0.01f;
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

    public void renderEntries(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
    {
        Minecraft minecraft = Minecraft.getInstance();
        GL11.glEnable(GL_SCISSOR_TEST);
        scissors();
        for (CoolerBookObject object : objects)
        {
            object.render(minecraft, stack, xOffset * 0.75f, -yOffset * 0.75f, mouseX, mouseY, partialTicks);
        }
        GL11.glDisable(GL_SCISSOR_TEST);
    }

    public void renderParallax(ResourceLocation texture, MatrixStack matrixStack, float xModifier, float yModifier, float extraXOffset, float extraYOffset)
    {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 8;
        int insideTop = guiTop + 8;
        float uOffset = -(xOffset) * xModifier - extraXOffset;
        float vOffset = (yOffset) * yModifier + extraYOffset;

        if (vOffset > parallax_height - texture_height)
        {
            vOffset = parallax_height - texture_height;
        }
        if (vOffset < 0)
        {
            vOffset = 0;
        }
        GL11.glEnable(GL_SCISSOR_TEST);
        renderTexture(texture, matrixStack, insideLeft, insideTop, uOffset, vOffset, bookInsideWidth, bookInsideHeight, parallax_width, parallax_height);
        GL11.glDisable(GL_SCISSOR_TEST);
    }

    public void scissors()
    {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 8;
        int insideTop = guiTop + 8;
        int scale = (int) getMinecraft().getMainWindow().getGuiScaleFactor();
        GL11.glScissor(insideLeft * scale, insideTop * scale, bookInsideWidth * scale, bookInsideHeight * scale);
    }

    public static void renderTexture(ResourceLocation texture, MatrixStack matrixStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight)
    {
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bindTexture(texture);
        blit(matrixStack, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
    }

    public static void renderTransparentTexture(ResourceLocation texture, MatrixStack matrixStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight)
    {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        renderTexture(texture, matrixStack, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
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