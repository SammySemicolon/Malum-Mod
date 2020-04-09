package com.kittykitcatcat.malum.integration.jei.spiritInfusion;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.recipes.SpiritFurnaceRecipe;
import com.kittykitcatcat.malum.recipes.SpiritInfusionRecipe;
import com.mojang.blaze3d.platform.GlStateManager;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SpiritInfusionRecipeCategory implements IRecipeCategory<SpiritInfusionRecipe>
{
    public static final ResourceLocation UID = new ResourceLocation(MalumMod.MODID, "spirit_infusion");
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritInfusionRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createBlankDrawable(180, 79);
        localizedName = I18n.format("malum.jei.spirit_infusion_overlay");
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/spirit_infusion_overlay.png"),
            0, 0, 172, 77);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModItems.soul_binder));
    }

    @Override
    public void draw(SpiritInfusionRecipe recipe, double mouseX, double mouseY)
    {
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();
        overlay.draw();
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
    public void setIngredients(SpiritInfusionRecipe spiritInfusionRecipe, IIngredients iIngredients)
    {
        List<ItemStack> list = new ArrayList<>();
        for (Item item : spiritInfusionRecipe.getItems())
        {
            list.add(new ItemStack(item));
        }
        list.add(new ItemStack(spiritInfusionRecipe.getCatalyst()));
        iIngredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(list.stream().map(ItemStack::copy).filter(s -> !s.isEmpty()).collect(Collectors.toList())));
        iIngredients.setOutput(VanillaTypes.ITEM, spiritInfusionRecipe.getOutputStack());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, SpiritInfusionRecipe spiritFurnaceRecipe, IIngredients iIngredients)
    {
        if (spiritFurnaceRecipe.getItems().get(0) != Items.AIR)
        {
            iRecipeLayout.getItemStacks().init(0, true, 53, 6);
            iRecipeLayout.getItemStacks().set(0, new ItemStack(spiritFurnaceRecipe.getItems().get(0)));
        }
        if (spiritFurnaceRecipe.getItems().get(1) != Items.AIR)
        {
            iRecipeLayout.getItemStacks().init(1, true, 77, 6);
            iRecipeLayout.getItemStacks().set(1, new ItemStack(spiritFurnaceRecipe.getItems().get(1)));
        }
        if (spiritFurnaceRecipe.getItems().get(2) != Items.AIR)
        {
            iRecipeLayout.getItemStacks().init(2, true, 101, 6);
            iRecipeLayout.getItemStacks().set(2, new ItemStack(spiritFurnaceRecipe.getItems().get(2)));
        }
        if (spiritFurnaceRecipe.getItems().get(3) != Items.AIR)
        {
            iRecipeLayout.getItemStacks().init(3, true, 53, 30);
            iRecipeLayout.getItemStacks().set(3, new ItemStack(spiritFurnaceRecipe.getItems().get(3)));
        }
        if (spiritFurnaceRecipe.getItems().get(4) != Items.AIR)
        {
            iRecipeLayout.getItemStacks().init(4, true, 101, 54);
            iRecipeLayout.getItemStacks().set(4, new ItemStack(spiritFurnaceRecipe.getItems().get(4)));
        }
        if (spiritFurnaceRecipe.getItems().get(5) != Items.AIR)
        {
            iRecipeLayout.getItemStacks().init(5, true, 77, 54);
            iRecipeLayout.getItemStacks().set(5, new ItemStack(spiritFurnaceRecipe.getItems().get(5)));
        }
        if (spiritFurnaceRecipe.getItems().get(6) != Items.AIR)
        {
            iRecipeLayout.getItemStacks().init(6, true, 53, 54);
            iRecipeLayout.getItemStacks().set(6, new ItemStack(spiritFurnaceRecipe.getItems().get(6)));
        }
        if (spiritFurnaceRecipe.getItems().get(7) != Items.AIR)
        {
            iRecipeLayout.getItemStacks().init(7, true, 101, 30);
            iRecipeLayout.getItemStacks().set(7, new ItemStack(spiritFurnaceRecipe.getItems().get(7)));
        }
        if (spiritFurnaceRecipe.getData() != null)
        {
            ItemStack stack = new ItemStack(ModItems.jei_spirit);
            CompoundNBT nbt = new CompoundNBT();
            spiritFurnaceRecipe.getData().writeSpiritDataIntoNBT(nbt);
            stack.setTag(nbt);
            iRecipeLayout.getItemStacks().init(8, true, 2, 55);
            iRecipeLayout.getItemStacks().set(8, stack);
        }
        if (spiritFurnaceRecipe.getCatalyst() != null)
        {
            iRecipeLayout.getItemStacks().init(9, true, 2, 30);
            iRecipeLayout.getItemStacks().set(9, new ItemStack(spiritFurnaceRecipe.getCatalyst()));
        }
        if (spiritFurnaceRecipe.getOutputStack() != null)
        {
            iRecipeLayout.getItemStacks().init(10, true, 153, 30);
            iRecipeLayout.getItemStacks().set(10, spiritFurnaceRecipe.getOutputStack());
        }
    }
}
