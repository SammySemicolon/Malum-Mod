package com.sammy.malum.common.integration.jei;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.modcontent.MalumRuneTableRecipes;
import com.sammy.malum.core.modcontent.MalumSpiritAltarRecipes;
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
        registry.addRecipeCategories(new SpiritAltarRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SpiritRitesRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new RuneTableRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }
    
    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry)
    {
        registry.addRecipes(MalumSpiritAltarRecipes.RECIPES, SpiritAltarRecipeCategory.UID);
        registry.addRecipes(MalumRites.RITES, SpiritRitesRecipeCategory.UID);
        registry.addRecipes(MalumRuneTableRecipes.RECIPES, RuneTableRecipeCategory.UID);
    }
    
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
    {
        //registry.addRecipeCatalyst(new ItemStack(MalumItems.TOTEM_CORE.get()), RiteRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(MalumItems.SPIRIT_ALTAR.get()), SpiritAltarRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(MalumItems.TOTEM_BASE.get()), SpiritRitesRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(MalumItems.RUNE_TABLE.get()), RuneTableRecipeCategory.UID);
    }
    @Nonnull
    @Override
    public ResourceLocation getPluginUid()
    {
        return ID;
    }
}