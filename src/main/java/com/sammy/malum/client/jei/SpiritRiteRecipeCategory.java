package com.sammy.malum.client.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.core.registry.items.ItemRegistry;
import com.sammy.malum.core.systems.recipe.IngredientWithCount;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.MalumHelper.prefix;
import static com.sammy.malum.MalumHelper.toArrayList;
import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.renderTexture;
import static com.sammy.malum.client.screen.codex.pages.SpiritInfusionPage.uOffset;
import static com.sammy.malum.client.screen.codex.pages.SpiritInfusionPage.vOffset;

public class SpiritRiteRecipeCategory implements IRecipeCategory<MalumRiteType>
{
    public static final ResourceLocation UID = prefix("spirit_rite");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;
    private final FontRenderer font;

    public SpiritRiteRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(142, 185);
        localizedName = I18n.format("malum.jei.spirit_rite");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/spirit_rite_jei.png"), 0, 0, 140, 183);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ItemRegistry.RUNEWOOD_TOTEM_BASE.get()));
        font = Minecraft.getInstance().fontRenderer;
    }

    @Override
    public void draw(MalumRiteType rite, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();

        overlay.draw(matrixStack);
        ProgressionBookScreen.renderText(matrixStack, new TranslationTextComponent(rite.translationIdentifier()), 105-font.getStringWidth(rite.translationIdentifier())/2,160);
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
    public Class<? extends MalumRiteType> getRecipeClass()
    {
        return MalumRiteType.class;
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
    public void setIngredients(MalumRiteType rite, IIngredients iIngredients)
    {
        ArrayList<ItemStack> items = new ArrayList<>();
        rite.spirits.forEach(spirit -> items.add(spirit.splinterItem().getDefaultInstance()));
        iIngredients.setInputs(VanillaTypes.ITEM, items);
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MalumRiteType rite, IIngredients iIngredients)
    {
        for (int i = 0; i < rite.spirits.size(); i++)
        {
            iRecipeLayout.getItemStacks().init(i, true, 61,120-19*i);
            iRecipeLayout.getItemStacks().set(i, rite.spirits.get(i).splinterItem().getDefaultInstance());
        }
    }
}