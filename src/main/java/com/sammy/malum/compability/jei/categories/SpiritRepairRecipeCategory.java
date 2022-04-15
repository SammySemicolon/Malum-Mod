package com.sammy.malum.compability.jei.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.core.helper.DataHelper.prefix;

public class SpiritRepairRecipeCategory implements IRecipeCategory<SpiritRepairRecipe> {

    public static final ResourceLocation UID = prefix("spirit_repair");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public SpiritRepairRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 185);
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MODID, "textures/gui/spirit_repair_jei.png"), 0, 0, 142, 183);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ItemRegistry.SPIRIT_CRUCIBLE.get()));
    }

    @Override
    public void draw(SpiritRepairRecipe recipe, PoseStack poseStack, double mouseX, double mouseY) {
        overlay.draw(poseStack);
        if (recipe.spirits.size() > 0) {
            ProgressionBookScreen.renderItemFrames(poseStack, 61, 12, false, recipe.spirits.size());
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends SpiritRepairRecipe> getRecipeClass() {
        return SpiritRepairRecipe.class;
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
    public void setIngredients(SpiritRepairRecipe recipe, IIngredients iIngredients) {
        ArrayList<ItemStack> items = (ArrayList<ItemStack>) recipe.inputs.stream().map(Item::getDefaultInstance).collect(Collectors.toList());
        recipe.spirits.forEach(i -> items.add(i.getStack()));
        items.addAll(recipe.repairMaterial.getStacks());
        iIngredients.setInputs(VanillaTypes.ITEM, items);
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, SpiritRepairRecipe recipe, IIngredients iIngredients) {
        int index = 0;
        List<ItemStack> repaired = recipe.inputs.stream().map(Item::getDefaultInstance).collect(Collectors.toList());
        ArrayList<ItemStack> repairIngredient = recipe.repairMaterial.getStacks();

        ArrayList<ItemStack> damaged = repaired.stream()
                .map(ItemStack::copy)
                .peek(s -> s.setDamageValue((int) (s.getMaxDamage() * recipe.durabilityPercentage)))
                .collect(Collectors.toCollection(ArrayList::new));

        index = ProgressionBookScreen.addItemsToJei(iRecipeLayout, 61, 12, false, recipe.spirits, index);

        iRecipeLayout.getItemStacks().init(index + 1, true, 81, 56);
        iRecipeLayout.getItemStacks().set(index + 1, damaged);

        iRecipeLayout.getItemStacks().init(index + 2, true, 62, 123);
        iRecipeLayout.getItemStacks().set(index + 2, repaired.stream().map(SpiritRepairRecipe::getRepairRecipeOutput).collect(Collectors.toList()));

        iRecipeLayout.getItemStacks().init(index + 3, true, 43, 56);
        iRecipeLayout.getItemStacks().set(index + 3, repairIngredient);
    }
}