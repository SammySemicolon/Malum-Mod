package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.EntryObject;
import com.sammy.malum.core.modcontent.MalumSpiritAltarRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

import static net.minecraft.client.gui.AbstractGui.blit;

public class SpiritInfusionPage extends BookPage
{
    public final ItemStack inputStack;
    public final ItemStack outputStack;
    public final ArrayList<ItemStack> spirits;
    
    public SpiritInfusionPage(ItemStack inputStack, ItemStack outputStack, ItemStack... spirits)
    {
        this.inputStack = inputStack;
        this.outputStack = outputStack;
        this.spirits = MalumHelper.toArrayList(spirits);
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/infusion.png");
    }
    
    @Override
    public void draw(MatrixStack stack, EntryObject object, BookScreen screen, int mouseX, int mouseY, int guiLeft, int guiTop, boolean isSecondPage)
    {
        super.draw(stack, object, screen, mouseX, mouseY, guiLeft, guiTop, isSecondPage);
        Minecraft minecraft = Minecraft.getInstance();
        Pair<Integer, Integer> position = getPosition(guiLeft, guiTop, isSecondPage);
        
        int posX = position.first;
        int posY = position.second;
        
        float angleStep = Math.min(30, 120 / spirits.size());
        double rootAngle = 90 - (spirits.size() - 1) * angleStep / 2;
        stack.push();
        stack.translate(-0.5f, 0,0);
        for (int i = 0; i < spirits.size(); i++)
        {
            double a = Math.toRadians(rootAngle + angleStep * i);
            int dx = (int) (59 - 42 * Math.cos(a));
            int dy = (int) (71 - 32 * Math.sin(a));
            minecraft.getTextureManager().bindTexture(BACKGROUND);
            blit(stack, posX+dx-8, posY+dy-8, 119, 1, 16, 16, 512, 512);
            screen.drawItem(stack, spirits.get(i), posX + dx - 8, posY + dy - 8, mouseX, mouseY);
        }
        stack.pop();
        screen.drawItem(stack, inputStack, posX + 50, posY + 62, mouseX, mouseY);
        screen.drawItem(stack, outputStack, posX + 50, posY + 102, mouseX, mouseY);
    }
}