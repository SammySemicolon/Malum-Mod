package com.sammy.malum.compability.jei;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.BlockTransmutationRecipe;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.core.setup.content.SpiritRiteRegistry;
import com.sammy.malum.core.setup.item.ItemRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

@JeiPlugin
public class JEIHandler implements IModPlugin
{
    private static final ResourceLocation ID = new ResourceLocation(MalumMod.MODID, "main");
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(new SpiritInfusionRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SpiritRiteRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new BlockTransmutationRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SpiritFocusingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }
    
    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry)
    {
        registry.addRecipes(SpiritInfusionRecipe.getRecipes(Minecraft.getInstance().level), SpiritInfusionRecipeCategory.UID);
        registry.addRecipes(SpiritRiteRegistry.RITES, SpiritRiteRecipeCategory.UID);
        registry.addRecipes(BlockTransmutationRecipe.getRecipes(Minecraft.getInstance().level), BlockTransmutationRecipeCategory.UID);
        registry.addRecipes(SpiritFocusingRecipe.getRecipes(Minecraft.getInstance().level), SpiritFocusingRecipeCategory.UID);
    }
    
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
    {
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SPIRIT_ALTAR.get()), SpiritInfusionRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.RUNEWOOD_TOTEM_BASE.get()), SpiritRiteRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SOULWOOD_TOTEM_BASE.get()), BlockTransmutationRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SPIRIT_CRUCIBLE.get()), SpiritFocusingRecipeCategory.UID);
    }
    @Nonnull
    @Override
    public ResourceLocation getPluginUid()
    {
        return ID;
    }
}