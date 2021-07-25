package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.EntryObject;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;

public class RitePage extends BookPage
{
    public final MalumRiteType rite;

    public RitePage(MalumRiteType rite)
    {
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/rite.png");
        this.rite = rite;
    }
    @Override
    public void draw(MatrixStack stack, EntryObject object, BookScreen screen, int mouseX, int mouseY, int guiLeft, int guiTop, boolean isSecondPage)
    {
        super.draw(stack, object, screen, mouseX, mouseY, guiLeft, guiTop, isSecondPage);
        Pair<Integer, Integer> position = getPosition(guiLeft, guiTop, isSecondPage);
        int posX = position.first;
        int posY = position.second;
        for (int i = 0; i < rite.spirits.size(); i++)
        {
            MalumSpiritType type = rite.spirits.get(i);
            int itemPosX = posX + 56;
            int itemPosY = posY + 113 - i * 22;
            screen.drawItem(stack, type.splinterItem().getDefaultInstance(), itemPosX, itemPosY, mouseX, mouseY);
        }
        screen.drawItem(stack, MalumItems.TOTEM_BASE.get().getDefaultInstance(), posX + 101, posY + 115, mouseX, mouseY);
    }
}