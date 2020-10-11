package com.sammy.malum.integration.jei;

import com.sammy.malum.MalumMod;
import com.sammy.malum.init.ModItems;
import com.sammy.malum.init.ModRecipes;
import com.sammy.malum.integration.jei.spiritFurnace.SpiritFurnaceFuelDataCategory;
import com.sammy.malum.integration.jei.spiritFurnace.SpiritFurnaceRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

@JeiPlugin
public class JEIHandler implements IModPlugin
{
    private static final ResourceLocation ID = new ResourceLocation(MalumMod.MODID, "main");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(
                new SpiritFurnaceRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new SpiritFurnaceFuelDataCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry)
    {
        registry.addRecipes(ModRecipes.spiritFurnaceRecipes, SpiritFurnaceRecipeCategory.UID);
        registry.addRecipes(ModRecipes.spiritFurnaceFuelData, SpiritFurnaceFuelDataCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
    {
        registry.addRecipeCatalyst(new ItemStack(ModItems.spirit_furnace), SpiritFurnaceRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModItems.spirit_furnace), SpiritFurnaceFuelDataCategory.UID);
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid()
    {
        return ID;
    }
}
