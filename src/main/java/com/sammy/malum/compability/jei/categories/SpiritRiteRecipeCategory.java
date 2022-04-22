package com.sammy.malum.compability.jei.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;

import static com.sammy.malum.core.helper.DataHelper.prefix;

public class SpiritRiteRecipeCategory implements IRecipeCategory<MalumRiteType> {
    public static final ResourceLocation UID = prefix("spirit_rite");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;
    private final Font font;

    public SpiritRiteRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 185);
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/spirit_rite_jei.png"), 0, 0, 142, 183);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ItemRegistry.RUNEWOOD_TOTEM_BASE.get()));
        font = Minecraft.getInstance().font;
    }

    @Override
    public void draw(MalumRiteType rite, PoseStack poseStack, double mouseX, double mouseY) {
        overlay.draw(poseStack);
        ProgressionBookScreen.renderText(poseStack, new TranslatableComponent(rite.translationIdentifier()), 106 - font.width(rite.translationIdentifier()) / 2, 160);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends MalumRiteType> getRecipeClass() {
        return MalumRiteType.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("malum.jei." + UID.getPath());
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(MalumRiteType rite, IIngredients iIngredients) {
        ArrayList<ItemStack> items = new ArrayList<>();
        rite.spirits.forEach(spirit -> items.add(spirit.getSplinterItem().getDefaultInstance()));
        iIngredients.setInputs(VanillaTypes.ITEM, items);
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MalumRiteType rite, IIngredients iIngredients) {
        for (int i = 0; i < rite.spirits.size(); i++) {
            iRecipeLayout.getItemStacks().init(i, true, 62, 120 - 20 * i);
            iRecipeLayout.getItemStacks().set(i, rite.spirits.get(i).getSplinterItem().getDefaultInstance());
        }
    }
}