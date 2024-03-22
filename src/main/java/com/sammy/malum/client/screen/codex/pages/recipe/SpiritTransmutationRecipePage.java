package com.sammy.malum.client.screen.codex.pages.recipe;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.common.recipe.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraftforge.data.loading.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritTransmutationRecipePage<T extends AbstractProgressionCodexScreen<T>>  extends BookPage<T> {
    private final String headlineTranslationKey;
    private final List<SpiritTransmutationRecipe> recipes;

    public SpiritTransmutationRecipePage(String headlineTranslationKey, Predicate<SpiritTransmutationRecipe> predicate) {
        super(MalumMod.malumPath("textures/gui/book/pages/transmutation_recipe_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.recipes = DatagenModLoader.isRunningDataGen() ? null : new ArrayList<>();
        if (!DatagenModLoader.isRunningDataGen()) {
            final ClientLevel level = Minecraft.getInstance().level;
            final SpiritTransmutationRecipe recipe = SpiritTransmutationRecipe.getRecipe(level, predicate);
            if (recipe != null) {
                recipes.add(recipe);
            }
            if (recipe.group != null) {
                for (SpiritTransmutationRecipe otherRecipe : SpiritTransmutationRecipe.getRecipes(level)) {
                    if (!recipe.equals(otherRecipe) && recipe.group.equals(otherRecipe.group)) {
                        recipes.add(otherRecipe);
                    }
                }
            }
        }
    }

    public SpiritTransmutationRecipePage(String headlineTranslationKey, SpiritTransmutationRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/transmutation_recipe_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.recipes = new ArrayList<>(List.of(recipe));
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    public static<T extends AbstractProgressionCodexScreen<T>> SpiritTransmutationRecipePage<T> fromInput(String headlineTranslationKey, Item inputItem) {
        return new SpiritTransmutationRecipePage<>(headlineTranslationKey, s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }

    public static<T extends AbstractProgressionCodexScreen<T>> SpiritTransmutationRecipePage<T> fromOutput(String headlineTranslationKey, Item outputItem) {
        return new SpiritTransmutationRecipePage<>(headlineTranslationKey, s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }

    @Override
    public boolean isValid() {
        return recipes != null;
    }

    @Override
    public void render(EntryScreen<T> screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, left + 70 - Minecraft.getInstance().font.width(component.getString()) / 2, top + 5);

        SpiritTransmutationRecipe recipe = recipes.get(getIndex());
        renderItem(screen, guiGraphics, recipe.ingredient, left + 63, top + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, left + 63, top + 126, mouseX, mouseY);
    }

    public int getIndex() {
        return (int) (Minecraft.getInstance().level.getGameTime() % (20L * recipes.size()) / 20);
    }
}
