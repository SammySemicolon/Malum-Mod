package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.EntryObject;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

import static net.minecraft.client.gui.AbstractGui.blit;

public class RitePage extends BookPage
{
    public ArrayList<Item> items;
    
    public RitePage(MalumRites.MalumRite rite)
    {
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/rite.png");
        items = new ArrayList<>();
        for (MalumSpiritType type : rite.spirits)
        {
            items.add(type.splinterItem);
        }
    }
    
    @Override
    public void draw(MatrixStack stack, EntryObject object, BookScreen screen, int mouseX, int mouseY, int guiLeft, int guiTop, boolean isSecondPage)
    {
        super.draw(stack, object, screen, mouseX, mouseY, guiLeft, guiTop, isSecondPage);
        Pair<Integer, Integer> position = getPosition(guiLeft, guiTop, isSecondPage);
        int posX = position.first;
        int posY = position.second;
        
        for (int i = 0; i < items.size(); i++)
        {
            Item item = items.get(i);
            int itemPosX = posX + 48;
            int itemPosY = posY + 106 - 23 * i;
            screen.drawItem(stack, item.getDefaultInstance(), itemPosX + 2, itemPosY + 2, mouseX, mouseY);
        }
    }
}