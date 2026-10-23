package com.sammy.malum.core.systems.spirit.umbral;

import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import com.sammy.malum.core.systems.spirit.SpiritColorProperties;
import com.sammy.malum.core.systems.spirit.SpiritTextData;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.*;

public class UmbralSpiritArcanaType extends SpiritArcanaType {

    public UmbralSpiritArcanaType(SpiritColorProperties colorProperties, DeferredHolder<Item, SpiritShardItem> spiritShard) {
        super(colorProperties, spiritShard);
    }

    @Override
    public SpiritTextData getTextData() {
        if (textData == null) {
            textData = new UmbralTextData(getRegistryName(), getPrimaryColor());
        }
        return textData;
    }
}
