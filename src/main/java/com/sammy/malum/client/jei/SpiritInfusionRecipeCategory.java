package com.sammy.malum.client.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.core.registry.item.ItemRegistry;
import com.sammy.malum.core.systems.recipe.IngredientWithCount;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.MalumHelper.prefix;
import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.renderTexture;
import static com.sammy.malum.client.screen.codex.pages.SpiritInfusionPage.uOffset;
import static com.sammy.malum.client.screen.codex.pages.SpiritInfusionPage.vOffset;

public class SpiritInfusionRecipeCategory implements IRecipeCategory<SpiritInfusionRecipe>
{
    public static final ResourceLocation BACKGROUND_TEXTURE = prefix("textures/gui/book/pages/spirit_infusion_page.png");
    public static final ResourceLocation UID = prefix("spirit_infusion");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritInfusionRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(142, 185);
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/spirit_infusion_jei.png"), 0, 0, 140, 183);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ItemRegistry.SPIRIT_ALTAR.get()));
    }

    @Override
    public void draw(SpiritInfusionRecipe recipe, PoseStack poseStack, double mouseX, double mouseY)
    {
        overlay.draw(poseStack);
        renderItemFrameTexture(poseStack, 9, 48, recipe.spirits.size());
        if (!recipe.extraItems.isEmpty())
        {
            renderItemFrameTexture(poseStack, 99, 48, recipe.extraItems.size());
        }
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

    @Override
    public Component getTitle() {
        return new TranslatableComponent("malum.jei." + UID.getPath());
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
        ArrayList<ItemStack> items = new ArrayList<>(Arrays.asList(recipe.input.ingredient.getItems()));

        recipe.extraItems.forEach(ingredient -> items.addAll(ingredient.asStackList()));
        recipe.spirits.forEach(ingredient -> items.add(ingredient.stack()));
        iIngredients.setInputs(VanillaTypes.ITEM, items);
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.output.stack());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, SpiritInfusionRecipe recipe, IIngredients iIngredients)
    {
        int index = 0;
        index = addItems(iRecipeLayout, 9, 48, recipe.spirits, index);
        if (!recipe.extraItems.isEmpty())
        {
            index = addIngredients(iRecipeLayout, 99, 48, recipe.extraItems, index);
        }

        iRecipeLayout.getItemStacks().init(index+1, true, 61,56);
        iRecipeLayout.getItemStacks().set(index+1, recipe.input.asStackList());

        iRecipeLayout.getItemStacks().init(index+2, true, 61,123);
        iRecipeLayout.getItemStacks().set(index+2, recipe.output.stack());
    }

    public void renderItemFrameTexture(PoseStack poseStack, int left, int top, int itemCount)
    {
        int index = itemCount - 1;
        int textureHeight = 32 + index * 19;
        int offset = (int) (6.5f * index);
        top -= offset;
        int uOffset = uOffset()[index];
        int vOffset = vOffset()[index];
        renderTexture(BACKGROUND_TEXTURE, poseStack, left, top, uOffset, vOffset, 32, textureHeight, 512, 512);
    }
    public int addIngredients(IRecipeLayout iRecipeLayout, int left, int top, List<IngredientWithCount> ingredients, int baseIndex)
    {
        ArrayList<ItemWithCount> items = (ArrayList<ItemWithCount>) ingredients.stream().map(ItemWithCount::fromIngredient).collect(Collectors.toList());
        return addItems(iRecipeLayout, left, top, items, baseIndex);
    }
    public int addItems(IRecipeLayout iRecipeLayout, int left, int top, List<ItemWithCount> items, int baseIndex)
    {
        int index = items.size()-1;
        int offset = (int) (6.5f * index);
        top -= offset;
        for (int i = 0; i < items.size(); i++)
        {
            ItemWithCount item = items.get(i);
            iRecipeLayout.getItemStacks().init(baseIndex+i, true, left+7,top+7+19*i);
            iRecipeLayout.getItemStacks().set(baseIndex+i, item.stack());
        }
        return baseIndex+items.size()+1;
    }
}