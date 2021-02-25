package com.sammy.malum.common.book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.categories.BookCategory;
import net.minecraft.client.Minecraft;

import java.util.function.Predicate;

import static com.sammy.malum.common.book.BookScreen.screen;
import static net.minecraft.client.gui.AbstractGui.blit;

public class CategoryObject extends BookObject
{
    public final BookCategory category;
    
    public CategoryObject(BookCategory category, int posX, int posY, int width, int height, Predicate<BookScreen> showPredicate)
    {
        super(posX, posY, width, height, showPredicate);
        this.category = category;
    }
    
    @Override
    public void draw(Minecraft minecraft, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, boolean isHovering)
    {
        if (isHovering)
        {
            screen.renderTooltip(matrixStack, ClientHelper.simpleTranslatableComponent(category.translationKey), mouseX, mouseY);
        }
        minecraft.getTextureManager().bindTexture(screen.texture());
        blit(matrixStack, posX,posY, 1, 211, 26, 26, 512, 512);
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(category.iconStack, posX + 5, posY + 5);
        
    }
    
    @Override
    public void interact(BookScreen screen)
    {
        screen.currentCategory = category;
        screen.currentPage = null;
        screen.currentGrouping = 0;
        
    }
}