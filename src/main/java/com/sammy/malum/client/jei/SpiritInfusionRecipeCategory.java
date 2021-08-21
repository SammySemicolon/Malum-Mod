package com.sammy.malum.client.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.cooler_book.CoolerBookScreen;
import com.sammy.malum.client.screen.cooler_book.pages.SpiritInfusionPage;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_systems.recipe.ItemCount;
import com.sammy.malum.core.mod_systems.recipe.SpiritInfusionRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.sammy.malum.MalumHelper.prefix;
import static com.sammy.malum.client.screen.cooler_book.CoolerBookScreen.renderTexture;
import static com.sammy.malum.client.screen.cooler_book.pages.SpiritInfusionPage.uOffset;
import static com.sammy.malum.client.screen.cooler_book.pages.SpiritInfusionPage.vOffset;

public class SpiritInfusionRecipeCategory implements IRecipeCategory<SpiritInfusionRecipe>
{
    public static final ResourceLocation BACKGROUND_TEXTURE = prefix("textures/gui/book/pages/spirit_infusion_page.png");
    public static final ResourceLocation UID = prefix("spirit_infusion");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritInfusionRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(142, 185);
        localizedName = I18n.format("malum.jei.spirit_infusion");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/spirit_infusion_jei.png"), 0, 0, 140, 183);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MalumItems.SPIRIT_ALTAR.get()));
    }

    @Override
    public void draw(SpiritInfusionRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();

        overlay.draw(matrixStack);
        renderItemFrameTexture(matrixStack, 9, 48, recipe.spirits.size());
        if (!recipe.extraItems.isEmpty())
        {
            renderItemFrameTexture(matrixStack, 99, 48, recipe.extraItems.size());
        }
        GlStateManager.disableBlend();
        GlStateManager.disableAlphaTest();
    }

    @Nonnull
    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends SpiritInfusionRecipe> getRecipeClass()
    {
        return SpiritInfusionRecipe.class;
    }

    @Nonnull
    @Override
    public String getTitle()
    {
        return localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setIngredients(SpiritInfusionRecipe recipe, IIngredients iIngredients)
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (ItemCount count : recipe.extraItems)
        {
            stacks.add(count.stack());
        }
        for (ItemCount count : recipe.spirits)
        {
            stacks.add(count.stack());
        }
        stacks.add(new ItemStack(recipe.input, recipe.inputCount));
        iIngredients.setInputs(VanillaTypes.ITEM, stacks);
        iIngredients.setOutput(VanillaTypes.ITEM, new ItemStack(recipe.output, recipe.outputCount));
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, SpiritInfusionRecipe recipe, IIngredients iIngredients)
    {
        int index = 0;
        index = addItems(iRecipeLayout, 9, 48, recipe.spirits, index);
        if (!recipe.extraItems.isEmpty())
        {
            index = addItems(iRecipeLayout, 99, 48, recipe.extraItems, index);
        }

        iRecipeLayout.getItemStacks().init(index+1, true, 61,56);
        iRecipeLayout.getItemStacks().set(index+1, new ItemStack(recipe.input, recipe.inputCount));

        iRecipeLayout.getItemStacks().init(index+2, true, 61,123);
        iRecipeLayout.getItemStacks().set(index+2, new ItemStack(recipe.output, recipe.outputCount));
    }

    public void renderItemFrameTexture(MatrixStack matrixStack, int left, int top, int itemCount)
    {
        int index = itemCount - 1;
        int textureHeight = 32 + index * 19;
        int offset = (int) (6.5f * index);
        top -= offset;
        int uOffset = uOffset()[index];
        int vOffset = vOffset()[index];
        renderTexture(BACKGROUND_TEXTURE, matrixStack, left, top, uOffset, vOffset, 32, textureHeight, 512, 512);
    }
    public int addItems(IRecipeLayout iRecipeLayout, int left, int top, List<ItemCount> items, int baseIndex)
    {
        int index = items.size()-1;
        int offset = (int) (6.5f * index);
        top -= offset;
        for (int i = 0; i < items.size(); i++)
        {
            ItemStack stack = items.get(i).stack();
            iRecipeLayout.getItemStacks().init(baseIndex+i, true, left+7,top+7+19*i);
            iRecipeLayout.getItemStacks().set(baseIndex+i, stack);
        }
        return baseIndex+items.size()+1;
    }
}