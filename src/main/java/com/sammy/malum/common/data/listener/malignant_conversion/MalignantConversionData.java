package com.sammy.malum.common.data.listener.malignant_conversion;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.List;

public record MalignantConversionData(Holder<Attribute> sourceAttribute, double consumptionRatio,
                                      boolean ignoreBaseValue,
                                      List<MalignantConversionAttributePayout> payoutData) {
}
