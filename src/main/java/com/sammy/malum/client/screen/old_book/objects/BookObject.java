package com.sammy.malum.client.screen.old_book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.client.screen.old_book.BookScreen;
import net.minecraft.client.Minecraft;

import java.util.function.Predicate;

public class BookObject
{
    public BookObject returnObject;
    public boolean isHovering;
    public float hover;
    public int posX;
    public int posY;
    public int width;
    public int height;
    public float draw;
    public Predicate<BookScreen> specialPredicate;
    public boolean renderAfterPages;

    public BookObject()
    {
    }
    public BookObject(int posX, int posY, int width, int height)
    {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }
    
    public BookObject(int posX, int posY, int width, int height, BookObject returnObject)
    {
        this(posX, posY, width, height);
        this.returnObject = returnObject;
    }
    public BookObject renderAfterPages()
    {
        this.renderAfterPages = true;
        return this;
    }
    public void draw(Minecraft minecraft, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
    
    }
    public BookObject addSpecialPredicate(Predicate<BookScreen> specialPredicate)
    {
        this.specialPredicate = specialPredicate;
        return this;
    }
    public boolean canAccess(BookScreen screen)
    {
        if (specialPredicate != null)
        {
            return specialPredicate.test(screen);
        }
        return false;
    }
    public void interact(BookScreen screen)
    {
        screen.currentObject = this;
        screen.playSound();
    }
}