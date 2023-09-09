package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.systems.item.*;
import net.minecraft.world.item.*;

public class FusedConsciousnessItem extends SimpleFoiledItem implements IVoidItem {
    public FusedConsciousnessItem(Properties builder) {
        super(builder);
    }

    @Override
    public float getVoidParticleIntensity() {
        return 1.25f;
    }
}