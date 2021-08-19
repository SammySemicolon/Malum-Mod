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

        new SpiritInfusionRecipeBuilder(MalumItems.CRUDE_SCYTHE.get(), 1, MalumItems.SOUL_STAINED_STEEL_SCYTHE.get(), 1)
                .addExtraItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(MalumItems.HEX_ASH.get(), 2)
                .addExtraItem(MalumItems.SOULSTONE.get(), 4)
                .addSpirit(WICKED_SPIRIT, 3)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_HELMET, 1, MalumItems.SOUL_STAINED_STEEL_HELMET.get(), 1)
                .addExtraItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(MalumItems.HEX_ASH.get(), 1)
                .addExtraItem(MalumItems.SOULSTONE.get(), 2)
                .addSpirit(WICKED_SPIRIT, 3)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addSpirit(EARTHEN_SPIRIT, 5)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_CHESTPLATE, 1, MalumItems.SOUL_STAINED_STEEL_CHESTPLATE.get(), 1)
                .addExtraItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(MalumItems.HEX_ASH.get(), 1)
                .addExtraItem(MalumItems.SOULSTONE.get(), 2)
                .addSpirit(WICKED_SPIRIT, 3)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addSpirit(EARTHEN_SPIRIT, 5)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_LEGGINGS, 1, MalumItems.SOUL_STAINED_STEEL_LEGGINGS.get(), 1)
                .addExtraItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(MalumItems.HEX_ASH.get(), 1)
                .addExtraItem(MalumItems.SOULSTONE.get(), 2)
                .addSpirit(WICKED_SPIRIT, 3)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addSpirit(EARTHEN_SPIRIT, 5)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.IRON_BOOTS, 1, MalumItems.SOUL_STAINED_STEEL_BOOTS.get(), 1)
                .addExtraItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(MalumItems.HEX_ASH.get(), 1)
                .addExtraItem(MalumItems.SOULSTONE.get(), 2)
                .addSpirit(WICKED_SPIRIT, 3)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addSpirit(EARTHEN_SPIRIT, 5)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.WHITE_WOOL, 1, MalumItems.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(Items.STRING, 2)
                .addExtraItem(MalumItems.HEX_ASH.get(), 1)
                .addSpirit(WICKED_SPIRIT, 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_HELMET, 1, MalumItems.SOUL_HUNTER_CLOAK.get(), 1)
                .addExtraItem(MalumItems.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(MalumItems.SOULSTONE.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 2)
                .addSpirit(AERIAL_SPIRIT, 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_CHESTPLATE, 1, MalumItems.SOUL_HUNTER_ROBE.get(), 1)
                .addExtraItem(MalumItems.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(MalumItems.SOULSTONE.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 2)
                .addSpirit(AERIAL_SPIRIT, 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_LEGGINGS, 1, MalumItems.SOUL_HUNTER_LEGGINGS.get(), 1)
                .addExtraItem(MalumItems.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(MalumItems.SOULSTONE.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 2)
                .addSpirit(AERIAL_SPIRIT, 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_BOOTS, 1, MalumItems.SOUL_HUNTER_BOOTS.get(), 1)
                .addExtraItem(MalumItems.SPIRIT_FABRIC.get(), 4)
                .addExtraItem(MalumItems.SOULSTONE.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 2)
                .addSpirit(AERIAL_SPIRIT, 4)
                .build(consumer);
    }
}