package com.sammy.malum.common.recipe.spirit.focusing;

import com.sammy.malum.core.systems.recipe.*;
import net.minecraft.world.item.*;

import java.util.*;

public class SpiritFocusingRecipeInput extends SpiritBasedRecipeInput {

    protected SpiritFocusingRecipeInput(ItemStack input, List<ItemStack> spirits) {
        super(List.of(input), spirits);
    }
}
