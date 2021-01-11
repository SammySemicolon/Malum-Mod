package com.sammy.malum.common.book;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.categories.BookCategory;
import com.sammy.malum.common.book.pages.BookPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BookScreen extends Screen
{
    public static final ResourceLocation BOOK_TEXTURE = MalumHelper.prefix("textures/gui/book.png");
    protected BookScreen()
    {
        super(ClientHelper.simpleTranslatableComponent("malum.gui.book.title"));
    }
    static BookScreen screen;
    public static BookScreen getInstance()
    {
        if (screen == null)
        {
            screen = new BookScreen();
            screen.currentChapter = MalumBookCategories.categories.get(0);
        }
        return screen;
    }
    public static void openScreen()
    {
        Minecraft.getInstance().displayGuiScreen(getInstance());
    }
    public boolean isHovering(int mouseX, int mouseY, int posX, int posY, int width, int height)
    {
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }
    public final int bookWidth = 270;
    public final int bookHeight = 164;
    public BookCategory currentChapter;
    public BookPage currentPage;
    public void drawCenterText(MatrixStack matrixStack, int guiLeft, int guiTop, ITextComponent component)
    {
        Minecraft mc = Minecraft.getInstance();
        int posX = guiLeft + 96;
        int posY = guiTop + 127;
        mc.fontRenderer.func_243246_a(matrixStack, component, posX, posY, 0);
    }
    public void drawBackArrow(MatrixStack matrixStack, int guiLeft, int guiTop)
    {
        Minecraft mc = Minecraft.getInstance();
        int posX = guiLeft + 6;
        int posY = guiTop + 143;
        mc.getTextureManager().bindTexture(BOOK_TEXTURE);
        blit(matrixStack, posX, posY, 1, 168, 39, 16, 512, 512);
    }
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        renderBackground(matrixStack);
        Minecraft mc = Minecraft.getInstance();
        
        mc.getTextureManager().bindTexture(BOOK_TEXTURE);
    
        this.width = mc.getMainWindow().getScaledWidth();
        this.height = mc.getMainWindow().getScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        blit(matrixStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        
        boolean backArrow = currentPage != null;
        int offset = 0;
        if (backArrow)
        {
            drawBackArrow(matrixStack, guiLeft,guiTop);
        }
        if (currentPage != null)
        {
            drawCenterText(matrixStack,guiLeft,guiTop, ClientHelper.simpleTranslatableComponent(currentPage.translationKey));
        }
        else
        {
            drawCenterText(matrixStack,guiLeft,guiTop, ClientHelper.simpleTranslatableComponent(currentChapter.translationKey));
            for (BookPage page : currentChapter.pages)
            {
                if (offset < 10)
                {
                    int posX = guiLeft + 31;
                    int posY = guiTop + 12 + (offset * 20);
                    if (offset > 4)
                    {
                        posX += 120;
                        posY -= 100;
                    }
                    mc.getTextureManager().bindTexture(BOOK_TEXTURE);
                    blit(matrixStack, posX, posY, 64, 168, 110, 18, 512, 512);
                    Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(page.iconStack, posX + 2, posY);
                    mc.fontRenderer.func_243246_a(matrixStack, ClientHelper.simpleTranslatableComponent(page.translationKey), posX + 20, posY + 2, 0);
                    offset++;
                }
            }
        }
        offset = 0;
        for (BookCategory chapter : MalumBookCategories.categories)
        {
            int posX = guiLeft + 1;
            int posY = guiTop + 14 + (offset * 25);
            mc.getTextureManager().bindTexture(BOOK_TEXTURE);
            blit(matrixStack, posX, posY, 42, 168, 20, 23, 512, 512);
            Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(chapter.iconStack, posX+2, posY + 3);
            if (isHovering(mouseX,mouseY,posX,posY,20,23))
            {
                renderTooltip(matrixStack, ClientHelper.simpleTranslatableComponent(chapter.translationKey), mouseX, mouseY);
            }
            offset++;
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        {
            int posX = guiLeft + 6;
            int posY = guiTop + 143;
            if (isHovering((int)mouseX,(int)mouseY,posX,posY,39,16))
            {
                currentPage = null;
            }
        }
        int offset = 0;
        for (BookCategory chapter : MalumBookCategories.categories)
        {
            int posX = guiLeft + 1;
            int posY = guiTop + 14 + (offset * 25);
            if (isHovering((int)mouseX,(int)mouseY,posX,posY,20,23))
            {
                currentChapter = chapter;
                currentPage = null;
                break;
            }
            offset++;
        }
        offset = 0;
        for (BookPage page : currentChapter.pages)
        {
            if (offset < 10)
            {
                int posX = guiLeft + 31;
                int posY = guiTop + 12 + (offset * 20);
                if (offset > 4)
                {
                    posX += 120;
                    posY -= 100;
                }
                if (isHovering((int) mouseX, (int) mouseY, posX, posY, 110, 18))
                {
                    currentPage = page;
                }
                offset++;
            }
        }
        return false;
    }
    
    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
