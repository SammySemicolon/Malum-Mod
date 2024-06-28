package com.sammy.malum.common.item;

import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.world.item.RecordItem;

public class AestheticaMusicDiscItem extends RecordItem implements IVoidItem {

    public AestheticaMusicDiscItem(Properties builder) {
        super(7, SoundRegistry.AESTHETICA.get(), builder, 7920);
    }

    @Override
    public float getVoidParticleIntensity() {
        return 1.25f;
    }
}