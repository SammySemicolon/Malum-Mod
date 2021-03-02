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
        Pair<Integer, Integer> position = getPosition(guiLeft, guiTop, isSecondPage);
        
        int posX = position.first;
        int posY = position.second;
        
        ArrayList<Pair<Integer, Integer>> offsets = MalumHelper.toArrayList(Pair.of(50, 15), Pair.of(50, 67), Pair.of(24, 41), Pair.of(76, 41), Pair.of(28, 19), Pair.of(72, 63), Pair.of(72, 19), Pair.of(28, 63));
        for (int i = 0; i < spirits.size(); i++)
        {
            Pair<Integer, Integer> offset = offsets.get(i);
            screen.drawItem(stack, spirits.get(i), posX + offset.first, posY + offset.second, mouseX, mouseY);
        }
        screen.drawItem(stack, inputStack, posX + 50, posY + 41, mouseX, mouseY);
        screen.drawItem(stack, outputStack, posX + 50, posY + 108, mouseX, mouseY);
    }
}