package com.sammy.malum.common.book;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.categories.BookCategory;
import com.sammy.malum.common.book.objects.*;
import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.common.book.pages.BookPageGrouping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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
    public BookObject currentObject;
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
    
        int posX = guiLeft + 106;
        int posY = guiTop + 157;
        
        BackArrowObject backArrowObject = new BackArrowObject(posX, posY, 20, 20, (s) -> s.currentObject != null && s.currentObject.returnObject != null);
        objects.add(backArrowObject);
        
        for (int i = 0; i < MalumBookCategories.categories.size(); i++)
        {
            BookCategory category = MalumBookCategories.categories.get(i);
            posX = guiLeft - 2;
            posY = guiTop + 12 + (i * 32);
            CategoryObject categoryObject = new CategoryObject(category, posX, posY, 26, 26, (s) -> true);
            objects.add(categoryObject);
            for (int j = 0; j < category.groupings.size(); j++)
            {
                BookPageGrouping grouping = category.groupings.get(j);
                int finalJ = j;
                for (int k = 0; k < grouping.pages.size(); k++)
                {
                    BookPage page = grouping.pages.get(k);
                    posX = guiLeft + 34;
                    posY = guiTop + 15 + (k * 22);
                    if (k > 4)
                    {
                        posX += 123;
                        posY -= 110;
                    }
                    
                    PageObject pageObject = new PageObject(posX, posY, 101, 16, categoryObject, (s) -> s.currentObject == null && s.currentGrouping == finalJ && s.currentCategory.equals(category), page);
                    objects.add(pageObject);
                    if (page.linkedPages != null)
                    {
                        for (int l = 0; l < page.linkedPages.size(); l++)
                        {
                            posX = guiLeft + 268;
                            posY = guiTop + 12 + (l * 32);
                            BookPage linkedPage = page.linkedPages.get(l);
                            Predicate<BookScreen> linkedPredicate = (s) -> s.currentObject instanceof PageObject && (((PageObject) s.currentObject).page.equals(linkedPage.sourcePage) || (((PageObject) s.currentObject).page.sourcePage != null && ((PageObject) s.currentObject).page.sourcePage.linkedPages.contains(linkedPage)));
                            LinkedPageObject linkedPageObject = new LinkedPageObject(posX,posY,26,26, pageObject, linkedPredicate, linkedPage);
                            objects.add(linkedPageObject);
                        }
                    }
                }
            }
        }
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
    public void resize(Minecraft minecraft, int width, int height)
    {
        setupObjects(minecraft);
        super.resize(minecraft, width, height);
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
        if (currentObject instanceof PageObject)
        {
            if (!(currentObject instanceof LinkedPageObject) && currentObject.draw < 20)
            {
                currentObject.draw++;
            }
            float brightness = (20 - currentObject.draw) / 20f;
            drawWrappingText(matrixStack, ClientHelper.simpleTranslatableComponent(((PageObject) currentObject).page.detailedTranslationKey), guiLeft+ 35, guiTop + 10, 100,brightness);
        }
        for (BookObject object : objects)
        {
            if (object.showPredicate.test(this))
            {
                if (object.draw < 20)
                {
                    object.draw++;
                }
                if (isHovering(mouseX, mouseY, object.posX, object.posY, object.width, object.height))
                {
                    if (object.hover < 20)
                    {
                        object.hover++;
                    }
                    object.isHovering = true;
                }
                else
                {
                    if (object.hover > 0)
                    {
                        object.hover--;
                    }
                    object.isHovering = false;
                }
                object.draw(mc, matrixStack, mouseX, mouseY, partialTicks);
            }
            else if (object.draw > 0 && !object.equals(currentObject))
            {
                object.draw--;
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
    
    public static int packColor(int alpha, int red, int green, int blue)
    {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }
    
    public static void drawWrappingText(MatrixStack mStack, ITextComponent component, int x, int y, int w, float brightness)
    {
        drawWrappingText(mStack, component.getString(), x, y, w, brightness);
    }
    public static void drawWrappingText(MatrixStack mStack, String text, int x, int y, int w, float brightness)
    {
        FontRenderer font = Minecraft.getInstance().fontRenderer;
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        String line = "";
        for (String s : words)
        {
            if (font.getStringWidth(line) + font.getStringWidth(s) > w)
            {
                lines.add(line);
                line = s + " ";
            }
            else line += s + " ";
        }
        if (!line.isEmpty()) lines.add(line);
        for (int i = 0; i < lines.size(); i++)
        {
            screen.renderPurpleText(mStack, lines.get(i), x, y + i * (font.FONT_HEIGHT + 1), brightness);
        }
    }
    
    public void renderPurpleText(MatrixStack stack, ITextComponent component, int x, int y, float brightness)
    {
        renderPurpleText(stack, component.getString(), x, y, brightness);
    }
    
    public void renderPurpleText(MatrixStack stack, String text, int x, int y, float brightness)
    {
        int r = (int) MathHelper.lerp(brightness, 112, 203);
        int g = (int) MathHelper.lerp(brightness, 30, 81);
        int b = (int) MathHelper.lerp(brightness, 169, 204);
        font.drawString(stack, text, x, y - 1, packColor(128, 255, 183, 236));
        font.drawString(stack, text, x - 1, y, packColor(128, 255, 210, 243));
        font.drawString(stack, text, x + 1, y, packColor(128, 240, 131, 232));
        font.drawString(stack, text, x, y + 1, packColor(128, 236, 110, 226));
        font.drawString(stack, text, x, y, packColor(255, r, g, b));
    }
}