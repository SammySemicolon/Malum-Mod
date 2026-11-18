package com.sammy.malum.common.recipe;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.recipe.*;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.*;
import net.minecraft.core.component.*;
import net.minecraft.core.registries.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import team.lodestar.lodestone.modules.toolkit.recipe.LodestoneInWorldRecipe;

import java.util.*;

public class SpiritInfusionRecipe extends LodestoneInWorldRecipe<SpiritBasedRecipeInput> {

    public static final MapCodec<SpiritInfusionRecipe> CODEC = RecordCodecBuilder.mapCodec((obj) -> obj.group(
            SizedIngredient.FLAT_CODEC.fieldOf("input").forGetter(recipe -> recipe.input),
            ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
            SpiritIngredient.CODEC.codec().listOf().fieldOf("spirits").forGetter(recipe -> recipe.spirits),
            SizedIngredient.FLAT_CODEC.listOf().optionalFieldOf("extraInputs", List.of()).forGetter(recipe -> recipe.extraInputs),
            Codec.BOOL.optionalFieldOf("carryOverComponentData", false).forGetter(recipe -> recipe.carryOverComponentData)
    ).apply(obj, SpiritInfusionRecipe::new));

    public static final String NAME = "spirit_infusion";

    public final SizedIngredient input;
    public final ItemStack result;

    public final List<SpiritIngredient> spirits;
    public final List<SizedIngredient> extraInputs;
    public final boolean carryOverComponentData;

    public SpiritInfusionRecipe(SizedIngredient input, ItemStack result, List<SpiritIngredient> spirits, List<SizedIngredient> extraInputs, boolean carryOverComponentData) {
        super(MalumRecipeSerializers.INFUSION_RECIPE_SERIALIZER.get(), MalumRecipeTypes.SPIRIT_INFUSION.get(), result);
        this.input = input;
        this.result = result;
        this.spirits = spirits;
        this.extraInputs = extraInputs;
        this.carryOverComponentData = carryOverComponentData;
    }

    @Override
    public boolean matches(SpiritBasedRecipeInput input, Level level) {
        return input.test(this.input, spirits);
    }

    public ItemStack getOutput(ServerLevel level, ItemStack input) {
        ItemStack outputStack = result.copy();
        if (carryOverComponentData) {
            List<DataComponentType<?>> toCopy = new ArrayList<>();
            for (TypedDataComponent<?> component : input.getComponents()) {
                var key = BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(component.type());
                if (key == null) {
                    throw new IllegalArgumentException("Data component type " + component.type() + " is not registered, somehow.");
                }
                Holder<DataComponentType<?>> holder = BuiltInRegistries.DATA_COMPONENT_TYPE.getHolder(key).orElseThrow();
                if (holder.is(MalumTags.DataComponents.SPIRIT_INFUSION_BLACKLIST)) {
                    continue;
                }
                toCopy.add(component.type());
            }
            for (DataComponentType<?> dataComponentType : toCopy) {
                if (dataComponentType.equals(net.minecraft.core.component.DataComponents.ENCHANTMENTS)) {
                    var lookup = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                    ItemEnchantments inputEnchantments = input.getAllEnchantments(lookup);
                    for (Object2IntMap.Entry<Holder<Enchantment>> entry : inputEnchantments.entrySet()) {
                        Holder<Enchantment> enchantment = entry.getKey();
                        int enchantmentLevel = entry.getIntValue();
                        if (outputStack.supportsEnchantment(enchantment)) {
                            outputStack.enchant(enchantment, enchantmentLevel);
                        }
                    }
                    continue;
                }
                outputStack.copyFrom(input, dataComponentType);
            }
        }
        return outputStack;
    }
}
