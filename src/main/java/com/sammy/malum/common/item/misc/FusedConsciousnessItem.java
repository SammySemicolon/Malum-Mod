package com.sammy.malum.common.item.misc;

import com.sammy.malum.common.item.*;
import net.minecraft.world.item.SimpleFoiledItem;

public class FusedConsciousnessItem extends SimpleFoiledItem implements IVoidItem {
    public FusedConsciousnessItem(Properties builder) {
        super(builder);
    }

    @Override
    public float getVoidParticleIntensity() {
        return 1.25f;
    }
}