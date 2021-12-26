package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.systems.recipe.IngredientWithCount;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.renderTexture;

@SuppressWarnings("all")
public class SpiritInfusionPage extends BookPage
{
    private final SpiritInfusionRecipe recipe;
    public SpiritInfusionPage(Predicate<SpiritInfusionRecipe> predicate)
    {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_infusion_page.png"));
        if (Minecraft.getInstance() == null) //this is null during datagen
        {
            this.recipe = null;
            return;
        }
        this.recipe = SpiritInfusionRecipe.getRecipe(Minecraft.getInstance().level, predicate);
    }
    public SpiritInfusionPage(SpiritInfusionRecipe recipe)
    {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_infusion_page.png"));
        this.recipe = recipe;
    }
    public static SpiritInfusionPage fromInput(Item inputItem)
    {
        return new SpiritInfusionPage(s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }
    public static SpiritInfusionPage fromOutput(Item outputItem)
    {
        return new SpiritInfusionPage(s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }
    public static SpiritInfusionPage fromImpetus(Item impetus, Item crackedImpetus)
    {
        return new SpiritInfusionPage(s -> s.doesOutputMatch(impetus.getDefaultInstance()) && !s.doesInputMatch(crackedImpetus.getDefaultInstance()));
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ItemStack inputStack = recipe.input.stack();
        ItemStack outputStack = recipe.output.stack();
        if (!recipe.extraItems.isEmpty())
        {
            renderIngredients(poseStack, guiLeft+105,guiTop+51, mouseX, mouseY, recipe.extraItems);
        }
        ProgressionBookScreen.renderItem(poseStack, inputStack, guiLeft+67, guiTop+59,mouseX,mouseY);
        ProgressionBookScreen.renderItem(poseStack, outputStack, guiLeft+67, guiTop+126,mouseX,mouseY);
        renderItems(poseStack, guiLeft+15,guiTop+51, mouseX, mouseY, recipe.spirits);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ItemStack inputStack = recipe.input.stack();
        ItemStack outputStack = recipe.output.stack();
        if (!recipe.extraItems.isEmpty())
        {
            renderIngredients(poseStack, guiLeft+247,guiTop+51, mouseX, mouseY, recipe.extraItems);
        }
        ProgressionBookScreen.renderItem(poseStack, inputStack, guiLeft+209, guiTop+59,mouseX,mouseY);
        ProgressionBookScreen.renderItem(poseStack, outputStack, guiLeft+209, guiTop+126,mouseX,mouseY);
        renderItems(poseStack, guiLeft+157,guiTop+51, mouseX, mouseY, recipe.spirits);
    }
    public void renderIngredients(PoseStack poseStack, int left, int top, int mouseX, int mouseY, List<IngredientWithCount> ingredients)
    {
        ArrayList<ItemWithCount> items = (ArrayList<ItemWithCount>) ingredients.stream().map(ItemWithCount::fromIngredient).collect(Collectors.toList());
        renderItems(poseStack, left, top, mouseX, mouseY, items);
    }
    public void renderItems(PoseStack poseStack, int left, int top, int mouseX, int mouseY, List<ItemWithCount> items)
    {
        //14 6, 156 6
        //15 51
        int index = items.size()-1;
        int textureHeight = 32 + index * 19;
        int offset = (int) (6.5f * index);
        top -= offset;
        int uOffset = uOffset()[index];
        int vOffset = vOffset()[index];
        renderTexture(BACKGROUND, poseStack, left,top,uOffset,vOffset, 32, textureHeight, 512, 512);

        for (int i = 0; i < items.size(); i++)
        {
            ItemStack stack = items.get(i).stack();
            ProgressionBookScreen.renderItem(poseStack, stack, left+8,top+8+19*i,mouseX,mouseY);
        }
    }
    public static int[] uOffset()
    {
        return new int[]{360,393,393,360,327,294,294,327};
    }
    public static int[] vOffset()
    {
        return new int[]{1,1,53,34,1,1,129,110};
    }
}
