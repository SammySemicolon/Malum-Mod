package com.sammy.malum.compability.jei;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.BlockTransmutationRecipe;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import com.sammy.malum.compability.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.compability.jei.categories.*;
import com.sammy.malum.core.setup.content.SpiritRiteRegistry;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.ortus.systems.recipe.IRecipeComponent;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.Nonnull;

import java.util.List;

import static com.sammy.malum.MalumMod.prefix;


@JeiPlugin
public class JEIHandler implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(MalumMod.MALUM, "main");

    public static int addItemsToJei(IRecipeLayout iRecipeLayout, int left, int top, boolean vertical, List<? extends IRecipeComponent> components, int baseIndex) {
        int slots = components.size();
        if (vertical) {
            top -= 10 * (slots - 1);
        } else {
            left -= 10 * (slots - 1);
        }
        for (int i = 0; i < slots; i++) {
            int offset = i * 20;
            int oLeft = left + 1 + (vertical ? 0 : offset);
            int oTop = top + 1 + (vertical ? offset : 0);
            ItemStack stack = components.get(i).getStack();
            iRecipeLayout.getItemStacks().init(baseIndex + i, true, oLeft, oTop);
            iRecipeLayout.getItemStacks().set(baseIndex + i, stack);
        }
        return baseIndex + components.size() + 1;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new SpiritInfusionRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new BlockTransmutationRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SpiritFocusingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SpiritRiteRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SpiritRepairRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry) {
        ClientLevel level = Minecraft.getInstance().level;
        registry.addRecipes(SpiritInfusionRecipe.getRecipes(level), SpiritInfusionRecipeCategory.UID);
        registry.addRecipes(BlockTransmutationRecipe.getRecipes(level), BlockTransmutationRecipeCategory.UID);
        registry.addRecipes(SpiritFocusingRecipe.getRecipes(level), SpiritFocusingRecipeCategory.UID);
        registry.addRecipes(SpiritRiteRegistry.RITES, SpiritRiteRecipeCategory.UID);
        registry.addRecipes(SpiritRepairRecipe.getRecipes(level), SpiritRepairRecipeCategory.UID);
        if (FarmersDelightCompat.LOADED) {
            FarmersDelightCompat.LoadedOnly.addInfo(registry);
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SPIRIT_ALTAR.get()), SpiritInfusionRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.RUNEWOOD_TOTEM_BASE.get()), SpiritRiteRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SOULWOOD_TOTEM_BASE.get()), BlockTransmutationRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SPIRIT_CRUCIBLE.get()), SpiritFocusingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SPIRIT_CRUCIBLE.get()), SpiritRepairRecipeCategory.UID);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IRecipeManager recipeRegistry = jeiRuntime.getRecipeManager();
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        recipeManager.byKey(prefix("the_device"))
                .ifPresent(r -> recipeRegistry.hideRecipe(r, VanillaRecipeCategoryUid.CRAFTING));
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}