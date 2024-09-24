package com.sammy.malum.common.item.disc;

import com.sammy.malum.common.item.*;
import net.minecraft.world.item.*;

public class AestheticaMusicDiscItem extends Item implements IVoidItem {

    public AestheticaMusicDiscItem(Properties builder) {
        super(builder);
    }

    @Override
    public float getVoidParticleIntensity() {
        return 1.25f;
    }
}