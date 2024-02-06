package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.common.recipe.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraftforge.data.loading.*;

import java.util.function.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

@SuppressWarnings("all")
public class VoidTransmutationPage extends BookPage {
    private final String headlineTranslationKey;
    private final FavorOfTheVoidRecipe recipe;

    public VoidTransmutationPage(String headlineTranslationKey, Predicate<FavorOfTheVoidRecipe> predicate) {
        this(headlineTranslationKey, DatagenModLoader.isRunningDataGen() ? null : FavorOfTheVoidRecipe.getRecipe(Minecraft.getInstance().level, predicate));
    }

    public VoidTransmutationPage(String headlineTranslationKey, FavorOfTheVoidRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/weeping_well_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.recipe = recipe;
    }

    public static VoidTransmutationPage fromInput(String headlineTranslationKey, Item inputItem) {
        return new VoidTransmutationPage(headlineTranslationKey, s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }

    public static VoidTransmutationPage fromOutput(String headlineTranslationKey, Item outputItem) {
        return new VoidTransmutationPage(headlineTranslationKey, s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }

    public String headlineTranslationKey() {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    @Override
    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, guiLeft + 75 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        renderItem(screen, guiGraphics, recipe.input, guiLeft + 67, guiTop + 44, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, guiLeft + 67, guiTop + 110, mouseX, mouseY);
    }

    @Override
    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = Component.translatable(headlineTranslationKey());
        renderText(guiGraphics, component, guiLeft + 75 - minecraft.font.width(component.getString()) / 2, guiTop + 10);
        renderItem(screen, guiGraphics, recipe.input, guiLeft + 209, guiTop + 44, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, guiLeft + 209, guiTop + 110, mouseX, mouseY);
    }
}
