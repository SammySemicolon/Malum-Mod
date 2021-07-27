package com.sammy.malum.common.integration.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_systems.rites.MalumRiteType;
import com.sammy.malum.core.mod_systems.spirit.MalumSpiritType;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;

import static com.sammy.malum.MalumHelper.prefix;
import static com.sammy.malum.common.book.BookScreen.packColor;
import static net.minecraft.client.gui.AbstractGui.blit;

public class SpiritRitesRecipeCategory implements IRecipeCategory<MalumRiteType>
{
    public static final ResourceLocation UID = prefix("spirit_rites");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public static final ResourceLocation BACKGROUND = MalumHelper.prefix("textures/gui/spirit_rites_recipe_overlay.png");

    public SpiritRitesRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(132, 163);
        localizedName = I18n.format("malum.jei.spirit_rites");
        overlay = guiHelper.createDrawable(BACKGROUND, 0, 0, 130, 161);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MalumItems.TOTEM_BASE.get()));
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
    public void setIngredients(MalumRiteType recipe, IIngredients iIngredients)
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (MalumSpiritType spirit : recipe.spirits)
        {
            stacks.add(spirit.splinterItem().getDefaultInstance());
        }
        iIngredients.setInputs(VanillaTypes.ITEM, stacks);
    }

    @Override
    public void draw(MalumRiteType recipe, MatrixStack matrixStack, double mouseX, double mouseY)
    {
        Minecraft minecraft = Minecraft.getInstance();
        overlay.draw(matrixStack);
        TranslationTextComponent component = new TranslationTextComponent("malum.gui.rite." + recipe.identifier);

        String text = component.getString();
        int r = 112;
        int g = 30;
        int b = 169;
        int x = 65 - minecraft.fontRenderer.getStringWidth(component.getString())/2;
        int y = 5;
        minecraft.fontRenderer.drawString(matrixStack, text, x, y - 1, packColor(128, 255, 183, 236));
        minecraft.fontRenderer.drawString(matrixStack, text, x - 1, y, packColor(128, 255, 210, 243));
        minecraft.fontRenderer.drawString(matrixStack, text, x + 1, y, packColor(128, 240, 131, 232));
        minecraft.fontRenderer.drawString(matrixStack, text, x, y + 1, packColor(128, 236, 110, 226));
        minecraft.fontRenderer.drawString(matrixStack, text, x, y, packColor(255, r, g, b));

    }
    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MalumRiteType recipe, IIngredients iIngredients)
    {
        for (int i = 0; i < recipe.spirits.size(); i++)
        {
            MalumSpiritType type = recipe.spirits.get(i);
            int itemPosX = 56;
            int itemPosY = 121 - i * 22;

            iRecipeLayout.getItemStacks().init(i, true, itemPosX, itemPosY);
            iRecipeLayout.getItemStacks().set(i, type.splinterItem().getDefaultInstance());
        }
    }
}