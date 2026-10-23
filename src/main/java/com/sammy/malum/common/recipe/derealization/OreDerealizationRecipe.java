package com.sammy.malum.common.recipe.derealization;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.common.data.component.soulstone.StoredInSoulstoneMetal;
import com.sammy.malum.common.recipe.CrystalPropertyModifier;
import com.sammy.malum.registry.common.recipe.MalumRecipeSerializers;
import com.sammy.malum.registry.common.recipe.MalumRecipeTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import team.lodestar.lodestone.modules.toolkit.recipe.LodestoneInWorldRecipe;

public class OreDerealizationRecipe extends LodestoneInWorldRecipe<RuleTestRecipeInput> {

    public static final MapCodec<OreDerealizationRecipe> CODEC = RecordCodecBuilder.mapCodec((obj) -> obj.group(
            RuleTest.CODEC.fieldOf("conditions").forGetter(recipe -> recipe.input),
            BlockState.CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
            CrystalPropertyModifier.CODEC.fieldOf("crystal_to_grow").forGetter(recipe -> recipe.crystalToGrow),
            StoredInSoulstoneMetal.CODEC.fieldOf("metal_data").forGetter(recipe -> recipe.metalData)
    ).apply(obj, OreDerealizationRecipe::new));

    public static final String NAME = "ore_derealization";
    private final RuleTest input; //TODO this should be a list later
    private final BlockState output;
    private final CrystalPropertyModifier crystalToGrow;
    private final StoredInSoulstoneMetal metalData;

    public OreDerealizationRecipe(RuleTest input, BlockState output, CrystalPropertyModifier crystalToGrow, StoredInSoulstoneMetal metalData) {
        super(MalumRecipeSerializers.ORE_DEREALIZATION_RECIPE_SERIALIZER.get(), MalumRecipeTypes.ORE_DEREALIZATION.get());
        this.input = input;
        this.output = output;
        this.crystalToGrow = crystalToGrow;
        this.metalData = metalData;
    }

    @Override
    public boolean matches(RuleTestRecipeInput input, Level level) {
        return input.test(this.input);
    }

    public RuleTest getInput() {
        return input;
    }

    public BlockState getOutput() {
        return output;
    }

    public CrystalPropertyModifier getCrystalToGrow() {
        return crystalToGrow;
    }

    public StoredInSoulstoneMetal getMetalData() {
        return metalData;
    }
}
