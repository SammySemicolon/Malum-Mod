package com.sammy.malum.common.book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.book.BookScreen;
import net.minecraft.client.Minecraft;

import java.util.function.Predicate;

public class BookObject
{
    public BookObject returnObject;
    public boolean isHovering;
    public float hover;
    public final int posX;
    public final int posY;
    public final int width;
    public final int height;
    public float draw;
    public Predicate<BookScreen> specialPredicate;
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