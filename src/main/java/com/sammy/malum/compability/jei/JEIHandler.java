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
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.ortus.systems.recipe.IRecipeComponent;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;


@JeiPlugin
public class JEIHandler implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(MalumMod.MALUM, "main");

    public static final RecipeType<SpiritInfusionRecipe> SPIRIT_INFUSION =
         new RecipeType<>(SpiritInfusionRecipeCategory.UID, SpiritInfusionRecipe.class);
    public static final RecipeType<BlockTransmutationRecipe> TRANSMUTATION =
         new RecipeType<>(BlockTransmutationRecipeCategory.UID, BlockTransmutationRecipe.class);
    public static final RecipeType<SpiritFocusingRecipe> FOCUSING =
         new RecipeType<>(SpiritFocusingRecipeCategory.UID, SpiritFocusingRecipe.class);
    public static final RecipeType<MalumRiteType> RITES =
         new RecipeType<>(SpiritRiteRecipeCategory.UID, MalumRiteType.class);
    public static final RecipeType<SpiritRepairRecipe> SPIRIT_REPAIR =
         new RecipeType<>(SpiritRepairRecipeCategory.UID, SpiritRepairRecipe.class);

    public static void addItemsToJei(IRecipeLayoutBuilder iRecipeLayout, RecipeIngredientRole role, int left, int top, boolean vertical, List<? extends IRecipeComponent> components) {
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
            iRecipeLayout.addSlot(role, oLeft, oTop).addItemStack(stack);
        }
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
        if (level != null) {
            registry.addRecipes(SPIRIT_INFUSION, SpiritInfusionRecipe.getRecipes(level));
            registry.addRecipes(TRANSMUTATION, BlockTransmutationRecipe.getRecipes(level));
            registry.addRecipes(FOCUSING, SpiritFocusingRecipe.getRecipes(level));
            registry.addRecipes(RITES, SpiritRiteRegistry.RITES);
            registry.addRecipes(SPIRIT_REPAIR, SpiritRepairRecipe.getRecipes(level));
            if (FarmersDelightCompat.LOADED) {
                FarmersDelightCompat.LoadedOnly.addInfo(registry);
            }
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SPIRIT_ALTAR.get()), SPIRIT_INFUSION);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SPIRIT_CRUCIBLE.get()), FOCUSING, SPIRIT_REPAIR);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.RUNEWOOD_ITEM_PEDESTAL.get()), SPIRIT_INFUSION);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.RUNEWOOD_ITEM_STAND.get()), SPIRIT_INFUSION);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.TWISTED_TABLET.get()), SPIRIT_INFUSION, SPIRIT_REPAIR);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.RUNEWOOD_TOTEM_BASE.get()), RITES);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SOULWOOD_TOTEM_BASE.get()), TRANSMUTATION);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IRecipeManager recipeRegistry = jeiRuntime.getRecipeManager();
        IFocusFactory focusFactory = jeiRuntime.getJeiHelpers().getFocusFactory();
        recipeRegistry.hideRecipes(RecipeTypes.CRAFTING, recipeRegistry
             .createRecipeLookup(RecipeTypes.CRAFTING)
             .limitFocus(List.of(focusFactory.createFocus(RecipeIngredientRole.OUTPUT, VanillaTypes.ITEM_STACK, new ItemStack(ItemRegistry.THE_DEVICE.get()))))
             .get().toList());
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
