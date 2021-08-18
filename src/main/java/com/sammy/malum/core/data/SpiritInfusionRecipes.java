package com.sammy.malum.core.data;

import com.sammy.malum.core.data.builder.SpiritInfusionRecipeBuilder;
import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;

import java.util.function.Consumer;

import static com.sammy.malum.core.init.MalumSpiritTypes.*;

public class SpiritInfusionRecipes extends RecipeProvider
{
    public SpiritInfusionRecipes(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    public String getName()
    {
        return "Malum Spirit Infusion Recipe Provider";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        new SpiritInfusionRecipeBuilder(Items.GUNPOWDER, 1, MalumItems.HEX_ASH.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.COBBLESTONE, 16, MalumItems.TAINTED_ROCK.get(), 16)
                .addSpirit(SACRED_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.COBBLESTONE, 16, MalumItems.TWISTED_ROCK.get(), 16)
                .addSpirit(WICKED_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.GLOWSTONE_DUST, 4, MalumItems.ETHER.get(), 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addExtraItem(MalumItems.BLAZING_QUARTZ.get(), 2)
                .addExtraItem(Items.BLAZE_POWDER, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.GOLD_INGOT, 1, MalumItems.HALLOWED_GOLD_INGOT.get(), 1)
                .addExtraItem(Items.QUARTZ, 2)
                .addExtraItem(MalumItems.SOULSTONE.get(), 1)
                .addSpirit(SACRED_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_INGOT, 1, MalumItems.SOUL_STAINED_STEEL_INGOT.get(), 1)
                .addExtraItem(Items.SOUL_SAND, 2)
                .addExtraItem(MalumItems.SOULSTONE.get(), 1)
                .addSpirit(WICKED_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

    }
}