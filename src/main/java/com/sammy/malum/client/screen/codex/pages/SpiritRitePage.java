package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.core.systems.recipe.IngredientWithCount;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.renderTexture;

public class SpiritRitePage extends BookPage
{
    private final MalumRiteType riteType;
    public SpiritRitePage(MalumRiteType riteType)
    {
        super(MalumHelper.prefix("textures/gui/book/pages/spirit_rite_page.png"));
        this.riteType = riteType;
    }

    @Override
    public void renderLeft(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderRite(matrixStack, guiLeft+67,guiTop+123, mouseX, mouseY, riteType.spirits);
    }

    @Override
    public void renderRight(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderRite(matrixStack, guiLeft+209,guiTop+123, mouseX, mouseY, riteType.spirits);
    }
    public void renderRite(MatrixStack matrixStack, int left, int top, int mouseX, int mouseY, ArrayList<MalumSpiritType> spirits)
    {
        for (int i = 0; i < spirits.size(); i++)
        {
            ItemStack stack = spirits.get(i).splinterItem().getDefaultInstance();
            ProgressionBookScreen.renderItem(matrixStack, stack, left,top-19*i,mouseX,mouseY);
        }
    }
}
