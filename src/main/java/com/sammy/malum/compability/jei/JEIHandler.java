package com.sammy.malum.compability.jei;

import com.google.common.collect.Maps;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.common.spiritrite.TotemicRiteType;
import com.sammy.malum.compability.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.compability.jei.categories.*;
import com.sammy.malum.compability.jei.recipes.SpiritTransmutationWrapper;
import com.sammy.malum.core.handlers.hiding.HiddenTagHandler;
import com.sammy.malum.registry.client.HiddenTagRegistry;
import com.sammy.malum.registry.common.SpiritRiteRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.compress.utils.Lists;
import team.lodestar.lodestone.systems.recipe.IRecipeComponent;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;


@JeiPlugin
public class JEIHandler implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(MalumMod.MALUM, "main");

    public static final RecipeType<SpiritInfusionRecipe> SPIRIT_INFUSION = new RecipeType<>(SpiritInfusionRecipeCategory.UID, SpiritInfusionRecipe.class);
    public static final RecipeType<SpiritTransmutationWrapper> TRANSMUTATION = new RecipeType<>(SpiritTransmutationRecipeCategory.UID, SpiritTransmutationWrapper.class);
    public static final RecipeType<SpiritFocusingRecipe> FOCUSING = new RecipeType<>(SpiritFocusingRecipeCategory.UID, SpiritFocusingRecipe.class);
    public static final RecipeType<TotemicRiteType> RITES = new RecipeType<>(SpiritRiteRecipeCategory.UID, TotemicRiteType.class);
    public static final RecipeType<SpiritRepairRecipe> SPIRIT_REPAIR = new RecipeType<>(SpiritRepairRecipeCategory.UID, SpiritRepairRecipe.class);
    public static final RecipeType<FavorOfTheVoidRecipe> WEEPING_WELL = new RecipeType<>(WeepingWellRecipeCategory.UID, FavorOfTheVoidRecipe.class);
    public static final RecipeType<RunicWorkbenchRecipe> RUNEWORKING = new RecipeType<>(RuneworkingRecipeCategory.UID, RunicWorkbenchRecipe.class);

    public JEIHandler() {
        HiddenTagRegistry.blankOutHidingTags();
    }

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
            iRecipeLayout.addSlot(role, oLeft, oTop).addItemStacks(components.get(i).getStacks());
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(new SpiritInfusionRecipeCategory(guiHelper),
            new SpiritTransmutationRecipeCategory(guiHelper),
            new SpiritFocusingRecipeCategory(guiHelper),
            new SpiritRiteRecipeCategory(guiHelper),
            new SpiritRepairRecipeCategory(guiHelper),
            new RuneworkingRecipeCategory(guiHelper),
            new WeepingWellRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            registry.addRecipes(SPIRIT_INFUSION, SpiritInfusionRecipe.getRecipes(level));

            List<SpiritTransmutationRecipe> transmutation = SpiritTransmutationRecipe.getRecipes(level);
            List<SpiritTransmutationRecipe> leftovers = Lists.newArrayList();
            Map<String, List<SpiritTransmutationRecipe>> groups = Maps.newLinkedHashMap();
            for (SpiritTransmutationRecipe recipe : transmutation) {
                if (recipe.group != null) {
                    var group = groups.computeIfAbsent(recipe.group, k -> Lists.newArrayList());
                    group.add(recipe);
                } else
                    leftovers.add(recipe);
            }

            registry.addRecipes(TRANSMUTATION, groups.values().stream()
                .map(list -> list.stream().filter(it -> !it.output.isEmpty() && !it.ingredient.isEmpty()).collect(Collectors.toList()))
                .map(SpiritTransmutationWrapper::new)
                .collect(Collectors.toList()));
            registry.addRecipes(TRANSMUTATION, leftovers.stream()
                .filter(it -> !it.output.isEmpty() && !it.ingredient.isEmpty())
                .map(List::of)
                .map(SpiritTransmutationWrapper::new)
                .collect(Collectors.toList()));

            registry.addRecipes(FOCUSING, SpiritFocusingRecipe.getRecipes(level).stream()
                .filter(it -> !it.output.isEmpty()).collect(Collectors.toList()));
            registry.addRecipes(RITES, SpiritRiteRegistry.RITES);
            registry.addRecipes(SPIRIT_REPAIR, SpiritRepairRecipe.getRecipes(level).stream()
                .filter(it -> !it.inputs.isEmpty()).collect(Collectors.toList()));
            registry.addRecipes(WEEPING_WELL, FavorOfTheVoidRecipe.getRecipes(level).stream()
                .filter(it -> !it.output.isEmpty()).collect(Collectors.toList()));
            registry.addRecipes(RUNEWORKING, RunicWorkbenchRecipe.getRecipes(level).stream()
                .filter(it -> !it.output.isEmpty()).collect(Collectors.toList()));
            if (FarmersDelightCompat.LOADED) {
                FarmersDelightCompat.LoadedOnly.addInfo(registry);
            }
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SPIRIT_ALTAR.get()), SPIRIT_INFUSION);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SPIRIT_CRUCIBLE.get()), FOCUSING);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.REPAIR_PYLON.get()), SPIRIT_REPAIR);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.RUNEWOOD_TOTEM_BASE.get()), RITES);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.SOULWOOD_TOTEM_BASE.get()), TRANSMUTATION);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.RUNIC_WORKBENCH.get()), RUNEWORKING);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistry.VOID_DEPOT.get()), WEEPING_WELL);
    }

    private static final Map<RecipeType<?>, HiddenRecipeSet<?>> hiddenRecipeSets = new HashMap<>();
    private static final List<UUID> callbacks = new ArrayList<>();
    private static final Set<ItemStack> hiddenStacks = new LinkedHashSet<>();

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IRecipeManager recipeRegistry = jeiRuntime.getRecipeManager();
        IIngredientManager ingredientManager = jeiRuntime.getIngredientManager();
        IJeiHelpers helpers = jeiRuntime.getJeiHelpers();
        IFocusFactory focusFactory = helpers.getFocusFactory();

        HiddenTagRegistry.rebuildHidingTags();

        callbacks.add(HiddenTagHandler.registerHiddenItemListener(() -> {
            var output = HiddenTagHandler.tagsToHide();

            if (!hiddenStacks.isEmpty()) {
                ingredientManager.addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, hiddenStacks);
                hiddenStacks.clear();
            }

            if (!output.isEmpty()) {
                Collection<ItemStack> ingredients = ingredientManager.getAllIngredients(VanillaTypes.ITEM_STACK);

                for (ItemStack stack : ingredients) {
                    if (output.stream().anyMatch(stack::is)) {
                        hiddenStacks.add(stack);
                    }
                }

                if (!hiddenStacks.isEmpty())
                    ingredientManager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, hiddenStacks);
            }

            helpers.getAllRecipeTypes().forEach(it -> {
                HiddenRecipeSet<?> hiddenRecipes = hiddenRecipeSets.computeIfAbsent(it, HiddenRecipeSet::createSet);

                hiddenRecipes.unhidePreviouslyHiddenRecipes(recipeRegistry);
                if (!output.isEmpty())
                    hiddenRecipes.scanAndHideRecipes(recipeRegistry, focusFactory, output);
            });
        }));
    }

    @Override
    public void onRuntimeUnavailable() {
        callbacks.forEach(HiddenTagHandler::removeListener);
        callbacks.clear();
        hiddenRecipeSets.clear();
        hiddenStacks.clear();
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
