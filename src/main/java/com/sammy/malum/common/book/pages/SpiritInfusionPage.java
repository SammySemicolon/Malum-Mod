package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.EntryObject;
import com.sammy.malum.core.modcontent.MalumSpiritAltarRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SpiritInfusionPage extends BookPage
{
    public final ItemStack inputStack;
    public final ItemStack outputStack;
    public final ArrayList<ItemStack> spirits;

    public SpiritInfusionPage(Item outputStack)
    {
        MalumSpiritAltarRecipes.MalumSpiritAltarRecipe finalRecipe = null;
        for (MalumSpiritAltarRecipes.MalumSpiritAltarRecipe recipe : MalumSpiritAltarRecipes.RECIPES)
        {
            if (recipe.outputIngredient.getItemAlt().getItem().equals(outputStack))
            {
                finalRecipe = recipe;
                break;
            }
        }
        if (finalRecipe == null)
        {
            MalumMod.LOGGER.info("this ain't good");
            throw new NullPointerException();
        }
        this.inputStack = finalRecipe.inputIngredient.getItemAlt();
        this.outputStack = finalRecipe.outputIngredient.getItemAlt();
        ArrayList<ItemStack> spirits = new ArrayList<>();
        finalRecipe.spiritIngredients.forEach(s -> spirits.add(s.getItem()));
        this.spirits = spirits;
        BACKGROUND = MalumHelper.prefix("textures/gui/pages/infusion.png");
    }
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
        
        ArrayList<Pair<Integer, Integer>> offsets = MalumHelper.toArrayList(Pair.of(56, 20), Pair.of(56, 72), Pair.of(30, 46), Pair.of(82, 46), Pair.of(34, 24), Pair.of(78, 68), Pair.of(78, 24), Pair.of(34, 69));
        for (int i = 0; i < spirits.size(); i++)
        {
            Pair<Integer, Integer> offset = offsets.get(i);
            screen.drawItem(stack, spirits.get(i), posX + offset.first, posY + offset.second, mouseX, mouseY);
        }
        screen.drawItem(stack, inputStack, posX + 56, posY + 46, mouseX, mouseY);
        screen.drawItem(stack, outputStack, posX + 56, posY + 113, mouseX, mouseY);
    }
}