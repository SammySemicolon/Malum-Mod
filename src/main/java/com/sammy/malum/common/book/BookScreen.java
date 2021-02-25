package com.sammy.malum.common.book;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.categories.BookCategory;
import com.sammy.malum.common.book.objects.BookObject;
import com.sammy.malum.common.book.objects.CategoryObject;
import com.sammy.malum.common.book.objects.PageObject;
import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.common.book.pages.BookPageGrouping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;

public class BookScreen extends Screen
{
    public static final ResourceLocation BOOK_LECTERN_TEXTURE = MalumHelper.prefix("textures/gui/book_lectern.png");
    public static final ResourceLocation BOOK_ITEM_TEXTURE = MalumHelper.prefix("textures/gui/book_item.png");
    
    public ResourceLocation texture()
    {
        if (screen.isLectern)
        {
            return BOOK_LECTERN_TEXTURE;
        }
        else
        {
            return BOOK_ITEM_TEXTURE;
        }
    }
    public final int bookWidth = 292;
    public final int bookHeight = 190;
    public BookCategory currentCategory;
    public int currentGrouping = 0;
    public BookPage currentPage;
    public boolean isLectern;
    public ArrayList<BookObject> objects;
    public static BookScreen screen;
    
    protected BookScreen()
    {
        super(ClientHelper.simpleTranslatableComponent("malum.gui.book.title"));
        setupObjects(Minecraft.getInstance());
    }
    public void setupObjects(Minecraft mc)
    {
        objects = new ArrayList<>();
        this.width = mc.getMainWindow().getScaledWidth();
        this.height = mc.getMainWindow().getScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        for (int i = 0; i < MalumBookCategories.categories.size(); i++)
        {
            BookCategory category = MalumBookCategories.categories.get(i);
            int posX = guiLeft - 2;
            int posY = guiTop + 12 + (i * 32);
            objects.add(new CategoryObject(category, posX, posY, 26, 26, (s) -> true));
            for (int j = 0; j < category.groupings.size(); j++)
            {
                BookPageGrouping grouping = category.groupings.get(j);
                int finalJ = j;
                for (int k = 0; k < grouping.pages.size(); k++)
                {
                    BookPage page = grouping.pages.get(k);
                    posX = guiLeft + 34;
                    posY = guiTop + 15 + (k * 22);
                    mc.getTextureManager().bindTexture(texture());
                    if (k > 4)
                    {
                        posX += 123;
                        posY -= 110;
                    }
                    objects.add(new PageObject(page, posX, posY, 101, 16, (s) -> s.currentGrouping == finalJ && s.currentCategory == category));
                }
            }
        }
    }
    
    public void drawBackArrow(MatrixStack matrixStack, int guiLeft, int guiTop)
    {
        Minecraft mc = Minecraft.getInstance();
        int posX = guiLeft + 106;
        int posY = guiTop + 157;
        mc.getTextureManager().bindTexture(BOOK_LECTERN_TEXTURE);
        blit(matrixStack, posX, posY, 51, 211, 20, 20, 512, 512);
    }
    
    public void drawNextArrow(MatrixStack matrixStack, int guiLeft, int guiTop)
    {
        Minecraft mc = Minecraft.getInstance();
        int posX = guiLeft + 162;
        int posY = guiTop + 157;
        mc.getTextureManager().bindTexture(BOOK_LECTERN_TEXTURE);
        blit(matrixStack, posX, posY, 29, 211, 20, 20, 512, 512);
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        renderBackground(matrixStack);
        Minecraft mc = Minecraft.getInstance();
        
        mc.getTextureManager().bindTexture(texture());
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        blit(matrixStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        for (BookObject object : objects)
        {
            if (object.showPredicate.test(this))
            {
                boolean isHovering = false;
                if (isHovering(mouseX, mouseY, object.posX, object.posY, object.width, object.height))
                {
                    if (object.hover < 20)
                    {
                        object.hover++;
                    }
                    isHovering = true;
                }
                else
                {
                    if (object.hover > 0)
                    {
                        object.hover--;
                    }
                }
                object.draw(mc, matrixStack, mouseX, mouseY, partialTicks, isHovering);
            }
        }

//        drawBackArrow(matrixStack, guiLeft, guiTop);
//        drawNextArrow(matrixStack, guiLeft, guiTop);
//
//        if (currentPage != null)
//        {
//            ITextComponent component = ClientHelper.simpleTranslatableComponent(currentPage.translationKey);
//            int posX = guiLeft + 145 - mc.fontRenderer.getStringWidth(component.getString()) /2;
//            int posY = guiTop + 131;
//            renderBrownText(matrixStack, component, posX,posY);
//        }
//        else
//        {
//            {
//                ITextComponent component = ClientHelper.simpleTranslatableComponent(currentCategory.translationKey);
//                int posX = guiLeft + 145 - mc.fontRenderer.getStringWidth(component.getString()) / 2;
//                int posY = guiTop + 131;
//                renderBrownText(matrixStack, component, posX, posY);
//            }
//            BookPageGrouping grouping = currentCategory.groupings.get(currentGrouping);
//
//            for (int i = 0; i < grouping.pages.size(); i++)
//            {
//                BookPage page = grouping.pages.get(i);
//                int posX = guiLeft + 34;
//                int posY = guiTop + 15 + (i * 22);
//                mc.getTextureManager().bindTexture(BOOK_LECTERN_TEXTURE);
//                if (i > 4)
//                {
//                    posX += 123;
//                    posY -= 110;
//                }
//                blit(matrixStack, posX, posY, 1, 193, 101, 16, 512, 512);
//                Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(page.iconStack, posX + 2, posY);
//                renderPurpleText(matrixStack, ClientHelper.simpleTranslatableComponent(page.translationKey), posX + 20, posY + 2);
//            }
//        }
//        for (int i =0; i < MalumBookCategories.categories.size(); i++)
//        {
//            BookCategory category = MalumBookCategories.categories.get(i);
//            int posX = guiLeft -2;
//            int posY = guiTop + 12 + (i * 32);
//            mc.getTextureManager().bindTexture(BOOK_LECTERN_TEXTURE);
//            blit(matrixStack, posX, posY, 1, 211, 26, 26, 512, 512);
//            Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(category.iconStack, posX + 5, posY + 5);
//            if (isHovering(mouseX, mouseY, posX, posY, 26, 26))
//            {
//                renderTooltip(matrixStack, ClientHelper.simpleTranslatableComponent(category.translationKey), mouseX, mouseY);
//            }
//        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        for (BookObject object : objects)
        {
            if (object.showPredicate.test(this))
            {
                if (isHovering((int) mouseX, (int) mouseY, object.posX, object.posY, object.width, object.height))
                {
                    object.interact(screen);
                    return true;
                }
            }
        }
//        if (currentGrouping < currentCategory.groupings.size()-1)
//        {
//            int posX = guiLeft + 162;
//            int posY = guiTop + 157;
//            if (isHovering((int) mouseX, (int) mouseY, posX, posY, 20, 20))
//            {
//                currentGrouping++;
//                return true;
//            }
//        }
//        if (currentPage != null)
//        {
//            int posX = guiLeft + 106;
//            int posY = guiTop + 157;
//            if (isHovering((int) mouseX, (int) mouseY, posX, posY, 20, 20))
//            {
//                currentPage = null;
//                return true;
//            }
//        }
//        else if (currentGrouping > 0)
//        {
//            int posX = guiLeft + 106;
//            int posY = guiTop + 157;
//            if (isHovering((int) mouseX, (int) mouseY, posX, posY, 20, 20))
//            {
//                currentGrouping--;
//                return true;
//            }
//        }
//        for (int i =0; i < MalumBookCategories.categories.size(); i++)
//        {
//            BookCategory category = MalumBookCategories.categories.get(i);
//            int posX = guiLeft -2;
//            int posY = guiTop + 12 + (i * 32);
//            if (isHovering((int) mouseX, (int) mouseY, posX, posY, 26, 26))
//            {
//                currentCategory = category;
//                currentPage = null;
//                currentGrouping = 0;
//                return true;
//            }
//        }
//        BookPageGrouping grouping = currentCategory.groupings.get(currentGrouping);
//
//        for (int i = 0; i < grouping.pages.size(); i++)
//        {
//            BookPage page = grouping.pages.get(i);
//            int posX = guiLeft + 34;
//            int posY = guiTop + 15 + (i * 22);
//            if (i > 4)
//            {
//                posX += 123;
//                posY -= 110;
//            }
//            if (isHovering((int) mouseX, (int) mouseY, posX, posY, 101, 16))
//            {
//                currentPage = page;
//                return true;
//            }
//        }
        return false;
    }
    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
    public static BookScreen getInstance(boolean isLectern)
    {
        if (screen == null)
        {
            screen = new BookScreen();
            screen.currentCategory = MalumBookCategories.categories.get(0);
        }
        screen.isLectern = isLectern;
        return screen;
    }
    
    public static void openScreen(boolean isLectern)
    {
        Minecraft.getInstance().displayGuiScreen(getInstance(isLectern));
    }
    
    public boolean isHovering(int mouseX, int mouseY, int posX, int posY, int width, int height)
    {
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }
    
    public static int packColor(int alpha, int red, int green, int blue) {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }
    public void renderBrownText(MatrixStack stack, ITextComponent component, int x, int y)
    {
        String text = component.getString();
        font.drawString(stack, text, x, y - 1, packColor(128, 55, 30, 26));
        font.drawString(stack, text, x - 1, y, packColor(128, 64, 35, 30));
        font.drawString(stack, text, x + 1, y, packColor(128, 40, 20, 17));
        font.drawString(stack, text, x, y + 1, packColor(128, 75, 41, 36));
        font.drawString(stack, text, x, y, packColor(255, 17, 8, 7));
    }
    public void renderPurpleText(MatrixStack stack, ITextComponent component, int x, int y)
    {
        String text = component.getString();
        font.drawString(stack, text, x, y - 1, packColor(128, 255, 183, 236));
        font.drawString(stack, text, x - 1, y, packColor(128, 255, 210, 243));
        font.drawString(stack, text, x + 1, y, packColor(128, 240, 131, 232));
        font.drawString(stack, text, x, y + 1, packColor(128, 236, 110, 226));
        font.drawString(stack, text, x, y, packColor(255, 182, 61, 183));
    }
}