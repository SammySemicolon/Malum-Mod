package com.sammy.malum.common.cooler_book;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11C.GL_SCISSOR_TEST;

public class CoolerBookScreen extends Screen
{
    public static final ResourceLocation FRAME_TEXTURE = MalumHelper.prefix("textures/gui/book/frame.png");
    public static final ResourceLocation FADE_TEXTURE = MalumHelper.prefix("textures/gui/book/fade.png");

    public static final ResourceLocation SKY_BELOW_TEXTURE = MalumHelper.prefix("textures/gui/book/sky_below.png");
    public static final ResourceLocation SKY_TEXTURE = MalumHelper.prefix("textures/gui/book/sky.png");
    public static final ResourceLocation SKY_ABOVE_TEXTURE = MalumHelper.prefix("textures/gui/book/sky_above.png");

    public static final ResourceLocation CLOSE_CLOUDS_BELOW_TEXTURE = MalumHelper.prefix("textures/gui/book/close_clouds_below.png");
    public static final ResourceLocation CLOSE_CLOUDS_TEXTURE = MalumHelper.prefix("textures/gui/book/close_clouds.png");

    public static final ResourceLocation FARAWAY_CLOUDS_BELOW_TEXTURE = MalumHelper.prefix("textures/gui/book/faraway_clouds_below.png");
    public static final ResourceLocation FARAWAY_CLOUDS_TEXTURE = MalumHelper.prefix("textures/gui/book/faraway_clouds.png");

    public final int bookWidth = 450;
    public final int bookHeight = 242;
    public final int bookInsideWidth = 424;
    public final int bookInsideHeight = 217;

    public final int render_width = 256;
    public final int render_height = 128;
    public final int texture_width = 512;
    public final int texture_height = 256;
    public static CoolerBookScreen screen;
    float xOffset;
    float yOffset;
    float zoom = 1;
    protected CoolerBookScreen()
    {
        super(ClientHelper.simpleTranslatableComponent("malum.gui.book.title"));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        renderParallax(SKY_BELOW_TEXTURE, SKY_TEXTURE, SKY_ABOVE_TEXTURE, matrixStack,0.5f,0.25f, 0, 128);
        renderParallax(FARAWAY_CLOUDS_BELOW_TEXTURE, FARAWAY_CLOUDS_TEXTURE, null, matrixStack,0.6f,0.35f, 32, 32);
        renderParallax(CLOSE_CLOUDS_BELOW_TEXTURE, CLOSE_CLOUDS_TEXTURE, null, matrixStack,0.75f,0.6f, 0, 0);

        renderTransparentTexture(FADE_TEXTURE, matrixStack, guiLeft, guiTop, 0, 0, bookWidth, bookHeight, 512, 512);
        renderTexture(FRAME_TEXTURE, matrixStack, guiLeft, guiTop, 0, 0, bookWidth, bookHeight, 512, 512);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta)
    {
        zoom *= delta > 0 ? 1.25f : 0.75f;
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

    public void renderParallax(ResourceLocation belowTexture, ResourceLocation texture, ResourceLocation aboveTexture, MatrixStack matrixStack, float xModifier, float yModifier, float extraXOffset, float extraYOffset)
    {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 13;
        int insideTop = guiTop + 12;
        int scale = (int) getMinecraft().getMainWindow().getGuiScaleFactor();
        float uOffset = -(xOffset) * xModifier - extraXOffset;
        float vOffset = (yOffset) * yModifier + extraYOffset;

        //for some reason, this sucks. note to future self: don't bother fixing it, it's not a big deal.
        float percentage = -vOffset / bookInsideHeight;
        float cutoff = Math.max(0, 1 - percentage);
        GL11.glEnable(GL_SCISSOR_TEST);
        GL11.glScissor(insideLeft * scale, insideTop * scale, bookInsideWidth * scale, MathHelper.ceil(bookInsideHeight * scale * cutoff));
        renderTexture(texture, matrixStack, insideLeft, insideTop, uOffset, vOffset, texture_width, texture_height, render_width, render_height * 2);
        //below is just fine
        GL11.glScissor(insideLeft * scale, insideTop * scale, bookInsideWidth * scale, bookInsideHeight * scale);
        if (belowTexture != null)
        {
            float downwardsProgress = (vOffset) / texture_height;
            if (MathHelper.ceil(downwardsProgress) >= 1)
            {
                float cappedProgress = Math.min(1, downwardsProgress);
                int up = (int) (texture_height - texture_height * cappedProgress);
                renderTexture(belowTexture, matrixStack, insideLeft, insideTop + up, uOffset, vOffset + up, texture_width, texture_height, render_width, render_height);
            }
        }
        if (aboveTexture != null)
        {
            float upwardsProgress = (-vOffset) / texture_height;
            if (MathHelper.ceil(upwardsProgress) >= 1)
            {
                float cappedProgress = Math.min(1, upwardsProgress);
                int up = (int) (texture_height - texture_height * cappedProgress) - scale;
                renderTexture(aboveTexture, matrixStack, insideLeft, insideTop - up, uOffset, vOffset - up, texture_width, texture_height, render_width, render_height);
            }
        }

        GL11.glDisable(GL_SCISSOR_TEST);
    }
    public void renderTexture(ResourceLocation texture, MatrixStack matrixStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight)
    {
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bindTexture(texture);
        blit(matrixStack, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
    }
    public void renderZoomAffectedTexture(ResourceLocation texture, MatrixStack matrixStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight)
    {
        renderTexture(texture, matrixStack, x, y, uOffset, vOffset, width, height, (int)(textureWidth*zoom), (int)(textureHeight*zoom));
    }
    public void renderTransparentTexture(ResourceLocation texture, MatrixStack matrixStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight)
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
        screen.xOffset = screen.texture_width * 0.6f;
        screen.yOffset = -screen.texture_height * 1.1f;
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