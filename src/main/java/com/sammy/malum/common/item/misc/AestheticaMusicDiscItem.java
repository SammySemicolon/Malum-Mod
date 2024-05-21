package com.sammy.malum.common.item.misc;

import com.sammy.malum.common.item.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.item.*;

public class AestheticaMusicDiscItem extends RecordItem implements IVoidItem {

    public AestheticaMusicDiscItem(Properties builder) {
        super(7, SoundRegistry.AESTHETICA, builder, 7920);
    }

    @Override
    public float getVoidParticleIntensity() {
        return 1.25f;
    }
}