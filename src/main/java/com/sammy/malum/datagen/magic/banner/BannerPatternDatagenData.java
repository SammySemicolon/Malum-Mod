package com.sammy.malum.datagen.magic.banner;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.rite.effect.SpiritRiteEffect;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import com.sammy.malum.core.systems.spirit.SpiritLike;
import com.sammy.malum.datagen.magic.MalumMagicDatagenProvider;
import com.sammy.malum.datagen.magic.rite.SpiritRiteJsonBody;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.ArrayList;
import java.util.List;

import static com.sammy.malum.registry.common.magic.MalumSpiritTypes.*;
import static com.sammy.malum.registry.common.magic.rite.MalumSpiritRiteEffectTypes.*;
import static com.sammy.malum.registry.common.magic.rite.MalumSpiritRiteTypes.*;

public class BannerPatternDatagenData {

    public static void init(MalumMagicDatagenProvider provider) {
        create(provider, "sword");
    }

    public static BannerPatternJsonBody create(MalumMagicDatagenProvider provider, String id) {
        return provider.addData(MalumMod.malumPath(id), BannerPatternJsonBody::new);
    }
}