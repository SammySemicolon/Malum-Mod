package com.sammy.malum.compability.jei;

import com.google.common.collect.Maps;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import com.sammy.malum.common.recipe.SpiritTransmutationRecipe;
import com.sammy.malum.common.spiritrite.TotemicRiteType;
import com.sammy.malum.compability.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.compability.jei.categories.*;
import com.sammy.malum.compability.jei.recipes.SpiritTransmutationWrapper;
import com.sammy.malum.core.handlers.HiddenItemHandler;
import com.sammy.malum.registry.common.SpiritRiteRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.*;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
                new SpiritRepairRecipeCategory(guiHelper));
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
    }

    private static final Map<RecipeType<?>, HiddenRecipeSet<?>> hiddenRecipeSets = new HashMap<>();
    private static List<Item> lastHiddenItems = new ArrayList<>();
    private static final List<UUID> callbacks = new ArrayList<>();
    private static final Map<Item, Collection<ItemStack>> hiddenStacks = new HashMap<>();

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IRecipeManager recipeRegistry = jeiRuntime.getRecipeManager();
        IIngredientManager ingredientManager = jeiRuntime.getIngredientManager();
        IFocusFactory focusFactory = jeiRuntime.getJeiHelpers().getFocusFactory();
        recipeRegistry.hideRecipes(RecipeTypes.CRAFTING, recipeRegistry
                .createRecipeLookup(RecipeTypes.CRAFTING)
                .limitFocus(List.of(focusFactory.createFocus(RecipeIngredientRole.OUTPUT, VanillaTypes.ITEM_STACK, new ItemStack(ItemRegistry.THE_DEVICE.get()))))
                .get().toList());

        callbacks.add(HiddenItemHandler.registerHiddenItemListener(() -> {
            var output = HiddenItemHandler.computeHiddenItems(lastHiddenItems);
            lastHiddenItems = output.toHide();

            if (!output.toHide().isEmpty()) {
                Collection<ItemStack> ingredients = ingredientManager.getAllIngredients(VanillaTypes.ITEM_STACK);
                Set<ItemStack> hiding = new HashSet<>();

                for (ItemStack stack : ingredients) {
                    if (output.toHide().contains(stack.getItem())) {
                        hiding.add(stack);
                        hiddenStacks.computeIfAbsent(stack.getItem(), (it) -> new HashSet<>()).add(stack);
                    }
                }

                ingredientManager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, hiding);
            }

            if (!output.toUnhide().isEmpty()) {
                Set<ItemStack> unhiding = new HashSet<>();
                for (Item item : output.toUnhide()) {
                    if (hiddenStacks.containsKey(item)) {
                        unhiding.addAll(hiddenStacks.get(item));
                        hiddenStacks.remove(item);
                    }
                }
                ingredientManager.addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, unhiding);
            }

            IRecipeCategoriesLookup lookup = recipeRegistry.createRecipeCategoryLookup();
            lookup.get().forEach(it -> {
                HiddenRecipeSet<?> hiddenRecipes = hiddenRecipeSets.computeIfAbsent(it.getRecipeType(), HiddenRecipeSet::createSet);

                if (!output.toUnhide().isEmpty())
                    hiddenRecipes.unhideRecipesThatAreNowShown(recipeRegistry, output.toUnhide());
                if (!output.toHide().isEmpty())
                    hiddenRecipes.scanForHiddenRecipes(recipeRegistry, focusFactory, output.toHide());
            });
        }));
    }

    @Override
    public void onRuntimeUnavailable() {
        callbacks.forEach(HiddenItemHandler::removeListener);
        callbacks.clear();
        hiddenRecipeSets.clear();
        lastHiddenItems.clear();
        hiddenStacks.clear();
    }

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
