package com.sammy.malum.common.item.curiosities.nitrate;

import com.sammy.malum.common.entity.nitrate.VividNitrateEntity;
import net.minecraft.world.item.ItemStack;

public class VividNitrateItem extends AbstractNitrateItem {

    public VividNitrateItem(Properties pProperties) {
        super(pProperties, p -> new VividNitrateEntity(p, p.level()));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}