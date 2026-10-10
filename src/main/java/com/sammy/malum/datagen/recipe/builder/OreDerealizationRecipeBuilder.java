package com.sammy.malum.datagen.recipe.builder;

import com.sammy.malum.common.data.component.soulstone.StoredInSoulstoneMetal;
import com.sammy.malum.common.recipe.CrystalPropertyModifier;
import com.sammy.malum.common.recipe.derealization.OreDerealizationRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import team.lodestar.lodestone.modules.toolkit.recipe.LodestoneRecipeBuilder;

public class OreDerealizationRecipeBuilder implements LodestoneRecipeBuilder<OreDerealizationRecipe> {
    private final RuleTest input;
    private final BlockState output;
    private final CrystalPropertyModifier crystalToGrow;
    private final StoredInSoulstoneMetal metalData;

    public OreDerealizationRecipeBuilder(RuleTest input, BlockState output, CrystalPropertyModifier crystalToGrow, StoredInSoulstoneMetal metalData) {
        this.input = input;
        this.output = output;
        this.crystalToGrow = crystalToGrow;
        this.metalData = metalData;
    }

    @Override
    public OreDerealizationRecipe buildRecipe(ResourceLocation id) {
        return new OreDerealizationRecipe(this.input, this.output, this.crystalToGrow, this.metalData);
    }

    public void save(RecipeOutput output) {
        this.save(output, BuiltInRegistries.BLOCK.getKey(this.output.getBlock()));
    }

    @Override
    public String getRecipeSubfolder() {
        return "ore_derealization";
    }
}
