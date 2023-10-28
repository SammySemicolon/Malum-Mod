package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.EntryScreen;
import com.sammy.malum.common.recipe.SpiritTransmutationRecipe;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.data.loading.DatagenModLoader;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.recipe.WrappedIngredient;

import java.util.ArrayList;
import java.util.List;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

@SuppressWarnings("all")
public class SpiritTransmutationPage extends BookPage {
    private final String headlineTranslationKey;
    private final List<WrappedIngredient> itemTree = new ArrayList<>();

    public SpiritTransmutationPage(String headlineTranslationKey, Item start) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_transmutation_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        if (DatagenModLoader.isRunningDataGen()) {
            return;
        }
        SpiritTransmutationRecipe recipe = SpiritTransmutationRecipe.getRecipe(Minecraft.getInstance().level, start);
        while (true) {
            if (recipe == null) {
                itemTree.add(new WrappedIngredient(Ingredient.of(ItemRegistry.BLIGHTED_SOIL.get())));
                break;
            }
            itemTree.add(new WrappedIngredient(recipe.ingredient));
            ItemStack output = recipe.output;
            recipe = SpiritTransmutationRecipe.getRecipe(Minecraft.getInstance().level, s -> s.ingredient.test(output));
        }
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    @Override
    public boolean isValid() {
        return !itemTree.isEmpty();
    }

    @Override
    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, guiLeft + 75 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        List<WrappedIngredient> copy = new ArrayList<>(itemTree);
        renderComponent(screen, guiGraphics, copy.remove(0), guiLeft + 67, guiTop + 44, mouseX, mouseY);
        renderComponent(screen, guiGraphics, copy.remove(copy.size() - 1), guiLeft + 67, guiTop + 126, mouseX, mouseY);
        renderComponents(screen, guiGraphics, copy, guiLeft + 65, guiTop + 82, mouseX, mouseY, false);
    }

    @Override
    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, guiLeft + 218 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        List<WrappedIngredient> copy = new ArrayList<>(itemTree);
        renderComponent(screen, guiGraphics, copy.remove(0), guiLeft + 209, guiTop + 44, mouseX, mouseY);
        renderComponent(screen, guiGraphics, copy.remove(copy.size() - 1), guiLeft + 209, guiTop + 126, mouseX, mouseY);
        renderComponents(screen, guiGraphics, DataHelper.reverseOrder(new ArrayList<>(), copy), guiLeft + 207, guiTop + 82, mouseX, mouseY, false);
    }
}